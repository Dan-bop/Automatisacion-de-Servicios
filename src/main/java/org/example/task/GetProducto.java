package org.example.task;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Performable;
import net.serenitybdd.screenplay.Tasks;
import net.serenitybdd.screenplay.rest.interactions.Get;
import org.example.apis.ProductoApi;

public class GetProducto implements Performable {

    private final int id;

    public GetProducto(int id) {
        this.id = id;
    }

    public static GetProducto withId(int id) {
        return Tasks.instrumented(GetProducto.class, id);
    }

    @Override
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                Get.resource(ProductoApi.PRODUCT_BY_ID)
                        .with(request -> request.pathParam("id", id))
        );
    }
}
