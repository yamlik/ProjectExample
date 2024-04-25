package com..test.pages.application;

import com..test.pages.BasePage;
import net.serenitybdd.core.annotations.findby.FindBy;
import net.serenitybdd.core.pages.WebElementFacade;
import org.openqa.selenium.Keys;

public class CrossCheckFieldsPage extends BasePage {

    @FindBy(xpath = "//tbody/tr[last()]//div[text()='Field Name']/following-sibling::div/input")
    private WebElementFacade fieldNameInput;

    @FindBy(xpath = "//tbody/tr[last()]//div[text()='Cross Field Name']/following-sibling::div/input")
    private WebElementFacade crossFieldNameInput;

    @FindBy(xpath = "//button[contains(text(),'Save')]")
    private WebElementFacade saveButton;

    @FindBy(xpath = "//div[@class='alp-notification-sticky-button suTSNTss alert-dismissable']")
    private WebElementFacade suTSNTssAlert;

    @FindBy(xpath = "//label[@appresourcetitle='FieldsConfiguration.Res_SelectAll']")
    private WebElementFacade selectAllCheckbox;

    @FindBy(xpath = "//label[@appresource='CommonButtons.Res_Delete']")
    private WebElementFacade deleteButton;

    @FindBy(xpath = "//span[@class='badge-count']")
    private WebElementFacade totalRecordCount;


    public void enterFieldName(String fieldName)
    {
        typeInto(fieldNameInput, fieldName);
        fieldNameInput.sendKeys(Keys.ENTER);
    }

    public void enterCrossFieldName(String crossFieldName)
    {
        typeInto(crossFieldNameInput, crossFieldName);
        crossFieldNameInput.sendKeys(Keys.ENTER);
    }

    public void clickSave()
    {
        clickOn(saveButton);
        waitABit(3000);
    }

    public void clickDeleteButton() {
        System.out.println("hhhhhhhhhhhh"+totalRecordCount.getText());
        if (!totalRecordCount.getText().equals("0")) {
            waitFor(deleteButton).waitUntilClickable();
            clickOn(deleteButton);
        }
    }

    public void clickSelectAllCheckbox() { clickOn(selectAllCheckbox); }

}
