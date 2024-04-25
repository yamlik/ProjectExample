package com..test.pages.application;

import com..test.context.constants.ApplicationConstants;
import com..test.context.ScenarioContext;
import com..test.pages.BasePage;
import com..test.utilities.FileHelper;
import com..test.utilities.JsHelper;
import net.serenitybdd.core.annotations.findby.By;
import net.serenitybdd.core.annotations.findby.FindBy;
import net.serenitybdd.core.pages.WebElementFacade;
import net.thucydides.core.annotations.Shared;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.simple.parser.ParseException;
import org.junit.Assert;
import org.json.simple.JSONObject;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.Color;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

public class MatchReviewPage extends BasePage {
    public String fraudScoreValue;

    @Shared
    private ScenarioContext ScenarioContext;

    @FindBy(css = ".CustomTable")
    private WebElementFacade tableRoot;

    @FindBy(xpath = "//button-drop-down//button[text()='Action']")
    private WebElementFacade actionButton;

    @FindBy(xpath = "//td//span[@class='rule-open']")
    private WebElementFacade rulesOpen;

    @FindBy(xpath = "//div[@class='label-text']")
    private WebElementFacade ruleLabel;

    @FindBy(xpath = "//div[@class='col-9 rule-desc-content']//div")
    private WebElementFacade applicationField;

    @FindBy(xpath = "//button[contains(text(),'System')]")
    private WebElementFacade SystemTab;

    @FindBy(xpath = "//h2[contains(text(),'Application Number')]")
    private WebElementFacade applicationTitle;

    @FindBy(xpath = "//a[@appresource='MatchReview.Res_FullApplicationDetails']")
    private WebElementFacade fullAppDetailsTab;

    @FindBy(xpath = "//div[@class='col-9 rule-desc-content']//div//following-sibling::div//following::div")
    private WebElementFacade matchedField;

    @FindBy(xpath = "//div[contains(text(),'Category')]/following::span[@class='ng-arrow-wrapper']")
    private WebElementFacade categoryDropdown;

    @FindBy(xpath = "//span[text()='Applicant']|//span[text()='Credit Bureau']|//span[text()='CBC']")
    private WebElementFacade selectCategory;

    @FindBy(xpath = "//div[@class='rule-count']")
    private WebElementFacade verifyRuleUnderRulesContent;

    @FindBy(xpath = "//button[contains(text(),'Review')]")
    private WebElementFacade reviewButton;

    @FindBy(xpath = "//*[@id=\"header-title\"]/h2")
    private WebElementFacade appNum;

    @FindBy(xpath = "//div[contains(text(),'Application Date')]/following-sibling::div")
    private WebElementFacade applicationDate;

    @FindBy(xpath = "//div[contains(text(),'Application Type')]/following-sibling::div")
    private WebElementFacade applicationType;

    @FindBy(xpath = "//div[contains(text(),'Fraud Alert')]/following-sibling::div")
    private WebElementFacade fraudAlert;

    @FindBy(xpath = "//div[@appresource='FieldRequirement.Ref_FraudScore']/following-sibling::div")
    private WebElementFacade fraudScore;

    @FindBy(xpath = "//tbody/tr[1]/td[3]")
    private WebElementFacade applicationValue;

    @FindBy(xpath = "//tbody/tr[1]/td[4]")
    private WebElementFacade dataBaseValue;

    @FindBy(xpath = "(//button[@class='alp-bonito-button-default filter-operator-dynamic dropdown-toggle'])[1]/span")
    private WebElementFacade selectedCategory;

    @FindBy(xpath = "//*[@id=\"link-analysis\"]/span")
    private WebElementFacade Link_Analysis;

    @FindBy(xpath = "//*[@id=\"linkAnalysisNetwork\"]/div/canvas")
    private WebElementFacade canvas;

    @FindBy(xpath = "//div[@class='vis-tooltip']")
    private WebElementFacade canvas_ToolTip;

    @FindBy(xpath = "//ng-select")
    private WebElementFacade systemTabCategoryDropdown;

    @FindBy(xpath = "//div[contains(@class,'match-review-rules-labeltext-linkstyle')]")
    private WebElementFacade rulesTriggeredLink;

    @FindBy(xpath = "//div[contains(@class,'sticky-button-error')]")
    private WebElementFacade redErrorMatchReview;

    @FindBy(xpath = "//app-match-review-rules-triggered//tbody/tr")
    private WebElementFacade firstRowInRulesTriggeredSection;

    @FindBy(xpath = "//div[contains(@class,'triggered-rules')]")
    private WebElementFacade ruleTriggeredUnderMatchesTab;

    @FindBy(xpath = "//ngb-popover-window")
    private WebElementFacade popupAfterMouseHover;

    @FindBy(xpath = "//a[contains(text(),'Value Matches (1)')]")
    private WebElementFacade valueMatcheTab;

    @FindBy(xpath = "//button[text()='Close']")
    private WebElementFacade closeButton;


    // MATCH REVIEW HEADER ELEMENTS

    @FindBy(xpath = "(//div[contains(@class,'match-review-header-box')])[1]/div[2]")
    private WebElementFacade applicationDateHeader;
    @FindBy(xpath = "(//div[contains(@class,'match-review-header-box')])[2]/div[2]")
    private WebElementFacade applicationTypeHeader;
    @FindBy(xpath = "(//div[contains(@class,'match-review-header-box')])[3]/div[2]")
    private WebElementFacade fraudScoreHeader;
    @FindBy(xpath = "(//div[contains(@class,'match-review-header-box')])[4]/div[2]")
    private WebElementFacade scorecardScoreHeader;
    @FindBy(xpath = "(//div[contains(@class,'match-review-header-box')])[5]/div[2]")
    private WebElementFacade noOfRulesTriggeredHeader;
    @FindBy(xpath = "(//div[contains(@class,'match-review-header-box')])[6]/div[2]")
    private WebElementFacade fraudAlertHeader;
    @FindBy(xpath = "(//div[contains(@class,'match-review-header-box')])[7]/div[2]")
    private WebElementFacade amountLimitHeader;

    @FindBy(xpath = "(//div[contains(@class,'match-review-header-box')])[1]/div[1]")
    private WebElementFacade applicationDateHeaderLabel;
    @FindBy(xpath = "(//div[contains(@class,'match-review-header-box')])[2]/div[1]")
    private WebElementFacade applicationTypeHeaderLabel;
    @FindBy(xpath = "(//div[contains(@class,'match-review-header-box')])[3]/div[1]")
    private WebElementFacade fraudScoreHeaderLabel;
    @FindBy(xpath = "(//div[contains(@class,'match-review-header-box')])[4]/div[1]")
    private WebElementFacade scorecardScoreHeaderLabel;
    @FindBy(xpath = "(//div[contains(@class,'match-review-header-box')])[5]/div[1]")
    private WebElementFacade noOfRulesTriggeredHeaderLabel;
    @FindBy(xpath = "(//div[contains(@class,'match-review-header-box')])[6]/div[1]")
    private WebElementFacade fraudAlertHeaderLabel;
    @FindBy(xpath = "(//div[contains(@class,'match-review-header-box')])[7]/div[1]")
    private WebElementFacade amountLimitHeaderLabel;
    @FindBy(xpath = "//div[contains(@class,'systemMatch-tab-content')]//div[contains(@class,'col-sm-7')]")
    private WebElementFacade equalOperator;
    @FindBy(xpath = "//button[contains(text(),'Full Data')]")
    private WebElementFacade fullDataButton;
    @FindBy(xpath = "//span[normalize-space()='Reference Table Review']")
    private WebElementFacade referenceTableReview;
    @FindBy(xpath = "//span[normalize-space()='Result Table Review']")
    private WebElementFacade resultTableReview;

    JsHelper jsHelper = new JsHelper();
    FileHelper fileHelper = new FileHelper();

    public void clickActionButton() { clickOn(actionButton); }

    public boolean verifyRuleTab(String source) {
        boolean res = false;
        try {
            List<WebElement> applicationCatLabel = tableRoot.findElements(By.xpath("//span[@class='rule-open']"));
            for (int i = 1; i <= applicationCatLabel.size(); i++) {
                res = false;
                WebElement uiApplicationValue = getDriver().findElement(By.xpath("(//table[@class='table CustomTable match-review-table']/thead/following::tr)[" + i + "]/td[3]"));
                JSONObject requestBody = ScenarioContext.getTSNTance().getDataDriveSectionAsJSON("VerifyApplicationValuesRuleTab");
                Set<Map.Entry> entries = requestBody.entrySet();
                for (Map.Entry entry : entries) {
                    String expectedData = entry.getValue().toString().trim();
                    expectedData=expectedData.replace("{{organisation}}", scenarioContext.getOrganisation());
                    if (expectedData.contains(uiApplicationValue.getText().trim())) {
                        Assert.assertEquals(uiApplicationValue.getText().trim(), expectedData);
                        res = true;
                    }
                }
            }
            for (int i = 1; i <= applicationCatLabel.size(); i++) {
                res = false;
                WebElement uiDataBaseValue = getDriver().findElement(By.xpath("(//table[@class='table CustomTable match-review-table']/thead/following::tr)[" + i + "]/td[4]"));
                JSONObject requestBody = ScenarioContext.getTSNTance().getDataDriveSectionAsJSON("VerifyDatabaseValuesRuleTab");
                Set<Map.Entry> entries = requestBody.entrySet();
                for (Map.Entry entry : entries) {
                    String expectedData = entry.getValue().toString().trim();
                    expectedData=expectedData.replace("{{organisation}}", scenarioContext.getOrganisation());
                    if (expectedData.contains(uiDataBaseValue.getText().trim())) {
                        Assert.assertEquals(uiDataBaseValue.getText().trim(), expectedData);
                        res = true;
                    }
                }
            }

        } catch (Exception e) {
            e.getMessage();
        }
        return res;
    }

    public void verifyRulesContent() {
        clickOn(rulesOpen);
        waitFor(ruleLabel);
        Assert.assertEquals(applicationField.getText(), matchedField.getText());
    }

    public boolean getSystemDetails() {
        boolean res = false;
        try {
            clickOn(SystemTab);
            waitABit(3000);
            List<WebElement> applicationCatLabel = tableRoot.findElements(By.xpath("//table[@class='table CustomTable match-review-table']/thead/following::tr"));
            for (int i = 1; i <= applicationCatLabel.size(); i++) {
                String uiApplicationValue = getDriver().findElement(By.xpath("(//table[@class='table CustomTable match-review-table']/thead/following::tr)[" + i + "]/td[3]")).getText().trim();
                JSONObject requestBody = ScenarioContext.getTSNTance().getDataDriveSectionAsJSON("VerifyApplicationValuesSystemTab");
                Set<Map.Entry> entries = requestBody.entrySet();
                for (Map.Entry entry : entries) {
                    String expectedData = entry.getValue().toString().trim();
                    expectedData=expectedData.replace("{{organisation}}", scenarioContext.getOrganisation());
                    if (expectedData.equals(uiApplicationValue)) {
                        Assert.assertEquals(uiApplicationValue, expectedData);
                        res = true;
                        break;
                    }
                }
            }
            for (int i = 1; i <= applicationCatLabel.size(); i++) {
                String uiDataBaseValue = getDriver().findElement(By.xpath("(//table[@class='table CustomTable match-review-table']/thead/following::tr)[" + i + "]/td[4]")).getText().trim();
                JSONObject requestBody = ScenarioContext.getTSNTance().getDataDriveSectionAsJSON("VerifyDatabaseValuesSystemTab");
                Set<Map.Entry> entries = requestBody.entrySet();
                for (Map.Entry entry : entries) {
                    String expectedData = entry.getValue().toString().trim();
                    expectedData=expectedData.replace("{{organisation}}", scenarioContext.getOrganisation());
                    if (expectedData.equals(uiDataBaseValue)) {
                        Assert.assertEquals(uiDataBaseValue, expectedData);
                        res = true;
                        break;
                    }
                }
            }

        } catch (Exception e) {
            e.getMessage();
        }
        return res;
    }

    public boolean getFullApplicationDetails() {
        boolean res = false;
        try {
            clickOn(fullAppDetailsTab);
            waitABit(3000);
            List<WebElement> applicationCatLabel = tableRoot.findElements(By.xpath("//tr[@class='matchreview-ruleTriggered'] | //tr[@class='matchreview-systemMatchTriggered']"));
            if (applicationCatLabel.size() == 0) {
                applicationCatLabel = tableRoot.findElements(By.xpath("//table[@class='table CustomTable match-review-table']/tbody/tr"));
                for (int i = 1; i <= applicationCatLabel.size(); i++) {
                    String uiApplicationValue = tableRoot.findElement(By.xpath("(//table[@class='table CustomTable match-review-table']/tbody/tr)[" + i + "]/td[2]")).getText().trim();
                    JSONObject requestBody = ScenarioContext.getTSNTance().getDataDriveSectionAsJSON("verifyAppValuesFullApplication");
                    Set<Map.Entry> entries = requestBody.entrySet();
                    for (Map.Entry entry : entries) {
                        String expectedData = entry.getValue().toString().trim();
                        expectedData = expectedData.replace("{{organisation}}", scenarioContext.getOrganisation());
                        if (expectedData.equals(uiApplicationValue)) {
                            Assert.assertEquals(uiApplicationValue, expectedData);
                            res = true;
                            break;
                        }
                    }
                }

            } else {

                JSONObject requestBody = ScenarioContext.getTSNTance().getDataDriveSectionAsJSON("verifyAppValuesFullApplication");
                //for (int i = 1; i <= applicationCatLabel.size(); i++) {
                Set<Map.Entry> entries = requestBody.entrySet();
                for (Map.Entry entry : entries) {
                    String expectedData = entry.getValue().toString().trim().toUpperCase();
                    expectedData = expectedData.replace("{{ORGANISATION}}", scenarioContext.getOrganisation());
                    String field = entry.getKey().toString().trim();
                    WebElementFacade actualApplicationColumnValue = find("//td[normalize-space()='"+field+"']/following-sibling::td[1]");
                    if(!expectedData.equalsIgnoreCase(actualApplicationColumnValue.getText())) {
                        return false;
                    }
                }

                try {
                    requestBody = ScenarioContext.getTSNTance().getDataDriveSectionAsJSON("verifyDbValuesFullApplication");
                    entries = requestBody.entrySet();
                    for (Map.Entry entry : entries) {
                        String expectedData = entry.getValue().toString().trim().toUpperCase();
                        expectedData = expectedData.replace("{{ORGANISATION}}", scenarioContext.getOrganisation());
                        String field = entry.getKey().toString().trim();
                        WebElementFacade actualApplicationColumnValue = find("//td[normalize-space()='" + field + "']/following-sibling::td[1]");
                        if (!expectedData.equalsIgnoreCase(actualApplicationColumnValue.getText())) {
                            return false;
                        }
                    }
                }
                catch (NullPointerException ex)
                {
                    //do nothing as there are no values for database column
                }

            }
            verifyFullDetailsColourRecords("verifyRowColorApplication");
            res=true;
        } catch (Exception e) {
            e.getMessage();
        }
        return res;
    }

    public void verifyFullDetailsColourRecords(String key) throws IOException, ParseException {
        try {
            JSONObject requestBody = ScenarioContext.getTSNTance().getDataDriveSectionAsJSON(key);
            Set<Map.Entry> entries = requestBody.entrySet();
            for (Map.Entry entry : entries) {
                if (entry.getValue().toString().equalsIgnoreCase("BLUE"))
                    verifyApplicationValueFieldColor(entry.getKey().toString(), ApplicationConstants.BlueColor);
                if (entry.getValue().toString().equalsIgnoreCase("GREEN"))
                    verifyApplicationValueFieldColor(entry.getKey().toString(), ApplicationConstants.GreenColor);
            }
        }
        catch (NullPointerException ex)
        {
            // This happens when no usage of the key exists in the JSON
        }
    }


    public boolean getFullApplicantDetails() {
        boolean res = false;
        try {
            clickOn(fullAppDetailsTab);
            waitABit(2000);
            clickOn(categoryDropdown);
            waitABit(500);
            clickOn(selectCategory);
            waitABit(3000);
            List<WebElement> applicationCatLabel = tableRoot.findElements(By.xpath("//tr[@class='matchreview-ruleTriggered'] | //tr[@class='matchreview-systemMatchTriggered']"));
            if (applicationCatLabel.size() == 0) {
                applicationCatLabel = tableRoot.findElements(By.xpath("//table[@class='table CustomTable match-review-table']/tbody/tr"));
                for (int i = 1; i <= applicationCatLabel.size(); i++) {
                    String uiApplicationValue = tableRoot.findElement(By.xpath("(//table[@class='table CustomTable match-review-table']/tbody/tr)[" + i + "]/td[2]")).getText().trim().toUpperCase();
                    JSONObject requestBody = ScenarioContext.getTSNTance().getDataDriveSectionAsJSON("verifyAppValuesFullApplication");
                    Set<Map.Entry> entries = requestBody.entrySet();
                    for (Map.Entry entry : entries) {
                        String expectedData = entry.getValue().toString().trim().toUpperCase();
                        expectedData = expectedData.replace("{{ORGANISATION}}", scenarioContext.getOrganisation());
                        if (expectedData.equalsIgnoreCase(uiApplicationValue)) {
                            Assert.assertEquals(uiApplicationValue, expectedData);
                            //res = true;
                            break;
                        }
                    }
                }
            } else {
                JSONObject requestBody = ScenarioContext.getTSNTance().getDataDriveSectionAsJSON("verifyAppValuesFullApplicant");
                    Set<Map.Entry> entries = requestBody.entrySet();
                    for (Map.Entry entry : entries) {
                        String expectedData = entry.getValue().toString().trim().toUpperCase();
                        expectedData = expectedData.replace("{{ORGANISATION}}", scenarioContext.getOrganisation());
                        String field = entry.getKey().toString().trim();
                        WebElementFacade actualApplicationColumnValue = find("//td[normalize-space()='"+field+"']/following-sibling::td[1]");
                        if(!expectedData.equalsIgnoreCase(actualApplicationColumnValue.getText())) {
                            return false;
                        }
                    }

                try {
                    requestBody = ScenarioContext.getTSNTance().getDataDriveSectionAsJSON("verifyDbValuesFullApplicant");
                    entries = requestBody.entrySet();
                    for (Map.Entry entry : entries) {
                        String expectedData = entry.getValue().toString().trim().toUpperCase();
                        expectedData = expectedData.replace("{{ORGANISATION}}", scenarioContext.getOrganisation());
                        String field = entry.getKey().toString().trim();
                        WebElementFacade actualApplicationColumnValue = find("//td[normalize-space()='" + field + "']/following-sibling::td[2]");
                        if (!expectedData.equalsIgnoreCase(actualApplicationColumnValue.getText())) {
                            return false;
                        }
                    }
                }
                catch (NullPointerException ex)
                {
                    //do nothing as there are no values for database column
                }

            }
            verifyFullDetailsColourRecords("verifyRowColorApplicant");
            res = true;
        } catch (Exception e) {
            e.getMessage();
        }
        return res;
    }

    public boolean getFullCBCDetails() {
        boolean res = false;
        try {
            clickOn(fullAppDetailsTab);
            waitABit(2000);
            clickOn(categoryDropdown);
            waitABit(1000);
            clickOn(selectCategory);
            waitABit(3000);
            WebElement Fieldvalue = getDriver().findElement(By.xpath("//td[contains(text(),'User Field 36')]//following::td[2]"));
            JSONObject requestBody = ScenarioContext.getTSNTance().getDataDriveSectionAsJSON("verifyDbValuesFullApplicant");
            Set<Map.Entry> entries = requestBody.entrySet();
            for (Map.Entry entry : entries) {
                String expectedData = entry.getValue().toString().trim();
                if (expectedData.contains(Fieldvalue.getText().trim())) {
                    Assert.assertEquals(Fieldvalue.getText().trim(), expectedData);
                    res = true;
                }
            }
        } catch (Exception e) {
            e.getMessage();
        }
        return res;
    }

    public void verifyRulesContent(String ruleNumber) {
        String[] expectedRuleNumbers = ruleNumber.split(",");
        List<WebElement> applicationCatLabel = tableRoot.findElements(By.xpath("//span[@class='rule-open']"));
        for (int i = 0; i <= applicationCatLabel.size() - 1; i++) {
            ((JavascriptExecutor) getDriver()).executeScript("scroll(0,200)");
            waitABit(1000);
            applicationCatLabel.get(i).click();
            String actualRule = verifyRuleUnderRulesContent.getText().trim();
            boolean result = false;
            for(int j=0;j< expectedRuleNumbers.length;j++)
            {
                 if(actualRule.equals(expectedRuleNumbers[j])){
                     result = true;
                     break;
                 }

            }
            Assert.assertEquals(true, result);
            waitABit(500);
            applicationCatLabel.get(i).click();
        }
    }

    public boolean checkApplicationRecordHeader() {
        boolean res = false;
        try {
            waitABit(3000);
            String[] s = appNum.getText().split("-");
            String actualData = s[1].trim();
            JSONObject expectedData = ScenarioContext.getTSNTance().getDataDriveSectionAsJSON("Application1");
            if (expectedData == null) {
                expectedData = ScenarioContext.getTSNTance().getDataDriveSectionAsJSON("Application");
            }
            Assert.assertEquals(expectedData.get("Application Number").toString().toLowerCase(), actualData.toLowerCase());
            res = true;

        } catch (Exception e) {
            e.getMessage();
        }
        return res;
    }

    public boolean checkApplicationDate() {
        boolean res = false;
        try {

            JSONObject expectedData = ScenarioContext.getTSNTance().getDataDriveSectionAsJSON("Application1");
            if (expectedData == null) {
                expectedData = ScenarioContext.getTSNTance().getDataDriveSectionAsJSON("Application");
            }
            String actualData = applicationDate.getText().trim();
            Assert.assertEquals(expectedData.get("Application Date").toString(), actualData);
            res = true;

        } catch (Exception e) {
            e.getMessage();
        }
        return res;
    }

    public boolean checkApplicationType() {
        boolean res = false;
        try {
            JSONObject expectedData = ScenarioContext.getTSNTance().getDataDriveSectionAsJSON("Application1");
            if (expectedData == null) {
                expectedData = ScenarioContext.getTSNTance().getDataDriveSectionAsJSON("Application");
            }
            String actualData = applicationType.getText().trim();
            Assert.assertEquals(expectedData.get("Application Type").toString().toLowerCase(), actualData.toLowerCase());
            res = true;
        } catch (Exception e) {
            e.getMessage();
        }
        return res;
    }

    public boolean verifyScore(String score){
        return fraudScore.getText().trim().equalsIgnoreCase(score);
    }

    public boolean checkFraudScore(String ruleNumber) {
        boolean res = false;
        try {
            String actual = fraudScore.getText().trim();
            switch (ruleNumber.toUpperCase()) {
                case "PAR0001":
                    fraudScoreValue = ApplicationConstants.FraudScorePar0001;
                    break;
                case "ID00001":
                    fraudScoreValue = (actual.equals(ApplicationConstants.FraudScoreHierarchy_is_Turned_ON)) ?
                            ApplicationConstants.FraudScoreHierarchy_is_Turned_ON : ApplicationConstants.FraudScoreHierarchy_is_Turned_OFF;
                    break;
                case "PAR0002":
                    fraudScoreValue = ApplicationConstants.FraudScorePar0002;
                    break;
                case "PAR0003":
                    fraudScoreValue = ApplicationConstants.FraudScorePar0003;
                    break;
                case "ATI0001":
                    fraudScoreValue = ApplicationConstants.FraudScoreATI0001;
                    break;
                case "M000001":
                    fraudScoreValue = ApplicationConstants.FraudScoreM000001;
                    break;
                case "RL00001":
                    fraudScoreValue = ApplicationConstants.FraudScoreRL00001;
                    break;
                case "A000003":
                    fraudScoreValue = ApplicationConstants.FraudScoreA000003;
                    break;
                case "AL00001":
                    fraudScoreValue = ApplicationConstants.FraudScoreAL00001;
                    break;
                case "AB00003":
                    fraudScoreValue = ApplicationConstants.FraudScoreAB00003;
                    break;
                case "AP00009":
                    fraudScoreValue = ApplicationConstants.FraudScoreAP00009;
                    break;
                case "AB00004":
                    fraudScoreValue = ApplicationConstants.FraudScoreAB00004;
                    break;
                case "AS00005":
                    fraudScoreValue = ApplicationConstants.FraudScoreAS00005;
                    break;
                case "FUZY001":
                    fraudScoreValue = ApplicationConstants.FraudScoreFuzy001;
                    break;
                case "AS00006":
                    fraudScoreValue = ApplicationConstants.FraudScoreAS00006;
                    break;
                case "AF00018":
                    fraudScoreValue = ApplicationConstants.FraudScoreAF00018;
                    break;
                case "AF00019":
                    fraudScoreValue = ApplicationConstants.FraudScoreAF00019;
                    break;
                case "DI00002":
                    fraudScoreValue = ApplicationConstants.FraudScoreDI00002;
                    break;
                case "U000004":
                    fraudScoreValue = ApplicationConstants.FraudScoreU000004;
                    break;
                case "A000017":
                    fraudScoreValue = ApplicationConstants.FraudScoreA000017;
                    break;
                case "A000033":
                    fraudScoreValue = ApplicationConstants.FraudScoreA000033;
                    break;
                case "L000001":
                    fraudScoreValue = ApplicationConstants.FraudScoreL000001;
                    break;
                case "I000003":
                    fraudScoreValue = ApplicationConstants.FraudScoreI000003;
                    break;
                case "IF00001":
                    fraudScoreValue = ApplicationConstants.FraudScoreIF00001;
                    break;
                case "IF00002":
                    fraudScoreValue = ApplicationConstants.FraudScoreIF00002;
                    break;
                case "IF00003":
                    fraudScoreValue = ApplicationConstants.FraudScoreIF00003;
                    break;
                case "IF00004":
                    fraudScoreValue = ApplicationConstants.FraudScoreIF00004;
                    break;

                default:
                    throw new NoSuchElementException(" fraud score does not match");
            }
            if (fraudScoreValue.equalsIgnoreCase(actual)) {
                res = true;
            }

        } catch (Exception e) {
            e.getMessage();
        }
        return res;
    }

    public boolean checkFraudAlert() {
        boolean res = false;
        try {
            String actual = fraudAlert.getText().trim();
            int fraudScoreCompareValue = Integer.parseInt(fraudScoreValue);
            String fraudScoreAlert = "Clean";
            if (fraudScoreCompareValue > Integer.parseInt(ApplicationConstants.FraudAlertSuspectLowerRange) && fraudScoreCompareValue < Integer.parseInt(ApplicationConstants.FraudAlertSuspectUpperRange)) {
                fraudScoreAlert = "Suspect";
            } else if (fraudScoreCompareValue > Integer.parseInt(ApplicationConstants.FraudAlertHFPLowerRange) && fraudScoreCompareValue < Integer.parseInt(ApplicationConstants.FraudAlertHFPUpperRange)) {
                fraudScoreAlert = "HFP";
            }
            if (fraudScoreAlert.equalsIgnoreCase(actual)) {
                res = true;
            }
        } catch (Exception e) {
            e.getMessage();
        }
        return res;
    }

    public void verifyRuleTab() {
        String applicationText = applicationValue.getText();
        String databaseText = dataBaseValue.getText();
        Assert.assertEquals(applicationText, databaseText);
    }

    public boolean getApplicationDetails(String field, String key) {
        boolean res = false;
        try {
            ((JavascriptExecutor) getDriver()).executeScript("scroll(0,-200)");
            waitFor(ExpectedConditions.visibilityOf(fullAppDetailsTab));
            clickOn(fullAppDetailsTab);
            waitABit(3000);
            if (key.contains("Applicant")) {
                if (selectedCategory.getText().trim().equalsIgnoreCase("Application 1")) {

                    clickOn(categoryDropdown);
                    waitABit(500);
                    clickOn(selectCategory);
                    waitABit(3000);
                }
            }
            JSONObject expectedData = ScenarioContext.getTSNTance().getDataDriveSectionAsJSON(key);
            ((JavascriptExecutor) getDriver()).executeScript("scroll(0,200)");
            WebElement applicationValue = tableRoot.findElement(By.xpath("//table[@class='table CustomTable match-review-table']/thead/following::tr/td[contains(text(),'" + field + "')]//following::td[1]"));
            String actualValue = applicationValue.getText().trim();
            Assert.assertEquals(expectedData.get(field).toString().trim().toUpperCase(), actualValue.toUpperCase());
            res = true;
        } catch (Exception e) {
            e.getMessage();
        }
        return res;
    }

    public boolean verifyApplicationValueFieldColor(String field, String expectedColor) {
        boolean res = false;
        try {
            ((JavascriptExecutor) getDriver()).executeScript("scroll(0,200)");
            WebElement applicationFieldColor = tableRoot.findElement(By.xpath("//table[@class='table CustomTable match-review-table']/thead/following::tr/td[normalize-space()='" + field + "']/.."));
            String a = applicationFieldColor.getCssValue("color");
            String actualColor = Color.fromString(a).asHex();
            Assert.assertEquals(expectedColor, actualColor);
            res = true;
        } catch (Exception e) {
            e.getMessage();
        }
        return res;
    }

    public boolean verifyApplicationMatchReview(String applicationNumber) {
        boolean res = false;
        try {
            waitABit(2000);
            waitFor(reviewButton).click();
            waitABit(4000);
            String[] s = appNum.getText().split("-");
            String actualData = s[1].trim();
            if(applicationNumber.equals("1")) {
                JSONObject expectedData = ScenarioContext.getTSNTance().getDataDriveSectionAsJSON("Application");
                Assert.assertEquals(expectedData.get("Application Number").toString().toLowerCase(), actualData.toLowerCase());
            }
            if(applicationNumber.equals("2")) {
                JSONObject expectedData = ScenarioContext.getTSNTance().getDataDriveSectionAsJSON("Application1");
                Assert.assertEquals(expectedData.get("Application Number").toString().toLowerCase(), actualData.toLowerCase());
            }
            res = true;

        } catch (Exception e) {
            e.getMessage();
        }
        return res;
    }

    public void click_Link_Analysis()
    {
        clickOn(Link_Analysis);
    }

    public  void get_Canvas_Details() throws IOException, ParseException {
        waitABit(3000);
       Actions action = new Actions(getDriver());
       action.moveByOffset(700,50).build().perform();
       action.click().build().perform();
           // action.moveToElement(canvas);
//        action.click().build().perform();
        try
//        {
//        if (canvas_ToolTip.isVisible())
        {
            waitABit(3000);
            String application_Number= getDriver().findElement(By.xpath("//div[@class='vis-tooltip']//font")).getText();
            JSONObject dataDrive = scenarioContext.getDataDriveSectionAsJSON("Application");
            String App_number=dataDrive.get("Application Number").toString();
            Assert.assertEquals(App_number,application_Number);
            String application_Type= getDriver().findElement(By.xpath("//strong[contains(text(),'Application Type')]")).getText();
            String App_Type=dataDrive.get("Application Type").toString();
            Assert.assertEquals(application_Type,App_Type);
        }

//        }
        catch (Exception ex)
        {
            ex.getMessage();
        }

    }

    public String[][] getRuleTabDetails() throws InterruptedException {
        String[][] table = new String[20][5];
        String ruleTabRoot = "//table[contains(@class,'match-review-table')]";

        //Header Columns
        table[0][0] = find(ruleTabRoot+"//th[1]").getText();
        table[0][1] = find(ruleTabRoot+"//th[3]").getText();
        table[0][2] = "Rules Triggered";
        table[0][3] = find(ruleTabRoot+"//th[2]").getText();
        table[0][4] = find(ruleTabRoot+"//th[4]").getText();

        List<WebElementFacade> firstColumnValues = findAll(org.openqa.selenium.By.xpath(ruleTabRoot+"//tr/td[1]"));
        List<WebElementFacade> secondColumnValues = findAll(org.openqa.selenium.By.xpath(ruleTabRoot+"//tr/td[2]"));
        List<WebElementFacade> thirdColumnValues = findAll(org.openqa.selenium.By.xpath(ruleTabRoot+"//tr/td[3]"));
        List<WebElementFacade> fourthColumnValues = findAll(org.openqa.selenium.By.xpath(ruleTabRoot+"//tr/td[4]"));
        List<WebElementFacade> ruleDetails = findAll(org.openqa.selenium.By.xpath(ruleTabRoot+"//tr/td[5]/span"));

        for(int row=0; row<firstColumnValues.size(); row++) {
            String appFieldValue1 = firstColumnValues.get(row).getText();
            String appFieldValue2 = secondColumnValues.get(row).getText();
            String appFieldValue3 = thirdColumnValues.get(row).getText();
            String appFieldValue4 = fourthColumnValues.get(row).getText();


            try {
                waitFor(ruleDetails.get(row)).waitUntilClickable();
                ruleDetails.get(row).click();
            }
            catch (ElementClickInterceptedException ex){
                this.evaluateJavascript("scroll(0,300)", ruleDetails.get(row));
                waitABit(500);
                ruleDetails.get(row).click();
            }

            table[row+1][0]=appFieldValue1.substring(appFieldValue1.lastIndexOf("\n") + 1);
            table[row+1][1]=appFieldValue3.substring(appFieldValue3.lastIndexOf("\n") + 1);
            waitFor(verifyRuleUnderRulesContent).waitUntilVisible();
            table[row+1][2]=verifyRuleUnderRulesContent.getText();
            table[row+1][3]=appFieldValue2.substring(appFieldValue2.lastIndexOf("\n") + 1);
            table[row+1][4]=appFieldValue4.substring(appFieldValue4.lastIndexOf("\n") + 1);
            ruleDetails.get(row).click();
        }

        jsHelper.scrollToTheTop(getDriver());
        return table;
    }

    public String[][] getSystemTabDetails() {
        String[][] table = new String[30][5];
        int rowClone=0;
        String systemTabRoot = "//table[contains(@class,'match-review-table')]";
        waitFor(SystemTab);
        clickOn(SystemTab);

        waitFor(systemTabCategoryDropdown).waitUntilClickable();
        clickOn(systemTabCategoryDropdown);

        table[0][0] = find(systemTabRoot+"//th[1]").getText();
        table[0][1] = find(systemTabRoot+"//th[3]").getText();
        table[0][2] = "Matched";
        table[0][3] = find(systemTabRoot+"//th[2]").getText();
        table[0][4] = find(systemTabRoot+"//th[4]").getText();

        int categorySize = findAll(By.xpath("//ng-dropdown-panel//span")).size();
        for(int i=1; i<=categorySize; i++) {
            systemTabCategoryDropdown.click();
            WebElementFacade item = find(By.xpath("(//ng-dropdown-panel//span)["+i+"]"));
            item.click();waitABit(1000);
            List<WebElementFacade> firstColumnValues = findAll(org.openqa.selenium.By.xpath(systemTabRoot+"//tr/td[1]"));
            List<WebElementFacade> secondColumnValues = findAll(org.openqa.selenium.By.xpath(systemTabRoot+"//tr/td[2]"));
            List<WebElementFacade> thirdColumnValues = findAll(org.openqa.selenium.By.xpath(systemTabRoot+"//tr/td[3]"));
            List<WebElementFacade> fourthColumnValues = findAll(org.openqa.selenium.By.xpath(systemTabRoot+"//tr/td[4]"));
            for (int row=0; row < firstColumnValues.size(); row++) {
                table[rowClone+1][0] = firstColumnValues.get(row).getText().substring(firstColumnValues.get(row).getText().lastIndexOf("\n") + 1);
                table[rowClone+1][1] = thirdColumnValues.get(row).getText().substring(thirdColumnValues.get(row).getText().lastIndexOf("\n") + 1);;
                table[rowClone+1][2] = "=";
                table[rowClone+1][3] = secondColumnValues.get(row).getText().substring(secondColumnValues.get(row).getText().lastIndexOf("\n") + 1);;
                table[rowClone+1][4] = fourthColumnValues.get(row).getText().substring(fourthColumnValues.get(row).getText().lastIndexOf("\n") + 1);;
                rowClone++;
            }
        }

        return table;
    }

    public String[][] getRulesTriggeredDetails() {
        String[][] table = new String[20][4];
        String rulesTriggeredRoot = "//app-match-review-rules-triggered";

        clickOn(rulesTriggeredLink);
        waitFor(firstRowInRulesTriggeredSection);

        List<WebElementFacade> header= findAll(rulesTriggeredRoot+"//thead//th");

        for(int i=0; i<header.size(); i++) {
            table[0][i] = header.get(i).getText();
        }

        int rows = findAll(rulesTriggeredRoot+"//tbody/tr").size();
        int columns = findAll(rulesTriggeredRoot+"//tbody/tr[1]/td").size();

        for(int i=1; i<=rows; i++) {
            for (int j = 1; j <= columns; j++) {
                table[i][j-1]=find(rulesTriggeredRoot+"//tbody/tr["+i+"]/td["+j+"]").getText();
            }
        }

        return table;
    }

    public String[][] getMatchReviewHeaderDetails() {
        String[][] table = new String[2][8];

        String applicationNumber = appNum.getText().substring(appNum.getText().lastIndexOf("-") + 1).trim();
        String applicationNumberLabel = appNum.getText().substring(0, appNum.getText().indexOf("-")).trim();

        table[0][0] = applicationNumberLabel;
        table[0][1] = applicationDateHeaderLabel.getText();
        table[0][2] = applicationTypeHeaderLabel.getText();
        table[0][3] = fraudScoreHeaderLabel.getText();
        table[0][4] = scorecardScoreHeaderLabel.getText();
        table[0][5] = noOfRulesTriggeredHeaderLabel.getText();
        table[0][6] = fraudAlertHeaderLabel.getText();
        table[0][7] = amountLimitHeaderLabel.getText();

        table[1][0] = applicationNumber;
        table[1][1] = applicationDateHeader.getText();
        table[1][2] = applicationTypeHeader.getText();
        table[1][3] = fraudScoreHeader.getText();
        table[1][4] = scorecardScoreHeader.getText();
        table[1][5] = noOfRulesTriggeredHeader.getText().substring(0, noOfRulesTriggeredHeader.getText().indexOf("(")).trim();
        table[1][6] = fraudAlertHeader.getText();
        table[1][7] = amountLimitHeader.getText();

        return table;
    }


    public String[][] getRuleTabDetailsFromExcel() throws IOException {
        boolean isbreak = false;
        String[][] table = new String[20][5];
        int r=0,c=0;
        int section=2;
        int ruleTabStart = 5;

        FileInputStream fis = new FileInputStream(fileHelper.getRecentFileFromDownloads());

        XSSFWorkbook workbook = new XSSFWorkbook(fis);
        XSSFSheet sheetName = workbook.getSheetAt(0);
        int rowcount = sheetName.getPhysicalNumberOfRows();
        int colcount = sheetName.getRow(0).getPhysicalNumberOfCells();

        for (int i = 0; i < table[0].length; i++) {
            table[r][i] = sheetName.getRow(ruleTabStart).getCell(section+1+i).getStringCellValue();
        }

        r++;
        for (int i = 7; i < rowcount && !isbreak; i++) {
            for (int j = section; j < colcount; j++) {
                if(j==section && sheetName.getRow(i).getCell(j).toString().contains("System"))
                {
                    isbreak = true;
                    break;
                }

                String testdata1 = "";
                if(!(sheetName.getRow(i).getCell(j) == null || sheetName.getRow(i).getCell(j).getCellType() == CellType.BLANK))
                    testdata1 = sheetName.getRow(i).getCell(j).getStringCellValue();
                if (!testdata1.isEmpty()){
                    table[r][c] = testdata1; c=c+1;
                }
            }
            r=r+1; // Increase Row Index
            c=0; // Reset Column Index
        }
        return table;
    }

    public String[][] getSystemTabDetailsFromExcel() throws IOException {

        boolean isbreak = false;
        String[][] table = new String[30][5];
        int systemRow = 0;
        int r=0,c=0;
        int section = 2;

        FileInputStream fis = new FileInputStream(fileHelper.getRecentFileFromDownloads());

        XSSFWorkbook workbook = new XSSFWorkbook(fis);
        XSSFSheet sheetName = workbook.getSheetAt(0);
        int rowcount = sheetName.getPhysicalNumberOfRows();

        for (int i = 0; i < rowcount; i++) {
            boolean isRuleRow = sheetName.getRow(i).getCell(section).toString().contains("System");
            if (isRuleRow) {
                systemRow = i;
                break;
            }
        }

        int colcount = sheetName.getRow(systemRow).getPhysicalNumberOfCells();

        for (int i = 0; i < table[0].length; i++) {
            table[r][i] = sheetName.getRow(systemRow).getCell(section+1+i).getStringCellValue();
        }

        r++;

        for (int i = systemRow+2; i < rowcount && !isbreak; i++) {
            for (int j = section; j < colcount; j++) {

                    if (j == section && sheetName.getRow(i).getCell(j).toString().contains("Rules Triggered")) {
                        isbreak = true;
                        break;
                    }
                    String testdata1 = "";
                    if (!(sheetName.getRow(i).getCell(j) == null || sheetName.getRow(i).getCell(j).getCellType() == CellType.BLANK))
                        testdata1 = sheetName.getRow(i).getCell(j).getStringCellValue();
                    if (!testdata1.isEmpty()) {
                        table[r][c] = testdata1;
                        c = c + 1;
                    }

            }
            r=r+1; // Increase Row Index
            c=0; // Reset Column Index
        }

        return table;
    }

    public String[][] getRulesTriggeredFromExcel() throws IOException {
        String[][] table = new String[20][4];
        int ruleTriggeredRow=0;
        int r=0,c=0;
        int section = 2;

        FileInputStream fis = new FileInputStream(fileHelper.getRecentFileFromDownloads());

        XSSFWorkbook workbook = new XSSFWorkbook(fis);
        XSSFSheet sheetName = workbook.getSheetAt(0);
        int rowcount = sheetName.getPhysicalNumberOfRows();

        for (int i = 0; i < rowcount; i++) {
            boolean isRuleRow = sheetName.getRow(i).getCell(section).toString().contains("Rules Triggered");
            if (isRuleRow) {
                ruleTriggeredRow = i;
                break;
            }
        }
        int colcount = sheetName.getRow(ruleTriggeredRow).getPhysicalNumberOfCells();

        for (int i = 0; i < table[0].length; i++) {
            String value = sheetName.getRow(ruleTriggeredRow).getCell(section+1+i).getStringCellValue();
            if(value!=null || sheetName.getRow(0).getCell(i).getCellType() == CellType.BLANK)
                table[r][i] = value;
        }

        r++;

        for (int i = ruleTriggeredRow+2; i < ruleTriggeredRow+3; i++) {
            for (int j = section+1; j < colcount; j++) {
                String testdata1 = "";
                if (!(sheetName.getRow(i).getCell(j) == null || sheetName.getRow(i).getCell(j).getCellType() == CellType.BLANK))
                    testdata1 = sheetName.getRow(i).getCell(j).getStringCellValue();

                 table[r][c] = testdata1;
                c = c + 1;
            }
            r=r+1; // Increase Row Index
            c=0; // Reset Column Index
        }

        return table;
    }

    public String[][] getHeaderFromExcel() throws IOException {
        int section = 2;
        String[][] table = new String[2][8];
        int r = 0, c = 0;

        FileInputStream fis = new FileInputStream(fileHelper.getRecentFileFromDownloads());

        XSSFWorkbook workbook = new XSSFWorkbook(fis);
        XSSFSheet sheetName = workbook.getSheetAt(0);

        int colcount = sheetName.getRow(0).getPhysicalNumberOfCells();

        for (int i = 0; i < table[0].length; i++) {
            String value = sheetName.getRow(0).getCell(section+1+i).getStringCellValue();
            if(value!=null || sheetName.getRow(0).getCell(i).getCellType() == CellType.BLANK)
                table[r][i] = value;
        }

        r++;

        for (int i = 2; i < 3; i++) {
            for (int j = 3; j < colcount; j++) {
                String testdata1 = "";
                if (!(sheetName.getRow(i).getCell(j) == null || sheetName.getRow(i).getCell(j).getCellType() == CellType.BLANK))
                    testdata1 = sheetName.getRow(i).getCell(j).getStringCellValue();

                table[r][c] = testdata1;
                c = c + 1;
            }
            r=r+1; // Increase Row Index
            c=0; // Reset Column Index
        }
        return table;
    }

    public String getRuleCodeOnMouseHover() {
        Actions action = new Actions(getDriver());
        action.moveToElement(ruleTriggeredUnderMatchesTab).perform();
        return popupAfterMouseHover.getText();

    }

    public void clickingMatchReviewButton() {
        clickOn(reviewButton);
        waitFor(applicationTitle).isDisplayed();


    }

    public boolean getRedError() {

        if (redErrorMatchReview.isVisible())
            return true;
        else
            return false;

    }

    public boolean verifyEqualInRule() {
        clickOn(valueMatcheTab);
        String operator = equalOperator.getText();
        if (operator.contains("="))
        {
            return true;
        }
            return false;

    }

    public boolean fullButtonExist() {
        Boolean buttonDisplay = fullDataButton.isDisplayed();
        if (buttonDisplay)
        {
            return true;
        }
        return false;
    }

    public void clickFullDataButton() {
        clickOn(fullDataButton);
        waitFor(referenceTableReview);
    }

    public void clickFullDataButtonOnResultTable() {
        clickOn(fullDataButton);
        waitFor(resultTableReview);
    }

    public void closeButton() {
        clickOn(closeButton);

    }
}
