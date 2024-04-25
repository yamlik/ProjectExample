package com..test.modules;

import com..test.clients.TAFSCLIENT;
import com..test.context.ScenarioContext;
import com..test.models.HttpRequest;
import com..test.models.HttpResponse;
import com..test.utilities.JSONHelper;
import net.thucydides.core.annotations.Shared;
import org.junit.Assert;

import java.io.IOException;

public class ProfileModule extends TAFSCLIENT {


    private final String deleteProfilesUrl = propertiesManager.getProperty("productUnderTest.api.delete.profiles.endpoint");

    public void deleteCreatedProfile(String profileName) throws IOException {
        String deleteProfileRequestBody = JSONHelper.messageAsSimpleString(System.getProperty("user.dir") + "/src/main/resources/data/API/rest/deleteProfiles.json");
        deleteProfileRequestBody = deleteProfileRequestBody.replace("{{PROFILE_NAME}}", profileName);
        HttpRequest deleteProfileReq = buildRequest(deleteProfilesUrl, deleteProfileRequestBody, defaultHeaders(scenarioContext.getATSNTssToken()), getQueryParamsDeleteProfile(profileName));
        HttpResponse response = httpClient.get(deleteProfileReq);
    }

}
