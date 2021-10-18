package fr.polytech.conception.r1;

import io.cucumber.java.ParameterType;

public class StepDefinitions
{

    @ParameterType(".*")
    public Sport sport(String sportName)
    {
        return Sport.getByName(sportName);
    }
}