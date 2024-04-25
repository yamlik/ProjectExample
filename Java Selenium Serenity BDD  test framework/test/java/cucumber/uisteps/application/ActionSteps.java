package com..cucumber.uisteps.application;

import com..cucumber.uisteps.BaseUISteps;
import com..test.context.constants.ApplicationConstants;
import com..test.pages.application.*;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import net.thucydides.core.annotations.Shared;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.junit.Assert;

import java.io.IOException;
import java.sql.SQLException;

public class ActionSteps extends BaseUISteps {

    @Shared
    ActionPage actionPage;
    AlertReviewPage alertReviewPage;
    AutoCheckResultsPage autoCheckResultsPage;
    ApplicationRecordPage applicationRecordPage;
    MatchReviewPage matchReviewPage;

    @Then("^I select the action button from page \"([^\"]*)\"$")
    public void clickActionButtonFromPage(String page) throws IOException, ParseException {
        JSONObject dataDrive;
        switch (page) {
            case "Alert Review":
                alertReviewPage.clickActionButton();
                break;
            case "Auto Check Results":
                dataDrive = scenarioContext.getDataDriveSectionAsJSON("applicationToAutoCheck");
                autoCheckResultsPage.selectRowToAction(dataDrive.get("Application Number").toString());
                autoCheckResultsPage.clickActionButton();
                break;
            case "Application Record":
                applicationRecordPage.clickMainReviewButton();
                applicationRecordPage.clickActionButton();
                break;
            default:
                matchReviewPage.clickActionButton();
        }
    }


    @Then("^I Action the (matched application|application) as \"([^\"]*)\" from \"([^\"]*)\" page$")
    public void iActionTheApplicationAsFromPage(String choice, String action, String page) throws IOException, ParseException {
        clickActionButtonFromPage(page);
        JSONObject dataDrive;
        dataDrive = scenarioContext.getDataDriveSectionAsJSON("actionDetails");
        waitABit(2000);
        actionPage.selectAction(action);
        actionPage.verifyActionPageHeader(action);
        actionPage.clickDecisionReasonDropDown(dataDrive.get("Decision Reason").toString());
        actionPage.clickNatureOfFraudDropDown(dataDrive.get("Nature of Fraud").toString());
        actionPage.verifyDecisionReasonHighlighted(dataDrive.get("Decision Reason").toString());
        actionPage.clickConfirmButton();
        if (action.contains(ApplicationConstants.KNOWN_FRAUD_ACTION) ||
                action.contains(ApplicationConstants.SUSPICIOUS_ACTION) ||
                action.contains(ApplicationConstants.UNDER_INVESTIGATION_ACTION))
            autoCheckResultsPage.getPageTitle();
        else if (action.contains(ApplicationConstants.FALSE_POSITIVE_ACTION))
            autoCheckResultsPage.waitForBannerToDisappear();
    }

    @When("^I select \"([^\"]*)\" action from \"([^\"]*)\" page$")
    public void iSelectActionFromPage(String action, String page) throws SQLException {
        switch (page.toUpperCase()) {
            case "APPLICATION RECORD":
                applicationRecordPage.clickReviewButton();
                applicationRecordPage.clickActionButton();
                break;
            case "AUTO CHECK RESULTS":
                autoCheckResultsPage.selectRowToAction(action);
                autoCheckResultsPage.clickActionButton();
                break;
        }
        actionPage.selectAction(action);

    }

    @And("^I (Include|Exclude) this application in the Fraud Savings Report$")
    public void iIncludeThisApplicationInTheFraudSavingsReport(String choice) {
        if (choice.equals("Include"))
            actionPage.includeInFraudSavingsReport("YES");
        else
            actionPage.includeInFraudSavingsReport("NO");
    }

    @And("^I select \"([^\"]*)\" as the Decision Reason$")
    public void iSelectAsTheDecisionReason(String decisionReason) {
        actionPage.clickDecisionReasonDropDown(decisionReason);
    }

    @And("^I select \"([^\"]*)\" as the Nature of Fraud$")
    public void iSelectAsTheNatureOfFraud(String natureOfFraud) {
        actionPage.clickNatureOfFraudDropDown(natureOfFraud);
    }


    @And("^I (Include|Exclude) a Savings Amount \"([^\"]*)\" for this application$")
    public void iIncludeASavingsAmountForThisApplication(String choice, String savings) {
        if (choice.equals("Include"))
            actionPage.includeSavingsAmount("YES", savings);
        else
            actionPage.includeSavingsAmount("NO", savings);
    }

    @And("^I Include \"([^\"]*)\" applications on Action page$")
    public void iIncludeApplicationsOnActionPage(String action) {
        actionPage.includeActionApplications(action);
    }

    @Then("^I Confirm the Action$")
    public void iConfirmTheAction() {
        actionPage.clickConfirmButton();
    }

    @Then("^I Action the matched application as \"([^\"]*)\"$")
    public void iActionTheMatchedApplicationAs(String action) throws Throwable {
        JSONObject dataDrive;
        alertReviewPage.clickActionButton();
        dataDrive = scenarioContext.getDataDriveSectionAsJSON("actionDetails");
        actionPage.selectAction(action);
        actionPage.verifyActionPageHeader(action);
        actionPage.clickDecisionReasonDropDown(dataDrive.get("Decision Reason").toString());
        actionPage.clickNatureOfFraudDropDown(dataDrive.get("Nature of Fraud").toString());
        actionPage.verifyDecisionReasonHighlighted(dataDrive.get("Decision Reason").toString());
        actionPage.clickConfirmButton();
        if (action.contains(ApplicationConstants.KNOWN_FRAUD_ACTION) ||
                action.contains(ApplicationConstants.SUSPICIOUS_ACTION) ||
                action.contains(ApplicationConstants.UNDER_INVESTIGATION_ACTION))
            autoCheckResultsPage.getPageTitle();
        else if (action.contains(ApplicationConstants.FALSE_POSITIVE_ACTION))
            autoCheckResultsPage.waitForBannerToDisappear();
    }

    @Then("^I Action the match application as \"([^\"]*)\" from \"([^\"]*)\" page$")
    public void iActionTheMatchApplicationAsFromPage(String action, String page) throws Throwable {
        JSONObject dataDrive;
        alertReviewPage.clickActionButton();
        dataDrive = scenarioContext.getDataDriveSectionAsJSON("actionDetails");
        actionPage.selectAction(action);
        actionPage.verifyActionPageHeader(action);
        actionPage.clickDecisionReasonDropDown(dataDrive.get("Decision Reason").toString());
        actionPage.clickNatureOfFraudDropDown(dataDrive.get("Nature of Fraud").toString());
        actionPage.verifyDecisionReasonHighlighted(dataDrive.get("Decision Reason").toString());
        actionPage.clickConfirmButton();
        if (action.contains(ApplicationConstants.KNOWN_FRAUD_ACTION) ||
                action.contains(ApplicationConstants.SUSPICIOUS_ACTION) ||
                action.contains(ApplicationConstants.UNDER_INVESTIGATION_ACTION))
            autoCheckResultsPage.getPageTitle();
        else if (action.contains(ApplicationConstants.FALSE_POSITIVE_ACTION))
            autoCheckResultsPage.waitForBannerToDisappear();
    }

    @Then("^I Verify no error message appears on Action page$")
    public void iVerifyNoErrorMessageAppearsOnActionPage() {
        boolean redError = matchReviewPage.getRedError();
        if (redError) {
            Assert.fail("Error Message appears");
        }
    }

    @When("^I Confirm the Action for WhiteList$")
    public void iConfirmTheActionForWhiteList() {
        actionPage.clickConfirmButtonForWhiteList();
    }

    @And("^I Action the application as \"([^\"]*)\" from \"([^\"]*)\" page for Whitelist$")
    public void iActionTheApplicationAsFromPageForWhitelist(String action, String page) throws IOException, ParseException {
        clickActionButtonFromPage(page);
        JSONObject dataDrive;
        dataDrive = scenarioContext.getDataDriveSectionAsJSON("actionDetails");
        actionPage.selectAction(action);
        actionPage.verifyActionPageHeader(action);
        actionPage.clickDecisionReasonDropDown(dataDrive.get("Decision Reason").toString());
        actionPage.clickNatureOfFraudDropDown(dataDrive.get("Nature of Fraud").toString());
        actionPage.verifyDecisionReasonHighlighted(dataDrive.get("Decision Reason").toString());
        actionPage.clickConfirmButtonForWhiteList();

    }

    @Then("^I verify expiry date of application$")
    public void iVerifyExpiryDateOfApplication() {
        actionPage.getExprirydate();

    }

    @And("^I add application as \"([^\"]*)\" to Whitelist and navigated to Autocheck result page$")
    public void iAddApplicationAsToWhitelistAndNavigatedToAutocheckResultPage(String action) throws Throwable {
        actionPage.selectApplicationWhitelistRecord();

    }


}



