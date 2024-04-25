package com..test.pages.definitions;

import com..test.pages.BasePage;
import com..test.utilities.JSONHelper;
import net.serenitybdd.core.annotations.findby.By;
import net.serenitybdd.core.annotations.findby.FindBy;
import net.serenitybdd.core.pages.WebElementFacade;
import org.json.JSONObject;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.io.IOException;

public class ClassingMaintenancePage extends BasePage {
    @FindBy(xpath = "//button[text()='Add']")
    private WebElementFacade AddButton;
    @FindBy(xpath = "//input[@class='classingIdTextBox alp-bonito-input-textbox ng-pristine ng-invalid error ng-touched']")
    private WebElementFacade ClassingId;
    @FindBy(xpath = "//button[text()='Add Attribute']")
    private WebElementFacade AddAttributeButton;
    @FindBy(xpath = "//*[text()=' Yes ']")
    public WebElementFacade PopUp;
    @FindBy(xpath = "//div[text()='Classing Id']/following-sibling::input")
    WebElementFacade classing;
    @FindBy(xpath = "//td[text()='Attribute2']")
    WebElementFacade verifyAttribute;
    @FindBy(xpath = "//button[@class='alp-bonito-button-dark']")
    private WebElementFacade AddNewAttributeDefinition;
    @FindBy(xpath = "//button[text()='Save']")
    WebElementFacade SaveButton;
    @FindBy(xpath = "//div[@class='col-sm-12']/following::input[@placeholder='Description']")
    private WebElementFacade AddDesc;
    //@FindBy(xpath = "//div[@class='col-sm-12']/following::input[@class='alp-bonito-input-textbox ng-pristine ng-invalid error ng-touched']")
    @FindBy(xpath = "//div[@class='col-sm-12']/following::input[@placeholder='Values/Ranges']")
    private WebElementFacade AddValues;
    @FindBy(xpath = "//button[text()='Change']")
    private WebElementFacade changeButton;
    @FindBy(xpath = "//div[@class='col-sm-12']/following::input[@placeholder='Points']")
    private WebElementFacade AddPoints;
    @FindBy(xpath = "(//td[text()='Attribute2']/following::div[@class='ellipse-img-wrapper dropdown-toggle']//img/following::div/ul/li[5])[last()]")
    private WebElementFacade ShiftDown;
    @FindBy(xpath = "//td[text()='Testing Only']")
    private WebElementFacade verifySave;
    @FindBy(xpath = "//td[text()='Testing Only']/following::div[2]/img")
    private WebElementFacade ellipsisMenu;
    @FindBy(xpath = "(//td[text()='Testing Only']/following::td)[1]/following::ul/li[1]")
    private WebElementFacade changeMenu;
    @FindBy(xpath = "(//td[text()='Others']/following::td/following::ul/li[2])[last()]")
    private WebElementFacade deleteOthersAttribute;
    @FindBy(xpath = "(//td[text()='Attribute2']/following::td/following::ul/li[1])[last()]")
    private WebElementFacade Attribute2;

    public void clickAddButton() {
        clickOn(AddButton);
        clickOn(AddAttributeButton);
    }

    public void testData() throws IOException {
        JSONObject requestBody = JSONHelper.messageAsActualJson(dataDir + "Definitions/ClassingMaintenance.json");
        String data = requestBody.getString("Description");
        String dat1 = requestBody.getString("TestData");
        String data2 = requestBody.getString("points");
        setAddNewAttributeDefinition(data, dat1, data2);
    }

    public void setAddNewAttributeDefinition(String description, String values, String points) {
        waitABit(500);
        clickOn(AddDesc);
        typeInto(AddDesc, description);
        clickOn(AddValues);
        typeInto(AddValues, values);
        clickOn(AddPoints);
        typeInto(AddPoints, String.valueOf(points));
        clickOn(AddNewAttributeDefinition);
    }

    public void addClassingId(String classingId) {
        clickOn(classing);
        typeInto(classing, classingId);
    }

    public void saveClassingDefinition() {
        clickOn(SaveButton);
    }

    public void verifyClassingId() {
        waitABit(500);
        waitFor(ExpectedConditions.visibilityOf(verifySave));
        if (getDriver().findElement(By.xpath("//td[text()='Testing Only']")).isDisplayed()) {
            System.out.println("Element is visible");
        } else {
            System.out.println("Element is not visible");
        }
    }

    public void changeClassingId() {
        clickOn(ellipsisMenu);
        waitABit(500);
        clickOn(changeMenu);
        waitABit(500);
        clickOn(AddAttributeButton);
    }

    public void setShiftDown(String points) {
        waitABit(500);
        getDriver().findElement(By.xpath("//td[text()='Others']/following::div[2]/img")).click();
        clickOn(deleteOthersAttribute);
        getDriver().findElement(By.xpath("//td[text()='Attribute2']/following::div[2]/img")).click();
        waitABit(1000);
        clickOn(Attribute2);
        clickOn(AddPoints);
        typeInto(AddPoints, points);
        clickOn(changeButton);
        waitABit(1000);
        clickOn(SaveButton);
    }

    public void verifyTheModifiedAttribute() {
        waitABit(1000);
        waitFor(ExpectedConditions.visibilityOf(verifyAttribute));
        if (getDriver().findElement(By.xpath("//td[text()='Attribute2']")).isDisplayed()) {
            System.out.println("Element is visible");
        } else {
            System.out.println("Element is not visible");
        }
        clickOn(SaveButton);
    }
    public void setDeleteClassingId(String data) {
        waitABit(1000);
        getDriver().findElement(By.xpath("//td[text()='" + data + "']/following::div[contains(@class,'ellipse-img-wrapper')]//img")).click();
        waitABit(1000);
        getDriver().findElement(By.xpath("//td[text()='" + data + "']/following::div[contains(@class,'ellipse-img-wrapper')]//img/following::div/ul/li[2]")).click();
        clickOn(PopUp);
    }
}
