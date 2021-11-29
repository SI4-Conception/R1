package fr.polytech.conception.r1;

import org.junit.Assert;

import java.time.ZonedDateTime;
import java.util.LinkedList;
import java.util.List;

import fr.polytech.conception.r1.profile.User;
import fr.polytech.conception.r1.session.SessionOneshot;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class InvitationSteps
{
    private final ZonedDateTime validDateTimeBegin = ZonedDateTime.parse("2030-02-01T12:00:00.000+01:00[Europe/Paris]");
    private final ZonedDateTime validDateTimeEnd = ZonedDateTime.parse("2030-02-01T15:00:00.000+01:00[Europe/Paris]");
    private final List<User> userList = new LinkedList<>();
    private SessionOneshot session;
    private Invitation invitation;
    private int numberInvitationSent;

    @Given("{int} distinct users")
    public void distinctUsers(int arg0)
    {
        for (int i = 0; i < arg0; i++)
        {
            userList.add(new User());
        }
    }

    @When("user {int} invites user {int} to the session")
    public void userInvitesUserToTheSession(int arg0, int arg1)
    {
        User u = userList.get(arg0 - 1);
        invitation = u.invite(session, userList.get(arg1 - 1));
    }

    @Then("user {int} can see the invitaion he has sent")
    public void userCanSeeTheInvitaionHeHasSent(int arg0)
    {
        User u = userList.get(arg0 - 1);
        Assert.assertTrue(u.hasSentInvitation(invitation));
    }

    @And("user {int} can see that the invitation is pending")
    public void userCanSeeThatTheInvitationIsPending(int arg0)
    {
        User u = userList.get(arg0 - 1);
        Assert.assertTrue(u.isInvitationSentPending(invitation));
    }

    @And("user {int} has received the invitation")
    public void userHasReceivedTheInvitation(int arg0)
    {
        User u = userList.get(arg0 - 1);
        Assert.assertTrue(u.isInvitationPending(invitation));
    }

    @Given("an invitation to a session from user {int} to user {int}")
    public void anInvitationToASessionFromUserToUser(int arg0, int arg1)
    {
        User u = userList.get(arg0 - 1);
        User u2 = userList.get(arg1 - 1);
        invitation = u.invite(session, u2);
    }

    @When("user {int} accepts the invitation")
    public void userAcceptsTheInvitation(int arg0)
    {
        User u = userList.get(arg0 - 1);
        try
        {
            u.acceptInvitation(invitation);
        }
        catch (InvalidSessionDataException e)
        {
            e.printStackTrace();
            Assert.fail();
        }
    }

    @When("user {int} accepts the invalid invitation")
    public void userAcceptsTheInvalidInvitation(int arg0)
    {
        User u = userList.get(arg0 - 1);
        try
        {
            u.acceptInvitation(invitation);
            Assert.fail();
        }
        catch (InvalidSessionDataException | IllegalArgumentException e)
        {
            //success
        }
    }

    //@When("user {int} accepts the invalid invitation")
    //public void userAcceptsTheInvalidInvitation(int arg0)
    //{
    //    User u = userList.get(arg0-1);
    //    try
    //    {
    //    }
    //
    //}

    @Then("user {int} is participating the session")
    public void userIsParticipatingTheSession(int arg0)
    {
        User u = userList.get(arg0 - 1);
        Assert.assertTrue(session.getParticipants().contains(u));
        Assert.assertFalse(u.isInvitationPending(invitation));
    }

    @Then("user {int} is denied accepting the invitation")
    public void userIsDeniedAcceptingTheInvitation(int arg0)
    {
        User u = userList.get(arg0 - 1);
        Assert.assertFalse(session.getParticipants().contains(u));
        Assert.assertTrue(u.isInvitationPending(invitation));
    }

    @Given("a full session")
    public void aFullSession() throws InvalidSessionDataException
    {
        session.setMaxParticipants(5);
        for (int i = 0; i < 5; i++)
        {
            User u = new User();
            u.participate(session);
        }
    }

    @When("user {int} declines the invitation")
    public void userDeclinesTheInvitation(int arg0)
    {
        User u = userList.get(arg0 - 1);
        u.declineInvitation(invitation);
    }

    @Then("user {int} can see the invitation sent is declined")
    public void userCanSeeTheInvitationIsDeclined(int arg0)
    {
        User u = userList.get(arg0 - 1);
        Assert.assertTrue(u.isInvitationSentDeclined(invitation));
    }

    @Given("user {int} has blacklisted user {int}")
    public void userHasBlacklistedUser(int arg0, int arg1)
    {
        User u = userList.get(arg0 - 1);
        User u2 = userList.get(arg1 - 1);
        u.blacklist(u2);
    }

    @Given("a session organized by user {int}")
    public void aSessionOrganizedByUser(int arg0) throws InvalidSessionDataException
    {
        User u = userList.get(arg0 - 1);
        session = new SessionOneshot(validDateTimeBegin, validDateTimeEnd, "75° 06′ 00″ S, 123° 19′ 58″ E", Sport.SKI, u, false);
    }

    @Then("user {int} doesn't see a pending invitation")
    public void userDoesnTSeeAPendingInvitation(int arg0)
    {
        User u = userList.get(arg0 - 1);
        Assert.assertFalse(u.isInvitationPending(invitation));
    }

    @And("user {int} sees the invitation sent as pending")
    public void userSeesTheInvitationAsPending(int arg0)
    {
        User u = userList.get(arg0 - 1);
        Assert.assertTrue(u.isInvitationSentPending(invitation));
    }

    @When("user {int} blacklists user {int}")
    public void userBlacklistsUser(int arg0, int arg1)
    {
        User u = userList.get(arg0 - 1);
        User u2 = userList.get(arg1 - 1);
        u.blacklist(u2);
    }

    @When("user {int} unblacklists user {int}")
    public void userUnblacklistsUser(int arg0, int arg1)
    {
        User u = userList.get(arg0 - 1);
        User u2 = userList.get(arg1 - 1);
        u.unblacklist(u2);
    }

    @Given("a session passed its subscription date")
    public void aSessionPassedItsSubscriptionDate() throws InvalidSessionDataException
    {
        session.setEntryDeadline(ZonedDateTime.now().minusDays(2));
    }

    @Given("a passed session")
    public void aPassedSession() throws InvalidSessionDataException
    {
        session.setStart(ZonedDateTime.now().minusDays(2));
    }

    @Then("user {int} is denied sending the invitation")
    public void userIsDeniedSendingTheInvitation(int arg0)
    {
        User u = userList.get(arg0 - 1);
        Assert.assertEquals(numberInvitationSent, u.numberInvitationSent());
    }

    @When("user {int} sends the invalid invitation to user {int}")
    public void userSendTheInvalidInvitationToUser(int arg0, int arg1)
    {
        User u = userList.get(arg0 - 1);
        try
        {
            invitation = u.invite(session, userList.get(arg1 - 1));
            Assert.fail();
        }
        catch (IllegalArgumentException e)
        {
            //success
        }
    }

    @Given("user {int} participating session")
    public void userParticipatingSession(int arg0) throws InvalidSessionDataException
    {
        User u = userList.get(arg0 - 1);
        session.participate(u);
    }

    @When("user {int} invites user {int} again")
    public void userInvitesUserAgain(int arg0, int arg1)
    {
        User u = userList.get(arg0 - 1);
        numberInvitationSent = u.numberInvitationSent();
        try
        {
            invitation = u.invite(session, userList.get(arg1 - 1));
            Assert.fail();
        }
        catch (IllegalArgumentException e)
        {
            //success
        }
    }
}
