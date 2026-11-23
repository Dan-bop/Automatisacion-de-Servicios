package stepdefinitions;

import io.cucumber.java.en.Then;
import net.serenitybdd.screenplay.actors.OnStage;

import static net.serenitybdd.screenplay.rest.questions.ResponseConsequence.seeThatResponse;

public class CommonSteps {

    @Then("el servicio responde con código {int}")
    public void validaCodigo(int codigo) {
        OnStage.theActorInTheSpotlight().should(
                seeThatResponse("el código de respuesta es " + codigo,
                        response -> response.statusCode(codigo))
        );
    }
}