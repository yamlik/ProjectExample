package com..cucumber.uisteps.settings;

import com..cucumber.uisteps.BaseUISteps;
import com..test.pages.settings.FieldSettingsPage;
import cucumber.api.PendingException;
import cucumber.api.java.en.And;


public class FieldSettingsSteps extends BaseUISteps {

    FieldSettingsPage fieldSettingsPage;

    @And("^I add \"([^\"]*)\" in selected fields list$")
    public void iAddInSelectedFieldsList(String fieldName) {
        fieldSettingsPage.selectConfigurationPurpose();
        fieldSettingsPage.addApplicantIdNumber1(fieldName);
    }

    @And("^I select \"([^\"]*)\" as Configuration Purpose$")
    public void iSelectAsConfigurationPurpose(String config) {
        fieldSettingsPage.enterConfigurationPurpose(config);
    }

    @And("^I assign \"([^\"]*)\" for the above Configuration Purpose$")
    public void iAssignForTheAboveConfigurationPurpose(String field) {
        fieldSettingsPage.selectAndMoveField(field);
    }

}
