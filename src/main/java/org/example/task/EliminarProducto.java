package org.example.task;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Tasks;
import net.serenitybdd.screenplay.rest.interactions.Delete;
import org.example.apis.ProductoApi;

public class EliminarProducto implements Performable {

    private final int id;

    public EliminarProducto(int id) {
        this.id = id;
    }

    public static EliminarProducto conId(int id) {
        return Tasks.instrumented(EliminarProducto.class, id);
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                Delete.from(ProductoApi.PRODUCT_BY_ID)
                        .with(request -> request.pathParam("id", id))
        );
    }
}