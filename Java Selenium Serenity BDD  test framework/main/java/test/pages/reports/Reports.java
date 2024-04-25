package com..test.pages.reports;

import com..test.pages.BasePage;
import net.serenitybdd.core.annotations.findby.By;
import net.serenitybdd.core.annotations.findby.FindBy;
import net.serenitybdd.core.pages.WebElementFacade;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Reports extends BasePage {

    @FindBy(xpath = "//div[contains(text(),'Known Fraud')]//following::input")
    private WebElementFacade label_Known_Fraud;

    @FindBy(xpath = "//button[contains(text(),'Save')]")
    private WebElementFacade btn_Save;

    //    @FindBy(xpath = "//label[@class='alp-bonito-checkbox-label css-label']")
    @FindBy(xpath = "(//label[@class='alp-bonito-checkbox-label css-label'])[1]")

    private WebElementFacade checkBox;

    @FindBy(xpath = "//button[contains(text(),'Export')]")
    private WebElementFacade Export;

    @FindBy(xpath = "//table[@class='table CustomTable report-result-table']")
    private WebElementFacade Table_list_Reports;


    public void save_Actions() {
        clickOn(btn_Save);
    }

    public void Available_fields_checkBox() {
        waitFor(checkBox);
        waitABit(200);
        waitFor(ExpectedConditions.elementToBeClickable(checkBox));
        clickOn(checkBox);

    }

    public void Export_Application_fields() {
        waitFor(Export);
        waitFor(ExpectedConditions.visibilityOf(Export));
        clickOn(Export);
        waitFor(ExpectedConditions.visibilityOf(Table_list_Reports));
    }

    public List<List<String>> get_TableData() {
        List<WebElement> rows = Table_list_Reports.findElements(By.tagName("tr"));
        List<String> RowData = new ArrayList<String>();
        List<List<String>> allTableData = new ArrayList<>();
        List<WebElement> columns = rows.get(0).findElements(By.tagName("th"));
        for (int cnum = 0; cnum < columns.size(); cnum++) {
            String cellValue = columns.get(cnum).getText();
            RowData.add(cellValue);
        }
        allTableData.add(RowData);
        for (int rownum = 1; rownum < rows.size(); rownum++) {
            List<String> tablerowData = new ArrayList<String>();
            List<WebElement> othercolumns = rows.get(rownum).findElements(By.tagName("td"));
            for (int cnum = 0; cnum < othercolumns.size(); cnum++) {
                String cellValue = othercolumns.get(cnum).getText();
                tablerowData.add(cellValue);
            }
            allTableData.add(tablerowData);
        }
        return allTableData;

    }

    public Map<String, String> compareData(Map<String, String> map) {


        return map;

    }

}
