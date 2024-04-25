package com..cucumber.uisteps.settings;
import com..cucumber.uisteps.BaseUISteps;
import com..test.pages.settings.ActionDecisionPage;
import com..test.pages.commonPages.Navigation;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import java.util.ArrayList;
import java.util.List;

public class ActionDecisionSteps extends BaseUISteps {

    Navigation navigation;

    ActionDecisionPage decision;

    List<String> placeHolder = new ArrayList<>();

    @When("^I add \"([^\"]*)\" as Decision Reason if it does not exist$")
    public void iAddAsDecisionReason( String parameter)  {
        placeHolder.add(parameter);
        if(!decision.verifyPresenceOfRule(parameter)) {
            decision.clickAdd();
            decision.inputToTextBox(parameter);
            decision.clickPopUpAddButton();
        }
    }

    @Then("^I verify the Decision Reason has been added$")
    public void iVerifyTheDecisionReasonHasBeenAdded() {
        for(String temp :  placeHolder) {
            assert(decision.verifyPresenceOfRule(temp));
        }
    }
}