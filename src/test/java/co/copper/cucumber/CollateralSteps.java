package co.copper.cucumber;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;

import static org.junit.Assert.assertEquals;

public class CollateralSteps {

    Response currentResponse;
    ObjectMapper Obj = new ObjectMapper();
    ApiCall apiCall;

    public CollateralSteps() {
        String appHost = System.getenv("APP_HOST");
        System.out.println("Apphost found was: " + appHost);
        apiCall = new ApiCall("http://" + appHost);
    }

    @Given("the Collaterals service is running")
    public void theCollateralsServiceIsRunning() {
    }

    @When("I add valid collateral with id {string}")
    public void iAddValidCollateralWithId(String cId) throws JsonProcessingException {
        CollateralData req = new CollateralData();
        req.id = Integer.parseInt(cId);
        String payload = Obj.writeValueAsString(req);

        currentResponse = apiCall.PostCall("/collaterals", payload);
    }

    @When("I add otherwise valid collateral with a value of {string}")
    public void iAddOtherwiseValidCollateralWithAValueOf(String valueAmount) throws JsonProcessingException {
        CollateralData req = new CollateralData();
        req.value = Integer.parseInt(valueAmount);
        String payload = Obj.writeValueAsString(req);

        currentResponse = apiCall.PostCall("/collaterals", payload);
    }

    @Then("I receive a {string} HTTP status")
    public void iReceiveAHTTPStatus(String expectedCode) {
        assertEquals(currentResponse.statusCode(), Integer.parseInt(expectedCode));
    }

    @And("I receive a response body with {string}")
    public void iReceiveAResponseBodyWith(String expectedBody) {
        String foundBody = currentResponse.getBody().asString();
        assertEquals(foundBody, expectedBody);

    }

    @When("I add otherwise valid collateral with no asset type")
    public void iAddOtherwiseValidCollateralWithNoAssetType() throws JsonProcessingException {
        CollateralData req = new CollateralData();
        req.type = null;
        String payload = Obj.writeValueAsString(req);

        currentResponse = apiCall.PostCall("/collaterals", payload);
    }

    @When("I add otherwise valid collateral with an asset type of {string}")
    public void iAddOtherwiseValidCollateralWithAnAssetTypeOfBonds(String assetString) throws JsonProcessingException {
        CollateralData req = new CollateralData();
        req.type = assetString;
        String payload = Obj.writeValueAsString(req);

        currentResponse = apiCall.PostCall("/collaterals", payload);
    }

    @When("I add otherwise valid collateral with no currency specified")
    public void iAddOtherwiseValidCollateralWithNoCurrencySpecified() throws JsonProcessingException {
        CollateralData req = new CollateralData();
        req.currency = null;
        String payload = Obj.writeValueAsString(req);

        currentResponse = apiCall.PostCall("/collaterals", payload);
    }

    @When("I add otherwise valid collateral with no year specified")
    public void iAddOtherwiseValidCollateralWithNoYearSpecified() throws JsonProcessingException {
        CollateralData req = new CollateralData();
        req.year = null;
        String payload = Obj.writeValueAsString(req);

        currentResponse = apiCall.PostCall("/collaterals", payload);
    }

    @When("I add otherwise valid collateral with a year {string} specified")
    public void iAddOtherwiseValidCollateralWithAYearSpecified(String myYear) throws JsonProcessingException {
        CollateralData req = new CollateralData();
        req.year = Integer.parseInt(myYear);
        String payload = Obj.writeValueAsString(req);

        currentResponse = apiCall.PostCall("/collaterals", payload);
    }

    @When("I add otherwise valid collateral with a date {string} specified")
    public void iAddOtherwiseValidCollateralWithADateSpecified(String myDate) {
        // This needs to be discussed and implemented
        assertEquals(0,1);
    }

    @And("I have added collateral with id {string}, type {string}, name {string}, currency {string}, year {string} and value {string}")
    public void iHaveAddedCollateralWithIdTypeNameCurrencyYearAndValue(String id, String type, String name, String currency, String year, String value) throws JsonProcessingException {
        CollateralData req = new CollateralData();
        req.id = Integer.parseInt(id);
        req.type = type;
        req.name = name;
        req.currency = currency;
        req.year = Integer.parseInt(year);
        req.value = Integer.parseInt(value);
        String payload = Obj.writeValueAsString(req);

        currentResponse = apiCall.PostCall("/collaterals", payload);
    }

    @When("I request collateral with id {string}")
    public void iRequestCollateralWithIdId(String id) {
        currentResponse = apiCall.GetCall("/collaterals/" + id);
    }

    @And("I receive a response body with id {string}, type {string}, name {string}, currency {string}, year {string} and value {string}")
    public void iReceiveAResponseBodyWithIdTypeNameCurrencyYearAndValue(String id, String type, String name, String currency, String year, String value) throws JsonProcessingException {
        CollateralData response = Obj.readValue(currentResponse.getBody().asString(), CollateralData.class);
        assertEquals(response.id, Integer.parseInt(id));
        assertEquals(response.type, type);
        assertEquals(response.name, name);
        assertEquals(response.currency, currency);
        assertEquals(response.year.intValue(), Integer.parseInt(year));
        assertEquals(response.value.intValue(), Integer.parseInt(value));
    }

    @When("I attempt to retrieve collateral with an unknown id")
    public void iAttemptToRetrieveCollateralWithAnUnknownId() {
        // This is a little dangerous - do we really know 1000 is unknown?
        currentResponse = apiCall.GetCall("/collaterals/1000");
    }



}
