package com..test.models;

import lombok.Builder;
import lombok.Data;
import java.util.Map;

@Data
@Builder
public class HttpResponse {
    private int statusCode;
    private String responseBody;
    private Map<String, String> responseHeaders;
    private Map<String, String> cookies;
    private String requestBody;
}