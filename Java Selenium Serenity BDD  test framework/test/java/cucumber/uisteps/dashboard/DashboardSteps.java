package com..cucumber.uisteps.dashboard;

import com..cucumber.uisteps.BaseUISteps;
import com..test.pages.BasePage;
import com..test.pages.commonPages.DashboardPage;
import cucumber.api.PendingException;
import cucumber.api.java.en.And;
import org.json.simple.parser.ParseException;

import java.io.IOException;

public class DashboardSteps extends BaseUISteps {

    private DashboardPage dashboardPage;
    BasePage basePage;


    @And("^I add \"([^\"]*)\" widget$")
    public void iAddWidget(String name) throws Throwable {
        dashboardPage.addMyQueueWidget(name);
    }

    @And("^I select the user Id as \"([^\"]*)\"and criteria as \"([^\"]*)\"for the user$")
    public void iSelectTheUserIdAsAndCriteriaAsForTheUser(String userId, String criteria) throws Throwable {

        dashboardPage.addCriteria(userId, criteria);
    }

    @And("^I verify the data in My Queue widget based on selected criteria$")
    public void iVerifyTheDataInMyQueueWidgetBasedOnSelectedCriteria() throws IOException, ParseException {
        basePage.verifyTable();

    }

    @And("^I navigate using breadcrumbs to \"([^\"]*)\" page$")
    public void iNavigateUsingBreadcrumbsToPage(String page) {
        dashboardPage.clickBreadcrumb(page);
    }
}
