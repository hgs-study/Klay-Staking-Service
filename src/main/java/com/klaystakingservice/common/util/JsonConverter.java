package com.klaystakingservice.common.util;

import org.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class JsonConverter {

    public String responseEntityToValue(ResponseEntity<String> responseEntity, String key) {
        JSONObject jsonObject = new JSONObject(responseEntity);
        String body = jsonObject.getString("body");

        JSONObject bodyJsonObject = new JSONObject(body);
        return bodyJsonObject.getString(key);
    }
}
