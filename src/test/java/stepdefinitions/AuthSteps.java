package stepdefinitions;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import net.serenitybdd.screenplay.actors.OnStage;
import org.example.questions.AuthResponse;
import org.example.task.ConsultarPerfil;
import org.example.task.IniciarSesion;

import static net.serenitybdd.screenplay.GivenWhenThen.seeThat;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;

public class AuthSteps {

    @Given("que {word} tiene acceso al servicio de autenticación")
    public void tieneAcceso(String nombre) {
        OnStage.theActorCalled(nombre);
    }

    @When("inicia sesión con usuario {string} y contraseña {string}")
    public void iniciaSesion(String usuario, String clave) {
        OnStage.theActorInTheSpotlight().attemptsTo(IniciarSesion.conCredenciales(usuario, clave));
    }

    @And("consulta su perfil con el token recibido")
    public void consultaPerfil() {
        OnStage.theActorInTheSpotlight().attemptsTo(ConsultarPerfil.conToken());
    }

    @Then("recibe un token de acceso")
    public void validaToken() {
        OnStage.theActorInTheSpotlight().should(
                seeThat("el token de acceso", AuthResponse.accessToken(), notNullValue()));
    }

    @And("el usuario del perfil es {string}")
    public void validaUsuario(String usuario) {
        OnStage.theActorInTheSpotlight().should(
                seeThat("el usuario del perfil", AuthResponse.username(), equalTo(usuario)));
    }
}