package com..test.pages.security;

import com..test.context.ScenarioContext;
import com..test.pages.BasePage;
import net.serenitybdd.core.annotations.findby.FindBy;
import net.serenitybdd.core.pages.WebElementFacade;
import net.thucydides.core.annotations.Shared;
import net.thucydides.core.pages.PageObject;
import org.apache.commons.lang3.RandomStringUtils;
import org.json.simple.parser.ParseException;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Properties;

public class Profile extends BasePage {

    private PageObject page;

    protected final String dataDir = System.getProperty("user.dir") + "/src/main/resources/data/UI/";
    @Shared
    ScenarioContext scenarioContext = ScenarioContext.getTSNTance();
    protected final String dataDir1 = System.getProperty("user.dir") + "/src/main/resources/";

    @FindBy(xpath = "//button[contains(text(),'Add')]")
    private WebElementFacade profileAdd;

    @FindBy(xpath = "//input[@name='profileName']")
    private WebElementFacade profileNameBox;

    @FindBy(xpath = "//button[contains(text(),'Presets')]")
    private WebElementFacade drpDownPersists;

    @FindBy(xpath = "//button[contains(text(),'Full')]")
    private WebElementFacade buttonFull;

    @FindBy(xpath = "//label[contains(text(),'Profiles')]/../label[1]")
    private WebElementFacade checkBoxProfile;

    @FindBy(xpath = "//label[contains(text(),'Users')]/../label[1]")
    private WebElementFacade checkBoxUsers;

    @FindBy(xpath = "//label[contains(text(),'Profiles')]/../../..//following-sibling::app-collapse-card//label[contains(text(),'Add')]/..//label[1]")
    private WebElementFacade checkBoxAdd;

    @FindBy(xpath = "//button[contains(text(),'Save')]")
    private WebElementFacade buttonSecuritySave;

    @FindBy(xpath = "//div[contains(text(),'Cancel')]")
    private WebElementFacade cancelButton;

    @FindBy(xpath = "//body/div//li[text()='Change']")
    private WebElementFacade changeButton;

    @FindBy(xpath = "//button[text()='Save'] | //button[text()='Submit']| //button[text()='Update']")
    private WebElementFacade buttonProfileDetailsSave;

    @FindBy(xpath = "(//button[text()='Save'] | //button[text()='Submit']| //button[text()='Update'])[2]")
    private WebElementFacade buttonProfileDetailsSubmit;

    @FindBy(xpath = "//input[@id='Comment']")
    private WebElementFacade commentBox;

    @FindBy(xpath = "//ul[@class='profile-ul']//label[contains(text(),'Security')]")
    private WebElementFacade checkboxSecurity;

    @FindBy(xpath = "//ul[@class='profile-ul']//label[contains(text(),'Watchlist')]")
    private WebElementFacade checkboxWatchlist;

    @FindBy(xpath = "//ul[@class='profile-ul']//label[contains(text(),'Rules Management')]")
    private WebElementFacade checkboxRulesManagement;

    @FindBy(xpath = "//ul[@class='profile-ul']//label[contains(text(),'Application Types')]")
    private WebElementFacade checkBoxApplicationTypes;

    @FindBy(xpath = "//div[contains(@class,'subtab-table-title-col')]//label[2]")
    private List<WebElementFacade> menuLabels;

    @FindBy(xpath = "//div[contains(@class,'rightPAnel')]//label")
    private List<WebElementFacade> aTSNTssLabels;

    @FindBy(xpath = "//label[@for='80107']")
    private WebElementFacade selfApproval;

    @FindBy(xpath = "//label[@for='20403']")
    private WebElementFacade selfApprovalWatchlist;

    @FindBy(xpath = "//label[@for='50112']")
    private WebElementFacade selfApprovalRulesManagement;

    @FindBy(xpath = "//label[@for='50113']")
    private WebElementFacade othersApprovalRulesManagement;

    @FindBy(xpath = "//label[@for='80201']")
    public WebElementFacade userAddCheckBox;

    @FindBy(xpath = "//input[@id='80201']")
    public WebElementFacade userAddHiddenCheckBox;

    @FindBy(xpath = "//label[@for='80202']")
    public WebElementFacade userUpdateCheckBox;

    @FindBy(xpath = "//input[@id='80202']")
    public WebElementFacade userUpdateHiddenCheckBox;

    @FindBy(xpath = "//label[@for='80207']")
    private WebElementFacade userSelfApproval;

    @FindBy(xpath = "//label[@for='80208']")
    private WebElementFacade userOtherApproval;

    public String pName = null;

    @FindBy(xpath = "//label[@for='80108']")
    private WebElementFacade profileOtherApproval;

    @FindBy(xpath = "//label[text()='Self Approval']/../label[1]")
    private WebElementFacade profileSelfApproval;

    @FindBy(xpath = "//label[text()='Others Approval']/../label[1]")
    private WebElementFacade watchlistOtherApproval;

    @FindBy(xpath = "//label[text()='Self Approval']/../label[1]")
    private WebElementFacade watchlistSelfApproval;

    @FindBy(xpath = "   //input[@id='Comment']")
    private WebElementFacade comment;

    @FindBy(xpath = "//tr//td[contains(text(),'All Approval')]/following-sibling::td//img[@class='loadmore']")
    private WebElementFacade changeProfileName1;

    @FindBy(xpath = "//tr//td[contains(text(),'Others Approval')]/following-sibling::td//img[@class='loadmore']")
    private WebElementFacade changeProfileName2;

    @FindBy(xpath = "//td[contains(text(),'ADMINISTRATOR')]/following-sibling::td//img[@class='loadmore']")
    private WebElementFacade administratorEllipsis;

    @FindBy(xpath = "//td[contains(text(),'NONADMIN')]/following-sibling::td//img[@class='loadmore']")
    private WebElementFacade nonAdministratorEllipsis;

    @FindBy(xpath = "//body/div[1]/div[1]/ul[1]/li[1]")
    private WebElementFacade change;


    @FindBy(xpath = "//div[contains(@class,'alert-dismissable')]")
    private WebElementFacade suTSNTssAlert;
//    //tagName[contains(@attribute,'value')]

//    @FindBy(xpath = "//div[@class='alp-notification-sticky-button suTSNTss alert-dismissable']")
//    private WebElementFacade suTSNTssAlert;

    @FindBy(xpath = "//span[contains(text(),'Pending')]")
    private WebElementFacade pendingLink;

    @FindBy(xpath = "//a[contains(text(),'Pending ')]")
    private WebElementFacade usersPendingLink;

    @FindBy(xpath = "(//tr//td[contains(text(),'automation123')]//following::td)[6]")
    private WebElementFacade ProfilePendingApproval1;

    @FindBy(xpath = "/html/body/div/div/ul/li[2]")
    private WebElementFacade userApprove;

    @FindBy(xpath = "//body/div[1]/div[1]/ul[1]/li[1]")
    private WebElementFacade userView;

    @FindBy(xpath = "//button[@class='close']")
    private WebElementFacade userViewClose;

    @FindBy(xpath = "//body/div[1]/div[1]/ul[1]/li[1]")
    private WebElementFacade userChange;

    @FindBy(xpath = "(//li[contains(text(),'Approve')])[2]")
    private WebElementFacade profileApprove;

    @FindBy(xpath = "(//li[contains(text(),'Approve')])[1]")
    private WebElementFacade profileApprove1;

    @FindBy(xpath = "//label[@for='CARD_Restriction']")
    private WebElementFacade restrictedToggleCreditCard;

    @FindBy(xpath = "//h2[contains(text(),'Profile Details')]")
    private WebElementFacade profileDetailsHeader;

    @FindBy(xpath = "//button[contains(text(),'Save')]")
    private WebElementFacade submitButton;

    @FindBy(xpath = "//button[@class='alp-bonito-button-dark' and contains(text(),'Submit')]")
    private WebElementFacade popUpSubmitButton;

    @FindBy(xpath = "//label[text()='Rules Management']")
    private WebElementFacade rulesManagementMenu;

    @FindBy(xpath = "//label[text()='Rules Configuration']")
    private WebElementFacade rulesConfigurationOption;

    @FindBy(xpath = "//label[contains(text(),'Rules Configuration')]/../../..//following-sibling::app-collapse-card//label[contains(text(),'History')]/..//label[1]")
    private WebElementFacade clickHistoryOption;

    @FindBy(xpath = "//*[text()='Profile Name']/following::input[1]")
    private WebElementFacade profileName;

    @FindBy(xpath = "//span[text()='Menu']/following::label[1]")
    private WebElementFacade investigationMenu;

    @FindBy(xpath = "//span[text()='Additional']/following::label[1]")
    private WebElementFacade organisationMenu;

    @FindBy(xpath = "//span[text()='Additional']/following::label[3]")
    private WebElementFacade applicationTypesMenu;

    @FindBy(xpath = "//label[contains(text(),'Application Maintenance')]/../../..//following-sibling::app-collapse-card//label[contains(text(),'Update')]/..//label[1]")
    private WebElementFacade updateOption;

    @FindBy(xpath = "(//div[@id='dropdownBasic2']//img)[1]")
    private WebElementFacade ruleEclipse;

    @FindBy(xpath = "//div[text()=' Decision Reason ']/following::input[1]")
    private WebElementFacade clickDecisionReason;

    @FindBy(xpath = "//h4[contains(text(),'Applicant')]")
    private WebElementFacade clickApplicantCategory;

    @FindBy(xpath = "//button[contains(text(),'Review')]/following::button[1]")
    private WebElementFacade dropdownToggle;


    @FindBy(xpath = "//button[contains(text(),'Update')]")
    private WebElementFacade updateButton;

    public void clickProfileADD() {
        waitABit(1000);
        waitFor(ExpectedConditions.visibilityOf(profileAdd));
        clickOn(profileAdd);
        waitFor(profileDetailsHeader);
    }

    public void profileApproval() throws InterruptedException {
        waitABit(2000);
        clickOn(profileApprove);

    }

    public void profileApproval1() {
        waitABit(2000);
        clickOn(profileApprove1);

    }


    public void enterUsername(String username) {
        typeInto(profileNameBox, username);
    }

    public Properties readPropertiesFile() throws IOException {
        Properties prop = new Properties();
        InputStream in = getClass().getResourceAsStream(dataDir1 + "Data.properties");
        prop.load(in);
        return prop;
    }


    public void enterProfileName() {
        String profileName = "profileName" + RandomStringUtils.randomNumeric(2);
        waitFor(ExpectedConditions.visibilityOf(profileNameBox));
        profileNameBox.sendKeys(profileName);
    }

    public void enterProfileName(String profile) {
        waitFor(ExpectedConditions.visibilityOf(profileNameBox));
        profileNameBox.sendKeys(profile);
    }

    public void enterProfileNameAllApproval() {
        String profileName = "All Approval";
        scenarioContext.setProfile1(profileName);
        waitFor(ExpectedConditions.visibilityOf(profileNameBox));
        profileNameBox.sendKeys(profileName);
    }

    public void enterProfileNameSelfApprovalAndAllApproval() {
        String profileName = "SelfApprovalAndAllApproval";
        waitFor(ExpectedConditions.visibilityOf(profileNameBox));
        profileNameBox.sendKeys(profileName);
    }

    public void enterProfileNameNoSelfApprovalAndOtherApproval() {
        String profileName = "No Self And Other Approval";
        scenarioContext.setProfile4(profileName);
        waitFor(ExpectedConditions.visibilityOf(profileNameBox));
        profileNameBox.sendKeys(profileName);
    }

    public void enterComment() {
        String Comment = "userComment" + RandomStringUtils.randomNumeric(2);
        comment.sendKeys(Comment);

    }

    public void enterProfileNameOthersApproval() throws IOException, ParseException {
        String profileName = "Others Approval";
        scenarioContext.setProfile2(profileName);
        waitFor(ExpectedConditions.visibilityOf(profileNameBox));
        profileNameBox.sendKeys(profileName);
    }

    public void enterProfileNameSelfApproval() throws IOException, ParseException {
        String profileName = "Self Approval";
        scenarioContext.setProfile3(profileName);
        waitFor(ExpectedConditions.visibilityOf(profileNameBox));
        profileNameBox.sendKeys(profileName);
    }

    public void ProfileName(String profileName) throws IOException, ParseException {
        profileNameBox.sendKeys(profileName);
    }

    public void enterProfile2NameOthersApproval() throws IOException, ParseException {
        String profileName = "enterProfile2NameOthersApproval";
        waitFor(ExpectedConditions.visibilityOf(profileNameBox));
        profileNameBox.sendKeys(profileName);
    }

    public void enterProfile2NameSelfApproval() throws IOException, ParseException {
        String profileName = "enterProfile2NameSelfApproval";
        waitFor(ExpectedConditions.visibilityOf(profileNameBox));
        profileNameBox.sendKeys(profileName);
    }


    public void clickOnPresetsDropDown() {

        waitFor(ExpectedConditions.visibilityOf(drpDownPersists));
        clickOn(drpDownPersists);
        waitFor(ExpectedConditions.visibilityOf(buttonFull));

    }

    public void clickOnFullButton() {
        clickOn(buttonFull);
        WebDriverWait wait = new WebDriverWait(getDriver(), 10);
        WebElement appTypesCheckbox = find(By.xpath("//input[contains(@checked,'IsApplicationTypeChecked')]"));
        wait.until(ExpectedConditions.attributeToBeNotEmpty(appTypesCheckbox, "checked"));

    }

    public void clickMenuLabel(String menuLabelToSelect) throws InterruptedException {
        for (WebElementFacade menuLabel : menuLabels) {
            if (menuLabelToSelect.equalsIgnoreCase(menuLabel.getText())) {
                scrollToElement(menuLabel);
                ((JavascriptExecutor) getDriver()).executeScript("scroll(0,400)");
                waitABit(1000);
                clickOn(menuLabel);
                waitABit(500);
            }
        }
    }

    public void removeUnwantedATSNTss(String aTSNTssItemToKeep) {
        for (WebElementFacade aTSNTssLabel : aTSNTssLabels) {
            if (!aTSNTssItemToKeep.equalsIgnoreCase(aTSNTssLabel.getText()))
                clickOn(aTSNTssLabel);
        }
    }

    public void checkUSerSelfApproval() {
        waitABit(1000);
        JavascriptExecutor jse = (JavascriptExecutor) getDriver();
        jse.executeScript("window.scrollBy(0,250)");
        waitABit(2000);
        userSelfApproval.click();
    }

    public void checkUserOtherApproval() {
        waitABit(1000);
        JavascriptExecutor jse = (JavascriptExecutor) getDriver();
        jse.executeScript("window.scrollBy(0,250)");
        waitABit(1000);
        userOtherApproval.click();

    }

    public void checkWatchlistSelfApproval() {
        waitABit(1000);
        JavascriptExecutor jse = (JavascriptExecutor) getDriver();
        jse.executeScript("window.scrollBy(0,250)");
        waitABit(2000);
        watchlistSelfApproval.click();
    }

    public void checkWatchlistOtherApproval() {
        waitABit(1000);
        JavascriptExecutor jse = (JavascriptExecutor) getDriver();
        jse.executeScript("window.scrollBy(0,250)");
        waitABit(1000);
        watchlistOtherApproval.click();

    }

    public void checkProfileOtherApproval() {
        waitABit(1000);
        profileOtherApproval.click();
    }

    public void checkProfileSelfApproval() {
        waitABit(1000);
        profileSelfApproval.click();

    }

    public void clickMenuProfileDetails(String element) {
        switch (element.toUpperCase()) {
            case "SECURITY":
                waitABit(2000);
                JavascriptExecutor jse = (JavascriptExecutor) getDriver();
                jse.executeScript("window.scrollBy(0,250)");
                waitABit(1000);
                clickOn(checkboxSecurity);
                break;
            case "WATCHLIST":
                waitABit(2000);
//                jse = (JavascriptExecutor) getDriver();
//                jse.executeScript("window.scrollBy(0,250)");
//                waitABit(1000);
                clickOn(checkboxWatchlist);
                break;
            case "RULES MANAGEMENT":
                waitABit(1000);
                jse = (JavascriptExecutor) getDriver();
                jse.executeScript("window.scrollBy(0,250)");
                waitABit(500);
                clickOn(checkboxRulesManagement);
                break;

            case "APPLICATION TYPES":
                waitABit(1000);
                jse = (JavascriptExecutor) getDriver();
                jse.executeScript("window.scrollBy(0,350)");
                waitABit(500);
                clickOn(checkBoxApplicationTypes);
                break;
            default:
                throw new NoSuchElementException("\"" + element + "\" is not visible on page");
        }
    }

    public void unCheckProfile() {
        waitABit(1000);
        clickOn(checkBoxProfile);
    }

    public void unCheckUsers() {
        //  waitABit(2000);
        clickOn(checkBoxUsers);
    }

    public void clickCheckBoxAdd() {
        clickOn(checkBoxAdd);
    }


    public void clickOnSave() {
        clickOn(buttonSecuritySave);
        waitFor(suTSNTssAlert).waitUntilNotVisible();
    }

    public void clickCancel() {
        clickOn(cancelButton);
    }

    public void clickEllipsisForRow(String profile) {
        WebElement profileRow = find(By.xpath("//td[contains(text(),'" + profile + "')]/following-sibling::td//app-ellipsis-menu//img"));
        clickOn(profileRow);
        clickOn(changeButton);
        waitABit(2000);
    }

    public void setChangeProfileName1() {
        waitFor(changeProfileName1);
        changeProfileName1.click();
    }

    public void changeProfileSettings() {
        waitFor(nonAdministratorEllipsis);
        nonAdministratorEllipsis.click();
    }

    public void setChangeProfileName2() {
        changeProfileName2.click();
    }

    public void changeProfile() {
        waitABit(1000);
        clickOn(change);
    }

    public void clickOnPending() {
        pendingLink.click();
    }

    public void profile1ApprovalEllipsisLink(String profileName) {

        waitABit(2000);
        WebElement ProfilePendingApproval1 = getDriver().findElement(org.openqa.selenium.By.xpath("//tr//td[contains(text(),'" + profileName + "')]/following-sibling::td//img[@class='loadmore']"));
        ProfilePendingApproval1.click();
    }

    public void userApprove() {
        userApprove.click();

    }

    public void user_Change() {
        waitABit(1000);
        clickOn(userChange);

    }

    public void userView() {
        userView.click();
        if (userViewClose.isCurrentlyVisible()) {
            userViewClose.click();
        }
    }

    public void clickOnUsersPendingLink() {
        waitFor(usersPendingLink);
        usersPendingLink.click();
    }

    public void unCheckSelfApproval() {
        selfApproval.click();
    }

    public void unCheckSelfApprovalWatchlist() {
        selfApprovalWatchlist.click();
    }

    public void unCheckSelfApprovalRulesManagement() {
        selfApprovalRulesManagement.click();
    }

    public void unCheckOthersApprovalRulesManagement() {
        waitABit(500);
        othersApprovalRulesManagement.click();
    }

    public void unCheckOthersApproval() {
        profileOtherApproval.click();
    }

    public void restrictCreditCard() throws InterruptedException {
        scrollToElement(profileDetailsHeader);
        waitFor(restrictedToggleCreditCard).click();

    }

    public void clickSubmitButton() {
        submitButton.click();
    }

    public void clickPopUpSubmitButton() {
        popUpSubmitButton.click();
    }

    public void clickOnSpecificProfile(String profileName) {
        WebElement profileNameLoadMore = getDriver().findElement(By.xpath("//td[contains(text(),'" + profileName + "')]/following-sibling::td//img[@class='loadmore']"));
        waitFor(ExpectedConditions.visibilityOf(profileNameLoadMore));
        clickOn(profileNameLoadMore);
    }

    public void clickMenuProfile() throws InterruptedException {
        clickOn(investigationMenu);
        scrollToElement(updateOption);
        ((JavascriptExecutor) getDriver()).executeScript("scroll(0,400)");
        waitABit(1000);
        clickOn(updateOption);
        clickOn(rulesManagementMenu);
        waitFor(ExpectedConditions.visibilityOf(clickHistoryOption));
        clickOn(clickHistoryOption);
        clickOn(organisationMenu);
        clickOn(applicationTypesMenu);
        waitABit(1000);
        clickOn(buttonSecuritySave);
    }

    public void addProfile() {

        WebElement addProfile = getDriver().findElement(By.xpath("//button[text()='Add']"));
        addProfile.click();
    }

    public void verifyViewATSNTss() {
        waitABit(1000);
        clickOn(ruleEclipse);
        WebElement viewMenu = getDriver().findElement(By.xpath("//li[contains(text(),'View')]"));
        Assert.assertEquals(false, viewMenu.isDisplayed());
        System.out.println("view is displayed- Assert Passed");
    }

    public void setCheckDecisionReason(String decisionReason) throws InterruptedException {

        waitABit(2000);
        WebElement decisionReasonBox = getDriver().findElement(By.xpath("//div[contains(text(),'" + decisionReason + "')]/following::input[1]"));

        scrollToElement(decisionReasonBox);
        ((JavascriptExecutor) getDriver()).executeScript("scroll(0,400)");

        boolean visible = decisionReasonBox.isDisplayed();
        boolean enabled = decisionReasonBox.isEnabled();
        if (visible == true && enabled == true) {

            Assert.assertTrue("Element is visible and editable", true);
        } else
            Assert.fail("Element is not editable");
    }

    public void setCheckNatureOfFraud(String natureOfFraud) throws InterruptedException {
        scrollToElement(clickApplicantCategory);
        clickOn(clickApplicantCategory);
        WebElement natureOfFraudUserField = getDriver().findElement(By.xpath("//div[contains(text(),'" + natureOfFraud + "')]/following::input[1]"));
        boolean visible = natureOfFraudUserField.isDisplayed();
        boolean enabled = natureOfFraudUserField.isEnabled();
        if (visible == true && enabled == true) {
            Assert.assertTrue("Element is visible and editable", true);
        } else {
            Assert.fail("Element is not editable");
        }
    }

    public void verifyNoUpdateATSNTss() {
        waitABit(1000);
        dropdownToggle.click();
        int updateCheck = getDriver().findElements(By.xpath("//button[contains(text(),'Update')]")).size();
        try {
            if (updateCheck == 0) {
                System.out.println("No Update ATSNTss");
            }
        } catch (Exception e) {
            System.out.println("Update element exist");
        }
    }
}



