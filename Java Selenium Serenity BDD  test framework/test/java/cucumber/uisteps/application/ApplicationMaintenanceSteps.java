package com..cucumber.uisteps.application;

import com..cucumber.uisteps.BaseUISteps;
import com..test.context.constants.ApplicationConstants;
import com..test.database.DataBaseClient;
import com..test.modules.ApplicationModule;
import com..test.modules.services.FraudCheckWS;
import com..test.pages.application.ApplicationMaintenancePage;
import com..test.pages.application.ApplicationRecordPage;
import com..test.pages.application.AutoCheckResultsPage;
import com..test.pages.application.MatchReviewPage;
import com..test.pages.commonPages.Navigation;
import com..test.pages.commonPages.RecentPage;
import com..test.pages.definitions.OrganisationChart;
import com..test.pages.settings.SystemParametersPage;
import com..test.pages.utilities.SystemImport;
import com..test.utilities.ExcelUtils;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import net.serenitybdd.core.annotations.findby.FindBy;
import net.serenitybdd.core.pages.WebElementFacade;
import net.thucydides.core.annotations.Shared;
import net.thucydides.core.annotations.Steps;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ApplicationMaintenanceSteps extends BaseUISteps {

    @Shared
    private ApplicationRecordPage applicationRecordPage;
    private OrganisationChart organisationChartPage;

    private ApplicationMaintenancePage applicationMaintenancePage;
    private Navigation navigation;
    private RecentPage recentPage;
    DataBaseClient dataBaseClient = new DataBaseClient();
    FraudCheckWS fraudCheckWS = new FraudCheckWS();
    private com..test.utilities.FileUtils FileUtils;

    private AutoCheckResultsPage autoCheckResultsPage;
    private MatchReviewPage matchReviewPage;


    @Steps
    private ApplicationModule applicationModule;
    private SystemParametersPage systemParametersPage;
    private SystemImport systemImport;
    private ExcelUtils excelUtils;
    private DataBaseClient DataBaseClient;
    private MatchReviewPage MatchReviewPage;

    @Then("^I click on the 'New Application' button on footer of the Application Maintenance page$")
    public void clickOnNewApplicationButtonOnApplicationMaintenancePage() {
        navigation.applicationMaintenanceDropdownSelection();
    }

    @Then("^I add detail on categories: \"([^\"]*)\" on the Application Record page$")
    public void iAddDetailOnApplicationPageWithDetailsDefinedIn(String source) throws IOException, ParseException {
        waitABit(5000);
        String[] categories = source.split(",");
        for (String item : categories) {
            JSONObject requestBody = scenarioContext.getDataDriveSectionAsJSON(item);

            Set<Map.Entry> entries = requestBody.entrySet();


            // Select Category
            applicationRecordPage.selectCategory(item);

            for (Map.Entry entry : entries) {

                // Enter Details
                applicationRecordPage.enterApplicationDetails(entry.getKey().toString(), entry.getValue().toString());
            }
        }
        applicationRecordPage.clickSave();
        waitABit(2000);
    }

    @Then("^I add details on categories: \"([^\"]*)\" on the Application Record page$")
    public void iAddDetailsOnApplicationPageWithDetailsDefinedIn(String source) throws IOException, ParseException {
        waitABit(2000);
        String[] categories = source.split(",");
        for (String item : categories) {
            JSONObject requestBody = scenarioContext.getDataDriveSectionAsJSON(item);

            Set<Map.Entry> entries = requestBody.entrySet();

            // Select Category
            applicationRecordPage.selectCategory(item);

            for (Map.Entry entry : entries) {

                // Enter Details
                applicationRecordPage.enterApplicationDetails(entry.getKey().toString(), entry.getValue().toString());
            }
        }
        applicationRecordPage.clickSave();
        waitABit(2000);
    }

    @FindBy(xpath = "//button[text()='Save']")
    private WebElementFacade saveButton;

    @FindBy(xpath = "//button[text()='Submit']")
    private WebElementFacade submitButton;

    @Then("^the new application record is added suTSNTssfully$")
    public void theNewApplicationRecordIsAddedSuTSNTssfully() {
        //Assert the new application is saved
        boolean isExists = applicationRecordPage.checkApplicationExists();
        if (!isExists)
            Assert.fail("Application has not been created");
    }

    @Then("^I enter details on the \"([^\"]*)\" category of the Application Record page$")
    public void iAddApplicationDetailsOnApplicationRecordPage(String source) throws IOException, ParseException {
        String[] categories = source.split(",");
        for (String item : categories) {
            JSONObject requestBody = scenarioContext.getDataDriveSectionAsJSON(item);
            Set<Map.Entry> entries = requestBody.entrySet();

            for (Map.Entry entry : entries) {
                // Enter Details
                applicationRecordPage.enterApplicationDetails(entry.getKey().toString(), entry.getValue().toString());
            }
        }
    }

    @Then("^I add the category \"([^\"]*)\" to the Application Record$")
    public void iAddCategoryToApplication(String source) throws IOException, ParseException {
        String[] categories = source.split(",");
        for (String item : categories) {
            JSONObject requestBody = scenarioContext.getDataDriveSectionAsJSON(item);
            Set<Map.Entry> entries = requestBody.entrySet();

            for (Map.Entry entry : entries) {
                // Enter Details
                applicationRecordPage.enterApplicationDetails(entry.getKey().toString(), entry.getValue().toString());
            }
        }
    }

    @Then("^I add a new applicant to the application$")
    public void iAddNewApplicant() {
        applicationRecordPage.addNewApplicant();
    }

    @Then("^I click the 'Save' button$")
    public void iClickSaveButton() throws InterruptedException {
        applicationRecordPage.clickSave();
        waitABit(3000);
    }

    @Then("^I verify that extra fields appear in New Application page$")
    public void iVerifyThatExtraFieldsAppearInNewApplicationPage() throws IOException, ParseException {
        JSONObject expectedFieldsToVerify = scenarioContext.getDataDriveSectionAsJSON("verify");
        Set<Map.Entry> entries = expectedFieldsToVerify.entrySet();

        for (Map.Entry entry : entries) {
            applicationRecordPage.verifyExtraFields(entry.getKey().toString(), "New Application");
        }
    }

    @And("^I select Fraud Check from review dropdown on footer of the Application Maintenance page$")
    public void iSelectFraudCheckFromReviewDropdownOnFooterOfTheApplicationMaintenancePage() throws InterruptedException {
        applicationRecordPage.clickOnFraudCheckOnReviewDropdown();
    }

    @Then("^I verify the Matches Tab$")
    public void iVerifyTheMatchesTab() {
        boolean matchesTAb = applicationRecordPage.checkApplicationMatchesTab();
        if (!matchesTAb) {
            Assert.fail("Application matches tab is enabled");
        }
    }

    @And("^I select Clone from review dropdown on footer of the Application Maintenance page$")
    public void iSelectCloneFromReviewDropdownOnFooterOfTheApplicationMaintenancePage() throws InterruptedException {
        applicationRecordPage.clickOnCloneOnReviewDropdown();
    }

    @And("^I select Investigation from sidebar and navigate to application maintenance page$")
    public void iSelectInvestigationFromSidebarAndNavigateToApplicationMaintenancePage() throws InterruptedException {
        navigation.navigateToApplicationMaintenance();
    }


    @Then("^I verify the contents of ValueMatches Tab for Application to itself match$")
    public void iVerifyTheContentsOfValueMatchesTabForApplicationToItselfMatch() {
        boolean valeMatchesRuleCat = applicationRecordPage.checkApplicationValueMatchesTab("Applicant", "Home Address 5", "Company Address 5");
        if (!valeMatchesRuleCat) {
            Assert.fail("Application Rules is not same as input");
        }
    }

    @Then("^I verify the contents of ValueMatches Tab for Like match$")
    public void iVerifyTheContentsOfValueMatchesTabForLikeMatch() {
        boolean valeMatchesRuleCat = applicationRecordPage.checkApplicationValueMatchesTab("Applicant", "Company Name");
        if (!valeMatchesRuleCat) {
            Assert.fail("Application Rules is not same as input");
        }

    }


    @And("^I click on the 'New Watchlist' button on footer of the Watchlist Maintenance page$")
    public void iClickOnTheNewWatchlistButtonOnFooterOfTheWatchlistMaintenancePage() {
        navigation.watchlistMaintenanceDropdownSelection();

    }

    @And("^I verify that extra fields appear in Full Application Details page$")
    public void iVerifyThatExtraFieldsAppearInFullApplicationDetailsPage() throws IOException, ParseException {
        applicationRecordPage.clickMainReviewButton();
        JSONObject expectedFieldsToVerify = scenarioContext.getDataDriveSectionAsJSON("verify");
        Set<Map.Entry> entries = expectedFieldsToVerify.entrySet();

        for (Map.Entry entry : entries) {
            applicationRecordPage.verifyExtraFields(entry.getKey().toString(), "Full Application Details");
        }
    }


    @And("^I add details on categories: \"([^\"]*)\" on the new Application Record page$")
    public void iAddDetailsOnCategoriesOnTheNewApplicationRecordPage(String source) throws Throwable {
        String[] categories = source.split(",");
        for (String item : categories) {
            JSONObject requestBody = scenarioContext.getDataDriveSectionAsJSON(item);
            Set<Map.Entry> entries = requestBody.entrySet();
            // Select Category
            applicationRecordPage.selectNewCategory(item);
            for (Map.Entry entry : entries) {
                // Enter Details
                applicationRecordPage.enterApplicationDetails(entry.getKey().toString(), entry.getValue().toString());
            }
        }

        applicationRecordPage.clickSave();
        waitABit(2000);
    }


    @And("^I add details on categories: \"([^\"]*)\" on the new \"([^\"]*)\" Record page$")
    public void iAddDetailsOnCategoriesOnTheNewApplicationRecordPage(String source, String page) throws Throwable {
        String[] categories = source.split(",");
        for (String item : categories) {
            JSONObject requestBody = scenarioContext.getDataDriveSectionAsJSON(item);
            Set<Map.Entry> entries = requestBody.entrySet();
            // Select Category
            applicationRecordPage.selectNewCategory(item);
            for (Map.Entry entry : entries) {
                // Enter Details
                applicationRecordPage.enterApplicationDetails(entry.getKey().toString(), entry.getValue().toString());
            }
        }

        if (page.equals("Application")) {
            applicationRecordPage.clickSaveOnApplicationRecord();
        }
        if (page.equals("Watchlist")) {
            applicationRecordPage.clickSaveOnWatchlistRecord();
            autoCheckResultsPage.getPageTitle();
        }

    }


    @And("^I am on Application Maintenance Page$")
    public void iAmOnApplicationMaintenancePage() throws InterruptedException {
        navigation.navigateToApplicationMaintenance();
    }

    @Then("^I Fraud Check the Application$")
    public void iFraudCheckTheApplication() throws InterruptedException {
        applicationRecordPage.clickOnFraudCheckOnReviewDropdown();
    }

    @And("^I Verify note page slides on notes page$")
    public void iVerifyNotePageSlidesOnNotesPage() {
        applicationRecordPage.clickOnNotesButton();
        applicationRecordPage.verifyNotesSlideContent();
    }

    @And("^I Verify note contents on matches tab$")
    public void iVerifyNoteContentsOnMatchesTab() {
        applicationRecordPage.verifyMatchesNotesSlideContent();
        applicationRecordPage.verifySlideContent();
        applicationRecordPage.verifyNotesSlideFieldColor(ApplicationConstants.RedColor);
    }

    @And("^I clone the created Application$")
    public void iCloneTheCreatedApplication() throws InterruptedException {
        applicationRecordPage.clickOnCloneOnReviewDropdown();
    }

    @And("^I Approve the Watchlist$")
    public void iApproveTheWatchlist() {
        applicationRecordPage.updateComment();
        applicationRecordPage.approveWatchlist();

    }

    @And("^I Approve the \"([^\"]*)\"$")
    public void iApproveThe(String watchlist) {
        applicationRecordPage.clickSubmit();
        applicationRecordPage.updateComment();
    }

    @Then("^I Verify the created watchlist for \"([^\"]*)\" data$")
    public void iVerifyTheCreatedWatchlistForData(String source) throws Throwable {
        String[] categories = source.split(",");
        for (String item : categories) {
            JSONObject requestBody = scenarioContext.getDataDriveSectionAsJSON(item);
        }
    }

    @Then("^I Verify the created watchlist$")
    public void iVerifyTheCreatedWatchlist() throws IOException, ParseException {
        applicationRecordPage.verifyWatchlist();
    }

    @And("^I update the Watchlist$")
    public void iUpdateTheWatchlist() {
        applicationRecordPage.updateWatchlist();

    }

    @And("^I approve the watchlist update$")
    public void iApproveTheWatchlistUpdate() {
        applicationRecordPage.approveUpdateWatchlist();
    }

    @And("^I verify the application is actioned as \"([^\"]*)\"$")
    public void iVerifyTheApplicationIsActionedAs(String action) throws IOException, ParseException {
        recentPage.clickRecentLink();
        recentPage.clickRecentApplicationsTab();
        JSONObject dataDrive = scenarioContext.getDataDriveSectionAsJSON("Application");
        recentPage.doubleClickApplicationRecord(dataDrive.get("Application Number").toString());
        dataDrive = scenarioContext.getDataDriveSectionAsJSON("actionDetails");
        applicationRecordPage.isApplicationRowExists("Application", "Decision Reason", dataDrive.get("Decision Reason").toString());
        applicationRecordPage.isApplicationRowExists("Application", "Action Taken", action);
        applicationRecordPage.isApplicationRowExists("Applicant", "Nature of Fraud", dataDrive.get("Nature of Fraud").toString());

    }

    @And("^I verify the contents of ValueMatches Tab for Value only match$")
    public void iVerifyTheContentsOfValueMatchesTabForValueOnlyMatch() {
        boolean valeMatchesRuleCat = applicationRecordPage.checkValueMatchesTab("verifyValueMatchesTab", "Age of Applicant (Years)", "Income");
        if (!valeMatchesRuleCat) {
            Assert.fail("Application Rules is not same as input");
        }
    }

    @Then("^I Review the Application$")
    public void iReviewTheApplication() {
        applicationRecordPage.review_Application();

    }

    @And("^I verify the contents of ValueMatches Tab for Variable match$")
    public void iVerifyTheContentsOfValueMatchesTabForVariableMatch() {
        boolean valeMatchesRuleCat = applicationRecordPage.checkValueMatchesTabVariableMatch();
        if (!valeMatchesRuleCat) {
            Assert.fail("Application Rules is not same as input");
        }
    }

    @Then("^I verify \"([^\"]*)\" details$")
    public void iVerifyDetails(String arg) throws Throwable {
        boolean details = applicationRecordPage.verifyApplicationDetails(arg);
        if (!details) {
            Assert.fail("Applicant details is not same as expected");

        }
    }

    @Given("^I create an application using api request with income \"([^\"]*)\" and application number \"([^\"]*)\"$")
    public void iCreateAnApplicationUsingApiRequestWithIncomeAndApplicationNumber(String income, String applicationNumber) throws Throwable {
        applicationModule.createApplication(income, applicationNumber);
    }

    @And("^I search with Application Number \"([^\"]*)\"$")
    public void iSearchWithApplicationNumber(String applicationNumber) throws Throwable {
        applicationRecordPage.searchApplication(applicationNumber);
    }

    @Then("^I verify the Header of \"([^\"]*)\"$")
    public void iVerifyTheHeaderOf(String arg) {
        boolean scoreHeader = applicationRecordPage.verifyAppHeader(arg);
        if (!scoreHeader) {
            Assert.fail("Applicant fraud score Header does not match");

        }
    }

    @And("^I click on the Value Matches tab and verify contents of ValueMatchesTab$")
    public void iClickOnTheValueMatchesTabAndVerifyContentsOfValueMatchesTab() throws IOException, ParseException {
        applicationRecordPage.checkApplicationValueMatchTab();
    }

    @And("^I change Display Mode to \"([^\"]*)\"$")
    public void iChangeDisplayModeTo(String displayMode) throws Throwable {
        systemParametersPage.changingDisplayMode(displayMode);
    }

    @And("^I Search and Review the Application$")
    public void iSearchAndReviewTheApplication() {
        applicationRecordPage.searchAndReviewTheApplication();
    }


    @Then("^I Fraud Check the Application and Verify Saved SuTSNTssfully$")
    public void iFraudCheckTheApplicationAndVerifySavedSuTSNTssfully() throws InterruptedException {
        applicationRecordPage.clickOnFraudCheckOnReviewDropdown();
        applicationRecordPage.verifySuTSNTssMessage();
    }

    @And("^I upload the file$")
    public void iUploadTheFile() {
        systemImport.clickOnBrowseExcel_sheet();
    }

    @Then("^I will Verify UI values with dataBase Values$")
    public void iWillVerifyUIValuesWithDataBaseValues() throws IOException, InvalidFormatException, SQLException {
        List<Map<String, String>> excel_result = FileUtils.get_Excel_Sheet_Data();
        List<Map<String, String>> db_result = FileUtils.get_values_From_database(excel_result);
        FileUtils.compare_excel_db_data(excel_result, db_result);
    }

    @And("^I delete Application \"([^\"]*)\"$")
    public void iDeleteApplication(String applicationRowNumber) {
        applicationRecordPage.deleteApplication(applicationRowNumber);

    }

    @And("^I delete Watchlist$")
    public void iDeleteWatchlist() {
        applicationRecordPage.deleteWatchlist();

    }

    @And("^I search for an application from Application Maintenance Page$")
    public void iSearchForAnApplicationFromApplicationMaintenancePage() throws IOException, ParseException {
        JSONObject dataDrive = scenarioContext.getDataDriveSectionAsJSON("searchApplication");
        applicationRecordPage.addSearchParameter(dataDrive.get("category").toString(),
                dataDrive.get("field").toString(),
                dataDrive.get("operator").toString(),
                dataDrive.get("value1").toString(),
                dataDrive.get("value2").toString());
        applicationRecordPage.searchButton.click();

        WebDriverWait wait = new WebDriverWait(getDriver(), 10);
        wait.until(ExpectedConditions.or(
                ExpectedConditions.elementToBeClickable(applicationRecordPage.applicationAppResultsTitle),
                ExpectedConditions.elementToBeClickable(applicationRecordPage.applicationCategory)));
        //waitFor(applicationRecordPage.applicationCategoryOrAppResultsTitle).waitUntilVisible();
    }

    @And("^I reassign \"([^\"]*)\" applications from Application Results Page to \"([^\"]*)\" team$")
    public void iReassignApplicationsFromApplicationResultsPageToTeam(String count, String team) {
        waitFor(applicationRecordPage.tableFirstColumn);
        WebDriverWait wait = new WebDriverWait(getDriver(), 10);
        waitFor(applicationRecordPage.selelctAllCheckbox);
        applicationRecordPage.selelctAllCheckbox.click();
        wait.until(ExpectedConditions.textToBePresentInElement(applicationRecordPage.badgeCount, count));
        applicationRecordPage.clickReviewButton();
        applicationRecordPage.reassignButton.waitUntilEnabled();
        applicationRecordPage.reassignButton.click();
        applicationRecordPage.enterSelectUserId(team);
        applicationRecordPage.reassignButtonInReassignPopup.click();
        if (applicationRecordPage.messageBanner.isVisible())
            waitABit(2000);
    }

    @And("^I verify the (\\d+) applications have been reassigned$")
    public void iVerifyTheApplicationsHaveBeenReassigned(int times) throws IOException, ParseException {
        for (int index = 1; index <= times; index++) {
            recentPage.clickRecentLink();
            recentPage.clickRecentApplicationsTab();
            JSONObject dataDrive = scenarioContext.getDataDriveSectionAsJSON("Application" + index);
            recentPage.doubleClickApplicationRecord(dataDrive.get("Application Number").toString());
            dataDrive = scenarioContext.getDataDriveSectionAsJSON("reassignDetails");
            applicationRecordPage.isApplicationRowExists("Application", "Fraud Alert Team", dataDrive.get("Fraud Alert Team").toString());
        }
    }

    @And("^I search Application by adding search parameter$")
    public void iSearchApplicationByAddingSearchParameter() {
        applicationMaintenancePage.addSearchParameterAndSearch();

    }

    @And("^I select multiple applications and view one$")
    public void iSelectMultipleApplicationsAndViewOne() {
        applicationMaintenancePage.selectMultipleAppAndViewOne();
    }

    @And("^I Verify the navigation to Application Record screen$")
    public void iVerifyTheNavigationToApplicationRecordScreen() {
        applicationMaintenancePage.verifyApplicationRecordHeader();
    }

    @Then("^I review and verify the navigation to Match Review screen$")
    public void iReviewAndVerifyTheNavigationToMatchReviewScreen() {
        applicationMaintenancePage.reviewAndVerifyMatchReviewHeader();
    }

    @Then("^I Verify the navigation to Watchlist Record screen$")
    public void iVerifyTheNavigationToWatchlistRecordScreen() {
        applicationMaintenancePage.verifyWatchlistRecordHeader();
    }

    @And("^i click on Link Analysis$")
    public void iClickOnLinkAnalysis() {
        MatchReviewPage.click_Link_Analysis();
    }

    @And("^I compare Results in Link Analysis page$")
    public void iCompareResultsInLinkAnalysisPage() throws IOException, ParseException {
        MatchReviewPage.get_Canvas_Details();
    }

    @Given("^I connect to database and execute \"([^\"]*)\" file$")
    public void iConnectToDatabaseAndExecuteFile(String RequiredFileName) throws Throwable {
        dataBaseClient.executeScriptUsingScriptRunner(RequiredFileName);
    }

    @And("^I Verify the fields \"([^\"]*)\" displayed in application category$")
    public void iVerifyTheFieldsDisplayedInApplicationCategory(String fieldName) throws Throwable {
        applicationMaintenancePage.verifyApplicationRestrictedFields(fieldName);

    }

    @And("^I Verify the fields \"([^\"]*)\" displayed in applicant category$")
    public void iVerifyTheFieldsDisplayedInApplicantCategory(String fieldName) throws Throwable {
        applicationMaintenancePage.verifyApplicantRestrictedFields(fieldName);
    }

    @And("^I approve the updated watchlist$")
    public void iApproveTheUpdatedWatchlist() {
        applicationRecordPage.approveUpdatedWatchlist();
    }

    @And("^I delete categories: \"([^\"]*)\"$")
    public void iDeleteCategories(String source) throws IOException, ParseException {
        waitABit(2000);
        String[] categories = source.split(",");
        for (String item : categories) {
            // delete Details
            applicationRecordPage.deleteApplicationCategory(item);
        }
        waitABit(2000);
        applicationRecordPage.clickOnUpdateOnReviewDropdown();

        waitABit(2000);

    }


    @And("^I verify deleted category on notes page$")
    public void iVerifyDeletedCategoryOnNotesPage() {
        applicationRecordPage.clickOnNotesButton();
        applicationRecordPage.verifyDeleteCategory();

    }


    @And("^I verify deleted category: \"([^\"]*)\" on update review page$")
    public void iVerifyDeletedCategoryOnUpdateReviewPage(String source) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        applicationRecordPage.verifyUpdateReviewContent();
        String[] categories = source.split(",");
        for (String item : categories) {

            boolean details = applicationRecordPage.verifyCategoryDeleted(item);
            if (!details) {
                Assert.fail("Category is not displayed");
            }

        }
    }

    @Given("^I submit \"([^\"]*)\" application request using Fraud Check WS$")
    public void iSubmitApplicationRequestUsingFraudCheckWS(int count) throws IOException, ParseException {
        JSONObject dataDrive = scenarioContext.getDataDriveSectionAsJSON("fcwsRequest");
        for (int i = 1; i <= count; i++)
            fraudCheckWS.sendRequest(dataDrive.get("fcwsRequest" + i).toString());
    }

    @And("^I add another security on the application record page having \"([^\"]*)\" and \"([^\"]*)\"$")
    public void iAddAnotherSecurityOnTheApplicationRecordPageHavingAnd(String data, String data1) throws Throwable {
        applicationMaintenancePage.addSecuritySecond(data, data1);

    }

    @And("^I verify Value Matches Tab$")
    public void iVerifyValueMatchesTab() throws IOException, ParseException {
        Assert.assertTrue("Value Matches tab result not as expected.", applicationRecordPage.verifyValueMatchesTab());

    }

    @Then("^I verify the application record details$")
    public void iVerifyTheApplicationRecordDetails() throws IOException, ParseException {
        JSONObject json = scenarioContext.getDataDriveAsJSON();
        String[] categories = json.get("verifyApplicationCategories").toString().split(",");
        for (String category : categories) {
            String[] categoriesMapping = category.split(":");
            JSONObject requestBody = scenarioContext.getDataDriveSectionAsJSON(categoriesMapping[1]); //application:application1

            Set<Map.Entry> entries = requestBody.entrySet();

            applicationRecordPage.selectCategory1(categoriesMapping[0]);
            for (Map.Entry entry : entries) {
                // Enter Details
                applicationRecordPage.verifyApplicationDetails(entry.getKey().toString(), entry.getValue().toString());
            }
        }
    }

    @Then("^I verify the Watchlist record details$")
    public void i_verify_the_Watchlist_record_details() throws IOException, ParseException {
        JSONObject json = scenarioContext.getDataDriveAsJSON();
        String[] categories = json.get("verifyApplicationCategories").toString().split(",");
        for (String category : categories) {
            JSONObject requestBody = scenarioContext.getDataDriveSectionAsJSON(category);
            Set<Map.Entry> entries = requestBody.entrySet();

            applicationRecordPage.selectCategory1(category);
            for (Map.Entry entry : entries) {
                // Enter Details
                applicationRecordPage.verifyApplicationDetails(entry.getKey().toString(), entry.getValue().toString());
            }
        }
    }

    @And("^I look for value \"([^\"]*)\" in Application Maintenance Page is not Restricted$")
    public void iLookForValueInApplicationMaintenancePageIsNotRestricted(String value) {
        waitABit(1000);
        assert (getDriver().findElement(By.xpath("//span[contains(text(), '" + value + "')]")).isDisplayed());
        assert (getDriver().findElement(By.xpath("//span[contains(text(), '<<Restricted>>')]")).isDisplayed());
    }

    @And("^I verify all footer button exist")
    public void iVerifyAllButtonAtFooterExist() {
        applicationRecordPage.verifyAllFooterButtonExist();
    }

    @And("^I verify all info in application valid")
    public void iVerifyAllInfoInApplicationValid() throws IOException, ParseException {
        applicationRecordPage.verifyAllInfoInApplication();
    }

    @And("^I verify rule \"([^\"]*)\" appears on mouse hover with the matched application$")
    public void iVerifyRuleAppearsOnMouseHoverWithTheMatchedApplication(String ruleCode) {
        String actualRule = matchReviewPage.getRuleCodeOnMouseHover();
        Assert.assertTrue("Incorrect rule displayed on hover", actualRule.contains(ruleCode));
    }

    @And("^I saw value for \"([^\"]*)\" in Application Record Page is \"([^\"]*)\"$")
    public void iLookForValueInApplicationRecordPageIsNotRestricted(String fieldName, String value) {
        WebDriverWait wait = new WebDriverWait(getDriver(), 10);
        wait.until(ExpectedConditions.elementToBeClickable(applicationRecordPage.applicationCategory));
        assert (getDriver().findElement(By.xpath("//td/div[contains(text(),'" + fieldName + "')]/../following-sibling::td//input")).getAttribute("value").equals(value));
    }

    @And("^I saw Field Name \"([^\"]*)\" in Application Record Page is in \"([^\"]*)\" color$")
    public void iSawFieldNameInApplicationRecordPageIsIn(String fieldName, String color) {
        assert (getDriver().findElement(By.xpath("//div[normalize-space(text())='" + fieldName + "']")).getCssValue("color").equals(color));
    }

    @And("^I select the application to perform Batch Action$")
    public void iSelectTheApplicationToPerformBatchAction() {
        applicationMaintenancePage.performBatchAction();
    }

    @And("^I add Dairy Note for the application selected from action page$")
    public void iAddDairyNoteForTheApplicationSelectedFromActionPage() {
        applicationMaintenancePage.addNoteFirstApplication();
        applicationMaintenancePage.addNoteSecondApplication();
    }

  /*  @Then("^I verify the \"([^\"]*)\" in application notes$")
    public void iVerifyTheInApplicationNotes(String batchNotes) throws Throwable {
        applicationMaintenancePage.verifyBatchNotes(batchNotes);
    }*/

    @Then("^I verify the \"([^\"]*)\" in \"([^\"]*)\" and \"([^\"]*)\" application notes$")
    public void iVerifyTheInAndApplicationNotes(String batchNotes, String applicationOne, String applicationTwo) throws Throwable {
        applicationMaintenancePage.verifyBatchNotes(batchNotes,applicationOne,applicationTwo);
    }

}