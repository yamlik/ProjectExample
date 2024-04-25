package com..cucumber.uisteps.settings;

import com..cucumber.uisteps.BaseUISteps;
import com..test.modules.ApplicationModule;
import com..test.pages.settings.FraudAlertsPage;
import com..test.pages.commonPages.Navigation;
import com..test.pages.commonPages.RecentPage;
import com..test.pages.settings.SystemParametersPage;
import cucumber.api.PendingException;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import net.thucydides.core.annotations.Shared;
import net.thucydides.core.annotations.Steps;

public class FraudAlertsSteps extends BaseUISteps {


    @Shared
    private FraudAlertsPage FraudAlertsPage;
    private Navigation navigation;
    private RecentPage recentPage;
    @Steps
    private ApplicationModule applicationModule;
    private SystemParametersPage systemParametersPage;

    @When("^I set 0 to \"([^\"]*)\" for Clean$")
    public void iSetToForClean(String cleanTextBox) throws Throwable {
        FraudAlertsPage.enterFraudScoreForClean(cleanTextBox);

    }

    @And("^I set \"([^\"]*)\" to \"([^\"]*)\" for Suspect$")
    public void iSetToForSuspect(String suspectFromTextBox, String suspectToTextBox) throws Throwable {
        FraudAlertsPage.enterFraudScoreForSuspectFrom(suspectFromTextBox);
        FraudAlertsPage.enterFraudScoreForSuspectTo(suspectToTextBox);

    }

    @And("^I set \"([^\"]*)\" to 999 for HFP$")
    public void iSetToForHFP(String highFraudPotentialTextBox) throws Throwable {
        FraudAlertsPage.enterFraudScoreForHigh(highFraudPotentialTextBox);
    }

    @Then("^I save the Fraud Alerts$")
    public void iSaveTheFraudAlerts() {

        FraudAlertsPage.clickSave();
    }

}

