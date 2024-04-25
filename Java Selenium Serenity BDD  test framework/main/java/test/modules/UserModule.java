package com..test.modules;

import com..test.clients.TAFSCLIENT;
import com..test.context.ScenarioContext;
import com..test.models.HttpRequest;
import com..test.models.HttpResponse;
import com..test.utilities.JSONHelper;
import net.thucydides.core.annotations.Shared;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.junit.Assert;

import java.io.IOException;

public class UserModule extends TAFSCLIENT {


    private final String deleteUsersUrl = propertiesManager.getProperty("productUnderTest.api.delete.users.endpoint");
    private final String createUserUrl = propertiesManager.getProperty("productUnderTest.api.create.user.endpoint");

    public void deleteCreatedUser(String userName) throws IOException {
        String deleteUsersRequestBody = JSONHelper.messageAsSimpleString(System.getProperty("user.dir") + "/src/main/resources/data/API/rest/deleteUsers.json");
        deleteUsersRequestBody = deleteUsersRequestBody.replace("{{USER_NAME}}", userName);
        HttpRequest deleteUserReq = buildRequest(deleteUsersUrl, deleteUsersRequestBody, defaultHeaders(scenarioContext.getATSNTssToken()), getQueryParamsDeleteUser(userName));
        HttpResponse response = httpClient.get(deleteUserReq);
    }

    public  void createAUser(String key) throws IOException, ParseException {
        JSONObject dataDrive = scenarioContext.getDataDriveSectionAsJSON(key);
        HttpRequest createUserReq = buildRequest(createUserUrl,dataDrive.toString(), defaultHeaders(scenarioContext.getATSNTssToken()), null);
        HttpResponse response = httpClient.post(createUserReq);
        Assert.assertEquals(response.getResponseBody(), 200, response.getStatusCode());
    }

}
