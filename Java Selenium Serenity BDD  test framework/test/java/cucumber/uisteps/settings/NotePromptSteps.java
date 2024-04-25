package com..cucumber.uisteps.settings;

import com..cucumber.uisteps.BaseUISteps;
import com..test.pages.application.ApplicationRecordPage;
import com..test.pages.settings.NotePromptPage;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.jetbrains.annotations.NotNull;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.sql.SQLException;

public class NotePromptSteps extends BaseUISteps {

    NotePromptPage notePromptPage;
    ApplicationRecordPage applicationRecordPage;

    @And("^I add a new Note Prompt$")
    public void iAddANewNotePrompt() throws IOException, ParseException {
        notePromptPage.clickAddButton();

        JSONObject dataDrive = scenarioContext.getDataDriveSectionAsJSON("noteFieldsToSelect");
        for(int i=1;i<=dataDrive.size();i++) {
            notePromptPage.enterAvailableFieldsList(dataDrive.get("Note Prompt "+i).toString());
            notePromptPage.clickAddFieldButton();
        }
        dataDrive = scenarioContext.getDataDriveSectionAsJSON("notePromptToInsert");
        notePromptPage.appendNotePromptTextBox(dataDrive.get("notePromptToInsert").toString());
        notePromptPage.clickAddButtonInAddPopup();
        dataDrive = scenarioContext.getDataDriveSectionAsJSON("notePromptToVerify");
        notePromptPage.insertContentToFilter(dataDrive.get("notePromptToVerify").toString());
        notePromptPage.verifyNotePromptIsAdded(dataDrive.get("notePromptToVerify").toString());
    }

    @Then("^I add a new note on \"([^\"]*)\" page$")
    public void iAddANewNoteOnPage(@NotNull String page) throws IOException, ParseException {
        if ("APPLICATION RECORD".equals(page.toUpperCase())) {
            applicationRecordPage.clickOnNotesButton();
        }
        if ("WATCHLIST RECORD".equals(page.toUpperCase())) {
            applicationRecordPage.clickWatchlistRecordBreadCrumb();
            applicationRecordPage.clickOnNotesButton();
        }

        applicationRecordPage.clickNotesDotsButton();
        applicationRecordPage.clickNotesAddButton();
        JSONObject dataDrive = scenarioContext.getDataDriveSectionAsJSON("noteToAddAndVerify");
        applicationRecordPage.enterNotePrompt(dataDrive.get("noteToAdd").toString());
        applicationRecordPage.clickSaveButtonOnNotes();
    }

    @And("^I verify the notes added$")
    public void iVerifyTheNotesAdded() throws IOException, ParseException, SQLException {
        JSONObject dataDrive = scenarioContext.getDataDriveSectionAsJSON("noteRecordsToVerify");
        applicationRecordPage.verifyNotesBeingAdded(dataDrive);

    }

    @And("^I delete the note prompt$")
    public void iDeleteTheNotePrompt() throws IOException, ParseException {
        JSONObject dataDrive = scenarioContext.getDataDriveSectionAsJSON("notePromptToVerify");
        notePromptPage.deleteNotePrompt(dataDrive.get("notePromptToVerify").toString());
    }

    @When("^I change the note prompt$")
    public void iChangeTheNotePrompt() throws IOException, ParseException {
        JSONObject dataDrive = scenarioContext.getDataDriveSectionAsJSON("notePromptToVerify");
        notePromptPage.insertContentToFilter(dataDrive.get("notePromptToVerify").toString());
        notePromptPage.clickEllipsisForRow(dataDrive.get("notePromptToVerify").toString());
        notePromptPage.clickClearButton();

        dataDrive = scenarioContext.getDataDriveSectionAsJSON("noteFieldsToSelectWithChange");
        for(int i=1;i<=dataDrive.size();i++) {
            notePromptPage.enterAvailableFieldsList(dataDrive.get("Note Prompt "+i).toString());
            notePromptPage.clickAddFieldButton();
        }

        notePromptPage.clickAddButtonInAddPopup();
        dataDrive = scenarioContext.getDataDriveSectionAsJSON("notePromptToVerifyWithChange");
        notePromptPage.insertContentToFilter(dataDrive.get("notePromptToVerify").toString());
        notePromptPage.verifyNotePromptIsAdded(dataDrive.get("notePromptToVerify").toString());
    }
}
