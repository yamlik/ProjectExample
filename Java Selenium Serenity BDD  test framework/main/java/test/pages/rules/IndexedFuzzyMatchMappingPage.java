package com..test.pages.rules;
import com..test.pages.BasePage;
import net.serenitybdd.core.annotations.findby.By;
import net.serenitybdd.core.annotations.findby.FindBy;
import net.serenitybdd.core.pages.WebElementFacade;
import net.thucydides.core.pages.PageObject;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class IndexedFuzzyMatchMappingPage extends BasePage {
    private PageObject page;
    public WebDriver Driver;

    @FindBy(xpath = "//button[contains(text(),'Add')]")
    private WebElementFacade addButton;

    @FindBy(xpath = "//input[@id='GroupName']")
    private WebElementFacade GroupName;

    @FindBy(xpath = "//label[@for='selectAllAlgorithms']")
    private WebElementFacade selectAllLabel;

    @FindBy(xpath = "//label[@for='selectAllFields']")
    private WebElementFacade selectAllField;

    @FindBy(xpath = "(//button[contains(@class,'alp-bonito-button-default')])[3]")
    private WebElementFacade filterSelectFieldButton;

    @FindBy(xpath = "(//input[contains(@class,'alp-bonito-input-textbox')])[2]")
    private WebElementFacade fieldInput;

    @FindBy(xpath = "//button[contains(text(),'Apply')]")
    private WebElementFacade applyButton;

    @FindBy(xpath = "//span[contains(text(),'Add')]")
    private WebElementFacade saveIndexed;

    @FindBy(xpath = "//h2[text()='Indexed Fuzzy Match Mapping']")
    private WebElementFacade indexedHeader;


    public void clickAdd() {
        waitFor(ExpectedConditions.visibilityOf(indexedHeader));
        clickOn(addButton);
    }


    public void EnterGroupName(String element) {
        typeInto(GroupName, element);
    }

    public void clickSelectAll() {
        clickOn(selectAllLabel);
    }


    public void selectField(String field) {
        clickOn(filterSelectFieldButton);
        typeInto(fieldInput, field);
        clickOn(applyButton);
        clickOn(selectAllField);
    }


    public void saveMapping() {
        waitFor(ExpectedConditions.visibilityOf(indexedHeader));
        clickOn(saveIndexed);
    }

    public void verifyMappingName(String fieldName) {
        waitABit(1000);
        assert(getDriver().findElement(By.xpath("//td[normalize-space(text())='" + fieldName + "']")).isDisplayed());
    }
}