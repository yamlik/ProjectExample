package com..test.pages.definitions;

import com..test.context.ScenarioContext;
import com..test.pages.BasePage;
import net.serenitybdd.core.annotations.findby.FindBy;
import net.serenitybdd.core.pages.WebElementFacade;
import org.joda.time.LocalDate;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.junit.Assert;
import org.openqa.selenium.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class WhiteListPage extends BasePage {

    @FindBy(xpath = "//div[@id = 'dropdownBasic2']//img")
    private WebElementFacade ellipsisMenu;

    @FindBy(xpath = "//div[contains(@class,'dropdown')]/ul/li[3]")
    private WebElementFacade delete;

    @FindBy(xpath = "//button[contains(text(),'Yes' )]")
    private WebElementFacade yesButton;

    public void verifyWhitelistRecord() throws IOException, ParseException {
        List<WebElement> tableRows = getDriver().findElements(By.xpath("//tbody//tr"));
        List<WebElement> tableColumns = getDriver().findElements(By.xpath("//thead//tr//th"));
        for (int rowCount = 1; rowCount <= tableRows.size(); rowCount++) {
            JSONObject requestBody = ScenarioContext.getTSNTance().getDataDriveSectionAsJSON("verifyRow" + rowCount + "");
            Set<Map.Entry> entries = requestBody.entrySet();
            for (Map.Entry entry : entries) {
                String applicationNumber = entry.getValue().toString().trim();

                if (applicationNumber.equals(getDriver().findElement(By.xpath("//tbody//tr[" + rowCount + "]/td[1]")).getText().trim())) {
                    for (int columnCount = 2; columnCount <= tableColumns.size(); columnCount++) {
                        String columnName = getDriver().findElement(By.xpath("//thead//th[" + columnCount + "]//span")).getText().trim();
                        if (columnName.equals("Expiry Date")) {

                            String actualData = getDriver().findElement(By.xpath("//tbody//tr[" + rowCount + "]//td[" + columnCount + "]")).getText().trim();
                            LocalDate futureDate = LocalDate.now().plusMonths(6);
                            String finalFutureDate = futureDate.toString("dd/MM/yyyy");
                            Assert.assertEquals("Application has not been whitelisted and expiry date does not match" , finalFutureDate, actualData);
                            break;
                        }
                    }
                }
            }
        }
    }

    public void deleteWhiteListRecord() throws IOException, ParseException {
        List<WebElement> tableRows = getDriver().findElements(By.xpath("//tbody//tr"));
        for (int rowCount = 1; rowCount <= tableRows.size(); rowCount++) {
            JSONObject requestBody = ScenarioContext.getTSNTance().getDataDriveSectionAsJSON("verifyRow" + rowCount + "");
            Set<Map.Entry> entries = requestBody.entrySet();
            for (Map.Entry entry : entries) {
                String applicationNumber = entry.getValue().toString().trim();
                if (applicationNumber.equals(getDriver().findElement(By.xpath("//tbody//tr[" + rowCount + "]/td[1]")).getText().trim())) {
                    clickOn(ellipsisMenu);
                    clickOn(delete);
                    clickOn(yesButton);
                }
            }
        }
    }

}




