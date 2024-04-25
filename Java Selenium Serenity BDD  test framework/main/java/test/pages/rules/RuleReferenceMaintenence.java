package com..test.pages.rules;

import com..test.pages.BasePage;
import net.serenitybdd.core.annotations.findby.By;
import net.serenitybdd.core.annotations.findby.FindBy;
import net.serenitybdd.core.pages.WebElementFacade;
import net.thucydides.core.pages.PageObject;
import org.junit.Assert;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class RuleReferenceMaintenence extends BasePage {
    private PageObject page;

    @FindBy(xpath = "//button[contains (text(), 'Yes')]")
    private WebElementFacade yesButton;

    @FindBy(xpath = "//button[contains(text(),'Save')]")
    private WebElementFacade saveTable;

    @FindBy(xpath = "//button[contains(text(),'Add')]")
    private WebElementFacade addButton;

    @FindBy(xpath = "(//button[contains(text(),'Add')])[2]")
    private WebElementFacade addButtonForNewTable;

    @FindBy(xpath = "//*[@id='TableId']")
    private WebElementFacade tableIdDropdown;

    @FindBy(xpath = "//*[@id='TableId']/div/div/div/input")
    private WebElementFacade tableIdDropdownInput;

    @FindBy(xpath = "//*[@class='ng-dropdown-panel-items scroll-host']")
    private WebElementFacade selectTableid;

    @FindBy(xpath = "//*[@id='FileType']")
    private WebElementFacade fileTypeDropdown;

    @FindBy(xpath = "//*[@id='FileType']/div/div/div/input")
    private WebElementFacade fileTypeDropdownInput;

    @FindBy(xpath = "//*[@id='TableName']")
    private WebElementFacade tableNAme;

    @FindBy(xpath = "(//td[text()='Test']/following::td)[3]")
    private WebElementFacade createdTableEllipsis;

    @FindBy(xpath = "(//li[text()='Change'])[last()]")
    private WebElementFacade optionsChangeEllipsis;

    @FindBy(xpath = "(//li[text()='Delete'])[last()]")
    private WebElementFacade optionsDeleteEllipsis;

    @FindBy(xpath = "//input[@id='FieldName']")
    private WebElementFacade fieldName;

    @FindBy(xpath = "//input[@id='Description']")
    private WebElementFacade fieldDescription;

    @FindBy(xpath = "(//*[@id='ValueType']/div//following::span[@class='ng-arrow-wrapper'])[1]")
    private WebElementFacade fieldValueDropDown;

    @FindBy(xpath = "//div[text()='int']")
    private WebElementFacade fieldValue;

    @FindBy(xpath = "//button[text()='Add Field']")
    private WebElementFacade addFieldButton;

    @FindBy(xpath = "//button[contains(@class,'dropdown-toggle')]")
    private WebElementFacade addFieldDropdown;

    @FindBy(xpath = "//h2[text()='Table Definition']")
    private WebElementFacade tableDefinationHeader;

    @FindBy(xpath = "//h2[text()='Reference Maintenance']")
    private WebElementFacade referenceMaintenanceHeader;

    @FindBy(xpath = "//input[@id='Length']")
    private WebElementFacade lengthFiled;

    @FindBy(xpath = "//textarea[@id='Calculation']")
    private WebElementFacade claculationFiled;

    @FindBy(xpath = "//input[@id='Active']/../label/div")
    private WebElementFacade toggleButtonActive;

    @FindBy(xpath = "//input[@id='Identifier']/../label/div ")
    private WebElementFacade toggleButtonIdentifier;

    @FindBy(xpath = "//label[@class='alp-bonito-expand-label css-label']")
    private WebElementFacade dropDownCal;

    @FindBy(xpath = "//div[@class='inner-panel-expanded-row']/div/div")
    private WebElementFacade textCal;

    @FindBy(xpath = "//div[@class='can-toggle__switch']")
    private WebElementFacade firstRowHeaderCheckBox;

   @FindBy(xpath = "//button[contains(text(), ' Filter ')]")
   public WebElementFacade filterButton;

    @FindBy(xpath = "//app-header-label[contains(text(), ' Table Id')]/following::input")
    public WebElementFacade tableIdInput;

    @FindBy(xpath = "//app-header-label[contains(text(), ' Table Name')]/following::input")
    public WebElementFacade tableNameInput;

    @FindBy(xpath = "//app-header-label[contains(text(), ' File Type')]/following::input")
    public WebElementFacade fileTypeInput;

    @FindBy(xpath = "//span[@class='badge-count']")
    public WebElementFacade itemSelectedCount;

    @FindBy(xpath = "//app-header-label[contains(text(), 'Clear All')]")
    public WebElementFacade clearAll;

    @FindBy(xpath = "//button[contains(text(), 'Apply')]")
    public WebElementFacade filterApplyButton;

    @FindBy(xpath = "//div[@class='btn-group dropdown']")
    public WebElementFacade addButtonDropDown;

    @FindBy(xpath =  "//button[contains(text(),'Save')]")
    public WebElementFacade addButtonDropDownSave;

    @FindBy(xpath =  "//button[contains(text(),'Table Status')]")
    public WebElementFacade addButtonDropDownTableStatus;

    @FindBy(xpath =  "//button[contains(text(),' Table Status ')]")
    public WebElementFacade tableStatusWindowHeader;

    @FindBy(xpath = "//button[contains(text(), ' Filter ')]")
    public WebElementFacade tableStatusFilterButton;

    @FindBy(xpath = "//div[contains(text(), ' Print ')]")
    public WebElementFacade tableStatusPrint;

    @FindBy(xpath = "//app-ellipsis-menu")
    public WebElementFacade tableDropdownMenu;

    @FindBy(xpath = "//li[contains(text(), 'Change')]")
    public WebElementFacade tableDropdownMenuChange;

    @FindBy(xpath = "//li[contains(text(), 'View')]")
    public WebElementFacade tableDropdownMenuView;

    @FindBy(xpath = "//li[contains(text(), 'Clone')]")
    public WebElementFacade tableDropdownMenuClone;

    @FindBy(xpath = "//li[contains(text(), 'Delete')]")
    public WebElementFacade tableDropdownMenuDelete;

    @FindBy(xpath = "//li[contains(text(), 'Generate Template')]")
    public WebElementFacade tableDropdownMenuGenerateTemplate;

    @FindBy(xpath = "//button[contains(text(), 'Apply')]")
    public WebElementFacade tableStatusFilterApplyButton;

    public void addANewTable(String tableId,String tableName,String fileType)  {
        //waitFor(ruleConfigurationHeader);
       // clickOn(addButton);
        clickOn(tableIdDropdown);
        typeInto(tableIdDropdownInput,tableId);
        clickOn(selectTableid);
        typeInto(tableNAme,tableName);
        clickOn(fileTypeDropdown);
        typeInto(fileTypeDropdownInput,fileType);
        getDriver().findElement(By.xpath("//div[contains(text(),'" + fileType + "')]")).click();
        //clickOn(selectTableid);
        clickOn(firstRowHeaderCheckBox);
        clickOn(addButtonForNewTable);
        waitABit(1000);

    }

    public void clickOnChange() {
        createdTableEllipsis.click();
        waitABit(500);
        optionsChangeEllipsis.click();
        waitABit(1000);
    }

    public void clickOnDelete() {
        createdTableEllipsis.click();
        waitABit(500);
        optionsDeleteEllipsis.click();
        waitABit(1000);
        yesConfirmationButton();
    }

    public void iAddAFiledTypeWithActiveCheckAsGiven(String filedName,String activeToggle,String valueType,String CalSyntax){
        typeInto(fieldName,filedName);
        typeInto(fieldDescription,filedName);
        if(valueType.equals("nvarchar")){
            typeInto(lengthFiled,"max");
        }
        if(!valueType.equals("nvarchar")) {
            clickOn(fieldValueDropDown);
            WebElement val = getDriver().findElement(By.xpath("//div[text()='" + valueType + "']"));
            val.click();
        }
        if(activeToggle.equals("No")){
            clickOn(toggleButtonIdentifier);
            clickOn(toggleButtonActive);
            typeInto(claculationFiled,CalSyntax);
        }
        clickOn(addButtonForNewTable);
    }

    public void clickAddField()  {
        waitFor(tableDefinationHeader);
        clickOn(addFieldButton);
    }

    public void saveReferenceTable()  {
        waitFor(referenceMaintenanceHeader);
        clickOn(addFieldDropdown);
        clickOn(saveTable);
        clickOn(addFieldDropdown);
    }

    public void saveTable() {
        waitABit(1000);
        waitFor(ExpectedConditions.visibilityOf(saveTable));
        clickOn(saveTable);

    }

    public void clickAdd()  {
        waitFor(referenceMaintenanceHeader);
        clickOn(addButton);
    }
    public void verifyTableContent(String expectedData) {
        //this should at step file
        waitABit(1000);
        waitFor(ExpectedConditions.visibilityOf(dropDownCal));
        clickOn(dropDownCal);
        String ele=getDriver().findElement(By.xpath("//div[@class='inner-panel-expanded-row']/div/div")).getText().trim();
        Assert.assertEquals(ele,expectedData);
        clickOn(dropDownCal);

    }

    public void yesConfirmationButton() {
        waitFor(ExpectedConditions.visibilityOf(yesButton));
        clickOn(yesButton);
    }

    public void clickAddButtonDropDownSaveButton(){
        clickOn(addButtonDropDown);
        clickOn(addButtonDropDownSave);
    }

    public void clickAddButtonDropDownTableStatusButton(){
        clickOn(addButtonDropDown);
        clickOn(addButtonDropDownTableStatus);
    }


}

