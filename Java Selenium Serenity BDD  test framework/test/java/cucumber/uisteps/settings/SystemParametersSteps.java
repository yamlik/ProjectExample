package com..cucumber.uisteps.settings;

import com..cucumber.uisteps.BaseUISteps;
import com..cucumber.uisteps.application.ActionSteps;
import com..test.pages.application.ActionPage;
import com..test.pages.application.AlertReviewPage;
import com..test.pages.application.ApplicationRecordPage;
import com..test.pages.application.AutoCheckResultsPage;
import com..test.pages.settings.SystemParametersPage;
import cucumber.api.PendingException;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import net.thucydides.core.annotations.Shared;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.sql.SQLException;


public class SystemParametersSteps extends BaseUISteps {

    SystemParametersPage systemParametersPage;

    @Shared
    ActionPage actionPage;

    @Shared
    AlertReviewPage alertReviewPage;

    @Shared
    AutoCheckResultsPage autoCheckResultsPage;

    @Shared
    ApplicationRecordPage applicationRecordPage;

    @Shared
    ActionSteps actionSteps;


    @When("^I turn \"([^\"]*)\" the \"([^\"]*)\" on System Parameters$")
    public void iTurnTheOnSystemParameters(String toggle, String parameter) {

        systemParametersPage.toggleParameter(toggle, parameter);
        systemParametersPage.clickSave();
    }

    @And("^I entered \"([^\"]*)\" in Rules Run In Parallel Textbox$")
    public void iEnteredInRulesRunInParallelTextbox(String value) throws Throwable {

        systemParametersPage.enter_Value_in_Rules_Run_In_Parallel(value);
        systemParametersPage.clickSave();
    }

    @Then("^I verify the Show Selected Rule Prompt function on \"([^\"]*)\" page$")
    public void iVerifyTheShowSelectedRulePromptFunction(String page) throws SQLException, IOException, ParseException {
        actionSteps.clickActionButtonFromPage(page);

        actionPage.clickActionInSelectActionPopup("Under Investigation");

        waitABit(2000);
        actionPage.verifyShowSelectedRulePrompt();
    }

    @And("^I navigate to Server Parameters$")
    public void iNavigateToServerParameters() {

        systemParametersPage.clickServerParameterTab();
    }


    @And("^I turn \"([^\"]*)\" the \"([^\"]*)\" on Server Parameters$")
    public void iTurnTheOnServerParameters(String toggle, String parameter) throws Throwable {
        systemParametersPage.enableUserField2(toggle, parameter);
        systemParametersPage.enableUserField3(toggle, parameter);
        systemParametersPage.enableUserField7(toggle, parameter);
        systemParametersPage.enableUserField10(toggle, parameter);
        systemParametersPage.clickSave();
    }

    @Then("^I verify the Input parameters to be updated$")
    public void iVerifyTheInputParametersToBeUpdated() {
        systemParametersPage.verifyInputMappingParameters();

    }

    @And("^I select Workflow Settings tab on System Parameters page$")
    public void iSelectWorkflowSettingsTabOnSystemParametersPage() {
        systemParametersPage.clickWorkflowSettingsTab();
    }




    @And("^I type \"([^\"]*)\" as ClientManualWorkflow on SystemParamters page$")
    public void iTypeAsClientManualWorkflowOnSystemParamtersPage(String manualWorkflow) throws Throwable {
        systemParametersPage.enterManualWorkflowClient(manualWorkflow);

    }
}