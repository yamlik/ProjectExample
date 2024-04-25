package com..test.modules.rules;

import com..test.clients.TAFSCLIENT;
import com..test.models.HttpRequest;
import com..test.models.HttpResponse;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.junit.Assert;

import java.io.IOException;

public class RuleAdminGroups extends TAFSCLIENT {

    private final String createRuleAdminGroupUrl = propertiesManager.getProperty("productUnderTest.api.create.ruleadmingroup.endpoint");
    private final String deleteRuleAdminGroupUrl = propertiesManager.getProperty("productUnderTest.api.delete.ruleadmingroup.endpoint");

    public  void createRuleAdminGroup() throws IOException, ParseException {
        JSONObject ruleAdminGrpReq = scenarioContext.getDataDriveSectionAsJSON("createRuleAdminGroup");
        HttpRequest createRuleAdminGrpReq = buildRequest(createRuleAdminGroupUrl,ruleAdminGrpReq.toString(), defaultHeaders(scenarioContext.getATSNTssToken()), null);
        HttpResponse response = httpClient.post(createRuleAdminGrpReq);
        Assert.assertEquals(response.getResponseBody(), 200, response.getStatusCode());
    }

    public  void deleteRuleAdminGroup(String key) throws IOException, ParseException {
        JSONObject ruleAdminGrpReq = scenarioContext.getDataDriveSectionAsJSON(key);
        HttpRequest deleteRuleAdminGrpReq = buildRequest(deleteRuleAdminGroupUrl,ruleAdminGrpReq.toString(), defaultHeaders(scenarioContext.getATSNTssToken()), null);
        HttpResponse response = httpClient.post(deleteRuleAdminGrpReq);
        Assert.assertEquals(response.getResponseBody(), 200, response.getStatusCode());
    }


}
