package ru.apolonov;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.Matchers.is;

public class ReqresTests {

    @BeforeAll
    static void setUp() {
        RestAssured.baseURI = "https://reqres.in/";
    }

    @Test
    void successfulLogin() {
        // https://reqres.in/api/login
        /*
            {
                "email": "eve.holt@reqres.in",
                "password": "cityslicka"
            }
        */
        // "token": "QpwL5tke4Pnpja7X4"
        // 200

        String data = "{ \"email\": \"eve.holt@reqres.in\", \"password\": \"cityslicka\" }";

        given()
                .contentType(JSON)
                .body(data)
                .when()
                .post("api/login")
                .then()
                .statusCode(200)
                .body("token", is("QpwL5tke4Pnpja7X4"));
    }

    @Test
    void negativeLogin() {
        // https://reqres.in/api/login
        /*
            {
                "email": "eve.holt@reqres.in"
            }
        */
        // "error": "Missing password"
        // 400

        String data = "{ \"email\": \"eve.holt@reqres.in\" }";

        given()
                .contentType(JSON)
                .body(data)
                .when()
                .post("api/login")
                .then()
                .statusCode(400)
                .body("error", is("Missing password"));
    }
}
