package com..test.modules.services;

import com..test.clients.TAFSCLIENT;
import com..test.models.HttpRequest;
import com..test.models.HttpResponse;
import com..test.utilities.JSONHelper;
import org.json.JSONObject;
import org.junit.Assert;

import java.io.IOException;

public class productUnderTestServiceModule extends TAFSCLIENT {

    private final String deleteAllApplicationUrl = propertiesManager.getProperty("productUnderTest.api.delete.all.application.endpoint");

    private final String productUnderTestServiceUrl = propertiesManager.getProperty("productUnderTest.service.url");

    public void createPostApplication(String applicationNumber) throws IOException {
        String createApplicationRequestBody = JSONHelper.messageAsSimpleString(System.getProperty("user.dir") + "/src/main/resources/data/API/rest/productUnderTestService/productUnderTestServicePostRequest.json");
        createApplicationRequestBody = createApplicationRequestBody.replace("{{APPLICATION_NUMBER}}", applicationNumber).replace("{{organisation}}", scenarioContext.getOrganisation()).replace("{{country}}", scenarioContext.getCountry());

        HttpRequest createApplicationReq = buildRequestApplication(productUnderTestServiceUrl, createApplicationRequestBody, createApplicationHeader(), null);
        HttpResponse response = httpClient.post(createApplicationReq);

        Assert.assertEquals(response.getResponseBody(), 200, response.getStatusCode());
    }

    public JSONObject createGetApplication(String applicationNumber) throws IOException {
        String createApplicationRequestBody = JSONHelper.messageAsSimpleString(System.getProperty("user.dir") + "/src/main/resources/data/API/rest/productUnderTestService/productUnderTestServiceGetRequest.json");
      System.out.println(createApplicationRequestBody);

        createApplicationRequestBody = createApplicationRequestBody.replace("{{APPLICATION_NUMBER}}", applicationNumber).replace("{{organisation}}", scenarioContext.getOrganisation()).replace("{{country}}", scenarioContext.getCountry());;
        System.out.println(createApplicationRequestBody);
        HttpRequest createApplicationReq = buildRequestApplication(productUnderTestServiceUrl, createApplicationRequestBody, createApplicationHeader(), null);
        HttpResponse response = httpClient.get(createApplicationReq);
        System.out.println(response.getResponseBody());


        JSONObject responseHolder = new JSONObject(response.getResponseBody());

        Assert.assertEquals(response.getResponseBody(), 200, response.getStatusCode());
        return responseHolder;

    }

    public void createPutApplication(String applicationNumber, String income) throws IOException {
        String createApplicationRequestBody = JSONHelper.messageAsSimpleString(System.getProperty("user.dir") + "/src/main/resources/data/API/rest/productUnderTestService/productUnderTestServicePutRequest.json");
        createApplicationRequestBody = createApplicationRequestBody.replace("{{APPLICATION_NUMBER}}", applicationNumber).replace("{{INCOME}}", income).replace("{{organisation}}", scenarioContext.getOrganisation()).replace("{{country}}", scenarioContext.getCountry());;

        HttpRequest createApplicationReq = buildRequestApplication(productUnderTestServiceUrl, createApplicationRequestBody, createApplicationHeader(), null);
        HttpResponse response =  httpClient.put(createApplicationReq);

        Assert.assertEquals(response.getResponseBody(), 200, response.getStatusCode());
    }


}
