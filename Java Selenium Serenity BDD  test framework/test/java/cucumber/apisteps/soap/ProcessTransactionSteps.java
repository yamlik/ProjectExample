package com..cucumber.apisteps.soap;

import com..test.context.ProcessTransactionContext;
import com..test.models.ProcessTransaction;
import com..test.soaphandlers.ProcessTransactionHandler;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import net.thucydides.core.annotations.Shared;
import org.json.simple.parser.ParseException;
import org.junit.Assert;

import java.io.IOException;

public class ProcessTransactionSteps extends BaseSOAPSteps {

    private final ProcessTransactionHandler processTransactionHandler = new ProcessTransactionHandler();


    @Shared
    ProcessTransactionContext processTransactionContext;

    public ProcessTransaction getProcessTransaction() throws IOException, ParseException {
        String requestBody = scenarioContext.getDataDriveAsString();
        return GSON.fromJson(requestBody, ProcessTransaction.class);
    }

    public ProcessTransaction getProcessTransaction(String section) throws IOException, ParseException {
        String requestBody = scenarioContext.getDataDriveSectionAsString(section);
        return GSON.fromJson(requestBody, ProcessTransaction.class);
    }

    @Given("^I submit an example Process Transaction$")
    public void iSubmitAnExampleProcessTransaction() {
        processTransactionHandler.setChannel("C03");
        processTransactionHandler.setTransactionDateTime(getDateTime());
        processTransactionHandler.setCustomerNo("CUSTOMER00001");
        processTransactionHandler.setStaffID("STAFF00001");
        processTransactionHandler.setAccountNo("ACCOUNT00001");
        processTransactionHandler.setSenderAccount("SENDER00001");
        processTransactionHandler.setTransactionType("067");
        String requestBody = processTransactionHandler.createProcessTransactionRequest();
        response = request().headers(setHeaders()).body(requestBody).post(baseURI);
        Assert.assertEquals("Process Transaction attempt did not return status code 200 as expected", 200, response.getStatusCode());
    }

    @Then("^The correct error message should be returned$")
    public void theCorrectErrorMessageShouldBeReturned() throws IOException, ParseException {
        String expected = scenarioContext.getDataDriveSectionAsString("Error Message");
        Assert.assertTrue("", response.getBody().asString().contains(expected));
    }
}
