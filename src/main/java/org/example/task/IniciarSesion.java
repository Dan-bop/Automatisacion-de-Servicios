package org.example.task;

import io.restassured.http.ContentType;
import net.serenitybdd.rest.SerenityRest;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Tasks;
import net.serenitybdd.screenplay.rest.interactions.Post;
import org.example.apis.AuthApi;
import org.example.models.LoginRequest;

public class IniciarSesion implements Performable {

    private final LoginRequest body;

    public IniciarSesion(LoginRequest body) {
        this.body = body;
    }

    public static IniciarSesion conCredenciales(String usuario, String clave) {
        return Tasks.instrumented(IniciarSesion.class, new LoginRequest(usuario, clave));
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                Post.to(AuthApi.LOGIN)
                        .with(request -> request
                                .contentType(ContentType.JSON)
                                .body(body))
        );

        String token = SerenityRest.lastResponse().path("accessToken");
        if (token != null) {
            actor.remember("token", token);
        }
    }
}