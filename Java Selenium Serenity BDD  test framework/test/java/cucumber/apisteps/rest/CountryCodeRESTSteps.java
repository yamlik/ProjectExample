package com..cucumber.apisteps.rest;

import com..test.utilities.JSONHelper;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.When;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.junit.Assert;

import java.io.IOException;

public class CountryCodeRESTSteps extends BaseRESTSteps {

    protected static JSONArray countryList = new JSONArray();
    private final String countryCodesURL = baseURI + "app/countryCodes";

    @When("^I get the country codes$")
    public void iGetTheCountryCodes() throws ParseException {
        response = request().headers(headers).get(countryCodesURL);
        Assert.assertEquals("Attempting to get the country codes did not return status code 200 as expected", 200, response.getStatusCode());
        //Store the JSONArray of country codes into the pre-defined variable
        countryList = JSONHelper.parseJSONArray(response.getBody().asString());
    }

    @And("^I check if country details from \"([^\"]*)\" are present in the list$")
    public void iCheckIfCountryDetailsFromArePresentInTheList(String country) throws IOException, ParseException {
        //Call the get list to get an up-to-date list of countries to work with
        iGetTheCountryCodes();
        JSONObject requestBody = scenarioContext.getDataDriveSectionAsJSON(country);
        Assert.assertTrue("Country details \"" + requestBody + "\" are not present in the list", countryList.contains(requestBody));
    }

    @And("^I check if country details from \"([^\"]*)\" are not present in the list$")
    public void iCheckIfCountryDetailsFromAreNotPresentInTheList(String source) throws Throwable {
        iGetTheCountryCodes();
        JSONObject requestBody = scenarioContext.getDataDriveSectionAsJSON(source);
        Assert.assertFalse("Country details \"" + requestBody + "\" are present in the list", countryList.contains(requestBody));
    }

    @When("^I give new country code from \"([^\"]*)\"$")
    public void iGiveNewCountryCodeFrom(String source) throws IOException, ParseException {
        JSONObject requestBody = scenarioContext.getDataDriveSectionAsJSON(source);
        response = request().headers(headers).body(requestBody).post(countryCodesURL);
        Assert.assertEquals("Creating new country code did not return status code 200 as expected", 200, response.getStatusCode());
    }

    @When("^I update country code with details from \"([^\"]*)\"$")
    public void iUpdateCountryCodeWithDetailsFrom(String section) throws Throwable {
        JSONObject requestBody = scenarioContext.getDataDriveSectionAsJSON(section);
        response = request().headers(headers).body(requestBody).put(countryCodesURL);
        Assert.assertEquals("PUT request for country code did not return status code 200 as expected", 200, response.getStatusCode());
    }

    @And("^delete the country code defined from \"([^\"]*)\"$")
    public void deleteTheCountryCodeDefinedFrom(String section) throws IOException, ParseException {
        String countryCode = scenarioContext.getDataDriveSectionAsJSON(section).get("Code").toString();
        response = request().headers(headers).delete(countryCodesURL + "/" + countryCode);
        Assert.assertEquals("Deleting the defined Country code did not return status code 200 as expected", 200, response.getStatusCode());
    }
}