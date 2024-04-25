package com..test.pages.definitions;

import com..test.pages.BasePage;
import net.serenitybdd.core.annotations.findby.By;
import net.serenitybdd.core.annotations.findby.FindBy;
import net.serenitybdd.core.pages.WebElementFacade;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class OutputAlertPage extends BasePage {
    @FindBy(xpath = "//*[normalize-space()='New Output Alert']/following::input[3]")
    private WebElementFacade InputEmailAddress;

    @FindBy(xpath = "//*[normalize-space()='New Output Alert']/following::input[4]")
    private WebElementFacade InputSubject;

    @FindBy(xpath = "//*[normalize-space()='New Output Alert']/following::input[1]")
    private WebElementFacade InputCriteria;

    @FindBy(xpath = "//*[normalize-space()='New Output Alert']/following::input[2]")
    private WebElementFacade InputAvailableFields;

    @FindBy(xpath = "//*[normalize-space()='New Output Alert']/following::button[2]")
    private WebElementFacade AddCriteriaButton;

    @FindBy(xpath = "//*[normalize-space()='New Output Alert']/following::button[3]")
    private WebElementFacade AddNewButton;

    @FindBy(xpath = "//*[normalize-space()='All Applications']")
    private WebElementFacade VerifyTestClassing;

    @FindBy(xpath = "//*[normalize-space()='Yes']")
    private WebElementFacade PopUp;

    @FindBy(xpath="//div[contains(@class,'arrow_box_top')]/ul/li[3] | (//li[text()='Delete'])[last()]")
    private WebElementFacade delete;


    public void enterEmailAddress(String name) {
        waitFor(InputEmailAddress).waitUntilClickable();
        clickOn(InputEmailAddress);
        InputEmailAddress.typeAndEnter(name);
    }
    public void enterSubject(String name) {
        clickOn(InputSubject);
        InputSubject.typeAndEnter(name);
    }

    public void enterCriteria(String name) {
        clickOn(InputCriteria);
        InputCriteria.typeAndEnter(name);
    }

    public void enterAvailableFields(String name) {
        clickOn(InputAvailableFields);
        InputAvailableFields.typeAndEnter(name);
    }

    public void saveAlert() {
        clickOn(AddNewButton);
    }

    public void addCriteria() {
        clickOn(AddCriteriaButton);
    }

    public void verifySaving(String data) throws InterruptedException {
        waitABit(1000);
        waitFor(ExpectedConditions.visibilityOf(VerifyTestClassing));
        if (getDriver().findElement(By.xpath("//td[contains(text(),\""+ data +"\")]")).isDisplayed()) {
            System.out.println("Element is Visible");
        } else {
            System.out.println("Element is not present");
        }
    }

    public void deleteData(String data) {
        getDriver().findElement(By.xpath("//td[text()='" + data + "']//following::img")).click();
        waitABit(1000);
        clickOn(delete);
        clickOn(PopUp);
    }


}
