package com..cucumber.uisteps.security;

import com..cucumber.uisteps.BaseUISteps;
import com..test.context.ScenarioContext;
import com..test.pages.commonPages.Navigation;
import com..test.pages.security.Users;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import net.thucydides.core.pages.PageObject;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.JavascriptExecutor;

import java.io.IOException;

public class ProfileSteps extends BaseUISteps {


    private Navigation navigation;
    //private Rule rules;
    private PageObject page;
    private com..test.pages.security.Profile Profile;
    private com..test.pages.security.Profile profile;
    private Users user;

    @And("^I create new profile$")
    public void iCreateNewProfile() throws InterruptedException, IOException, ParseException {
        Profile.clickProfileADD();
        Profile.enterProfileName();
        Profile.clickOnPresetsDropDown();
        Profile.clickOnFullButton();
        Profile.clickMenuProfileDetails("Security");
        Profile.unCheckProfile();
        Profile.clickCheckBoxAdd();
        Profile.clickOnSave();
    }

    @And("^I create new profile \"([^\"]*)\"$")
    public void iCreateNewProfileAs(String profile) {
        Profile.clickProfileADD();
        Profile.enterProfileName(profile);
        Profile.clickOnPresetsDropDown();
        Profile.clickOnFullButton();
        Profile.clickOnSave();
        if (ScenarioContext.getTSNTance().getProfile1().equals(""))
            ScenarioContext.getTSNTance().setProfile1(profile);
        else if (ScenarioContext.getTSNTance().getProfile2().equals(""))
            ScenarioContext.getTSNTance().setProfile2(profile);
    }


    @And("^iCreateProfileWithOthersApproval$")
    public void iCreateProfileWithOthersApproval() throws InterruptedException, IOException, ParseException {
        Profile.clickProfileADD();
        Profile.enterProfileNameOthersApproval();
        Profile.clickOnPresetsDropDown();
        Profile.clickOnFullButton();
        Profile.clickMenuProfileDetails("Security");
        Profile.checkProfileOtherApproval();
        Profile.clickOnSave();
    }

    @And("^I create new profile with All Approval$")
    public void iCreateNewProfileWithAllApproval() throws InterruptedException, IOException, ParseException {
        Profile.clickProfileADD();
        Profile.enterProfileNameAllApproval();
        Profile.clickOnPresetsDropDown();
        Profile.clickOnFullButton();
        Profile.clickOnSave();
    }


    @And("^I create new profile with Others Approval$")
    public void iCreateNewProfileWithOthersApproval() throws InterruptedException, IOException, ParseException {
        Profile.clickProfileADD();
        Profile.enterProfileNameOthersApproval();
        Profile.clickOnPresetsDropDown();
        Profile.clickOnFullButton();
        Profile.clickMenuProfileDetails("Security");
        Profile.unCheckSelfApproval();
        Profile.clickOnSave();
    }


    @When("^I create new profile with Self Approval and Other Approval$")
    public void iCreateNewProfileWithSelfApprovalAndOtherApproval() throws InterruptedException, IOException, ParseException {
        Profile.clickProfileADD();
        Profile.enterProfileNameSelfApprovalAndAllApproval();
        Profile.clickOnPresetsDropDown();
        Profile.clickOnFullButton();
        Profile.clickMenuProfileDetails("Security");
        Profile.unCheckUsers();
        Profile.checkUSerSelfApproval();
        Profile.checkUserOtherApproval();
        Profile.clickOnSave();
    }

    @And("^I create profile with All Approval$")
    public void iCreateProfileWithAllApproval() throws InterruptedException, IOException, ParseException {
        Profile.clickProfileADD();
        Profile.enterProfileNameOthersApproval();
        Profile.clickOnPresetsDropDown();
        Profile.clickOnFullButton();
        Profile.clickMenuProfileDetails("Security");
        Profile.clickOnSave();
    }


    @And("^I create New profile to verify Others Approval$")
    public void iCreateNewProfileToVerifyOthersApproval() throws InterruptedException, IOException, ParseException {

        Profile.clickProfileADD();
        Profile.enterProfileNameOthersApproval();
        Profile.clickOnPresetsDropDown();
        Profile.clickOnFullButton();
        Profile.clickMenuProfileDetails("Security");
        Profile.clickOnSave();
    }

    @And("^I create NewProfile \"([^\"]*)\"$")
    public void iCreateNewProfile(String ProfileName) throws Throwable {
        Profile.clickProfileADD();
        Profile.ProfileName(ProfileName);
        Profile.clickOnPresetsDropDown();
        Profile.clickOnFullButton();
        Profile.clickMenuProfileDetails("Security");
        user.btnSubmit();
        Profile.enterComment();
        user.commentBtnSubmit();
        waitABit(5000);
    }

    @Then("^I approve Pending profiles$")
    public void iApprovePendingProfiles() throws InterruptedException {
        Profile.clickOnUsersPendingLink();
        Profile.profile1ApprovalEllipsisLink("Profile1234");
        Profile.profileApproval();
        Profile.profile1ApprovalEllipsisLink("Profile123");
        Profile.profileApproval1();
    }

    @And("^I create new profile with Self Approval on Rules Management$")
    public void iCreateNewProfileWithSelfApprovalOnRulesManagement() throws InterruptedException, IOException, ParseException {
        Profile.clickProfileADD();
        Profile.enterProfileNameSelfApproval();
        Profile.clickOnPresetsDropDown();
        Profile.clickOnFullButton();
        Profile.clickMenuProfileDetails("Rules management");
        Profile.unCheckOthersApprovalRulesManagement();
        Profile.clickOnSave();

    }

    @And("^I create new profile with Others Approval on Rules Management$")
    public void iCreateNewProfileWithOthersApprovalOnRulesManagement() throws InterruptedException, IOException, ParseException {
        Profile.clickProfileADD();
        Profile.enterProfileNameOthersApproval();
        Profile.clickOnPresetsDropDown();
        Profile.clickOnFullButton();
        Profile.clickMenuProfileDetails("Rules management");
        Profile.unCheckSelfApprovalRulesManagement();
        Profile.clickOnSave();

    }

    @And("^I create new profile with No Self and Other Approval$")
    public void iCreateNewProfileWithNoSelfAndOtherApproval() throws InterruptedException, IOException, ParseException {
        Profile.clickProfileADD();
        Profile.enterProfileNameNoSelfApprovalAndOtherApproval();
        Profile.clickOnPresetsDropDown();
        Profile.clickOnFullButton();
        Profile.clickMenuProfileDetails("Rules management");
        Profile.unCheckOthersApprovalRulesManagement();
        Profile.unCheckSelfApprovalRulesManagement();
        Profile.clickOnSave();
    }

    @And("^I create new profile with Others Approval on Watchlist$")
    public void iCreateNewProfileWithOthersApprovalOnWatchlist() throws InterruptedException, IOException, ParseException {
        Profile.clickProfileADD();
        Profile.enterProfileNameOthersApproval();
        Profile.clickOnPresetsDropDown();
        Profile.clickOnFullButton();
        Profile.clickMenuProfileDetails("Watchlist");
        Profile.unCheckSelfApprovalWatchlist();
        Profile.clickOnSave();
    }

    @And("^I Update the Profile with All Approvals$")
    public void iUpdateTheProfileWithAllApprovals() throws InterruptedException {
        Profile.changeProfileSettings();
        Profile.changeProfile();
        Profile.clickMenuProfileDetails("Security");
        Profile.checkUSerSelfApproval();
        Profile.checkUserOtherApproval();
        Profile.clickOnSave();
    }

    @And("^I Update the Watchlist Profile with All Approvals$")
    public void iUpdateTheWatchlistProfileWithAllApprovals() throws InterruptedException {
        Profile.changeProfileSettings();
        Profile.changeProfile();
        Profile.clickMenuProfileDetails("Watchlist");
        Profile.checkWatchlistSelfApproval();
        Profile.checkWatchlistOtherApproval();
        Profile.clickOnSave();
    }

    @And("^I Update the Profile with Restricted Application Types$")
    public void iUpdateTheProfileWithRestrictedApplicationTypes() throws InterruptedException {
        Profile.changeProfileSettings();
        Profile.changeProfile();
        Profile.clickMenuProfileDetails("Application Types");
        Profile.restrictCreditCard();
        Profile.clickOnSave();
    }

    @When("^I create a Profile \"([^\"]*)\" with Full ATSNTss and Part ATSNTss to \"([^\"]*)\" and \"([^\"]*)\"$")
    public void iCreateAProfileWithAndToAnd(String profile, String menu, String aTSNTssItem) throws InterruptedException {
        Profile.clickProfileADD();
        Profile.enterProfileName(profile);
        Profile.clickOnPresetsDropDown();
        Profile.clickOnFullButton();

        Profile.clickMenuLabel(menu);
        Profile.removeUnwantedATSNTss(aTSNTssItem);

        Profile.clickOnSave();
        Profile.clickCancel();
        if (ScenarioContext.getTSNTance().getProfile1().equals(""))
            ScenarioContext.getTSNTance().setProfile1(profile);
        else if (ScenarioContext.getTSNTance().getProfile2().equals(""))
            ScenarioContext.getTSNTance().setProfile2(profile);
    }

    @Then("^I give \"([^\"]*)\" aTSNTss to \"([^\"]*)\" profile$")
    public void iGiveATSNTssToProfile(String aTSNTssType, String profile) {
        Profile.clickEllipsisForRow(profile);
        if (aTSNTssType.equalsIgnoreCase("FULL")) {
            Profile.clickOnPresetsDropDown();
            Profile.clickOnFullButton();
        }

        Profile.clickOnSave();

    }


    @And("^I create a new profile \"([^\"]*)\" with Update User only$")
    public void iCreateANewProfileWithUpdateUserOnly(String profile) throws InterruptedException {
        Profile.clickProfileADD();
        Profile.enterProfileName(profile);
        Profile.clickOnPresetsDropDown();
        Profile.clickOnFullButton();
        Profile.clickMenuLabel("Security");
        JavascriptExecutor js = (JavascriptExecutor) getDriver();
        if (js.executeScript("return document.getElementById('80201').checked").toString().equals("true"))
            Profile.userAddCheckBox.click();
        Profile.clickOnSave();
        Profile.clickCancel();
        if (ScenarioContext.getTSNTance().getProfile1().equals(""))
            ScenarioContext.getTSNTance().setProfile1(profile);
        else if (ScenarioContext.getTSNTance().getProfile2().equals(""))
            ScenarioContext.getTSNTance().setProfile2(profile);
    }

    @And("^I create a new profile \"([^\"]*)\" with Add User only$")
    public void iCreateANewProfileWithAddUserOnly(String profile) throws Throwable {
        Profile.clickProfileADD();
        Profile.enterProfileName(profile);
        Profile.clickOnPresetsDropDown();
        Profile.clickOnFullButton();
        Profile.clickMenuLabel("Security");
        JavascriptExecutor js = (JavascriptExecutor) getDriver();
        if (js.executeScript("return document.getElementById('80202').checked").toString().equals("true"))
            Profile.userUpdateCheckBox.click();
        Profile.clickOnSave();
        Profile.clickCancel();
        if (ScenarioContext.getTSNTance().getProfile1().equals(""))
            ScenarioContext.getTSNTance().setProfile1(profile);
        else if (ScenarioContext.getTSNTance().getProfile2().equals(""))
            ScenarioContext.getTSNTance().setProfile2(profile);
    }

    @And("^I Update the Profile Named \"([^\"]*)\" with Restricted Application Types$")
    public void iUpdateTheProfileNamedWithRestrictedApplicationTypes(String profileName) throws InterruptedException {
        Profile.clickOnSpecificProfile(profileName);
        Profile.changeProfile();
        Profile.clickMenuProfileDetails("Application Types");
        Profile.restrictCreditCard();
        Profile.clickOnSave();
    }


    @And("^I create a Profile \"([^\"]*)\" having Part ATSNTss to \"([^\"]*)\" and  \"([^\"]*)\"$")
    public void iCreateAProfileHavingPartATSNTssToAnd(String testing, String investigation, String rulesManagement) throws Throwable {
        Profile.addProfile();
        Profile.enterProfileName("Testing");
        Profile.clickMenuProfile();
    }

    @Then("^I verify the view aTSNTss for the selected rule$")
    public void iVerifyTheViewATSNTssForTheSelectedRule() {
        Profile.verifyViewATSNTss();
    }

    @And("^I update the \"([^\"]*)\" and \"([^\"]*)\" in applicant category$")
    public void iUpdateTheAndInApplicantCategory(String decisionReason, String natureOfFraud) throws Throwable {
        Profile.setCheckDecisionReason(decisionReason);
        Profile.setCheckNatureOfFraud(natureOfFraud);

    }

    @Then("^I verify the user with no update aTSNTss cannot update other application record fields$")
    public void iVerifyTheUserWithNoUpdateATSNTssCannotUpdateOtherApplicationRecordFields() {
        Profile.verifyNoUpdateATSNTss();
    }
}