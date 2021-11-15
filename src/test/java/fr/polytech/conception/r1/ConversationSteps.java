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
    private final List<User> userList=new LinkedList<>();
    private Conversation conv;

    @Given("{int} Users who wants to talk")
    public void usersWhoWantToTalk(int arg0)
    {
        for(int i=0; i<arg0; i++)
        {
            userList.add(new User());
        }
    }

    @When("User {int} starts a conversation with user {int}")
    public void userSendsStartsAConversationWithUser(int arg0, int arg1)
    {
        conv = userList.get(arg0-1).startConversation(userList.get(arg1-1), "hey");
    }

    @Then("There is {int} message in the whole conversation")
    public void iShouldFindOneMessageInTheConversation(int arg0)
    {
        Assert.assertEquals(arg0, conv.numberMessages());
    }
}
