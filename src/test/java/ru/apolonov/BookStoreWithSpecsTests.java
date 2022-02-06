package ru.apolonov;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import ru.apolonov.lombok.LombokBookData;
import ru.apolonov.models.BookData;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.apolonov.specs.Specs.request;
import static ru.apolonov.specs.Specs.responseSpec;

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

    @Test
    @Disabled
    void getBookWithModelsTest() {
        BookData data = given()
                .spec(request)
                .when()
                .get("BookStore/v1/Book?ISBN=9781449325862")
                .then()
                .spec(responseSpec)
                .extract().as(BookData.class);
        assertEquals("9781449325862", data.getBody().getIsbn());
        assertEquals(234, data.getBody().getPages());
        assertEquals("title", data.getBody().getTitle());
        assertEquals("publishDate", data.getBody().getPublishDate());
    }

    @Test
    @Disabled
    void getBookWithLombokTest() {
        LombokBookData data = given()
                .spec(request)
                .when()
                .get("BookStore/v1/Book?ISBN=9781449325862")
                .then()
                .spec(responseSpec)
                .extract().as(LombokBookData.class);
        assertEquals("9781449325862", data.getBook().getIsbn());
        assertEquals(234, data.getBook().getPages());
        assertEquals("title", data.getBook().getTitle());
        assertEquals("publishDate", data.getBook().getPublishDate());
    }
}
