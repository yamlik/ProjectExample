package com..test.pages.watchlist;

import com..test.pages.BasePage;
import com..test.pages.application.ApplicationRecordPage;
import com..test.pages.application.AutoCheckResultsPage;
import com..test.utilities.JSONHelper;
import net.serenitybdd.core.annotations.findby.By;
import net.serenitybdd.core.annotations.findby.FindBy;
import net.serenitybdd.core.pages.WebElementFacade;
import org.json.JSONObject;
import org.junit.Assert;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.io.IOException;
import java.util.List;


public class WatchlistRecordPage extends BasePage {

    AutoCheckResultsPage autoCheckResultsPage;

    ApplicationRecordPage applicationRecordPage;

    @FindBy(className = "itemMain")
    private WebElementFacade categoryRoot;

    @FindBy(xpath = "//span[contains(text(),'Category')]")
    private WebElementFacade categoryHeading;

    @FindBy(xpath = "//button[text()='Save'] | //button[text()='Submit']| //button[text()='Update']")
    private WebElementFacade updateSaveButton;

    @FindBy(xpath = "//*[@id=\"header-title\"]/h2")
    private WebElementFacade appNum;
    @FindBy(xpath = "//div[contains(@class,'alert-dismissable')]")
    public WebElementFacade messageBanner;

    @FindBy(xpath = "//div[contains(text(),'Add Search Parameter')]")
    private WebElementFacade addSearchParameter;

    @FindBy(xpath = "//span[@class='advancesearch-add-icon']")
    private WebElementFacade advancedSearchIcon;

    @FindBy(xpath = "//ng-select[@bindlabel='shortResourceName']//span[@class='ng-arrow-wrapper']")
    private WebElementFacade operatorToggle;

    @FindBy(xpath = "//div[contains(text(),'=')]")
    private WebElementFacade equalOperator;

    @FindBy(xpath = "//ng-select[@bindvalue='Value']//input")
    private WebElementFacade valueBox;

    @FindBy(xpath = "//button[contains(text(),'Search')]")
    public WebElementFacade searchButton;

    @FindBy(xpath = "//button[contains(text(),'Yes')]")
    private WebElementFacade yesButton;

    @FindBy(xpath = "//h4[contains(text(),'Watchlist')] | //h2[contains(text(),'Watchlist Maintenance Results')] | //h2[contains(text(),'Watchlist Record')]")
    public WebElementFacade watchlistCategoryOrAppResultsTitle;

    @FindBy(xpath = "//div[@class='field-requirement-content']/div[last()-1]//ng-select[contains(@id,'fields')]//input")
    private WebElementFacade searchCategoryDropDown;

    @FindBy(xpath = "//div[@class='field-requirement-content']/div[last()-1]//ng-select[contains(@bindvalue,'FieldNumber')]//input")
    private WebElementFacade searchFieldDropDown;

    @FindBy(xpath = "//div[@class='field-requirement-content']/div[last()-1]//*[contains(local-name(), 'app-record-control')]/div/div[1]//ng-select")
    private WebElementFacade searchOperatorDropDown;

    @FindBy(xpath = "//div[@class='field-requirement-content']/div[last()-1]//*[contains(local-name(), 'app-record-control')]/div/div[2]//input[1]")
    private WebElementFacade searchValueField1;

    @FindBy(xpath = "//div[@class='field-requirement-content']/div[last()-1]//*[contains(local-name(), 'app-record-control')]/div/div[2]//input[2]")
    private WebElementFacade searchValueField2;

    @FindBy(xpath = "//button[@class='dropdown-toggle -dropdown-dark']")
    private WebElementFacade watchlistRecordDropdown;

    @FindBy(xpath = "//button[contains(text(),'Clone')]")
    private WebElementFacade watchlistRecordDropdownClone;

    public void clickSave() {
        clickOn(updateSaveButton);
        waitFor(autoCheckResultsPage.pageHeading);
    }

    public void enterWatchlistDetails(String field, String value) {
        value = value.replace("{{organisation}}", scenarioContext.getOrganisation());
        WebElement element = getCategoryFieldValueElement(field);
        waitFor(element);
        typeInto(element, value);
        element.sendKeys(Keys.ENTER);
    }

    public void updateWatchlistRecord(String field, String value) throws IOException {
        applicationRecordPage.enterApplicationDetails(field, value);
    }

    public void clickOnUpdateSaveButton() {
        clickOn(updateSaveButton);
    }

    public void clickOnUpdateYesButton() {
        clickOn(yesButton);
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

    public WebElement getCategoryFieldValueElement(String field) {
        waitFor(appNum);
        WebElement fieldValue;
        ((JavascriptExecutor) getDriver()).executeScript("scroll(0,100)");
        waitABit(500);
        if (field.contains("Company Name") || field.contains("Surname") || field.contains("First Name"))
            fieldValue = getDriver().findElement(By.xpath("//td/div[contains(text(),'" + field + "')]/../following-sibling::td//textarea"));
        else
            fieldValue = getDriver().findElement(By.xpath("//td/div[contains(text(),'" + field + "')]/../following-sibling::td//input"));
        return (fieldValue);
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

    public void verifyWatchlistDetails(String field, String value) {
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

        Assert.assertEquals("Watchlist details not matching",value,fieldValue);

    }

    public void clickClone(){
        clickOn(watchlistRecordDropdown);
        clickOn(watchlistRecordDropdownClone);
    }
}