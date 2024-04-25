package com..test.pages.rules;

import com..test.pages.BasePage;
import net.serenitybdd.core.annotations.findby.FindBy;
import net.serenitybdd.core.pages.WebElementFacade;

public class RuleGroupsPage extends BasePage {

    @FindBy(xpath = "//button[contains(text(),'Add')]")
    private WebElementFacade addButton;

    @FindBy(id = "RuleGroupCode")
    private WebElementFacade ruleGroupTextBox;

    @FindBy(id = "ruleGroupDescription")
    private WebElementFacade descriptionTextBox;

    @FindBy(xpath = "//button[@id='action-button']/span")
    private WebElementFacade addButtonInPopup;

    public void clickAdd() { clickOn(addButton); }

    public void clickAddInPopup() {
        clickOn(addButtonInPopup);
        waitFor(messageBanner).waitUntilNotVisible();
    }

    public void enterRuleGroupCode(String code) { typeInto(ruleGroupTextBox,code); }

    public void enterDescription(String description) { typeInto(descriptionTextBox, description); }

}
