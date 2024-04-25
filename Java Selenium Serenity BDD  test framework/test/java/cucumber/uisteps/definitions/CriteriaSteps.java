package com..cucumber.uisteps.definitions;

import com..cucumber.uisteps.BaseUISteps;
import com..test.modules.ApplicationModule;
import com..test.pages.commonPages.*;
import com..test.pages.definitions.Criteria;
import com..test.pages.settings.SystemParametersPage;
import cucumber.api.PendingException;
import cucumber.api.java.en.And;
import net.thucydides.core.annotations.Shared;
import net.thucydides.core.annotations.Steps;

public class CriteriaSteps extends BaseUISteps {

    @Shared
    private Criteria Criteria;
    private Navigation navigation;
    private RecentPage recentPage;

    @Steps
    private ApplicationModule applicationModule;
    private SystemParametersPage systemParametersPage;

    @And("^I add details on criteria definition page$")
    public void iAddDetailsOnCriteriaDefinitionPage() {
        Criteria.addDetailsOnCriteriaDefinitionPage();
    }

    @And("^I add details on criteria definition page with \"([^\"]*)\" criteria$")
    public void iAddDetailsOnCriteriaDefinitionPageWithData(String criteria) {
        Criteria.addDetailsOnCriteriaDefinitionPageWithData(criteria);
    }

    @And("^I delete created criteria$")
    public void iDeleteCreatedCriteria() {
        Criteria.deleteCreatedCriteria();
    }

    @And("^I select from criteria dropdown and search$")
    public void iSelectFromCriteriaDropdownAndSearch() {
        Criteria.selectCriteriaAndSearch();

    }
}