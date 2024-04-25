package com..cucumber.uisteps.application;

import com..cucumber.uisteps.BaseUISteps;
import com..test.pages.application.AlertReviewPage;
import com..test.pages.application.ApplicationRecordPage;
import com..test.pages.BasePage;
import com..test.pages.application.ApplicationReviewBatchLoadsPage;
import com..test.pages.application.MatchReviewPage;
import com..test.pages.watchlist.WatchlistReviewBatchLoadsPage;
import com..test.utilities.FilterPopup;
import cucumber.api.DataTable;
import cucumber.api.PendingException;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class AlertReviewSteps extends BaseUISteps {

    AlertReviewPage alertReviewPage;
    ApplicationReviewBatchLoadsPage applicationReviewBatchLoadsPage;
    WatchlistReviewBatchLoadsPage watchlistReviewBatchLoadsPage;
    BasePage basePage;
    MatchReviewPage matchReviewPage;

    @And("^I see the application is on \"([^\"]*)\" tab$")
    public void iSeeTheApplicationIsOnTab(String alert) throws IOException, ParseException {
        alertReviewPage.clickTab(alert);
        JSONObject dataDrive = scenarioContext.getDataDriveSectionAsJSON("Application");
        alertReviewPage.applicationExists(dataDrive.get("Application Number").toString());
    }

    @And("^I enable date range and apply filter$")
    public void iEnableDateRangeAndApplyFilter() throws IOException, ParseException {
        alertReviewPage.clickTab("Clean");
        JSONObject dataDrive = scenarioContext.getDataDriveSectionAsJSON("ReviewDetails");
        alertReviewPage.enterFilterDetails(dataDrive.get("StartDate").toString(),dataDrive.get("EndDate").toString());
    }

    @Then("^I verify the Table content$")
    public void iVerifyTheTableContent() throws IOException, ParseException {
        basePage.verifyTable();

    }

    @And("^I select filter$")
    public void iSelectFilter() {
        alertReviewPage.clickTab("Clean");
        alertReviewPage.clickFilterButton();

    }

    @And("^I add following In filter$")
    public void iAddFollowingInFilter(DataTable dataTable ) {

       List<FilterPopup> FilterPopup = dataTable.asList(FilterPopup.class);
        for(FilterPopup  filterPopup: FilterPopup){
            String field = filterPopup.getField();
            String operator = filterPopup.getOperator();
            String value = filterPopup.getValue();
            WebElement selectField = find("//div[contains(text(),' "+field+" ')]//following::div[2]//button");
            waitFor(selectField).waitUntilClickable();
            clickOn(selectField);
            WebElement operatorField= find("//div[contains(text(),' "+field+" ')]//following::div[3]//span[contains(text(),'"+operator+"')]") ;
            clickOn(operatorField);
            alertReviewPage.clickFieldValue();
            WebElement selectFieldValue = find("//ng-select[@bindlabel= 'Description']//following::span[contains(text(),'"+value+"')]");
            clickOn(selectFieldValue);

        }
        alertReviewPage.clickApply();

    }

    @And("^I see these applications are on \"([^\"]*)\" tab$")
    public void iSeeTheseApplicationAreOnTab(String alert) throws IOException, ParseException {
        alertReviewPage.clickTab(alert);
        WebElement applicationRow = find("//table/tbody");
        if(!applicationRow.isDisplayed())  { waitFor(applicationRow); }

        JSONObject dataDrive = scenarioContext.getDataDriveSectionAsJSON("verify_alertReview_" + alert );

        if(alert.equals("Errors")) {
            for (int i = 1; i <= dataDrive.size(); i++)
              alertReviewPage.errorExists(dataDrive.get("Application Number" + i).toString());
        } else {
            for (int i = 1; i <= dataDrive.size(); i++)
              alertReviewPage.applicationExists(dataDrive.get("Application Number" + i).toString());

        }
    }

    @And("^I add \"([^\"]*)\" to \"([^\"]*)\" filter input$")
    public void iAddValueToFilterInput(String attributeValue, String attributeName) {
        WebElement inputField= find("//div[contains(text(),' "+ attributeName+" ')]//following::input");
        typeInto(inputField, attributeValue);
        alertReviewPage.clickApply();
    }

    @And("^I select application with application number$")
    public void iSelectApplicationWithApplicationNumber() throws IOException, ParseException {
        JSONObject dataDrive = scenarioContext.getDataDriveSectionAsJSON("Application");
        WebElement inputField= find("//span[contains(text(),'" + dataDrive.get("Application Number").toString() + "')]");
        clickOn(inputField);
    }


    @When("^I search for date ranges from \"([^\"]*)\" to \"([^\"]*)\" and refresh$")
    public void iSearchForDateRangesFromToAndRefresh(String fromDate, String toDate) throws IOException {
        if(basePage.getCurrentPageTitle().contains("Application"))
            applicationReviewBatchLoadsPage.enterDatesAndRefresh(fromDate, toDate);
        else
            watchlistReviewBatchLoadsPage.enterDatesAndRefresh(fromDate, toDate);
    }

    @And("^I verify the expected counts for HFP is \"([^\"]*)\", Suspect is \"([^\"]*)\", Clean is \"([^\"]*)\"$")
    public void iVerifyTheExpectedCountsForHFPIsSuspectIsCleanIs(String hfp, String suspect, String clean) {
        String hfpActual = applicationReviewBatchLoadsPage.getValuesFromFirstRow("HFP");
        Assert.assertEquals("HFP value is not as expected", hfp, hfpActual);
        String suspectActual = applicationReviewBatchLoadsPage.getValuesFromFirstRow("SUSPECT");
        Assert.assertEquals("SUSPECT value is not as expected", suspect, suspectActual);
        String cleanActual = applicationReviewBatchLoadsPage.getValuesFromFirstRow("CLEAN");
        Assert.assertEquals("CLEAN value is not as expected", clean, cleanActual);
    }

    @And("^I double-click the above Batch verified record$")
    public void iDoubleClickTheAboveBatchVerifiedRecord() {
        if(basePage.getCurrentPageTitle().contains("Application"))
            applicationReviewBatchLoadsPage.doubleClickFirstRow();
        else
            watchlistReviewBatchLoadsPage.doubleClickFirstRow();
    }

    @And("^I verify the HPF \"([^\"]*)\" /Suspect \"([^\"]*)\" /Clean \"([^\"]*)\" /Error \"([^\"]*)\" counts on Alert Review Page$")
    public void iVerifyTheHPFSuspectCleanErrorCountsOnAlertReviewPage(String hfpCount, String suspectCount, String cleanCount, String errorCount) throws Throwable {
        Assert.assertEquals("HFP Count is not as expected.",hfpCount, alertReviewPage.getHfpCountOnTab());
        Assert.assertEquals("Suspect Count is not as expected.", suspectCount, alertReviewPage.getSuspectCountOnTab());
        Assert.assertEquals("Clean Count is not as expected.", cleanCount, alertReviewPage.getCleanCountOnTab());
        Assert.assertEquals("Error Count is not as expected.", errorCount, alertReviewPage.getErrorCountOnTab());
    }

    @Then("^I select a record from \"([^\"]*)\" tab to go to Match Review Page$")
    public void iSelectARecordFromTabToGoToMatchReviewPage(String alertTab) {
        alertReviewPage.clickTab(alertTab);
        alertReviewPage.doubleClickFirstRecordOnSelectTab();
        Assert.assertTrue("Application not loaded on Match Review Page",matchReviewPage.checkApplicationRecordHeader());
    }

    @And("^I verify the expected counts on Watchlist Review Batch$")
    public void iVerifyTheExpectedCountsOnWatchlistReviewBatch(DataTable table) throws IOException {
        List<Map<String, String>> rows = table.asMaps(String.class, String.class);
        for (Map<String, String> row : rows) {
            String actualValue = watchlistReviewBatchLoadsPage.getValuesFromFirstRow(row.get("Column"));
            Assert.assertEquals("Batch record values not as expected",row.get("Value"), actualValue);
        }
    }

    @And("^I select one of the records$")
    public void iSelectOneOfTheRecords() throws IOException, ParseException {
        String toSelect = scenarioContext.getDataDriveSectionAsString("applicationToSelect");
        watchlistReviewBatchLoadsPage.selectARecord(toSelect);
    }

    @And("^I select the above Batch verified record$")
    public void iSelectTheAboveBatchVerifiedRecord() {
        watchlistReviewBatchLoadsPage.selectABatchRecord();
    }

    @And("^I review the errors from the recent Batch$")
    public void iReviewTheErrorsFromTheRecentBatch() {
        watchlistReviewBatchLoadsPage.clickReviewErrorsButton();
    }

    @And("^I Verify the Watchlist review errors Page$")
    public void iVerifyTheWatchlistReviewErrorsPage() throws IOException {
        List<List<String>> watchlistErrorsFromExcel = watchlistReviewBatchLoadsPage.getWatchlistErrorsFromExcel();
        List<List<String>> watchlistErrorsFromUI = watchlistReviewBatchLoadsPage.getWatchlistErrorsFromUI();
        Assert.assertEquals("Watchlist Errors from Excel does not match the UI",watchlistErrorsFromExcel,watchlistErrorsFromUI);
        watchlistReviewBatchLoadsPage.getDateTimeFromExcel();
    }

    @And("^I will delete the watchlist errors$")
    public void iWillDeleteTheWatchlistErrors() {
        watchlistReviewBatchLoadsPage.clickDeleteAllErrorsButton();
        watchlistReviewBatchLoadsPage.clickYesButtonOnConfirmDelete();
    }
}
