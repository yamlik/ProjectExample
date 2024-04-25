package com..cucumber.uisteps.definitions;

import com..cucumber.uisteps.BaseUISteps;
import com..test.modules.ApplicationModule;
import com..test.pages.commonPages.Navigation;
import com..test.pages.commonPages.RecentPage;
import com..test.pages.definitions.ClassingMaintenancePage;
import com..test.pages.settings.SystemParametersPage;
import com..test.utilities.JSONHelper;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import net.thucydides.core.annotations.Shared;
import net.thucydides.core.annotations.Steps;
import org.json.JSONObject;

import java.io.IOException;

public class ClassingMaintenanceSteps extends BaseUISteps {
    @Shared
    private ClassingMaintenancePage ClassingMaintenancePage;
    private Navigation navigation;
    private RecentPage recentPage;
    @Steps
    private ApplicationModule applicationModule;
    private SystemParametersPage systemParametersPage;


    @And("^I add the Classing Id as \"([^\"]*)\"$")
    public void iAddTheClassingIdAs(String iD) throws Throwable {
        ClassingMaintenancePage.addClassingId(iD);
        ClassingMaintenancePage.saveClassingDefinition();
    }

    @Then("^I verify the added classing definition is saved correctly$")
    public void iVerifyTheAddedClassingDefinitionIsSavedCorrectly() {
        ClassingMaintenancePage.verifyClassingId();
    }

    @And("^I add New Attribute Definition having values$")
    public void iAddNewAttributeDefinitionHavingValuesWith() throws IOException {
        waitABit(3000);
        ClassingMaintenancePage.testData();
    }

    @And("^I change the existing classing Id$")
    public void iChangeTheExistingClassingId() {
        ClassingMaintenancePage.changeClassingId();
    }

    @And("^I change one of the attribute definition points value as \"([^\"]*)\"after shift down$")
    public void iChangeOneOfTheAttributeDefinitionPointsValueAsAfterShiftDown(String points) throws Throwable {
        ClassingMaintenancePage.setShiftDown(points);
    }

    @Then("^I verify the modified attribute is saved correctly$")
    public void iVerifyTheModifiedAttributeIsSavedCorrectly() {
        ClassingMaintenancePage.verifyTheModifiedAttribute();
    }

    @And("^I delete the added classing Id \"([^\"]*)\"$")
    public void iDeleteTheAddedClassingId(String data) throws Throwable {
        ClassingMaintenancePage.setDeleteClassingId(data);
    }

    @And("^I create new attribute$")
    public void iCreateNewAttribute() {
        ClassingMaintenancePage.clickAddButton();
    }

    @And("^I add New Attribute Definition having new values \"([^\"]*)\",\"([^\"]*)\",\"([^\"]*)\"$")
    public void iAddNewAttributeDefinitionHavingNewValues(String data, String data1, String data2) throws Throwable {
        JSONObject requestBody = JSONHelper.messageAsActualJson(dataDir + "Definitions/ClassingMaintenance.json");
        data = requestBody.getString("Desc");
        data1 = requestBody.getString("Values");
        data2 = requestBody.getString("Attributepoints");
        ClassingMaintenancePage.setAddNewAttributeDefinition(data, data1, data2);
    }
}


