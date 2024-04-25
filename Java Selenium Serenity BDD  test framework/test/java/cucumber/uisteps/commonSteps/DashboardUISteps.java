package com..cucumber.uisteps.commonSteps;

import com..cucumber.uisteps.BaseUISteps;
import com..test.pages.commonPages.Dashboard;
import cucumber.api.java.en.Then;
import org.junit.Assert;

public class DashboardUISteps extends BaseUISteps {

    private Dashboard dashboard;

    @Then("^I should see the Dashboard page$")
    public void iShouldSeeTheDashboardPage() throws InterruptedException {
        Assert.assertTrue("Basic elements for dashboard page are not displayed", dashboard.isDashboardDisplayed());

    }}