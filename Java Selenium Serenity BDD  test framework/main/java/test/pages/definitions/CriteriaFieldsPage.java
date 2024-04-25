package com..test.pages.definitions;

import com..test.pages.BasePage;
import net.serenitybdd.core.annotations.findby.By;
import net.serenitybdd.core.annotations.findby.FindBy;
import net.serenitybdd.core.pages.WebElementFacade;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class CriteriaFieldsPage extends BasePage {
    @FindBy(xpath = "//a[text()='Definitions']")
    private WebElementFacade DefinitionsMenu;
    @FindBy(xpath = "//button[text()='Add']")
    private WebElementFacade AddButton;
    @FindBy(xpath = "//div[text()='Criteria 1']/following::input[1]")
    private WebElementFacade Criteria1;
    @FindBy(xpath = "//div[text()='Criteria 2']/following::input[1]")
    private WebElementFacade Criteria2;
    @FindBy(xpath = "//div[text()='Criteria 3']/following::input[1]")
    private WebElementFacade Criteria3;
    @FindBy(xpath = "//div[text()='Available Fields']/following::input[1]")
    private WebElementFacade AvailableFields;
    @FindBy(xpath = "//div[text()='Order Id']/following::input[1]")
    private WebElementFacade OrderId;
    @FindBy(xpath = "//div[text()='Value']/following::input[1]")
    private WebElementFacade Value;
    @FindBy(xpath = "//div[@class='button-box']/button")
    private WebElementFacade AddCriteriaField;
    @FindBy(xpath = "//td[text()='Application.Amount/Limit']")
    private WebElementFacade AddedCriteriaField;
    @FindBy(xpath="//div[contains(@class,'arrow_box_top')]/ul/li[3] | (//li[text()='Delete'])[last()]")
    private WebElementFacade delete;
    @FindBy(xpath = "//*[text()=' Yes ']")
    private WebElementFacade PopUp;

    public void addCriteriaFields(String Criteria) {
        clickOn(AddButton);
        clickOn(Criteria1);
        Criteria1.typeAndEnter(Criteria);

        clickOn(Criteria2);
        Criteria2.typeAndEnter(Criteria);

        clickOn(Criteria3);
        Criteria3.typeAndEnter(Criteria);
    }

    public void addOtherFields(String availableFields, String orderId, String value) {
        clickOn(AvailableFields);
        AvailableFields.typeAndEnter(availableFields);
        OrderId.typeAndEnter(orderId);
        clickOn(Value);
        Value.typeAndEnter(value);
        clickOn(AddCriteriaField);
    }

    public void verifyAddedCriteriaFields() {
        waitABit(1000);
        waitFor(ExpectedConditions.visibilityOf(AddedCriteriaField));
        if (getDriver().findElement(By.xpath("//td[contains(text(),\"Application.Amount/Limit\")]")).isDisplayed()) {
            System.out.println("Element is Visible");
        } else {
            System.out.println("Element is not present");
        }
    }

    public void deleteData(String data) {
        getDriver().findElement(By.xpath("//td[text()='" + data + "']/following::img")).click();
        waitABit(1000);
        clickOn(delete);
        clickOn(PopUp);
    }

}
