package com..test.pages.utilities;

import com..test.context.constants.ApplicationConstants;
import com..test.pages.BasePage;
import net.serenitybdd.core.annotations.findby.FindBy;
import net.serenitybdd.core.pages.WebElementFacade;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

public class SystemImport extends BasePage {


    @FindBy(xpath = "//li//a[contains(text(),'System Import')]")
    private WebElementFacade systemImport;

    @FindBy(xpath = "//button[contains(text(),'Upload File...')]")
    private WebElementFacade uploadFile;

    @FindBy(xpath = "//div[@class='upload-btn-wrapper']")
    private WebElementFacade browse;

    @FindBy(xpath = "//div//button[(text()='Upload')]")
    private WebElementFacade upload;
    @FindBy(xpath = "//button[contains(text(),'OK')]")
    private WebElementFacade ok;
    @FindBy(xpath = "//button[contains(text(),'Close')]")
    private WebElementFacade close;

    @FindBy(xpath = "//button[contains(normalize-space(),'Filter')]")
    private WebElementFacade filterButton;

    //Filter popup
    @FindBy(xpath = "(//div[contains(@class,'modal-content')]//input)[1]")
    private WebElementFacade importDataTextBoxOnFilterPopup;



    @FindBy(xpath = "//div[@class='btn-group mr-3']//button[contains(text(),'Import')]")
    private WebElementFacade importFile;

    @FindBy(xpath = "//tbody//tr//following::td//following::td//following::td[contains(text(),'.')]")
    private  List<WebElement> productUnderTestData;

    @FindBy(xpath = "//div[contains(@class,'alp-notification-sticky-button')]")
    private WebElementFacade suTSNTssAlert;

    @FindBy(xpath = "//button[text()='Apply']")
    private WebElementFacade applyButtonInFilterPopup;

    @FindBy(xpath = "//ng-select")
    private WebElementFacade firstRowImportCriteriaDropDown;


    public void clickOnSystemImport() {
        waitFor(systemImport).click();
    }

    public void clickOnUploadFileButton() {
        waitFor(uploadFile).click();
    }

    public void clickFilterButton() { clickOn(filterButton); }

    public void clickApplyButtonInFilterPopup() { clickOn(applyButtonInFilterPopup); }

    public void enterImportDataInFilterPopup(String importData) { typeInto(importDataTextBoxOnFilterPopup, importData); }

    public void selectImportCriteriaOption(String data,String criteria)
    {
        find(By.xpath("//td[contains(text(),'"+data+"')]/following-sibling::td//ng-select")).click();
        find(By.xpath("//ng-dropdown-panel//span[contains(text(),'"+criteria+"')]")).click();
    }

    public void clickOnBrowse() {

        if (productVersion.equals("") || (productVersion.equals(".0"))) {
            JavascriptExecutor js = (JavascriptExecutor) getDriver();
            js.executeScript("document.getElementById('FileNames').style.visibility = 'visible';");
            getDriver().findElement(By.id("FileNames")).sendKeys(dataDir + "SystemExports/productUnderTest_Data_.DAT");
        }

        else if (productVersion.equals(".0")) {
            JavascriptExecutor js = (JavascriptExecutor) getDriver();
            js.executeScript("document.getElementById('FileNames').style.visibility = 'visible';");
            getDriver().findElement(By.id("FileNames")).sendKeys(dataDir + "SystemExports/productUnderTest.0.DAT");
        }
        else if (productVersion.equals(".2")) {
            JavascriptExecutor js = (JavascriptExecutor) getDriver();
            js.executeScript("document.getElementById('FileNames').style.visibility = 'visible';");
            getDriver().findElement(By.id("FileNames")).sendKeys(dataDir + "SystemExports/productUnderTest.2.DAT");
        }

    }

    public void clickOnBrowseExcel_sheet() {
        JavascriptExecutor js = (JavascriptExecutor) getDriver();
        js.executeScript("document.getElementById('FileNames').style.visibility = 'visible';");
        getDriver().findElement(By.id("FileNames")).sendKeys(dataDir+ "Excel_Data/Field Requirements_Application.xlsx");
        clickOn(upload);
        ((JavascriptExecutor) getDriver()).executeScript("arguments[0].scrollIntoView(true);", ok);waitABit(1000);
        js.executeScript("arguments[0].click();", ok);
        clickOn(close);

    }

    public void clickOnBrowseOld() {
        JavascriptExecutor js = (JavascriptExecutor) getDriver();
        js.executeScript("document.getElementById('FileNames').style.visibility = 'visible';");
        getDriver().findElement(By.id("FileNames")).sendKeys(dataDir+"SystemExports/productUnderTest_Data_Old.DAT");
    }

    public void getDefaultDownloadFolder()
    {
        Path downloadsDir = Paths.get(System.getProperty("user.home"), "Downloads");

        //get the most recent file
        File folder = new File(downloadsDir.toString());
        File[] files = folder.listFiles();
        assert files != null;
        Arrays.sort(files, (o1, o2) -> Long.compare(o2.lastModified(), o1.lastModified()));

        JavascriptExecutor js = (JavascriptExecutor) getDriver();
        js.executeScript("document.getElementById('FileNames').style.visibility = 'visible';");
        getDriver().findElement(By.id("FileNames")).sendKeys(files[0].toString());
    }

    public void clickUploadButton()
    {
        waitFor(upload).isClickable();
        clickOn(upload);
        waitFor(suTSNTssAlert).waitUntilNotVisible();
    }

    public void uploadFile() {
        waitFor(uploadFile).isEnabled();
        clickOn(uploadFile);
    }


    public void browseFile() {

       waitFor(browse).click();

    }

    public void importFile()  {
        waitFor(3).second();
        clickOn(importFile);
        waitFor(suTSNTssAlert).waitUntilNotVisible();
    }

    public void verifyDataImport() {
        for (WebElement message : productUnderTestData)
        {
            Assert.assertEquals(ApplicationConstants.DATA_IMPORT_SUTSNTSS_MESSAGE,message.getText());

        }
    }
}
