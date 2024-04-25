package com..cucumber.uisteps.rules;

import com..cucumber.uisteps.BaseUISteps;
import com..test.pages.rules.IndexedFuzzyMatchMappingPage;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;



public class IndexedFuzzyMatchMappingSteps extends BaseUISteps {
    private IndexedFuzzyMatchMappingPage indexedFuzzyMatchMappingPage;

    @When("^I add new mapping with \"([^\"]*)\" group name and \"([^\"]*)\" field$")
    public void iAddNewMapping(String groupName, String field) throws Throwable {
        {
            indexedFuzzyMatchMappingPage.clickAdd();
            indexedFuzzyMatchMappingPage.EnterGroupName(groupName);
            indexedFuzzyMatchMappingPage.clickSelectAll();
            indexedFuzzyMatchMappingPage.selectField(field);
        }
    }

    @Then("^I save the mapping$")
    public void iAddNewMapping() throws Throwable {
        {
            indexedFuzzyMatchMappingPage.saveMapping();
        }
    }

    @Then("^I verify the Mapping \"([^\"]*)\" already added$")
    public void iVerifyMappingName(String fieldName) throws InterruptedException {
        indexedFuzzyMatchMappingPage.verifyMappingName(fieldName);
    }




}
