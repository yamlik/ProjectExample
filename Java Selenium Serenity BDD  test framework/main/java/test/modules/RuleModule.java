package com..test.modules;
import com..test.clients.TAFSCLIENT;
import com..test.database.DataBaseClient;
import io.restassured.path.json.JsonPath;
import net.serenitybdd.rest.SerenityRest;
import net.thucydides.core.annotations.Steps;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import com..test.clients.TAFSCLIENT;
import com..test.context.ScenarioContext;
import com..test.models.HttpRequest;
import com..test.models.HttpResponse;
import com..test.utilities.JSONHelper;
import com.google.gson.JsonParser;
import net.thucydides.core.annotations.Shared;
import org.json.simple.JSONValue;
import org.json.simple.parser.ParseException;
import org.junit.Assert;
import org.openqa.selenium.json.Json;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class RuleModule extends TAFSCLIENT {
    JsonParser jsonParser = new JsonParser();
    DataBaseClient dataBaseClient;

    @Steps
    RuleSetModule ruleSetModule;


    private final String deleteRuleUrl = propertiesManager.getProperty("productUnderTest.api.delete.rule");
    private final String createRuleUrl = propertiesManager.getProperty("productUnderTest.api.create.rule");
    private final String getResultTableRuleUrl = propertiesManager.getProperty("productUnderTest.api.get.resulttablefield");
    private final String getATSNTssTokenUrl = propertiesManager.getProperty("productUnderTest.api.get.aTSNTss.token.url");
    private final String authValue = propertiesManager.getProperty("auth.value");
    private final String getResultTableRuleUrlApplicationValue = propertiesManager.getProperty("productUnderTest.api.get.resulttablefield.applicationValue");


    public void deleteRule() throws IOException {

        String deleteRuleRequestBody = JSONHelper.messageAsSimpleString(System.getProperty("user.dir") + "/src/main/resources/data/API/rest/deleteRule.json");
        HttpRequest deleteRuleReq = buildRequest(deleteRuleUrl, deleteRuleRequestBody, defaultHeaders(scenarioContext.getATSNTssToken()), null);
        HttpResponse response = httpClient.post(deleteRuleReq);
        Assert.assertEquals(response.getResponseBody(), 200, response.getStatusCode());
            }

    public  void createARule(String key) throws IOException, ParseException {
        JSONObject dataDrive = scenarioContext.getDataDriveSectionAsJSON(key);
        HttpRequest createRuleReq = buildRequest(createRuleUrl,dataDrive.toString(), defaultHeaders(scenarioContext.getATSNTssToken()), null);
        HttpResponse response = httpClient.post(createRuleReq);
        Assert.assertEquals(response.getResponseBody(), 200, response.getStatusCode());

    }


    public void deleteARule() throws IOException, ParseException {
        JSONArray dataDrive = scenarioContext.getDataDriveSectionAsArray("deleteARule");
        HttpRequest deleteRuleReq = buildRequest(deleteRuleUrl,dataDrive.toString(), defaultHeaders(scenarioContext.getATSNTssToken()), null);
        HttpResponse response = httpClient.post(deleteRuleReq);
        Assert.assertEquals(response.getResponseBody(), 200, response.getStatusCode());
    }
    public  void createARuleWithResultTableHavingDatabaseFieldValue(String key) throws IOException, ParseException {

       JSONObject dataDrive = scenarioContext.getDataDriveSectionAsJSON(key);
       response = SerenityRest.given().request().headers(defaultHeaders(scenarioContext.getATSNTssToken())).get(environment+getResultTableRuleUrl);
       JsonPath jsonPathEvaluator = response.jsonPath();
       String variable  = jsonPathEvaluator.get("FieldId[2]").toString();
       HttpRequest createRuleReq = buildRequest(createRuleUrl,dataDrive.toString().replace("{{DatabaseFieldNumber}}", variable), defaultHeaders(scenarioContext.getATSNTssToken()), null);
       HttpResponse ruleResponse = httpClient.post(createRuleReq);
        Assert.assertEquals(ruleResponse.getResponseBody(), 200, ruleResponse.getStatusCode());

    }

    public  void createARuleWithResultTableHavingApplicationFieldValue(String key) throws IOException, ParseException {

        JSONObject dataDrive = scenarioContext.getDataDriveSectionAsJSON(key);
        response = SerenityRest.given().request().headers(defaultHeaders(scenarioContext.getATSNTssToken())).get(environment+getResultTableRuleUrlApplicationValue);
        JsonPath jsonPathEvaluator = response.jsonPath();
        String variable  = jsonPathEvaluator.get("FieldId[2]").toString();
        HttpRequest createRuleReq = buildRequest(createRuleUrl,dataDrive.toString().replace("{{ApplicationFieldNumber}}", variable), defaultHeaders(scenarioContext.getATSNTssToken()), null);
        HttpResponse ruleResponse = httpClient.post(createRuleReq);
        Assert.assertEquals(ruleResponse.getResponseBody(), 200, ruleResponse.getStatusCode());

    }
}
