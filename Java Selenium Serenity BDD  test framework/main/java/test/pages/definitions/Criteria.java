package com..test.pages.definitions;

import com..test.context.ScenarioContext;
import com..test.pages.BasePage;
import com..test.utilities.JsHelper;
import net.serenitybdd.core.annotations.findby.By;
import net.serenitybdd.core.annotations.findby.FindBy;
import net.serenitybdd.core.pages.WebElementFacade;
import org.json.simple.JSONObject;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

public class Criteria extends BasePage {

    @FindBy(xpath = "//div[text()='Criteria']/following::div/input")
    private WebElementFacade enterCriteria;

    @FindBy(xpath = "(//div[text()='Category']/following::div/ng-select[1]/div/span)[1]")
    private WebElementFacade criteriaCategoryDropdown;

    @FindBy(xpath = "(//div[text()='Field']/following::div/ng-select[1]/div/span)[1]")
    private WebElementFacade fieldDropdown;

    @FindBy(xpath = "(//div[text()='Operator']/following::div/ng-select[1]/div/span)[1]")
    private WebElementFacade operatorDropdown;

    @FindBy(xpath = "//div[text()='Value']/following::textarea")
    private WebElementFacade valueTextarea;

    @FindBy(xpath = "//button[contains(text(),'Add')]")
    private WebElementFacade addButton;

    @FindBy(xpath = "//button[text()='Save'] | //button[text()='Submit']| //button[text()='Update']")
    private WebElementFacade saveButton;

    @FindBy(xpath = "//button[contains(text(),'Yes')]")
    private WebElementFacade yesButton;
    @FindBy(xpath = "//span[text()='Definitions']/..")
    private WebElementFacade sidebarDefinitions;
    @FindBy(css = ".CustomTable")
    private WebElementFacade tableRoot;
    @FindBy(xpath = "(//img[@class='loadmore'])[last()]")
    private WebElementFacade selectCriteria;

    @FindBy(xpath = "(//li[text()='Delete'])[last()]")
    private WebElementFacade deleteCriteria;

    @FindBy(xpath = "(//div[text()='Criteria']/following::div/ng-select[1]/div/span)[2]")
    private WebElementFacade criteriaDropdown;

    @FindBy(xpath = "//button[contains(text(),'Search')]")
    private WebElementFacade searchButton;

    public void addDetailsOnCriteriaDefinitionPage() {
        try {
            JSONObject data = ScenarioContext.getTSNTance().getDataDriveSectionAsJSON("ApplicationData");
            String criteria = data.get("Criteria").toString();
            typeInto(enterCriteria, criteria);
            waitABit(500);
            scrollToElement(addButton);
            clickOn(criteriaCategoryDropdown);
            WebElement ele = getDriver().findElement(By.xpath("//span[contains(text(),'Application')]"));
            ele.click();
            waitABit(500);
            clickOn(fieldDropdown);
            WebElement ele1 = getDriver().findElement(By.xpath("//div[contains(text(),'Application Type')]"));
            ele1.click();
            waitABit(500);
            clickOn(operatorDropdown);
            WebElement ele2 = getDriver().findElement(By.xpath("//span[text()='=']"));
            ele2.click();
            waitABit(500);
            scrollToElement(addButton);
            JSONObject data1 = ScenarioContext.getTSNTance().getDataDriveSectionAsJSON("ApplicationData");
            String value = data1.get("Value").toString();
            typeInto(valueTextarea, value);
            waitABit(500);
            clickOn(addButton);
            waitABit(500);
            clickOn(saveButton);
            waitABit(500);
            clickOn(yesButton);
            waitABit(500);

        } catch (Exception e) {
            e.getMessage();
        }
    }
    JsHelper jsHelper = new JsHelper();

    public void addDetailsOnCriteriaDefinitionPageWithData(String criteria) {
        try {
            JSONObject data = ScenarioContext.getTSNTance().getDataDriveSectionAsJSON("Criteria");
            String category = data.get("Category").toString();
            String field = data.get("Field").toString();
            String value = data.get("Value").toString();
            typeInto(enterCriteria, criteria);
            clickOn(criteriaCategoryDropdown);
            jsHelper.scrollToTheBottom(getDriver());
            WebElement ele = getDriver().findElement(By.xpath("//span[contains(text(),'"+category+"')]"));
            ele.click();
            waitABit(500);
            clickOn(fieldDropdown);
            WebElement ele1 = getDriver().findElement(By.xpath("//div[contains(text(),'"+field+"')]"));
            ele1.click();
            waitABit(500);
            clickOn(operatorDropdown);
            WebElement ele2 = getDriver().findElement(By.xpath("//span[text()='"+value+"']"));
            ele2.click();
            clickOn(addButton);
            clickOn(saveButton);
            clickOn(yesButton);

        } catch (Exception e) {
            e.getMessage();
        }
    }

    public void deleteCreatedCriteria() {
        try {
            waitABit(3000);
            waitFor(ExpectedConditions.visibilityOf(sidebarDefinitions));
            clickOn(sidebarDefinitions);
            waitABit(2000);
            clickOn(getDriver().findElement(By.xpath("//a[text()='Criteria']")));
            waitABit(2000);
            List<WebElement> applicationCatLabel = tableRoot.findElements(By.xpath("//table[@class='table CustomTable']/thead/following::tr"));
            int criteriaSize = applicationCatLabel.size();
            if (criteriaSize > 1) {
                waitFor(ExpectedConditions.visibilityOf(selectCriteria));
                waitABit(1000);
                clickOn(selectCriteria);
                waitFor(ExpectedConditions.visibilityOf(deleteCriteria));
                waitABit(1000);
                clickOn(deleteCriteria);
                waitFor(ExpectedConditions.visibilityOf(yesButton));
                waitABit(1000);
                clickOn(yesButton);
                waitABit(1000);
            }
        } catch (Exception e) {
            e.getMessage();
        }
    }

    public void selectCriteriaAndSearch() {
        try {
            waitABit(1500);
            clickOn(criteriaDropdown);
            JSONObject value = ScenarioContext.getTSNTance().getDataDriveSectionAsJSON("ApplicationData");
            String Criteria = value.get("Criteria").toString();
            waitABit(500);
            WebElement ele = getDriver().findElement(By.xpath("//div[contains(text(),'" + Criteria + "')]"));
            ele.click();
            waitABit(500);
            clickOn(searchButton);
            waitABit(1500);
        } catch (Exception e) {
            e.getMessage();
        }
    }
}
