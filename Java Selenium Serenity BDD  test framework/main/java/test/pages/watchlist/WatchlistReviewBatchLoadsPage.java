package com..test.pages.watchlist;

import com..test.pages.BasePage;
import com..test.utilities.FileHelper;
import cucumber.api.java.bs.A;
import net.serenitybdd.core.annotations.findby.FindBy;
import net.serenitybdd.core.pages.WebElementFacade;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Assert;
import org.openqa.selenium.interactions.Actions;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class WatchlistReviewBatchLoadsPage extends BasePage {

    @FindBy(name = "dpStart")
    private WebElementFacade fromDateRange;

    @FindBy(name = "dpEnd")
    private WebElementFacade toDateRange;

    @FindBy(xpath = "//button[@appresource='CriminalReviewBatchLoads.Button_Refresh']")
    private WebElementFacade refreshButton;

    @FindBy(xpath = "//h2[normalize-space()='Watchlist Review Batch']")
    private WebElementFacade watchReviewBatchTitle;

    @FindBy(xpath = "//button[text()='Auto Check']")
    private WebElementFacade autoCheckButton;

    @FindBy(xpath = "//button[contains(@class,'dropdown-toggle')]")
    private WebElementFacade toggleButton;

    @FindBy(xpath = "//button[text()='Review Errors']")
    private WebElementFacade reviewErrorsButton;

    @FindBy(xpath = "//button[text()='Delete All Errors']")
    private WebElementFacade deleteAllErrorsButton;

    @FindBy(xpath = "//span[@class='warning-icon']")
    private WebElementFacade confirmDeletePopup;

    @FindBy(xpath = "(//div[@class='modal-footer']//button)[1]")
    private WebElementFacade confirmDeleteNoButton;

    @FindBy(xpath = "(//div[@class='modal-footer']//button)[2]")
    private WebElementFacade confirmDeleteYesButton;

    //TABLE ELEMENTS
    @FindBy(xpath = "//app-header-label[normalize-space()='Batch Number']")
    private WebElementFacade batchColumnHeader;

    @FindBy(xpath = "//tbody/tr[1]")
    private WebElementFacade firstDataRow;

    @FindBy(xpath = "//tbody/tr[1]/td[6]")
    private WebElementFacade firstRowStatus;

    @FindBy(xpath = "//tbody/tr[1]/td[8]")
    private WebElementFacade firstRowTotal;

    @FindBy(xpath = "//tbody/tr[1]/td[9]")
    private WebElementFacade firstRowAdded;

    @FindBy(xpath = "//tbody/tr[1]/td[10]")
    private WebElementFacade firstRowReplaced;

    @FindBy(xpath = "//tbody/tr[1]/td[11]")
    private WebElementFacade firstRowErrors;

    Actions actions;
    FileHelper fileHelper = new FileHelper();

    public void clickRefreshButton()
    {
        clickOn(refreshButton);
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
            case "STATUS": return firstRowStatus.getText().trim();
            case "REPLACED": return firstRowReplaced.getText().trim();
            case "TOTAL": return firstRowTotal.getText().trim();
            case "ERRORS": return firstRowErrors.getText().trim();
            case "ADDED": return firstRowAdded.getText().trim();
        }
        return "";
    }

    public void doubleClickFirstRow()
    {
        actions = new Actions(getDriver());
        actions.doubleClick(firstDataRow).perform();
        waitFor(watchReviewBatchTitle).waitUntilVisible();
    }

    public void clickAutoCheckButton()
    {
        clickOn(autoCheckButton);
    }

    public void selectARecord(String toSelect) {
        WebElementFacade recordToSelect = find("//td[normalize-space()='"+toSelect+"']/..");
        clickOn(recordToSelect);
    }

    public void getDateTimeFromExcel() throws IOException {
        String dateField = "";
        String timeField = "";
        FileInputStream fis = new FileInputStream(fileHelper.getRecentFileFromDownloads());

        XSSFWorkbook workbook = new XSSFWorkbook(fis);
        XSSFSheet sheetName = workbook.getSheetAt(0);
        int rowcount = sheetName.getPhysicalNumberOfRows();

        for (int i = 0; i < rowcount && !dateField.contains("Date"); i++) {
            dateField = sheetName.getRow(i).getCell(0).getStringCellValue();
        }

        for (int i = 0; i < rowcount && !dateField.contains("Time"); i++) {
            timeField = sheetName.getRow(i).getCell(0).getStringCellValue();
        }

        Assert.assertTrue("Date is blank",dateField.length()>4);
        Assert.assertTrue("Time is blank",timeField.length()>4);

    }

    public List<List<String>> getWatchlistErrorsFromExcel() throws IOException {
        FileInputStream fis = new FileInputStream(fileHelper.getRecentFileFromDownloads());

        XSSFWorkbook workbook = new XSSFWorkbook(fis);
        XSSFSheet sheetName = workbook.getSheetAt(0);
        int rowcount = sheetName.getPhysicalNumberOfRows();
        int colcount = sheetName.getRow(0).getPhysicalNumberOfCells();

        List<List<String>> allTableData = new ArrayList<>();

        List<String> RowData = new ArrayList<String>();
        //get header from 3rd column to 8th
        for (int i = 2; i < 8; i++) {

            String testdata1 = "";
            testdata1 = sheetName.getRow(0).getCell(i).getStringCellValue();
            RowData.add(testdata1);
        }
        allTableData.add(RowData);

        //get table data
        for (int i = 2; i < rowcount; i++) {
            RowData = new ArrayList<String>();
            for (int j = 2; j < colcount; j++) {
                String testdata1 = "";
                if(!(sheetName.getRow(i).getCell(j) == null ))
                    testdata1 = sheetName.getRow(i).getCell(j).getStringCellValue();
                RowData.add(testdata1);
            }
            allTableData.add(RowData);
        }
        return allTableData;
    }

    public List<List<String>> getWatchlistErrorsFromUI() {

        //get header
        List<String> RowData = new ArrayList<String>();
        List<List<String>> allTableData = new ArrayList<>();
        List<WebElementFacade> columns = findAll("//th");
        for(WebElementFacade column:columns)
        {
            RowData.add(column.getText());
        }
        allTableData.add(RowData);

        // get table data
        List<WebElementFacade> rows = findAll("//tbody//tr");
        for(int i=1;i<=rows.size();i++)
        {
            RowData = new ArrayList<String>();
            List<WebElementFacade> data = findAll("//tbody/tr["+i+"]//td");
            for (int j=0;j<columns.size();j++) {
                RowData.add(data.get(j).getText());
            }
            allTableData.add(RowData);
        }
        return allTableData;
    }

    public void selectABatchRecord() {
        clickOn(firstDataRow);
    }

    public void clickReviewErrorsButton()
    {
        clickOn(toggleButton);
        clickOn(reviewErrorsButton);

    }

    public void clickDeleteAllErrorsButton()
    {
        clickOn(toggleButton);
        clickOn(deleteAllErrorsButton);
    }

    public void clickYesButtonOnConfirmDelete()
    {
        clickOn(confirmDeleteYesButton);
        waitFor(messageBanner).waitUntilVisible();
    }
}
