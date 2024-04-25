package com..cucumber.uisteps.rules;
import com..cucumber.uisteps.BaseUISteps;
import com..test.database.DataBaseClient;
import com..test.pages.application.ApplicationRecordPage;
import com..test.pages.rules.RuleReferenceMaintenence;

import cucumber.api.java.en.And;
import com..test.pages.rules.RulesConfigurationPage;
import cucumber.api.java.en.When;
import net.thucydides.core.annotations.Shared;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import com..test.utilities.FileHelper;
import org.junit.Assert;
import org.openqa.selenium.By;

import java.io.*;
import java.util.Scanner;

public class RulesReferenceMaintenanceSteps extends BaseUISteps {

    @Shared

    private RulesConfigurationPage rulesConfiguration;
    private RuleReferenceMaintenence ruleReferenceMaintenence;
    private ApplicationRecordPage applicationRecordPage;
    private com..test.utilities.FileUtils FileUtils;
    FileHelper fileHelper = new FileHelper();
    DataBaseClient dataBaseClient =new DataBaseClient();

    @And("^I search Reference Maintenance table with table name of \"([^\"]*)\"$")
    public void iSearchReferenceMaintenanceWithNameOf(String tableName) throws IOException, ParseException {
        clickOn(ruleReferenceMaintenence.filterButton);
        JSONObject requestBody = scenarioContext.getDataDriveSectionAsJSON(tableName);
        String tableId = requestBody.get("TableId").toString();
        typeInto(ruleReferenceMaintenence.tableIdInput, tableId);
        clickOn(ruleReferenceMaintenence.filterApplyButton);
        waitABit(1000);
    }

    @And("^I search Reference Maintenance table named \"([^\"]*)\" in Table Status$")
    public void iSearchReferenceMaintenanceTableNamedInTableStatus(String tableName) throws  IOException, ParseException {
        JSONObject requestBody = scenarioContext.getDataDriveSectionAsJSON(tableName);
        String tableId = requestBody.get("TableId").toString();
        String tableFullName = requestBody.get("TableName").toString();
        clickOn(ruleReferenceMaintenence.addButtonDropDown);
        clickOn(ruleReferenceMaintenence.addButtonDropDownTableStatus);
        clickOn(ruleReferenceMaintenence.tableStatusFilterButton);
        typeInto(ruleReferenceMaintenence.tableIdInput, tableId);
        clickOn(ruleReferenceMaintenence.tableStatusFilterApplyButton);
    }

    @When("^I add a new reference table table \"([^\"]*)\"$")
    public void iAddANewReferenceTable(String tableDetails) throws IOException, ParseException {
        JSONObject requestBody = scenarioContext.getDataDriveSectionAsJSON(tableDetails);
        //JSONObject requestBody = scenarioContext.
        ruleReferenceMaintenence.clickAdd();
        ruleReferenceMaintenence.addANewTable(requestBody.get("TableId").toString(),requestBody.get("TableName").toString(),requestBody.get("FileType").toString());

    }

    @And("^I click on change of the reference table named \"([^\"]*)\"$")
    public void iChangeTheReferenceTableNamed(String tableName){
        clickOn(ruleReferenceMaintenence.tableDropdownMenu);
        clickOn(ruleReferenceMaintenence.tableDropdownMenuChange);
    }

    @And("^I generate template of table named \"([^\"]*)\"$")
    public void iGenerateTemplateOfTableNamed(String tableName) throws IOException, ParseException {
        iSearchReferenceMaintenanceWithNameOf(tableName);
        clickOn(ruleReferenceMaintenence.tableDropdownMenu);
        clickOn(ruleReferenceMaintenence.tableDropdownMenuGenerateTemplate);
        waitABit(2000);
    }

    @And("I verify reference table named \"([^\"]*)\" updated correctly in table status")
    public void iVerifyReferenceTableNamedUpdatedCorrectlyInTableStatus(String tableName) throws IOException, ParseException{
        ruleReferenceMaintenence.clickAddButtonDropDownTableStatusButton();
        JSONObject requestBody = scenarioContext.getDataDriveSectionAsJSON(tableName);
        String tableId = requestBody.get("TableId").toString();
        String tableFullName = requestBody.get("TableName").toString();
        String tableStatus = requestBody.get("TableStatus").toString();

        Assert.assertTrue(getDriver().findElement(By.xpath("//td[contains(text(),'" + tableId + "')]")).isDisplayed());
        Assert.assertTrue(getDriver().findElement(By.xpath("//td[contains(text(),'" + tableFullName + "')]")).isDisplayed());
        Assert.assertTrue(getDriver().findElement(By.xpath("//td[contains(text(),'" + tableStatus + "')]")).isDisplayed());
    }

    @And("^I verify header in generated template of XLS or XLSX file is correct with given list \"([^\"]*)\"$")
    public void iVerifyXlsOrXlsxTemplateHeader(String listOfHeaderName) throws IOException, ParseException {
        JSONObject requestBody =  scenarioContext.getDataDriveSectionAsJSON(listOfHeaderName);

        FileInputStream fis = new FileInputStream(fileHelper.getRecentFileFromDownloads());
        XSSFWorkbook workbook = new XSSFWorkbook(fis);

        XSSFSheet sheetName = workbook.getSheetAt(0);
        int rowCount = sheetName.getPhysicalNumberOfRows();
        int colCount;

        for(int i = 0; i < rowCount; i++){
            colCount = sheetName.getRow(i).getPhysicalNumberOfCells();
            for(int q = 0; q < colCount; q++){
                int invalid_counter = 0;
                for(int w = 1; w < requestBody.size(); w++) {
                    if(!sheetName.getRow(i).getCell(q).getStringCellValue().equals(requestBody.get("header" + w ).toString())){
                        invalid_counter++;
                    };
                }
                Assert.assertFalse(invalid_counter == requestBody.size());
            }
        }
    }

    @And("^I verify header in generated template of TXT or CSV file is correct with given list \"([^\"]*)\"$")
    public void iVerifyTxtOrCsvTemplateHeader(String listOfHeaderName) throws IOException, ParseException {
        JSONObject requestBody =  scenarioContext.getDataDriveSectionAsJSON(listOfHeaderName);

        BufferedReader br = new BufferedReader(new FileReader(fileHelper.getRecentFileFromDownloads()));

        String delimiter="";
        if(fileHelper.getRecentFileFromDownloads().getName().contains("csv")){
            delimiter = ",";
        }
        if(fileHelper.getRecentFileFromDownloads().getName().contains("txt")){
            delimiter = "\\|";
        }

        String line = "";

        while ((line = br.readLine()) != null){
            System.out.println(line);
            String[] headers = line.split(delimiter);
              for( String name: headers){
                  int invalid_counter = 0;
                  for(int w = 0; w < requestBody.size(); w++) {
                  if(!name.equals(requestBody.get("header" + w ).toString())){
                      invalid_counter++;
                  }
              }
                  Assert.assertFalse(invalid_counter == requestBody.size());
            }
        }

    }
}
