package com..test.pages.settings;

import com..test.pages.BasePage;
import net.serenitybdd.core.annotations.findby.FindBy;
import net.serenitybdd.core.pages.WebElementFacade;
import com..test.pages.commonPages.Navigation;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.Color;
import org.openqa.selenium.support.ui.ExpectedConditions;


public class FraudAlertsPage extends BasePage {

    @FindBy(xpath = "//div[contains(@class,'alert-dismissable')]")
    public WebElementFacade messageBanner;

    @FindBy(xpath = "//li[@class='direct-menu-item']//a[text()='Fraud Alerts']")
    private WebElementFacade pageTittle;

    @FindBy(xpath = "//input[@id='fraudAlerts-clean-to']")
    private WebElementFacade cleanTextBox;

    @FindBy(xpath = "//input[@id='fraudAlerts-suspect-from']")
    private WebElementFacade suspectFromTextBox;

    @FindBy(xpath = "//input[@id='fraudAlerts-suspect-to']")
    private WebElementFacade suspectToTextBox;

    @FindBy(xpath = "//input[@id='fraudAlerts-highFraudPotential-from']")
    private WebElementFacade highFraudPotentialTextBox;

    @FindBy(xpath = "//button[contains(text(),'Save')]")
    private WebElementFacade saveButton;



    public void enterFraudScoreForClean (String score){
        typeInto(cleanTextBox, score);

    }

    public void enterFraudScoreForSuspectFrom (String score){
        typeInto(suspectFromTextBox, score);

    }

    public void enterFraudScoreForSuspectTo (String score){
        typeInto(suspectToTextBox, score);

    }

    public void enterFraudScoreForHigh (String score){
        typeInto(highFraudPotentialTextBox, score);

    }

    public void clickSave()
    {
        clickOn(saveButton);
        waitFor(messageBanner).waitUntilVisible();
    }


}
