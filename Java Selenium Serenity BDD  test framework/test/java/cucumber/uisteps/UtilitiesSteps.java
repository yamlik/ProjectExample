package com..cucumber.uisteps;

import com..test.pages.utilities.SystemImport;
import com..test.utilities.ServiceHelper;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.json.simple.parser.ParseException;

import java.awt.*;

public class UtilitiesSteps extends BaseUISteps {

    private SystemImport systemImport;

    @When("^I upload the required file$")
    public void iUploadTheRequiredFile() throws AWTException {
        systemImport.clickOnUploadFileButton();
        systemImport.clickOnBrowse();
        systemImport.clickUploadButton();

    }

    @And("^I import the attached file into the system$")
    public void iImportTheAttachedFileIntoTheSystem() {
        systemImport.importFile();
    }

    @Then("^I should be able to verify that the import is suTSNTssful$")
    public void iShouldBeAbleToVerifyThatTheImportIsSuTSNTssful() {
       systemImport.verifyDataImport();
    }

    @Then("^I import the above exported file$")
    public void iImportTheAboveExportedFile() {
        systemImport.uploadFile();
        systemImport.getDefaultDownloadFolder();
        systemImport.clickUploadButton();
        systemImport.importFile();
    }

    @And("^I \"([^\"]*)\" service named \"([^\"]*)\"$")
    public void iPerformActionOnServiceNamed( String operationName, String serviceName ) throws ParseException, InterruptedException {

        ServiceHelper svc = new ServiceHelper();
        if(operationName.equals("start") || operationName.equals("stop")) {
            svc.servicePooling(operationName, serviceName);
        }
        if(operationName.equals("restart")){
            svc.servicePooling("stop", serviceName);
            waitABit(3000);
            svc.servicePooling("start", serviceName);
        }

    }

    @And("^I restart batch service$")
    public void iRestartBatchService() throws ParseException, InterruptedException {
        ServiceHelper svc = new ServiceHelper();
        svc.servicePooling("stop", svc.batchServiceName);
        waitABit(3000);
        svc.servicePooling("start", svc.batchServiceName);
        }

    @And("^I restart online service$")
    public void iRestartOnlineService() throws ParseException, InterruptedException {
        ServiceHelper svc = new ServiceHelper();
        svc.servicePooling("stop", svc.onlineServiceName);
        waitABit(3000);
        svc.servicePooling("start", svc.onlineServiceName);
    }

    @And("^I restart communication service$")
    public void iRestartCommunicationService() throws ParseException, InterruptedException {
        ServiceHelper svc = new ServiceHelper();
        svc.servicePooling("stop", svc.communicationServiceName);
        waitABit(3000);
        svc.servicePooling("start", svc.communicationServiceName);
    }

    @And("^I restart action service$")
    public void iRestartActionService() throws ParseException, InterruptedException {
        ServiceHelper svc = new ServiceHelper();
        svc.servicePooling("stop", svc.actionServiceName);
        waitABit(3000);
        svc.servicePooling("start", svc.actionServiceName);
    }
}