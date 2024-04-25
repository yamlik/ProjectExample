package com..cucumber.uisteps.rules;

import com..test.database.DataBaseClient;
import com..test.pages.application.ApplicationRecordPage;
import com..test.pages.rules.RuleReferenceMaintenence;
import cucumber.api.PendingException;
import cucumber.api.java.en.And;
import com..test.pages.rules.RulesConfigurationPage;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import net.thucydides.core.annotations.Shared;
import com..cucumber.uisteps.BaseUISteps;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.junit.Assert;
import org.openqa.selenium.JavascriptExecutor;

import java.io.IOException;
import java.sql.SQLException;
import java.util.*;


public class RulesConfigurationSteps extends BaseUISteps {
    @Shared

    private RulesConfigurationPage rulesConfiguration;
    private RuleReferenceMaintenence ruleReferenceMaintenence;
    private ApplicationRecordPage applicationRecordPage;
    private com..test.utilities.FileUtils FileUtils;

    DataBaseClient dataBaseClient =new DataBaseClient();

   @When("^I add rule details$")
    public void iAddRuleDetails() throws Throwable {
        rulesConfiguration.clickAdd();
        JSONObject requestBody = scenarioContext.getDataDriveSectionAsJSON("Rule Details");
        rulesConfiguration.enterRuleCode(requestBody.get("RuleCode").toString());
        rulesConfiguration.enterRuleDescritption(requestBody.get("ruleDescription").toString());
       JavascriptExecutor jse = (JavascriptExecutor) getDriver();
       jse.executeScript("window.scrollBy(0,250)");
       rulesConfiguration.enterRuleScore(requestBody.get("RuleScore").toString());
        rulesConfiguration.enterAgeOfApplicationStart(requestBody.get("Age of Applications (Days) - Start").toString());
        rulesConfiguration.enterAgeOfApplicationTo(requestBody.get("Age of Applications (Days) - End").toString());
        rulesConfiguration.clickRuleConditions();
       requestBody = scenarioContext.getDataDriveSectionAsJSON("Rule Conditions");
       Set<Map.Entry> entries = requestBody.entrySet();

       for (Map.Entry entry : entries) {

           rulesConfiguration.enterRuleConditions(entry.getKey().toString(), (JSONObject) entry.getValue());
       }
   }

    @And("^save the rule$")
    public void saveTheRule() {

        rulesConfiguration.saveRule();
        rulesConfiguration.yesConfirmationButton();
        waitABit(3000);
        rulesConfiguration.yesButton1();
        waitABit(5000);
    }

    @Then("^I verify error window$")
    public void iVerifyErrorWindow() {

        boolean textValue =rulesConfiguration.result();
        if (!textValue) {
            Assert.fail("Does not contain the fuzzy rule error message");
        }
    }

    @When("^I add rule details having similarity$")
    public void iAddRuleDetailsWithSimilarity() throws Throwable {
        iAddRuleDetails();

        JSONObject requestBodyApplicationValue = scenarioContext.getDataDriveSectionAsJSON("Application Value");
        rulesConfiguration.enterSimilarity((requestBodyApplicationValue.get("Similarity").toString()));
    }


    @Then("^I verify maximum value of similarity$")
    public void iVerifyMaximumValueOfSimilarity() {
      boolean maximumSimilarityValue =  rulesConfiguration.maximumSimilarity();

      if(!maximumSimilarityValue)
      {
          Assert.fail("Value is not maximum similarity value");
      }

    }

    @When("^I add rule details having operator$")
    public void iAddRuleDetailsHavingOperator() throws Throwable{
        iAddRuleDetails();

        JSONObject requestBodyLogicalOperatorValue = scenarioContext.getDataDriveSectionAsJSON("Logical Operator");
        rulesConfiguration.enterLogicalOperator((requestBodyLogicalOperatorValue.get("OperatorValue1").toString()));

    }


    @And("^I add RuleConditions based on ApplicationValue$")
    public void iAddRuleConditionsBasedOnApplicationValue() throws Throwable {
        rulesConfiguration.clickRuleConditions();
        rulesConfiguration.clickAddRuleLink();
        rulesConfiguration.clickApplicationValueLink();
        JSONObject requestBodyApplicationValue = scenarioContext.getDataDriveSectionAsJSON("Application Value");
        rulesConfiguration.enterApplicationCategory(requestBodyApplicationValue.get("Application Category").toString());
        rulesConfiguration.enterField(requestBodyApplicationValue.get("Field").toString());
        rulesConfiguration.enterOperator(requestBodyApplicationValue.get("Operator").toString());
        rulesConfiguration.enterValue(requestBodyApplicationValue.get("Value").toString());
    }

    @And("^I dont save the rule$")
    public void iDontSaveTheRule() {
        rulesConfiguration.saveRule();
        rulesConfiguration.yesConfirmationButton();
        rulesConfiguration.yesButton1();
    }

    @And("^save dataBaseField rule$")
    public void saveDataBaseFieldRule() {
        rulesConfiguration.saveRule();
        rulesConfiguration.yesConfirmationButton();
        waitABit(3000);
        rulesConfiguration.yesButton1();
        rulesConfiguration.yesButton2();
    }


    @And("^I add a Rule \"([^\"]*)\"$")
    public void iAddARule(String ruleCode) throws Throwable {

        rulesConfiguration.clickAdd();
        JSONObject requestBody = scenarioContext.getDataDriveSectionAsJSON("Rule Details");
        rulesConfiguration.enterRuleCode(ruleCode);
        rulesConfiguration.enterRuleScore(requestBody.get("RuleScore").toString());
        rulesConfiguration.enterRuleDescritption(requestBody.get("ruleDescription").toString());
        rulesConfiguration.enterAgeOfApplicationStart(requestBody.get("Age of Applications (Days) - Start").toString());
        rulesConfiguration.enterAgeOfApplicationTo(requestBody.get("Age of Applications (Days) - End").toString());
        rulesConfiguration.clickRuleConditions();
        requestBody = scenarioContext.getDataDriveSectionAsJSON("Rule Conditions");
        Set<Map.Entry> entries = requestBody.entrySet();

        for (Map.Entry entry : entries) {

            rulesConfiguration.enterRuleConditions(entry.getKey().toString(), (JSONObject) entry.getValue());
        }
        rulesConfiguration.saveRule();
        rulesConfiguration.yesConfirmationButton();
        rulesConfiguration.yesButton1();
        rulesConfiguration.yesButton3();

    }

    @And("^I add Comment and Submit$")
    public void iAddCommentAndSubmit() {
        applicationRecordPage.addComment();
    }

    @And("^I Verify the Self Approval aTSNTss$")
    public void iVerifyTheSelfApprovalATSNTss() {
        rulesConfiguration.verifySelfApprovalATSNTss();

    }

    @And("^I Verify the Other Approval aTSNTss$")
    public void iVerifyTheOtherApprovalATSNTss() {
        rulesConfiguration.verifyOtherApprovalATSNTss();

    }

    @And("^I Verify the All Approval aTSNTss$")
    public void iVerifyTheAllApprovalATSNTss() {
        rulesConfiguration.verifyAllApprovalATSNTss();
    }

    @And("^I Approve all the pending Rules$")
    public void iApproveAllThePendingRules() {
        rulesConfiguration.approveAllPendingRules();
    }

    @And("^I Verify the No Approval aTSNTss$")
    public void iVerifyTheNoApprovalATSNTss() {
        rulesConfiguration.verifyNoApprovalATSNTss();
    }

    @Given("^I am running the batchJob$")
    public void iAmRunningTheBatchJob() throws SQLException {
        dataBaseClient.executeJob();
    }


    @When("^I add a new table \"([^\"]*)\"$")
    public void iAddANewTable(String tableDetails) throws IOException, ParseException {
        JSONObject requestBody = scenarioContext.getDataDriveSectionAsJSON(tableDetails);
        //JSONObject requestBody = scenarioContext.
        ruleReferenceMaintenence.clickAdd();
        ruleReferenceMaintenence.addANewTable(requestBody.get("TableId").toString(),requestBody.get("TableName").toString(),requestBody.get("FileType").toString());
        ruleReferenceMaintenence.clickOnChange();

    }

    @And("^I add a filed \"([^\"]*)\" type withActive check as \"([^\"]*)\"$")
    public void iAddAFiledTypeWithActiveCheckAs(String arg0, String arg1) throws Throwable {
       ruleReferenceMaintenence.clickAddField();
       JSONObject requestBody = scenarioContext.getDataDriveSectionAsJSON("Field Detail1");
       ruleReferenceMaintenence.iAddAFiledTypeWithActiveCheckAsGiven(requestBody.get("FieldName").toString(),requestBody.get("ActiveCheckBox").toString(),requestBody.get("ValueType").toString(),requestBody.get("CalSyntax").toString());
       ruleReferenceMaintenence.saveTable();
    }

    @And("^I save the reference Table$")
    public void iSaveTheReferenceTable() {
        ruleReferenceMaintenence.saveTable();
       ruleReferenceMaintenence.saveReferenceTable();
    }

    @And("^I add a filed on the new table with data \"([^\"]*)\"$")
    public void iAddAFiledOnTheNewTableWithData(String data) throws Throwable {
        ruleReferenceMaintenence.clickAddField();
        JSONObject requestBody = scenarioContext.getDataDriveSectionAsJSON(data);
        ruleReferenceMaintenence.iAddAFiledTypeWithActiveCheckAsGiven(requestBody.get("FieldName").toString(),requestBody.get("ActiveCheckBox").toString(),requestBody.get("ValueType").toString(),requestBody.get("CalSyntax").toString());
        //ruleReferenceMaintenence.saveTable();
    }

    @Then("^I verify the Table content of \"([^\"]*)\"$")
    public void iVerifyTheTableContentOf(String data) throws Throwable {
        JSONObject requestBody = scenarioContext.getDataDriveSectionAsJSON(data);
        ruleReferenceMaintenence.verifyTableContent(requestBody.get("CalSyntax").toString().trim());
    }

    @And("^I delete the created table$")
    public void iDeleteTheCreatedTable() {
       ruleReferenceMaintenence.clickOnDelete();

    }

    @And("^I am on the newly created table$")
    public void iAmOnTheNewlyCreatedTable() {
       ruleReferenceMaintenence.clickOnChange();
    }


    @Then("^I compare UI results with downloaded Excel sheet results$")
    public void iCompareUIResultsWithDownloadedExcelSheetResults() throws IOException, InvalidFormatException {

       List<List<String>> UIData = FileUtils.get_Table_Rule_Data();
       List<List<String>> excelData = FileUtils.get_Excel_File_Data();
       HashMap<String,String> rule_code_criteria_data = FileUtils.get_rule_code_criteria();
       HashMap<String,String> UI_rule_code_criteria_data = FileUtils.get_UI_rule_code_criteria();
//       FileUtils.compare_Data(UIData, excelData , rule_code_criteria_data,UI_rule_code_criteria_data);
        FileUtils.compare_Data(UIData, excelData , rule_code_criteria_data,UI_rule_code_criteria_data);

    }
}

