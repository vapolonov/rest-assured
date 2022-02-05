package ru.apolonov;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.Matchers.*;

import static ru.apolonov.specs.SpecAuth.request;
import static ru.apolonov.specs.SpecAuth.responseSpec;

public class BookStoreWithSpecsTests {
    @Test
    void authorizeTest() {

        Map<String, String> data = new HashMap<>();
        data.put("userName", "alex");
        data.put("password", "asdsad#frew_DFS2");

        given()
                .spec(request)
                .body(data)
                .when()
                .post("Account/v1/GenerateToken")
                .then()
                .spec(responseSpec)
                .body("status", is("Success"))
                .body("result", is("User authorized successfully."));
    }

    @Test
    void getBooksTest() {
        given()
                .spec(request)
                .when()
                .get("/BookStore/v1/Books")
                .then()
                .spec(responseSpec)
                .body("books", hasSize(greaterThan(0)))
                .body("books.findAll{it.publisher =~/.*?Media/}.publisher.flatten()",
                        hasItem("O'Reilly Media"));
    }

    @Test
    void getBookTest() {
        given()
                .spec(request)
                .when()
                .get("BookStore/v1/Book?ISBN=9781449325862")
                .then()
                .spec(responseSpec)
                .body("isbn", is("9781449325862"))
                .body("title", is("Git Pocket Guide"))
                .body("author", is("Richard E. Silverman"))
                .body("publisher", is("O'Reilly Media"))
                .body("pages", is(234));
    }
}
