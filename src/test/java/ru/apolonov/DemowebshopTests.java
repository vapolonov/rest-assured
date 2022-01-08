package ru.apolonov;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

public class DemowebshopTests {

    @BeforeAll
    static void setUp() {
        RestAssured.baseURI = "http://demowebshop.tricentis.com/";
    }

    @Test
    void addToCartWithCookies() {
        String data = "product_attribute_72_5_18=53&" +
                "product_attribute_72_6_19=54&" +
                "product_attribute_72_3_20=57&" +
                "addtocart_72.EnteredQuantity=1";

        Response response =
        given()
                .contentType("application/x-www-form-urlencoded; charset=UTF-8")
                .cookie("Nop.customer=548ca2b7-9467-4f3f-b7e9-598c9906b8b0;")
                .body(data)
                .when()
                .post("addproducttocart/details/72/1")
                .then()
                .statusCode(200)
                .body("success", is(true))
                .body("message", is("The product has been added to your <a href=\"/cart\">shopping cart</a>"))
                .extract().response();
        System.out.println("Response: " + response.asString());
    }

    @Test
    void addToCart() {
        String data = "product_attribute_72_5_18=53&" +
                "product_attribute_72_6_19=54&" +
                "product_attribute_72_3_20=57&" +
                "addtocart_72.EnteredQuantity=1";

        Response response =
                given()
                        .contentType("application/x-www-form-urlencoded; charset=UTF-8")
                        //.cookie("Nop.customer=548ca2b7-9467-4f3f-b7e9-598c9906b8b0;")
                        .body(data)
                        .when()
                        .post("addproducttocart/details/72/1")
                        .then()
                        .statusCode(200)
                        .body("success", is(true))
                        .body("message", is("The product has been added to your <a href=\"/cart\">shopping cart</a>"))
                        .body("updatetopcartsectionhtml", is("(1)"))
                        .extract().response();
        System.out.println("Response: " + response.asString());
    }
}
