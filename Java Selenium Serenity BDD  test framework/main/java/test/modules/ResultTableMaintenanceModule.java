package com..test.modules;
import com..test.clients.TAFSCLIENT;
import net.thucydides.core.annotations.Steps;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import com..test.context.ScenarioContext;
import com..test.models.HttpRequest;
import com..test.models.HttpResponse;
import com..test.utilities.JSONHelper;
import com.google.gson.JsonParser;
import net.thucydides.core.annotations.Shared;
import org.json.simple.parser.ParseException;
import org.junit.Assert;
import java.io.IOException;

public class ResultTableMaintenanceModule extends TAFSCLIENT {

    private final String createResultTableUrl = propertiesManager.getProperty("productUnderTest.api.create.resulttable");
    private final String createResultTableFieldUrl = propertiesManager.getProperty("productUnderTest.api.create.resulttablefield");
    private final String getResultTableRuleUrl = propertiesManager.getProperty("productUnderTest.api.get.resulttablefield");

    private final String createWorkflowUrl = propertiesManager.getProperty("productUnderTest.api.create.workflow");
    private final String getATSNTssTokenUrl = propertiesManager.getProperty("productUnderTest.api.get.aTSNTss.token.url");
    private final String authValue = propertiesManager.getProperty("auth.value");

    public  void createAResultTable(String key) throws IOException, ParseException {
        JSONObject dataDrive = scenarioContext.getDataDriveSectionAsJSON(key);
        HttpRequest createResultTableReq = buildRequest(createResultTableUrl,dataDrive.toString(), defaultHeaders(scenarioContext.getATSNTssToken()), null);
        HttpResponse response = httpClient.post(createResultTableReq);
        Assert.assertEquals(response.getResponseBody(), 200, response.getStatusCode());
    }

    public  void createAResultTableFields(String key) throws IOException, ParseException {

        JSONObject dataDrive = scenarioContext.getDataDriveSectionAsJSON(key);
        HttpRequest createResultTableFields = buildRequest(createResultTableFieldUrl,dataDrive.toString(), defaultHeaders(scenarioContext.getATSNTssToken()), null);
        HttpResponse response = httpClient.post(createResultTableFields);
        Assert.assertEquals(response.getResponseBody(), 200, response.getStatusCode());
    }

    public void createAWorkflow(String key) throws IOException, ParseException {
        JSONObject dataDrive = scenarioContext.getDataDriveSectionAsJSON(key);
        HttpRequest createWorkflowReq = buildRequest(createWorkflowUrl,dataDrive.toString(), defaultHeaders(scenarioContext.getATSNTssToken()), null);
        HttpResponse response = httpClient.post(createWorkflowReq);
        Assert.assertEquals(response.getResponseBody(), 200, response.getStatusCode());
    }


}
