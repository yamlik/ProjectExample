package com..test.pages.rules;

import com..test.context.constants.ApplicationConstants;
import com..test.pages.BasePage;
import net.serenitybdd.core.annotations.findby.By;
import net.serenitybdd.core.annotations.findby.FindBy;
import net.serenitybdd.core.pages.WebElementFacade;
import net.thucydides.core.pages.PageObject;
import org.json.simple.JSONObject;
import org.junit.Assert;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.Map;
import java.util.Set;

public class RulesConfigurationPage extends BasePage {
    private PageObject page;
    private String maximumSimilaityValue = "0.99";

    @FindBy(xpath = "//h2[text()='Rules Configuration']")
    private WebElementFacade ruleConfigurationHeader;

    @FindBy(xpath = "//h2[text()='Confirmation']")
    private WebElementFacade confirmationRuleMessage;

    @FindBy(xpath = "//h2[text()='Information']")
    private WebElementFacade informationRuleMessage;

    @FindBy(xpath = "//button[contains(text(),'Add')]")
    private WebElementFacade addButton;

    @FindBy(xpath = "//ng-select[@bindvalue='RuleAdminGroupCode']//input")
    private WebElementFacade ruleAdminGroupDropDown;

    @FindBy(xpath = "//input[@formcontrolname= 'ruleCode']")
    private WebElementFacade ruleCode;

    @FindBy(xpath = "//input[@formcontrolname= 'description']")
    private WebElementFacade ruleDescription;

    @FindBy(xpath = "//input[@formcontrolname= 'applicationAgeFrom']")
    private WebElementFacade applicationAgeFrom;

    @FindBy(xpath = "//input[@formcontrolname= 'applicationAgeTo']")
    private WebElementFacade applicationAgeTo;

    @FindBy(xpath = "//input[@formcontrolname= 'ruleScore']")
    private WebElementFacade ruleScore;

    @FindBy(xpath = "//label[contains(.,'Rule Conditions')]")
    private WebElementFacade ruleConditionsRadioButton;

    @FindBy(xpath = "//div[@id='alp-subtab__content2']/div/div/span/img")
    private WebElementFacade addRuleLink;

    @FindBy(xpath = "//label[contains(.,'Application Value')]")
    private WebElementFacade appllicationValueLink;

    @FindBy(xpath = "//label[contains(.,'Database Value')]")
    private WebElementFacade databaseValueLink;

    @FindBy(xpath = "//label[contains(.,' Rule Details ')]")
    private WebElementFacade rule_Details;

//    @FindBy(xpath = "//ng-select[contains(@formcontrolname,'applicationCategoryNumber')]")
//    private WebElementFacade applicationCategorySelect;

    //  @FindBy(xpath = "//div[contains(text(),'Application Category')]//following-sibling::div//ng-select")
    @FindBy(xpath = "//div[contains(text(),'Application Category')]//following::div[5]//input")
    private WebElementFacade applicationCategorySelect;

    //   @FindBy(xpath = "//ng-select[contains(@formcontrolname,'databaseCategoryNumber')]")
    @FindBy(xpath = "//div[contains(text(),'Database Category')]//following::div[5]//input")
    private WebElementFacade databseCategorySelect;

    //   @FindBy(xpath = "//ng-select[contains(@formcontrolname,'applicationFieldNumber')]")
    @FindBy(xpath = "//div[contains(text(),'Field')]//following::div[5]//input")
    private WebElementFacade fieldSelect;

    //  @FindBy(xpath = "//ng-select[contains(@formcontrolname,'databaseFieldNumber')]")
    @FindBy(xpath = "//div[contains(text(),'Field')]//following::div[5]//input")
    private WebElementFacade DatabaseFieldSelect;

    @FindBy(xpath = "//ng-select[contains(@formcontrolname,'operatorValue')]")
    private WebElementFacade operatorSelect;

    //@FindBy(xpath = "//textarea[contains(@formcontrolname,'assignmentValue')]")
    @FindBy(xpath = "//*[@id='alp-subtab__content2']//following::textarea")
    private WebElementFacade value;

    @FindBy(css = ".card-btm-btn > .alp-bonito-button-dark")
    private WebElementFacade addRuleButton;

    @FindBy(xpath = "//span[@class='ladda-label'][contains(text(),'Save')]| //button[contains(text(),'Submit')]")
    private WebElementFacade saveRule;

    @FindBy(xpath = "//button[contains (text(), 'Yes')]")
    private WebElementFacade yesButton;
    @FindBy(xpath = "//button[contains (text(), 'No')]")
    private WebElementFacade noButton;

    @FindBy(xpath = "//h2[@class='alp-bonito-h2']/../div")
    private WebElementFacade text;

    @FindBy(xpath = "//h2[contains(text(),'Error')]")
    private WebElementFacade errorPopupHeading;

    @FindBy(xpath = "//input[contains(@formcontrolname,'similarity')]")
    private WebElementFacade similarity;

    @FindBy(css = ".col-sm-12 > .toggle-pos .simple-toggle-slider")
    private WebElementFacade partialDefinition;

    @FindBy(xpath = "(//span[@class='simple-toggle-slider simple-toggle-round'])[2]")
    private WebElementFacade variable;

    @FindBy(xpath = "//ng-select[contains(@formcontrolname, 'logicalOperator')]")
    private WebElementFacade logicalOperator;

    @FindBy(xpath = "//div[@id='alp-subtab__content2']/div/div/div/div/div/app-rule-definition-viewer")
    private WebElementFacade ruleDefinitionViewer;

    @FindBy(xpath = "//input[@formcontrolname= 'partialStart']")
    private WebElementFacade startPosition;
    @FindBy(xpath = "//li//a[contains(text(),'Pending')]")
    private WebElementFacade pendingLink;

    @FindBy(xpath = "//div[@class='dropdown']//div//ul//li[1] | //div[@class='dropup']//div//ul//li[1]")
    private WebElementFacade dropUpValue1;

    @FindBy(xpath = "//div[@class='dropdown']//div//ul//li[2] | //div[@class='dropup']//div//ul//li[2]")
    private WebElementFacade dropUpValue2;

    @FindBy(xpath = "//input[@formcontrolname= 'partialLength']")
    private WebElementFacade partialLength;

    @FindBy(xpath = "//label[contains(text(),'Match Application To Itself')]")
    private WebElementFacade matchApplicationItself;

    @FindBy(xpath = "//*[@id='Row_0']/td[2]")
    private WebElementFacade firstRowInGridRuleColumn;

    //Rules Configuration Page Filter Popup
    @FindBy(xpath = "//button[contains(text(),'Filter')]")
    private WebElementFacade filterButton;

    @FindBy(xpath = "//ngb-modal-window//app-header-label[normalize-space()='Rule']/ancestor::div[contains(@class,'filter-row')]//input")
    private WebElementFacade ruleTextBoxInFilterPopup;

    @FindBy(xpath = "//div[contains(@class,'filter-footer')]//button[text()='Apply']")
    private WebElementFacade applyButtonOnFilterPopup;

    @FindBy(xpath = " //div[contains(text(),'Print')]")
    private WebElementFacade print;

    @FindBy(xpath = "//button[contains(text(),'Excel')]")
    private WebElementFacade print_Excel;
    @FindBy(xpath = "//button[contains(text(),'CSV')]")
    private WebElementFacade print_CSV;


    @FindBy(xpath = "//button[contains(text(),'Cancel')]//following::button")
    private WebElementFacade click_print;

    @FindBy(xpath = "//div[contains(text(),'Known Fraud')]//following::input")
    private WebElementFacade label_Known_Fraud;


    public void clickAdd() {
        clickOn(addButton);
    }

    public void enterRuleAdminGroup(String ruleAdminGroup) { ruleAdminGroupDropDown.typeAndEnter(ruleAdminGroup); }


    public void clickFilterButton() {
        clickOn(filterButton);
    }

    public void enterRuleCodeInFilterPopup(String rule) { typeInto(ruleTextBoxInFilterPopup, rule);}

    public void clickApplyButtonOnFilterPopup() {
        clickOn(applyButtonOnFilterPopup);
    }

    public void enterRuleCode(String ruleCodeValue) {
        typeInto(ruleCode, ruleCodeValue);
    }

    public void enterRuleDescritption(String ruleDescritptionValue) {
        typeInto(ruleDescription, ruleDescritptionValue);
    }

    public void enterAgeOfApplicationStart(String ageOfApplicationStart) {
        typeInto(applicationAgeFrom, ageOfApplicationStart);
    }

    public void enterAgeOfApplicationTo(String ageOfApplicationTo) {
        typeInto(applicationAgeTo, ageOfApplicationTo);
    }


    public void clickRuleConditions() {
        waitFor(ExpectedConditions.visibilityOf(ruleConditionsRadioButton));
        JavascriptExecutor jse = (JavascriptExecutor) getDriver();
        jse.executeScript("window.scrollBy(0,-250)", "ruleConditionsRadioButton");
        waitABit(2000);
        clickOn(ruleConditionsRadioButton);

    }

    public void clickAddRuleLink() {
        waitFor(addRuleLink);
        waitABit(3000);
        clickOn(addRuleLink);

    }

    public void clickApplicationValueLink() {

        clickOn(appllicationValueLink);
    }

    public void enterApplicationCategory(String applicationCategoryValue) {
        waitABit(500);
        clickOn(applicationCategorySelect);
        waitABit(500);
        applicationCategorySelect.typeAndEnter(applicationCategoryValue);
    }

    public void enterDatabaseCategory(String databaseCategoryValue) {

        clickOn(databseCategorySelect);
        waitABit(500);
        databseCategorySelect.typeAndEnter(databaseCategoryValue);
    }

    public void enterField(String fieldValue) {
        clickOn(fieldSelect);
        waitABit(500);
        fieldSelect.typeAndEnter(fieldValue);
    }

    public void enterDfield(String DfieldValue) {

        clickOn(DatabaseFieldSelect);
        waitABit(500);
        DatabaseFieldSelect.typeAndEnter(DfieldValue);
    }

    public void enterOperator(String operatorValue) {

        clickOn(operatorSelect);
        waitABit(500);
        WebElement val = getDriver().findElement(By.xpath("//span[contains(text(),'" + operatorValue + "')]"));
        val.click();
    }

    public void enterLogicalOperator(String logicalOperatorValue) {
        clickOn(logicalOperator);
        waitABit(500);
        WebElement val = getDriver().findElement(By.xpath("//span[contains(text(),'" + logicalOperatorValue + "')]"));
        val.click();
    }

    public void enterValue(String fieldValue) {
        waitABit(1000);
        typeInto(value, fieldValue);
    }

    public void addRule() {

        waitFor(addRuleButton);
        ((JavascriptExecutor) getDriver()).executeScript("arguments[0].scrollIntoView(true);", addRuleButton);
        waitABit(1000);
        clickOn(addRuleButton);

    }

    public void saveRule() {
        waitABit(1000);
        waitFor(ExpectedConditions.visibilityOf(saveRule));
        clickOn(saveRule);

    }

    public void yesConfirmationButton() {
        waitABit(1000);
        if(informationRuleMessage.isVisible()) {
            waitFor(ExpectedConditions.visibilityOf(yesButton));
            clickOn(yesButton);
        }
    }

    public boolean result() {
        waitFor(errorPopupHeading);
        waitFor(text);
        return text.getText().contains(ApplicationConstants.errorFuzzyRule);
    }

    public void enterSimilarity(String similarityValue) {
        similarity.typeAndTab(similarityValue);
        waitABit(4000);
    }

    public boolean maximumSimilarity() {
        String similarityValue = ruleDefinitionViewer.getText();

        if (similarityValue.contains(maximumSimilaityValue)) {
            return true;
        } else
            return false;
    }

    public void enterRuleConditions(String key, JSONObject value) {
        clickAddRuleLink();
        if (key.contains("Application Value")) {
            clickOn(appllicationValueLink);
            enterApplicationCategory(value.get("Application Category").toString());
            enterField(value.get("Field").toString());
            enterOperator(value.get("Operator").toString());
            enterValue(value.get("Value").toString());

            try {
                if (value.get("Operator").toString().toLowerCase().contains("fuzzy match")) {
                    enterSimilarity(value.get("Similarity").toString());
                }
            } catch (NullPointerException ex) {

            }
        }

        if (key.contains("Database Field")) {
            enterApplicationCategory(value.get("Application Category").toString());
            enterField(value.get("Field").toString());
            enterOperator(value.get("Operator").toString());

            enterDatabaseCategory(value.get("Database Category").toString());
            enterDfield(value.get("DField").toString());
            try {
                if (value.get("Similarity").toString().length() > 0) {
                    enterSimilarity(value.get("Similarity").toString());
                }


            } catch (NullPointerException ex) {
                //Do nothing
            }

            try {
                enterValue(value.get("Value").toString());
            } catch (NullPointerException ex) {

            }

            try {
                if (value.get("Logical Operator").toString().length() > 0) {

                    enterLogicalOperator(value.get("Logical Operator").toString());
                }
            } catch (NullPointerException ex) {
                //Do nothing
            }

            if (value.get("Partial Definition").toString().contains("1")) {
                waitABit(3000);
                clickOn(partialDefinition);
                enterStartPosition(value.get("Start Position").toString());
                enterLength(value.get("Length").toString());
            }
        }

        if (key.contains("Database Value")) {
            waitABit(3000);
            clickOn(databaseValueLink);
            enterApplicationCategory(value.get("Application Category").toString());
            enterField(value.get("Field").toString());
            enterOperator(value.get("Operator").toString());
            try {
                if (value.get("Operator").toString().toLowerCase().contains("fuzzy match")) {
                    enterSimilarity(value.get("Similarity").toString());
                }
            } catch (NullPointerException ex) {

            }
            try {
                if (value.get("Partial Definition").toString().contains("1")) {
                    waitABit(3000);
                    clickOn(partialDefinition);
                    enterStartPosition(value.get("Start Position").toString());
                    enterLength(value.get("Length").toString());
                }
            } catch (NullPointerException ex) {

            }
            try {
                if (value.get("Logical Operator").toString().length() > 0) {
                    waitABit(3000);
                    enterLogicalOperator(value.get("Logical Operator").toString());
                }
            } catch (NullPointerException ex) {
                //do nothing
            }
            try {
                if (value.get("Variable").toString().contains("1")) {
                    waitABit(3000);
                    clickOn(variable);
                    enterValue(value.get("Value").toString());
                } else {
                    enterValue(value.get("Value").toString());
                }
            } catch (NullPointerException ex) {
                //do nothing
            }
        }
        addRule();
    }

    private void enterStartPosition(String startPositionValue) {
        typeInto(startPosition, startPositionValue);
    }

    private void enterLength(String lengthValue) {
        typeInto(partialLength, lengthValue);
    }

    public void yesButton1() {
        waitABit(2000);
        if (text.getText().contains(ApplicationConstants.addRule) || (text.getText().contains(ApplicationConstants.confirmationFuzzyRule)) ) {
            clickOn(yesButton);
            waitABit(2000);
        }
        if (text.getText().contains(ApplicationConstants.addRuleSet)) {
            waitFor(noButton);
            waitABit(2000);
            clickOn(noButton);
        } /*else {
            waitFor(yesButton);
            waitABit(1000);
            clickOn(yesButton);
        }*/
    }

    public void yesButton2() {
        waitABit(2000);
        clickOn(yesButton);
        waitABit(2000);
        clickOn(noButton);
    }

    public void yesButton3() {
        waitABit(1000);
        clickOn(yesButton);
    }

    public void enterRuleScore(String ruleScoreValue) {
        typeInto(ruleScore, ruleScoreValue);
    }

    public void clickMatchApplicationItself() {

        waitABit(3000);
        clickOn(matchApplicationItself);
        waitABit(3000);
    }

    public void verifySelfApprovalATSNTss() {
        for (int i = 1; i <= 3; i++) {
            waitFor(pendingLink).click();
            WebElement RulePendingApproval = getDriver().findElement(org.openqa.selenium.By.xpath("//tr["+i+"]//td[contains(text(),'Rule')]//following-sibling::td[last()]//img"));
            waitABit(1000);
            RulePendingApproval.click();
            if (i == 1) {
                Assert.assertEquals(dropUpValue1.getText(), "View");
                Assert.assertEquals(dropUpValue2.getText(), "Approve");
                pendingLink.click();

            }
            if (i == 2) {
                Assert.assertEquals(dropUpValue1.getText(), "View");
                Assert.assertNotEquals(dropUpValue2.getText(), "Approve");
                pendingLink.click();

            }

            if (i == 3) {
                Assert.assertEquals(dropUpValue1.getText(), "View");
                Assert.assertNotEquals(dropUpValue2.getText(), "Approve");
                pendingLink.click();
            }
        }

    }

    public void verifyOtherApprovalATSNTss() {
        waitFor(pendingLink).click();
        for (int i = 1; i <= 3; i++) {
            WebElement RulePendingApproval = getDriver().findElement(org.openqa.selenium.By.xpath("//tr["+i+"]//td[contains(text(),'Rule')]//following-sibling::td[last()]//img"));
            waitABit(1000);
            RulePendingApproval.click();
            if (i == 1) {
                Assert.assertEquals(dropUpValue1.getText(), "View");
                Assert.assertEquals(dropUpValue2.getText(), "Approve");
                pendingLink.click();
            }
            if (i == 2) {
                Assert.assertEquals(dropUpValue1.getText(), "View");
                Assert.assertNotEquals(dropUpValue2.getText(), "Approve");
                pendingLink.click();
            }

            if (i == 3) {
                Assert.assertEquals(dropUpValue1.getText(), "View");
                Assert.assertEquals(dropUpValue2.getText(), "Approve");
                pendingLink.click();
            }

        }
    }

    public void verifyAllApprovalATSNTss() {
        waitFor(pendingLink).click();
        for (int i = 1; i <= 3; i++) {
            WebElement RulePendingApproval = getDriver().findElement(org.openqa.selenium.By.xpath("//tr["+i+"]//td[contains(text(),'Rule')]//following-sibling::td[last()]//img"));
            waitABit(1000);
            RulePendingApproval.click();
            if (i == 1) {
                Assert.assertEquals(dropUpValue1.getText(), "View");
                Assert.assertEquals(dropUpValue2.getText(), "Approve");
                pendingLink.click();
            }
            if (i == 2) {
                Assert.assertEquals(dropUpValue1.getText(), "View");
                Assert.assertEquals(dropUpValue2.getText(), "Approve");
                pendingLink.click();
            }

            if (i == 3) {
                Assert.assertEquals(dropUpValue1.getText(), "View");
                Assert.assertEquals(dropUpValue2.getText(), "Approve");
                pendingLink.click();
            }
        }
    }

    public void verifyNoApprovalATSNTss() {
        waitFor(pendingLink).click();
        for (int i = 1; i <= 3; i++) {
            WebElement RulePendingApproval = getDriver().findElement(org.openqa.selenium.By.xpath("//tr["+i+"]//td[contains(text(),'Rule')]//following-sibling::td[last()]//img"));
            waitABit(1000);
            RulePendingApproval.click();
            if (i == 1) {
                Assert.assertEquals(dropUpValue1.getText(), "View");
                Assert.assertNotEquals(dropUpValue2.getText(), "Approve");
                pendingLink.click();
            }
            if (i == 2) {
                Assert.assertEquals(dropUpValue1.getText(), "View");
                Assert.assertNotEquals(dropUpValue2.getText(), "Approve");
                pendingLink.click();
            }

            if (i == 3) {
                Assert.assertEquals(dropUpValue1.getText(), "View");
                Assert.assertNotEquals(dropUpValue2.getText(), "Approve");
                pendingLink.click();
            }
        }
    }

    public void approveAllPendingRules() {
        waitFor(pendingLink).click();
        for (int i = 1; i <= 3; i++) {
            waitABit(1000);
            WebElement RulePendingApproval = getDriver().findElement(org.openqa.selenium.By.xpath("//tr//td[contains(text(),'Rule')]//following-sibling::td[last()]//img"));
            RulePendingApproval.click();
            dropUpValue2.click();
            waitABit(1000);
        }
    }

    public void enterFilterPopupValues(Set<Map.Entry> entrySet) {
        for (Map.Entry entry : entrySet) {
            WebElement filterField =
                    getDriver().findElement(By.xpath("//div[contains(@class,'filter-row') and .//app-header-label[normalize-space()='" + entry.getKey().toString() + "']]//input"));
            typeInto(filterField, entry.getValue().toString());
        }
        clickApplyButtonOnFilterPopup();
    }

    public void verifyRuleCodeExists(String ruleCode) {
        try {
            Assert.assertEquals("Rule code " + ruleCode + "does not exist.", firstRowInGridRuleColumn.getText(), ruleCode);
        } catch (NoSuchElementException exception) {
            Assert.fail("Rule code " + ruleCode + " does not exist.");
        }
    }

    public void download_Excel() throws InterruptedException {
        waitFor(ExpectedConditions.visibilityOf(print));
        clickOn(print);
        waitFor(ExpectedConditions.visibilityOf(print_Excel));
        clickOn(print_Excel);
        waitFor(ExpectedConditions.visibilityOf(click_print));
        clickOn(click_print);
        Thread.sleep(2000);
    }

    public void get_label_Name() {
        String Data = "kF";
        typeInto(label_Known_Fraud, Data);

    }


}

