package com..test.pages.definitions;

import com..test.pages.BasePage;
import net.serenitybdd.core.annotations.findby.By;
import net.serenitybdd.core.annotations.findby.FindBy;
import net.serenitybdd.core.pages.WebElementFacade;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class ClassingAssociationPage extends BasePage {
    @FindBy(xpath = "//a[text()='Definitions']")
    private WebElementFacade DefinitionsMenu;

    @FindBy(xpath = "//a[text()='Classing Associations']/following::a[1]")
    public WebElementFacade ClassingMaintenanceOption;

    @FindBy(xpath = "//button[text()='Add']")
    private WebElementFacade AddButton;

    @FindBy(xpath = "//*[text()=' New Classing Association ']/following::input[1]")
    private WebElementFacade InputName;

    @FindBy(xpath = "//*[text()=' New Classing Association ']/following::input[2]")
    private WebElementFacade InputId;

    @FindBy(xpath = "//*[text()='test classing']")
    private WebElementFacade VerifyTestClassing;

    @FindBy(xpath = "//*[text()=' New Classing Association ']/following::input[3]")
    private WebElementFacade InputCategory;

    @FindBy(xpath = "//*[text()=' New Classing Association ']/following::input[4]")
    private WebElementFacade InputField;

    @FindBy(xpath = "//*[text()=' New Classing Association ']/following::button[2]")
    private WebElementFacade AddNewClassingButton;

    @FindBy(xpath = "//td[text()='test classing']/following::li[3]")
    private WebElementFacade Delete;

    @FindBy(xpath = "//*[text()=' Yes ']")
    private WebElementFacade PopUp;

   @FindBy(xpath="//div[contains(@class,'arrow_box_top')]/ul/li[3] | (//li[text()='Delete'])[last()]")
    private WebElementFacade delete;


    public void clickClassingAssociations(String name) {
        waitABit(1000);
        clickOn(InputName);
        InputName.typeAndEnter(name);
    }

    public void enterCategory(String category) {
        waitABit(1000);
        clickOn(InputCategory);
        InputCategory.typeAndEnter(category);
    }

    public void enterField(String Field) {
        waitABit(500);
        clickOn(InputField);
        InputField.typeAndEnter(Field);
    }

    public void enterClassingId(String classingId) {

        clickOn(InputId);
        InputId.typeAndEnter(classingId);
    }

    public void saveClassingButton() throws InterruptedException {
        clickOn(AddNewClassingButton);
    }
    public void verifySaving() throws InterruptedException {
        waitABit(1000);
        waitFor(ExpectedConditions.visibilityOf(VerifyTestClassing));
        if (getDriver().findElement(By.xpath("//td[contains(text(),\"test classing\")]")).isDisplayed()) {
            System.out.println("Element is Visible");
        } else {
            System.out.println("Element is not present");
        }
    }
    public void deleteDatta(String data) {
        getDriver().findElement(By.xpath(" //td[text()='" + data + "']//following::img")).click();
        waitABit(1000);
        clickOn(delete);
        clickOn(PopUp);
    }
}

