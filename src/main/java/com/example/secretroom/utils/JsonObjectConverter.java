package com.example.secretroom.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import static org.aspectj.bridge.MessageUtil.fail;

public class JsonObjectConverter {

    public static String objectToJson(Object object) {
        try{
            return new ObjectMapper().writeValueAsString(object);
        } catch (JsonProcessingException e) {
            fail("file to serialize object to Json");
            return null;
        }
    };

}
