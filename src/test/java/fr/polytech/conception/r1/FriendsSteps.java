package fr.polytech.conception.r1;

import org.junit.Assert;

import java.util.LinkedList;
import java.util.List;

import fr.polytech.conception.r1.profile.InvalidFriendshipException;
import fr.polytech.conception.r1.profile.User;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class FriendsSteps
{
    private List<User> userList=new LinkedList<>();

    @Given("{int} distincts users")
    public void distinctsUsers(int arg0)
    {
        for(int i=0; i<arg0; i++)
        {
            userList.add(new User());
        }
    }

    @When("User {int} sends a friend request to user {int}")
    public void userSendsAFriendRequestToUser(int arg0, int arg1) throws InvalidFriendshipException
    {
        userList.get(arg0-1).sendFriendRequest(userList.get(arg1-1));
    }

    @When("User {int} accepts the friend request of the user {int}")
    public void userAcceptsTheFriendRequestOfTheUser(int arg0, int arg1) throws InvalidFriendshipException
    {
        User u2=userList.get(arg0-1);
        u2.acceptFriendRequest(userList.get(arg1-1));
    }

    @Then("User {int} and {int} should be friend")
    public void userAndShouldBeFriend(int arg0, int arg1)
    {
        User u1 = userList.get(arg0-1);
        User u2 = userList.get(arg1-1);
        Assert.assertTrue(u1.getFriends().contains(u2));
        Assert.assertTrue(u2.getFriends().contains(u1));
    }

    @When("User {int} declines the friend request of the user {int}")
    public void userDeclinesTheFriendRequestOfTheUser(int arg0, int arg1) throws InvalidFriendshipException
    {
        User u2=userList.get(arg0-1);
        u2.denyFriendRequest(userList.get(arg1-1));
    }

    @Then("User {int} and {int} should not be friend")
    public void userAndShouldNotBeFriend(int arg0, int arg1)
    {
        User u1 = userList.get(arg0-1);
        User u2 = userList.get(arg1-1);
        Assert.assertFalse(u1.getFriends().contains(u2));
        Assert.assertFalse(u2.getFriends().contains(u1));
    }

    @When("User {int} resends a friend request to user {int}")
    public void userResendsAFriendRequestToUser(int arg0, int arg1)
    {
        User u1 = userList.get(arg0-1);
        User u2 = userList.get(arg1-1);
        Assert.assertThrows(InvalidFriendshipException.class, () -> u1.sendFriendRequest(u2));
    }
}
