package com..test.modules.definitions;

import com..test.clients.TAFSCLIENT;
import com..test.models.HttpRequest;
import com..test.models.HttpResponse;
import org.junit.Assert;

import java.util.HashMap;
import java.util.Map;

public class TeamModule extends TAFSCLIENT {

    private final String deleteTeamUrl = propertiesManager.getProperty("productUnderTest.api.delete.team.endpoint");

    private Map<String, String> getQueryParamsDeleteTeam(String team) {
        Map<String, String> queryParams = new HashMap<>();
        queryParams.put("teamName", team);
        return queryParams;
    }

    public  void deleteTeam(String team) {
        HttpRequest deleteTeamReq = buildRequest(deleteTeamUrl,null, defaultHeaders(scenarioContext.getATSNTssToken()), getQueryParamsDeleteTeam(team));

        HttpResponse response = httpClient.get(deleteTeamReq);
        Assert.assertEquals(response.getResponseBody(), 200, response.getStatusCode());
    }
}
