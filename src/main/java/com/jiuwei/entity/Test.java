package com.jiuwei.entity;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

public class Test {
    public static void main(String[] args) {
        String response="{\n" +
                " \"access_token\": \"35_GwAeiP2-KUb6y25N7lQvKB3hXnY8FVZWp69IixDCoUILowqSw8omrGfDLTDg0ryJv6o4ruX-H9W3y7_PQ8buFmjwIBOAsGW-6WqfJv_TPF0\",\n" +
                " \"expires_in\": 7200,\n" +
                " \"refresh_token\": \"35___ULtmWihLQ2uPOX6NaOj2cbBbARd35VsOlJk3CHBVgQAH6Jj5iN9lV7Wzdw_CzPHMohYyvbZvUcWYxq-yKLjonq-f4k6mgfQHwfxMEQiDA\",\n" +
                " \"openid\": \"oyCVU1efQtlNMtSLaM6kIOHVj3Ds\",\n" +
                " \"scope\": \"snsapi_base\"\n" +
                "}";
        JSONObject json= JSON.parseObject(response);
        System.out.println(json.getString("access_token"));
        System.out.println(json.getString("openid"));
    }
}
