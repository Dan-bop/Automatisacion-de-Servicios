package org.example.task;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Tasks;
import net.serenitybdd.screenplay.rest.interactions.Get;
import org.example.apis.AuthApi;

public class ConsultarPerfil implements Performable {

    public static ConsultarPerfil conToken() {
        return Tasks.instrumented(ConsultarPerfil.class);
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        String token = actor.recall("token");
        actor.attemptsTo(
                Get.resource(AuthApi.PROFILE)
                        .with(request -> request.header("Authorization", "Bearer " + token))
        );
    }
}