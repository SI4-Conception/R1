package fr.polytech.conception.r1;

import org.junit.Assert;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class SessionSteps
{
    Session session;

    @Given("valid datas for the session")
    public void validDatasForTheSession()
    {
    }

    @When("I create the session")
    public void iCreateTheSession()
    {
        session = new Session();
    }

    @Then("It should have been a success")
    public void itShouldHaveBeenASuccess()
    {
        Assert.assertNotNull(session);
    }
}
