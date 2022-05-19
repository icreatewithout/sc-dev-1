package com.ifeb2.scdevcore.captcha;

import com.wf.captcha.*;
import com.wf.captcha.base.Captcha;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.awt.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class CaptchaService {

    private final CaptchaProperties properties;
    private Captcha captcha;

    /**
     * 依据配置信息生产验证码  CaptchaProperties
     *
     * @return /
     */
    public Captcha getCaptcha() {

        synchronized (this) {
            switch (properties.getCodeType()) {
                case arithmetic:
                    // 算术类型 https://gitee.com/whvse/EasyCaptcha
                    captcha = new ArithmeticCaptcha(properties.getWidth(), properties.getHeight());
                    // 几位数运算，默认是两位
                    captcha.setLen(properties.getLength());
                    break;
                case chinese:
                    captcha = new ChineseCaptcha(properties.getWidth(), properties.getHeight());
                    captcha.setLen(properties.getLength());
                    break;
                case chinese_gif:
                    captcha = new ChineseGifCaptcha(properties.getWidth(), properties.getHeight());
                    captcha.setLen(properties.getLength());
                    break;
                case gif:
                    captcha = new GifCaptcha(properties.getWidth(), properties.getHeight());
                    captcha.setLen(properties.getLength());
                    break;
                case spec:
                    captcha = new SpecCaptcha(properties.getWidth(), properties.getHeight());
                    captcha.setLen(properties.getLength());
                    break;
                default:
                    log.error("验证码配置信息错误");
            }
        }
        if (StringUtils.isNotBlank(properties.getFontName())) {
            captcha.setFont(new Font(properties.getFontName(), Font.PLAIN, properties.getFontSize()));
        }
        return captcha;
    }

}
