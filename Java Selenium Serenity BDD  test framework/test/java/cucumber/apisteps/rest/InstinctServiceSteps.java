package com..cucumber.apisteps.rest;

import com..cucumber.uisteps.BaseUISteps;
import com..test.modules.services.productUnderTestServiceModule;
import net.thucydides.core.annotations.Steps;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import org.json.JSONObject;
import org.junit.Assert;


public class productUnderTestServiceSteps extends BaseUISteps {

    @Steps
    private productUnderTestServiceModule productUnderTestServiceModule;

    @Given("^I create a POST request using productUnderTest Service with application number \"([^\"]*)\"$")
    public void iCreateAPOSTRequestUsingproductUnderTestServiceWithApplicationNumber(String applicationNumber) throws Throwable {
        productUnderTestServiceModule.createPostApplication(applicationNumber);
    }

    @Then("^I verify the GET request using productUnderTest Service with application number \"([^\"]*)\"$")
    public void IVerifyTheGETRequestUsingproductUnderTestServiceWithApplicationNumber(String applicationNumber) throws Throwable {
       JSONObject productUnderTestServiceReturn = productUnderTestServiceModule.createGetApplication(applicationNumber);


        System.out.println(productUnderTestServiceReturn.getJSONObject("Application").get("Application_Number").toString());

       Assert.assertEquals(productUnderTestServiceReturn.getJSONObject("Application").get("Application_Number").toString(), applicationNumber);

    }

    @Given("^I create a PUT request using productUnderTest Service with application number \"([^\"]*)\" and income \"([^\"]*)\"$")
    public void iCreateAPUTRequestUsingproductUnderTestServiceRequestWithNumberAndIncome(String applicationNumber, String income) throws Throwable{
        productUnderTestServiceModule.createPutApplication(applicationNumber, income);
    }


}
