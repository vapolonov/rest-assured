package ru.apolonov;

import io.qameta.allure.restassured.AllureRestAssured;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.*;
import static ru.apolonov.filters.CustomLogFilter.customLogFilter;

public class BookStoreTests {

    @Test
    void noLogsTest() {
        given()
                .get("https://demoqa.com/BookStore/v1/Books")
                .then()
                .body("books", hasSize(greaterThan(0)));
    }

    @Test
    void allLogsTest() {
        given()
                //.log().all()
                .get("https://demoqa.com/BookStore/v1/Books")
                .then()
                .log().all()
                .body("books", hasSize(greaterThan(0)));
    }

    @Test
    void withSomeLogsTest() {
        given()
                .log().body()
                .get("https://demoqa.com/BookStore/v1/Books")
                .then()
                .log().body()
                .body("books", hasSize(greaterThan(0)));
    }

    @Test
    void authorizeTest() {
        //String data = "{" +
        //        "  \"userName\": \"alex\"," +
        //        "  \"password\": \"asdsad#frew_DFS2\"" +
        //        "}";

        Map<String, String> data = new HashMap<>();
        data.put("userName", "alex");
        data.put("password", "asdsad#frew_DFS2");

        given()
                .contentType(JSON)
                .body(data)
                .when()
                .log().uri()
                .log().body()
                .post("https://demoqa.com/Account/v1/GenerateToken")
                .then()
                .log().body()
                .body("status", is("Success"))
                .body("result", is("User authorized successfully."));
    }

    @Test
    void authorizeWithListenerTest() {
        Map<String, String> data = new HashMap<>();
        data.put("userName", "alex");
        data.put("password", "asdsad#frew_DFS2");

        given()
                .filter(new AllureRestAssured())  // добавляем детализацию в Allure отчет (запрос и ответ)
                .contentType("application/json")
                .body(data)
                .when()
                .log().uri()
                .log().body()
                .post("https://demoqa.com/Account/v1/GenerateToken")
                .then()
                .log().body()
                .body("status", is("Success"))
                .body("result", is("User authorized successfully."));
    }

    @Test
    void authorizeWithTemplatesTest() {
        Map<String, String> data = new HashMap<>();
        data.put("userName", "alex");
        data.put("password", "asdsad#frew_DFS2");

        step("Generate token", () ->
                given()
                        .filter(customLogFilter().withCustomTemplates()) //красивые шаблоны дл отчетов Allure
                        .contentType("application/json")
                        .body(data)
                        .when()
                        .log().uri()
                        .log().body()
                        .post("https://demoqa.com/Account/v1/GenerateToken")
                        .then()
                        .log().body()
                        .body("status", is("Success"))
                        .body("result", is("User authorized successfully."))
        );
        step("Some UI action");
    }

    @Test
    void authorizeWithSchemeTest() {
        Map<String, String> data = new HashMap<>();
        data.put("userName", "alex");
        data.put("password", "asdsad#frew_DFS2");

        step("Generate token", () ->
                given()
                        .filter(customLogFilter().withCustomTemplates())
                        .contentType("application/json")
                        .accept("application/json")
                        .body(data)
                        .when()
                        .log().uri()
                        .log().body()
                        .post("https://demoqa.com/Account/v1/GenerateToken")
                        .then()
                        .log().body()
                        .body(matchesJsonSchemaInClasspath("schemas/GenerateTokenScheme.json")) // проверка json-схемы
                        .body("status", is("Success"))
                        .body("result", is("User authorized successfully."))
        );
        step("Some UI action");
    }
}
