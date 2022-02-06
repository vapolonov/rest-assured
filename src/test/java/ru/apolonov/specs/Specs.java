package ru.apolonov.specs;

import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import static io.restassured.RestAssured.with;
import static io.restassured.http.ContentType.JSON;
import static ru.apolonov.filters.CustomLogFilter.customLogFilter;

public class Specs {

    public static RequestSpecification request = with()
            .baseUri("https://demoqa.com/")
            .filter(customLogFilter().withCustomTemplates())
            .log().all()
            .contentType(JSON);

    public static ResponseSpecification responseSpec = new ResponseSpecBuilder()
            .expectStatusCode(200)
            .log(LogDetail.ALL)
            .build();
}
