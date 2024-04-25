package com..cucumber.uisteps.definitions;

import com..cucumber.uisteps.BaseUISteps;
import com..test.modules.ApplicationModule;
import com..test.pages.commonPages.Navigation;
import com..test.pages.commonPages.RecentPage;
import com..test.pages.definitions.OutputAlertPage;
import com..test.pages.settings.SystemParametersPage;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import net.thucydides.core.annotations.Shared;
import net.thucydides.core.annotations.Steps;
import org.yecht.ruby.Out;

public class OutputAlertSteps extends BaseUISteps {

    @Shared
    private OutputAlertPage OutputAlertPage;
    private Navigation navigation;
    private RecentPage recentPage;

    @Steps
    private ApplicationModule applicationModule;
    private SystemParametersPage systemParametersPage;


    @And("^I add email address name as \"([^\"]*)\"$")
    public void iAddEmailAddress(String name) throws Throwable{
        OutputAlertPage.enterEmailAddress(name);
    }

    @And("^I add subject as \"([^\"]*)\"$")
    public void iAddSubject(String name) throws Throwable{
        OutputAlertPage.enterSubject(name);
    }

    @And("^I add email content with \"([^\"]*)\" criteria and \"([^\"]*)\" field$")
    public void iAddEmailContent(String criteria, String field) throws Throwable{
        OutputAlertPage.enterCriteria(criteria);
        OutputAlertPage.enterAvailableFields(field);
        OutputAlertPage.addCriteria();
    }

    @Then("^I verify the added \"([^\"]*)\" alert is saved correctly$")
    public void iVerifyTheAddedIsSavedCorrectly(String data) throws InterruptedException {
        OutputAlertPage.saveAlert();
        OutputAlertPage.verifySaving(data);
    }

    @And("^I delete \"([^\"]*)\" alert$")
    public void iDeleteData(String removeData) throws Throwable {
        OutputAlertPage.deleteData(removeData);
    }


}
