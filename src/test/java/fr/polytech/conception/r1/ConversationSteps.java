package fr.polytech.conception.r1;

import org.junit.Assert;

import java.util.LinkedList;
import java.util.List;

import fr.polytech.conception.r1.profile.User;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class ConversationSteps
{
    private List<User> userList=new LinkedList<>();
    private Conversation conv;

    @Given("{int} Users who want to talk")
    public void usersWhoWantToTalk(int arg0)
    {
        for(int i=0; i<arg0; i++)
        {
            userList.add(new User());
        }
    }

    @When("User {int} sends starts a conversation with user {int}")
    public void userSendsStartsAConversationWithUser(int arg0, int arg1)
    {
        conv = userList.get(arg0-1).startConversation(userList.get(arg1-1), "hey");
    }

    @Then("I should find one message in the conversation")
    public void iShouldFindOneMessageInTheConversation()
    {
        Assert.assertEquals(1, conv.numberMessages());
    }
}
