package com.ifeb2.scdevsecurity.filter;

import cn.hutool.core.util.StrUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ifeb2.scdevbase.response.Result;
import com.ifeb2.scdevbase.utils.Constants;
import com.ifeb2.scdevbase.utils.RequestWrapper;
import com.ifeb2.scdevbase.utils.RsaUtils;
import com.ifeb2.scdevbase.utils.SHA;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Slf4j
public class RequestFilter extends OncePerRequestFilter {

    private final String prikey;
    private final Set<String> paramIgnores;

    private final RedisTemplate<String, Object> redisTemplate;

    public static final String SIGNATURE = "signature";
    public static final String TIME_STAMP = "timestamp";
    public static final String RANDOM_STR = "randomStr";
    public static final String RE_SUBMIT = "resubmit:uri:";

    private static final String POST = "POST";
    private static final String PUT = "PUT";
    private static final String SECRET_KEY = "secret_key";

    public RequestFilter(Set<String> paramIgnores, RedisTemplate<String, Object> redisTemplate, String prikey) {
        this.paramIgnores = paramIgnores;
        this.redisTemplate = redisTemplate;
        this.prikey = prikey;
    }

    private PathMatcher delegate = new AntPathMatcher();

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse
            response, FilterChain filterChain) throws ServletException, IOException {

        log.info("请求URI:{}", request.getRequestURI());
        log.info("http headers:{}", request.getHeader(HttpHeaders.AUTHORIZATION));

        if ("/v2/api-docs".equals(request.getRequestURI())) {
            filterChain.doFilter(request, response);
            return;
        }

        if (delegate.match("/actuator/**", request.getRequestURI())) {
            String val = request.getHeader(Constants.X_SBA_CLIENT_NAME);

            if (StrUtil.isBlank(val)) {
                errorResponse(response, "请求异常");
                return;
            }

            try {
                String text = RsaUtils.decryptByPrivateKey(prikey, val);
                if (!text.equals(Constants.X_SBA_CLIENT_VAL)) {
                    errorResponse(response, "请求异常");
                    return;
                }
            } catch (Exception e) {
                errorResponse(response, "请求异常");
                return;
            }

            //解析正确放行
            filterChain.doFilter(request, response);
            return;
        }

        if (paramIgnores.stream().anyMatch(uri -> delegate.match(uri, request.getRequestURI()))) {
            filterChain.doFilter(request, response);
            return;
        }

        RequestWrapper requestWrapper = new RequestWrapper(request, response);
        String body = requestWrapper.getBody();
        Map<String, Object> map = new HashMap<>();
        if (StringUtils.isBlank(body)) {
            Map<String, String[]> params = request.getParameterMap();
            Map<String, Object> finalMap = map;
            params.forEach((key, value) -> finalMap.put(key, value[0]));
        } else {
            map = strToMap(body);
        }
        if (null == map) {
            log.info("请求异常");
            errorResponse(response, "请求异常");
            return;
        }
        handlerParam(map);

        if (null == map.get("appid") || StringUtils.isBlank(map.get("appid").toString())) {
            log.info("请求参数异常");
            errorResponse(response, "请求参数异常");
            return;
        }

        if (null == map.get(SIGNATURE) || StringUtils.isBlank(map.get(SIGNATURE).toString())) {
            log.info("请求参数异常");
            errorResponse(response, "请求参数异常");
            return;
        }

        if (null == map.get(TIME_STAMP) || StringUtils.isBlank(map.get(TIME_STAMP).toString())) {
            log.info("请求参数异常");
            errorResponse(response, "请求参数异常");
            return;
        } else {
            //判断是否时间戳 true 表示参数异常
            if (ckIsTimestamp(map.get(TIME_STAMP).toString())) {
                log.info("请求参数异常");
                errorResponse(response, "请求参数异常");
                return;
            }
        }

        if (null == map.get(RANDOM_STR) || StringUtils.isBlank(map.get(RANDOM_STR).toString())) {
            log.info("请求参数异常");
            errorResponse(response, "请求参数异常");
            return;
        }

        String secretKey = request.getHeader(SECRET_KEY);

        if (null == secretKey) {
            errorResponse(response, "请求参数异常");
            return;
        }

        String sign = SHA.SHA256Str(map.get("signStr").toString() + secretKey);
        if (!map.get(SIGNATURE).equals(sign)) {
            log.info("请求参数异常");
            errorResponse(response, "请求参数异常");
            return;
        }

        if (request.getMethod().equals(POST) || request.getMethod().equals(PUT)) {
            String re_key = RE_SUBMIT + request.getRequestURI();
            boolean res = repeat(re_key, map);
            if (res) {
                errorResponse(response, "重复请求失败");
                return;
            }
        }

        filterChain.doFilter(requestWrapper, response);
    }


    private boolean repeat(String key, Map<String, Object> map) {
        Map<String, Object> param = (Map<String, Object>) redisTemplate.opsForValue().get(key);
        if (null != param) {
            Map<String, Object> targetMap = filterParam(map);
            long time1 = Long.parseLong(targetMap.get(TIME_STAMP).toString());//当前请求
            long time2 = Long.parseLong(param.get(TIME_STAMP).toString());//上次请求
            // 两次hash只一样并且 值小于5 就
            return param.get("signStr").equals(targetMap.get("signStr")) && (time1 - time2) < (2 * 1000);
        } else {
            redisTemplate.opsForValue().set(key, filterParam(map), 10, TimeUnit.SECONDS);
        }
        return false;
    }

    private void errorResponse(HttpServletResponse response, String msg) throws IOException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        PrintWriter writer = response.getWriter();
        writer.println(toJson(Result.error(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), msg)));
        writer.close();
    }

    private String toJson(Result result) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writeValueAsString(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "{}";
    }

    private Map<String, Object> filterParam(Map<String, Object> map) {
        Map<String, Object> targetMap = new HashMap<>();
        // 构造加密字符串
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, Object> entry : getEntryList(map)) {
            if (entry.getKey().equals("randomStr") || entry.getKey().equals("signature")
                    || entry.getKey().equals("signStr")
                    || entry.getKey().equals("timestamp")) {
                continue;
            }
            sb.append(entry.getKey()).append("=").append(entry.getValue());
            sb.append("&");
        }
        targetMap.put("signStr", SHA.SHA256Str(sb.toString()));
        targetMap.put(TIME_STAMP, map.get(TIME_STAMP));
        return targetMap;
    }

    private List<Map.Entry<String, Object>> getEntryList(Map<String, Object> map) {
        // 字典排序
        List<Map.Entry<String, Object>> entryList = new ArrayList<>(map.entrySet());
        // 对所有传入参数按照字段名的 ASCII 码从小到大排序（字典序）
        entryList.sort(Map.Entry.comparingByKey());
        return entryList;
    }

    private void handlerParam(Map<String, Object> map) {
        // 构造加密字符串
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, Object> entry : getEntryList(map)) {
            if (entry.getKey().equals("signature")) {
                continue;
            }
            sb.append(entry.getKey()).append("=").append(entry.getValue().toString());
            sb.append("&");
        }
        map.put("signStr", sb.toString());
        log.info("请求参数：{}", map);
        log.info("加密参数：{}", sb);
    }

    /**
     * 检测是否时间戳
     *
     * @param timestamp
     * @return
     */
    private Boolean ckIsTimestamp(String timestamp) {

        if (timestamp.length() < 13) {
            return true;
        }
        try {
            new Date(Long.parseLong(timestamp));
        } catch (Exception e) {
            e.printStackTrace();
            return true;
        }

        return false;
    }


    public static Map strToMap(String str) {
        ObjectMapper mapper = new ObjectMapper();
        Map map = null;
        try {
            map = mapper.readValue(str, Map.class);
        } catch (JsonMappingException e) {
            log.error("map集合参数转换异常:{}", "JsonMappingException");
        } catch (JsonProcessingException e) {
            log.error("map集合参数转换异常:{}", "JsonProcessingException");
        }
        return map;
    }

}