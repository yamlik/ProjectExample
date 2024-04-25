package com..cucumber.uisteps.definitions;

import com..cucumber.uisteps.BaseUISteps;
import com..test.modules.ApplicationModule;
import com..test.pages.commonPages.Navigation;
import com..test.pages.commonPages.RecentPage;
import com..test.pages.settings.SystemParametersPage;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import net.thucydides.core.annotations.Shared;
import net.thucydides.core.annotations.Steps;

public class CriteriaFieldsSteps extends BaseUISteps {
    @Shared
    private com..test.pages.definitions.CriteriaFieldsPage CriteriaFieldsPage;
    private Navigation navigation;
    private RecentPage recentPage;

    @Steps
    private ApplicationModule applicationModule;
    private SystemParametersPage systemParametersPage;

    @And("^I add new criteria field having values as \"([^\"]*)\"$")
    public void iAddNewCriteriaFieldHavingValuesAs(String Criteria) throws Throwable {
        CriteriaFieldsPage.addCriteriaFields(Criteria);

    }

    @And("^I add the \"([^\"]*)\" in available fields ,\"([^\"]*)\" in Order Id and Value as \"([^\"]*)\"$")
    public void iAddTheInAvailableFieldsInOrderIdAndValueAs(String availableFields, String orderId, String value) throws Throwable {
        CriteriaFieldsPage.addOtherFields(availableFields, orderId, value);
    }

    @Then("^I verify the added Criteria Field$")
    public void iVerifyTheAddedCriteriaField() {
        CriteriaFieldsPage.verifyAddedCriteriaFields();
    }

    @And("^I delete the Order Id \"([^\"]*)\" data$")
    public void iDeleteTheOrderIdData(String data) throws Throwable {
        CriteriaFieldsPage.deleteData(data);
    }
}
