package fr.polytech.conception.r1;

import org.junit.Assert;

import java.time.ZonedDateTime;
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
    private Session session;
    private final ZonedDateTime validDateTimeBegin = ZonedDateTime.parse("2030-02-01T12:00:00.000+01:00[Europe/Paris]");
    private final ZonedDateTime validDateTimeEnd = ZonedDateTime.parse("2030-02-01T15:00:00.000+01:00[Europe/Paris]");

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

    @Given("{int} friends users")
    public void friendsUsers(int arg0) throws InvalidFriendshipException
    {
        for(int i=0; i<arg0; i++)
        {
            User u = new User();
            userList.add(u);
            if(i > 0)
            {
                for(int j=i-1; j>=0; j--)
                {
                    User u2 = userList.get(j);
                    u.sendFriendRequest(u2);
                    u2.acceptFriendRequest(u);
                }
            }
        }
    }

    @Given("A session created by user {int} for friends only")
    public void aSessionCreatedByUserForFriendsOnly(int arg0) throws InvalidSessionDataException
    {
        User u = userList.get(arg0-1);
        session = new Session(validDateTimeBegin, validDateTimeEnd, "75° 06′ 00″ S, 123° 19′ 58″ E", Sport.SKI, u);
        session.setReserveAuxAmis(true);
    }

    @When("User {int} try to participate to the session")
    public void userTryToParticipateToTheSession(int arg0)
    {
        User u = userList.get(arg0 - 1);
        u.participer(session);
    }

    @Then("User {int} registration should be ok")
    public void userRegistrationShouldBeOk(int arg0)
    {
        User u = userList.get(arg0 - 1);
        Assert.assertTrue(u.getListSessions().contains(session));
        Assert.assertTrue(session.getParticipants().contains(u));
    }

    @Then("User {int} registration should not be ok")
    public void userRegistrationShouldNotBeOk(int arg0)
    {
        User u = userList.get(arg0 - 1);
        Assert.assertFalse(u.getListSessions().contains(session));
        Assert.assertFalse(session.getParticipants().contains(u));
    }
}
