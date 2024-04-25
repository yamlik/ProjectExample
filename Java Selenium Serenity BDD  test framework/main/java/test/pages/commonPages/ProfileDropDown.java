package com..test.pages.commonPages;

import com..test.pages.BasePage;
import net.serenitybdd.core.annotations.findby.FindBy;
import net.serenitybdd.core.pages.WebElementFacade;

public class ProfileDropDown extends BasePage {

    @FindBy(xpath = "//a[@class='titlemenu']")
    private WebElementFacade profileDropDownButton;

    @FindBy(xpath = "//a[contains(text(),'Logout')]")
    private WebElementFacade logoutButton;


    public void clickProfileDropDownButton()
    {
        profileDropDownButton.click();
    }

    public void clickLogoutButton()
    {
        logoutButton.click();
    }



}
