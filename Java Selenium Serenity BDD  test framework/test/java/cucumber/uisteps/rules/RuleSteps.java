package com..cucumber.uisteps.rules;

import com..cucumber.uisteps.BaseUISteps;
import com..test.modules.ResultTableMaintenanceModule;
import com..test.modules.RuleModule;
import com..test.modules.rules.RuleAdminGroups;
import com..test.pages.commonPages.Navigation;
import com..test.pages.rules.RuleSetsPage;
import com..test.pages.rules.RulesConfigurationPage;
import com..test.utilities.FileUtils;
import cucumber.api.PendingException;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import net.thucydides.core.annotations.Steps;
import net.thucydides.core.pages.PageObject;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.junit.Assert;
import org.openqa.selenium.By;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;


public class RuleSteps extends BaseUISteps {
    private Navigation navigation;
    private RuleSetsPage rules;
    private PageObject page;
    private RulesConfigurationPage rulesConfigurationPage;
    private com..test.utilities.FileUtils FileUtils;

    @Steps
    private RuleModule ruleModule;


    @Then("^I click on Add$")
    public void iClickOnAdd() {
    }

    @And("^I select add button to add a new Rule Set \"([^\"]*)\"$")
    public void iSelectAddButtonToAddANewRuleSet(String ruleSetName) throws Throwable {
        rules.clickAdd();
        rules.EnterRuleSetname(ruleSetName);
        rules.selectcriteria();
        rules.addOrderId();
    }

    @And("^I search Rule \"([^\"]*)\" in the filter and assign the rule to the Rule Set$")
    public void iSearchRuleInTheFilterAndAssignTheRuleToTheRuleSet(String ruleCode) throws Throwable {
        rules.clickFilter();
        rules.EnterRulename(ruleCode);
        rules.clickApplyRule();
        rules.selectRule();
        rules.clickRightNavigation();
        rules.saveRule();
    }

    @When("^I add new Rule Set that includes Rule \"([^\"]*)\"$")
    public void iAddNewRuleSetThatIncludesRule(String ruleCode) throws Throwable {
        rules.clickAdd();
        rules.EnterRuleSetname(ruleCode);
        rules.selectcriteria();
        rules.addOrderId();
        rules.clickFilter();
        rules.EnterRulename(ruleCode);
        rules.clickApplyRule();
        rules.selectRule();
        rules.clickRightNavigation();
        rules.saveRule();
    }


    @When("^I add new Rule Set that includes Rules \"([^\"]*)\"$")
    public void iAddNewRuleSetThatIncludesRules(String ruleCode) throws Throwable {
        String[] categories = ruleCode.split(",");
        for (String item : categories) {
            rules.clickAdd();
            rules.EnterRuleSetname(item);
            rules.selectcriteria();
            rules.addOrderId();
            rules.clickFilter();
            rules.EnterRulename(item);
            rules.clickApplyRule();
            rules.selectRule();
            rules.clickRightNavigation();
            rules.saveRule();

        }
    }

    @Then("^I verify all content for rules \"([^\"]*)\" on RuleTriggered page$")
    public void iVerifyAllContentForRulesOnRuleTriggeredPage(String ruleCode) {
        rules.clickOnTriggeredLink();
        rules.verifyRuleContent(ruleCode);
    }

    @And("^I Filter the rule \"([^\"]*)\"$")
    public void iFilterTheRule(String rule) throws Throwable {
        rules.clickFilter();
        rules.EnterRulename(rule);
        rules.clickApplyRule();
    }

    @And("^I will verify  Rule Performance Test$")
    public void iWillVerifyRulePerformanceTest() {
        rules.clickRulePerformanceTest();
    }

    @And("^I verify the rule  \"([^\"]*)\" on Rule Performance page$")
    public void iVerifyTheRuleOnRulePerformancePage(String ruleCode) throws Throwable {
        rules.verifyRulePerformanceTest(ruleCode);
    }

    @And("^I will verify   Rule Performance Result - Execute a rule$")
    public void iWillVerifyRulePerformanceResultExecuteARule() {
        rules.clickRuleExecuteTest();
    }

    @When("^I add new Rule Sets that includes Rules \"([^\"]*)\"$")
    public void iAddNewRuleSetsThatIncludesRules(String ruleCode) throws Throwable {
        {
            String[] categories = ruleCode.split(",");
            String RuleName = categories[0].trim();
            rules.clickAdd();
            rules.EnterRuleSetname(RuleName);
            rules.selectcriteria();
            rules.addOrderId();
            for (String item : categories) {
//           rules.clickFilter();
//              rules.EnterRulename(item);
//               rules.clickApplyRule();
//               rules.selectRule();
                rules.selectRules(item);
                rules.clickRightNavigation();
            }
            rules.saveRule();
            waitFor(3000);
        }
    }

    @When("^I add new Rule Sets that includes Rules \"([^\"]*)\" with \"([^\"]*)\" criteria$")
    public void iAddNewRuleSetsThatIncludesRulesAndSecondCriteria(String ruleCode, String criteria) throws Throwable {
        {
            String[] categories = ruleCode.split(",");
            String RuleName = categories[0].trim();
            rules.clickAdd();
            rules.EnterRuleSetname(RuleName);
            rules.selectSecondCriteria(criteria);
            rules.addOrderId();
            for (String item : categories) {
                rules.selectRules(item);
                rules.clickRightNavigation();
            }
            rules.saveRule();
        }
    }

    @Given("^I create a rule using an API$")
    public void iCreateARule() throws IOException, ParseException {
        ruleModule.createARule("createARule1");
    }

    @And("^I create \"([^\"]*)\" rule using an API$")
    public void iCreateRuleUsingAnAPI(int count) throws IOException, ParseException {
        for (int i=1; i<=count; i++)
            ruleModule.createARule("createARule"+i);
    }

    @And("^I delete the rule using an API$")
    public void iDeleteTheRuleUsingAnAPI() throws IOException, ParseException {
        ruleModule.deleteARule();
    }

    @And("^I search if the rule exists on Rules Configuration page$")
    public void iSearchIfTheRuleExistsOnRulesConfigurationPage() throws IOException, ParseException {
        rulesConfigurationPage.clickFilterButton();
        JSONObject dataDrive = scenarioContext.getDataDriveSectionAsJSON("searchForRule");
        rulesConfigurationPage.enterFilterPopupValues(dataDrive.entrySet());
        rulesConfigurationPage.verifyRuleCodeExists(dataDrive.get("Rule").toString());
    }

    @And("^i will download Rule Performance Results in Excel sheet$")
    public void iWillDownloadRulePerformanceResultsInExcelSheet() throws InterruptedException, IOException {
        rulesConfigurationPage.download_Excel();
    }

    @And("^I will Print Results On Excel sheet$")
    public void iWillPrintResultsInExcelSheet() throws InterruptedException, IOException {
        rulesConfigurationPage.download_Excel();
    }


    @And("^I added labels in action settings$")
    public void iAddedLabelsInActionSettings() {
     rulesConfigurationPage.get_label_Name();

    }

    @And("^I add new Rule Sets that includes Rules \"([^\"]*)\" to Organisation Rule Set Definition$")
    public void iAddNewRuleSetsThatIncludesRulesToOrganisationRuleSetDefinition(String ruleCode) throws Throwable {
        String[] categories = ruleCode.split(",");
        String RuleName = categories[0].trim();
        for (String item : categories) {
            rules.selectRules(item);
            rules.clickRightNavigation();
        }
        clickOn(getDriver().findElement(By.xpath("//button[contains(text(),'Save')]")));

        waitFor(3000);
    }


    @And("^I select the Rule Admin Group \"([^\"]*)\" on \"([^\"]*)\" page$" )
    public void iSelectTheRuleAdminGroup(String ruleAdminGroup, String page) {
        if (page.equals("Rule Sets"))
            rules.clickAdd();

        rules.enterRuleAdminGroup(ruleAdminGroup);
    }

    @And("^I verify the available rules listed$")
    public void iVerifyTheAvailableRulesListed() throws IOException, ParseException {
        JSONObject dataDrive = scenarioContext.getDataDriveSectionAsJSON("verifyAvailableRulesOnRuleSets");
        for (int index=1 ; dataDrive.size()>=index ; index++) {
            System.out.println(dataDrive.get("availableRule"+index).toString());
            boolean isAvailableRulesExist=rules.verifyAvailableRules(dataDrive.get("availableRule"+index).toString());
            Assert.assertTrue(dataDrive.get("availableRule"+index)+" Rule is not listed",isAvailableRulesExist);
        }
    }

    @And("^I add new Rule Set that includes Rule \"([^\"]*)\" with Rule Admin Group \"([^\"]*)\"$")
    public void iAddNewRuleSetThatIncludesRuleWithRuleAdminGroup(String ruleCode, String ruleAdminGroup) throws Throwable {
        rules.clickAdd();
        rules.EnterRuleSetname(ruleCode);
        rules.selectcriteria();
        rules.selectRuleAdminGroup(ruleAdminGroup);
        rules.addOrderId();
        rules.clickFilter();
        rules.EnterRulename(ruleCode);
        rules.clickApplyRule();
        rules.selectRule();
        rules.clickRightNavigation();
        rules.saveRule();
    }

    @And("^I create \"([^\"]*)\" rule \"([^\"]*)\"$")
    public void iCreateRule(String code, String description) throws IOException, ParseException {
        ruleModule.createARule(code);
    }


    @And("^I create \"([^\"]*)\" rule with Result Table using an API having DatabaseValue$")
    public void iCreateRuleWithResultTableUsingAnAPIHavingDatabaseValue(int count) throws IOException, ParseException {
        for (int i=1; i<=count; i++)
            ruleModule.createARuleWithResultTableHavingDatabaseFieldValue("createARule"+i);
    }


    @And("^I create \"([^\"]*)\" rule with Result Table using an API having applicationValue$")
    public void iCreateRuleWithResultTableUsingAnAPIHavingApplicationValue(int count) throws IOException, ParseException {
        for (int i=1; i<=count; i++)
            ruleModule.createARuleWithResultTableHavingApplicationFieldValue("createARule"+i);

    }
}



