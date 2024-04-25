package com..test.pages.commonPages;

import com..test.pages.BasePage;
import net.serenitybdd.core.annotations.findby.FindBy;
import net.serenitybdd.core.pages.WebElementFacade;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;


public class DashboardPage extends BasePage {


    @FindBy(xpath = "//h2[contains(text(),'Dashboard')]")
    private WebElementFacade dashboardPage;
    @FindBy(xpath = "//i[contains(@title,'Dashboard Settings')]")
    private WebElementFacade dashboardSettings;
    @FindBy(xpath = "//div[text()='Widgets']/following::input")
    private WebElementFacade dropdown;
    @FindBy(xpath = "//button[text()='Add ']")
    private WebElementFacade addButton;
    @FindBy(xpath = "//button[text()='Save']")
    private WebElementFacade saveButton;
    @FindBy(xpath = "//label[@title='Select All']/following::input[1]")
    private WebElementFacade userId1;

    @FindBy(xpath = "//label[@title='Select All']/following::input[2]")
    private WebElementFacade criteria1;

    @FindBy(xpath = "//button[text()='Save']")
    private WebElementFacade saveButton1;

    @FindBy(xpath = "//ng-select//input")
    private WebElementFacade widgetDropdown;

    public void addMyQueueWidget(String name) {
        clickOn(dashboardSettings);
        clickOn(widgetDropdown);
        widgetDropdown.typeAndEnter(name);
        clickOn(addButton);
        waitABit(1000);
        WebElement element = getDriver().findElement(By.xpath("//*[@id=\"gridster-item-0\"]/div[6]"));
        Actions action = new Actions(getDriver());
        action.dragAndDropBy(element, 900, 300).perform();
        clickOn(saveButton);
    }
    public void addCriteria(String userId, String criteria) {
        clickOn(userId1);
        userId1.typeAndEnter(userId);
        clickOn(criteria1);
        criteria1.typeAndEnter(criteria);
        waitABit(500);
        clickOn(saveButton1);

    }

    public void clickBreadcrumb(String breadcrumb)
    {
        clickOn(find("//app-breadcrumb//li[last()]/preceding-sibling::li[normalize-space()='"+breadcrumb+"']"));
    }
}

