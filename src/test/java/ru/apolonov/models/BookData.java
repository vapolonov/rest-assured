package ru.apolonov.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class BookData {
    private Book body;

    public Book getBody() {
        return body;
    }

    public void setBody(Book body) {
        this.body = body;
    }
}
