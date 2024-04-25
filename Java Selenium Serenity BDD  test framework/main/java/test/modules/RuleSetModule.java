package com..test.modules;

import com..test.clients.TAFSCLIENT;
import com..test.context.ScenarioContext;
import com..test.models.HttpRequest;
import com..test.models.HttpResponse;
import com..test.utilities.JSONHelper;
import com.google.gson.JsonParser;
import io.restassured.response.Response;
import net.thucydides.core.annotations.Shared;
import org.junit.Assert;
import java.io.IOException;

public class RuleSetModule extends TAFSCLIENT {

    private final String getRuleSetUrl = propertiesManager.getProperty("productUnderTest.api.get.rule.set.url");
    private final String deleteRuleSetUrl = propertiesManager.getProperty("productUnderTest.api.delete.rule.set.url");

    public Response getRuleSet() {
        return request().headers(defaultHeaders).get(environment+getRuleSetUrl);
    }

    public void deleteRuleSet(String ruleSetName) throws IOException {
        String deleteRuleSetRequestBody = JSONHelper.messageAsSimpleString(System.getProperty("user.dir") + "/src/main/resources/data/API/rest/deleteRuleSet.json");
        deleteRuleSetRequestBody = deleteRuleSetRequestBody.replace("{{ruleSet}}", ruleSetName);
        request().headers(defaultHeaders).body(deleteRuleSetRequestBody).post(environment+deleteRuleSetUrl).then().statusCode(200).log().body();
    }
}
