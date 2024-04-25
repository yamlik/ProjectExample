package com..test.pages.settings;

import com..test.pages.BasePage;
import net.serenitybdd.core.annotations.findby.FindBy;
import net.serenitybdd.core.pages.WebElementFacade;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;


public class ActionDecisionPage extends BasePage {


    @FindBy(xpath = "//div[contains(text(),'Decision Reason')]")
    private WebElementFacade pageTitle;

    @FindBy(xpath = "//button[contains(text(),'Add')]")
    private WebElementFacade addButton;

    @FindBy(xpath = "//button[contains(text(),'Filter')]")
    private WebElementFacade filterButton;

    @FindBy(xpath = "//div[contains(text(),'Print')]")
    private WebElementFacade printUrl;

    @FindBy(xpath = "//button[@aria-label='Close']")
    private WebElementFacade closeButton;

    @FindBy(xpath = "//input[@type='checkbox')]")
    private WebElementFacade popUpCheckBox;

    @FindBy(xpath = "//button/span[contains(text(),'Add') and @class = 'ladda-label']")
    private WebElementFacade popUpAddButton;

    @FindBy(xpath = "//input[@id='txtReason']")
    private WebElementFacade decisionReasonTxtBox;

    public void clickAdd()
    {
        clickOn(addButton);
        waitABit(2000);
    }

    public void clickFilter()
    {
        clickOn(filterButton);
        waitABit(2000);
    }

    public void clickPrint()
    {
        clickOn(printUrl);
        waitABit(2000);
    }

    public void clickClose()
    {
        clickOn(closeButton);
        waitABit(2000);
    }

    public void inputToTextBox(String title)
    {
        typeInto(decisionReasonTxtBox, title);
    }

    public void clickPopUpAddButton()
    {
        clickOn(popUpAddButton);
    }

    public boolean verifyPresenceOfRule(String ruleName)
    {
        waitABit(1000);
        try {
            return getDriver().findElement(By.xpath("//td[normalize-space(text())='" + ruleName + "']")).isDisplayed();
        }catch (NoSuchElementException e){
            return false;
        }

    }

    public void clickPopUpCheckbox(){
        clickOn(popUpCheckBox);
    }

    public void inputToPopUpReasonTextbox(String ruleName)
    {
        WebElement elementHolder=getDriver().findElement(By.xpath(" //input[@id='txtReason']"));
        elementHolder.clear();
        typeInto(elementHolder, ruleName);
    }

    public void clickPopUpChangeButton() { clickOn(getDriver().findElement(By.xpath("//button/span[contains(text(),'Change') and @class = 'ladda-label'] "))); }

    public void clickPopUpCloseButton()
    {
        clickOn(getDriver().findElement(By.xpath("//button[@aria-label='Close']")));
    }

    public void clickPopUpYesButton() { clickOn(getDriver().findElement(By.xpath("//button[contains(text(),'Yes')]"))); }

    public void clickPopUpNoButton()
    {
        clickOn(getDriver().findElement(By.xpath("//button[contains(text(),'No')]")));
    }
}