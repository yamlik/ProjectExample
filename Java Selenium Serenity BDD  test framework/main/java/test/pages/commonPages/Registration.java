package com..test.pages.commonPages;

import com..test.pages.BasePage;
import net.serenitybdd.core.annotations.findby.FindBy;
import net.serenitybdd.core.pages.WebElementFacade;
import org.junit.Assert;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.IOException;

public class Registration extends BasePage {


    @FindBy(className = "alp-bonito-button-dark")
    private WebElementFacade registrationButton;

    @FindBy(className = "alp-bonito-button-dark")
    private  WebElementFacade save;

    @FindBy(id="licenceKey")
    private  WebElementFacade lisenceKeyField;

    @FindBy(xpath ="//button[contains(text(),'Yes')]")
    private  WebElementFacade yesButton;

    @FindBy(xpath = "//button[@aria-label='Close']")
    private WebElementFacade closeButton;

    @FindBy(xpath = "//h2[contains(text(),'License Key will expire soon')]")
    private WebElementFacade registrationTitle;

    Dashboard dashboardPage;

    public void clickproductUnderTestRegistration (){
     if (registrationButton.isClickable())
        {
        registrationButton.click();
        new WebDriverWait(getDriver(), 60).until(ExpectedConditions.elementToBeClickable(save));
        }
    }
    public void enterRegistrationKey(String key) {
        typeInto(lisenceKeyField,key);
        }

    public void clickSave() {
        save.click();
    }

    public  void yesButton() throws InterruptedException {
        new WebDriverWait(getDriver(), 60).until(ExpectedConditions.elementToBeClickable(yesButton));
        if (yesButton.isClickable()) {
            yesButton.click();
            Thread.sleep(3000);
        }
    }

    public void clickClosePopUpWindow() throws InterruptedException {
        try
        {
            waitFor(registrationTitle);
            if(registrationTitle.isVisible()) {
                closeButton.click();
            }
        }
        catch(NoSuchElementException ex)
        {
            Assert.assertTrue("Dashboard not displayed",dashboardPage.isDashboardDisplayed());
        }
        catch(TimeoutException ex) {}

    }
}
