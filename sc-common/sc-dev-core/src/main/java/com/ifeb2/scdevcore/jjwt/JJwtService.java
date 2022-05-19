package com.ifeb2.scdevcore.jjwt;

import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateUtil;
import com.ifeb2.scdevbase.domain.LoginUser;
import com.ifeb2.scdevbase.utils.RsaUtils;
import com.ifeb2.scdevcore.jjwt.properties.JJwtProperties;
import com.ifeb2.scdevcore.jjwt.properties.RsaProperties;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.security.Key;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
@RequiredArgsConstructor
public class JJwtService implements InitializingBean {

    private final JJwtProperties properties;
    private final RsaProperties rsaProperties;
    private final RedisTemplate<String, Object> redisTemplate;

    private JwtParser jwtParser;
    private JwtBuilder jwtBuilder;

    private final String SC_ID = "sc_id:";

    @Override
    public void afterPropertiesSet() throws Exception {
        String s = RsaUtils.decryptByPrivateKey(rsaProperties.getPrivateKey(), properties.getBase64Secret());
        byte[] keyBytes = Decoders.BASE64.decode(s);
        Key key = Keys.hmacShaKeyFor(keyBytes);
        jwtParser = Jwts.parserBuilder()
                .setSigningKey(key)
                .build();
        jwtBuilder = Jwts.builder()
                .signWith(key, SignatureAlgorithm.HS512);
    }

    public String createToken(LoginUser loginUser) {
        return jwtBuilder
                // 加入ID确保生成的 Token 都不一致
                .setId(String.valueOf(loginUser.getId()))
                .claim(SC_ID, loginUser.getId())
                .setSubject(loginUser.getUsersVo().getId().toString())
                .compact();
    }

    public LoginUser getLoginUser(HttpServletRequest request) {
        String token = getToken(request);
        if (null != token) {
            try {
                Claims claims = getClaims(token);
                if (claims == null) {
                    return null;
                }
                Long id = claims.get(SC_ID, Long.class);
                LoginUser loginUser = (LoginUser) redisTemplate.opsForValue().get(properties.getOnlineKey() + id.toString());
                if (null != loginUser) {
                    return loginUser;
                }
            } catch (Exception e) {
                log.info("解析错误：{}", e.getMessage());
                e.printStackTrace();
            }

        }
        return null;
    }

    public String getToken(HttpServletRequest request) {
        final String requestHeader = request.getHeader(properties.getHeader());
        final String bearer = properties.getBearer() + " ";
        if (requestHeader != null && requestHeader.startsWith(bearer)) {
            return requestHeader.replace(bearer, "");
        }
        return null;
    }

    public void checkRenewal(Long id) {
        // 判断是否续期token,计算token的过期时间
        long time = redisTemplate.getExpire(properties.getOnlineKey() + id);
        Date expireDate = DateUtil.offset(new Date(), DateField.MILLISECOND, (int) time);
        // 判断当前时间与过期时间的时间差
        long differ = expireDate.getTime() - System.currentTimeMillis();
        // 如果在续期检查的范围内，则续期 10分钟
        if (differ <= properties.getDetect() * 60) {
            long renew = time + properties.getRenew();
            redisTemplate.expire(properties.getOnlineKey() + id, renew, TimeUnit.SECONDS);
        }
    }


    /**
     * 刷新令牌有效期
     *
     * @param loginUser 登录信息
     */
    public void cacheToken(LoginUser loginUser) {
        loginUser.setLoginTime(System.currentTimeMillis());
        loginUser.setExpireTime(loginUser.getLoginTime() + properties.getExpired() * 60); //30*60*1000 毫秒
        redisTemplate.opsForValue().set(properties.getOnlineKey() + loginUser.getId(), loginUser, properties.getExpired() * 60, TimeUnit.SECONDS);
    }

    public Claims getClaims(String token) {
        try {
            return jwtParser
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


}
