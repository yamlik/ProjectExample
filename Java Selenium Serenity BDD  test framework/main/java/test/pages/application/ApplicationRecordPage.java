package com..test.pages.application;

import com..test.context.constants.ApplicationConstants;
import com..test.context.ScenarioContext;
import com..test.database.DataBaseClient;
import com..test.pages.BasePage;
import net.serenitybdd.core.annotations.findby.By;
import net.serenitybdd.core.annotations.findby.FindBy;
import net.serenitybdd.core.pages.WebElementFacade;
import org.jetbrains.annotations.NotNull;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.junit.Assert;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.Color;
import org.openqa.selenium.support.ui.ExpectedConditions;


import java.io.IOException;
import java.io.PrintStream;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ApplicationRecordPage extends BasePage {


    DataBaseClient dataBaseClient = new DataBaseClient();

    public String applicationNumber;

    public String fraudScoreValue;

    @FindBy(css = ".CustomTable")
    private WebElementFacade tableRoot;

    @FindBy(className = "table custom-table")
    private WebElementFacade tableRoot1;

    @FindBy(xpath = "//thead//tr//th")
    private List<WebElement> tableColumns;

    @FindBy(xpath = "//tbody//tr")
    private List<WebElement> tableRows;

    @FindBy(xpath = "//tbody//tr/td[2]")
    public WebElementFacade tableFirstColumn;

    @FindBy(className = "itemKeyList")
    private WebElementFacade applicationRoot;

    @FindBy(className = "itemMain")
    private WebElementFacade categoryRoot;

    @FindBy(xpath = "//button[text()='Save'] | //button[text()='Submit']| //button[text()='Update']")
    private WebElementFacade saveButton;

    @FindBy(xpath = "//button[text()='Submit']")
    private WebElementFacade submitButton;

    @FindBy(xpath = "//button[text()='Reassign']")
    public WebElementFacade reassignButton;

    @FindBy(xpath = "//ngb-modal-window//button[text()='Reassign']")
    public WebElementFacade reassignButtonInReassignPopup;

    @FindBy(xpath = "//ng-select[@id='UserId']")
    public WebElementFacade selectUserIdInReassignPopup;


    @FindBy(xpath = "//button[text()='View']")
    public WebElementFacade viewButton;

    @FindBy(xpath = "//button-drop-down//button[text()='Action']")
    private WebElementFacade actionButton;

    @FindBy(xpath = "//button[text()='Update']")
    private WebElementFacade updateButton;

    @FindBy(xpath = "//button[contains(text(),'Review')]")
    private WebElementFacade reviewButton;

    @FindBy(xpath = "//input[@id='Comment']")
    private WebElementFacade commentBox;

    @FindBy(xpath = "//div[@class='button-box']//button[text()='Submit']| //button[contains(text(),'Reject')]")
    private WebElementFacade submitComment;

    @FindBy(xpath = "//span[contains(text(),'Pending')]| //a[contains(text(),'Pending')]")
    private WebElementFacade pendinglink;

    @FindBy(xpath = "//h2[contains(text(),'Users')] | //h2[contains(text(),'Watchlist')]")
    private WebElementFacade usersheader;

    @FindBy(xpath = "//tr//td[contains(text(),'')]//following::td//app-ellipsis-menu//..")
    private WebElementFacade applicationEllipsis;

    @FindBy(xpath = "(//img[@class='loadmore'])[1]|(//div[@class='ellipse-img-wrapper dropdown-toggle'])[1]")
    private WebElementFacade watchlistEllipsis;

    @FindBy(xpath = "//li[contains(text(),'Approve')]")
    private WebElementFacade approveApplication;

    @FindBy(xpath = "//li[contains(text(),'Approve')]")
    private WebElementFacade approveApplicationUpdate;

    @FindBy(id = "record-control-1-1")
    private WebElementFacade organisation;

    @FindBy(xpath = "//button[contains(text(),'Fraud Check')]")
    private WebElementFacade fraudCheck;

    @FindBy(xpath = "//button[contains(text(),'Update')]")
    private WebElementFacade update;

    @FindBy(xpath = "//button[contains(text(),'Auto Check')]")
    private WebElementFacade AutoCheck;

    @FindBy(className = "alp-notification-sticky-button suTSNTss alert-dismissable")
    private WebElementFacade saveBanner;

    @FindBy(xpath = "//div[contains(@class,'alert-dismissable')]")
    public WebElementFacade messageBanner;

    @FindBy(css = ".-dropdown-dark")
    private WebElementFacade applicationMaintenanceDropdown;

    @FindBy(xpath = "//button[contains(@class,'dropdown-toggle')]")
    private WebElementFacade ReviewDropdown;

    @FindBy(xpath = "//button[contains(text(),'Clone')]")
    private WebElementFacade Clone;

    @FindBy(xpath = "//div[contains(text(),'Application Type')]/following-sibling::div")
    private WebElementFacade applicationType;

    @FindBy(xpath = "//div[contains(text(),'Application Date')]/following-sibling::div")
    private WebElementFacade applicationDate;

    @FindBy(xpath = "//div[contains(text(),'Fraud Score')]/following-sibling::div")
    private WebElementFacade fraudScore;

    @FindBy(xpath = "//div[contains(text(),'Fraud Alert')]/following-sibling::div")
    private WebElementFacade fraudAlert;

    @FindBy(xpath = "//*[@id=\"header-title\"]/h2")
    private WebElementFacade appNum;

    @FindBy(xpath = "//h4[contains(text(),'Application')]")
    public WebElementFacade applicationCategory;

    @FindBy(xpath = "//h2[contains(text(),'Application Maintenance Results')]")
    public WebElementFacade applicationAppResultsTitle;

    @FindBy(xpath = "//li[@class='-nav-item disabled']")
    private WebElementFacade matcheTab;

    @FindBy(xpath = "//div[contains(text(),'Applicant.Date of Birth')]//following::button[1]")
    private WebElementFacade verify_Matches_Content_DOB;

    //div[contains(text(),'Applicant.Date of Birth')]//following::button[1]
    @FindBy(xpath = "//div[contains(text(),'Applicant.First Name')]//following::button[1]")
    private WebElementFacade verify_Matches_Content_FirstName;

    @FindBy(xpath = "(//div[@class='tableWrap ref-review-wrapper']//following::td)[5]")
    private WebElementFacade verify_Matches_Content_Name;

    @FindBy(xpath = "(//div[@class='tableWrap ref-review-wrapper']//following::td)[13]")
    private WebElementFacade verify_Matches_DOB;

    @FindBy(xpath = "//div[contains(text(),'Applicant.Surname')]//following::button[1]")
    private WebElementFacade verify_Matches_Content_SurName;

    @FindBy(xpath = "(//div[@class='tableWrap ref-review-wrapper']//following::td)[7]")
    private WebElementFacade verify_Matches_Content_Last_Name;

    @FindBy(xpath = "//a[contains(text(),'Value Matches (1)')]")
    private WebElementFacade valueMatcheTab;

    @FindBy(xpath = "//a[contains(text(),'Full Application Details')]")
    private WebElementFacade fullAppDetailsTab;

    @FindBy(xpath = "//button[text()=\"Close\"]")
    private WebElementFacade close;

    @FindBy(xpath = "///a[contains(text(),'Full Application Details')]")
    private WebElementFacade AppDetailsTab;

    @FindBy(xpath = "//div[contains(text(),'Rule Triggered Category')]/../following-sibling::div/div")
    private WebElementFacade ruleTriggerCatMsg;

    @FindBy(xpath = "(//div[contains(text(),'Applicant.Home Address 5')]/../following-sibling::div/div)[1]/div")
    private WebElementFacade homeAdd;

    @FindBy(xpath = "(//div[contains(text(),'Applicant.Home Address 5')]/../following-sibling::div/div)[4]/div")
    private WebElementFacade companyAdd;

    @FindBy(xpath = "//button[contains(text(),'System')]")
    private WebElementFacade SystemTab;

    @FindBy(xpath = "//tbody/tr[1]/td[3]")
    private WebElementFacade applicationValue;

    @FindBy(xpath = "//tbody/tr[1]/td[4]")
    private WebElementFacade dataBaseValue;

    @FindBy(xpath = "//div[contains(text(),'Category')]/following::span[@class='ng-arrow-wrapper']")
    private WebElementFacade categoryDropdown;

    @FindBy(xpath = "//span[contains(text(),'Category')]")
    private WebElementFacade category;

    @FindBy(xpath = "(//button[@class='alp-bonito-button-default filter-operator-dynamic dropdown-toggle'])[1]/span")
    private WebElementFacade selectedCategory;

    @FindBy(xpath = "//button[@class='alp-bonito-button-default filter-operator-dynamic dropdown-toggle']")
    private WebElementFacade application1Button;

    @FindBy(xpath = "//h2[text()='New Application']")
    private WebElementFacade newApplicationHeader;

    @FindBy(xpath = "//h2[contains(text(),'Application')] | //h2[contains(text(),'Watchlist')]")
    public WebElementFacade applicationHeader;

    @FindBy(xpath = "//span[text()='Applicant']|//span[text()='Credit Bureau']|//span[text()='CBC']")
    private WebElementFacade selectCategory;

    @FindBy(xpath = "//span[text()='Applicant']")
    private WebElementFacade applicantDropdownValue;

    @FindBy(xpath = "//td//span[@class='rule-open']")
    private WebElementFacade rulesOpen;

    @FindBy(xpath = "//div[@class='col-9 rule-desc-content']//div")
    private WebElementFacade applicationField;

    @FindBy(xpath = "//div[@class='col-9 rule-desc-content']//div//following-sibling::div//following::div")
    private WebElementFacade matchedField;

    @FindBy(xpath = "//div[@class='label-text']")
    private WebElementFacade ruleLabel;

    @FindBy(xpath = "//div[@class='row content-row']/div[2]/div[1]/div")
    private WebElementFacade firstValue;

    @FindBy(xpath = "//div[@class='row content-row']/div[2]/div[2]/div")
    private WebElementFacade secondValue;

    @FindBy(xpath = "//div[@class='rule-count']")
    private WebElementFacade verifyRuleUnderRulesContent;

    @FindBy(xpath = "//div[@class='row content-row']/div[2]/div[3]/div")
    private WebElementFacade thirdValue;

    @FindBy(xpath = "//div[@class='row content-row']/div[2]/div[4]/div")
    private WebElementFacade fourthValue;

    @FindBy(xpath = "//div[3][@class='row content-row']/div[2]/div[1]/div")
    private WebElementFacade incomeValue;

    @FindBy(xpath = "//div[3][@class='row content-row']/div[2]/div[4]/div")
    private WebElementFacade incomeLimit;

    @FindBy(xpath = "//span[contains(text(),'Category')]")
    private WebElementFacade categoryHeading;

    @FindBy(xpath = "//a[contains(text(),'Application 1')]")
    private WebElementFacade application1;

    @FindBy(xpath = "//h2[contains(text(),'Application Number')]")
    private WebElementFacade applicationNumberHeader;

    @FindBy(xpath = "//div[@class='record-tab-tab-container']/../following::div[2]/div/span")
    private WebElementFacade plusButton;

    @FindBy(xpath = "//li//div[contains(@class,'stat-wrapper')]")
    private WebElementFacade tabName;

    @FindBy(xpath = "//h2[contains(text(),'Watchlist')]")
    private WebElementFacade newWatchlistHeader;

    @FindBy(xpath = "//button[@class='actionButtons alp-bonito-button-dark']")
    private WebElementFacade reviewApplication;

    @FindBy(xpath = "//div[text()='Criteria']/following::div/input")
    private WebElementFacade enterCriteria;

    //APPLICATION MAINTENANCE SEARCH
    @FindBy(xpath = "//div[@class='add-rule-link linkstyle']")
    private WebElementFacade addSearchParameter;

    @FindBy(xpath = "//div[@class='field-requirement-content']/div[last()-1]//ng-select[contains(@id,'fields')]//input")
    private WebElementFacade searchCategoryDropDown;

    @FindBy(xpath = "//div[@class='field-requirement-content']/div[last()-1]//ng-select[contains(@bindvalue,'FieldNumber')]//input")
    private WebElementFacade searchFieldDropDown;

    @FindBy(xpath = "//div[@class='field-requirement-content']/div[last()-1]//*[contains(local-name(), 'app-record-control')]/div/div[1]//ng-select")
    private WebElementFacade searchOperatorDropDown;

    //@FindBy(xpath = "//div[@class='field-requirement-content']/div[last()-1]//*[contains(local-name(), 'app-record-control')]/div/div[1]//input")

    @FindBy(xpath = "//div[@class='field-requirement-content']/div[last()-1]//*[contains(local-name(), 'app-record-control')]/div/div[2]//input[1]")
    private WebElementFacade searchValueField1;

    @FindBy(xpath = "//div[@class='field-requirement-content']/div[last()-1]//*[contains(local-name(), 'app-record-control')]/div/div[2]//input[2]")
    private WebElementFacade searchValueField2;

    @FindBy(xpath = "(//div[text()='Category']/following::div/ng-select[1]/div/span)[1]")
    private WebElementFacade criteriaCategoryDropdown;

    @FindBy(xpath = "(//div[text()='Field']/following::div/ng-select[1]/div/span)[1]")
    private WebElementFacade fieldDropdown;

    @FindBy(xpath = "(//div[text()='Operator']/following::div/ng-select[1]/div/span)[1]")
    private WebElementFacade operatorDropdown;

    @FindBy(xpath = "//div[text()='Value']/following::textarea")
    private WebElementFacade valueTextarea;

    @FindBy(xpath = "(//div[text()='Criteria']/following::div/ng-select[1]/div/span)[2]")
    private WebElementFacade criteriaDropdown;

    @FindBy(xpath = "//button[contains(text(),'Search')]")
    public WebElementFacade searchButton;

    @FindBy(xpath = "//span[text()='Investigation']/..")
    private WebElementFacade sidebarInvestigation;

    @FindBy(xpath = "//span[text()='Definitions']/..")
    private WebElementFacade sidebarDefinitions;

    @FindBy(xpath = "//span[text()='Watchlist']/..")
    private WebElementFacade sidebarWatchlist;

    @FindBy(xpath = "(//img[@class='loadmore'])[last()]")
    private WebElementFacade selectCriteria;

    @FindBy(xpath = "(//li[text()='Delete'])[last()]")
    private WebElementFacade deleteCriteria;

    @FindBy(xpath = "//button[contains(text(),'Add')]")
    private WebElementFacade addButton;

    @FindBy(xpath = "//button[contains(text(),'Yes')]")
    private WebElementFacade yesButton;

    @FindBy(xpath = "//button[contains(text(),'OK')]")
    private WebElementFacade okButton;

    @FindBy(xpath = "(//div[@class='match-review-descp'])[3]")
    private WebElementFacade fraudScoreHeader;

    @FindBy(xpath = "//button[contains(text(),'Filter')]")
    private WebElementFacade filterApplication;

    @FindBy(xpath = "//div[contains(text(),'Application Number')]//following::div//div//div//following::div//input")
    private WebElementFacade filterApplicationNumberBox;

    @FindBy(xpath = "//button[contains(text(),'Apply')]")
    private WebElementFacade applyFilterButton;

    @FindBy(xpath = "//button[contains(text(),'Auto Check')]")
    private WebElementFacade autoCheckButton;

    @FindBy(xpath = "//span[@class='badge-count']")
    public WebElementFacade badgeCount;

    //APPLICATION MAINTENANCE RESULTS PAGE
    @FindBy(xpath = "//div[@class='table-title-checkbox']")
    public WebElementFacade selelctAllCheckbox;

    @FindBy(xpath = "//button[contains(text(),'View')]")
    private WebElementFacade viewApplication;

    @FindBy(xpath = "//button[@class='-dropdown-dark dropdown-toggle'] | //button[@class='dropdown-toggle -dropdown-dark'] ")
    private WebElementFacade toggleDropdown;

    @FindBy(xpath = "//button[contains(text(),'Delete')]")
    private WebElementFacade delete;

    @FindBy(xpath = "//span[@class='delete-sequence']/img")
    private WebElementFacade deleteCategory;


    //BREADCRUMBS
    @FindBy(xpath = "//div[@class='breadcrumb-box']//a[text()='Watchlist Record']")
    private WebElementFacade watchlistRecordBreadCrumb;

    //NOTE POPUP PAGE
    @FindBy(xpath = "//span[contains(text(),'Note')]")
    private WebElementFacade notesButton;

    @FindBy(xpath = "//img[@class='diary-options dropdown-toggle'] | //img[@class='dropdown-toggle diary-options']")
    private WebElementFacade notesDotsButton;


    @FindBy(xpath = "//img[@class='']")
    private WebElementFacade notesRecordUpdatedDots;

    @FindBy(xpath = "//a[contains(text(),'Close')]")
    private WebElementFacade notesCloseButton;

    @FindBy(xpath = "//a[text()='Add']")
    private WebElementFacade notesAddButton;

    @FindBy(xpath = " //img[@class='loadmore']")
    private WebElementFacade notesLoadMore;

    @FindBy(xpath = "//div[@class='card-btm-btn diary-note-buttonGroup']//button")
    private WebElementFacade saveNoteButton;

    @FindBy(xpath = "//li[contains(text(),'Note')]")
    private WebElementFacade matchesNotesDropDown;

    @FindBy(xpath = "(//li[contains(text(),'Update Review')])[last()]")
    private WebElementFacade updateReviewNoteLast;

    @FindBy(xpath = "//ng-select[contains(@formcontrolname,'DiaryPrompt')]//input")
    private WebElementFacade promptDropDown;

    @FindBy(xpath = "//ng-select[contains(@formcontrolname,'DiaryPrompt')]//input")
    private WebElementFacade noteRecordOnNotePopup;

    @FindBy(xpath = "//ng-select[contains(@bindvalue,'Code')]")
    private WebElementFacade categoryUpdateReviewListDropdown;

    @FindBy(xpath = "//ng-dropdown-panel//div[contains(@class,'ng-option')]")
    private WebElementFacade categoryUpdateReviewList;

    @FindBy(xpath = "//button[contains(text(), 'Toggle')]")
    private WebElementFacade toggleButton;

    @FindBy(xpath = "//button[contains(text(), 'Match History')]")
    private WebElementFacade matchHistoryButton;

    @FindBy(xpath = "//button[contains(text(), 'Machine Learning History')]")
    private WebElementFacade machineLearningHistoryButton;

    @FindBy(xpath = "//button[contains(text(), 'Link')]")
    private WebElementFacade linkButton;

    public void clickSave() {
        clickOn(saveButton);
    }

    public void clickSaveOnApplicationRecord() {
        clickOn(saveButton);
        waitFor(messageBanner).waitUntilVisible();

    }

    public void clickSaveOnWatchlistRecord() {
        clickOn(saveButton);
    }

    public void clickActionButton() {
        clickOn(actionButton);
    }
    public void clickSubmit() {
        clickOn(submitButton);
    }

    public void clickReviewButton()  {
        waitFor(ReviewDropdown).waitUntilClickable();
        clickOn(ReviewDropdown);
    }

    public void clickWatchlistRecordBreadCrumb() {
        clickOn(watchlistRecordBreadCrumb);
    }

    public void waitForBannerToDisappear() {
        waitFor(saveBanner).waitUntilNotVisible();
    }

    public void clickAutoCheckButton()  {
        clickReviewButton();
        waitFor(autoCheckButton);
        clickOn(autoCheckButton);
    }

    public void clickMainReviewButton() {
        clickOn(reviewButton);
        waitABit(3000);
    }

    public void enterApplicationDetails(String field, String value) {
        value = value.replace("{{organisation}}", scenarioContext.getOrganisation());
        WebElement element = getCategoryFieldValueElement(field);
        waitFor(element);
        typeInto(element, value);
        element.sendKeys(Keys.ENTER);
    }

    public void verifyApplicationDetails(String field, String value) {
        value = value.replace("{{organisation}}", scenarioContext.getOrganisation());
        waitABit(1000);
        waitFor(appNum);
        String fieldValue;
        ((JavascriptExecutor) getDriver()).executeScript("scroll(0,110)");
        waitABit(500);
        if (field.contains("Company Name") || field.contains("Surname") || field.contains("First Name")) {
            fieldValue = getDriver().findElement(By.xpath("//td/div[contains(text(),'" + field + "')]/../following-sibling::td//textarea")).getAttribute("value");
        }
        else {
            fieldValue = getDriver().findElement(By.xpath("//td/div[contains(text(),'" + field + "')]/../following-sibling::td//input")).getAttribute("value");
        }
        if (fieldValue.isEmpty())
            fieldValue = getDriver().findElement(By.xpath("//td/div[contains(text(),'" + field + "')]/../following-sibling::td")).getAttribute("textContent");
        Assert.assertEquals("Application details not matching",value,fieldValue);
    }

    public void deleteApplicationCategory(String category) {
        List<WebElement> applicationCatLabel = categoryRoot.findElements(By.xpath("//*[@class='row']"));

        //Every "row" is expected to have "h4" - Category Label and "add-new-sequence" - the PLUS button

        for (WebElement webCategory : applicationCatLabel) {
            String categoryName = webCategory.findElement(By.xpath(".//h4")).getText();
            WebElement addCategoryExists = webCategory.findElement(By.className("tab-name"));
            try {
                if (categoryName.equalsIgnoreCase("Application") && category.contains("Application")) {
                    break;
                } else if (category.contains("Applicant") && webCategory.getText().equals("Applicant")) {

                    clickOn(webCategory);
                    clickOn(deleteCategory);
                    waitABit(3000);
                    break;
                } else if (category.contains("Introducer") && webCategory.getText().equals("Introducer/Agent")) {
                    clickOn(webCategory);
                    clickOn(deleteCategory);
                    break;

                } else if (category.contains("User") && webCategory.getText().equals("User")) {


                    clickOn(webCategory);
                    waitABit(3000);
                    clickOn(deleteCategory);
                    waitABit(3000);

                    break;
                } else if (category.contains("Credit Bureau") && webCategory.getText().equals("Credit Bureau")) {
                    waitABit(3000);
                    clickOn(webCategory);
                    waitABit(3000);
                    clickOn(deleteCategory);
                    break;
                }


            } catch (NullPointerException e) {
                clickOn(addCategoryExists);
                break;
            }

        }
    }


    public void selectCategory(String category) {
        List<WebElement> applicationCatLabel = categoryRoot.findElements(By.xpath("//*[@class='row']"));

        //Every "row" is expected to have "h4" - Category Label and "add-new-sequence" - the PLUS button
        for (WebElement webCategory : applicationCatLabel) {
            String categoryName = webCategory.findElement(By.xpath(".//h4")).getText();

            WebElement addCategoryExists = webCategory.findElement(By.className("tab-name"));
            try {
                if (categoryName.equalsIgnoreCase("Application") && category.contains("Application")) {
                    break;
                } else if (categoryName.contains(category)) {
                    WebElement addNewSequenceExists = webCategory.findElement(By.xpath("//div[@class='add-new-sequence']"));
                    clickOn(addNewSequenceExists);
                    break;
                } else if (category.contains(categoryName)) {
                    WebElement addNewSequenceExists = webCategory.findElement(By.xpath("//h4[contains(text(),'Applicant')]"));
                    clickOn(addNewSequenceExists);
                    break;
                } else if (category.contains("SecondApplicant")) {
                    WebElement plusButton = webCategory.findElement(By.xpath("//div[@class='record-tab-tab-container']/../following::div[2]/div/span"));
                    ((JavascriptExecutor) getDriver()).executeScript("scroll(0,100)");
                    waitABit(500);
                    clickOn(plusButton);
                    break;
                } else if (category.contains("Introducer")) {
                    WebElement addNewSequenceExists = webCategory.findElement(By.xpath("//h4[contains(text(),'Introducer')]//following::div//div"));
                    scrollToElement(addNewSequenceExists);
                    scrollToElement(categoryHeading);
                    clickOn(addNewSequenceExists);
                    break;
                } else if (category.contains("User")) {
                    WebElement addNewSequenceExists = webCategory.findElement(By.xpath("//h4[contains(text(),'User')]//following::div//div"));
                    scrollToElement(categoryHeading);
                    clickOn(addNewSequenceExists);
                    break;
                } else if (category.contains("Credit Bureau")) {
                    WebElement addNewSequenceExists = webCategory.findElement(By.xpath("//h4[contains(text(),'Credit Bureau')]//following::div//div"));
                    scrollToElement(categoryHeading);
                    clickOn(addNewSequenceExists);
                    break;
                } else if (category.contains("Security")) {
                    WebElement plusButton = webCategory.findElement(By.xpath("//h4[contains(text(),'Security')]//following::div//div"));
                    // ((JavascriptExecutor) getDriver()).executeScript("scroll(0,100)");
                    scrollToElement(categoryHeading);
                    waitABit(500);
                    clickOn(plusButton);
                    break;
                }


            } catch (NullPointerException | InterruptedException e) {
                clickOn(addCategoryExists);
                break;
            }
        }
    }

    public void selectNewCategory(String category) {

        List<WebElement> applicationCatLabel = categoryRoot.findElements(By.xpath("//*[@class='row']"));
        //Every "row" is expected to have "h4" - Category Label and "add-new-sequence" - the PLUS button
        for (WebElement webCategory : applicationCatLabel) {
            String categoryName = webCategory.findElement(By.xpath(".//h4")).getText();
            WebElement addCategoryExists = webCategory.findElement(By.className("tab-name"));
            try {
                if (categoryName.equalsIgnoreCase("Application") && category.contains("Application")) {
                    break;
                } else if (categoryName.contains(category) || category.contains(categoryName)) {
                    WebElement addNewSequenceExists = webCategory.findElement(By.xpath("//div[@class='add-new-sequence']"));
                    clickOn(addNewSequenceExists);
                    break;
                } else if (category.contains(categoryName)) {
                    WebElement addNewSequenceExists = webCategory.findElement(By.xpath("//h4[contains(text(),'Applicant')]"));
                    clickOn(addNewSequenceExists);
                    break;
                } else if (category.contains("SecondApplicant")) {
                    WebElement plusButton = webCategory.findElement(By.xpath("//div[@class='record-tab-tab-container']/../following::div[2]/div/span"));
                    ((JavascriptExecutor) getDriver()).executeScript("scroll(0,100)");
                    waitABit(500);
                    clickOn(plusButton);
                    break;
                } else if (category.contains("CBC")) {
                    waitABit(1000);
                    JavascriptExecutor jse = (JavascriptExecutor) getDriver();
                    jse.executeScript("window.scrollBy(0,250)");
                    waitABit(1000);
                    WebElement addNewSequenceExists = webCategory.findElement(By.xpath("//h4[contains(text(),'CBC')]//following::div//div"));
                    clickOn(addNewSequenceExists);
                    break;
                }

            } catch (NullPointerException e) {
                clickOn(addCategoryExists);
                break;
            }
        }
    }

    public void clickOnFraudCheckOnReviewDropdown() {
        waitABit(5000);
        waitFor(ExpectedConditions.visibilityOf(application1));
        clickOn(applicationMaintenanceDropdown);
        clickOn(fraudCheck);
        waitABit(2000);
        waitFor(ExpectedConditions.visibilityOf(applicationNumberHeader));

    }

    public void clickOnUpdateOnReviewDropdown() {
        clickOn(applicationMaintenanceDropdown);
        clickOn(update);
        clickOn(yesButton);
        clickOn(okButton);
        waitABit(3000);

    }

    public void verifySuTSNTssMessage() {
        waitABit(3000);
        String ExpectedMessage = "Saved suTSNTssfully.";
        String ActualMessage = getDriver().findElement(By.xpath("//span[@class='alert-message']")).getText();
        Assert.assertEquals(ExpectedMessage, ActualMessage);
    }

    public void clickOnAutoCheckOnReviewDropdown() {
        waitFor(ExpectedConditions.visibilityOf(application1));
        clickOn(applicationMaintenanceDropdown);
        clickOn(AutoCheck);

    }

    public void clickonSystemTab() {
        clickOn(SystemTab);
    }

    public void clickOnCloneOnReviewDropdown() throws InterruptedException {
        waitFor(ExpectedConditions.visibilityOf(category));
        waitABit(5000);
        waitFor(ExpectedConditions.visibilityOf(application1));
        clickOn(applicationMaintenanceDropdown);
        clickOn(Clone);
        waitFor(ExpectedConditions.visibilityOf(application1));
    }

    public boolean checkApplicationExists() {
        List<WebElement> application = applicationRoot.findElements(By.className("tab-name"));

        for (WebElement app : application) {
            if (app.getText().equalsIgnoreCase(applicationNumber)) {
                return true;
            }
        }
        return false;
    }

    public void verifyExtraFields(String field, String currentPage) {
        try {
            if (currentPage.equals("New Application"))
                getDriver().findElement(By.xpath("//div[contains(text(),'" + field + "')]"));
            else
                getDriver().findElement(By.xpath("//td[contains(text(),'" + field + "')]"));
        } catch (NoSuchElementException e) {
            Assert.fail(field + " field does not exist.");
        }
    }

    public void addNewApplicant() {
        clickOn(applicationMaintenanceDropdown);
    }

    public void checkApplicationValueMatchTab() throws IOException, ParseException {
        waitABit(1000);
        clickOn(verify_Matches_Content_SurName);
        String LastName = verify_Matches_Content_Last_Name.getText();
        JSONObject requestBody = ScenarioContext.getTSNTance().getDataDriveSectionAsJSON("verifyAppValuesFullApplicant");
        String Json_SurName = requestBody.get("Surname").toString();
        Assert.assertEquals(LastName, Json_SurName);
        waitABit(100);
        clickOn(close);
        waitABit(1001);
        clickOn(verify_Matches_Content_FirstName);
        waitABit(200);
        String Name = verify_Matches_Content_Name.getText();
        String Json_Name = requestBody.get("First Name").toString();
        Assert.assertEquals(Name, Json_Name);
        clickOn(close);
        clickOn(verify_Matches_Content_DOB);
        String Expected_DOB = verify_Matches_DOB.getText();
        String Json_DOB = requestBody.get("Date of Birth").toString();
        Assert.assertEquals(Expected_DOB, Json_DOB);
        clickOn(close);
        waitABit(1000);
    }

    public boolean checkApplicationValueMatchesTab() {
        boolean res = false;
        try {
            clickOn(valueMatcheTab);
            String homeAddress = homeAdd.getText().trim();
            String companyAddress = companyAdd.getText().trim();
            String[] s = ruleTriggerCatMsg.getText().split("-");
            String actualRule = s[0].trim();
            JSONObject expectedData = ScenarioContext.getTSNTance().getDataDriveSectionAsJSON("Applicant");
            if (ApplicationConstants.RuleTriggerCatMsg.contains(actualRule) && expectedData.get("Home Address 5").toString().contains(homeAddress) && expectedData.get("Company Address 5").toString().contains(companyAddress)) {
                res = true;
            }
        } catch (Exception e) {
            e.getMessage();
        }
        return res;
    }

    public boolean checkApplicationMatchesTab() {
        boolean res = false;
        try {
            if (matcheTab.isEnabled()) {
                res = true;
            }
        } catch (Exception e) {

            e.getMessage();
        }
        return res;
    }

    public boolean checkMatchesTabContent() {
        boolean res = false;
        try {
            if (matcheTab.isEnabled()) {
                res = true;
            }
        } catch (Exception e) {
            e.getMessage();
        }
        return res;
    }

    public boolean checkApplicationValueMatchesTab(String key, String input1, String input2) {
        boolean res = false;
        try {
            clickOn(valueMatcheTab);
            String expectedValue1 = firstValue.getText().trim();
            String expectedValue2 = fourthValue.getText().trim();
            String[] s = ruleTriggerCatMsg.getText().split("-");
            String actualRule = s[0].trim();
            JSONObject expectedData = ScenarioContext.getTSNTance().getDataDriveSectionAsJSON(key);
            if (ApplicationConstants.RuleTriggerCatMsg.contains(actualRule) && expectedData.get(input1).toString().contains(expectedValue1) && expectedData.get(input2).toString().contains(expectedValue2)) {
                res = true;
            }
        } catch (Exception e) {
            e.getMessage();
        }
        return res;
    }

    public boolean checkApplicationValueMatchesTab(String key, String input1) {
        boolean res = false;
        try {
            clickOn(valueMatcheTab);
            String expectedValue1 = firstValue.getText().trim();
            String expectedValue2 = fourthValue.getText().trim();
            String[] s = ruleTriggerCatMsg.getText().split("-");
            String actualRule = s[0].trim();
            JSONObject expectedData = ScenarioContext.getTSNTance().getDataDriveSectionAsJSON(key);
            if (ApplicationConstants.RuleTriggerCatMsgAL00001.contains(actualRule) && expectedData.get(input1).toString().contains(expectedValue1) && ApplicationConstants.LikeRuleCondition.contains(expectedValue2)) {
                res = true;
            }
        } catch (Exception e) {
            e.getMessage();
        }
        return res;
    }

    public boolean checkValueMatchesTab(String key, String input1, String input2) {
        boolean res = false;
        try {
            clickOn(valueMatcheTab);
            String expectedValue1 = firstValue.getText().trim();
            String expectedValue2 = fourthValue.getText().trim();
            String income = incomeValue.getText().trim();
            String limitIncome = incomeLimit.getText().trim();
            String[] s = ruleTriggerCatMsg.getText().split("-");
            String actualRule = s[0].trim();
            JSONObject expectedData = ScenarioContext.getTSNTance().getDataDriveSectionAsJSON(key);
            if (ApplicationConstants.RuleTriggerCatMsgDI00002.contains(actualRule) && expectedData.get(input1).toString().contains(expectedValue1) && ApplicationConstants.valueOnlyAgeCondition.equals(expectedValue2) && expectedData.get(input2).toString().equals(income) && ApplicationConstants.valueOnlyIncomeCondition.equals(limitIncome)) {
                res = true;
            }
        } catch (Exception e) {
            e.getMessage();
        }
        return res;
    }

    public boolean checkValueMatchesTabVariableMatch() {
        boolean res = false;
        try {
            clickOn(valueMatcheTab);
//            String expectedValue1 = firstValue.getText().trim();
            String expectedValue2 = thirdValue.getText().trim();
//            String income = incomeValue.getText().trim();
//            String limitIncome = incomeLimit.getText().trim();
            String[] s = ruleTriggerCatMsg.getText().split("-");
            String actualRule = s[0].trim();
            //  JSONObject expectedData = ScenarioContext.getTSNTance().getDataDriveSectionAsJSON(key);
            if (ApplicationConstants.RuleTriggerCatMsgU000004.contains(actualRule) && ApplicationConstants.variableMatchCondition.equals(expectedValue2)) {
                res = true;
            }
        } catch (Exception e) {
            e.getMessage();
        }
        return res;
    }

    public void clickOnNotesButton() {
        clickOn(notesButton);
        waitABit(2000);

    }

    public void verifyNotesSlideContent() {
        waitABit(2000);
        String firstElement = tableRoot.findElement(org.openqa.selenium.By.xpath("//span[contains(text(),'Workflow executed')]")).getText();
        String secondElement = tableRoot.findElement(org.openqa.selenium.By.xpath("//span[contains(text(),'Application fraud checked - Clean (ID00001)')]")).getText();
        String thirdElement = tableRoot.findElement(org.openqa.selenium.By.xpath("//span[contains(text(),'Record added')]")).getText();
        //  Assert.assertEquals(firstElement, ApplicationConstants.NotesFirstMessage);
        Assert.assertTrue("", firstElement.contains(ApplicationConstants.NotesFirstMessage));
        Assert.assertEquals(secondElement, ApplicationConstants.NotesSecondMessage);
        Assert.assertEquals(thirdElement, ApplicationConstants.NotesThirdMessage);
        clickNotesDotsButton();
        clickOn(notesCloseButton);
        waitABit(1000);

    }

    public void verifyDeleteCategory() {
        String applicantDeleted = tableRoot.findElement(org.openqa.selenium.By.xpath("//span[contains(text(),'Applicant deleted')]")).getText();
        String userDeleted = tableRoot.findElement(org.openqa.selenium.By.xpath("//span[contains(text(),'User deleted')]")).getText();
        String recordUpdated = tableRoot.findElement(org.openqa.selenium.By.xpath("//span[contains(text(),'Record updated')]")).getText();

        Assert.assertEquals(applicantDeleted, ApplicationConstants.NotesApplicantDeletedMessage);
        Assert.assertEquals(userDeleted, ApplicationConstants.NotesUserDeletedMessage);
        if (recordUpdated.equals("Record updated")) {
            clickOn(notesLoadMore);
            waitABit(3000);
            //clickOn();
        }


    }

    public void clickNotesDotsButton() {
        clickOn(notesDotsButton);
    }

    public void clickNotesAddButton() {
        clickOn(notesAddButton);
    }

    public void enterNotePrompt(String note) {
        promptDropDown.typeAndEnter(note);
    }

    public void clickSaveButtonOnNotes() {
        clickOn(saveNoteButton);
    }

    public void verifyNotesBeingAdded(@NotNull JSONObject noteDetails) throws SQLException {
        waitFor(messageBanner).waitUntilNotVisible();
        for (int i = 1; i <= noteDetails.size() / 2; i++) {
            WebElement note =
                    getDriver().findElement(By.xpath("//div[@class='list-diary-wrapper']//div[@class='card no-border'][" + i + "]//li[1]"));
            Assert.assertEquals("Note not as expected. ", note.getText().toUpperCase(), noteDetails.get("Note Row " + i).toString().toUpperCase());

            WebElement noteUserAndDetails =
                    getDriver().findElement(By.xpath("//div[@class='list-diary-wrapper']//div[@class='card no-border'][" + i + "]//li[2]/span"));
            Assert.assertEquals("Note not as expected. ", noteUserAndDetails.getText().toUpperCase(), noteDetails.get("Note Details " + i).toString().toUpperCase());

            WebElement noteTimestamp =
                    getDriver().findElement(By.xpath("//div[@class='list-diary-wrapper']//div[@class='card no-border'][" + i + "]//li[2]/h6"));

            if (noteDetails.get("Source").equals("Application"))
                dataBaseClient.executeSelect("select format(Diary_Date,'dd/MM/yyyy')+' at '+format(Diary_Time, 'HH:mm:ss') from A_Diary_Notes where Diary_Note='" + note.getText() + "'");
            if (noteDetails.get("Source").equals("Watchlist"))
                dataBaseClient.executeSelect("select format(Diary_Date,'dd/MM/yyyy')+' at '+format(Diary_Time, 'HH:mm:ss') from C_Diary_Notes where Diary_Note='" + note.getText() + "'");


            List<Map<String, String>> results = dataBaseClient.getResultSetAsMapList();
            Assert.assertTrue("Note datetime not as expected. ", results.toString().contains(noteTimestamp.getText()));

        }

    }

    public void verifyMatchesNotesSlideContent() {
        clickOn(notesLoadMore);
        clickOn(matchesNotesDropDown);
        waitABit(1000);
    }

    public void verifySlideContent() {
        waitABit(5000);
        String element = tableRoot.findElement(org.openqa.selenium.By.xpath("//span[contains(text(),'Record added')]")).getText();
        Assert.assertEquals(element, ApplicationConstants.NotesThirdMessage);

    }

    public void verifyNotesSlideFieldColor(String expectedColor) {
        try {
            WebElement applicationFieldColor = tableRoot.findElement(By.xpath("//span[@class='badge badge-pill criminal-background']"));
            String a = applicationFieldColor.getCssValue("background-color");
            String actualColor = Color.fromString(a).asHex();
            Assert.assertEquals(expectedColor, actualColor);
        } catch (Exception e) {
            e.getMessage();
        }
    }

    public void updateComment() {
        waitFor(ExpectedConditions.visibilityOf(commentBox));
        typeInto(commentBox, "Approved");
        clickOn(submitComment);
        waitABit(3000);
        waitFor(ExpectedConditions.visibilityOf(usersheader));
        waitABit(2000);
        waitFor(pendinglink).click();

    }

    public void approveWatchlist() {
        applicationEllipsis.click();
        waitABit(500);
        approveApplication.click();
        waitFor(messageBanner).waitUntilNotVisible();
    }


    public void updateWatchlist() {
        submitButton.click();
        waitFor(ExpectedConditions.visibilityOf(commentBox));
        typeInto(commentBox, "Updated");
        clickOn(submitComment);
        waitABit(3000);
    }

    public void isApplicationRowExists(String category, String field, String value) {
        WebElement fieldValue = null;
        waitFor(appNum);
        clickCategory(category);
        switch (field) {
            case "Decision Reason":
                fieldValue = getDriver().findElement(By.xpath("//td/div[contains(text(),'" + field + "')]/../following-sibling::td"));
                Assert.assertTrue(field + " Value not existing", fieldValue.getText().contains(value));
                break;
            case "Fraud Alert Team":
                fieldValue = getDriver().findElement(By.xpath("//td/div[contains(text(),'" + field + "')]/../following-sibling::td//input"));
                Assert.assertTrue(field + " Value not existing", fieldValue.getAttribute("value").contains(value));
                break;
        }

    }

    public WebElement getCategoryFieldValueElement(String field) {
        waitABit(1000);
        waitFor(appNum);
        WebElement fieldValue;
        ((JavascriptExecutor) getDriver()).executeScript("scroll(0,100)");
        waitABit(2000);
        if (field.contains("Company Name") || field.contains("Surname") || field.contains("First Name"))
            fieldValue = getDriver().findElement(By.xpath("//td/div[contains(text(),'" + field + "')]/../following-sibling::td//textarea"));
        else
            fieldValue = getDriver().findElement(By.xpath("//td/div[contains(text(),'" + field + "')]/../following-sibling::td//input"));
        return (fieldValue);
    }

    public void clickCategory(String category) {
        waitFor(applicationCategory);
        WebElement categoryElement = getDriver().findElement(By.xpath("//h4[contains(text(),'" + category + "')]"));
        try {
            clickOn(categoryElement);
        } catch (NoSuchElementException ex) {
            getDriver().findElement(By.xpath("//h4[contains(text(),'" + category + "')]/../following-sibling::div")).click();
        }

    }

    public void approveUpdateWatchlist() {
        clickOn(pendinglink);
        waitABit(1000);
        applicationEllipsis.click();
        waitABit(1000);
        approveApplicationUpdate.click();
        waitFor(messageBanner).waitUntilNotVisible();
    }

    public void approveUpdatedWatchlist() {
        clickOn(pendinglink);
        waitABit(1000);
        watchlistEllipsis.click();
        waitABit(1000);
        approveApplicationUpdate.click();
        waitFor(messageBanner).waitUntilNotVisible();
    }

    public void verifyWatchlist() throws IOException, ParseException {
        waitABit(5000);
        waitFor(ExpectedConditions.visibilityOf(newWatchlistHeader));
        JSONObject data = ScenarioContext.getTSNTance().getDataDriveSectionAsJSON("Application");
        String expectedData = data.get("Application Number").toString();
        String actualData = tabName.getText();
        Assert.assertEquals(expectedData.toLowerCase(), actualData.toLowerCase());

    }

    public void addDetailsOnCriteriaDefinitionPage() {
        try {
            JSONObject data = ScenarioContext.getTSNTance().getDataDriveSectionAsJSON("ApplicationData");
            String criteria = data.get("Criteria").toString();
            typeInto(enterCriteria, criteria);
            waitABit(1500);
            ((JavascriptExecutor) getDriver()).executeScript("scroll(0,400)");
            clickOn(criteriaCategoryDropdown);
            WebElement ele = getDriver().findElement(By.xpath("//span[contains(text(),'Application')]"));
            ele.click();
            waitABit(1500);
            clickOn(fieldDropdown);
            WebElement ele1 = getDriver().findElement(By.xpath("//div[contains(text(),'Application Type')]"));
            ele1.click();
            waitABit(1500);
            clickOn(operatorDropdown);
            waitABit(1500);
            WebElement ele2 = getDriver().findElement(By.xpath("//span[text()='=']"));
            ele2.click();
            waitABit(1500);
            ((JavascriptExecutor) getDriver()).executeScript("scroll(0,1000)");
            JSONObject data1 = ScenarioContext.getTSNTance().getDataDriveSectionAsJSON("ApplicationData");
            String value = data1.get("Value").toString();
            typeInto(valueTextarea, value);
            waitABit(1500);
            waitFor(addButton).click();
            waitABit(1500);
            clickOn(saveButton);
            waitABit(1500);
            clickOn(yesButton);
            waitABit(1500);

        } catch (Exception e) {
            e.getMessage();
        }
    }

    public void selectCriteriaAndSearch() {
        try {
            waitABit(1500);
            clickOn(criteriaDropdown);
            JSONObject value = ScenarioContext.getTSNTance().getDataDriveSectionAsJSON("ApplicationData");
            String Criteria = value.get("Criteria").toString();
            waitABit(500);
            WebElement ele = getDriver().findElement(By.xpath("//div[contains(text(),'" + Criteria + "')]"));
            ele.click();
            waitABit(500);
            clickOn(searchButton);
            waitABit(1500);
        } catch (Exception e) {
            e.getMessage();
        }
    }

    public void deleteCreatedCriteria() {
        try {
            waitABit(3000);
            waitFor(ExpectedConditions.visibilityOf(sidebarDefinitions));
            clickOn(sidebarDefinitions);
            waitABit(2000);
            clickOn(getDriver().findElement(By.xpath("//a[text()='Criteria']")));
            waitABit(2000);
            List<WebElement> applicationCatLabel = tableRoot.findElements(By.xpath("//table[@class='table CustomTable']/thead/following::tr"));
            int criteriaSize = applicationCatLabel.size();
            if (criteriaSize > 1) {
                waitFor(ExpectedConditions.visibilityOf(selectCriteria));
                waitABit(1000);
                clickOn(selectCriteria);
                waitFor(ExpectedConditions.visibilityOf(deleteCriteria));
                waitABit(1000);
                clickOn(deleteCriteria);
                waitFor(ExpectedConditions.visibilityOf(yesButton));
                waitABit(1000);
                clickOn(yesButton);
                waitABit(1000);
            }
        } catch (Exception e) {
            e.getMessage();
        }
    }

    public boolean verifyApplicationDetails(String arg) {
        Logger logger = Logger.getLogger(ApplicationRecordPage.class.getName());
        logger.setLevel(Level.WARNING);
        boolean res = false;
        try {
            waitABit(2000);
            List<WebElement> applicationCatLabel = tableRoot.findElements(By.xpath("//table[@class='table CustomTable']/thead/following::tr"));
            int criteriaSize = applicationCatLabel.size();
            for (int i = 1; i <= applicationCatLabel.size(); i++) {
                String uiApplicationValue = getDriver().findElement(By.xpath("(//table[@class='table CustomTable']/thead/following::tr)[" + i + "]/td[6]/app-string-cell/span")).getText().trim();
                JSONObject requestBody = ScenarioContext.getTSNTance().getDataDriveSectionAsJSON("ApplicationData");
                String value = requestBody.get("Value").toString();
                logger.log(Level.WARNING, "value1: " + uiApplicationValue + " ," + "value2 : " + value);
                System.out.println("value1: " + uiApplicationValue + " ," + "value2 : " + value);
                if (value.equals(uiApplicationValue)) {
                    Assert.assertEquals(uiApplicationValue, value);
                    res = true;
                }
            }
            if (arg.equalsIgnoreCase("Applications")) {
                clickOn(sidebarInvestigation);
                clickOn(getDriver().findElement(org.openqa.selenium.By.xpath("//button[contains(text(), 'Applications')]")));
                clickOn(getDriver().findElement(org.openqa.selenium.By.xpath("//a[contains(text(), 'Application Maintenance')]")));
                waitABit(2000);
                clickOn(searchButton);
            }
            if (arg.equalsIgnoreCase("Watchlist")) {
                clickOn(sidebarWatchlist);
                clickOn(getDriver().findElement(org.openqa.selenium.By.xpath("//a[contains(text(), 'Watchlist Maintenance')]")));
                waitABit(2000);
                clickOn(searchButton);
            }
            List<WebElement> applicationManRes = tableRoot.findElements(By.xpath("//table[@class='table CustomTable']/thead/following::tr"));
            int appManSize = applicationManRes.size();
            for (int i = 1; i <= applicationManRes.size(); i++) {
                String uiApplicationValue = getDriver().findElement(By.xpath("(//table[@class='table CustomTable']/thead/following::tr)[" + i + "]/td[6]/app-string-cell/span")).getText().trim();
                JSONObject requestBody = ScenarioContext.getTSNTance().getDataDriveSectionAsJSON("ApplicationData");
                String value = requestBody.get("Value").toString();
                System.out.println("value1: " + uiApplicationValue + " ," + "value2 : " + value);
                if (value.equals(uiApplicationValue)) {
                    Assert.assertEquals(uiApplicationValue, value);
                    res = true;
                }
            }
            WebElement rowLabel = getDriver().findElement(By.xpath("//label[@for='rowtitle']"));
            rowLabel.click();
            String rowCount = badgeCount.getText();
            Assert.assertEquals(Integer.toString(criteriaSize), rowCount);

        } catch (Exception e) {
            e.getMessage();
        }
        return res;
    }

    public boolean verifyAppHeader(String arg) {
        boolean res = false;
        try {
            waitFor(fraudScoreHeader);
            waitFor(ExpectedConditions.visibilityOf(fraudScoreHeader));
            JSONObject requestBody = ScenarioContext.getTSNTance().getDataDriveSectionAsJSON("VerifyData");
            String actual = fraudScoreHeader.getText().toUpperCase().trim();
            switch (arg.toUpperCase()) {
                case "FRAUD SCORE":
                    String expected = requestBody.get("Value1").toString();
                    if (expected.equals(actual)) {
                        Assert.assertEquals(actual, expected);
                        res = true;
                    }
                    break;
                case "RULES SCORE":
                    String expected2 = requestBody.get("Value2").toString();
                    if (expected2.equals(actual)) {
                        Assert.assertEquals(actual, expected2);
                        res = true;
                    }
                    break;
                default:
                    throw new NoSuchElementException(" fraud score Header does not match");
            }
        } catch (Exception e) {
            e.getMessage();
        }
        return res;
    }

    public void searchAndReviewTheApplication() {
        waitABit(10000);
        waitFor(ExpectedConditions.elementToBeClickable(searchButton));
        clickOn(searchButton);
        waitABit(1500);
        waitFor(ExpectedConditions.visibilityOf(application1));
        WebElement element = getDriver().findElement(By.xpath("//button[@class='actionButtons alp-bonito-button-dark'] | //button[text()='Review']"));
        JavascriptExecutor executor = (JavascriptExecutor) getDriver();
        executor.executeScript("arguments[0]. click();", element);
        waitABit(1500);
    }

    public void review_Application() {
        waitFor(1500);
        waitFor(ExpectedConditions.visibilityOf(application1));
        WebElement element = getDriver().findElement(By.xpath("//button[@class='actionButtons alp-bonito-button-dark'] | //button[text()='Review']"));
        JavascriptExecutor executor = (JavascriptExecutor) getDriver();
        executor.executeScript("arguments[0]. click();", element);
        waitFor(1500);
    }

    public void searchApplication(String applicationNumber) {
        waitABit(2000);
        clickOn(filterApplication);
        waitFor(ExpectedConditions.visibilityOf(filterApplicationNumberBox));
        typeInto(filterApplicationNumberBox, applicationNumber);
        clickOn(applyFilterButton);
    }

    public void addComment() {
        waitFor(ExpectedConditions.visibilityOf(commentBox));
        typeInto(commentBox, "Approved");
        clickOn(submitComment);
    }

    public void deleteApplication(String applicationRowNumber) {
        WebElement applicationRow = getDriver().findElement(By.xpath("//tbody//tr[" + applicationRowNumber + "]"));
        applicationRow.click();
        waitABit(1000);
        viewApplication.click();
        waitABit(3000);
        waitFor(toggleDropdown).click();
        waitFor(delete).click();
        waitFor(yesButton).click();
        waitABit(5000);
    }

    public void deleteWatchlist() {
        waitABit(2000);
        waitFor(toggleDropdown).click();
        waitFor(delete).click();
        waitFor(yesButton).click();
        waitABit(5000);
    }

    public void addSearchParameter(String category, String field, String operator, String value1, String value2) {
        clickOn(addSearchParameter);
        searchCategoryDropDown.typeAndEnter(category);
        searchFieldDropDown.typeAndEnter(field);
        clickOn(searchOperatorDropDown);
        WebElementFacade operatorElement =
                find(By.xpath("//div[@class='field-requirement-content']/div[last()-1]//*[contains(local-name(), 'app-record-control')]/div/div[1]//ng-dropdown-panel//div[text()='" + operator + "']"));
        clickOn(operatorElement);
        searchValueField1.typeAndEnter(value1);
        if (!value2.isEmpty())
            searchValueField2.typeAndEnter(value2);
    }

    public void enterSelectUserId(String team) {
        clickOn(selectUserIdInReassignPopup);
        find(By.xpath("//div[normalize-space()='" + team + "']//div")).click();
    }


    public void verifyUpdateReviewContent() {
        clickOn(notesLoadMore);
        waitABit(1000);
        clickOn(updateReviewNoteLast);
        waitABit(3000);
    }

    public boolean verifyCategoryDeleted(String item) {
        clickOn(categoryUpdateReviewListDropdown);
        waitABit(1000);
        boolean res = false;
        List<WebElement> categoryUpdateReviewLists = getDriver().findElements(org.openqa.selenium.By.xpath("//ng-dropdown-panel//div[contains(@class,'ng-option')]"));

        int categoryUpdateReviewListsSize = categoryUpdateReviewLists.size();
        for (int i = 0; i < categoryUpdateReviewListsSize; i++) {
            String uiCategoryValue = categoryUpdateReviewLists.get(i).getText();

            if (item.equals(uiCategoryValue)) {
                Assert.assertEquals(uiCategoryValue, item);
                res = true;
            }

        }
        return res;
    }

    public boolean verifyValueMatchesTab() throws IOException, ParseException {
        boolean leftResult = false;
        boolean rightResult = false;
        boolean ruleDetailResult = false;
        List<WebElementFacade> leftList = findAll(By.xpath("//div[contains(@class,'left-label')]"));
        List<WebElementFacade> rightList = findAll(By.xpath("//div[contains(@class,'col-sm-7')]"));
        List<WebElementFacade> ruleDetailList = findAll(By.xpath("//div[contains(@class,'highlight-row')]//div[contains(@class,'right-label')]"));

        JSONObject dataDrive = scenarioContext.getDataDriveSectionAsJSON("valueMatchesTab");
        for (int i=0 ; i< ruleDetailList.size(); i++)
        {

            if(ruleDetailList.get(i).getText().trim().equals(dataDrive.get("ruleDetails"+(i+1)).toString()))
                ruleDetailResult = true;
        }

        for (int i=1 ; i< leftList.size(); i++)
        {

            if(leftList.get(i).getText().trim().equals(dataDrive.get("leftColumn"+i).toString()))

                leftResult = true;
        }

        for (int i=1 ; i< rightList.size(); i++)
        {

            if(rightList.get(i).getText().replace("\n"," ").equals(dataDrive.get("rightColumn"+i).toString()))
                rightResult = true;
        }

        if(leftResult&&rightResult&&ruleDetailResult)
            return true;
        else
            return false;
    }


    public void selectCategory1(String category) {
        WebElement newCategory;
        waitFor(applicationCategory);
        WebElement applicationCatLabel = getDriver().findElement(By.xpath("//h4[text()='"+category+"']/.."));

        if(applicationCatLabel.getAttribute("class").contains("add-new-category")){
            newCategory = getDriver().findElement(By.xpath("//h4[text()='" + category + "']/../following-sibling::div"));
            clickOn(newCategory);
        }
        else
            clickOn(applicationCatLabel);
    }
    public void verifyAllFooterButtonExist(){
        applicationMaintenanceDropdown.click();
        waitFor(notesButton);
        Assert.assertTrue(notesButton.isVisible() && reassignButton.isVisible() && actionButton.isVisible() && updateButton.isVisible());
        Assert.assertTrue( reviewButton.isVisible() && fraudCheck.isVisible() && AutoCheck.isVisible() && Clone.isVisible() && delete.isVisible());
        Assert.assertTrue( toggleButton.isVisible() && updateButton.isVisible() && matchHistoryButton.isVisible() && machineLearningHistoryButton.isVisible() && linkButton.isVisible());
        toggleDropdown.click();
    }

    public void verifyAllInfoInApplication() throws IOException, ParseException{
        JSONObject valueHolder  = ScenarioContext.getTSNTance().getDataDriveSectionAsJSON("Application");
        Assert.assertTrue(getDriver().findElement(By.xpath("//h4[contains(text(),'" + valueHolder.get("Application Number").toString().toUpperCase()  + "')]")).isDisplayed());
        Assert.assertTrue(getDriver().findElement(By.xpath("//div[contains(text(),'" + valueHolder.get("Application Type")  + "')]")).isDisplayed());


    }
}