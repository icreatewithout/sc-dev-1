package com.ifeb2.scdevbase.utils;

import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import eu.bitwalker.useragentutils.Browser;
import eu.bitwalker.useragentutils.UserAgent;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class Constants {

    public static String X_SBA_CLIENT_NAME = "X-SBA-CLIENT";
    public static String X_SBA_CLIENT_VAL = "X-SBA-CLIENT-REQUEST-INFO";

    public static final Long DEPT_CODE = 1000L;

    public static final String ROLE = "ROLE_";

    public static final String DEFAULT_PASSWD = "000000";

    public static final String NORMAL = "0";
    public static final String NORMAL1 = "1";

    public static final String DEl = "1";
    public static final String NOT_DEl = "0";

    private static final String UNKNOWN = "unknown";

    public static final String WHOIS_URL = "http://whois.pconline.com.cn/ipJson.jsp?ip=%s&json=true";

    public static String getBrowser(HttpServletRequest request) {
        UserAgent userAgent = UserAgent.parseUserAgentString(request.getHeader("User-Agent"));
        Browser browser = userAgent.getBrowser();
        return browser.getName();
    }

    public static String getCityInfoByIp(String ip) {
        String api = String.format(WHOIS_URL, ip);
        JSONObject object = JSONUtil.parseObj(HttpUtil.get(api));
        return object.get("addr", String.class);
    }

    public static String getIp(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        String comma = ",";
        String localhost = "127.0.0.1";
        if (ip.contains(comma)) {
            ip = ip.split(",")[0];
        }
        if (localhost.equals(ip)) {
            try {
                ip = InetAddress.getLocalHost().getHostAddress();
            } catch (UnknownHostException e) {
                e.printStackTrace();
            }
        }
        return ip;
    }
}
