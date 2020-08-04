package com.macro.mall.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.macro.mall.utils.HttpUtils;
import io.lettuce.core.dynamic.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 *
 */
@Component
public class WxConfig {

    private String appId="wx13c3f0b68cc0c707";

    private String backedAppId;

    private String backedAppSecret;

    private String appSecret="77b0e887601e3712e7bcf836a7da7359";

    private String grantType="authorization_code";

    private String requestUrl="https://api.weixin.qq.com/sns/jscode2session";

    private String tokenUrl;

    private String unionUrl;

    /*获取用户的openid*/
    @SuppressWarnings("unchecked")
    public Map<String, Object> getSessionByCode(String code) {
        Map<String, Object> json = null;
        try {
            String url = requestUrl + "?appid=" + appId + "&secret=" + appSecret + "&js_code=" + code + "&grant_type=" + grantType;
            // 发送请求
            String data = HttpUtils.get(url);
            ObjectMapper mapper = new ObjectMapper();
            json = mapper.readValue(data, Map.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return json;
    }

    /*获取用户的access_token*/
    @SuppressWarnings("unchecked")
    public Map<String, Object> getUserAccessToken(String code) {
        Map<String, Object> json = null;
        try {
            String url = tokenUrl + "?appid=" + appId + "&secret=" + appSecret + "&code=" + code + "&grant_type=authorization_code";
            // 发送请求
            System.out.println(url);
            String data = HttpUtils.get(url);
            ObjectMapper mapper = new ObjectMapper();
            json = mapper.readValue(data, Map.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return json;
    }

    /*获取用户的unionId*/
    @SuppressWarnings("unchecked")
    public Map<String, Object> getUnionId(String token, String openId) throws Exception {
        Map<String, Object> json = null;
        try {
            String url = unionUrl + "?access_token=" + token + "&openid=" + openId;
            // 发送请求
            String data = HttpUtils.get(url);
            ObjectMapper mapper = new ObjectMapper();
            json = mapper.readValue(data, Map.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return json;
    }
}
