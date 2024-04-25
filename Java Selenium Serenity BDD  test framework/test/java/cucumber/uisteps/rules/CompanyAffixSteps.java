package com..cucumber.uisteps.rules;

import com..cucumber.uisteps.BaseUISteps;
import com..test.pages.commonPages.Navigation;
import com..test.pages.rules.CompanyAffixPage;
import cucumber.api.PendingException;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import net.serenitybdd.core.annotations.findby.FindBy;
import net.serenitybdd.core.pages.WebElementFacade;
import net.thucydides.core.annotations.Shared;

public class CompanyAffixSteps extends BaseUISteps {

    @Shared
    private Navigation navigation;
    private CompanyAffixPage companyAffixPage;



        @When("^I Add the Company \"([^\"]*)\"$")
    public void iAddTheCompanySuffix(String suffix) {
            companyAffixPage.addSuffix(suffix);

    }

    @And("^I update the Company name for all categories with \"([^\"]*)\"$")
    public void iUpdateTheCompanyNameWith(String testData) throws InterruptedException {
        companyAffixPage.updateCompanyName(testData);

    }

    @Then("^I verify the Company name for all categories with \"([^\"]*)\"$")
    public void iVerifyTheCompanyNameForAllCategoriesWith(String expectedData) throws InterruptedException {
        companyAffixPage.verifyCompanyName(expectedData);
    }
}