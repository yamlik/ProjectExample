package com..cucumber.uisteps.settings;

import com..cucumber.uisteps.BaseUISteps;
import com..test.pages.settings.FieldRequirementsPage;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;

public class FieldRequirementsSteps extends BaseUISteps {

FieldRequirementsPage fieldRequirementsPage;

    @And("^I select security category for the application$")
    public void iSelectSecurityCategoryForTheApplication() {
        fieldRequirementsPage.selectCategory();
    }


}
