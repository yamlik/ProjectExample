package com..test.pages.application;

import com..test.context.constants.ApplicationConstants;
import com..test.pages.BasePage;
import net.serenitybdd.core.annotations.findby.FindBy;
import net.serenitybdd.core.pages.WebElementFacade;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.io.IOException;

public class ApplicationMaintenancePage extends BasePage {


    @FindBy(xpath = "//div[contains(text(),'Add Search Parameter')]")
    private WebElementFacade addSearchParameter;

    @FindBy(xpath = "//ng-select[@bindlabel='shortResourceName']//span[@class='ng-arrow-wrapper']")
    private WebElementFacade operatorToggle;

    @FindBy(xpath = "//div[contains(text(),'=')]")
    private WebElementFacade equalOperator;

    @FindBy(xpath = "//ng-select[@bindvalue='Value']//input")
    private WebElementFacade valueBox;

    @FindBy(xpath = "//button[contains(text(),'Search')]")
    private WebElementFacade searchButton;

    @FindBy(xpath = "//span[contains(text(),'Application')]")
    private WebElementFacade applicationHeader;

    @FindBy(xpath = "//label[@for='rowtitle']")
    private WebElementFacade selectAllLabel;

    @FindBy(xpath = "//tbody//tr[2]")
    private WebElementFacade firstRowApplication;

    @FindBy(xpath = "//button[contains(text(),'View')]")
    private WebElementFacade viewButton;

    @FindBy(xpath = "//button[contains(text(),'Review')]")
    private WebElementFacade reviewButton;

    @FindBy(xpath = "//h2")
    private WebElementFacade header2;

    @FindBy(xpath = "//div[contains(text(),'Organisation')]")
    private WebElementFacade expiryDate;

    @FindBy(xpath = "//h4")
    private WebElementFacade header4;

    @FindBy(xpath = "//h4[contains(text(),'Applicant')]")
    private WebElementFacade applicantCategory;

    @FindBy(xpath = "//span[@class='advancesearch-add-icon']")
    private WebElementFacade advancedSearchIcon;

    @FindBy(xpath = "//a[normalize-space(text())='Security 1']/following::img[2]")
    private WebElementFacade plusButtonSecurity;

    @FindBy(xpath="//button[contains(text(),'View')]/following::button[1]")
    private WebElementFacade applicationMaintenanceDropdownToggle;

    @FindBy(xpath="//button[contains(text(),'Batch Action')]")
    private WebElementFacade batchActionOption;

    @FindBy(xpath="//button[contains(text(),'Known Fraud')]")
    private WebElementFacade knownFraudButton;

    @FindBy(xpath="(//div[@id='dropdownBasic2']//img)[1]")
    private WebElementFacade applicationEclipsis;

    @FindBy(xpath="(//div[@id='dropdownBasic2']//img)[2]")
    private WebElementFacade secondApplicationEclipsis;


    @FindBy(xpath="(//div[@placement='bottom-right']//img)[1]")
    private WebElementFacade diaryDropdownToggle;

    @FindBy(xpath="//a[contains(text(),'Add')]")
    private WebElementFacade addNote;

    @FindBy(xpath="//textarea[@maxlength='10000']")
    private WebElementFacade noteTextArea;

    @FindBy(xpath="//span[contains(text(),'Save')]/..")
    private WebElementFacade noteSaveButton;

    @FindBy(xpath="//button[@appresource='CommonButtons.Res_Save']")
    private WebElementFacade noteSaveButton2;

    @FindBy(xpath="//span[contains(text(),'Note')]")
    private WebElementFacade diaryNote;

    @FindBy(xpath="//button[contains(text(),'Yes')]")
    private WebElementFacade confirmButton;

    @FindBy(xpath="//button[contains(text(),'Continue')]")
    private WebElementFacade clickContinueButton;

    @FindBy(xpath="//a[text()='Application']")
    private WebElementFacade applicationHeaders;

    @FindBy(xpath="//div[@class='dropdown']")
    private WebElementFacade selectNote;



    public void addSearchParameterAndSearch() {
        waitABit(1000);
        if (productVersion.equals("") || productVersion.equals(".1") || productVersion.equals(".2") || productVersion.equals(".0")) {
            clickOn(addSearchParameter);
        }
        if (productVersion.equals(".0")) {
            clickOn(advancedSearchIcon);
        }
        clickOn(operatorToggle);
        clickOn(equalOperator);
        valueBox.typeAndEnter("");
        clickOn(searchButton);
    }

    public void selectMultipleAppAndViewOne() {
        clickOn(selectAllLabel);
        clickOn(selectAllLabel);
        clickOn(firstRowApplication);
        clickOn(viewButton);
        waitABit(4000);
        waitFor(ExpectedConditions.visibilityOf(applicationHeader));
    }

    public void verifyApplicationRecordHeader() {
        String header2Value = header2.getText();
        waitABit(500);
        String header4Value = header4.getText();
        Assert.assertEquals("Application Record", header2Value);
        Assert.assertEquals("AUTOMATION_58890", header4Value);
    }

    public void verifyWatchlistRecordHeader() {
        String header2Value = header2.getText();
        waitABit(500);
        String header4Value = header4.getText();
        Assert.assertEquals("Watchlist Record", header2Value);
        Assert.assertTrue("Application not found", header4Value.contains("AUTOMATION_58890"));
    }

    public void reviewAndVerifyMatchReviewHeader() {
        clickOn(reviewButton);
        waitABit(4000);
        String header2Value = header2.getText();
        Assert.assertEquals("Application Number - AUTOMATION_58890", header2Value);
    }

    public void verifyApplicationRestrictedFields(String fieldName) {
        String[] fields = fieldName.split(",");
        for (String item : fields) {

            WebElement field = getDriver().findElement(By.xpath("//div[contains(text(),'" + item + "')]"));
            String actualClass = field.getAttribute("class");

            if (item.contains("Organisation") || (item.contains("Application Number") || (item.contains("Application Type")))) {
                Assert.assertEquals("", actualClass);

            } else if (item.contains("Expiry Date") || (item.contains("Application Date"))) {
                Assert.assertEquals("restricted-field", actualClass);
            }
        }

    }

    public void verifyApplicantRestrictedFields(String fieldName) {
        applicantCategory.click();
        String[] fields = fieldName.split(",");
        for (String item : fields) {

            WebElement field = getDriver().findElement(By.xpath("//div[contains(text(),'" + item + "')]"));
            String actualClass = field.getAttribute("class");

            if (item.contains("Id Number 1") || (item.contains("Application Number") || (item.contains("Application Type")))) {
                Assert.assertEquals("", actualClass);

            } else if (item.contains("Id Number 2") || (item.contains("Id Number 3"))) {
                Assert.assertEquals("restricted-field", actualClass);
            }
        }

    }

    public void addSecuritySecond(String data, String data1) throws IOException, ParseException {
        WebElement plusButton = getDriver().findElement(By.xpath("//h4[contains(text(),'Security')]"));
        clickOn(plusButton);
        waitABit(500);
        clickOn(plusButtonSecurity);
        waitABit(1000);
        JSONObject dataDrive = scenarioContext.getDataDriveSectionAsJSON("Security2");
        data = dataDrive.get("Id Number 1").toString();
        WebElement num1 = getDriver().findElement(By.xpath("//div[contains(normalize-space(text()),'Id Number 1')]/following::input[1]"));
        typeInto(num1, data);
        data1 = dataDrive.get("Id Number 2").toString();
        WebElement num2 = getDriver().findElement(By.xpath("//div[contains(normalize-space(text()),'Id Number 2')]/following::input[1]"));
        typeInto(num2, data1);
        WebElement toggle = getDriver().findElement(By.xpath("//button[contains(@class,'dropdown-toggle')]"));
        clickOn(toggle);
        WebElement update = getDriver().findElement(By.xpath("//div[contains(@class,'dropdown-menu')]/button[contains(text(),'Update')]"));
        clickOn(update);
        waitABit(500);
        WebElement popUp = getDriver().findElement(By.xpath("//div[normalize-space(@class)='col-sm-6']/button[contains(text(),'No')]"));
        waitABit(500);
        clickOn(popUp);
    }

    public void performBatchAction(){
        clickOn(selectAllLabel);
        clickOn(applicationMaintenanceDropdownToggle);
        clickOn(batchActionOption);
        clickOn(knownFraudButton);
    }
    public void addNoteFirstApplication(){
        clickOn(confirmButton);
        clickOn(clickContinueButton);
        waitFor(ExpectedConditions.visibilityOf(applicationHeaders));
        clickOn(applicationEclipsis);
        clickOn(selectNote);
        waitFor(ExpectedConditions.visibilityOf(diaryDropdownToggle));
        clickOn(diaryDropdownToggle);
        clickOn(addNote);
        clickOn(noteTextArea);
        noteTextArea.sendKeys("Batch Notes added");
        waitFor(ExpectedConditions.visibilityOf(noteSaveButton));
        clickOn(noteSaveButton);
    }
    public void addNoteSecondApplication(){
        clickOn(secondApplicationEclipsis);
        clickOn(selectNote);
        waitFor(ExpectedConditions.visibilityOf(diaryDropdownToggle));
        clickOn(diaryDropdownToggle);
        clickOn(addNote);
        clickOn(noteTextArea);
        noteTextArea.sendKeys("Batch Notes added");
        waitFor(ExpectedConditions.visibilityOf(noteSaveButton));
        clickOn(noteSaveButton2);

    }

    public void verifyBatchNotes(String batchNotes,String firstApplication,String secondApplication){
        waitFor(ExpectedConditions.visibilityOf(viewButton));
        clickOn(viewButton);
        WebElement clickFirstApplication=getDriver().findElement(By.xpath("//h4[text()='" + firstApplication + "']"));
        clickOn(clickFirstApplication);
        clickOn(diaryNote);
        waitABit(1000);
        String element = getDriver().findElement(By.xpath("//span[contains(text(),'" + batchNotes + "')]")).getText();
        Assert.assertEquals(element, ApplicationConstants.BatchNotesMessage);
        waitABit(500);
        WebElement clickSecondApplication=getDriver().findElement(By.xpath("//h4[text()='" + secondApplication + "']"));
        clickOn(clickSecondApplication);
        clickOn(diaryNote);
        waitABit(1000);
        String secondApplicationVerify = getDriver().findElement(By.xpath("//span[contains(text(),'" + batchNotes + "')]")).getText();
        Assert.assertEquals(element, ApplicationConstants.BatchNotesMessage);

    }
}
