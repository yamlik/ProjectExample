package com..cucumber.uisteps.application;

import com..cucumber.uisteps.BaseUISteps;
import com..test.pages.application.ApplicationRecordPage;
import com..test.pages.application.AutoCheckResultsPage;
import com..test.pages.application.CrossCheckFieldsPage;
import com..test.pages.watchlist.WatchlistReviewBatchLoadsPage;
import cucumber.api.PendingException;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class AutoCheckSteps extends BaseUISteps {

    CrossCheckFieldsPage crossCheckFieldsPage;
    ApplicationRecordPage applicationRecordPage;
    AutoCheckResultsPage autoCheckResultsPage;
    WatchlistReviewBatchLoadsPage watchlistReviewBatchLoadsPage;

    @And("^I add Cross Check Fields$")
    public void iAddCrossCheckFields() throws IOException, ParseException {
        JSONObject dataDrive = scenarioContext.getDataDriveSectionAsJSON("Cross Check Fields");
        Set<Map.Entry> entries = dataDrive.entrySet();
        for (Map.Entry entry : entries) {
            if (entry.getKey().toString().contains("Field Name"))
                crossCheckFieldsPage.enterFieldName(entry.getValue().toString());
            else
                crossCheckFieldsPage.enterCrossFieldName(entry.getValue().toString());
        }
        crossCheckFieldsPage.clickSave();
    }

    @When("^I Auto Check the application from \"([^\"]*)\" page (and verify|without verifying) results$")
    public void iAutoCheckTheApplicationFromPage(String page, String isVerify) throws IOException, ParseException {
        if (page.equals("Application Record"))
            applicationRecordPage.clickAutoCheckButton();
        if (page.equals("Watchlist Review Batch"))
            watchlistReviewBatchLoadsPage.clickAutoCheckButton();

        if(isVerify.equals("and verify")) {
            JSONObject dataDrive = scenarioContext.getDataDriveSectionAsJSON("verifyAutoCheckHeader");
            autoCheckResultsPage.verifyAutoCheckDetailsHeader(dataDrive.get("Main Application Number").toString());
            dataDrive = scenarioContext.getDataDriveSectionAsJSON("verifyAutoCheckGridResults");
            autoCheckResultsPage.verifyAutoCheckGridResults(dataDrive);
        }
    }

    @And("^I verify by selecting \"([^\"]*)\" record$")
    public void iVerifyBySelectingRecord(String data) {
     autoCheckResultsPage.verifyAppRecord(data);
    }

    @Then("^I navigate through the pages$")
    public void iNavigateThroughThePages() throws InterruptedException {
        autoCheckResultsPage.clickRightPage();
        autoCheckResultsPage.clickLeftPage();
    }

    @And("^I delete the cross check fields$")
    public void iDeleteTheCrossCheckFields() {
        crossCheckFieldsPage.clickSelectAllCheckbox();
        crossCheckFieldsPage.clickDeleteButton();
    }
}
