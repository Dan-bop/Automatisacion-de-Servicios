package stepdefinitions;

import io.cucumber.java.Before;
import net.serenitybdd.core.environment.EnvironmentSpecificConfiguration;
import net.serenitybdd.screenplay.actors.Cast;
import net.serenitybdd.screenplay.actors.OnStage;
import net.serenitybdd.screenplay.rest.abilities.CallAnApi;
import net.thucydides.core.util.EnvironmentVariables;
import net.thucydides.core.util.SystemEnvironmentVariables;

public class Hooks {

    @Before
    public void prepararEscenario() {
        EnvironmentVariables env = SystemEnvironmentVariables.createEnvironmentVariables();
        String baseUrl = EnvironmentSpecificConfiguration.from(env)
                .getProperty("restapi.baseurl");

        OnStage.setTheStage(Cast.whereEveryoneCan(CallAnApi.at(baseUrl)));
    }
}