package org.example.questions;

import net.serenitybdd.screenplay.Question;

public final class AuthResponse {
    private AuthResponse() { }

    public static Question<String> accessToken() { return ResponseField.of("accessToken"); }
    public static Question<String> username()    { return ResponseField.of("username"); }
}