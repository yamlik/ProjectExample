package com..cucumber.uisteps.commonSteps;

import com..cucumber.uisteps.BaseUISteps;
import com..test.pages.commonPages.Registration;
import com..test.utilities.JSONHelper;
import cucumber.api.java.en.Then;
import net.thucydides.core.annotations.Shared;
import org.json.JSONObject;

import java.io.IOException;


public class productUnderTestRegistration  extends BaseUISteps {

    @Shared
    private Registration registration;


    @Then("^I should see productUnderTestRegistration PopupWindow$")
    public void iShouldSeeproductUnderTestRegistrationPopupWindow() throws InterruptedException, IOException {
        JSONObject requestBody = JSONHelper.messageAsActualJson(dataDir + "valid/productUnderTestKey.json");
        registration.clickproductUnderTestRegistration();
        registration.enterRegistrationKey(requestBody.getString("licenceKey"));
        registration.clickSave();
        registration.yesButton();
         }

    @Then("^I close the productUnderTestRegistration PopupWindow without supplying a Licence Key$")
    public void iClickCloseproductUnderTestRegistrationPopupWindow() throws InterruptedException, IOException {
        registration.clickClosePopUpWindow();
    }
}