package com..cucumber.apisteps.rest;
import com..test.utilities.JSONHelper;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.junit.Assert;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class LoginRESTSteps extends BaseRESTSteps {

    private final String signInURL = baseURI + "/oauth2/token";

    public Response loginRequest(JSONObject dataDrive) {
        return RestAssured.given()
                .contentType("text/plain")
                .body(dataDrive.get("body"))
                .post(signInURL);
    }

    @Given("^I have logged in with a standard user$")
    public void iHaveLoggedInWithAStandardUser() throws IOException, ParseException {
        JSONObject data = JSONHelper.messageAsSimpleJson(dataDir + "ValidAuthentication.json");
        response = loginRequest(data);
        Assert.assertEquals("Login attempt did not return status code 200 as expected", 200, response.getStatusCode());
        setAuthHeader(response);
    }

    @Given("^I submit a valid login request$")
    public void iSubmitAValidLoginRequest() throws IOException, ParseException {
        JSONObject data = scenarioContext.getDataDriveAsJSON();
        response = loginRequest(data);
        Assert.assertEquals("Login attempt did not return status code 200 as expected", 200, response.getStatusCode());
        setAuthHeader(response);
    }


}
