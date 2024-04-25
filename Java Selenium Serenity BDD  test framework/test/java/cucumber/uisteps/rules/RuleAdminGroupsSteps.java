package com..cucumber.uisteps.rules;

import com..cucumber.uisteps.BaseUISteps;
import com..test.modules.rules.RuleAdminGroups;
import com..test.pages.rules.RuleAdminGroupPage;
import com..test.pages.rules.RuleGroupsPage;
import com..test.pages.rules.RulesConfigurationPage;
import cucumber.api.DataTable;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class RuleAdminGroupsSteps extends BaseUISteps {

    private RuleAdminGroups ruleAdminGroups = new RuleAdminGroups();
    private RuleAdminGroupPage ruleAdminGroupPage = new RuleAdminGroupPage();
    private RulesConfigurationPage rulesConfigurationPage = new RulesConfigurationPage();
    private RuleGroupsPage ruleGroupsPage = new RuleGroupsPage();

    @And("^I create a new Rule Admin Group$")
    public void iCreateANewRuleAdminGroup() throws IOException, ParseException {
        ruleAdminGroups.createRuleAdminGroup();
    }

    @And("^I delete the rule admin group$")
    public void iDeleteTheRuleAdminGroup() throws IOException, ParseException {
        ruleAdminGroups.deleteRuleAdminGroup("deleteRuleAdminGroup1");
    }

    @And("^I add the following Rule Admin Groups$")
    public void iAddTheFollowingRuleAdminGroups(DataTable dataTable) {
        List<Map<String, String>> rows = dataTable.asMaps(String.class, String.class);
        for (Map<String, String> columns : rows) {
            ruleAdminGroupPage.clickAddButton();
            ruleAdminGroupPage.enterRuleAdminGroup(columns.get("Rule Admin Group"));
            ruleAdminGroupPage.enterDescription(columns.get("Description"));
            ruleAdminGroupPage.clickAddButtonInPopup();
        }
    }


    @And("^I verify that the user can view Rule \"([^\"]*)\" for Rule Admin Group \"([^\"]*)\"$")
    public void iVerifyThatTheUserCanViewRuleForRuleAdminGroup(String rule, String ruleAdminGroup)  {
        rulesConfigurationPage.enterRuleAdminGroup(ruleAdminGroup);
        rulesConfigurationPage.clickFilterButton();
        rulesConfigurationPage.enterRuleCodeInFilterPopup(rule);
        rulesConfigurationPage.clickApplyButtonOnFilterPopup();
        rulesConfigurationPage.verifyRuleCodeExists(rule);
    }

    @And("^I delete the \"([^\"]*)\" rule admin group$")
    public void iDeleteTheRuleAdminGroup(int count) throws Throwable {
        for (int i=1; i<=count; i++)
            ruleAdminGroups.deleteRuleAdminGroup("deleteRuleAdminGroup"+i);
    }

    @And("^I add the below Rule Group$")
    public void iAddTheBelowRuleGroup(DataTable dataTable) {
        List<Map<String, String>> rows = dataTable.asMaps(String.class, String.class);

        for (Map<String, String> columns : rows) {
            ruleGroupsPage.clickAdd();
            ruleGroupsPage.enterRuleGroupCode(columns.get("Code"));
            ruleGroupsPage.enterDescription(columns.get("Description"));
            ruleGroupsPage.clickAddInPopup();
        }

    }

    @Then("^I delete all the records$")
    public void iDeleteAllTheRecords() {
        ruleAdminGroupPage.deleteAllRecordsOnGrid();
    }
}
