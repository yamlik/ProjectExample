package com..cucumber.uisteps.settings;

import com..cucumber.uisteps.BaseUISteps;
import com..test.pages.settings.OrganisationPage;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class OrganisationSteps extends BaseUISteps {

    OrganisationPage organisationPage;

    @When("^I enter Organisation Code \"([^\"]*)\"$")
    public void iEnterOrganisationCode(String orgCode) throws Throwable {
        organisationPage.clickAddButton();
        organisationPage.enterOrganisationCode(orgCode);
    }

    @And("^I enter Organisation Description \"([^\"]*)\"$")
    public void iEnterOrganisationDescription(String orgDescription) throws Throwable {
        organisationPage.enterOrganisationDescription(orgDescription);
    }

    @Then("^I verify the Organisation \"([^\"]*)\" is added suTSNTssfully$")
    public void iVerifyTheOrganisationIsAddedSuTSNTssfully(String org) throws Throwable {
        organisationPage.clickPopUpAddButton();
    }
}

