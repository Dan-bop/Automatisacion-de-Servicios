package org.example.questions;

import net.serenitybdd.rest.SerenityRest;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Question;

public class ResponseField<T> implements Question<T> {

    private final String path;

    private ResponseField(String path) {
        this.path = path;
    }

    public static <T> ResponseField<T> of(String path) {
        return new ResponseField<>(path);
    }

    @Override
    public T answeredBy(Actor actor) {
        return SerenityRest.lastResponse().path(path);
    }
}