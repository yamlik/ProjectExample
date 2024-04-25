package com..cucumber.uisteps.commonSteps;

import com..cucumber.uisteps.BaseUISteps;
import com..test.pages.application.ApplicationRecordPage;
import com..test.pages.commonPages.RecentPage;
import cucumber.api.java.en.And;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import java.io.IOException;

public class RecentSteps extends BaseUISteps {

    RecentPage recentPage;
    ApplicationRecordPage applicationRecordPage;

    @And("^I Fraud Check the Application using Recent Applications$")
    public void iFraudCheckTheApplicationUsingRecentApplications() throws IOException, ParseException, InterruptedException {
        recentPage.clickRecentLink();
        recentPage.clickRecentApplicationsTab();
        JSONObject dataDrive = scenarioContext.getDataDriveSectionAsJSON("Application");
        recentPage.doubleClickApplicationRecord(dataDrive.get("Application Number").toString());
        applicationRecordPage.clickOnFraudCheckOnReviewDropdown();
    }



}
