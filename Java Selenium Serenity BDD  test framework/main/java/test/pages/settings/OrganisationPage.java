package com..test.pages.settings;

import com..test.pages.BasePage;
import net.serenitybdd.core.annotations.findby.FindBy;
import net.serenitybdd.core.pages.WebElementFacade;

public class OrganisationPage extends BasePage {
    @FindBy(xpath = "//button[text()='Add']")
    private WebElementFacade addButton;

    @FindBy(xpath = "//input[@id='organisationCode']")
    private WebElementFacade organisationTextBox;

     @FindBy(xpath = "//input[@id='OrganisationDescription']")
    private WebElementFacade descriptionTextBox;

    @FindBy(xpath = "//button[contains(text(),'Add') and @class = 'alp-bonito-button-dark']")
    private WebElementFacade popUpAddButton;

    public void clickAddButton()
    { clickOn(addButton); }

    public void enterOrganisationCode(String organisationCode)
    { typeInto(organisationTextBox, organisationCode);}

    public void enterOrganisationDescription(String organisationDescription)
    { typeInto(descriptionTextBox,organisationDescription); }

    public void clickPopUpAddButton()
    {
        clickOn(popUpAddButton);
        waitFor(messageBanner).waitUntilNotVisible();
    }
}
