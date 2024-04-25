package com..cucumber.uisteps.commonSteps;

import com.fasterxml.jackson.databind.ObjectMapper;
import com..cucumber.uisteps.BaseUISteps;
import com..test.pages.commonPages.Navigation;
import com..test.pages.reports.Reports;
import com..test.pages.rules.RuleSetsPage;
import com..test.pages.rules.RuleSetsPage;
import com..test.pages.settings.SystemParametersPage;
import com.google.gson.Gson;
import cucumber.api.PendingException;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import net.thucydides.core.annotations.Shared;
import net.thucydides.core.pages.PageObject;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class NavigationSteps extends BaseUISteps {
    private static final Gson GSON = new Gson();
    @Shared
    private Navigation navigation;
    private RuleSetsPage rules;
    private PageObject page;
    private  Reports Reports;

    @Given("^I expand the sidebar menu$")
    public void clickExpandSidebarMenu() {
        navigation.clickExpandSidebarMenu();
    }

    @Given("^I select \"([^\"]*)\" from Navigation bar$")
    public void clickSidebarNavigation(String element) {
        navigation.clickSidebarOption(element);
    }

    @Then("I select \"([^\"]*)\" from Navigation sub menu$")
    public void clickSidebarSubNavigation(String element) throws InterruptedException {
        navigation.clickSidebarSubMenuOption(element);
    }
    @Given("^I should navigate to the system import page$")
    public void iShouldNavigateToTheSystemImportPage() throws InterruptedException {
        navigation.clickSidebarOption("utilities");
        navigation.clickUtilitiesSubMenu("systemImport");
    }
    @Given("^User is on application \"([^\"]*)\" page$")
    public void userIsOnApplicationPage(String element) {

        navigation.clickSidebarOption(element);
        navigation.clickSidebarSubMenuOption(element);
    }
    @And("^I click on \"([^\"]*)\" to add a Rule$")
    public void iClickOnToAddARule(String element)  {
        navigation.clickSidebarSubMenuOption(element);
    }

    @Given("^User is on application Rules page$")
    public void userIsOnApplicationRulesPage() {
        navigation.clickSidebarOption("Rules");
        navigation.clickSidebarSubMenuOption("Rule Sets");
    }
    @And("^I am on \"([^\"]*)\" Page$")
    public void iAmOnPage(String requiredPage) throws Throwable {
        navigation.navigateToPage(requiredPage);
    }
    @And("^I  navigate to New Watchlist page$")
    public void iNavigateToNewWatchlistPage() {
        navigation.watchlistMaintenanceDropdownSelection();
    }

    @And("^I navigate to New Application page$")
    public void iNavigateToNewApplicationPage() {
        navigation.applicationMaintenanceDropdownSelection();
    }

    @And("^I navigate to New \"([^\"]*)\" Page$")
    public void iNavigateToNewPage(String requiredPage) throws Throwable {
        navigation.navigateTo(requiredPage);
    }
    @Given("^User is on Security page$")
    public void userIsOnSecurityPage() {
        navigation.clickSidebarOption("Security");
    }
    @And("^I navigate to profile page$")
    public void iNavigateToProfilePage() {
        navigation.clickSidebarSubMenuOption("Profiles");
    }
    @And("^I am on Users Page$")
    public void iAmOnUsersPage() throws InterruptedException {
        navigation.clickSidebarSubMenuOption("Users");
        Thread.sleep(2000);
    }

    @And("^I navigate to New \"([^\"]*)\" Page and upload Excelsheet$")
    public void iNavigateToNewPageAndUploadExcelsheet(String requiredPage) throws Throwable {
        navigation.applicationPage(requiredPage);
    }
    @And("^I Export Available Fields$")
    public void iExportAvailableFields() {
        Reports.Available_fields_checkBox();
        Reports.Export_Application_fields();
    }
    @And("^I verify Action Taken labels and UI labels from \"([^\"]*)\"$")
    public void iVerifyActionTakenLabelsAndUILabelsFrom(String categories) throws Throwable {

        List<List<String>> UIData = Reports.get_TableData ();
        JSONObject requestBody = scenarioContext.getDataDriveSectionAsJSON(categories);
        Set<Map.Entry> entries = requestBody.entrySet();
    }

    }