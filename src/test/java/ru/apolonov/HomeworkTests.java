package ru.apolonov;

import io.qameta.allure.AllureId;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.*;
import static io.restassured.http.ContentType.JSON;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.is;

class HomeworkTests {

    @BeforeAll
    static void setUp() {
        RestAssured.baseURI = "https://reqres.in/";
    }

    @Test
    void successfulRegister() {
        String data = "{ \"email\": \"eve.holt@reqres.in\", \"password\": \"pistol\" }";
        given()
                .contentType(JSON)
                .body(data)
                .when()
                .post("/api/register")
                .then()
                .statusCode(200)
                .body("id", is(4))
                .body("token", is("QpwL5tke4Pnpja7X4"));
    }

    @Test
    void unsuccessfulRegister() {
        String data = "{ \"email\": \"sydney@fife\" }";
        given()
                .contentType(JSON)
                .body(data)
                .when()
                .post("/api/register")
                .then()
                .statusCode(400)
                .body("error", is("Missing password"));
    }

    @Test
    void SingleUserTest() {
        Integer response =
                get("/api/users/2")
                        .then()
                        .statusCode(200)
                        .extract().path("data.id");
        assertThat(response).isEqualTo(2);
    }

    @Test
    void updateUserPatch() {
        String data = "{ \"name\": \"morpheus\", \"job\": \"zion resident\" }";
        given()
                .contentType(JSON)
                .body(data)
                .when()
                .patch("/api/users/2")
                .then()
                .statusCode(200)
                .body("name", is("morpheus"))
                .body("job", is("zion resident"));
    }

    @Test
    void deleteUser() {
        given()
                .contentType(JSON)
                .when()
                .delete("/api/users/2")
                .then()
                .statusCode(204);
    }
}
