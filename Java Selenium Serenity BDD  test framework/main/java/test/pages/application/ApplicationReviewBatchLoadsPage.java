package com..test.pages.application;

import com..test.pages.BasePage;
import net.serenitybdd.core.annotations.findby.FindBy;
import net.serenitybdd.core.pages.WebElementFacade;
import org.openqa.selenium.interactions.Actions;

import java.util.List;

public class ApplicationReviewBatchLoadsPage extends BasePage {

    @FindBy(name = "dpStart")
    private WebElementFacade fromDateRange;

    @FindBy(name = "dpEnd")
    private WebElementFacade toDateRange;

    @FindBy(xpath = "//button[@appresource='ReviewBatchLoads.Button_Refresh']")
    private WebElementFacade refreshButton;

    //TABLE ELEMENTS
    @FindBy(xpath = "//app-header-label[normalize-space()='Batch']")
    private WebElementFacade batchColumnHeader;

    @FindBy(xpath = "//tbody/tr[1]")
    private WebElementFacade firstDataRow;

    @FindBy(xpath = "//tbody/tr[1]/td[7]")
    private WebElementFacade firstRowStatus;

    @FindBy(xpath = "//tbody/tr[1]/td[13]")
    private WebElementFacade firstRowHfp;

    @FindBy(xpath = "//tbody/tr[1]/td[15]")
    private WebElementFacade firstRowSuspect;

    @FindBy(xpath = "//tbody/tr[1]/td[17]")
    private WebElementFacade firstRowClean;

    Actions actions;
    AlertReviewPage alertReviewPage;


    public void doubleClickFirstRow()
    {
        actions = new Actions(getDriver());
        actions.doubleClick(firstDataRow).perform();
        waitFor(alertReviewPage.alertReviewTitle).waitUntilVisible();
    }


    public void enterDatesAndRefresh(String fromDate, String toDate)
    {
        typeInto(fromDateRange, fromDate);
        typeInto(toDateRange, toDate);
        clickOn(refreshButton);
        waitFor(firstDataRow);
        clickOn(batchColumnHeader);
        waitForBatchToComplete();
    }

    public void waitForBatchToComplete()
    {
        String status = getValuesFromFirstRow("STATUS");
        int attempts = 0;
        while (!status.equalsIgnoreCase("Complete") && attempts<5)
        {
            waitABit(1000);
            clickOn(refreshButton);
            attempts++;
        }
    }

    public String getValuesFromFirstRow(String field)
    {
        switch (field.toUpperCase())
        {
            case "HFP": return firstRowHfp.getText().trim();
            case "SUSPECT": return firstRowSuspect.getText().trim();
            case "CLEAN": return firstRowClean.getText().trim();
            case "STATUS": return firstRowStatus.getText().trim();
        }

        return "";
    }


}
