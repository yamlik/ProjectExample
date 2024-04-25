package com..cucumber.uisteps.utilities;

import com..cucumber.uisteps.BaseUISteps;
import com..test.pages.utilities.SystemExport;
import com..test.pages.utilities.SystemImport;
import com..test.utilities.JsHelper;
import cucumber.api.PendingException;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.apache.commons.io.FileUtils;
import org.jruby.RubyProcess;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.JavascriptExecutor;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

public class SystemExportSteps extends BaseUISteps {

    private SystemExport systemExport = new SystemExport();
    private SystemImport systemImport = new SystemImport();
    private JsHelper jsHelper = new JsHelper();

    @And("^Export the System file$")
    public void exportTheSystemFile() {
        systemExport.clickExportButton();
    }

    @Then("^I copy the \"([^\"]*)\" export file to the Exports folder$")
    public void iCopyTheExportFileToTheExportsFolder(String sysFile) throws IOException {

        Path downloadsDir = Paths.get(System.getProperty("user.home"), "Downloads");
        File destDir = new File(dataDir+"SystemExports/" +sysFile);

        //get the most recent file
        File folder = new File(downloadsDir.toString());
        File[] files = folder.listFiles();
        assert files != null;
        Arrays.sort(files, (o1, o2) -> Long.compare(o2.lastModified(), o1.lastModified()));

        FileUtils.copyFile(files[0],destDir);
    }

    @And("^I (choose|again choose) \"([^\"]*)\" = \"([^\"]*)\"$")
    public void iChoose(String choice, String data, String criteria)  {
        if(choice.contains("again")){
            systemExport.clickSelectRecordsButton();
        }
        else{
            systemImport.clickFilterButton();
            systemImport.enterImportDataInFilterPopup(data);
            systemImport.clickApplyButtonInFilterPopup();
            systemImport.selectImportCriteriaOption(data,criteria);
        }
    }

    @When("^I upload the above exported file$")
    public void i_upload_the_above_exported_file() {
        systemImport.uploadFile();
        systemImport.getDefaultDownloadFolder();
        systemImport.clickUploadButton();
    }

    @When("^I (select|de-select) \"([^\"]*)\" to export$")
    public void iSelectToExport(String choice, String productUnderTestData) throws InterruptedException {
        waitFor(systemExport.selectAllCheckBox).waitUntilClickable();

        systemImport.clickFilterButton();
        systemExport.enterproductUnderTestDataInFilterPopup(productUnderTestData);
        systemImport.clickApplyButtonInFilterPopup();

        String isChecked = jsHelper.getPropertyById(getDriver(), systemExport.selectAllHiddenCheckbox, "checked");
        if(isChecked.equals("true"))
            systemExport.clickSelectAllCheckbox();
        systemExport.clickImportData(choice, productUnderTestData);
    }

    @And("^I (deselect|select) \"([^\"]*)\" row on Select Records to Export$")
    public void iDeselectRowOnSelectRecordsToExport(String choice, int count) throws IOException, ParseException {
        JSONObject dataDrive = scenarioContext.getDataDriveSectionAsJSON("deselect");
        if(!(dataDrive==null)){
        for (int i=1; i<=count; i++)
            systemExport.selectOrDeselect(choice, dataDrive.get("deselect"+i).toString());
        }

        dataDrive = scenarioContext.getDataDriveSectionAsJSON("select");
        if(!(dataDrive==null)){
        for (int i=1; i<=count; i++)
            systemExport.selectOrDeselect(choice, dataDrive.get("select"+i).toString());
        }

        systemExport.clickOkButtonOnSelectPopup();
    }

    @Then("^I verify the records selected on System Export$")
    public void iVerifyTheRecordsSelectedOnSystemExport() throws IOException, ParseException {
        JSONObject dataDrive = scenarioContext.getDataDriveSectionAsJSON("deselect");
        if(!(dataDrive==null)){
        for (int i=1; i<= dataDrive.size(); i++)
            systemExport.verifySelectOrDeselect("deselect", dataDrive.get("deselect"+i).toString());
        }

        dataDrive = scenarioContext.getDataDriveSectionAsJSON("select");
        if(!(dataDrive==null)){
            for (int i=1; i<= dataDrive.size(); i++)
                systemExport.verifySelectOrDeselect("select", dataDrive.get("select"+i).toString());
        }
    }
}
