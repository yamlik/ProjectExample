package com..test.pages.security;

import com..test.context.constants.ApplicationConstants;
import com..test.pages.BasePage;
import net.serenitybdd.core.annotations.findby.FindBy;
import net.serenitybdd.core.pages.WebElementFacade;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.Color;


public class SecurityParameters extends BasePage {

    @FindBy(xpath = "//button[contains(text(),'Save')]")
    private WebElementFacade saveButton;

    @FindBy(xpath = "//button[normalize-space()='Collapse All']")
    private WebElementFacade collapseAllButton;

    @FindBy(xpath = "//div[contains(text(),'Client Parameters')] | //h2[contains(text(),'Security Parameters')]")
    private WebElementFacade pageTitle;

    @FindBy(xpath = "//div[@class='alp-notification-sticky-button suTSNTss alert-dismissable']")
    private WebElementFacade suTSNTssAlert;

    public void toggleParameterSecurity(String toggle, String parameter) throws InterruptedException {
        waitFor(collapseAllButton).waitUntilClickable();
        scrollToElement(saveButton);
        WebElement parameterElement = getDriver().findElement(By.xpath("//div[contains(text(),'" + parameter + "')]/../../div/span/label/span"));
        if (((Color.fromString(parameterElement.getCssValue("background-color")).asHex().equals(ApplicationConstants.toggleOnColour)) && toggle.equals("OFF")) ||
                ((Color.fromString(parameterElement.getCssValue("background-color")).asHex().equals(ApplicationConstants.toggleOffColour)) && toggle.equals("ON")))
        {
            ((JavascriptExecutor) getDriver()).executeScript("arguments[0].scrollIntoView(true);", parameterElement);waitABit(2000);
            ((JavascriptExecutor) getDriver()).executeScript("javascript:window.scrollBy(0,-100)");waitABit(2000);
            parameterElement.click();
        }
    }

    public void clickSave()
    {
        clickOn(saveButton);
        waitABit(2000);
    }

}
