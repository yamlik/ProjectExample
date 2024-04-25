package com..test.pages.settings;

import com..test.pages.BasePage;
import net.serenitybdd.core.annotations.findby.FindBy;
import net.serenitybdd.core.pages.WebElementFacade;


public class FieldRequirementsPage extends BasePage {
    @FindBy(xpath = "//a[text()='Field Requirements']")
    WebElementFacade subMenu;
    @FindBy(xpath = "//td[text()='Reference']/following::div[1]")
    WebElementFacade securityCategory;
    @FindBy(xpath = "//button[text()='Save']")
    WebElementFacade saveButton;

    public void selectCategory() {
        waitFor(securityCategory).waitUntilVisible();
        boolean isSelected = securityCategory.isSelected();
        if (!isSelected) {
            securityCategory.click();
        } else {
            clickOn(saveButton);
        }
        clickOn(saveButton);
        waitFor(messageBanner).waitUntilNotVisible();
    }

}


