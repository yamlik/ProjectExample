package com..cucumber.uisteps.definitions;

import com..cucumber.uisteps.BaseUISteps;
import com..test.modules.definitions.OrganisationChartModule;
import com..test.modules.definitions.TeamModule;
import com..test.pages.definitions.OrganisationChart;
import com..test.pages.definitions.TeamMaintenanceAssignmentPage;
import cucumber.api.PendingException;
import cucumber.api.java.en.And;
import org.json.simple.parser.ParseException;

import java.io.IOException;

public class TeamMaintenanceAssignmentSteps extends BaseUISteps {

    TeamMaintenanceAssignmentPage teamMaintenanceAssignmentPage;
    OrganisationChart organisationChartPage;
    OrganisationChartModule organisationChartModule = new OrganisationChartModule();
    TeamModule teamModule = new TeamModule();


    @And("^I add a Team \"([^\"]*)\"$")
    public void iAddATeam(String team) {
        organisationChartPage.clickTeamMaintenanceButton();
        teamMaintenanceAssignmentPage.clickAddButton();
        teamMaintenanceAssignmentPage.enterTeamName(team);
    }

    @And("^I assign \"([^\"]*)\" users to the Team with (\\d+) Weight and Out Of Office Turned \"([^\"]*)\"$")
    public void iAssignUsersToTheTeamWithWeightAndOutOfOfficeTurned(String user, String weight, String ooo) {
        teamMaintenanceAssignmentPage.enterWeightInAvailableUser(weight,user);
        teamMaintenanceAssignmentPage.selectOutOfOfficeInAvailableUsersGrid(ooo, user);
        teamMaintenanceAssignmentPage.moveAvailableUserToAssignedUser(user);
    }

    @And("^I give \"([^\"]*)\" ATSNTss to new Team with Action \"([^\"]*)\" and Reassign \"([^\"]*)\"$")
    public void iGiveATSNTssToNewTeamWithActionAndReassign(String permission, String actionPermission, String reassignPermission) {
        switch (permission) {
            case "Same Team":
                teamMaintenanceAssignmentPage.actionSameTeam(actionPermission);
                teamMaintenanceAssignmentPage.reassignSameTeam(reassignPermission);
                break;
        }
        teamMaintenanceAssignmentPage.clickSaveButton();
    }

    @And("^I add the child team \"([^\"]*)\" to the parent team \"([^\"]*)\" in the Organisation Chart$")
    public void iAddTheChildTeamToTheParentTeamInTheOrganisationChart(String childTeam, String parentTeam){
        organisationChartPage.addTeamToOrgChart(parentTeam,childTeam);
    }

    @And("^I Clear the Organisation Chart$")
    public void iClearTheOrganisationChart() throws IOException, ParseException {
        organisationChartModule.clearOrgChart();
    }

    @And("^I Delete the Team \"([^\"]*)\"$")
    public void iDeleteTheTeam(String team)  {
        teamModule.deleteTeam(team);
    }
}
