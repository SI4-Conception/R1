package fr.polytech.conception.r1;

import java.util.Locale;

import io.cucumber.core.api.TypeRegistry;
import io.cucumber.core.api.TypeRegistryConfigurer;
import io.cucumber.java.ParameterType;

import static java.util.Locale.ENGLISH;

public class StepDefinitions
{

    @ParameterType(".*")
    public Sport sport(String sportName)
    {
        return Sport.getByName(sportName);
    }
}