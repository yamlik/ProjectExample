package com..test.pages.rules;

import com..test.pages.BasePage;
import net.serenitybdd.core.annotations.findby.FindBy;
import net.serenitybdd.core.pages.WebElementFacade;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.NoSuchElementException;

public class RuleAdminGroupPage extends BasePage {

    @FindBy(xpath = "//button[contains(@class,'actionButtons')]")
    private WebElementFacade addButton;

    @FindBy(xpath = "//input[@id='RuleAdminGroupcode']")
    private WebElementFacade ruleAdminGroupTextBox;

    @FindBy(xpath = "//input[@id='Description']")
    private WebElementFacade descriptionTextBox;

    @FindBy(xpath = "//button[@id='action-button']")
    private WebElementFacade addButtonInPopup;

    @FindBy(xpath = "//div[contains(@class,'alert-dismissable')]")
    private WebElementFacade suTSNTssAlert;

    @FindBy(xpath = "//img[@class='loadmore']")
    private List<WebElementFacade> ellipsisButtons;

    @FindBy(xpath = "//img[@class='loadmore']")
    private WebElementFacade ellipsisButton;

    @FindBy(xpath = "//body/div//li[normalize-space()='Delete']")
    private WebElementFacade deleteButton;

    @FindBy(xpath = "//ngb-modal-window//button[normalize-space()='Yes']")
    private WebElementFacade yesButton;

    public void clickAddButton() { clickOn(addButton); }

    public void enterRuleAdminGroup(String ruleAdminGrp) { typeInto(ruleAdminGroupTextBox, ruleAdminGrp); }

    public void clickAddButtonInPopup()
    {
        clickOn(addButtonInPopup);
        waitFor(suTSNTssAlert).waitUntilNotVisible();
    }

    public void enterDescription(String description) { typeInto(descriptionTextBox,description); }

    public void deleteAllRecordsOnGrid()
    {
        try
        {
            waitFor(ellipsisButton);
            int totalRows = ellipsisButtons.size();
            for (int i=0;i<totalRows;i++) {
                WebDriverWait wait = new WebDriverWait(getDriver(), 2);
                wait.until(ExpectedConditions.elementToBeClickable(ellipsisButton));
                clickOn(ellipsisButton);
                waitFor(deleteButton).waitUntilClickable();
                clickOn(deleteButton);
                waitFor(yesButton).waitUntilClickable();
                clickOn(yesButton);
                waitFor(messageBanner).waitUntilNotVisible();
            }
        }
        catch (Exception ex)
        {
            // do nothing

        }
    }

}
