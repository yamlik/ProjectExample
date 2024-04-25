package com..cucumber.uisteps.security;

import com..cucumber.uisteps.BaseUISteps;
import com..test.context.ScenarioContext;
import com..test.modules.UserModule;
import com..test.pages.application.ApplicationRecordPage;
import com..test.pages.commonPages.Navigation;
import com..test.pages.security.Profile;
import com..test.pages.security.Users;
import com..test.utilities.JSONHelper;
import cucumber.api.PendingException;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import net.serenitybdd.core.pages.WebElementFacade;
import net.thucydides.core.annotations.Shared;
import net.thucydides.core.annotations.Steps;
import net.thucydides.core.pages.PageObject;
import org.json.JSONObject;
import org.json.simple.parser.ParseException;
import org.junit.Assert;
import org.openqa.selenium.WebDriver;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class UserSteps  extends BaseUISteps {
    @Shared
    @Steps
    private Navigation navigation;
    private PageObject page;
    private Users user;
    private Profile Profile;
    public WebDriver Driver;
    private ApplicationRecordPage applicationRecordPage;

    private final UserModule userModule = new UserModule();

    protected final String dataDir = System.getProperty("user.dir") + "/src/main/resources/data/UI/valid/";
    protected ScenarioContext scenarioContext = ScenarioContext.getTSNTance();
    private WebElementFacade addUserButton;

    @And("^I create New user$")
    public void ICreateNewUser() throws IOException, InterruptedException {
        waitABit(3000);
        user.addUser();
        Thread.sleep(2000);
        getDriver().manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);
        JSONObject requestBody = JSONHelper.messageAsActualJson(dataDir + "NewUser.json");
        user.enterUserID(requestBody.getString("userid"));
        user.enterUsername(requestBody.getString("username"));
        user.enterPassword(requestBody.getString("password"));
        user.reenterPassword(requestBody.getString("password"));
        user.drpProfileName();
        user.clickActiveButton();
        user.save();
    }

    @And("^I create New user with all Approval$")
    public void iCreateNewUserWithAllApproval() throws IOException {

        user.addUser();
        JSONObject requestBody = JSONHelper.messageAsActualJson(dataDir + "NewUserWithAllApproval.json");
        user.enterUserID(requestBody.getString("userid"));
        user.enterUsername(requestBody.getString("username"));
        scenarioContext.setUser1(requestBody.getString("username"));
        user.enterPassword(requestBody.getString("password"));
        user.reenterPassword(requestBody.getString("password"));
        user.selectAllApprovalProfile();
        user.clickActiveButton();
        user.save();
    }

    @And("^I create New user with Others Approval$")
    public void iCreateNewUserWithOthersApproval() throws IOException {
        user.addUser();
        JSONObject requestBody = JSONHelper.messageAsActualJson(dataDir + "NewUserWithOthersApproval.json");
        user.enterUserID(requestBody.getString("userid"));
        user.enterUsername(requestBody.getString("userName"));
        scenarioContext.setUser2(requestBody.getString("userName"));
        user.enterPassword(requestBody.getString("password"));
        user.reenterPassword(requestBody.getString("password"));
        user.selectOthersApprovalProfile();
        user.clickActiveButton();
        user.save();
    }

    @And("^I delete the Users Created$")
    public void iDeleteTheUsersCreated() {
        user.deleteUser("productUnderTestuser");
        user.deleteUser("productUnderTestuser1");

    }

    @And("^I delete the Profiles Created$")
    public void iDeleteTheProfilesCreated() {
        user.deleteProfile("All Approval");
        user.deleteProfile("Others Approval");

    }

    @Then("^I am able to add new user when profile has ADD permission aTSNTss only$")
    public void iamAbleToAddNewUserWhenProfileHasADDPermissionATSNTssOnly() throws IOException {
        navigation.navigateToPage("USERS");
        user.addUser();
        waitABit(3000);
    }

    @And("^I create New user with  Self Approval and Other Approval$")
    public void iCreateNewUserWithSelfApprovalAndOtherApproval() throws IOException {
        user.addUser();
        JSONObject requestBody = JSONHelper.messageAsActualJson(dataDir + "UserApproval.json");
        user.enterUserID(requestBody.getString("userid"));
        user.enterUsername(requestBody.getString("username"));
        user.enterPassword(requestBody.getString("password"));
        user.reenterPassword(requestBody.getString("password"));
        user.selectSelfAndAllApprovalProfile();
        user.clickActiveButton();
        user.btnSubmit();
        Profile.enterComment();
        user.commentBtnSubmit();
    }

    @And("^I create New user with Others Approval user$")
    public void iCreateNewUserWithOthersApprovalUser() throws IOException {
        user.addUser();
        JSONObject requestBody = JSONHelper.messageAsActualJson(dataDir + "UserApproval.json");
        user.enterUserID(requestBody.getString("userid1"));
        user.enterUsername(requestBody.getString("username1"));
        user.enterPassword(requestBody.getString("password1"));
        user.reenterPassword(requestBody.getString("password1"));
        user.selectOthersApprovalProfile();
        user.clickActiveButton();
        user.btnSubmit();
        Profile.enterComment();
        user.commentBtnSubmit();
        waitABit(5000);
    }

    @And("^I create New user with ExistingProfileOne$")
    public void iCreateNewUserWithExistingProfileOne() throws IOException {
        user.addUser();
        JSONObject requestBody = JSONHelper.messageAsActualJson(dataDir + "UserApproval.json");
        user.enterUserID(requestBody.getString("userid1"));
        user.enterUsername(requestBody.getString("username1"));
        scenarioContext.setUser3(requestBody.getString("username1"));
        user.enterPassword(requestBody.getString("password1"));
        user.reenterPassword(requestBody.getString("password1"));
        user.selectAllApprovalProfile();
        user.clickActiveButton();
        user.btnSubmit();
        Profile.enterComment();
        user.commentBtnSubmit();
    }


    @And("^I create New user with ExistingProfileTwo$")
    public void iCreateNewUserWithExistingProfileTwo() throws IOException {
        waitABit(3000);
        user.addUser();
        JSONObject requestBody = JSONHelper.messageAsActualJson(dataDir + "UserApproval.json");
        user.enterUserID(requestBody.getString("userid2"));
        user.enterUsername(requestBody.getString("username2"));
        scenarioContext.setUser4(requestBody.getString("username2"));
        user.enterPassword(requestBody.getString("password2"));
        user.reenterPassword(requestBody.getString("password2"));
        user.selectOthersApprovalProfile();
        user.clickActiveButton();
        user.btnSubmit();
        Profile.enterComment();
        user.commentBtnSubmit();
        waitABit(5000);
    }

    @And("^I  update  profileOne settings all approvals to enable Self Approval & Others Approval$")
    public void iUpdateProfileOneSettingsAllApprovalsToEnableSelfApprovalOthersApproval() throws InterruptedException {

        Profile.setChangeProfileName1();
        Profile.changeProfile();
        Profile.clickMenuProfileDetails("Security");
        Profile.checkUSerSelfApproval();
        Profile.checkUserOtherApproval();
        Profile.clickOnSave();
    }

    @And("^I  update  profileTwo settings to enable Others Approval$")
    public void iUpdateProfileTwoSettingsAllApprovalsToEnableOthersApproval() throws InterruptedException {
        Profile.setChangeProfileName2();
        Profile.changeProfile();
        Profile.clickMenuProfileDetails("Security");
        Profile.checkUserOtherApproval();
        Profile.clickOnSave();
        waitABit(2000);
    }

    @Then("^I View newly created user from pendingUser$")
    public void iViewNewlyCreatedUserFromPendingUser() {
        Profile.clickOnUsersPendingLink();
        Profile.profile1ApprovalEllipsisLink("automation123");
        Profile.userView();
    }

    @And("^I Approve newly created user from pendingUser$")
    public void iApproveNewlyCreatedUserFromPendingUser() {
        Profile.clickOnUsersPendingLink();
        Profile.profile1ApprovalEllipsisLink("automation1234");
        Profile.userApprove();
        waitABit(2000);
        Profile.profile1ApprovalEllipsisLink("automation123");
        Profile.userApprove();
        waitABit(2000);
    }

    @And("^I delete the Users$")
    public void iDeleteTheUsers() {
            user.deleteUser("productUnderTestuser");
            user.deleteUser("productUnderTestuser1");
            user.deleteUser("automation123");
            user.deleteUser("automation1234");
        }

    @Then("^I View newlyCreated profile from pendingUser$")
    public void iViewNewlyCreatedProfileFromPendingUser() throws InterruptedException {
        Profile.clickOnUsersPendingLink();
        Profile.profile1ApprovalEllipsisLink("Profile123");
        Profile.userView();

    }

    @And("^I delete the Profiles created for Profile Approval$")
    public void iDeleteTheProfilesCreatedForProfileApproval() {
        user.deleteProfile("All Approval");
        user.deleteProfile("Others Approval");
        user.deleteProfile("Profile123");
        user.deleteProfile("Profile1234");
    }

    @And("^I modified newly created user$")
    public void iModifiedNewlyCreatedUser() {
        waitABit(1000);
        Profile.profile1ApprovalEllipsisLink("All Approval");
        Profile.user_Change();
        user.selectAdministrator();
        user.save();
    }

    @And("^I delete newly created user \"([^\"]*)\"$")
    public void iDeleteNewlyCreatedUser(String User) throws Throwable {
        user.deleteUser(User);
    }

    @And("^I delete newly created profile \"([^\"]*)\"$")
    public void iDeleteNewlyCreatedProfile(String Profile) throws Throwable {
        user.deleteProfile(Profile);
    }

    @Then("^I able to add new user when profile has ADD permission aTSNTss only$")
    public void iAbleToAddNewUserWhenProfileHasADDPermissionATSNTssOnly() throws IOException {
        navigation.navigateToPage("USERS");
        waitABit(1000);
        user.addUser();
        waitABit(2000);
    }

    @And("^i create NewUser$")
    public void iCreateNewUser() throws IOException {
        JSONObject requestBody = JSONHelper.messageAsActualJson(dataDir + "NewUser.json");
        user.enterUserID(requestBody.getString("userid1"));
        user.enterUsername(requestBody.getString("username1"));
        scenarioContext.setUser2(requestBody.getString("username1"));
        user.enterPassword(requestBody.getString("password1"));
        user.reenterPassword(requestBody.getString("password1"));
        user.selectAllApprovalProfile();
        user.clickActiveButton();
        user.save();
        waitABit(5000);
    }

    @Then("^I Verify the created user \"([^\"]*)\"$")
    public void iVerifyTheCreatedUser(String users) throws Throwable {
        user.verify_Users(users);
    }

    @And("^I create New user \"([^\"]*)\" with all Approval$")
    public void iCreateNewUserWithAllApproval(String userName) throws Throwable {
        user.addUser();
        JSONObject requestBody = JSONHelper.messageAsActualJson(dataDir + "NewUserWithAllApproval.json");
        user.enterUserID(userName);
        user.enterUsername(userName);
        scenarioContext.setUser1(userName);
        user.enterPassword(requestBody.getString("password"));
        user.reenterPassword(requestBody.getString("password"));
        user.selectAllApprovalProfile();
        user.clickActiveButton();
        user.save();
    }

    @And("^I create New user \"([^\"]*)\" with No Self and Other Approval$")
    public void iCreateNewUserWithNoSelfAndOtherApproval(String userName) throws Throwable {
        user.addUser();
        JSONObject requestBody = JSONHelper.messageAsActualJson(dataDir + "NewUserWithAllApproval.json");
        user.enterUserID(userName);
        user.enterUsername(userName);
        scenarioContext.setUser4(userName);
        user.enterPassword(requestBody.getString("password"));
        user.reenterPassword(requestBody.getString("password"));
        user.selectNoSelfAndOtherApprovalProfile();
        user.clickActiveButton();
        user.save();
    }

    @And("^I create New user \"([^\"]*)\" with Others Approval$")
    public void iCreateNewUserWithOthersApproval(String userName) throws IOException {
        user.addUser();
        JSONObject requestBody = JSONHelper.messageAsActualJson(dataDir + "NewUserWithOthersApproval.json");
        user.enterUserID(userName);
        user.enterUsername(userName);
        scenarioContext.setUser2(userName);
        user.enterPassword(requestBody.getString("password"));
        user.reenterPassword(requestBody.getString("password"));
        user.selectOthersApprovalProfile();
        user.clickActiveButton();
        user.save();

    }

    @And("^I create New user \"([^\"]*)\" with Self Approval$")
    public void iCreateNewUserWithSelfApproval(String userName) throws IOException {
        user.addUser();
        JSONObject requestBody = JSONHelper.messageAsActualJson(dataDir + "NewUserWithOthersApproval.json");
        user.enterUserID(userName);
        user.enterUsername(userName);
        scenarioContext.setUser3(userName);
        user.enterPassword(requestBody.getString("password"));
        user.reenterPassword(requestBody.getString("password"));
        user.selectSelfApprovalProfile();
        user.clickActiveButton();
        user.save();

    }

    @And("^I update comment and Approve the User \"([^\"]*)\"$")
    public void iUpdateCommentAndApproveTheUser(String userName) throws Throwable {
        applicationRecordPage.updateComment();
        user.approveUsers(userName);
    }


    @And("^I add User \"([^\"]*)\" and Save as Draft$")
    public void iAddUserAndSaveAsDraft(String userName) throws Throwable {
        user.addUser();
        waitABit(1000);
        JSONObject requestBody = JSONHelper.messageAsActualJson(dataDir + "NewUserWithAllApproval.json");
        user.enterUserID(userName);
        user.enterUsername(userName);
        scenarioContext.setUser1(userName);
        user.enterPassword(requestBody.getString("password"));
        user.reenterPassword(requestBody.getString("password"));
        user.selectAllApprovalProfile();
        user.clickActiveButton();
        user.saveAsDraft();
    }

    @And("^I Submit the User \"([^\"]*)\" and Approve$")
    public void iSubmitTheUserAndApprove(String userName) throws Throwable {
        user.submitUser(userName);
        applicationRecordPage.updateComment();
        user.approveUsers(userName);
    }

    @And("^I Submit the User \"([^\"]*)\" and Reject$")
    public void iSubmitTheUserAndReject(String userName){
        user.submitUser(userName);
        applicationRecordPage.updateComment();
        user.rejectUsers(userName);
        applicationRecordPage.updateComment();
        user.discardUser(userName);

    }

    @Given("^I create a user using the API$")
    public void iCreateAUserUsingTheAPI() throws IOException, ParseException {
        userModule.createAUser("createAUser");
    }

    @And("^I delete the User \"([^\"]*)\" by API$")
    public void iDeleteTheUserByAPI(String user) throws IOException {
        userModule.deleteCreatedUser(user);
    }

    @And("^I create \"([^\"]*)\" users using the above profiles$")
    public void iCreateUsersUsingTheAboveProfiles(int count) throws IOException, ParseException {
        for (int i=1; i<=count; i++)
            userModule.createAUser("createAUser" + i);
    }


    @Then("^I verify that user with \"([^\"]*)\" can change the user details and cannot add new users$")
    public void iVerifyThatUserWithCanChangeTheUserDetailsAndCannotAddNewUsers(String profile)  {
        Assert.assertFalse("User is able to Add a new user",user.addUserButton.isCurrentlyVisible());
        Assert.assertTrue("Not able to change a user",user.selectChangeForUser("ADMINISTRATOR"));
        user.closePopup.click();
    }

    @Then("^I verify that user with \"([^\"]*)\" cannot change the user details and can add new users$")
    public void iVerifyThatUserWithCannotChangeTheUserDetailsAndCanAddNewUsers(String profile)  {
        Assert.assertTrue("User is not able to Add a new user",user.addUserButton.isCurrentlyVisible());
        Assert.assertFalse("User is able to change a user",user.selectChangeForUser("ADMINISTRATOR"));
    }
}
