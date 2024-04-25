package com..test.pages.commonPages;

import com.gargoylesoftware.htmlunit.ElementNotFoundException;
import com..test.pages.BasePage;
import net.serenitybdd.core.annotations.findby.FindBy;
import net.serenitybdd.core.pages.WebElementFacade;
import org.openqa.selenium.By;


import java.time.Duration;

import java.time.temporal.ChronoUnit;

public class WhatsNewPage extends BasePage {
    @FindBy(xpath = "//button[@class='close']")
    private WebElementFacade userViewClose;


    @FindBy(xpath = "//div[@class='popup']")
    public WebElementFacade popUp;


    public boolean verifyWhatsNewPageLoads(String message) {
        try
        {
            getDriver().findElement(By.xpath("//h1[contains(text(),'"+message+"')]"));
            return true;
        }
        catch (ElementNotFoundException e)
        {
            return false;
        }
    }

    public void makeAChoice(String choice) {
        if(popUp.isVisible()) {
            getDriver().findElement(By.xpath("//a[contains(text(),'" + choice + "')]")).click();
        }
    }
}
