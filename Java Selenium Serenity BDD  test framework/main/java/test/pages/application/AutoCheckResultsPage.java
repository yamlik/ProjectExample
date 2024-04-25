package com..test.pages.application;

import com..test.context.ScenarioContext;
import com..test.pages.BasePage;
import com..test.utilities.JsHelper;
import net.serenitybdd.core.annotations.findby.FindBy;
import net.serenitybdd.core.pages.WebElementFacade;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

public class AutoCheckResultsPage extends BasePage {

    @FindBy(xpath = "//h2[contains(text(),'Auto Check Results')]")
    public WebElementFacade pageHeading;

    @FindBy(xpath = "//div[@class='match-review-labeltext body-text']")
    private WebElementFacade headerApplicationNumber;

    @FindBy(xpath = "//tr[@class='predator-clickable']")
    private WebElementFacade firstRowInGrid;

    @FindBy(css = " button.-dropdown-dark.dropdown-toggle")
    private WebElementFacade reviewToggle;

    @FindBy(xpath = "//div[contains(@class, '-dropUp-menu dropdown-menu show')]//button[text()='Action']")
    private WebElementFacade actionButton;

    @FindBy(xpath = "//div[contains(@class, 'alert-dismissable')]")
    private WebElementFacade saveBanner;

    @FindBy(className = "table custom-table")
    private WebElementFacade tableRoot1;

    @FindBy(xpath = "//button[contains(@class,'pagination-action-button')][1]")
    private WebElementFacade navigateLeftPage;

    @FindBy(xpath = "//button[contains(@class,'pagination-action-button')][2]")
    private WebElementFacade navigateRightPage;

    @FindBy(xpath = "//thead//tr//th")
    private List<WebElement> tableColumns;

    @FindBy(xpath = "//tbody//tr")
    private List<WebElement> tableRows;

    Actions actions;
    JsHelper jsHelper = new JsHelper();


    public void getPageTitle() {
        waitFor(pageHeading);
        Assert.assertTrue("Auto Check not performed.", pageHeading.isDisplayed());
    }

    public void verifyAutoCheckDetailsHeader(String applicationNumber)
    {
        waitFor(pageHeading);
        Assert.assertEquals("Application Number in the header is not expected",headerApplicationNumber.getText().toUpperCase(),applicationNumber.toUpperCase());
    }

    public void verifyAutoCheckGridResults(JSONObject rows) {
        verifyTable(rows);
    }

    public void selectRowToAction(String applicationNumber)
    {
        waitFor(firstRowInGrid);
        getDriver().findElement(By.xpath("//span[contains(text(),'"+applicationNumber.toUpperCase()+"')]")).click();
    }

    public void clickActionButton()
    {
        clickOn(reviewToggle);
        clickOn(actionButton);
    }

    public void clickRightPage() {
        waitFor(navigateRightPage);
        if (navigateRightPage.isCurrentlyEnabled())
            navigateRightPage.sendKeys(Keys.PAGE_DOWN);
        clickOn(navigateRightPage);
    }

    public void clickLeftPage() {
        waitFor(navigateLeftPage);
        if (navigateLeftPage.isCurrentlyEnabled())
            navigateLeftPage.sendKeys(Keys.PAGE_DOWN);
        clickOn(navigateLeftPage);
    }



    public void waitForBannerToDisappear() {
        waitFor(saveBanner).waitUntilNotVisible();
    }

    public void verifyTable(JSONObject rows) {

        for (int rowCount = 1; rowCount <= tableRows.size(); rowCount++) {

            JSONObject requestBody = (JSONObject) rows.get("verifyRow"+rowCount+"");
            Set<Map.Entry> entries = requestBody.entrySet();

            for (Map.Entry entry : entries) {

                String colName = entry.getKey().toString();
                String expectedData = entry.getValue().toString().trim();

                for (int  columnCount= 2; columnCount <= tableColumns.size(); columnCount++) {

                    String columnName = getDriver().findElement(By.xpath("//thead//th[" + columnCount + "]//span")).getText().trim();

                    if (columnName.equalsIgnoreCase(colName)) {

                        String actualData = getDriver().findElement(By.xpath("//tbody//tr[" + rowCount + "]//td[" + columnCount + "]")).getText().trim();
                        Assert.assertEquals(expectedData.toUpperCase(), actualData.toUpperCase());
                        break;
                    }
                }
            }
        }
    }

    public void verifyAppRecord(String record)
    {
        waitFor(pageHeading);
        waitABit(1000);
        if (record.equals("Single")){
            getDriver().findElement(By.xpath("(//span[@class='align-middle'])[1]")).click();
            String row=getDriver().findElement(By.xpath("(//span[@class='badge-count'])[1]")).getText().trim();
            int rowCount=Integer.parseInt(row);
            if(rowCount==1){
            Assert.assertTrue("Single row is selected",true);
            }else{
                Assert.fail("Single row is not selected");
            }
        }
       else if(record.equals("Multiple")){
            getDriver().findElement(By.xpath("(//label[@class='alp-bonito-checkbox-label css-label'])[1]")).click();
            String row=getDriver().findElement(By.xpath("(//span[@class='badge-count'])[1]")).getText().trim();
            int rowCount=Integer.parseInt(row);
            if(rowCount>1){
                Assert.assertTrue("Multiple row is selected",true);
            }
            else{
                Assert.fail("Multiple row is not selected");
        }
        }

        }
}
