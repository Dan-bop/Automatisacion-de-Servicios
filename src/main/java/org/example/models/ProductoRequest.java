package org.example.models;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProductoRequest {

    private final String title;
    private final Double price;

    public ProductoRequest(String title, Double price) {
        this.title = title;
        this.price = price;
    }

    public String getTitle() { return title; }
    public Double getPrice() { return price; }
}