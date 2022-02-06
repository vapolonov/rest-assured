package ru.apolonov;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.apolonov.lombok.*;
import ru.apolonov.models.UserData;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static ru.apolonov.specs.Specs.request;
import static ru.apolonov.specs.Specs.responseSpec;
import static ru.apolonov.specs.SpecsReqres.*;

public class ReqresTests {

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
                .spec(specRequest)
                .body(data)
                .when()
                .post("api/login")
                .then()
                .spec(specResponse200)
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
                .spec(specRequest)
                .body(data)
                .when()
                .post("api/login")
                .then()
                .spec(specResponse400)
                .body("error", is("Missing password"));
    }

    @Test
    void singleUserWithModelTest() {
        UserData data = given()
                .spec(specRequest)
                .when()
                .get("/api/users/2")
                .then()
                .spec(specResponse200)
                .extract().as(UserData.class);
        assertEquals(2, data.getData().getId());
        assertEquals("Janet", data.getData().getFirstName());
        assertEquals("janet.weaver@reqres.in", data.getData().getEmail());
    }

    @Test
    void singleUserWithLombokTest() {
        LombokUserData data = given()
                .spec(specRequest)
                .when()
                .get("/api/users/2")
                .then()
                .spec(specResponse200)
                .extract().as(LombokUserData.class);
        assertEquals(2, data.getUser().getId());
        assertEquals("Janet", data.getUser().getFirstName());
        assertEquals("janet.weaver@reqres.in", data.getUser().getEmail());
    }

    @Test
    void singleUserWithLombokTest2() {
        LombokTestData data = given()
                .spec(specRequest)
                .when()
                .get("/api/users/2")
                .then()
                .spec(specResponse200)
                .extract().as(LombokTestData.class);
        assertEquals("To keep ReqRes free, contributions towards server costs are appreciated!", data.getUser().getText());
    }

    @Test
    @DisplayName("Register successful Lombok")
    void registerSuccessfulLombok() {
        RegisterUser registrationData = new RegisterUser();
        registrationData.setEmail("eve.holt@reqres.in");
        registrationData.setPassword("pistol");

        CreateUserResponse response = given().spec(specRequest)
                .body(registrationData)
                .when()
                .post("/api/register")
                .then()
                .spec(specResponse200)
                .extract().as(CreateUserResponse.class);

        assertEquals("4", response.getId());
        assertEquals("QpwL5tke4Pnpja7X4", response.getToken());
    }

    @Test
    @DisplayName("Create user lombok")
    void createUserLombok() {
        CreateUserRequest newCreateUser = new CreateUserRequest("morpheus", "leader");

        CreateUserResponse response = given().spec(specRequest)
                .body(newCreateUser)
                .when()
                .post("/api/users")
                .then()
                .statusCode(201)
                .extract().as(CreateUserResponse.class);

        assertEquals(newCreateUser.getName(), response.getName());
        assertEquals(newCreateUser.getJob(), response.getJob());
        assertFalse(response.getId().isEmpty());
        assertFalse(response.getCreatedAt().isEmpty());

    }
}
