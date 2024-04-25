package com..test.pages.commonPages;

import com..test.pages.BasePage;
import net.serenitybdd.core.annotations.findby.FindBy;
import net.serenitybdd.core.pages.WebElementFacade;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.NoSuchElementException;

public class Navigation extends BasePage {

    //Expand Sidemenu
    @FindBy(id = "menuToggle")
    private WebElementFacade menuToggle;

    //Side Nav Bar
    @FindBy(xpath = "//span[text()='Dashboard']/..")
    private WebElementFacade sidebarDashboard;

    @FindBy(xpath = "//span[text()='Investigation']/..")
    private WebElementFacade sidebarInvestigation;

    @FindBy(xpath = "//span[text()='Watchlist']/..")
    private WebElementFacade sidebarWatchlist;

    @FindBy(xpath = "//span[text()='Reports']/..")
    private WebElementFacade sidebarReports;

    @FindBy(xpath = "//span[text()='Definitions']/..")
    private WebElementFacade sidebarDefinitions;

    @FindBy(xpath = "//span[text()='Rules']/..")
    private WebElementFacade sidebarRules;

    @FindBy(xpath = "//span[text()='Settings']/..")
    private WebElementFacade sidebarSettings;

    @FindBy(xpath = "//*[@id='page-content-wrapper']//following::input")
    private WebElementFacade configurationPurpose;

    @FindBy(xpath = "//span[contains(text(),'Link Analysis Maintenance')]")
    private WebElementFacade link_Analysis_Maintenance;
//    @FindBy(xpath = "//li[@class='menu-item settings']")
//    private WebElementFacade sidebarSettings;

    @FindBy(xpath = "//span[text()='Utilities']/..")
    private WebElementFacade sidebarUtilities;

    @FindBy(xpath = "//span[text()='Security']/..")
    private WebElementFacade sidebarSecurity;

    @FindBy(xpath = "//h2[text()='Organisation']")
    private WebElementFacade Organisation;

    @FindBy(xpath = "//h2[text()='Company Affix']/..")
    private WebElementFacade companyAffix;

    @FindBy(xpath = "//h2[text()='Classing Maintenance']/..")
    private WebElementFacade classingMaintenance;

    @FindBy(xpath = "//h2[text()='Criteria Fields']")
    private WebElementFacade CriteriaFields;
    @FindBy(xpath = "//h2[text()='Indexed Fuzzy Match Mapping']")
    private WebElementFacade indexedFuzzyMatch;
    @FindBy(xpath = "//h2[text()='Field Requirements']")
    private WebElementFacade fieldRequirements;
    @FindBy(xpath="//h2[text()='Dashboard']")
    private WebElementFacade dashboardMenu;

    @FindBy(xpath="//h2[text()='Decision Reason']")
    private WebElementFacade decisionReasonTableHeader;

//    @FindBy(xpath = "//li[@class='menu-item invest']")
//    private WebElementFacade sidebarInvestigation;
//    @FindBy(xpath = "//li//span[contains(text(),'Investigation')]")
//    private WebElementFacade sidebarInvestigation;
//    @FindBy(css = "css=.sm-invest")
//    private WebElementFacade sidebarInvestigation;
//
//    @FindBy(xpath = "//li[@class='menu-item blacklist']")
//    private WebElementFacade sidebarWatchlist;
//
//    @FindBy(xpath = "//li[@class='menu-item reports']")
//    private WebElementFacade sidebarReports;
//
//    @FindBy(xpath = "//li[@class='menu-item definitions']")
//    private WebElementFacade sidebarDefinitions;
//
//    @FindBy(xpath = "//li[@class='side-nav sm-rules']")
//    private WebElementFacade sidebarRules;
//
//    @FindBy(xpath = "//li[@class='menu-item settings']")
//    private WebElementFacade sidebarSettings;
//
//    @FindBy(xpath = "//li[@class='side-nav sm-utilities']")
//    private WebElementFacade sidebarUtilities;
//
//    @FindBy(xpath = "//li[@class='menu-item security']")
//    private WebElementFacade sidebarSecurity;

    @FindBy(css = ".sm-invest")
    private WebElementFacade investigationMenu;

    //@FindBy (css=".active > .sub-menu-item > .alp-accordion")
    @FindBy(xpath = "//button[text()='Applications']")
    private WebElementFacade investigationSubMenu;

    @FindBy(linkText = "Application Maintenance")
    private WebElementFacade applicationMaintenance;

    @FindBy(xpath = "//button[contains(text(), 'Applications')]")
    private WebElementFacade applications;

    @FindBy(css = ".-dropdown-dark")
    private WebElementFacade applicationMaintenanceDropdown;

    @FindBy(xpath = "//button[contains(text(),'Search')]")
    private WebElementFacade searchApplication;

    @FindBy(css = "button:nth-child(3)")
    private WebElementFacade newApplication;

    @FindBy(xpath = "   //button[contains(text(),'Upload Application File...')]")
    private WebElementFacade uploadApplicationPage;

    @FindBy(xpath = "//button[contains(text(),'New Watchlist')]")
    private WebElementFacade newWatchList;

    @FindBy(xpath = "//h2[contains(text(),'Application')]")
    private WebElementFacade newApplicationHeader;

    @FindBy(xpath = "//h2[contains(text(),'Watchlist')]")
    private WebElementFacade newWatchlistHeader;

    //Utilities SubMenu: Need to update the xpath other than systemImport as and when required

    @FindBy(xpath = "//li//a[contains(text(),'System Import')]")
    private WebElementFacade dataBaseCleanUP;

    @FindBy(xpath = "//li//a[contains(text(),'System Import')]")
    private WebElementFacade systemImport;

    @FindBy(xpath = "//li//a[contains(text(),'System Import')]")
    private WebElementFacade systemExport;

    @FindBy(xpath = "//li//a[contains(text(),'System Import')]")
    private WebElementFacade dashboardDisplay;

    // Sub Navigation Menu
    @FindBy(xpath = "//a[text()='Rule Sets']")
    private WebElementFacade subRuleSets;

    @FindBy(xpath = "//button[contains(text(),'Add')]")
    private WebElementFacade addButton;

    @FindBy(xpath = "//div[@class='add-rule-link linkstyle']/span")
    private WebElementFacade newCriteria;

    @FindBy(xpath = "//h2[text()='Add Criteria']")
    private WebElementFacade newCriteriaHeader;

    @FindBy(xpath = "//div[@class='-logo-small']")
    private WebElementFacade Logo;

    public void navigateToDashboard() {
        clickOn(Logo);
        waitABit(2000);
    }


    public void navigateToApplicationMaintenance() throws InterruptedException {
        waitFor(investigationMenu).click();
        waitABit(3000);
        waitFor(investigationSubMenu).click();
        waitABit(3000);
        waitFor(applicationMaintenance).click();
        waitABit(3000);
    }

    public void applicationMaintenanceDropdownSelection() {
        waitFor(ExpectedConditions.visibilityOf(applicationMaintenanceDropdown));
        waitFor(applicationMaintenanceDropdown).click();
        waitFor(newApplication).click();
        if(newApplication.isVisible()) Assert.fail("error displayed");
        waitFor(ExpectedConditions.visibilityOf(newApplicationHeader));
    }

    public void watchlistMaintenanceDropdownSelection() {
        waitFor(ExpectedConditions.visibilityOf(applicationMaintenanceDropdown));
        waitFor(applicationMaintenanceDropdown).click();
        waitFor(newWatchList).click();
        waitFor(ExpectedConditions.visibilityOf(newWatchlistHeader));
    }

    public void clickExpandSidebarMenu() {
        clickOn(menuToggle);
    }

    public void clickSidebarOption(String element) {
        switch (element.toUpperCase()) {
            case "DASHBOARD":
                clickOn(sidebarDashboard);
                break;
            case "INVESTIGATION":
                //  waitFor(sidebarInvestigation).click();
                clickOn(sidebarInvestigation);
                break;
            case "WATCHLIST":
                clickOn(sidebarWatchlist);
                break;
            case "REPORTS":
                clickOn(sidebarReports);
                break;
            case "DEFINITIONS":
                clickOn(sidebarDefinitions);
                break;
            case "RULES":
                clickOn(sidebarRules);
                break;
            case "SETTINGS":
                clickOn(sidebarSettings);
                break;
            case "UTILITIES":
                clickOn(sidebarUtilities);
                break;
            case "SECURITY":
                clickOn(sidebarSecurity);
                break;
            default:
                throw new NoSuchElementException("\"" + element + "\" element is not visible on page");
        }
    }

    public void navigateToPage(String requiredPage) {
        switch (requiredPage.toUpperCase()) {
            case "RULE SETS":
                waitFor(ExpectedConditions.visibilityOf(sidebarRules));
                clickOn(sidebarRules);
                clickOn(getDriver().findElement(By.xpath("//a[text()='Rule Sets']")));
                break;
            case "APPLICATION MAINTENANCE":
                waitFor(ExpectedConditions.visibilityOf(sidebarInvestigation));
                clickOn(sidebarInvestigation);
                clickOn(getDriver().findElement(By.xpath("//button[contains(text(), 'Applications')]")));
                clickOn(getDriver().findElement(By.xpath("//a[contains(text(), 'Application Maintenance')]")));
                break;
            case "APPLICATION REVIEW BATCH LOADS":
                waitFor(ExpectedConditions.visibilityOf(sidebarInvestigation));
                clickOn(sidebarInvestigation);
                clickOn(getDriver().findElement(By.xpath("//button[contains(text(), 'Applications')]")));
                clickOn(getDriver().findElement(By.xpath("//a[contains(text(), 'Application Review Batch Loads')]")));
                break;
            case "SYSTEM PARAMETERS":
                waitFor(ExpectedConditions.visibilityOf(sidebarSettings));
                clickOn(sidebarSettings);
                clickOn(getDriver().findElement(By.xpath("//a[contains(text(), 'System Parameters')]")));
                break;
            case "SECURITY PARAMETERS":
                waitFor(ExpectedConditions.visibilityOf(sidebarSecurity));
                clickOn(sidebarSecurity);
                clickOn(getDriver().findElement(By.xpath("//a[contains(text(), 'Security Parameters')]")));
                break;
            case "FIELD SETTINGS":
                waitFor(ExpectedConditions.visibilityOf(sidebarSettings));
                clickOn(sidebarSettings);
                clickOn(getDriver().findElement(By.xpath("//a[contains(text(), 'Field Settings')]")));
                break;
            case "CROSS CHECK FIELDS":
                waitFor(ExpectedConditions.visibilityOf(sidebarSettings));
                clickOn(sidebarSettings);
                clickOn(getDriver().findElement(By.xpath("//a[text()='Cross Check Fields']")));
                break;
            case "LINK ANALYSIS MAINTENANCE":
                waitFor(ExpectedConditions.visibilityOf(sidebarSettings));
                clickOn(sidebarSettings);
                clickOn(getDriver().findElement(By.xpath("//a[text()='Cross Check Fields']")));
//                waitFor(ExpectedConditions.visibilityOf(configurationPurpose));
                clickOn(configurationPurpose);
                waitFor(ExpectedConditions.visibilityOf(link_Analysis_Maintenance));
                clickOn(link_Analysis_Maintenance);
                break;
            case "NOTE PROMPT":
                waitFor(ExpectedConditions.visibilityOf(sidebarSettings));
                clickOn(sidebarSettings);
                clickOn(getDriver().findElement(By.xpath("//a[contains(text(), 'Note Prompt')]")));
                break;
            case "WATCHLIST MAINTENANCE":
                waitABit(2000);
                waitFor(ExpectedConditions.visibilityOf(sidebarWatchlist));
                clickOn(sidebarWatchlist);
                clickOn(getDriver().findElement(By.xpath("//a[contains(text(), 'Watchlist Maintenance')]")));
                waitABit(1000);
                break;
            case "WATCHLIST REVIEW BATCH LOADS":
                waitABit(2000);
                waitFor(ExpectedConditions.visibilityOf(sidebarWatchlist));
                clickOn(sidebarWatchlist);
                clickOn(getDriver().findElement(By.xpath("//a[contains(text(), 'Watchlist Review Batch Loads')]")));
                waitABit(1000);
                break;
            case "RULES CONFIGURATION":
                waitFor(ExpectedConditions.visibilityOf(sidebarRules));
                clickOn(sidebarRules);
                clickOn(getDriver().findElement(By.xpath("//a[contains(text(), 'Rules Configuration')]")));
                break;
            case "RULE ADMIN GROUPS":
                waitFor(ExpectedConditions.visibilityOf(sidebarRules));
                clickOn(sidebarRules);
                clickOn(find("//a[contains(text(), 'Rule Admin Groups')]"));
                break;
            case "RULE GROUPS":
                waitFor(ExpectedConditions.visibilityOf(sidebarRules));
                clickOn(sidebarRules);
                clickOn(find("//a[contains(text(), 'Rule Groups')]"));
                break;
            case "ALERT REVIEW":
                waitFor(ExpectedConditions.visibilityOf(sidebarInvestigation));
                clickOn(sidebarInvestigation);
                clickOn(getDriver().findElement(By.xpath("//button[contains(text(), 'Applications')]")));
                clickOn(getDriver().findElement(By.xpath("//a[contains(text(), 'Alert Review')]")));
                break;
            case "PROFILES":
                waitABit(2000);
                JavascriptExecutor jse = (JavascriptExecutor) getDriver();
                jse.executeScript("window.scrollBy(0,250)");
                clickOn(sidebarSecurity);
                clickOn(getDriver().findElement(By.xpath("//a[contains(text(), 'Profiles')]")));
                break;
            case "FRAUD ALERTS":
                waitFor(ExpectedConditions.visibilityOf(sidebarSettings));
                clickOn(sidebarSettings);
                clickOn(getDriver().findElement(By.xpath("//a[contains(text(), 'Fraud Alerts')]")));
                break;
            case "USERS":
                waitFor(ExpectedConditions.visibilityOf(sidebarSecurity));
                clickOn(sidebarSecurity);
                clickOn(getDriver().findElement(By.xpath("//a[contains(text(), 'Users')]")));
                break;
            case "APPLICATION SEARCH USING CRITERIA":
                waitFor(ExpectedConditions.visibilityOf(sidebarInvestigation));
                clickOn(sidebarInvestigation);
                clickOn(getDriver().findElement(By.xpath("//button[contains(text(), 'Applications')]")));
                clickOn(getDriver().findElement(By.xpath("//a[contains(text(), 'Application Search Using Criteria')]")));
                break;
            case "WATCHLIST SEARCH USING CRITERIA":
                waitFor(ExpectedConditions.visibilityOf(sidebarWatchlist));
                clickOn(sidebarWatchlist);
                clickOn(getDriver().findElement(By.xpath("//a[contains(text(), 'Watchlist Search Using Criteria')]")));
                break;
            case "CRITERIA":
                waitFor(ExpectedConditions.visibilityOf(sidebarDefinitions));
                clickOn(sidebarDefinitions);
                clickOn(getDriver().findElement(By.xpath("//a[text()='Criteria']")));
                break;
            case "ORGANISATION CHART":
                waitFor(ExpectedConditions.visibilityOf(sidebarDefinitions));
                clickOn(sidebarDefinitions);
                clickOn(getDriver().findElement(By.xpath("//a[text()='Organisation Chart']")));
                break;
            case "CUSTOMER MAINTENANCE":
                waitFor(ExpectedConditions.visibilityOf(sidebarInvestigation));
                clickOn(sidebarInvestigation);
                clickOn(getDriver().findElement(By.xpath("//button[contains(text(), 'Customers')]")));
                clickOn(getDriver().findElement(By.xpath("//a[contains(text(), 'Customer Maintenance')]")));
                break;
            case "SYSTEM EXPORT":
                waitFor(ExpectedConditions.visibilityOf(sidebarUtilities));
                clickOn(sidebarUtilities);
                clickOn(getDriver().findElement(By.xpath("//a[text()='System Export']")));
                break;
            case "SYSTEM IMPORT":
                waitFor(ExpectedConditions.visibilityOf(sidebarUtilities));
                clickOn(sidebarUtilities);
                clickOn(getDriver().findElement(By.xpath("//a[text()='System Import']")));
                break;

            case "REFERENCE MAINTENANCE":
                waitFor(ExpectedConditions.visibilityOf(sidebarRules));
                clickOn(sidebarRules);
                clickOn(getDriver().findElement(By.xpath("//button[text()='Reference Lists']")));
                clickOn(getDriver().findElement(By.xpath("//a[contains(text(), 'Reference Maintenance')]")));
                break;

            case "REPORTS":
                waitFor(ExpectedConditions.visibilityOf(sidebarUtilities));
                clickOn(sidebarReports);
                clickOn(getDriver().findElement(By.xpath("//a[contains(text(),'List Report')]")));
                break;
            case "SETTINGS":
                waitFor(ExpectedConditions.visibilityOf(sidebarSettings));
                clickOn(sidebarSettings);
                clickOn(getDriver().findElement(By.xpath("//button[contains(text(),'Action')]")));
                clickOn(getDriver().findElement(By.xpath("//a[contains(text(),'Action Settings')]")));
                break;
            case "DEFINITIONS":
                waitFor(ExpectedConditions.visibilityOf(sidebarDefinitions));
                clickOn(sidebarDefinitions);
                clickOn(getDriver().findElement(By.xpath("//a[contains(text(),'Classing Associations')]")));
                break;
            case "CLASSING ASSOCIATIONS":
                waitFor(ExpectedConditions.visibilityOf(sidebarDefinitions));
                clickOn(sidebarDefinitions);
                clickOn(getDriver().findElement(By.xpath("//li[@class='direct-menu-item']/a[text()='Classing Associations']")));
                waitABit(1000);
                waitFor(addButton).click();
                waitABit(1000);
                break;
            case "WHITE LIST":
                waitFor(ExpectedConditions.visibilityOf(sidebarDefinitions));
                clickOn(sidebarDefinitions);
                clickOn(getDriver().findElement(By.xpath("//li[@class='direct-menu-item']/a[text()='White List']")));
                waitFor(addButton);

                break;
            case "OUTPUT ALERT":
                waitFor(ExpectedConditions.visibilityOf(sidebarDefinitions));
                clickOn(sidebarDefinitions);
                clickOn(getDriver().findElement(By.xpath("//li[@class='direct-menu-item']/a[text()='Output Alert']")));
                waitABit(1000);
                waitFor(addButton).click();
                waitABit(1000);
                break;
            case "ORGANISATION":
                waitFor(ExpectedConditions.visibilityOf(sidebarSettings));
                clickOn(sidebarSettings);
                clickOn(getDriver().findElement(By.xpath("//a[text()='Organisation']")));
                waitABit(1000);

                break;

            case "ORGANISATION RULE SETS":
                waitFor(ExpectedConditions.visibilityOf(sidebarRules));
                clickOn(sidebarRules);
                clickOn(getDriver().findElement(By.xpath("//a[text()='Organisation Rule Sets']")));
                waitABit(1000);
                clickOn(getDriver().findElement(By.xpath("//div[@id='dropdownBasic2']//img[1]")));
                waitABit(1000);
                clickOn(getDriver().findElement(By.xpath("//li[contains(text(),'Change')]")));
                break;

            case "COMPANY AFFIX":
                waitFor(ExpectedConditions.visibilityOf(sidebarRules));
                clickOn(sidebarRules);
                clickOn(getDriver().findElement(By.xpath("//button[text()='Reference Lists']")));
                clickOn(getDriver().findElement(By.xpath("//a[text()='Company Affix']")));
                waitFor(ExpectedConditions.visibilityOf(companyAffix));
                break;
            case "CLASSING MAINTENANCE":
                waitFor(ExpectedConditions.visibilityOf(sidebarDefinitions));
                clickOn(sidebarDefinitions);
                waitABit(1000);
                clickOn(getDriver().findElement(By.xpath("//a[text()='Classing Associations']/following::a[1]")));
                waitABit(1000);
                waitFor(ExpectedConditions.visibilityOf(classingMaintenance));
                break;
            case "CRITERIA FIELDS":
                waitFor(ExpectedConditions.visibilityOf(sidebarDefinitions));
                clickOn(sidebarDefinitions);
                clickOn(getDriver().findElement(By.xpath("//li[@class='direct-menu-item']/a[text()='Criteria Fields']")));
                waitFor(ExpectedConditions.visibilityOf(CriteriaFields));
                break;
            case "INDEXED FUZZY MATCH MAPPING":
                waitFor(ExpectedConditions.visibilityOf(sidebarRules));
                clickOn(sidebarRules);
                clickOn(getDriver().findElement(By.xpath("//li[contains(@class,'direct-menu-item')]/a[text()='Indexed Fuzzy Match Mapping']")));
                waitFor(ExpectedConditions.visibilityOf(indexedFuzzyMatch));
                break;
            case "FIELD REQUIREMENTS":
                waitFor(ExpectedConditions.visibilityOf(sidebarSettings));
                clickOn(sidebarSettings);
                clickOn(getDriver().findElement(By.xpath("//a[text()='Field Requirements']")));
                waitFor(ExpectedConditions.visibilityOf(fieldRequirements));
                break;
            case "DECISION REASON":
                waitFor(ExpectedConditions.visibilityOf(sidebarSettings));
                clickOn(sidebarSettings);
                clickOn(getDriver().findElement(By.xpath("//button[contains(text(), 'Action')]")));
                clickOn(getDriver().findElement(By.xpath("//a[contains(text(), 'Decision Reason')]")));
                waitFor(ExpectedConditions.visibilityOf(decisionReasonTableHeader));
                break;
            case "DASHBOARD":
                waitABit(1000);
                clickOn(getDriver().findElement(By.xpath("//span[contains(text(),'Dashboard')]")));
                waitFor(ExpectedConditions.visibilityOf(dashboardMenu));
                break;
            case "DASHBOARD DISPLAY":
                waitABit(1000);
                waitFor(ExpectedConditions.visibilityOf(sidebarUtilities));
                clickOn(sidebarUtilities);
                clickOn(getDriver().findElement(By.xpath("//a[text()='Dashboard Display']")));
                break;

            default:
                throw new NoSuchElementException("\"" + requiredPage + "\" is not visible on page");
        }
    }

    public void clickSidebarSubMenuOption(String element) {
        switch (element.toUpperCase()) {
            case "APPLICATION RULES":
                waitFor(ExpectedConditions.visibilityOf(sidebarRules));
                clickOn(sidebarRules);
                clickOn(getDriver().findElement(By.xpath("//a[text()='Rule Sets']")));
                break;
            case "APPLICATION MAINTENANCE":
                waitFor(ExpectedConditions.visibilityOf(sidebarInvestigation));
                clickOn(sidebarInvestigation);
                clickOn(getDriver().findElement(By.xpath("//button[contains(text(), 'Applications')]")));
                clickOn(getDriver().findElement(By.xpath("//a[contains(text(), 'Application Maintenance')]")));
                break;
            case "SYSTEM PARAMETERS":
//                waitFor(ExpectedConditions.visibilityOf(sidebarSettings));
//                clickOn(sidebarSettings);
                clickOn(getDriver().findElement(By.xpath("//a[contains(text(), 'System Parameters')]")));
                break;
            case "WATCHLIST MAINTENANCE":
                waitFor(ExpectedConditions.visibilityOf(sidebarWatchlist));
                clickOn(sidebarWatchlist);
                clickOn(getDriver().findElement(By.xpath("//a[contains(text(), 'Watchlist Maintenance')]")));
                break;

            case "USERS":
                clickOn(getDriver().findElement(By.xpath("//a[contains(text(), 'Users')]")));
                break;

            default:
                throw new NoSuchElementException("\"" + element + "\" is not visible on page");
        }
    }

    public void clickWatchlist() {
        waitFor(ExpectedConditions.visibilityOf(sidebarWatchlist));
        clickOn(sidebarWatchlist);
    }

    public void clickUtilitiesSubMenu(String element) {
        switch (element) {

            case "dataBaseCleanUp":
                clickOn(dataBaseCleanUP);
                break;

            case "systemImport":
                clickOn(systemImport);
                break;

            case "systemExport":
                clickOn(systemExport);
                break;

            case "dashboardDisplay":
                clickOn(dashboardDisplay);
                break;

            default:
                throw new NoSuchElementException("\"" + element + "\" element is not visible on page");
        }
    }

    public void applicationPage(String Page) {
        switch (Page.toUpperCase()) {
            case "APPLICATION":
                waitFor(ExpectedConditions.visibilityOf(applicationMaintenanceDropdown));
                waitFor(applicationMaintenanceDropdown).click();
                waitFor(uploadApplicationPage).click();

        }
    }

    public void navigateTo(String requiredPage) {
        switch (requiredPage.toUpperCase()) {
            case "APPLICATION":
                waitFor(ExpectedConditions.visibilityOf(applicationMaintenanceDropdown));
                waitFor(applicationMaintenanceDropdown).click();
                waitFor(newApplication).click();
                waitFor(ExpectedConditions.visibilityOf(newApplicationHeader));
                break;
            case "SETTINGS":
                waitFor(ExpectedConditions.visibilityOf(applicationMaintenanceDropdown));
                waitFor(applicationMaintenanceDropdown).click();
                waitFor(newApplication).click();
                waitFor(ExpectedConditions.visibilityOf(newApplicationHeader));
                break;
            case "WATCHLIST":
                waitFor(ExpectedConditions.visibilityOf(applicationMaintenanceDropdown));
                waitFor(applicationMaintenanceDropdown).click();
                waitFor(newWatchList).click();
                waitFor(ExpectedConditions.visibilityOf(newWatchlistHeader));
                break;
            case "WATCHLIST-SEARCH":
                waitABit(5000);
                clickOn(searchApplication);
                waitABit(3000);
                waitFor(ExpectedConditions.visibilityOf(newWatchlistHeader));
                break;
            case "APPLICATION-SEARCH":
                waitFor(ExpectedConditions.visibilityOf(searchApplication));
                waitABit(3000);
                clickOn(searchApplication);
                waitABit(3000);
                waitFor(ExpectedConditions.visibilityOf(newApplicationHeader));
                break;
            case "CRITERIA":
                waitFor(ExpectedConditions.visibilityOf(addButton));
                waitABit(1000);
                waitFor(addButton).click();
                waitABit(1000);
                waitFor(newCriteria).click();
                waitFor(ExpectedConditions.visibilityOf(newCriteriaHeader));
                break;
            default:
                throw new NoSuchElementException("\"" + requiredPage + "\" is not visible on page");
        }
    }
}
