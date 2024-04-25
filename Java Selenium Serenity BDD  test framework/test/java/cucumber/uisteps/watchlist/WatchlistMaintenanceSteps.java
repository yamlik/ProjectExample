package com..cucumber.uisteps.watchlist;

import com..cucumber.uisteps.BaseUISteps;
import com..test.pages.watchlist.WatchlistRecordPage;
import com.paulhammant.ngwebdriver.ByAngular;
import cucumber.api.DataTable;
import cucumber.api.PendingException;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import net.serenitybdd.core.pages.WebElementFacade;
import net.serenitybdd.core.pages.WebElementFacadeImpl;
import net.thucydides.core.annotations.Shared;
import org.jetbrains.annotations.NotNull;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class WatchlistMaintenanceSteps extends BaseUISteps {

    @Shared
    private WatchlistRecordPage watchlistRecordPage;

    @Then("^I add details on categories: \"([^\"]*)\" on the Watchlist Record page$")
    public void iAddDetailsOnWatchlistPageWithDetailsDefinedIn(String source) throws IOException, ParseException {
        String[] categories = source.split(",");
        for (String item : categories) {
            JSONObject requestBody = scenarioContext.getDataDriveSectionAsJSON(item);

            Set<Map.Entry> entries = requestBody.entrySet();

            // Select Category
            watchlistRecordPage.selectCategory(item);

            for (Map.Entry entry : entries) {

                // Enter Details
                watchlistRecordPage.enterWatchlistDetails(entry.getKey().toString(), entry.getValue().toString());
            }
        }
        watchlistRecordPage.clickSave();
    }

    @And("^I search for a Watchlist from Watchlist Maintenance Page$")
    public void iSearchForAWatchlistFromWatchlistMaintenancePage() throws IOException, ParseException {
        JSONObject dataDrive = scenarioContext.getDataDriveSectionAsJSON("searchWatchlist");
        watchlistRecordPage.addSearchParameter(dataDrive.get("category").toString(),
                dataDrive.get("field").toString(),
                dataDrive.get("operator").toString(),
                dataDrive.get("value1").toString(),
                dataDrive.get("value2").toString());
        watchlistRecordPage.searchButton.click();
        waitFor(watchlistRecordPage.watchlistCategoryOrAppResultsTitle).waitUntilVisible();
    }

    @And("^I modify details on Watchlist Record Page$")
    public void i_modify_details_on_Watchlist_Record_Page(@NotNull DataTable modifyTable) throws IOException {

        List<Map<String, String>> rows = modifyTable.asMaps(String.class, String.class);
        for (Map<String, String> row : rows) {
            watchlistRecordPage.updateWatchlistRecord(row.get("Field"), row.get("Value"));
        }
        watchlistRecordPage.clickOnUpdateSaveButton();
        watchlistRecordPage.clickOnUpdateYesButton();
    }

    @Then("^I verify the watchlist record is updated correctly$")
    public void iVerifyTheWatchlistRecordIsUpdatedCorrectly() throws IOException, ParseException {
        JSONObject dataDrive = scenarioContext.getDataDriveSectionAsJSON("verifyWatchlist");
        Set<Map.Entry> entries = dataDrive.entrySet();
        for (Map.Entry entry : entries) {
            WebElement watchlistData = watchlistRecordPage.getCategoryFieldValueElement(entry.getKey().toString());
            Assert.assertEquals("Watchlist Data is not same as expected", watchlistData.getText(), entry.getValue().toString());
        }
    }

    @And("I clone current Watchlist Record$")
    public void iCloneCurrentWatchListRecord() {
        watchlistRecordPage.clickClone();
    }

    @And("^I search for \"([^\"]*)\" from Watchlist Maintenance Page$")
    public void iSearchForAWatchlistFromWatchlistMaintenancePage(String searchTarget) throws IOException, ParseException {
        JSONObject dataDrive = scenarioContext.getDataDriveSectionAsJSON(searchTarget);
        watchlistRecordPage.addSearchParameter(dataDrive.get("category").toString(),
                dataDrive.get("field").toString(),
                dataDrive.get("operator").toString(),
                dataDrive.get("value1").toString(),
                dataDrive.get("value2").toString());
        watchlistRecordPage.searchButton.click();
        waitFor(watchlistRecordPage.watchlistCategoryOrAppResultsTitle).waitUntilVisible();
    }

    @Then("^I verify the search result of watchlist maintenance display total of \"([^\"]*)\" records with pattern \"([^\"]*)\"$")
    public void iVerifySearchResultDisplayTotalOfRecordsWithPattern(int numberOfExpectedRecords, String pattern){
        assert(getDriver().findElements(By.xpath("//span[contains(text(),'" + pattern + "')]")).size() == numberOfExpectedRecords);
    }
}












