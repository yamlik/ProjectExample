package com..cucumber.uisteps.definitions;

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
import java.util.List;

public class ResultTableSteps extends BaseUISteps {

    ResultTableMaintenanceModule resultTableMaintenanceModule = new ResultTableMaintenanceModule();

    @Given("^I create \"([^\"]*)\" ResultTable using an API$")
    public void iCreateResultTableUsingAnAPI(int count) throws Throwable {
        for (int i=1; i<=count; i++)
            resultTableMaintenanceModule.createAResultTable("createAResultTable"+i);

    }

    @And("^I create \"([^\"]*)\" ResultTableFields using an API$")
    public void iCreateResultTableFieldsUsingAnAPI(int count) throws Throwable {
        for (int i=1; i<=count; i++)
            resultTableMaintenanceModule.createAResultTableFields("createResultTableFields"+i);
            }

    @And("^I create \"([^\"]*)\" Workflow using an API$")
    public void iCreateWorkflowUsingAnAPI(int count) throws Throwable {

        for (int i=1; i<=count; i++)
            resultTableMaintenanceModule.createAWorkflow("createAWorkflow"+i);
    }


}


