package com..test.pages.utilities;

import com..test.pages.BasePage;
import com..test.utilities.JsHelper;
import net.serenitybdd.core.annotations.findby.FindBy;
import net.serenitybdd.core.pages.WebElementFacade;
import net.thucydides.core.webdriver.exceptions.ElementShouldBeEnabledException;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.w3c.dom.xpath.XPathResult;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathEvaluationResult;
import java.util.List;

public class SystemExport extends BasePage {

    JsHelper jsHelper = new JsHelper();

    @FindBy(xpath = "//button[contains(@class, 'actionButtons')]")
    private WebElementFacade exportButton;

    @FindBy(xpath = "//label[@for='rowTitleCheckBox']")
    public WebElementFacade selectAllCheckBox;

    @FindBy(xpath = "//label[contains(@for,'ProcessManagerMaintenance')]")
    private WebElementFacade workflowManagerCheckbox;

    @FindBy(xpath = "//h2[text()='System Export']")
    private WebElementFacade pageHeading;

    @FindBy(xpath = "//button[text()='Select Specific Records']")
    private WebElementFacade selectRecordsButton;

    //SELECT RECORDS POPUP
    @FindBy(xpath = "//app-sub-selection-export-records")
    private WebElementFacade selectPopup;

    @FindBy(xpath = "//button[contains(text(),'OK')]")
    private WebElementFacade okButton;

    @FindBy(xpath = "//div[contains(@class,'modal-content')]//button[contains(@class,'pagination-action-button')][2]")
    private WebElementFacade rightPageButtonInSelectPopup;

    // FILTER POPUP
    @FindBy(xpath = "(//div[contains(@class,'modal-content')]//input)[1]")
    private WebElementFacade productUnderTestDataFieldInFilterPopup;

    // Web Elements by String
    String workflowManagerHiddenCheckBoxId="ProcessManagerMaintenance";
    public String selectAllHiddenCheckbox="rowTitleCheckBox";

    Actions action;

    public void clickExportButton()
    {
        clickOn(exportButton);
        waitABit(2000);
    }

    public void enterproductUnderTestDataInFilterPopup(String data) { typeInto(productUnderTestDataFieldInFilterPopup, data);  }

    public void clickOkButtonOnSelectPopup() {
        clickOn(okButton);
        waitFor(pageHeading).waitUntilClickable();
    }

    public void clickSelectAllCheckbox() { clickOn(selectAllCheckBox); }

    public void clickSelectRecordsButton() {
        clickOn(selectRecordsButton);
        waitFor(selectPopup).waitUntilVisible();

    }

    public void clickImportData(String choice, String productUnderTestData) throws InterruptedException {
        String isChecked;

        switch (productUnderTestData.toUpperCase()){
            case "WORKFLOW MANAGER":
                isChecked = jsHelper.getPropertyThatContainsId(getDriver(), workflowManagerHiddenCheckBoxId, "checked");
                if((isChecked.equals("true") && choice.equals("de-select")) || (isChecked.equals("false") && choice.equals("select"))) {
                    workflowManagerCheckbox.click();
                }
                break;
        }
    }

    public void selectOrDeselect(String choice, String data) {
        action = new Actions(getDriver());
        waitFor(selectPopup);
        while (true) {
            try {
                WebElement selectCheckBox = find(By.xpath("//div[contains(@class,'modal-content')]//tr/td[normalize-space()='" + data + "']/preceding-sibling::td"));
                WebElement selectHiddenCheckBox = find(By.xpath("//div[contains(@class,'modal-content')]//tr/td[normalize-space()='" + data + "']/preceding-sibling::td//input"));
                String isChecked = selectHiddenCheckBox.getAttribute("checked");

                if ((choice.equals("deselect") && isChecked.equals("true")) || (choice.equals("select") && isChecked.equals("false"))) {
                    action.moveToElement(selectCheckBox).perform();
                    clickOn(selectCheckBox);
                    break;
                }
            }
            catch (Exception ex) {
                if(rightPageButtonInSelectPopup.isCurrentlyEnabled())
                    clickOn(rightPageButtonInSelectPopup);
                else
                    Assert.fail("Could not find to select: "+data);
            }
        }
    }

    public void verifySelectOrDeselect(String choice, String data) {
        waitFor(selectPopup);
        while(true) {
                List<WebElementFacade> allHiddenCheckboxes = findAll(By.xpath("//div[contains(@class,'modal-content')]//tr/td[1]//input"));
                List<WebElementFacade> firstColumn = findAll(By.xpath("//div[contains(@class,'modal-content')]//tr/td[2]"));

                for (int index = 0; index < firstColumn.size(); index++) {

                    //Verifies that in deselect option, the checkbox is unchecked for the 'data' and checked for all others
                    if (choice.equals("deselect")) {
                        if (firstColumn.get(index).getText().equals(data)) {
                            if(jsHelper.getPropertyById(getDriver(), allHiddenCheckboxes.get(index).getAttribute("id"), "checked").equals("true"))
                                Assert.fail("Selection of records is not as expected");
                        } else {
                            if(jsHelper.getPropertyById(getDriver(), allHiddenCheckboxes.get(index).getAttribute("id"), "checked").equals("false"))
                                Assert.fail("Selection of records is not as expected");
                        }
                    }
                    //Verifies that in deselect option, the checkbox is checked for the 'data' and unchecked for all others
                    else {
                        if (firstColumn.get(index).getText().equals(data)) {
                            if (jsHelper.getPropertyById(getDriver(), allHiddenCheckboxes.get(index).getAttribute("id"), "checked").equals("false"))
                                Assert.fail("Selection of records is not as expected");
                        } else {
                            if (jsHelper.getPropertyById(getDriver(), allHiddenCheckboxes.get(index).getAttribute("id"), "checked").equals("true"))
                                Assert.fail("Selection of records is not as expected");
                        }
                    }
                }
                if(rightPageButtonInSelectPopup.isCurrentlyEnabled())
                    clickOn(rightPageButtonInSelectPopup);
                else
                    break;
            }
        }
}
