package com..test.utilities;

import net.serenitybdd.core.pages.WebElementFacade;
import org.junit.Assert;
import org.openqa.selenium.*;

public class JsHelper {

    // Returns the value of an element's property
    public String getPropertyById(WebDriver driver, String elementID, String property){
        JavascriptExecutor js = (JavascriptExecutor) driver;

        try {
            return (js.executeScript("return document.getElementById('" + elementID + "')."+property+"").toString());
        }
        catch (JavascriptException ex){
            Assert.fail("Web Element not found");
            return "";
        }
    }

    public String getPropertyThatContainsId(WebDriver driver, String elementID, String property){
        JavascriptExecutor js = (JavascriptExecutor) driver;

        try {
            return (js.executeScript("return document.querySelector('[id^=\""+elementID+"\"]')."+property+"").toString());
        }
        catch (JavascriptException ex){
            Assert.fail("Web Element not found");
            return "";
        }
    }

    public String getPropertyByElement(WebDriver driver, WebElementFacade element, String property){
        JavascriptExecutor js = (JavascriptExecutor) driver;

        try {
            return (js.executeScript("arguments[0]."+property, element).toString());
        }
        catch (JavascriptException ex){
            Assert.fail("Web Element not found");
            return "";
        }
    }

    public void scrollToTheBottom(WebDriver driver) throws InterruptedException {
        ((JavascriptExecutor) driver).executeScript("window.scrollBy(0,document.body.scrollHeight)");
        Thread.sleep(1000);
    }

    public void scrollToTheTop(WebDriver driver) throws InterruptedException {
        ((JavascriptExecutor) driver).executeScript("window.scrollTo(0,0)");
        Thread.sleep(500);
    }

    public void setSessionStorage(WebDriver driver, String item, String value) {
        ((JavascriptExecutor)driver).executeScript(String.format(
                "sessionStorage.setItem('%s','%s');", item, value));
    }
}
