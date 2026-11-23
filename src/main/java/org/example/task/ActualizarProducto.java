package org.example.task;

import io.restassured.http.ContentType;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Tasks;
import net.serenitybdd.screenplay.rest.interactions.Put;
import org.example.apis.ProductoApi;
import org.example.models.ProductoRequest;

public class ActualizarProducto implements Performable {

    private final int id;
    private final ProductoRequest body;

    public ActualizarProducto(int id, ProductoRequest body) {
        this.id = id;
        this.body = body;
    }

    public static ActualizarProducto conId(int id, String nuevoTitulo) {
        return Tasks.instrumented(ActualizarProducto.class, id, new ProductoRequest(nuevoTitulo, null));
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                Put.to(ProductoApi.PRODUCT_BY_ID)
                        .with(request -> request
                                .pathParam("id", id)
                                .contentType(ContentType.JSON)
                                .body(body))
        );
    }
}