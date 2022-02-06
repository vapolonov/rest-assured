package ru.apolonov;

import org.junit.jupiter.api.Test;
import ru.apolonov.lombok.LombokTestData;
import ru.apolonov.lombok.LombokUserData;
import ru.apolonov.models.UserData;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
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
}
