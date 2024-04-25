package com..test.pages.definitions;

import com..test.pages.BasePage;
import net.serenitybdd.core.annotations.findby.FindBy;
import net.serenitybdd.core.pages.WebElementFacade;
import org.openqa.selenium.By;

public class OrganisationChart extends BasePage {

    //ORGANISATION CHART PAGE
    @FindBy(xpath = "//h2[text()='Organisation Chart']")
    private WebElementFacade orgChartHeading;

    @FindBy(xpath = "//button-drop-down[contains(@id,'action-button')]//button[contains(text(),'Team Maintenance')]")
    private WebElementFacade teamMaintenanceButton;

    //TEAM MAINTENANCE POPUP
    @FindBy(xpath = "//ng-select[contains(@id,'action-form__teamName-selected-field')]//input")
    private WebElementFacade teamNameDropDownInPopup;

    @FindBy(xpath = "//button[@id='action-button']//span[text()='OK']")
    private WebElementFacade okButtonInTeamMaintenancePopup;

    public void clickTeamMaintenanceButton() { clickOn(teamMaintenanceButton); }

    public void enterTeamNameInTeamMaintenancePopup(String teamName) { teamNameDropDownInPopup.typeAndEnter(teamName); }

    public WebElementFacade selectOrgChartNode(String nodeName)
    {
        WebElementFacade nodeElement = find(By.xpath("//tree-node-content/span[contains(text(),'"+nodeName+"')]"));
        clickOn(nodeElement);
        return nodeElement;
    }

    public void addTeamToOrgChart(String node, String team)
    {
        WebElementFacade nodeElement = selectOrgChartNode(node);
        WebElementFacade addElement =
                find(By.xpath("//tree-node-content/span[contains(text(),'"+nodeElement.getText()+"')]/ancestor::div[1]/following-sibling::div//span[@appresource='OrganisationChart.Ref_Context_Add']/../img"));
        waitFor(addElement).waitUntilVisible();
        clickOn(addElement);
        enterTeamNameInTeamMaintenancePopup(team);
        waitFor(okButtonInTeamMaintenancePopup).waitUntilClickable();
        clickOn(okButtonInTeamMaintenancePopup);
        waitFor(orgChartHeading).waitUntilClickable();
    }


}
