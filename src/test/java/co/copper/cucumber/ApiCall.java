package co.copper.cucumber;

import io.restassured.RestAssured;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class ApiCall {

    public ApiCall(String baseUrl) {
        RestAssured.baseURI = baseUrl;
    }


    public Response GetCall(String endPoint) {
        return given()
                .port(8080)
                .header("Content-type", "application/json")
                .when()
                .get(endPoint)
                .then()
                .extract().response();
    }

    public Response PostCall(String endPoint, String payload) {
        return given()
                .port(8080)
                .header("Content-type", "application/json")
                .and()
                .body(payload)
                .when()
                .post(endPoint)
                .then()
                .extract().response();
    }
}
