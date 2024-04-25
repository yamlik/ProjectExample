package com..cucumber.uisteps.application;

import com..cucumber.uisteps.BaseUISteps;
import com..test.context.constants.ApplicationConstants;
import com..test.modules.ApplicationModule;
import com..test.pages.application.ApplicationRecordPage;
import com..test.pages.application.MatchReviewPage;
import com..test.pages.commonPages.Navigation;
import com..test.pages.commonPages.RecentPage;
import com..test.pages.settings.SystemParametersPage;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import net.thucydides.core.annotations.Shared;
import net.thucydides.core.annotations.Steps;
import org.json.simple.parser.ParseException;
import org.junit.Assert;
import java.io.IOException;
import java.util.Arrays;

public class MatchReviewSteps extends BaseUISteps {

    @Shared
    private ApplicationRecordPage applicationRecordPage;
    private Navigation navigation;
    private RecentPage recentPage;
    private MatchReviewPage MatchReviewPage;
    @Steps
    private ApplicationModule applicationModule;
    private SystemParametersPage systemParametersPage;

    @Then("^I verify Application value under RuleTab for \"([^\"]*)\"$")
    public void iVerifyApplicationValueUnderRuleTabFor(String ruleNumber) throws Throwable {
        MatchReviewPage.verifyRuleTab(ruleNumber);
        MatchReviewPage.verifyRulesContent(ruleNumber);

    }

    @And("^I verify rule content for \"([^\"]*)\"$")
    public void iVerifyRuleContentFor(String ruleNumber) throws Throwable {
        MatchReviewPage.verifyRulesContent(ruleNumber);
    }

    @Then("^I click on the Matches tab and verify contents of System tab$")
    public void iClickOnTheMatchesTabAndVerifyContentsOfSystemTab() {
        boolean systemDetails = MatchReviewPage.getSystemDetails();
        if (!systemDetails) {
            Assert.fail("System Details is not same as expected");
        }
    }

    @And("^I verify the contents of \"([^\"]*)\" and \"([^\"]*)\" Tab$")
    public void iVerifyTheContentsOfAndTab(String Source, String Source1) throws Throwable {

        if (Source.contains("Full Application Details")) {
            boolean applicationDetails = MatchReviewPage.getFullApplicationDetails();
            if (!applicationDetails) {
                Assert.fail("Applications details is not same as expected");
            }
        }
        if (Source1.contains("CBC")) {

            boolean applicantDetails = MatchReviewPage.getFullCBCDetails();
            if (!applicantDetails) {
                Assert.fail("CBC details is not same as expected");
            }
        }
    }

    @Then("^I verify the Header for \"([^\"]*)\"$")
    public void iVerifyTheHeaderFor(String ruleNumber) throws Throwable {
        boolean isExists = MatchReviewPage.checkApplicationRecordHeader();
        boolean date = MatchReviewPage.checkApplicationDate();
        boolean type = MatchReviewPage.checkApplicationType();
        boolean score = MatchReviewPage.checkFraudScore(ruleNumber);
        boolean alert = MatchReviewPage.checkFraudAlert();

        if (!isExists) {
            Assert.fail("Application has not been created");
        }
        if (!date) {
            Assert.fail("Application date has not been created");
        }
        if (!type) {
            Assert.fail("Application Type has not been created");
        }
        if (!score) {
            Assert.fail("Application Fraud score has not been created");
        }
        if (!alert) {
            Assert.fail("Application Alert has not been created");
        }
    }


    @Then("^I verify the contents of Full Application Details Tab$")
    public void iVerifyTheContentsOfFullApplicationDetailsTab() {

        boolean applicationDetails = MatchReviewPage.getFullApplicationDetails();
        if (!applicationDetails) {
            Assert.fail("Applications details is not same as expected");
        }
        boolean applicantDetails = MatchReviewPage.getFullApplicantDetails();
        if (!applicantDetails) {
            Assert.fail("Applicant details is not same as expected");
        }
    }

    @Then("^I verify Application value under RuleTab$")
    public void iVerifyApplicationValueUnderRuleTab() {
        MatchReviewPage.verifyRuleTab();
        MatchReviewPage.verifyRulesContent();
    }

    @And("^I verify the contents of Full Application Details Tab for sounds like match$")
    public void iVerifyTheContentsOfFullApplicationDetailsTabForSoundsLikeMatch() {

        boolean organization = MatchReviewPage.getApplicationDetails("Organisation", "Application1");
        if (!organization) {
            Assert.fail("Organization name is not same as expected");
        }
        boolean appNumber = MatchReviewPage.getApplicationDetails("Application Number", "Application1");
        if (!appNumber) {
            Assert.fail("Application Number is not same as expected");
        }

        boolean appType = MatchReviewPage.getApplicationDetails("Application Type", "Application1");
        if (!appType) {
            Assert.fail("Application Type is not same as expected");
        }

        boolean surName = MatchReviewPage.getApplicationDetails("Surname", "Applicant1");
        boolean surNameColor = MatchReviewPage.verifyApplicationValueFieldColor("Surname", ApplicationConstants.BlueColor);
        if (!surName) {
            Assert.fail("Applicant details is not same as expected");
        }
        if (!surNameColor) {
            Assert.fail("Applicant details field colour is not same as expected");
        }

        boolean dob = MatchReviewPage.getApplicationDetails("Date of Birth", "Applicant1");
        boolean dobColor = MatchReviewPage.verifyApplicationValueFieldColor("Date of Birth", ApplicationConstants.BlueColor);
        if (!dob) {
            Assert.fail("Applicant details is not same as expected");
        }
        if (!dobColor) {
            Assert.fail("Applicant details field colour is not same as expected");
        }

        boolean homePhoneNumber = MatchReviewPage.getApplicationDetails("Home Phone Number", "Applicant1");
        boolean homePhoneNumberColor = MatchReviewPage.verifyApplicationValueFieldColor(" Home Phone Number", ApplicationConstants.BlueColor);
        if (!homePhoneNumber) {
            Assert.fail("Applicant details is not same as expected");
        }
        if (!homePhoneNumberColor) {
            Assert.fail("Applicant details field colour is not same as expected");
        }

        boolean userField2 = MatchReviewPage.getApplicationDetails("User Field 2", "Applicant1");
        boolean userField2Color = MatchReviewPage.verifyApplicationValueFieldColor(" User Field 2", ApplicationConstants.BlueColor);
        if (!userField2) {
            Assert.fail("Applicant details is not same as expected");
        }
        if (!userField2Color) {
            Assert.fail("Applicant details field colour is not same as expected");
        }
    }

    @And("^I verify Application value under RuleTab for Sounds like match \"([^\"]*)\"$")
    public void iVerifyApplicationValueUnderRuleTabForSoundsLikeMatch(String ruleNumber) {
        MatchReviewPage.verifyRulesContent(ruleNumber);
    }


    @Then("^I verify the contents of Full Application Details Tab for Application to itself match$")
    public void iVerifyTheContentsOfFullApplicationDetailsTabForApplicationToItselfMatch() {
        boolean organization = MatchReviewPage.getApplicationDetails("Organisation", "Application");
        if (!organization) {
            Assert.fail("Organization name is not same as expected");
        }
        boolean appNumber = MatchReviewPage.getApplicationDetails("Application Number", "Application");
        if (!appNumber) {
            Assert.fail("Application Number is not same as expected");
        }
        boolean appType = MatchReviewPage.getApplicationDetails("Application Type", "Application");
        if (!appType) {
            Assert.fail("Application Type is not same as expected");
        }
        boolean homeAdd = MatchReviewPage.getApplicationDetails("Home Address 5", "Applicant");
        boolean homeAddColor = MatchReviewPage.verifyApplicationValueFieldColor("Home Address 5", ApplicationConstants.BlueColor);

        if (!homeAdd) {
            Assert.fail("Applicant details is not same as expected");
        }
        if (!homeAddColor) {
            Assert.fail("Applicant details field colour is not same as expected");
        }
        boolean companyAdd = MatchReviewPage.getApplicationDetails("Company Address 5", "Applicant");
        if (!companyAdd) {
            Assert.fail("Applicant details is not same as expected");
        }

    }


    @Then("^I verify the contents of Full Application Details Tab for Near match including full address match$")
    public void iVerifyTheContentsOfFullApplicationDetailsTabForNearMatchIncludingFullAddressMatch() {

        boolean organization = MatchReviewPage.getApplicationDetails("Organisation", "Application1");
        if (!organization) {
            Assert.fail("Organization name is not same as expected");
        }
        boolean appNumber = MatchReviewPage.getApplicationDetails("Application Number", "Application1");
        if (!appNumber) {
            Assert.fail("Application Number is not same as expected");
        }

        boolean appType = MatchReviewPage.getApplicationDetails("Application Type", "Application1");
        if (!appType) {
            Assert.fail("Application Type is not same as expected");
        }

        boolean homeAddress1 = MatchReviewPage.getApplicationDetails("Home Address 1", "Applicant1");
        boolean homeAdd1 = MatchReviewPage.verifyApplicationValueFieldColor("Home Address 1", ApplicationConstants.BlueColor);

        if (!homeAddress1) {
            Assert.fail("Application Type is not same as expected");
        }
        if (!homeAdd1) {
            Assert.fail("Applicant details field colour is not same as expected");
        }

        boolean homeAddress2 = MatchReviewPage.getApplicationDetails("Home Address 2", "Applicant1");
        boolean homeAdd2 = MatchReviewPage.verifyApplicationValueFieldColor("Home Address 2", ApplicationConstants.BlueColor);
        if (!homeAddress2) {
            Assert.fail("Application Type is not same as expected");
        }

        if (!homeAdd2) {
            Assert.fail("Applicant details field colour is not same as expected");
        }

        boolean homeAddress3 = MatchReviewPage.getApplicationDetails("Home Address 3", "Applicant1");
        boolean homeAdd3 = MatchReviewPage.verifyApplicationValueFieldColor("Home Address 2", ApplicationConstants.BlueColor);
        if (!homeAddress3) {
            Assert.fail("Application Type is not same as expected");
        }

        if (!homeAddress3) {
            Assert.fail("Applicant details field colour is not same as expected");
        }
        boolean homeAddress4 = MatchReviewPage.getApplicationDetails("Home Address 4", "Applicant1");
        boolean homeAdd4 = MatchReviewPage.verifyApplicationValueFieldColor("Home Address 2", ApplicationConstants.BlueColor);
        if (!homeAddress4) {
            Assert.fail("Application Type is not same as expected");
        }

        if (!homeAddress4) {
            Assert.fail("Applicant details field colour is not same as expected");
        }

    }

    @Then("^I verify the contents of Full Application Details Tab for Exact Match$")
    public void iVerifyTheContentsOfFullApplicationDetailsTabForExactMatch() {
        boolean organization = MatchReviewPage.getApplicationDetails("Organisation", "Application");
        boolean organizationColor = MatchReviewPage.verifyApplicationValueFieldColor("Organisation", ApplicationConstants.GreenColor);

        if (!organization) {
            Assert.fail("Organization name is not same as expected");
        }
        if (!organizationColor) {
            Assert.fail("Organization name Color is not same as expected");
        }

        boolean appType = MatchReviewPage.getApplicationDetails("Application Type", "Application");
        boolean appColor = MatchReviewPage.verifyApplicationValueFieldColor("Application Type", ApplicationConstants.GreenColor);

        if (!appType) {
            Assert.fail("Application Type is not same as expected");
        }
        if (!appColor) {
            Assert.fail("Application Type Color is not same as expected");
        }

        boolean homeAdd = MatchReviewPage.getApplicationDetails("Home Address 1", "Applicant");
        boolean homeAddColor = MatchReviewPage.verifyApplicationValueFieldColor("Home Address 1", ApplicationConstants.BlueColor);

        if (!homeAdd) {
            Assert.fail("Home Address1 is not same as expected");
        }
        if (!homeAddColor) {
            Assert.fail("Home Address1 field colour is not same as expected");
        }
        boolean homeAdd2 = MatchReviewPage.getApplicationDetails("Home Address 1", "Applicant");
        boolean homeAddColor2 = MatchReviewPage.verifyApplicationValueFieldColor("Home Address 1", ApplicationConstants.BlueColor);

        if (!homeAdd2) {
            Assert.fail("Home Address2 is not same as expected");
        }
        if (!homeAddColor2) {
            Assert.fail("Home Address2 field colour is not same as expected");
        }
        boolean homeAdd3 = MatchReviewPage.getApplicationDetails("Home Address 1", "Applicant");
        boolean homeAddColor3 = MatchReviewPage.verifyApplicationValueFieldColor("Home Address 1", ApplicationConstants.BlueColor);

        if (!homeAdd3) {
            Assert.fail("Home Address3 is not same as expected");
        }
        if (!homeAddColor3) {
            Assert.fail("Home Address3 field colour is not same as expected");
        }
        boolean homeAdd4 = MatchReviewPage.getApplicationDetails("Home Address 1", "Applicant");
        boolean homeAddColor4 = MatchReviewPage.verifyApplicationValueFieldColor("Home Address 1", ApplicationConstants.BlueColor);

        if (!homeAdd4) {
            Assert.fail("Home Address4 is not same as expected");
        }
        if (!homeAddColor4) {
            Assert.fail("Home Address4 field colour is not same as expected");
        }
        boolean homeAdd5 = MatchReviewPage.getApplicationDetails("Home Address 1", "Applicant");
        boolean homeAddColor5 = MatchReviewPage.verifyApplicationValueFieldColor("Home Address 1", ApplicationConstants.BlueColor);

        if (!homeAdd5) {
            Assert.fail("Home Address5 is not same as expected");
        }
        if (!homeAddColor5) {
            Assert.fail("Home Address5 field colour is not same as expected");
        }
        boolean homeAdd6 = MatchReviewPage.getApplicationDetails("Home Address 1", "Applicant");
        boolean homeAddColor6 = MatchReviewPage.verifyApplicationValueFieldColor("Home Address 1", ApplicationConstants.BlueColor);

        if (!homeAdd6) {
            Assert.fail("Home Address6 is not same as expected");
        }
        if (!homeAddColor6) {
            Assert.fail("Home Address6 field colour is not same as expected");
        }
        boolean homePost = MatchReviewPage.getApplicationDetails("Home Postcode", "Applicant");
        boolean homePostColor = MatchReviewPage.verifyApplicationValueFieldColor("Home Postcode", ApplicationConstants.BlueColor);

        if (!homePost) {
            Assert.fail("Home Post Address is not same as expected");
        }
        if (!homePostColor) {
            Assert.fail("Home Post field colour is not same as expected");
        }
    }


    @Then("^I verify the contents of Full Application Details Tab for Like match$")
    public void iVerifyTheContentsOfFullApplicationDetailsTabForLikeMatch() {
        boolean organization = MatchReviewPage.getApplicationDetails("Organisation", "Application");
        if (!organization) {
            Assert.fail("Organization name is not same as expected");
        }
        boolean appNumber = MatchReviewPage.getApplicationDetails("Application Number", "Application");
        if (!appNumber) {
            Assert.fail("Application Number is not same as expected");


        }
        boolean appType = MatchReviewPage.getApplicationDetails("Application Type", "Application");
        if (!appType) {
            Assert.fail("Application Type is not same as expected");
        }

        boolean homeAdd = MatchReviewPage.getApplicationDetails("Company Name", "Applicant");
        boolean homeAddColor = MatchReviewPage.verifyApplicationValueFieldColor("Company Name", ApplicationConstants.BlueColor);
        if (!homeAdd) {
            Assert.fail("Applicant details is not same as expected");
        }
        if (!homeAddColor) {
            Assert.fail("Applicant details field colour is not same as expected");
        }
    }


    @Then("^I verify the contents of Full Application Details Tab for Exact Match_SecondPart$")
    public void iVerifyTheContentsOfFullApplicationDetailsTabForExactMatch_SecondPart() throws IOException, ParseException {
        boolean organization = MatchReviewPage.getApplicationDetails("Organisation", "Application");
        boolean organizationColor = MatchReviewPage.verifyApplicationValueFieldColor("Organisation", ApplicationConstants.GreenColor);

        if (!organization) {
            Assert.fail("Organization name is not same as expected");
        }
        if (!organizationColor) {
            Assert.fail("Organization name Color is not same as expected");
        }
        boolean appType = MatchReviewPage.getApplicationDetails("Application Type", "Application");
        boolean appColor = MatchReviewPage.verifyApplicationValueFieldColor("Application Type", ApplicationConstants.GreenColor);

        if (!appType) {
            Assert.fail("Application Type is not same as expected");
        }
        if (!appColor) {
            Assert.fail("Application Type Color is not same as expected");
        }

        boolean homeAdd = MatchReviewPage.getApplicationDetails("Home Address 1", "Applicant");
        boolean homeAddColor = MatchReviewPage.verifyApplicationValueFieldColor("Home Address 1", ApplicationConstants.GreenColor);

        if (!homeAdd) {
            Assert.fail("Home Address1 is not same as expected");
        }
        if (!homeAddColor) {
            Assert.fail("Home Address1 field colour is not same as expected");
        }
        boolean homeAdd2 = MatchReviewPage.getApplicationDetails("Home Address 1", "Applicant");
        boolean homeAddColor2 = MatchReviewPage.verifyApplicationValueFieldColor("Home Address 1", ApplicationConstants.GreenColor);

        if (!homeAdd2) {
            Assert.fail("Home Address2 is not same as expected");
        }
        if (!homeAddColor2) {
            Assert.fail("Home Address2 field colour is not same as expected");
        }
        boolean homeAdd3 = MatchReviewPage.getApplicationDetails("Home Address 1", "Applicant");
        boolean homeAddColor3 = MatchReviewPage.verifyApplicationValueFieldColor("Home Address 1", ApplicationConstants.GreenColor);

        if (!homeAdd3) {
            Assert.fail("Home Address3 is not same as expected");
        }
        if (!homeAddColor3) {
            Assert.fail("Home Address3 field colour is not same as expected");
        }
        boolean homeAdd4 = MatchReviewPage.getApplicationDetails("Home Address 1", "Applicant");
        boolean homeAddColor4 = MatchReviewPage.verifyApplicationValueFieldColor("Home Address 1", ApplicationConstants.GreenColor);

        if (!homeAdd4) {
            Assert.fail("Home Address4 is not same as expected");
        }
        if (!homeAddColor4) {
            Assert.fail("Home Address4 field colour is not same as expected");
        }
        boolean homeAdd5 = MatchReviewPage.getApplicationDetails("Home Address 1", "Applicant");
        boolean homeAddColor5 = MatchReviewPage.verifyApplicationValueFieldColor("Home Address 1", ApplicationConstants.GreenColor);

        if (!homeAdd5) {
            Assert.fail("Home Address5 is not same as expected");
        }
        if (!homeAddColor5) {
            Assert.fail("Home Address5 field colour is not same as expected");
        }
        boolean homeAdd6 = MatchReviewPage.getApplicationDetails("Home Address 1", "Applicant");
        boolean homeAddColor6 = MatchReviewPage.verifyApplicationValueFieldColor("Home Address 1", ApplicationConstants.GreenColor);

        if (!homeAdd6) {
            Assert.fail("Home Address6 is not same as expected");
        }
        if (!homeAddColor6) {
            Assert.fail("Home Address6 field colour is not same as expected");
        }
        boolean homePost = MatchReviewPage.getApplicationDetails("Home Postcode", "Applicant");
        boolean homePostColor = MatchReviewPage.verifyApplicationValueFieldColor("Home Postcode", ApplicationConstants.GreenColor);

        if (!homePost) {
            Assert.fail("Home Post Address is not same as expected");
        }
        if (!homePostColor) {
            Assert.fail("Home Post field colour is not same as expected");
        }
    }

    @And("^I verify the contents of Full Application Details Tab Company Name match$")
    public void iVerifyTheContentsOfFullApplicationDetailsTabCompanyNameMatch() {

        boolean organization = MatchReviewPage.getApplicationDetails("Organisation", "Application1");
        if (!organization) {
            Assert.fail("Organization name is not same as expected");
        }
        boolean appNumber = MatchReviewPage.getApplicationDetails("Application Number", "Application1");
        if (!appNumber) {
            Assert.fail("Application Number is not same as expected");
        }
        boolean appType = MatchReviewPage.getApplicationDetails("Application Type", "Application1");
        if (!appType) {
            Assert.fail("Application Type is not same as expected");
        }
        boolean homeAdd = MatchReviewPage.getApplicationDetails("Company Name", "Applicant1");
        boolean homeAddColor = MatchReviewPage.verifyApplicationValueFieldColor("Company Name", ApplicationConstants.BlueColor);
        if (!homeAdd) {
            Assert.fail("Applicant details is not same as expected");
        }
        if (!homeAddColor) {
            Assert.fail("Applicant details field colour is not same as expected");
        }
    }

    @And("^I verify the contents of Full Application Detail Tab$")
    public void iVerifyTheContentsOfFullApplicationDetailTab() {
        boolean applicationDetails = MatchReviewPage.getFullApplicationDetails();
        if (!applicationDetails) {
            Assert.fail("Applications details is not same as expected");
        }
    }

    @Then("^I Verify the Match review for Application \"([^\"]*)\"$")
    public void iVerifyTheMatchReviewForApplication(String applicationNumber) throws Throwable {

        boolean isExists = MatchReviewPage.verifyApplicationMatchReview(applicationNumber);
        if (!isExists) {
            Assert.fail("Match Review page has issues");
        }
    }

    @And("^I will compare results on Excel for Match Review$")
    public void iWillCompareResultsOnExcelForMatchReview() throws IOException, InterruptedException {
        String[][] table1;
        String[][] table2;
        boolean result = false;
        boolean isBreak = false;

        table1 = MatchReviewPage.getRuleTabDetails();
        table2 = MatchReviewPage.getRuleTabDetailsFromExcel();

        for (int i = 0; i<table1.length && !isBreak; i++) {
            for (int j = 0; j < 5; j++) {
                if(table1[i][j]!=null && table2[i][j]!=null) {
                    if (table2[i][j].contains(table1[i][j])) {
                        result = true;
                    } else {
                        result = false;
                        isBreak = true;
                        break;
                    }
                }
            }
        }

        Assert.assertTrue("Match review Rule Tab does not match to the Excel",result);
        result=false;

        table1 = MatchReviewPage.getSystemTabDetails();
        table2 = MatchReviewPage.getSystemTabDetailsFromExcel();

        for (int i = 0; i<table1.length && !isBreak; i++) {
            for (int j = 0; j < 5; j++) {
                if(table1[i][j]!=null && table2[i][j]!=null) {
                    if (table2[i][j].contains(table1[i][j])) {
                        result = true;
                    } else {
                        result = false;
                        isBreak = true;
                        break;
                    }
                }
            }
        }

        Assert.assertTrue("Match review System tab does not match to the Excel",result);

        table1=MatchReviewPage.getRulesTriggeredDetails();
        table2=MatchReviewPage.getRulesTriggeredFromExcel();

        Assert.assertTrue("Match review Rules Triggered tab does not match to the Excel",Arrays.deepEquals(table1,table2));

        table1=MatchReviewPage.getMatchReviewHeaderDetails();
        table2=MatchReviewPage.getHeaderFromExcel();
        Assert.assertTrue("Match Review Header does not match to the Excel",Arrays.deepEquals(table1,table2));

    }


    @Then("^I Verify no error message appears on MatchReview page$")
    public void iVerifyNoErrorMessageAppearsOnMatchReviewPage() {
        MatchReviewPage.clickingMatchReviewButton();
        boolean redError = MatchReviewPage.getRedError();
        if(redError)
        {
           Assert.fail("Error Message appears");
        }

    }

    @Then("^I verify the score is \"([^\"]*)\" on Match Review header$")
    public void iVerifyTheScoreIsOnMatchReviewHeader(String score) {
        boolean isScoreExists = MatchReviewPage.verifyScore(score);
        Assert.assertTrue("Score is not as expected",isScoreExists);
    }



    @And("^I click on the Value Matches tab and verify Full data if rule contains =$")
    public void iClickOnTheValueMatchesTabAndVerifyFullDataIfRuleContains() {
        boolean isEqualExisits = MatchReviewPage.verifyEqualInRule();

       if(isEqualExisits)
       {
            boolean isFullButtonExist= MatchReviewPage.fullButtonExist();
            Assert.assertTrue("Button does not exists",isFullButtonExist);
       }
        else
       {
           boolean isFullButtonExist= MatchReviewPage.fullButtonExist();
           Assert.fail("Full data button exists.");
       }

    }

    @And("^I click on Full data button$")
    public void iClickOnFullDataButton() {
        MatchReviewPage.clickFullDataButton();
         }

    @And("^I verify the contents of Full Application Details Tab with multiple records$")
    public void iVerifyTheContentsOfFullApplicationDetailsTabWithMultipleRecords() {
        boolean applicationDetails = MatchReviewPage.getFullApplicationDetails();
        if (!applicationDetails) {
            Assert.fail("Applications details is not same as expected");
        }
        boolean applicantDetails = MatchReviewPage.getFullApplicantDetails();
        if (!applicantDetails) {
            Assert.fail("Applicant details is not same as expected");
        }
    }

    @And("^I click on Full data button for result table$")
    public void iClickOnFullDataButtonForResultTable() {
        MatchReviewPage.clickFullDataButtonOnResultTable();
    }

    @And("^I close the ResultTableReview window$")
    public void iCloseTheResultTableReviewWindow() {
        MatchReviewPage.closeButton();
    }
}



