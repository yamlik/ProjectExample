package com..test.modules.definitions;

import com..test.clients.TAFSCLIENT;
import com..test.models.HttpRequest;
import com..test.models.HttpResponse;
import org.json.simple.parser.ParseException;
import org.junit.Assert;

import java.io.IOException;

public class OrganisationChartModule extends TAFSCLIENT {

    private final String clearOrgChartUrl = propertiesManager.getProperty("productUnderTest.api.delete.organisationchart.endpoint");

    public  void clearOrgChart() throws IOException, ParseException {
        HttpRequest clearOrgChartReq = buildRequest(clearOrgChartUrl,null, defaultHeaders(scenarioContext.getATSNTssToken()), null);
        HttpResponse response = httpClient.get(clearOrgChartReq);
        Assert.assertEquals(response.getResponseBody(), 200, response.getStatusCode());
    }
}
