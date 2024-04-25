package com..test.pages;

import com..test.context.ScenarioContext;
import com..test.utilities.Properties;
import com.paulhammant.ngwebdriver.NgWebDriver;
import net.serenitybdd.core.annotations.findby.FindBy;
import net.serenitybdd.core.pages.PageObject;
import net.serenitybdd.core.pages.WebElementFacade;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class BasePage extends PageObject {

    public static final Properties fieldRequirements = new Properties("productUnderTestFieldRequirements.properties");
    protected final String dataDir = System.getProperty("user.dir") + "/src/main/resources/data/UI/";
    protected final String productVersion = System.getProperty("productUnderTest.version");

    protected ScenarioContext scenarioContext = ScenarioContext.getTSNTance();

    @FindBy(xpath = "//div[contains(@class,'alert-dismissable')]")
    protected WebElementFacade messageBanner;


    public void scrollToElement(WebElement element) throws InterruptedException {
        this.evaluateJavascript("arguments[0].scrollIntoView(true);", new Object[]{element});
        this.evaluateJavascript("scroll(0,-1000)",new Object[]{element});
        Thread.sleep(500L);
    }

    public void verifyTable() throws IOException, ParseException {
        List<WebElement> tableRows = getDriver().findElements(By.xpath("//tbody//tr"));
        List<WebElement> tableColumns = getDriver().findElements(By.xpath("//thead//tr//th"));

        for (int rowCount = 1; rowCount <= tableRows.size(); rowCount++)
        {
            JSONObject requestBody = ScenarioContext.getTSNTance().getDataDriveSectionAsJSON("verifyRow"+rowCount+"");
        Set<Map.Entry> entries = requestBody.entrySet(); for (Map.Entry entry : entries)
        {
            String colName = entry.getKey().toString();
            String expectedData = entry.getValue().toString().trim();
            expectedData=expectedData.replace("{{organisation}}", scenarioContext.getOrganisation());
            for (int columnCount= 2; columnCount <= tableColumns.size(); columnCount++)
            { String columnName = getDriver().findElement(By.xpath("//thead//th[" + columnCount + "]//span")).getText().trim();
                if (columnName.equalsIgnoreCase(colName))
                {
                    String actualData = getDriver().findElement(By.xpath("//tbody//tr[" + rowCount + "]//td[" + columnCount + "]")).getText().trim();
                Assert.assertEquals(expectedData.toLowerCase(), actualData.toLowerCase());
                break;
            }
            }
        }
    }
    }

    public String getCurrentPageTitle()
    {
        return find("//h2").getText();
    }

}
