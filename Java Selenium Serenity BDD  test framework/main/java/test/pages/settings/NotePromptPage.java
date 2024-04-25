package com..test.pages.settings;

import com..test.pages.BasePage;
import net.serenitybdd.core.annotations.findby.FindBy;
import net.serenitybdd.core.pages.WebElementFacade;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

public class NotePromptPage extends BasePage {

    @FindBy(xpath = "//button-drop-down[contains(@id,'action-button')]//button")
    private WebElementFacade addButton;

    @FindBy(xpath = "//ng-select[contains(@formcontrolname,'availableFieldsList')]//input")
    private WebElementFacade availableFieldsList;

    @FindBy(xpath = "//button[contains(@id,'addField')]")
    private WebElementFacade addFieldButton;

    @FindBy(xpath = "//body/div//li[text()='Change']")
    private WebElementFacade changeButton;

    @FindBy(xpath = "//textarea[contains(@id,'diaryprompts_emailContent')]")
    private WebElementFacade notePromptTextBox;

    @FindBy(xpath = "//div[contains(@class,'modal-footer')]//button")
    private WebElementFacade addButtonInAddPopup;

    @FindBy(xpath = "//div[contains(@class,'alert-dismissable')]")
    private WebElementFacade messageBanner;

    @FindBy(xpath = "//div[@class='dropdown']//li[contains(text(),'Delete')]")
    private WebElementFacade deleteButton;

    @FindBy(xpath = "//img[contains(@class,'loadmore')]")
    private WebElementFacade firstEllipsisButton;

    @FindBy(xpath = "//h2[contains(text(),'Confirm Delete')]")
    private WebElementFacade confirmDeletePopupHeading;

    @FindBy(xpath = "//div[contains(@class,'modal-footer')]//button[contains(text(),'Yes')]")
    private WebElementFacade yesButtonConfirmDeletePopup;

    @FindBy(xpath = "//div[contains(@class,'modal-footer')]//button[contains(text(),'No')]")
    private WebElementFacade noButtonConfirmDeletePopup;

    @FindBy(xpath = "//div[@id='clear']")
    private WebElementFacade clearButton;

    @FindBy(xpath = "//button[contains(text(),'Filter')]")
    private WebElementFacade filterButton;

    @FindBy(xpath = "//div[@role='document']//input")
    private WebElementFacade filterTextBox;

    @FindBy(xpath = "//button[contains(text(),'Apply')]")
    private WebElementFacade filterApplyButton;

    @FindBy(xpath = "//span[contains(text(),'Clear All')]")
    private WebElementFacade filterClearAll;

    public void clickAddButton() {clickOn(addButton);}

    public void enterAvailableFieldsList(String availableField) {availableFieldsList.typeAndEnter(availableField);}

    public void clickAddFieldButton() { clickOn(addFieldButton);}

    public void clickDelete() { clickOn(deleteButton); }

    public void appendNotePromptTextBox(String notePrompt)
    {
        String previousText = notePromptTextBox.getAttribute("value");
        typeInto(notePromptTextBox,previousText+notePrompt);
    }

    public void clickAddButtonInAddPopup() { clickOn(addButtonInAddPopup);}

    public void verifyNotePromptIsAdded(String notePromptToVerify)
    {
        boolean notePromptExists=false;

        waitABit(4000);

        List<WebElement> notePromptsList = getDriver().findElements(By.xpath("//table[contains(@id,'diaryPromptTable')]//td[1]"));
        for (WebElement webElement : notePromptsList) {
            if (webElement.getText().equals(notePromptToVerify))
                notePromptExists=true;
        }
        Assert.assertTrue("Note Prompt not added "+notePromptToVerify,notePromptExists);
    }

    public void deleteNotePrompt(String notePrompt)
    {
        waitFor(firstEllipsisButton).waitUntilClickable();
        WebElement noteElementEllipsis =
                getDriver().findElement(By.xpath("//td[contains(text(),'"+notePrompt+"')]/following-sibling::td//img[contains(@class,'loadmore')]"));
        clickOn(noteElementEllipsis);
        clickDelete();
        waitFor(confirmDeletePopupHeading);
        clickOn(yesButtonConfirmDeletePopup);
        waitFor(messageBanner).waitUntilVisible();
        Assert.assertTrue("Note Prompt not deleted.",messageBanner.getText().equals("Deleted suTSNTssfully."));
    }

    public void clickEllipsisForRow(String note)
    {
        WebElement noteRow = find(By.xpath("//td[contains(text(),'"+note+"')]/following-sibling::td//img"));
        clickOn(noteRow);
        clickOn(changeButton);
    }

    public void clickClearButton() {clickOn(clearButton);}

    public void clickFilterButton() {clickOn(filterButton);}

    public void clickFilterApplyButton() {
        clickOn(filterApplyButton);
        waitABit(1000);
    }

    public void clickFilterClearAll() {clickOn(filterClearAll);}

    public void insertContentToFilter(String content) {
        clickOn(filterButton);
        clickOn(filterClearAll);
        filterTextBox.type(content);
        clickOn(filterApplyButton);
        waitABit(1000);
    }
}
