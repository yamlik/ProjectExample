package com..test.pages.commonPages;

import com..test.pages.BasePage;
import net.serenitybdd.core.annotations.findby.FindBy;
import net.serenitybdd.core.pages.WebElementFacade;

public class Dashboard  extends BasePage {

    @FindBy (xpath = "//h2[contains(text(),'Dashboard')]")
    private WebElementFacade dashboardPage;

    public boolean dashboardPageDisplayed() {
       return dashboardPage.isVisible();
    }

    public boolean isDashboardDisplayed() throws InterruptedException {
        Thread.sleep(3000);
        return dashboardPageDisplayed();
    }

}

