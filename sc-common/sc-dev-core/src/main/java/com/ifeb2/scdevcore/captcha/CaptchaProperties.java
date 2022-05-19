package com.ifeb2.scdevcore.captcha;

import com.ifeb2.scdevcore.captcha.enums.CaptchaType;
import lombok.Data;

@Data
public class CaptchaProperties {

    private boolean singleLogin;

    public boolean isSingleLogin() {
        return singleLogin;
    }

    private Long expiration;

    private int length;

    private int width;

    private int height;

    private String fontName;

    private int fontSize;

    private CaptchaType codeType;

}
