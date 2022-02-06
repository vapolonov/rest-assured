package ru.apolonov.specs;

import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import static io.restassured.RestAssured.with;
import static io.restassured.http.ContentType.JSON;
import static ru.apolonov.filters.CustomLogFilter.customLogFilter;

public class SpecsReqres {
    public static RequestSpecification specRequest = with()
            .baseUri("https://reqres.in/")
            .filter(customLogFilter().withCustomTemplates())
            .log().all()
            .contentType(JSON);

    public static ResponseSpecification specResponse200 = new ResponseSpecBuilder()
            .expectStatusCode(200)
            .log(LogDetail.ALL)
            .build();

    public static ResponseSpecification specResponse400 = new ResponseSpecBuilder()
            .expectStatusCode(400)
            .log(LogDetail.ALL)
            .build();
}
