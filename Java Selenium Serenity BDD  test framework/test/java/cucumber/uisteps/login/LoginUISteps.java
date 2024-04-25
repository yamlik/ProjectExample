package com..cucumber.uisteps.login;

import com..cucumber.uisteps.BaseUISteps;
import com..test.context.LoginContext;
import com..test.context.ScenarioContext;
import com..test.models.LoginInformation;
import com..test.pages.login.Login;
import com..test.pages.commonPages.ProfileDropDown;
import com..test.pages.commonPages.Registration;
import com..test.pages.commonPages.WhatsNewPage;
import com..test.pages.security.Users;
import com..test.utilities.JSONHelper;
import com.google.common.base.Enums;
import com.google.gson.Gson;
import cucumber.api.PendingException;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import net.thucydides.core.annotations.Shared;
import org.apache.commons.io.FileUtils;
import org.jruby.RubyProcess;
import org.json.JSONObject;
import org.json.simple.parser.ParseException;
import com..test.enums.AllEnums;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;

public class LoginUISteps extends BaseUISteps {
    private static final Gson GSON = new Gson();

    @Shared
    private LoginContext loginContext;
    WhatsNewPage whatsNewPage;
    private Login login;
    private Users user;
    private ProfileDropDown profileDropDown;
    private Registration registration;

    public LoginInformation getLoginInformation(String loginData) throws IOException {

        String filePath = dataDir + loginData;
        return GSON.fromJson(
                FileUtils.readFileToString(new File(filePath),
                        String.valueOf(Charset.defaultCharset())), LoginInformation.class);
    }

    @Given("^I login to the portal$")
    public void loginToApplication() throws Exception {

        JSONObject requestBody = JSONHelper.messageAsActualJson(dataDir + "valid/validLogin.json");
        login.enterUsername(requestBody.getString("username"));
        login.enterPassword(requestBody.getString("password"));
        login.selectDatabase(requestBody.getString("database"));
        login.clickSignIn();
    }

    @Given("^I login to the portal with details defined in \"([^\"]*)\"$")
    public void iLoginToThePortalWithDetailsDefinedIn(String dataFile) throws Throwable {
        LoginInformation loginInformation = this.getLoginInformation(dataFile);
        loginContext.setLoginInformation(loginInformation);
        login.selectDatabase(loginInformation.getDatabase());
        login.enterUsername(loginInformation.getUsername());
        login.enterPassword(loginInformation.getPassword());
        login.clickSignIn();
    }

    @And("^I re-login into the application$")
    public void iReLoginIntoTheApplication() throws IOException, InterruptedException {
        profileDropDown.clickProfileDropDownButton();
        profileDropDown.clickLogoutButton();
        getDriver().manage().timeouts().pageLoadTimeout(50, TimeUnit.SECONDS);
        getDriver().manage().timeouts().implicitlyWait(50, TimeUnit.SECONDS);
        JSONObject requestBody = JSONHelper.messageAsActualJson(dataDir + "valid/validLogin.json");
        login.enterUsername(requestBody.getString("username"));
        login.enterPassword(requestBody.getString("password"));
        login.selectDatabase(requestBody.getString("database"));
        login.clickSignIn();
        registration.clickClosePopUpWindow();
    }

    @And("^I re-login into the application with AD$")
    public void iReLoginIntoTheApplicationWithAD() throws IOException, InterruptedException {
        profileDropDown.clickProfileDropDownButton();
        profileDropDown.clickLogoutButton();
        getDriver().manage().timeouts().pageLoadTimeout(50, TimeUnit.SECONDS);
        getDriver().manage().timeouts().implicitlyWait(50, TimeUnit.SECONDS);
        waitFor(login.signInBtnText);
        clickOn(login.signInBtnText);
        registration.clickClosePopUpWindow();
        whatsNewPage.makeAChoice("No thanks");
    }

    @And("^I re-login into the application with \"([^\"]*)\"$")
    public void iReLoginIntoTheApplicationWith(String userName) throws Throwable {
        profileDropDown.clickProfileDropDownButton();
        profileDropDown.clickLogoutButton();
        getDriver().manage().timeouts().pageLoadTimeout(50, TimeUnit.SECONDS);
        getDriver().manage().timeouts().implicitlyWait(50, TimeUnit.SECONDS);
        JSONObject requestBody = JSONHelper.messageAsActualJson(dataDir + "valid/NewUserWithOthersApproval.json");
        login.enterUsername(userName);
        login.enterPassword(requestBody.getString("password"));
        login.selectDatabase(requestBody.getString("database"));
        login.clickSignIn();
        registration.clickClosePopUpWindow();
        login.enterOldPassword(requestBody.getString("password"));
        login.enterNewPassword(requestBody.getString("newPassword"));
        login.confirmNewPassword(requestBody.getString("newPassword"));
        user.change();
        waitABit(1000);
        // This will pass only one time in local, as from next time New user ("No thanks") popup will not come up.
        // This has been kept uncommented keeping in view of the pipeline as in pipeline everytime this popup comes
        whatsNewPage.makeAChoice("No thanks");
    }

    @And("^I login again into the application with \"([^\"]*)\"$")
    public void iLoginAgainIntoTheApplicationWith(String userName) throws Throwable {
        profileDropDown.clickProfileDropDownButton();
        profileDropDown.clickLogoutButton();
        getDriver().manage().timeouts().pageLoadTimeout(50, TimeUnit.SECONDS);
        getDriver().manage().timeouts().implicitlyWait(50, TimeUnit.SECONDS);
        JSONObject requestBody = JSONHelper.messageAsActualJson(dataDir + "valid/NewUserWithOthersApproval.json");
        login.enterUsername(userName);
        login.enterPassword(requestBody.getString("newPassword"));
        login.selectDatabase(requestBody.getString("database"));
        login.clickSignIn();
        registration.clickClosePopUpWindow();
      
    }

    @Then("^I re-login (for the first time|again) into the application as \"([^\"]*)\"$")
    public void iReLoginIntoTheApplicationAs(String choice, String username) throws IOException, InterruptedException, ParseException {
        profileDropDown.clickProfileDropDownButton();
        profileDropDown.clickLogoutButton();
        getDriver().manage().timeouts().pageLoadTimeout(50, TimeUnit.SECONDS);
        getDriver().manage().timeouts().implicitlyWait(50, TimeUnit.SECONDS);
        org.json.simple.JSONObject dataDrive = scenarioContext.getDataDriveSectionAsJSON(username);
        login.enterUsername(username);
        login.enterPassword(dataDrive.get("password").toString());
        login.selectDatabase(dataDrive.get("database").toString());
        login.clickSignIn();
        registration.clickClosePopUpWindow();
        if(choice.equalsIgnoreCase("for the first time")){
            login.enterOldPassword(dataDrive.get("password").toString());
            login.enterNewPassword(dataDrive.get("newPassword").toString());
            login.confirmNewPassword(dataDrive.get("newPassword").toString());
            user.change();
            waitABit(1000);
        }
        if(!AllEnums.containsAdministrators(username)) {
            if (ScenarioContext.getTSNTance().getUser1().equals(""))
                ScenarioContext.getTSNTance().setUser1(username);
            else if (ScenarioContext.getTSNTance().getUser2().equals(""))
                ScenarioContext.getTSNTance().setUser2(username);
        }
        // This will pass only one time in local, as from next time New user ("No thanks") popup will not come up.
        // This has been kept uncommented keeping in view of the pipeline as in pipeline everytime this popup comes
        if(choice.equalsIgnoreCase("for the first time")){
            waitFor(whatsNewPage.popUp);
            whatsNewPage.makeAChoice("No thanks");
        }
    }
}
