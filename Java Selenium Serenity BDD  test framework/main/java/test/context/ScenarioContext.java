package com..test.context;

import com..test.utilities.JSONHelper;
import com..test.utilities.PropertiesManager;
import cucumber.api.Scenario;
import lombok.Data;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import java.io.IOException;

@Data
public class ScenarioContext {

    private static final ScenarioContext TSNTance = new ScenarioContext();
    private static  String dataDrive;
    private static String databaseName;
    private static String scenarioNumber;
    private static String organisation;
    private static String country;
    private String aTSNTssToken;
    private String profile1="";
    private String profile2="";
    private String profile3;
    private String profile4;
    private String user1="";
    private String user2="";
    private String user3;
    private String user4;

    public static ScenarioContext getTSNTance() {
        return TSNTance;
    }

    public void setScenarioName(Scenario scenarioName) {
        scenarioNumber = scenarioName.getName().substring(0, scenarioName.getName().indexOf(' '));
        String dataDir = scenarioName.getUri().substring(scenarioName.getUri().indexOf("features/") + 9, scenarioName.getUri().lastIndexOf(".feature"));
        dataDrive = System.getProperty("user.dir") + "/src/main/resources/data/" + dataDir + ".json";
    }

    public void setDatabase()
    {
        String databaseURL = PropertiesManager.get().getProperty("database.server");
        String partString = databaseURL.substring(databaseURL.indexOf(";")+1);
        databaseName = partString.substring(partString.indexOf("=")+1,partString.indexOf(";"));
    }

    public void setOrganisationCode(String organisationCode) {
        organisation = organisationCode;
    }

    public String getOrganisation() { return organisation; }

    public String getCountry() { return  country; }

    public String getDatabaseName() { return databaseName; }

    public void setCountryCode(String countryCode) {
        country = countryCode;
    }

    public String getDataDriveAsString() throws IOException, ParseException {
        return JSONHelper.messageAsSimpleJson(dataDrive).get(scenarioNumber).toString();
    }

    public String getDataDriveSectionAsString(String key) throws IOException, ParseException {
        JSONObject json = (JSONObject) JSONHelper.messageAsSimpleJson(dataDrive).get(scenarioNumber);
        return json.get(key).toString();
    }

    public JSONObject getDataDriveAsJSON() throws IOException, ParseException {
        return (JSONObject) JSONHelper.messageAsSimpleJson(dataDrive).get(scenarioNumber);
    }

    public JSONObject getDataDriveSectionAsJSON(String key) throws IOException, ParseException {
        JSONObject json = (JSONObject) JSONHelper.messageAsSimpleJson(dataDrive).get(scenarioNumber);
        return (JSONObject) json.get(key);

    }
    public JSONArray getDataDriveAsArray() throws IOException, ParseException {
        return (JSONArray) JSONHelper.messageAsSimpleJson(dataDrive).get(scenarioNumber);
    }

    public JSONArray getDataDriveSectionAsArray(String key) throws IOException, ParseException {
        JSONObject json = (JSONObject) JSONHelper.messageAsSimpleJson(dataDrive).get(scenarioNumber);
        return (JSONArray) json.get(key);
    }
}
