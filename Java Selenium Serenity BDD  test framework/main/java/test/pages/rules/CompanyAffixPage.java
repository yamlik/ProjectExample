package com..test.pages.rules;

import com..test.pages.BasePage;
import net.serenitybdd.core.annotations.findby.FindBy;
import net.serenitybdd.core.pages.WebElementFacade;
import org.junit.Assert;
import org.openqa.selenium.support.ui.ExpectedConditions;


public class CompanyAffixPage extends BasePage {


    @FindBy(xpath = "//button[contains(text(), 'Add')]")
    private WebElementFacade addButton;

    @FindBy(xpath = "//span[contains(text(), 'Add')]")
    private WebElementFacade addSuffix;

    @FindBy(xpath = "//input[@placeholder='Company Suffix']")
    private WebElementFacade companySuffixTextBox;

    @FindBy(xpath = "//div[contains(text(), 'Company Name')]//following::td//textarea")
    private WebElementFacade companyNameTextBox;

    @FindBy(xpath = "//span[contains(text(),'Category')]")
    private WebElementFacade categoryHeading;

    @FindBy(xpath = "//h4[contains(text(), 'Applicant')]")
    private WebElementFacade applicant;

    @FindBy(xpath = "//h2[contains(text(),'Application')] | //h2[contains(text(),'Watchlist')]")
    private WebElementFacade applicationHeader;

    @FindBy(xpath = "//h4[contains(text(), 'Introducer')]")
    private WebElementFacade Introducer;

    @FindBy(xpath = "//h4[contains(text(), 'User')]")
    private WebElementFacade user;

    @FindBy(xpath = "//h4[contains(text(), 'Credit Bureau')]")
    private WebElementFacade creditBureau;

    @FindBy(xpath = "//button[@class='-dropdown-dark dropdown-toggle'] | //button[@class='dropdown-toggle -dropdown-dark'] ")
    private WebElementFacade toggleDropdown;

    @FindBy(xpath = "//button[contains(text(), 'Update')]")
    private WebElementFacade updateButton;

    @FindBy(xpath = "//button[contains(text(), 'No')]")
    private WebElementFacade noButton;

    @FindBy(xpath = "//button[contains(text(), 'Review')]")
    private WebElementFacade reviewButton;

    @FindBy(xpath = "//div[contains(text(), 'Category')]")
    private WebElementFacade categoryDropdownHeader;

    @FindBy(xpath = "//a[@appresource='MatchReview.Res_FullApplicationDetails']")
    private WebElementFacade fullApplicationDetailsTab;

    @FindBy(xpath = "//input[@role='combobox'] | //div[@role='combobox']//input")
    private WebElementFacade categoryComboBox;

    @FindBy(xpath = "//span[contains(text(), 'Applicant')]")
    private WebElementFacade comboApplicant;

    @FindBy(xpath = "//span[contains(text(), 'Introducer')]")
    private WebElementFacade comboIntroducer;

    @FindBy(xpath = "//span[contains(text(), 'User')]")
    private WebElementFacade comboUser;

    @FindBy(xpath = "//span[contains(text(), 'Credit')]")
    private WebElementFacade comboCreditBureau;

    @FindBy(xpath = "//td[contains(text(), 'Company Name')]//following::td")
    private WebElementFacade companyNameValue;

    @FindBy(xpath = "//td[contains(text(), ' Id Number 1')]")
    private WebElementFacade idNumber1;

    public void addSuffix(String suffix) {
        addButton.click();
        waitABit(1000);
        typeInto(companySuffixTextBox, suffix);
        addSuffix.click();

    }

    public void updateCompanyName(String testData) throws InterruptedException {
        waitABit(3000);
        waitFor(ExpectedConditions.visibilityOf(applicationHeader));
        clickOn(applicant);
        scrollToElement(companyNameTextBox);
        typeInto(companyNameTextBox,testData);
        scrollToElement(categoryHeading);
        clickOn(Introducer);
        scrollToElement(companyNameTextBox);
        typeInto(companyNameTextBox,testData);
        scrollToElement(categoryHeading);
        clickOn(user);
        scrollToElement(companyNameTextBox);
        typeInto(companyNameTextBox,testData);
        scrollToElement(categoryHeading);
        clickOn(creditBureau);
        scrollToElement(companyNameTextBox);
        typeInto(companyNameTextBox,testData);
        clickOn(toggleDropdown);
        clickOn(updateButton);
        clickOn(noButton);


    }

    public void verifyCompanyName(String expectedData) throws InterruptedException {
        waitABit(3000);
        clickOn(reviewButton);
        waitABit(2000);
        waitFor(categoryComboBox).click();
        clickOn(comboApplicant);
        waitABit(1000);
        scrollToElement(companyNameValue);
        Assert.assertEquals(expectedData.toUpperCase(),companyNameValue.getText().toUpperCase());
        scrollToElement(fullApplicationDetailsTab);
        clickOn(categoryComboBox);
        clickOn(comboIntroducer);
        waitABit(1000);
        scrollToElement(companyNameValue);
        Assert.assertEquals(expectedData.toUpperCase(),companyNameValue.getText().toUpperCase());
        scrollToElement(fullApplicationDetailsTab);
        clickOn(categoryComboBox);
        clickOn(comboUser);
        waitABit(1000);
        scrollToElement(companyNameValue);
        Assert.assertEquals(expectedData.toUpperCase(),companyNameValue.getText().toUpperCase());
        scrollToElement(fullApplicationDetailsTab);
        clickOn(categoryComboBox);
        clickOn(comboCreditBureau);
        waitABit(1000);
        scrollToElement(companyNameValue);
        Assert.assertEquals(expectedData.toUpperCase(),companyNameValue.getText().toUpperCase());
    }


}

