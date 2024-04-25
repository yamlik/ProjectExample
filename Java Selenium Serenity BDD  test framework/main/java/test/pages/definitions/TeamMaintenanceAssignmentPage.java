package com..test.pages.definitions;

import com..test.context.constants.ApplicationConstants;
import com..test.pages.BasePage;
import net.serenitybdd.core.annotations.findby.FindBy;
import net.serenitybdd.core.pages.WebElementFacade;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;

public class TeamMaintenanceAssignmentPage extends BasePage {

    //TEAM MAINTENANCE PAGE
    @FindBy(xpath = "//button-drop-down[contains(@id,'action-button')]//button[contains(text(),'Add')]")
    private WebElementFacade addButton;


    //TEAM ASSIGNMENT PAGE
    String availableUserRoot = "//div[contains(@class,'alp-bonito-h3') and text()='Available Users']//following-sibling::div";
    String assignedUserRoot = "//div[contains(@class,'alp-bonito-h3') and text()='Assigned Users']//following-sibling::div";

    @FindBy(xpath = "//h2[contains(text(),'Team Assignment')]")
    private WebElementFacade teamAssignmentHeading;

    @FindBy(xpath = "//div[@class='label-text form-label' and text()='Team Name']/following-sibling::div/input")
    private WebElementFacade teamNameTextBox;

    @FindBy(xpath = "//div[@class='button-col']//img[1]")
    private WebElementFacade moveRightButton;

    @FindBy(xpath = "//div[@class='button-col']//img[2]")
    private WebElementFacade moveLeftButton;

    @FindBy(xpath = "//div[@appresource='OrganisationChart.Ref_Definition_SameTeam' and text()='Same team']/..//following-sibling::div[2]//div[@class='can-toggle__switch']")
    private WebElementFacade reassignSameTeamToggleButton;

    @FindBy(xpath = "//div[@appresource='OrganisationChart.Ref_Definition_SameTeam' and text()='Same team']/..//following-sibling::div[1]//div[@class='can-toggle__switch']")
    private WebElementFacade actionSameTeamToggleButton;

    @FindBy(xpath = "//button/span[contains(text(),'Save')]")
    private WebElementFacade saveButton;

    @FindBy(xpath = "//div[contains(@class,'alert-dismissable')]")
    private WebElementFacade messageBanner;


    //TEAM MAINTENANCE PAGE METHODS
    public void clickAddButton() { clickOn(addButton); }

    public void clickSaveButton()
    {
        clickOn(saveButton);
        waitFor(messageBanner);
        Assert.assertEquals(messageBanner.getText(), ApplicationConstants.MESSAGE_WHEN_SAVED);
        waitFor(messageBanner).waitUntilNotVisible();
    }


    //TEAM ASSIGNMENT PAGE METHODS
    public void enterTeamName(String teamName) { typeInto(teamNameTextBox, teamName); }

    public void enterWeightInAvailableUser(String weight, String user) {
        WebElement weightField =
                getDriver().findElement(By.xpath(availableUserRoot+"//td[contains(text(),'"+user+"')]/following-sibling::td/input"));
        typeInto(weightField,weight);
    }

    public void enterWeightInAssignedUser(String weight, String user) {
        WebElement weightField =
                getDriver().findElement(By.xpath(assignedUserRoot+"//td[contains(text(),'"+user+"')]/following-sibling::td/input"));
        typeInto(weightField,weight);
    }

    public void moveAvailableUserToAssignedUser(String user)
    {
        WebElement userToSelect =
                getDriver().findElement(By.xpath(availableUserRoot+"//td[contains(text(),'"+user+"')]"));
        clickOn(userToSelect);
        clickOn(moveRightButton);
    }

    public void moveAssignedUserToAvailableUser(String user)
    {
        WebElement userToSelect =
                getDriver().findElement(By.xpath(assignedUserRoot+"//td[contains(text(),'"+user+"')]"));
        clickOn(userToSelect);
        clickOn(moveLeftButton);
    }

    public void selectOutOfOfficeInAvailableUsersGrid(String ooo, String user) {
        WebElement oooField =
                getDriver().findElement(By.xpath(availableUserRoot+"//td[contains(text(),'"+user+"')]//following-sibling::td//div[@class='can-toggle__switch']"));
        WebElement oooHiddenCheckbox =
                getDriver().findElement(By.xpath(availableUserRoot+"//td[contains(text(),'"+user+"')]//following-sibling::td//input[contains(@id,'availableOutOffOffice')]"));
        if((ooo.equals("OFF") && oooHiddenCheckbox.isSelected()) ||
                (ooo.equals("ON") && !oooHiddenCheckbox.isSelected()))
            oooField.click();
    }

    public void selectOutOfOfficeInAssignedUsersGrid(String ooo, String user) {
        WebElement oooField =
                getDriver().findElement(By.xpath(assignedUserRoot+"//td[contains(text(),'"+user+"')]//following-sibling::td//div[@class='can-toggle__switch']"));
        WebElement oooHiddenCheckbox =
                getDriver().findElement(By.xpath(assignedUserRoot+"//td[contains(text(),'"+user+"')]//following-sibling::td//input[contains(@id,'assignedOutOffOffice')]"));
        if((ooo.equals("OFF") && oooHiddenCheckbox.isSelected()) ||
                (ooo.equals("ON") && !oooHiddenCheckbox.isSelected()))
            oooField.click();
    }

    public void reassignSameTeam(String reassign) {
        ((JavascriptExecutor) getDriver()).executeScript("arguments[0].scrollIntoView(true);", reassignSameTeamToggleButton);waitABit(1000);
        ((JavascriptExecutor) getDriver()).executeScript("javascript:window.scrollBy(0,-100)");waitABit(1000);
        WebElement reassignSameTeamHiddenCheckbox =
                getDriver().findElement(By.xpath("//div[@appresource='OrganisationChart.Ref_Definition_SameTeam' and text()='Same team']/..//following-sibling::div[2]//input[@id='reassignSameTeam']"));
        if((reassign.equals("OFF") && reassignSameTeamHiddenCheckbox.isSelected()) ||
                (reassign.equals("ON") && !reassignSameTeamHiddenCheckbox.isSelected()))
            reassignSameTeamToggleButton.click();
    }

    public void actionSameTeam(String action) {
        ((JavascriptExecutor) getDriver()).executeScript("arguments[0].scrollIntoView(true);", actionSameTeamToggleButton);waitABit(1000);
        ((JavascriptExecutor) getDriver()).executeScript("javascript:window.scrollBy(0,-100)");waitABit(1000);
        WebElement actionSameTeamHiddenCheckbox =
                getDriver().findElement(By.xpath("//div[@appresource='OrganisationChart.Ref_Definition_SameTeam' and text()='Same team']/..//following-sibling::div[1]//input[@id='actionSameTeam']"));
        if((action.equals("OFF") && actionSameTeamHiddenCheckbox.isSelected()) ||
                (action.equals("ON") && !actionSameTeamHiddenCheckbox.isSelected()))
            actionSameTeamToggleButton.click();
    }

    public void verifyTeamCreated(String team)
    {
        WebElementFacade teamNameInGrid = find((By.xpath("//td[text()='"+team+"']")));
        Assert.assertEquals("Team is not created.",team,teamNameInGrid);
    }


}
