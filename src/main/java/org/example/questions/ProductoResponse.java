package org.example.questions;


import net.serenitybdd.screenplay.Question;

public final class ProductoResponse {
    private ProductoResponse() { }

    public static Question<Integer> id()       { return ResponseField.of("id"); }
    public static Question<String> title()     { return ResponseField.of("title"); }
    public static Question<String> message()   { return ResponseField.of("message"); }
    public static Question<Boolean> isDeleted(){ return ResponseField.of("isDeleted"); }
}