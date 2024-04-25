package com..cucumber.uisteps;

import com..test.context.ScenarioContext;
import com..test.utilities.Properties;
import net.serenitybdd.core.pages.PageObject;

public class BaseUISteps extends PageObject {
    protected final String dataDir = System.getProperty("user.dir") + "/src/main/resources/data/UI/";
    protected ScenarioContext scenarioContext = ScenarioContext.getTSNTance();
}
