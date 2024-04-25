package com..test.modules;

import com..test.clients.TAFSCLIENT;
import com..test.context.ScenarioContext;
import com..test.models.HttpRequest;
import com..test.models.HttpResponse;
import com..test.utilities.JSONHelper;
import net.serenitybdd.rest.SerenityRest;
import net.thucydides.core.annotations.Shared;
import org.junit.Assert;
import java.io.IOException;

public class ApplicationModule extends TAFSCLIENT {

    private final String deleteAllApplicationUrl = propertiesManager.getProperty("productUnderTest.api.delete.all.application.endpoint");
    private final String deleteAllWatchlistUrl = propertiesManager.getProperty("productUnderTest.api.delete.all.watchlist.endpoint");
    private final String createApplicationUrl = propertiesManager.getProperty("productUnderTest.service.url");



    public void createApplication(String income,String applicationNumber) throws IOException {
        String createApplicationRequestBody = JSONHelper.messageAsSimpleString(System.getProperty("user.dir") + "/src/main/resources/data/API/rest/createApplication.json");
        createApplicationRequestBody = createApplicationRequestBody.replace("{{INCOME}}", income);
        createApplicationRequestBody = createApplicationRequestBody.replace("{{APPLICATION_NUMBER}}", applicationNumber);
        createApplicationRequestBody = createApplicationRequestBody.replace("{{organisation}}", scenarioContext.getOrganisation());
        createApplicationRequestBody = createApplicationRequestBody.replace("{{country}}", scenarioContext.getCountry());
        HttpRequest createApplicationReq = buildRequestApplication(createApplicationUrl, createApplicationRequestBody, createApplicationHeader(), null);
        HttpResponse response =  httpClient.post(createApplicationReq);
        Assert.assertEquals(response.getResponseBody(), 200, response.getStatusCode());
    }

    public void deleteAllApplication() throws IOException {
        String deleteAllApplicationRequestBody = JSONHelper.messageAsSimpleString(System.getProperty("user.dir") + "/src/main/resources/data/API/rest/deleteAllApplication.json");
        SerenityRest.given().request().headers(defaultHeaders).body(deleteAllApplicationRequestBody).post(environment+deleteAllApplicationUrl).then().statusCode(200);
    }

    public void deleteAllWatchlist() throws IOException {
        String deleteAllWatchlistRequestBody = JSONHelper.messageAsSimpleString(System.getProperty("user.dir") + "/src/main/resources/data/API/rest/deleteAllWatchlist.json");
        request().headers(defaultHeaders).body(deleteAllWatchlistRequestBody).post(environment+deleteAllWatchlistUrl).then().statusCode(200);
    }



}
