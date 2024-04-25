package com..cucumber.uisteps.commonSteps;

import com..test.pages.commonPages.WhatsNewPage;
import com..test.pages.settings.SystemParametersPage;
import cucumber.api.java.en.When;
import com..cucumber.uisteps.BaseUISteps;
import cucumber.api.java.en.Then;
import org.junit.Assert;

public class whatsNewSteps extends BaseUISteps{

    WhatsNewPage whatsNewPage;
    SystemParametersPage systemParametersPage;

    @When("^I see the \"([^\"]*)\" popup$")
    public void iSeeThePopup(String message) {
        Assert.assertTrue("What's New popup not displayed", whatsNewPage.verifyWhatsNewPageLoads(message));
    }

    @Then("^I choose \"([^\"]*)\" on the What's New Popup$")
    public void iChooseOnTheWhatSNewPopup(String choice) {
        whatsNewPage.makeAChoice(choice);
    }


}
