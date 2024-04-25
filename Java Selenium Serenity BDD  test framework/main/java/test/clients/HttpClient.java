package com..test.clients;

import com..test.models.HttpRequest;
import com..test.models.HttpResponse;
import com..test.utilities.PropertiesManager;
import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import net.serenitybdd.rest.SerenityRest;
import org.apache.commons.collections.MultiMap;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.proxy;

public class HttpClient<T extends HttpRequest, V extends HttpResponse> implements  IHttpClient<T, V>{

    private final PropertiesManager propertiesManager = PropertiesManager.get();

    public HttpResponse post(HttpRequest httpRequest) {
        RequestSpecification requestSpecification = updateRequestSpecification(httpRequest);
        return parseResponse(requestSpecification.relaxedHTTPSValidation().post(), httpRequest.getRequestBody());
    }

    public HttpResponse put(HttpRequest httpRequest) {
        RequestSpecification requestSpecification = updateRequestSpecification(httpRequest);
        return parseResponse(requestSpecification.put(httpRequest.getUrl()), httpRequest.getRequestBody());
    }

    public HttpResponse delete(HttpRequest httpRequest) {
        RequestSpecification requestSpecification = updateRequestSpecification(httpRequest);
        return parseResponse(requestSpecification.delete(httpRequest.getUrl()),httpRequest.getRequestBody());
    }

    public HttpResponse get(HttpRequest httpRequest) {
        RequestSpecification requestSpecification = updateRequestSpecification(httpRequest);
        return parseResponse(requestSpecification.get(httpRequest.getUrl()),httpRequest.getRequestBody());
    }

    /**
     * Parses the RestAssured Response to HttpResponse
     * @param response RestAssured response Object
     * @return HttpResponse
     */
    private HttpResponse parseResponse(Response response, String requestBody) {
        String responseBody = response.getBody().asString();
        Map<String, String> headers = getResponseHeaders(response.getHeaders());
        Map<String, String> cookies = response.getCookies();
        int statusCode = response.getStatusCode();
       return HttpResponse.builder()
                .statusCode(statusCode)
                .cookies(cookies)
                .responseHeaders(headers)
                .responseBody(responseBody)
                .requestBody(requestBody)
                .build();
    }

    private Map<String, String> getResponseHeaders(Headers headers) {
        Map<String, String> headerMap = new HashMap<>();
        for(Header header: headers) {
            String name = header.getName();
            String value = header.getValue();
            headerMap.put(name, value);
        }
        return headerMap;
    }

    /**
     * return RequestSpecification object
     * @return
     */
    private RequestSpecification getRequestSpec(String endpoint) {
        baseURI = endpoint;
        String proxyHost = propertiesManager.getProperty("");
        String proxyPort = propertiesManager.getProperty("");
        if(proxyHost != null && proxyPort != null) {
            proxy(proxyHost, Integer.parseInt(proxyPort));
        }
        SerenityRest.useRelaxedHTTPSValidation();
        return SerenityRest.given();
    }

    /**
     * updates the request specification object with the request metadata such as url, headers, cookies, body
     * @param httpRequest
     * @return
     */
    private RequestSpecification updateRequestSpecification(HttpRequest httpRequest) {
        String url  = httpRequest.getUrl();
        Map<String, String> headers = httpRequest.getHeaders();
        Map<String, String> queryParams = httpRequest.getQueryParams();
        MultiMap formData = httpRequest.getFormData();
        String requestBody = httpRequest.getRequestBody();
        RequestSpecification requestSpecification = getRequestSpec(url);
        if(headers != null && headers.size() > 0) {
            requestSpecification.headers(headers);
        }
        if(queryParams != null && queryParams.size() > 0) {
            requestSpecification.queryParams(queryParams);
        }
        if(requestBody != null && !requestBody.equals("")) {
            requestSpecification.body(requestBody);
        }
        if(formData != null && formData.size()>0) {
            for(Object entry: formData.entrySet()) {
                Map.Entry entryKV = ((Map.Entry)entry);
                if(entryKV.getKey().equals("file")) {
                    //TODO the mime type is currently hard coded, this must be changed when the file type we upload changes
                    requestSpecification.multiPart("file", (File)((List)entryKV.getValue()).get(0), "application/pdf");
                } else {
                    requestSpecification.formParam((String) entryKV.getKey(), ((List)entryKV.getValue()).get(0));
                }
            }
        }
        requestSpecification = requestSpecification.relaxedHTTPSValidation();
        return requestSpecification;
    }
}