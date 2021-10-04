package fr.polytech.conception.r1;

import org.junit.Assert;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class MyServiceSteps
{
    private MyService service;
    private boolean success;

    @Given("^my service exists$")
    public void myServiceExists()
    {
        service = new MyService();
    }

    @When("^I call my service$")
    public void iCallMyService()
    {
        success = service.testMethod();
    }

    @Then("^It should have been a success$")
    public void itShouldHaveBeenASuccess()
    {
        Assert.assertTrue(success);
    }
}
