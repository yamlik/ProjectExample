package com..test.models;

import lombok.Builder;
import lombok.Data;
import org.apache.commons.collections.MultiMap;
import java.util.Map;

@Data
@Builder
public class HttpRequest {
    private String url;
    private Map<String, String> headers;
    private Map<String, String> queryParams;
    private MultiMap formData;
    private String requestBody;

}
