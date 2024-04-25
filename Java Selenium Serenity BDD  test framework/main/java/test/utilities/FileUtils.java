package com..test.utilities;

import com..test.database.DataBaseClient;
import com..test.pages.BasePage;
import net.serenitybdd.core.annotations.findby.By;
import net.thucydides.core.annotations.Shared;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Assert;
import org.openqa.selenium.WebElement;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.*;

public class FileUtils extends BasePage {
    @Shared
    private ExcelUtils excelUtils;
    private com..test.context.constants.SQLConstants SQLConstants;
    private DataBaseClient dbClient = new DataBaseClient();

    public List<List<String>> get_Excel_File_Data() throws IOException {
        String Path = "";
        Path downloadsDir = Paths.get(System.getProperty("user.home"), "Downloads");
        File folder = new File(downloadsDir.toString());
        File[] files = folder.listFiles();
        assert files != null;
        Arrays.sort(files, (o1, o2) -> Long.compare(o2.lastModified(), o1.lastModified()));
        FileInputStream fis = new FileInputStream(files[0]);

        XSSFWorkbook workbook = new XSSFWorkbook(fis);
        XSSFSheet sheetName = workbook.getSheetAt(0);
        int rowcount = sheetName.getPhysicalNumberOfRows();
        int colcount = sheetName.getRow(0).getPhysicalNumberOfCells();
        List<List<String>> allTableData = new ArrayList<>();
        for (int i = 0; i < rowcount; i++) {
            List<String> RowData = new ArrayList<String>();
            for (int j = 5; j < colcount; j++) {
                String testdata1 = "";
                if(!(sheetName.getRow(i).getCell(j) == null || sheetName.getRow(i).getCell(j).getCellType() == CellType.BLANK))
                    testdata1 = sheetName.getRow(i).getCell(j).getStringCellValue();
                //System.out.println("Test data from excel cell  :" + testdata1);
                if (!testdata1.isEmpty())
                    RowData.add(testdata1);
            }
            if (!RowData.isEmpty())
                allTableData.add(RowData);
        }
        return allTableData;
    }

    public HashMap<String, String> get_rule_code_criteria() throws IOException {
        String Path = "";
        Path downloadsDir = Paths.get(System.getProperty("user.home"), "Downloads");
        File folder = new File(downloadsDir.toString());
        File[] files = folder.listFiles();
        assert files != null;
        Arrays.sort(files, (o1, o2) -> Long.compare(o2.lastModified(), o1.lastModified()));
        FileInputStream fis = new FileInputStream(files[0]);
        XSSFWorkbook workbook = new XSSFWorkbook(fis);
        XSSFSheet sheetName = workbook.getSheetAt(0);
        int rowcount = sheetName.getPhysicalNumberOfRows();
        HashMap<String, String> result = new HashMap<String, String>();
        for (int i = 0; i < rowcount; i++) {
            String ruleCode = sheetName.getRow(i).getCell(0).getStringCellValue();
            if (ruleCode.contains("Rule Code")) {
                String[] ruleCodeValue = ruleCode.split(":");
                result.put("Rule Code", ruleCodeValue[1]);
            } else if (ruleCode.contains("Criteria")) {
                String[] criteria = ruleCode.split(":");
                result.put("Criteria", criteria[1]);
            } else if (ruleCode.contains("Average Time")) {
                String[] time = ruleCode.split(":", 2);
                result.put("Average Time", time[1]);
            }
        }
        return result;
    }

    public HashMap<String, String> get_UI_rule_code_criteria() {
        HashMap<String, String> result = new HashMap<String, String>();
        WebElement rule= getDriver().findElement(By.xpath("//div[contains(text(),'Rule Code')]//following::span[1]"));
        String r= rule.getText();
        result.put("Rule Code",r);

        WebElement rule_Criteria = getDriver().findElement(By.xpath("//div[contains(text(),'Criteria')]//following::span[1]"));
        String s=rule_Criteria.getText();
        result.put("Criteria",s);

        WebElement Time = getDriver().findElement(By.xpath("//*[@id='page-content-wrapper']//following::section/div/div/div[2]/div/div[2]"));
        String[] formatted_time;
        formatted_time = Time.getText().split("\\s");
        String result_time = "";
        for(int i=2; i<formatted_time.length;i++){
            result_time +=  formatted_time[i];
        }
        result_time = result_time.replace("("," ");
        result_time = result_time.replace(")","");
        result_time = result_time.replace(".",":");
        result_time = result_time.replace("]","]");
        result.put("Average Time", result_time);

        return result;
    }

    public List<List<String>> get_Table_Rule_Data() {
        WebElement rule_table = getDriver().findElement(By.xpath("//table[@id='rule-group-table']"));
        List<WebElement> rows = rule_table.findElements(By.tagName("tr"));
        List<String> RowData = new ArrayList<String>();
        List<List<String>> allTableData = new ArrayList<>();
        List<WebElement> columns = rows.get(0).findElements(By.tagName("th"));
        for (int cnum = 3; cnum < columns.size(); cnum++) {
            String cellValue = columns.get(cnum).getText();
            RowData.add(cellValue);
        }
        allTableData.add(RowData);
        for (int rownum = 1; rownum < rows.size(); rownum++) {
            List<String> tablerowData = new ArrayList<String>();
            List<WebElement> othercolumns = rows.get(rownum).findElements(By.tagName("td"));
            for (int cnum = 3; cnum < othercolumns.size(); cnum++) {
                String cellValue = othercolumns.get(cnum).getText();
                tablerowData.add(cellValue);
            }
            allTableData.add(tablerowData);
        }
        return allTableData;
    }

    public void compare_Data(List<List<String>> UIData, List<List<String>> excelData, HashMap<String, String> rule_code_criteria_data, HashMap<String, String> UI_rule_code_criteria_data) throws IOException, InvalidFormatException {
        //comparing code/criteria/time
//        for(String key : rule_code_criteria_data.keySet())
//        {

//            Assert.assertEquals(UI_rule_code_criteria_data.get(key).toString(), rule_code_criteria_data.get(key).toString());

//        }

        //comparing data
        Assert.assertTrue(UIData.size() == excelData.size());
        Assert.assertTrue("UI and excel Data doesn't match", UIData.containsAll(excelData) && excelData.containsAll(UIData));
    }

    public List<Map<String, String>> get_Excel_Sheet_Data() throws IOException, InvalidFormatException {

        File file = new File(dataDir + "Excel_Data/Field Requirements_Application.xlsx");
        XSSFWorkbook workbook = new XSSFWorkbook(file);
        XSSFSheet sheetName = workbook.getSheetAt(0);
        int rowcount = sheetName.getPhysicalNumberOfRows();
        int colcount = sheetName.getRow(0).getPhysicalNumberOfCells();
        List<Map<String, String>> result = new ArrayList<Map<String, String>>();
        Map<String, String> excel_data = new HashMap<String, String>();

        for(int row=1; row < rowcount; row++){
            for(int i=0; i < colcount ; i++){
                if(sheetName.getRow(0).getCell(i).getStringCellValue().equals("Surname")){
                    if(sheetName.getRow(row).getCell(i) == null || sheetName.getRow(row).getCell(i).getCellType() == CellType.BLANK)
                        excel_data.put("Surname", "null");
                    else
                        excel_data.put("Surname", sheetName.getRow(row).getCell(i).getStringCellValue());

                }
                else if(sheetName.getRow(0).getCell(i).getStringCellValue().equals("First Name")) {
                    if(sheetName.getRow(row).getCell(i) == null || sheetName.getRow(row).getCell(i).getCellType() == CellType.BLANK)
                        excel_data.put("First Name", "null");
                    else
                        excel_data.put("First Name", sheetName.getRow(row).getCell(i).getStringCellValue());
                }
                else if(sheetName.getRow(0).getCell(i).getStringCellValue().equals("Application Number -  Application  Number")) {
                    if(sheetName.getRow(row).getCell(i) == null || sheetName.getRow(row).getCell(i).getCellType() == CellType.BLANK)
                        excel_data.put("Application Number -  Application  Number", "null");
                    else
                        excel_data.put("Application Number -  Application  Number", sheetName.getRow(row).getCell(i).getStringCellValue());
                }
                else if(sheetName.getRow(0).getCell(i).getStringCellValue().equals("Home Address 1")) {
                    if(sheetName.getRow(row).getCell(i) == null || sheetName.getRow(row).getCell(i).getCellType() == CellType.BLANK)
                        excel_data.put("Home Address 1", "null");
                    else
                        excel_data.put("Home Address 1", sheetName.getRow(row).getCell(i).getStringCellValue());
                }
                else if(sheetName.getRow(0).getCell(i).getStringCellValue().equals("Company Address 1")) {
                    if(sheetName.getRow(row).getCell(i) == null || sheetName.getRow(row).getCell(i).getCellType() == CellType.BLANK)
                        excel_data.put("Company Address 1", "null");
                    else
                        excel_data.put("Company Address 1", sheetName.getRow(row).getCell(i).getStringCellValue());
                }
            }
            result.add(excel_data);
        }
        return result;
    }

    public  List<Map<String, String>> get_values_From_database(List<Map<String, String>> excelResult) throws SQLException {
        dbClient.connectToDataBase();
        String application_no = excelResult.get(0).get("Application Number -  Application  Number");
        String sql_Query=SQLConstants.query1;
        String query = sql_Query +"'"+ application_no + "'";
    query.replaceAll("","");
//        String query = "select full_name,Full_Company_Address,Full_Home_Address from a_applicant where Application_Number='" + application_no + "'";
      String replace=  query.replaceAll("^\"|\"$", "");
       dbClient.executeSelect(replace);
        List<Map<String, String>> result = dbClient.getResultSetAsMapList();
        return result;
    }

    public void compare_excel_db_data(List<Map<String, String>> excel_result, List<Map<String, String>> db_result) {
        for(Map<String, String> data : excel_result)
        {
            String fullNameFromExcel = data.get("First Name")+ " " + data.get("Surname");
            for(Map<String, String> dbData : db_result)
            {
                Assert.assertEquals(fullNameFromExcel.toUpperCase(), dbData.get("full_name"));
                Assert.assertNull("Full Home address is not Null",dbData.get("Full_Home_Address"));
                Assert.assertNull("Full Company address is not Null",dbData.get("Full_Company_Address"));
            }
        }
    }


}
