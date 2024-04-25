package com..test.pages.application;

import com..test.pages.BasePage;
import net.serenitybdd.core.annotations.findby.FindBy;
import net.serenitybdd.core.pages.WebElementFacade;
import org.json.simple.JSONObject;
import org.junit.Assert;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class AlertReviewPage extends BasePage {

    @FindBy(xpath = "//*[@id=\"myTab\"]//a[contains(text(),'Clean')]")
    private WebElementFacade cleanTab;

    @FindBy(xpath = "//*[@id=\"myTab\"]//a[contains(text(),'Suspect')]")
    private WebElementFacade suspectTab;

    @FindBy(xpath = "//*[@id=\"myTab\"]//a[contains(text(),'HFP')]")
    private WebElementFacade hfpTab;

    @FindBy(xpath = "//*[@id=\"myTab\"]//a[contains(text(),'Error')]")
    private WebElementFacade errorTab;

    @FindBy(xpath="//*[@id=\"myTab\"]//a[contains(text(),'Errors')]")
    private WebElementFacade errorsTab;

    @FindBy(xpath = "//div[@class='-dropUp-menu dropdown-menu show']//button[contains(text(),'Action')]")
    private WebElementFacade actionButton;

    @FindBy(css = "button.-dropdown-dark.dropdown-toggle")
    private WebElementFacade reviewDropDown;

    @FindBy(className = "highlight")
    private WebElementFacade alertTabSelected;

    @FindBy(xpath = "(//input[@name='dpStart'])[2]")
    private WebElementFacade startDate;

    @FindBy(xpath = "(//input[@name='dpEnd'])[2]")
    private WebElementFacade endDate;

    @FindBy(xpath = "//button[@class='alp-bonito-button-default']")
    private WebElementFacade filter;

    @FindBy(xpath = "(//div[@class='can-toggle__switch'])[2]")
    private WebElementFacade dateToggleButton;

    @FindBy(xpath = "//button[contains(text(),'Apply')]")
    private WebElementFacade applyRule;

    @FindBy (xpath = "//ng-select[@bindlabel= 'Description']")
    private WebElementFacade valueFilter;

    @FindBy(xpath = "//h2[contains(text(),'Alert Review')]")
    public WebElementFacade alertReviewTitle ;

    @FindBy(xpath = "//h2[contains(text(),'Filter')]")
    private WebElementFacade filterTitle ;

    @FindBy(xpath = "//tbody/tr[1]")
    private WebElementFacade firstRowOnGridOnSelectedTab ;

    Actions actions;

    public void clickFirstRecordOnSelectTab()
    {
        clickOn(firstRowOnGridOnSelectedTab);
    }

    public void doubleClickFirstRecordOnSelectTab()
    {
        Actions actions = new Actions(getDriver());
        actions.doubleClick(firstRowOnGridOnSelectedTab).perform();
    }

    public void clickTab(String alert) {
        waitABit(500);
        if (alert.contains("Clean")) {
            waitFor(cleanTab);
            clickOn(cleanTab);
        }
        else if (alert.contains("Suspect")) {
            waitFor(suspectTab).waitUntilClickable();
            waitABit(1000);
            waitFor(suspectTab).waitUntilClickable();
            clickOn(suspectTab);
        }
        else if (alert.contains("HFP"))
        {
            clickOn(hfpTab);
        }
        else if (alert.contains("Errors")) {
            clickOn(errorsTab);
        }
        waitABit(500);
    }

    public void clickActionButton()
    {
        waitFor(ExpectedConditions.visibilityOf(reviewDropDown));
        clickOn(reviewDropDown);
        waitFor(ExpectedConditions.visibilityOf(actionButton));
        clickOn(actionButton);
    }

    public void applicationExists(String applicationNumber) {
        waitABit(2000);
        WebDriverWait wait = new WebDriverWait(getDriver(),5);
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[contains(text(),'" + applicationNumber + "')]"))).click();
        }
        catch(NoSuchElementException ex)
        {
            Assert.fail("Record not found");
        }


    }

    public void errorExists(String errorCodeMessage) {
        waitABit(2000);
        WebDriverWait wait = new WebDriverWait(getDriver(),5);
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//td[contains(text(),'" + errorCodeMessage + "')]"))).click();
        }
        catch(NoSuchElementException ex)
        {
            Assert.fail("Error code not found");
        }
    }

    public void enterFilterDetails(String data1,String data2) {
        waitABit(2000);
        clickOn(filter);
        clickOn(dateToggleButton);
        startDate.click();
        startDate.clear();
        typeInto(startDate,data1);
        startDate.sendKeys(Keys.TAB);
        typeInto(endDate,data2);
        clickOn(applyRule);
        waitABit(2000);
    }
    public void clickFilterButton() {
        clickOn(filter);
        waitFor(filterTitle);

    }

    public  void clickFieldValue() {
        clickOn(valueFilter);
    }


    public void clickApply() {
        clickOn(applyRule);
        waitFor(alertReviewTitle);
    }

    public String getHfpCountOnTab()
    {
        waitFor(hfpTab).waitUntilClickable();
        String fullText = hfpTab.getText();
        return fullText.substring(fullText.indexOf("(")+1, fullText.length()-1);
    }

    public String getSuspectCountOnTab()
    {
        waitFor(suspectTab).waitUntilClickable();
        String fullText = suspectTab.getText();
        return fullText.substring(fullText.indexOf("(")+1, fullText.length()-1);
    }

    public String getCleanCountOnTab()
    {
        waitFor(cleanTab).waitUntilClickable();
        String fullText = cleanTab.getText();
        return fullText.substring(fullText.indexOf("(")+1, fullText.length()-1);
    }

    public String getErrorCountOnTab()
    {
        WebDriverWait wait = new WebDriverWait(getDriver(), 2);
        try {
            wait.until(ExpectedConditions.elementToBeClickable(errorTab));
            String fullText = errorTab.getText();
            return fullText.substring(fullText.indexOf("(")+1, fullText.length()-1);
        }
        catch (TimeoutException ex){
            return "0";
        }
    }


}
