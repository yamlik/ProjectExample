package com..cucumber.uisteps.security;

import com..cucumber.uisteps.BaseUISteps;
import com..test.pages.security.SecurityParameters;
import com..test.pages.settings.SystemParametersPage;
import cucumber.api.java.en.When;

public class SecurityParameterSteps extends BaseUISteps {

    SecurityParameters securityParameters;

    @When("^I turn \"([^\"]*)\" the \"([^\"]*)\" on Security Parameters$")
    public void iTurnTheOnSecurityParameters(String toggle, String parameter) throws InterruptedException {
        securityParameters.toggleParameterSecurity(toggle, parameter);
        securityParameters.clickSave();
    }
}