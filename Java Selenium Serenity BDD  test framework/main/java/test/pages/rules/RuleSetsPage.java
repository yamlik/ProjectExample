package com..test.pages.rules;

import com..test.context.constants.ApplicationConstants;
import com..test.pages.BasePage;
import net.serenitybdd.core.annotations.findby.By;
import net.serenitybdd.core.annotations.findby.FindBy;
import net.serenitybdd.core.pages.WebElementFacade;
import net.thucydides.core.pages.PageObject;
import org.junit.Assert;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class RuleSetsPage extends BasePage {
    private PageObject page;
    public WebDriver Driver;
    private static int ruleSetOrderId=1;

    @FindBy(xpath = "//button[contains(text(),'Add')]")
    private WebElementFacade addButton;

    @FindBy(xpath = "//input[@id='RuleSetName']")
    private WebElementFacade RuleSetName;

    @FindBy(xpath = "//div[@class='ng-select-container ng-has-value']")
    private WebElementFacade Criteria;

    @FindBy(xpath = "//div[contains(text(),'All Applications')]")
    private WebElementFacade allaplications;

    @FindBy(xpath = "(//button[@class='alp-bonito-button-default'])[1]")
    private WebElementFacade Filter;

    @FindBy(xpath = "  //img[@class='loadmore']")
    private WebElementFacade clickEllipsis;

    @FindBy(xpath = "//li[contains(text(),'Rule Performance')]")
    private WebElementFacade clickRulePerformance;

    @FindBy(xpath = "//div[contains(text(),'Criteria')]//following::div| //ng-select[@bindlabel='CriteriaName']")
    private WebElementFacade selectCriteria;

    @FindBy(xpath = "//ng-select[@formcontrolname='ruleAdminGroup']//input")
    private WebElementFacade selectRuleAdminGroup;

    @FindBy(xpath = "//button[contains(text(),'Test')]")
    private WebElementFacade clickOnTest;

    @FindBy(xpath = "//span[contains(text(),'All Applications')]\n")
    private WebElementFacade clickOnAllApplications;

    @FindBy(xpath = "//button[contains(text(),'Execute')]")
    private WebElementFacade clickOnExecute;

    @FindBy(xpath = "/html/body/ngb-modal-window/div/div/app-static-filter/div[2]/div/div/div[1]/div[3]/input")
    private WebElementFacade EnterRulename;

    @FindBy(xpath = " (//div[@class='body-text inner-label']//following::input")
    private WebElementFacade EnterRule;

    @FindBy(xpath = "//ng-select[@formcontrolname='ruleAdminGroup']//input")
    private WebElementFacade ruleAdminGrpDropDown;

    ////td[contains(text(),'RL00001')]
    @FindBy(xpath = "//tbody//tr")
    private WebElementFacade selectRule;

    @FindBy(xpath = "//table//tbody")
    private WebElementFacade tableBody;

    @FindBy(xpath="//tr[@class='active']")
    private WebElementFacade activeRow;

    @FindBy(xpath = "//button[contains(text(),'Apply')]")
    private WebElementFacade Applyrule;

    @FindBy(xpath = "(//div[@class='button-col']//div//img)[1]")
    private WebElementFacade rightNavigation;

    @FindBy(xpath = "//button[contains(text(),'Save')]")
    private WebElementFacade saveRule;

    @FindBy(xpath = "//h2[text()='Rule Sets']")
    private WebElementFacade ruleSetsHeader;

    @FindBy(xpath = "//input[@placeholder='Number']")
    private WebElementFacade orderId;
    //th
    @FindBy(xpath = "//th")
    private WebElementFacade table;

    @FindBy(xpath = "//div[@class='match-review-rules-labeltext match-review-rules-labeltext-linkstyle']")
    private WebElementFacade triggeredRuleLink63;

    @FindBy(xpath = "//a[contains(text(),'View all rules triggered')]")
    private WebElementFacade triggeredRuleLink62;

    @FindBy(xpath = "//span[text()='Rules Triggered']")
    private WebElementFacade ruleTriggered;

    public void clickAdd()  {
        waitFor(ExpectedConditions.visibilityOf(ruleSetsHeader));
        clickOn(addButton);

    }

    public void clickFilter() {
        clickOn(Filter);
    }

    public void EnterRuleSetname(String element) {
        typeInto(RuleSetName, element);
    }

    public void clickApplyRule() {
        clickOn(Applyrule);
    }

    public void enterRuleAdminGroup(String ruleAdminGroup) { ruleAdminGrpDropDown.typeAndEnter(ruleAdminGroup); }

    public void EnterRulename(String element) {
        typeInto(EnterRulename, element);
    }

    public void enterRule(String element) {
        typeInto(EnterRule, element);

    }

    public void selectcriteria() throws InterruptedException {
        clickOn(Criteria);
        clickOn(allaplications);
    }

    public void selectSecondCriteria(String criteria) throws InterruptedException {
        clickOn(Criteria);
        WebElement ele = getDriver().findElement(By.xpath("//div[contains(text(),'"+criteria+"')]"));
        clickOn(ele);
    }

    public void addOrderId() throws InterruptedException {
        typeInto(orderId, String.valueOf(ruleSetOrderId++));
    }

    public void selectRule() {
        clickOn(selectRule);
    }

    public void selectRules(String ruleCode) {
        JavascriptExecutor jse = (JavascriptExecutor) getDriver();
//        clickOn(tableBody);
//        clickOn(tableBody);

        WebElement ruleSets= getDriver().findElement(By.xpath("//td[(text()='"+ruleCode+"')]/.."));

        ((JavascriptExecutor) getDriver()).executeScript("arguments[0].scrollIntoView(true);", ruleSets);waitABit(1000);
        jse.executeScript("arguments[0].click();", ruleSets);

    }

    public void unselectActiveRow() {
       clickOn(activeRow);
    }

    public void clickRightNavigation() {
        clickOn(rightNavigation);
    }

    public void clickOnTriggeredLink() {

        if (productVersion.equals("")|| productVersion.equals(".1")|| productVersion.equals(".2")|| productVersion.equals(".0"))
            clickOn(triggeredRuleLink63);
        if (productVersion.equals(".0") )
            clickOn(triggeredRuleLink62);
        waitABit(2000);
    }

    public void saveRule() {
        waitABit(1000);
        clickOn(saveRule);
        waitABit(5000);
        waitFor(ExpectedConditions.visibilityOf(ruleSetsHeader));

    }

    public void verifyRuleContent(String ruleCode) {
        String[] categories = ruleCode.split(",");
        for (String item : categories) {
            String actualRulesNumber = getDriver().findElement(By.xpath("//td[text()='" + item + "']")).getText().trim();
            Assert.assertEquals(actualRulesNumber, item.trim());

            switch (item.toUpperCase()) {
                case "AL00001":
                    String actualRulesDescription = getDriver().findElement(By.xpath("//td[text()='" + item + "']/following::td[1]")).getText().trim();
                    String expectedRulesDescription = ApplicationConstants.RulesDescriptionATI0001;
                    Assert.assertEquals(actualRulesDescription, expectedRulesDescription);
                    break;
                case "ID00001":
                    actualRulesDescription = getDriver().findElement(By.xpath("//td[text()='" + item + "']/following::td[1]")).getText().trim();
                    expectedRulesDescription = ApplicationConstants.RulesDescriptionID00001;
                    Assert.assertEquals(actualRulesDescription, expectedRulesDescription);
                    break;
                case "FUZY001":
                    actualRulesDescription = getDriver().findElement(By.xpath("//td[text()='" + item + "']/following::td[1]")).getText().trim();
                    expectedRulesDescription = ApplicationConstants.RulesDescriptionFuzy001;
                    Assert.assertEquals(actualRulesDescription, expectedRulesDescription);
                    break;
                case "AP00009":
                    actualRulesDescription = getDriver().findElement(By.xpath("//td[text()='" + item + "']/following::td[1]")).getText().trim();
                    expectedRulesDescription = ApplicationConstants.RulesDescriptionAP00009;
                    Assert.assertEquals(actualRulesDescription, expectedRulesDescription);
                    break;
                case "DI00002":
                    actualRulesDescription = getDriver().findElement(By.xpath("//td[text()='" + item + "']/following::td[1]")).getText().trim();
                    expectedRulesDescription = ApplicationConstants.RulesDescriptionDI00002;
                    Assert.assertEquals(actualRulesDescription, expectedRulesDescription);
                    break;
                case "IF00005":
                    actualRulesDescription = getDriver().findElement(By.xpath("//td[text()='" + item + "']/following::td[1]")).getText().trim();
                    expectedRulesDescription = ApplicationConstants.RulesDescriptionIF00005;
                    Assert.assertEquals(actualRulesDescription, expectedRulesDescription);
                    break;

                default:
                    throw new NoSuchElementException("\"" + item + "\" is not visible on page");
            }

        }
    }

    public void clickRulePerformanceTest() {
        clickOn(clickEllipsis);
        clickOn(clickRulePerformance);
        clickOn(selectCriteria);
        clickOn(clickOnAllApplications);
        waitABit(2000);
        clickOn(clickOnTest);
        waitABit(2000);
    }

    public void clickRuleExecuteTest() {
        clickOn(clickEllipsis);
        clickOn(clickRulePerformance);
        clickOn(selectCriteria);
        clickOn(clickOnAllApplications);
        waitABit(2000);
        clickOn(clickOnExecute);
        WebDriverWait wait = new WebDriverWait(getDriver(), 60);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//table[@id='rule-group-table']//tbody//tr/td[string-length()>0]")));
    }

    public  void verifyRulePerformanceTest(String rule)
    {
        String actualRuleCode= getDriver().findElement(org.openqa.selenium.By.xpath("//div[contains(text(),'Rule Code')]//following::span")).getText();
        Assert.assertEquals(actualRuleCode, rule);

    }

    public boolean verifyAvailableRules(String availableRule)
    {
        boolean isRuleExists = false;
        List<WebElementFacade> availableRulesCode= findAll(By.xpath("//div[contains(text(),'Available Rules')]//following-sibling::div//tbody/tr/td[2]"));
        for (WebElementFacade ruleCode: availableRulesCode)
        {
            if (availableRule.equals(ruleCode.getText()))
               isRuleExists=true;
        }
        return isRuleExists;
    }

    public void selectRuleAdminGroup(String ruleAdminGroup) {
        selectRuleAdminGroup.typeAndEnter(ruleAdminGroup);
    }
}