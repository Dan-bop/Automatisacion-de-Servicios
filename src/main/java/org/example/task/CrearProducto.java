package org.example.task;

import io.restassured.http.ContentType;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Tasks;
import net.serenitybdd.screenplay.rest.interactions.Post;
import org.example.apis.ProductoApi;
import org.example.models.ProductoRequest;

public class CrearProducto implements Performable {

    private final ProductoRequest body;

    public CrearProducto(ProductoRequest body) {
        this.body = body;
    }

    public static CrearProducto llamado(String titulo, Double precio) {
        return Tasks.instrumented(CrearProducto.class, new ProductoRequest(titulo, precio));
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                Post.to(ProductoApi.ADD_PRODUCT)
                        .with(request -> request
                                .contentType(ContentType.JSON)
                                .body(body))
        );
    }
}