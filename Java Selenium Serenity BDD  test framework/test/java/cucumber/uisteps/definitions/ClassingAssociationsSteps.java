
package com..cucumber.uisteps.definitions;

import com..cucumber.uisteps.BaseUISteps;
import com..test.modules.ApplicationModule;
import com..test.pages.commonPages.Navigation;
import com..test.pages.commonPages.RecentPage;
import com..test.pages.definitions.ClassingAssociationPage;
import com..test.pages.settings.SystemParametersPage;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import net.thucydides.core.annotations.Shared;
import net.thucydides.core.annotations.Steps;

public class ClassingAssociationsSteps extends BaseUISteps {
    @Shared
    private ClassingAssociationPage ClassingAssociationPage;
    private Navigation navigation;
    private RecentPage recentPage;
    @Steps
    private ApplicationModule applicationModule;
    private SystemParametersPage systemParametersPage;

    @And("^I add a new Classing Association selecting all the fields$")
    public void iAddANewClassingAssociationSelectingAllTheFields() {
        ClassingAssociationPage.enterCategory("Application");
        ClassingAssociationPage.enterField("Account Number");
        ClassingAssociationPage.enterClassingId("Testing Only");
    }

    @And("^I add classing association name as \"([^\"]*)\"$")
    public void iAddClassingAssociationNameAs(String name) throws Throwable {
        ClassingAssociationPage.clickClassingAssociations(name);
    }

    @And("^I delete \"([^\"]*)\" data$")
    public void iDeleteData(String removeData) throws Throwable {
        ClassingAssociationPage.deleteDatta(removeData);
    }

    @Then("^I verify the added is saved correctly$")
    public void iVerifyTheAddedIsSavedCorrectly() throws InterruptedException {
        ClassingAssociationPage.saveClassingButton();
        ClassingAssociationPage.verifySaving();
    }
}