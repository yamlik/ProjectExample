package com..test.pages.login;
import com..test.pages.BasePage;
import com..test.utilities.Properties;
import net.serenitybdd.core.annotations.findby.By;
import net.serenitybdd.core.annotations.findby.FindBy;
import net.serenitybdd.core.pages.WebElementFacade;

public class Login extends BasePage {
    public static final Properties fieldRequirements = new Properties("productUnderTestFieldRequirements.properties");

    @FindBy(name = "userId")
    private WebElementFacade usernameField;

    @FindBy(xpath = "//input[@id='userName']")
    private WebElementFacade username;

     @FindBy(name = "password")
    private WebElementFacade passwordField;

    @FindBy(xpath = "//input[@id='reEnterPassword']")
    private WebElementFacade reEnterPassword;

    @FindBy(id = ("database-select"))
    private WebElementFacade databaseSelector;

    @FindBy(xpath = "//input[@id='oldPassword']")
    private WebElementFacade oldPassword;

    @FindBy(xpath = "//input[@id='newPassword']")
    private WebElementFacade newPassword;

    @FindBy(xpath = "//input[@id='confirmPassword']")
    private WebElementFacade confirmNewPassword;

    @FindBy(xpath = "//div[@class='ng-input']")
    private WebElementFacade comboProfile;

    @FindBy(xpath = "//button[contains(text(),'Active')]")
    private WebElementFacade status;

    @FindBy(xpath = "//h2[text()='Dashboard']")
    public WebElementFacade dashboardHeading;

    @FindBy(xpath = " //span[contains(text(),'Save')]")
    private WebElementFacade save;

    @FindBy(xpath = "//button[contains(text(),'Change')]")
    private WebElementFacade changePasswordButton;

    @FindBy(xpath = ("//span[@class='ng-option-label'][contains(text(),'Reg')]"))

    private WebElementFacade automationDb;

    @FindBy(css = (".alp-bonito-button-dark"))
    private WebElementFacade signInBtn;

    @FindBy(xpath = ("//button[text()='Log In']"))
    public WebElementFacade signInBtnText;

    /**
     * Method to enter the username into the username field
     * @param username String value to enter into the field
     */
    public void enterUsername(String username) {
        typeInto(usernameField, username);
    }

    public void enterUserID(String userid) {
        typeInto(usernameField, userid);
    }

    /**
     * Method to enter the password into the password field
     * @param password String value to enter into the field
     */
    public void enterPassword(String password) {
        typeInto(passwordField, password);
    }
    public void enterOldPassword(String password) {
        typeInto(oldPassword, password);
    }
    public void enterNewPassword(String password) {
        typeInto(newPassword, password);
    }
    public void confirmNewPassword(String password) {
        typeInto(confirmNewPassword, password);
    }

    public void reenterPassword(String password) {
        typeInto(reEnterPassword, password);
    }

    /**
     * Method to select the appropriate Database option from the dropdown
     * @param database Value to be selected from the dropdown
     */

    public void selectDatabase(String database){
        databaseSelector.click();
        databaseSelector.findElement(By.xpath("//span[@class='ng-option-label'][contains(text(),'"+database+"')]")).click();
    }
    /**
     * Method to click the Sign In button
     */

    public void clickSignIn()  {
           clickOn(signInBtn);

}

}
