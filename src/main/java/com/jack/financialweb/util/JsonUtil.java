package com.jack.financialweb.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonUtil {
    private final static ObjectMapper objectMapper =
            new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    public static String toJson(Object obj) throws JsonProcessingException {
        return objectMapper.writeValueAsString(obj);
    }

    public static <T> T toObject(String json, Class<T> objectClass) throws JsonProcessingException {
        return objectMapper.readValue(json, objectClass);
    }

    public static <T> T toObject(String json, TypeReference<T> valueTypeRef) throws JsonProcessingException {
        return objectMapper.readValue(json, valueTypeRef);
    }

    public static JsonNode toNode(String json) throws JsonProcessingException {
        return objectMapper.readTree(json);
    }
}
