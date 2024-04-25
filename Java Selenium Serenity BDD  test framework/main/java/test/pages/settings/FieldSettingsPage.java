package com..test.pages.settings;

import com..test.pages.BasePage;
import net.serenitybdd.core.annotations.findby.FindBy;
import net.serenitybdd.core.pages.WebElementFacade;
import org.openqa.selenium.*;

public class FieldSettingsPage extends BasePage {

    @FindBy(xpath = "//span[@class='ng-arrow-wrapper']")
    private WebElementFacade configurationPurposeToggle;

    @FindBy(xpath = "//div[contains(text(),'Application Type Field Display')]")
    private WebElementFacade applicationTypeFieldDisplay;

    @FindBy(xpath = " //button[contains(text(),'Filter')]")
    private WebElementFacade filterFields;

    @FindBy(xpath = "//input[@class='alp-bonito-input-textbox ng-untouched ng-pristine ng-valid']")
    private WebElementFacade filterBox;

    @FindBy(xpath = "//button[contains(text(),'Apply')]")
    private WebElementFacade applyButton;

    @FindBy(xpath = "//div[contains(text(),'Applicant.Id Number 1')]")
    private WebElementFacade idNumber1;

    @FindBy(xpath = "//div[@class='button-col']//div//img")
    private WebElementFacade arrowRight;

    @FindBy(xpath = "//button[contains(text(),'Save')]")
    private WebElementFacade saveButton;

    @FindBy(xpath = "//ng-select//input")
    private WebElementFacade configurationPurposeDropDown;

    public void selectConfigurationPurpose() {
        clickOn(configurationPurposeToggle);
        waitFor(applicationTypeFieldDisplay).click();

    }

    public void addApplicantIdNumber1(String fieldName) {
        waitFor(filterFields).click();
        filterBox.typeAndEnter(fieldName);
        clickOn(idNumber1);
        clickOn(arrowRight);
        clickOn(saveButton);

    }

    public void enterConfigurationPurpose(String config){
        configurationPurposeDropDown.typeAndEnter(config);
    }

    public void selectAndMoveField(String field)
    {
        waitFor(filterFields).click();
        filterBox.typeAndEnter(field);
        WebElementFacade availableField = find("//div[contains(text(),'"+field+"')]");
        clickOn(availableField);
        if(arrowRight.isCurrentlyEnabled())
            clickOn(arrowRight);
        clickOn(saveButton);
    }
}
