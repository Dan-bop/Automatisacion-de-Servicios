package stepdefinitions;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import net.serenitybdd.screenplay.actors.OnStage;
import org.example.questions.ProductoResponse;
import org.example.task.ActualizarProducto;
import org.example.task.CrearProducto;
import org.example.task.EliminarProducto;
import org.example.task.GetProducto;

import static net.serenitybdd.screenplay.GivenWhenThen.seeThat;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.equalTo;

public class ProductSteps {


    @Given("que {word} tiene acceso al servicio de productos")
    public void tieneAcceso(String nombre) {
        OnStage.theActorCalled(nombre);
    }


    @When("consulta el producto con id {int}")
    public void consulta(int id) {
        OnStage.theActorInTheSpotlight().attemptsTo(GetProducto.withId(id));
    }

    @When("crea un producto llamado {string} con precio {double}")
    public void crea(String titulo, Double precio) {
        OnStage.theActorInTheSpotlight().attemptsTo(CrearProducto.llamado(titulo, precio));
    }

    @When("actualiza el producto {int} con el título {string}")
    public void actualiza(int id, String titulo) {
        OnStage.theActorInTheSpotlight().attemptsTo(ActualizarProducto.conId(id, titulo));
    }

    @When("elimina el producto con id {int}")
    public void elimina(int id) {
        OnStage.theActorInTheSpotlight().attemptsTo(EliminarProducto.conId(id));
    }


    @Then("el producto devuelto tiene el id {int}")
    public void validaId(int id) {
        OnStage.theActorInTheSpotlight().should(
                seeThat("el id del producto", ProductoResponse.id(), equalTo(id)));
    }

    @Then("el título del producto es {string}")
    public void validaTitulo(String titulo) {
        OnStage.theActorInTheSpotlight().should(
                seeThat("el título del producto", ProductoResponse.title(), equalTo(titulo)));
    }


    @And("el producto aparece como eliminado")
    public void validaEliminado() {
        OnStage.theActorInTheSpotlight().should(
                seeThat("el producto está eliminado", ProductoResponse.isDeleted(), equalTo(true)));
    }

    @And("el mensaje de error contiene {string}")
    public void validaMensaje(String texto) {
        OnStage.theActorInTheSpotlight().should(
                seeThat("el mensaje de error", ProductoResponse.message(), containsString(texto)));
    }
}