package com..test.pages.security;

import com..test.context.ScenarioContext;
import com..test.utilities.Properties;
import net.serenitybdd.core.annotations.findby.By;
import net.serenitybdd.core.annotations.findby.FindBy;
import net.serenitybdd.core.pages.WebElementFacade;
import com..test.pages.BasePage;
import net.thucydides.core.annotations.Shared;
import net.thucydides.core.pages.PageObject;
import org.junit.Assert;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;



public class Users extends BasePage {

    @Shared
    ScenarioContext scenarioContext;
    private PageObject page;
    private Profile Profile;
    public static final Properties fieldRequirements = new Properties("productUnderTestFieldRequirements.properties");

    @FindBy(xpath = "//button[contains(text(),'Add')]")
    public WebElementFacade addUserButton;

    @FindBy(xpath = "//input[@id='userId']")
    private WebElementFacade userId;

    @FindBy(xpath = "//input[@id='userName']")
    private WebElementFacade userName;

    @FindBy(xpath = "//input[@id='password']")
    private WebElementFacade passwordField;

    @FindBy(xpath = "//input[@id='reEnterPassword']")
    private WebElementFacade reEnterPassword;

    @FindBy(xpath = "//span[contains(text(),'Save')]")
    private WebElementFacade new_BtnSave;
    // Login Screen
    @FindBy(name = "userId")
    private WebElementFacade login_Userid;

    @FindBy(name = "password")
    private WebElementFacade login_Password;

    @FindBy(css = (".alp-bonito-button-dark"))
    private WebElementFacade login;

    // change password screen
    @FindBy(xpath = "//input[@id='oldPassword']")
    private WebElementFacade oldPassword;

    @FindBy(xpath = "//input[@id='newPassword']")
    private WebElementFacade newPassword;

    @FindBy(xpath = "//input[@id='confirmPassword']")
    private WebElementFacade confirmNewPassword;

    @FindBy(xpath = "//div[@class='ng-input']/..")
    private WebElementFacade comboProfile;

    @FindBy(xpath = "//button[contains(text(),'Active')]")
    private WebElementFacade status;

    @FindBy(xpath = "//button[contains(text(),'Save')] | //button[contains(text(),'Submit')] | //button//span[contains(text(),'Save')] | //button//span[contains(text(),'Submit')] ")
    private WebElementFacade save;

    @FindBy(xpath = "//button[@class='-dropdown-dark dropdown-toggle'] | //button[@class='dropdown-toggle -dropdown-dark']")
    private WebElementFacade submitToggle;

    @FindBy(xpath = "//button[contains(text(),'Save As Draft')]")
    private WebElementFacade saveAsDraft;

    @FindBy(xpath = "//button[contains(text(),'Submit')]")
    private WebElementFacade btnSubmit;

    @FindBy(xpath = "//button[@class='alp-bonito-button-dark']")
    private WebElementFacade commentSubmit;

    @FindBy(xpath = "//div[contains(@class,'alert-dismissable')]")
    public WebElementFacade messageBanner;

    @FindBy(xpath = "//button[contains(text(),'Change')]")
    private WebElementFacade changePasswordButton;

    @FindBy(xpath = ("//span[@class='ng-option-label'][contains(text(),'Reg')]"))

    private WebElementFacade automationDb;

    @FindBy(css = (".alp-bonito-button-dark"))
    private WebElementFacade signInBtn;

    @FindBy(xpath = "//div[@role='option']//span[contains(text(),'All Approval')]")
    private WebElementFacade allApprovalProfile;

    @FindBy(xpath = "//div[@role='option']//span[contains(text(),'No Self And Other Approval')]")
    private WebElementFacade noSelfAndOtherApprovalProfile;

    @FindBy(xpath = "//div[@role='option']//span[contains(text(),'ADMINISTRATOR')]")
    private WebElementFacade administrator;

    @FindBy(xpath = "//div[@role='option']//span[contains(text(),'Others Approval')]")
    private WebElementFacade othersApprovalProfile;

    @FindBy(xpath = "//div[@role='option']//span[contains(text(),'Self Approval')]")
    private WebElementFacade selfApprovalProfile;

    @FindBy(xpath = "//span[contains(text(),'SelfApprovalAndAllApproval')]")
    private WebElementFacade SelfApprovalAndOtherApproval;

    @FindBy(xpath = "//div[@class='dropup']//ul//li//following::li")
    private WebElementFacade deleteUserButton;

    @FindBy(xpath = "//div[@class='dropup']//ul//li//following::li//following::li")
    private WebElementFacade deleteProfileButton;

    @FindBy(xpath = "//button[contains(text(),'Yes')]")
    private WebElementFacade confirmButton;

    @FindBy(xpath = "//a[contains(text(),'Pending')]")
    private WebElementFacade pending;

    @FindBy(xpath = "//tr//td[contains(text(),'productUnderTest_user_58874')]//following::td//app-ellipsis-menu//..")
    private WebElementFacade userEllipsis;

    @FindBy(xpath = "//li[contains(text(),'Approve')]")
    private WebElementFacade approveUser;

    @FindBy(xpath = "//li[contains(text(),'Reject')]")
    private WebElementFacade rejectUser;

    @FindBy(xpath = "//li[contains(text(),'Discard')]")
    private WebElementFacade discardUser;

    @FindBy(xpath = "//body/div[last()]//li[text()='Change']")
    private WebElementFacade changeUser;

    @FindBy(xpath = "//h2[normalize-space()='Change User']")
    private WebElementFacade changeUserPopupHeading;

    @FindBy(xpath = "//h2[text()='Users']")
    private WebElementFacade userPageHeading;

    @FindBy(xpath = "//app-ellipsis-menu")
    private WebElementFacade firstRowEllipsis;

    @FindBy(xpath = "//button[@class='close']")
    public WebElementFacade closePopup;

    public  void addUser()
    {   waitABit(2000);
        waitFor(ExpectedConditions.visibilityOf(addUserButton));
        waitFor(addUserButton).click();
    }

    public void enterUserID(String userid) {
        typeInto(userId, userid);
    }


    public void enterUsername(String username) {
      typeInto(userName, username);

    }

    public void enterPassword(String password) {
       typeInto(passwordField, password);
    }

    public void reenterPassword(String password) {
       typeInto(reEnterPassword, password);
    }

    public void confirmPassword(String password) {
        actionClick(confirmNewPassword, password);
    }

    public void clickSignIn()  {
        clickOn(signInBtn);
    }
        public void drpProfileName() {
        clickOn(comboProfile);
        String pNme = Profile.pName;
        WebElementFacade profileName = getDriver().findElement(By.xpath("//span[text()='" + pNme + "'"));
        waitABit(3000);
        clickOn(profileName);
    }
	public void drpProfileName(String name) {
        clickOn(comboProfile);
        WebElement profileName = getDriver().findElement(By.xpath("//span[normalize-space(text())='" + name + "']"));
        clickOn(profileName);
    }
    public void selectAllApprovalProfile() {
        clickOn(comboProfile);
        waitABit(1000);
        clickOn(allApprovalProfile);
    }

    public void selectNoSelfAndOtherApprovalProfile() {
        clickOn(comboProfile);
        waitABit(1000);
        clickOn(noSelfAndOtherApprovalProfile);
    }

    public void selectAdministrator() {
        waitABit(2000);
        clickOn(comboProfile);
        waitABit(2000);
        clickOn(administrator);
    }

    public void selectSelfAndAllApprovalProfile() {
        clickOn(comboProfile);
        waitABit(1000);
        clickOn(SelfApprovalAndOtherApproval);
    }

    public void selectOthersApprovalProfile() {
        clickOn(comboProfile);
        waitABit(1000);
        clickOn(othersApprovalProfile);
    }

    public void selectSelfApprovalProfile() {
        clickOn(comboProfile);
        waitABit(1000);
        clickOn(selfApprovalProfile);
    }

    public  void clickActiveButton()
    {
        JavascriptExecutor jse = (JavascriptExecutor) getDriver();
        jse.executeScript("window.scrollBy(0,250)");
        clickOn(status);
    }

    public  void save()
    {
        clickOn(save);
        if(messageBanner.isVisible())
            waitFor(messageBanner).waitUntilNotVisible();
    }

    public  void change()
    {
        clickOn(changePasswordButton);
    }

    public void actionClick(WebElementFacade element,String value) {
        Actions act = new Actions(getDriver());
        waitFor(ExpectedConditions.visibilityOf(element));
        act.moveToElement(element).click().sendKeys(value).build().perform();
    }

    public void deleteUser(String userName){
        WebElement userEllipsis = getDriver().findElement(org.openqa.selenium.By.xpath("//tr//td[contains(text(),'"+userName+"')]//following::td//app-ellipsis-menu//.."));
        userEllipsis.click();
        deleteUserButton.click();
        confirmButton.click();

    }
    public void deleteProfile(String profileName){
        WebElement userEllipsis = getDriver().findElement(org.openqa.selenium.By.xpath("//tr//td[contains(text(),'"+profileName+"')]//following::td//app-ellipsis-menu//.."));
        userEllipsis.click();
        deleteProfileButton.click();
        confirmButton.click();

    }

    public  void btnSubmit()
    {
        clickOn(btnSubmit);
    }

    public  void  commentBtnSubmit()
    {
        clickOn(commentSubmit);
        waitFor(messageBanner);

    }

    public void verify_Users(String UserName)
    {
        String createdUsers=getDriver().findElement(By.xpath("(//tr//td[contains(text(),'"+UserName+"')])[1]")).getText();
        Assert.assertEquals(UserName,createdUsers);
        waitABit(2000);
    }


    public void approveUsers(String userName) {
        pending.click();
        waitABit(1000);
        WebElement userEllipsis = getDriver().findElement(By.xpath("//tr//td[contains(text(),'"+userName+"')]//following::td//app-ellipsis-menu//.."));
        userEllipsis.click();
        waitABit(500);
        approveUser.click();

    }

    public  void saveAsDraft()
    {
        clickOn(submitToggle);
        waitABit(1000);
       waitFor(saveAsDraft).click();
       waitFor(messageBanner).waitUntilNotVisible();
    }

    public void submitUser(String userName) {
        pending.click();
        WebElement userEllipsis = getDriver().findElement(By.xpath("//tr//td[contains(text(),'"+userName+"')]//following::td//app-ellipsis-menu//.."));
        userEllipsis.click();
        waitABit(500);
        changeUser.click();
        waitABit(500);
        save.click();
    }

    public boolean selectChangeForUser(String user)
    {
        waitFor(firstRowEllipsis).waitUntilClickable();
        WebElement userEllipsis = getDriver().findElement(By.xpath("//tr/td[1][contains(text(),'"+user+"')]/following-sibling::td[last()]//app-ellipsis-menu"));
        userEllipsis.click();
        waitABit(500);
        if(changeUser.isVisible())
            changeUser.click();
        if(changeUserPopupHeading.isVisible()) {
            return true;
        }
        return false;
    }

    public void rejectUsers(String userName) {
        WebElement userEllipsis = getDriver().findElement(By.xpath("//tr//td[contains(text(),'"+userName+"')]//following::td//app-ellipsis-menu//.."));
        userEllipsis.click();
        waitABit(500);
        rejectUser.click();

    }

    public void discardUser(String userName) {
        pending.click();
        WebElement userEllipsis = getDriver().findElement(By.xpath("//tr//td[contains(text(),'"+userName+"')]//following::td//app-ellipsis-menu//.."));
        userEllipsis.click();
        waitABit(500);
        discardUser.click();
        waitABit(1000);


    }
}


