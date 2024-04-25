package com..test.pages.commonPages;

import com..test.pages.BasePage;
import net.serenitybdd.core.annotations.findby.FindBy;
import net.serenitybdd.core.pages.WebElementFacade;
import org.openqa.selenium.By;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class RecentPage extends BasePage {

    @FindBy(xpath = "/html/body/app-root/app-home-layout/header/div/div[3]/img")
    private WebElementFacade recentLink;

    @FindBy(xpath ="//a[text()='Application']")
    private WebElementFacade recentApplicationsTab;


    public void clickRecentLink()
    {
        WebDriverWait wait = new WebDriverWait(getDriver(), 10);
        wait.until(ExpectedConditions.elementToBeClickable(recentLink));
        clickOn(recentLink);
    }

    public void clickRecentApplicationsTab()
    {
        clickOn(recentApplicationsTab);
    }

    public void doubleClickApplicationRecord(String applicationNumber) {
        Actions actions = new Actions(getDriver());
        actions.doubleClick(getDriver().findElement(By.xpath("//td[contains(text(),'"+applicationNumber.toUpperCase()+"')]"))).perform();
    }
}
