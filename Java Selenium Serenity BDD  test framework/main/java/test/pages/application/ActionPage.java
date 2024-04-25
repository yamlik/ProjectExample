package com..test.pages.application;

import com..test.context.constants.ApplicationConstants;
import com..test.database.DataBaseClient;
import com..test.pages.BasePage;
import net.serenitybdd.core.annotations.findby.FindBy;
import net.serenitybdd.core.pages.WebElementFacade;
import net.thucydides.core.annotations.Shared;
import org.joda.time.LocalDate;
import org.junit.Assert;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class ActionPage extends BasePage {

    @Shared
    DataBaseClient dataBaseClient;

    @FindBy(xpath = "//button[contains(@class,'knownFraud-background')]")
    private WebElementFacade knownFraudActionButton;

    @FindBy(xpath = "//button[contains(@class,'falsePositive-background')]")
    private WebElementFacade falsePositiveActionButton;

    @FindBy(xpath = "//button[contains(@class,'underInvestigation-background')]")
    private WebElementFacade underInvestigationActionButton;

    @FindBy(xpath = "//button[contains(@class,'suspicious-background')]")
    private WebElementFacade suspiciousActionButton;

    @FindBy(xpath = "//ng-select[@id='decisionReason']")
    private WebElementFacade decisionReasonDropDown;

    @FindBy(xpath = "//div[contains(@id,'decisionReasonControl')]//div[contains(text(),'Continue')]")
    private WebElementFacade decisionReasonContinueButton;

    @FindBy(xpath = "//*[@id='natureoffraud']")
    private WebElementFacade natureOfFraudDropDownSelectAllApplicants;

    @FindBy(xpath = "//div[contains(@id,'natureOfFraudControl')]//div[contains(text(),'Continue')]")
    private WebElementFacade natureOfFraudContinueButton;

    @FindBy(xpath = "//button//div[contains(text(),'Confirm')]")
    private WebElementFacade confirmActionButton;

    @FindBy(xpath = "//h2//span")
    private WebElementFacade actionNameOnHeading;

    @FindBy(xpath = "//button[contains(text(),'Application Details')]")
    private WebElementFacade expandHeaderButton;

    @FindBy(xpath = "//button[contains(text(),'Hide')]")
    private WebElementFacade collapseHeaderButton;

    @FindBy(xpath = "//div[contains(@class,'highlighted-table-caption')]/div/div[2]")
    private WebElementFacade highlightedDecisionReason;

    @FindBy(xpath = "//div[contains(@class,'modal-dialog-centered')]//div[contains(@class,'modal-content')]")
    private WebElementFacade WhiteListWindow;

    @FindBy(xpath = "//h2[contains(text(),'Would you like to select rule(s)?')]")
    private WebElementFacade selectRulesPopupTitle;

    @FindBy(xpath = "//button[contains(text(),'Yes')]")
    private WebElementFacade selectRulesPopupYesButton;

    @FindBy(xpath = "//button[contains(text(),'No')]")
    private WebElementFacade selectRulesPopupNoButton;

    @FindBy(xpath = "//label[contains(@for,'fraudSaving-opt2')]")
    private WebElementFacade yesButtonIncludeFraudSavings;

    @FindBy(xpath = "//label[contains(@for,'fraudSaving-opt1')]")
    private WebElementFacade noButtonIncludeFraudSavings;

    @FindBy(xpath = "//label[contains(@for,'savingAmount-opt2')]")
    private WebElementFacade yesButtonIncludeSavingsAmount;

    @FindBy(xpath = "//label[contains(@for,'savingAmount-opt1')]")
    private WebElementFacade noButtonIncludeSavingsAmount;

    @FindBy(xpath = "//label[contains(@for,'savingAmount-opt2')]/following-sibling::input")
    private WebElementFacade savingsAmountTextBox;

    @FindBy(xpath = "//label[contains(@for,'action-known-fraud')]")
    private WebElementFacade includeKFApplication;

    @FindBy(xpath = "//label[contains(@for,'action-false-positive')]")
    private WebElementFacade includePositiveApplication;

    @FindBy(xpath = "//label[contains(@for,'action-under-investigation')]")
    private WebElementFacade includeUnderInvestigationApplication;

    @FindBy(xpath = "//label[contains(@for,'action-suspicious')]")
    private WebElementFacade includeSuspiciousApplication;

    @FindBy(xpath = "//ngb-modal-window")
    private WebElementFacade actionPopupModal;

    @FindBy(xpath = "//div[@class ='table-responsive']//tbody//tr")
    private WebElementFacade whitelistRecord;

    @FindBy(xpath = "//button[contains(@class,'alp-bonito-button-dark')]//div[contains(text(),'Yes')]")
    private WebElementFacade whitelistPopupYesButton;


    public void selectAction(String action) {
        clickActionInSelectActionPopup(action);

        if (selectRulesPopupTitle.isVisible())
            clickOn(selectRulesPopupNoButton);
    }

    public void clickActionInSelectActionPopup(String action)
    {
        if(ApplicationConstants.KNOWN_FRAUD_ACTION.contains(action)) {
            ExpectedConditions.elementToBeClickable(knownFraudActionButton);
            knownFraudActionButton.click();
        }
        else if (ApplicationConstants.FALSE_POSITIVE_ACTION.contains(action))
            clickOn(falsePositiveActionButton);
        else if (ApplicationConstants.UNDER_INVESTIGATION_ACTION.contains(action))
            clickOn(underInvestigationActionButton);
        else if (ApplicationConstants.SUSPICIOUS_ACTION.contains(action))
            clickOn(suspiciousActionButton);
    }

    public void verifyShowSelectedRulePrompt() throws SQLException {
        dataBaseClient.executeSelect("select Parameter_Value1 from productUnderTest_parameters where Parameter_Name='Show Selected Rule Prompt'");
        List<Map<String, String>> results =  dataBaseClient.getResultSetAsMapList();
        if(results.toString().contains("=Y"))
        {
            try
            {
                waitFor(selectRulesPopupTitle);
                clickOn(selectRulesPopupNoButton);
            }
            catch (TimeoutException ex)
            {
                Assert.fail("Select Rules popup does not appear.");
            }

        }
    }

    public void clickDecisionReasonDropDown(String decisionReason)
    {
        waitFor(ExpectedConditions.elementToBeClickable(decisionReasonDropDown));
     waitFor(decisionReasonDropDown);
       clickOn(decisionReasonDropDown);
    waitFor(decisionReasonDropDown);
        getDriver().findElement(By.xpath("//div[contains(text(),'"+decisionReason+"')]")).click();
        clickOn(decisionReasonContinueButton);
    }

    public void clickNatureOfFraudDropDown(String natureOfFraud) {
        waitFor(natureOfFraudDropDownSelectAllApplicants);
        clickOn(natureOfFraudDropDownSelectAllApplicants);
        getDriver().findElement(By.xpath("//div[contains(text(),'"+natureOfFraud+"')]")).click();
        clickOn(natureOfFraudContinueButton);
    }

    public void clickConfirmButton() {
        waitFor(confirmActionButton);
        clickOn(confirmActionButton);
        waitFor(actionPopupModal).waitUntilNotVisible();
    }

    public void verifyActionPageHeader(String action) {
        waitFor(expandHeaderButton);
        clickOn(expandHeaderButton);
        waitFor(actionNameOnHeading);
        Assert.assertEquals("Action Taken mismatch.", action, actionNameOnHeading.getText());
        clickOn(collapseHeaderButton);
    }

    public void verifyDecisionReasonHighlighted(String decisionReason) {
        Assert.assertEquals("Decision Reason mismatch.", highlightedDecisionReason.getText(), decisionReason);
    }

    public void includeInFraudSavingsReport(String choice) {
        if (choice.equals("YES"))
            clickOn(yesButtonIncludeFraudSavings);
        if (choice.equals("NO"))
            clickOn(noButtonIncludeFraudSavings);
    }

    public void includeSavingsAmount(String choice, String savings) {
        if (choice.equals("YES")) {
            clickOn(yesButtonIncludeSavingsAmount);
            typeInto(savingsAmountTextBox,savings);
        }
        if (choice.equals("NO"))
            clickOn(noButtonIncludeSavingsAmount);
    }

    public void includeActionApplications(String action) {
        switch (action.toUpperCase()) {
            case "KNOWN FRAUD":
                clickOn(includeKFApplication);
                break;
            case "FALSE POSITIVE":
                clickOn(includePositiveApplication);
                break;
            case "UNDER INVESTIGATION":
                clickOn(includeUnderInvestigationApplication);
                break;
            case "SUSPICIOUS":
                clickOn(includeSuspiciousApplication);
                break;
        }
    }

    public void clickConfirmButtonForWhiteList() {
        waitFor(confirmActionButton);
        clickOn(confirmActionButton);
        waitFor(WhiteListWindow);;

    }

    public void getExprirydate() {
        String expiryDate =getDriver().findElement(By.xpath("//app-date-input[@formcontrolname='whiteListDateValue']//input")).getAttribute("value");
        LocalDate futureDate = LocalDate.now().plusMonths(6);
        String finalFutureDate = futureDate.toString("dd/MM/yyyy");
        Assert.assertEquals("expiry date does not match.", finalFutureDate, expiryDate);

    }

    public void selectApplicationWhitelistRecord() {
        clickOn(whitelistRecord);
        clickOn(whitelistPopupYesButton);

    }


}
