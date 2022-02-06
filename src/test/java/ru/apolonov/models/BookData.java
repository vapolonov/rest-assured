package ru.apolonov.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class BookData {
    private Book book;

    public Book getData() {
        return book;
    }

    public void setData(Book book) {
        this.book = book;
    }
}
