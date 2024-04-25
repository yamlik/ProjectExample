package com..test.pages.settings;

import com..test.context.constants.ApplicationConstants;
import com..test.pages.BasePage;
import net.serenitybdd.core.annotations.findby.FindBy;
import net.serenitybdd.core.pages.WebElementFacade;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.Color;

import static com..test.context.constants.ApplicationConstants.*;

public class SystemParametersPage extends BasePage {
    @FindBy(xpath = "//button[contains(text(),'Save')]")
    private WebElementFacade saveButton;

    @FindBy(xpath = "//div[contains(text(),'Client Parameters')]")
    private WebElementFacade pageTitle;

    @FindBy(xpath = "//div[contains(@class,'alert-dismissable')]")
    private WebElementFacade suTSNTssAlert;

    @FindBy(xpath = "//div[text()='Display Mode']/../following::div[1]/ng-select/div/div/div[@class='ng-input']/input")
    private WebElementFacade displayModeDropdown;

    @FindBy(xpath = "//div[contains(text(),'Rules Run In Parallel')]//following::input")
    private WebElementFacade Rules_Run_In_Parallel;

    @FindBy(xpath = "//div[contains(text(),' Server Parameters')]")
    private WebElementFacade serverParameterTab;
    @FindBy(xpath = "//div[contains(text(),'Workflow Settings')]")
    private WebElementFacade workflowSettingsTab;

    @FindBy(xpath = "//div[contains(text(),'Client - Manual Workflow')]//../following-sibling::div//input")
    private WebElementFacade clientManualWorkflow;


    public void toggleParameter(String toggle, String parameter) {

        waitFor(pageTitle);
        WebElement parameterElement = getDriver().findElement(By.xpath("//div[contains(text(),'" + parameter + "')]/../../div/span/label/span"));
        if (((Color.fromString(parameterElement.getCssValue("background-color")).asHex().equals(ApplicationConstants.toggleOnColour)) && toggle.equals("OFF")) ||
                ((Color.fromString(parameterElement.getCssValue("background-color")).asHex().equals(ApplicationConstants.toggleOffColour)) && toggle.equals("ON"))) {
            ((JavascriptExecutor) getDriver()).executeScript("arguments[0].scrollIntoView(true);", parameterElement);
            waitABit(2000);
            ((JavascriptExecutor) getDriver()).executeScript("javascript:window.scrollBy(0,-100)");
            waitABit(2000);
            parameterElement.click();
        }
    }

    public void changingDisplayMode(String displayMode) {
        try {
            waitFor(pageTitle);
            ((JavascriptExecutor) getDriver()).executeScript("arguments[0].scrollIntoView(true);", displayModeDropdown);
            waitABit(2000);
            ((JavascriptExecutor) getDriver()).executeScript("javascript:window.scrollBy(0,-100)");
            waitABit(2000);
            clickOn(displayModeDropdown);
            WebElement dropdownElement = getDriver().findElement(By.xpath("(//span[contains(text(),'" + displayMode + "')])"));
            dropdownElement.click();
            clickOn(saveButton);
            //  waitFor(suTSNTssAlert);
            waitABit(3000);

        } catch (Exception e) {
            e.getMessage();
        }
    }

    public void clickSave() {
        clickOn(saveButton);
        waitFor(suTSNTssAlert).waitUntilNotVisible();
    }

    public void enter_Value_in_Rules_Run_In_Parallel(String Value) {
        ((JavascriptExecutor) getDriver()).executeScript("window.scrollBy(0,)", Rules_Run_In_Parallel);
//        ((JavascriptExecutor) getDriver()).executeScript("arguments[0].scrollIntoView(true);", Rules_Run_In_Parallel);waitABit(2000);

        Rules_Run_In_Parallel.clear();
        typeInto(Rules_Run_In_Parallel, Value);
    }

    public void clickServerParameterTab() {
        clickOn(serverParameterTab);
    }

    public void enableUserField2(String toggle, String parameter) {
        waitFor(serverParameterTab);
        WebElement parameterElement = getDriver().findElement(By.xpath("//div[contains(text(),'" + parameter + "')]/../../div/span/label/span"));
        if (((Color.fromString(parameterElement.getCssValue("background-color")).asHex().equals(ApplicationConstants.toggleOnColour)) && toggle.equals("OFF")) ||
                ((Color.fromString(parameterElement.getCssValue("background-color")).asHex().equals(ApplicationConstants.toggleOffColour)) && toggle.equals("ON"))) {
            ((JavascriptExecutor) getDriver()).executeScript("arguments[0].scrollIntoView(true);", parameterElement);
            waitABit(2000);
            ((JavascriptExecutor) getDriver()).executeScript("javascript:window.scrollBy(0,-100)");
            waitABit(2000);
            parameterElement.click();
        }
    }

    public void enableUserField3(String toggle, String parameter) {
        waitFor(serverParameterTab);
        WebElement parameterElement = getDriver().findElement(By.xpath("//div[contains(text(),'" + parameter + "')]/../../div/span/label/span"));
        if (((Color.fromString(parameterElement.getCssValue("background-color")).asHex().equals(ApplicationConstants.toggleOnColour)) && toggle.equals("OFF")) ||
                ((Color.fromString(parameterElement.getCssValue("background-color")).asHex().equals(ApplicationConstants.toggleOffColour)) && toggle.equals("ON"))) {
            ((JavascriptExecutor) getDriver()).executeScript("arguments[0].scrollIntoView(true);", parameterElement);
            waitABit(2000);
            ((JavascriptExecutor) getDriver()).executeScript("javascript:window.scrollBy(0,-100)");
            waitABit(2000);
            parameterElement.click();
        }
    }

    public void enableUserField7(String toggle, String parameter) {
        waitFor(serverParameterTab);
        WebElement parameterElement = getDriver().findElement(By.xpath("//div[contains(text(),'" + parameter + "')]/../../div/span/label/span"));
        if (((Color.fromString(parameterElement.getCssValue("background-color")).asHex().equals(ApplicationConstants.toggleOnColour)) && toggle.equals("OFF")) ||
                ((Color.fromString(parameterElement.getCssValue("background-color")).asHex().equals(ApplicationConstants.toggleOffColour)) && toggle.equals("ON"))) {
            ((JavascriptExecutor) getDriver()).executeScript("arguments[0].scrollIntoView(true);", parameterElement);
            waitABit(2000);
            ((JavascriptExecutor) getDriver()).executeScript("javascript:window.scrollBy(0,-100)");
            waitABit(2000);
            parameterElement.click();
        }
    }

    public void enableUserField10(String toggle, String parameter) {
        waitFor(serverParameterTab);
        WebElement parameterElement = getDriver().findElement(By.xpath("//div[contains(text(),'" + parameter + "')]/../../div/span/label/span"));
        if (((Color.fromString(parameterElement.getCssValue("background-color")).asHex().equals(ApplicationConstants.toggleOnColour)) && toggle.equals("OFF")) ||
                ((Color.fromString(parameterElement.getCssValue("background-color")).asHex().equals(ApplicationConstants.toggleOffColour)) && toggle.equals("ON"))) {
            ((JavascriptExecutor) getDriver()).executeScript("arguments[0].scrollIntoView(true);", parameterElement);
            waitABit(2000);
            ((JavascriptExecutor) getDriver()).executeScript("javascript:window.scrollBy(0,-100)");
            waitABit(2000);
            parameterElement.click();
        }
    }

    public void verifyInputMappingParameters() {
        String checkbox = getDriver().findElement(By.xpath("//div[contains(text(),'"+ApplicationConstants.savingsAmountField+"')]/../../div/span/label/span")).getAttribute("checked");
        String checkbox1 = getDriver().findElement(By.xpath("//div[contains(text(),'"+ accountNumber+"')]/../../div/span/label/span")).getAttribute("checked");
        String checkbox2 = getDriver().findElement(By.xpath("//div[contains(text(),'"+ nature_Of_Fraud+"')]/../../div/span/label/span")).getAttribute("checked");
        String checkbox3 = getDriver().findElement(By.xpath("//div[contains(text(),'"+ incomeField+"')]/../../div/span/label/span")).getAttribute("checked");

        if (checkbox == null && checkbox1 == null && checkbox2 == null && checkbox3 == null) {
            Assert.assertTrue(true);

        } else {
            Assert.assertTrue("Input Parameters are not enabled", false);
        }
    }

    public void clickWorkflowSettingsTab() {
        clickOn(workflowSettingsTab);
    }



    public void enterManualWorkflowClient(String manualWorkflow) {
        waitFor(workflowSettingsTab);
        clickOn(clientManualWorkflow);
        WebElement selectedWorkflow = getDriver().findElement(By.xpath("//span[contains(text(),'"+manualWorkflow+"')]"));
        selectedWorkflow.click();
        clickOn(saveButton);
        waitABit(3000);

    }
}
