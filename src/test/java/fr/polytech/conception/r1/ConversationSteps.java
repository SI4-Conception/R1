package fr.polytech.conception.r1;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class ConversationSteps
{
    @Given("{int} Users who want to talk")
    public void usersWhoWantToTalk(int arg0)
    {
    }

    @When("User {int} sends starts a conversation with user {int}")
    public void userSendsStartsAConversationWithUser(int arg0, int arg1)
    {
    }

    @Then("I should find one message in the conversation")
    public void iShouldFindOneMessageInTheConversation()
    {
    }
}
