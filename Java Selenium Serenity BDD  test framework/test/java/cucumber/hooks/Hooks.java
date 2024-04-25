package com..cucumber.hooks;
import com..cucumber.apisteps.rest.BaseRESTSteps;
import com..cucumber.apisteps.rest.LoginRESTSteps;
import com..test.browserstack.BrowserStackSerenityDriver;
import com..test.context.ScenarioContext;
import com..test.database.DataBaseClient;
import com..test.models.HttpResponse;
import com..test.modules.*;
import com..test.pages.commonPages.Navigation;
import com..test.pages.commonPages.Dashboard;
import com..test.pages.commonPages.Registration;
import com..test.pages.login.Login;
import com..test.pages.utilities.SystemImport;
import com..test.utilities.Properties;
import com..test.utilities.JSONHelper;
import com..test.utilities.JsHelper;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.it.Ma;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import net.serenitybdd.core.pages.PageObject;
import net.thucydides.core.annotations.Shared;
import net.thucydides.core.annotations.Steps;
import org.jruby.RubyProcess;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.junit.Assert;
import org.openqa.selenium.JavascriptExecutor;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class Hooks extends PageObject {
    protected final String dataDir = System.getProperty("user.dir") + "/src/main/resources/data/UI/";
    DataBaseClient dataBaseClient =new DataBaseClient();
    @Shared
    ScenarioContext scenarioContext;
    private Login login;
    private Registration registration;
    private Dashboard dashboard;
    @Shared
    private Navigation navigation;
    private SystemImport systemImport;
    @Steps
    private RuleSetModule ruleSetModule;
    @Steps
    private ApplicationModule applicationModule;
    @Steps
    private UserModule userModule;
    @Steps
    private ProfileModule profileModule;
    @Steps
    private RuleModule ruleModule;

    JsonParser jsonParser = new JsonParser();
    JsHelper jsHelper = new JsHelper();

    private static final Properties properties = new Properties("productUnderTest.properties");
    protected final String URL = properties.getProperty("productUnderTest.ui.url");

    private  final String orgCountry = properties.getProperty("productUnderTest.organisation");

    public Hooks() {}

    @Before(order = 1)
    public void setDataDrives(Scenario scenario) {
        ScenarioContext.getTSNTance().setScenarioName(scenario);
    }

    @Before(value = "@SETUP_UI")
    public void iGetAdminATSNTssToken() throws ParseException {ruleSetModule.getATSNTssToken();}

    @Before(value = "@UI")
    public void iGetNonAdminATSNTssToken() throws ParseException {
        ruleSetModule.getNonAdminATSNTssToken();
    }

    @Before(value = "@UI")
    public void iSetDatabaseName() {
        ScenarioContext.getTSNTance().setDatabase();
    }

    @Before
    public void iSetOrganisationCountry() {
        scenarioContext.setCountryCode(orgCountry.substring(orgCountry.lastIndexOf("_") + 1));
        scenarioContext.setOrganisationCode(orgCountry.substring(0,orgCountry.indexOf("_")));
    }


    @Before(value = "@BROWSERSTACK", order = 2)
    public void setTestName(Scenario scenario) {
        BrowserStackSerenityDriver.scenarioName = scenario.getName();
    }

    @Before(value = "@UI", order = 3)
    @Given("^I aTSNTss the login page$")
    public void iATSNTssTheLoginPage() {
        getDriver().manage().deleteAllCookies();
        openUrl(URL);
        getDriver().manage().window().maximize();
        getDriver().manage().deleteAllCookies();
        getDriver().manage().timeouts().pageLoadTimeout(20, TimeUnit.SECONDS);
        getDriver().manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
    }

    @Before(value = "@SETUP_UI", order = 4)
    public void iLoginToproductUnderTestAsAdmin() throws IOException, InterruptedException {
        getDriver().manage().deleteAllCookies();
        openUrl(URL);
        getDriver().manage().window().maximize();
        getDriver().manage().deleteAllCookies();
        getDriver().manage().timeouts().pageLoadTimeout(20, TimeUnit.SECONDS);
        getDriver().manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
        org.json.JSONObject requestBody = JSONHelper.messageAsActualJson(dataDir + "valid/validLogin.json");
        login.enterUsername(requestBody.getString("admin_username"));
        login.enterPassword(requestBody.getString("admin_password"));
        login.selectDatabase(requestBody.getString("database"));
        login.clickSignIn();
        registration.clickClosePopUpWindow();
        jsHelper.setSessionStorage(getDriver(), "noOfResults", "50");

    }

    @Before(value = "@UI", order = 4)
    public void iLoginToproductUnderTestApplication() throws IOException, InterruptedException {
        org.json.JSONObject requestBody = JSONHelper.messageAsActualJson(dataDir + "valid/validLogin.json");
        login.enterUsername(requestBody.getString("username"));
        login.enterPassword(requestBody.getString("password"));
        login.selectDatabase(requestBody.getString("database"));
        login.clickSignIn();
        registration.clickClosePopUpWindow();
        jsHelper.setSessionStorage(getDriver(), "noOfResults", "50");

    }

    @Before(value="@IMPORT_DAT_FILE", order = 5)
    public void importDatFil1e1() throws IOException {
        navigation.clickSidebarOption("utilities");
        navigation.clickUtilitiesSubMenu("systemImport");
        systemImport.clickOnUploadFileButton();
        systemImport.clickOnBrowse();
        systemImport.clickUploadButton();
        systemImport.importFile();
    }



    @After(value = "@DELETE_APPLICATION", order = 1)
    @And("^I delete application record to clean up$")
    public void iDeleteAppRecord() throws ParseException, IOException {
        LoginRESTSteps loginRESTSteps = new LoginRESTSteps();
        loginRESTSteps.iHaveLoggedInWithAStandardUser();
        Map<String, String> headers = BaseRESTSteps.headers;
        JSONObject data = ScenarioContext.getTSNTance().getDataDriveSectionAsJSON("delete");
        String organisationCode = System.getProperty("productUnderTest.organisation").replaceAll("_.*$", "");
        String countryCode = System.getProperty("productUnderTest.organisation").replaceAll("^.*?_", "");
        Response delete = RestAssured.given().headers(headers).body(JSONHelper.parseJSONObject("{\"appKey\":\"" + data.get("AppKey").toString().replace("{{organisation}}", organisationCode).replace("{{country}}", countryCode) + "\"}")).post(URL + "api/applicationMaintenance/deleteApplication");
        Assert.assertEquals("API request to delete application was not suTSNTssful", 200, delete.statusCode());
    }

    @After(value = "@DELETE_RULE_SET", order = 2)
    public void iGetAllTheRuleSetFromTheApplicationAndDeleteThem() throws IOException {
        Response  response= ruleSetModule.getRuleSet();
        List<String> ruleSetName = response.jsonPath().getList("RuleSetList.RuleSetName");
        for (int i = 0; i < ruleSetName.size(); i++) {
            ruleSetModule.deleteRuleSet(ruleSetName.get(i));
        }
    }
    @After(value = "@DELETE_RULE", order = 2)
    public void iGetAllTheRulesFromTheApplicationAndDeleteThem() throws IOException {
        iGetAllTheRuleSetFromTheApplicationAndDeleteThem();
        ruleModule.deleteRule();

    }

    @After(value = "@DELETE_ALL_APPLICATION", order = 2)
    public void iDeleteAllTheApplicationCreated() throws IOException {
        applicationModule.deleteAllApplication();
    }

    @After(value = "@DELETE_ALL_WATCHLIST", order = 2)
    public void iDeleteAllTheWatchlistCreated() throws IOException {
        applicationModule.deleteAllWatchlist();
    }

    @After(value = "@DELETE_USERS", order = 2)
    public void iDeleteUsersCreated() throws IOException {
        String userName = ScenarioContext.getTSNTance().getUser1();
        if(userName != null) {
            userModule.deleteCreatedUser(userName);
            ScenarioContext.getTSNTance().setUser1("");
        }
        userName = ScenarioContext.getTSNTance().getUser2();
        if(userName != null) {
            userModule.deleteCreatedUser(userName);
            ScenarioContext.getTSNTance().setUser2("");
        }
        userName = ScenarioContext.getTSNTance().getUser3();
        if(userName != null) {
            userModule.deleteCreatedUser(userName);
        }
        userName = ScenarioContext.getTSNTance().getUser4();
        if(userName != null) {
            userModule.deleteCreatedUser(userName);
        }
    }

    @After(value = "@DELETE_PROFILE", order = 2)
    public void iDeleteProfilesCreated() throws IOException {
        String profileName = ScenarioContext.getTSNTance().getProfile1();
        if(profileName != null) {
            profileModule.deleteCreatedProfile(profileName);
            ScenarioContext.getTSNTance().setProfile1("");
        }
        profileName = ScenarioContext.getTSNTance().getProfile2();
        if(profileName != null) {
            profileModule.deleteCreatedProfile(profileName);
            ScenarioContext.getTSNTance().setProfile2("");
        }
        profileName = ScenarioContext.getTSNTance().getProfile3();
        if(profileName != null) {
            profileModule.deleteCreatedProfile(profileName);
        }
        profileName = ScenarioContext.getTSNTance().getProfile4();
        if(profileName != null) {
            profileModule.deleteCreatedProfile(profileName);
        }

    }

    @After(value="@UI", order = 3)
    public void killBrowser() {
        getDriver().quit();
    }

}
