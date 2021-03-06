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

public class NotificationSteps
{
    private final ZonedDateTime validDateTimeBegin = ZonedDateTime.parse("2030-02-01T12:00:00.000+01:00[Europe/Paris]");
    private final ZonedDateTime validDateTimeEnd = ZonedDateTime.parse("2030-02-01T15:00:00.000+01:00[Europe/Paris]");
    private final List<User> userList = new LinkedList<>();
    private SessionOneshot session;
    private Invitation invitation;

    @Given("{int} distinct users to notify")
    public void distinctUsersToNotify(int arg0)
    {
        for (int i = 0; i < arg0; i++)
        {
            userList.add(new User());
        }
    }

    @Given("a session organized by user {int} in notify")
    public void aSessionOrganizedByUserInNotify(int arg0) throws InvalidSessionDataException
    {
        User u = userList.get(arg0 - 1);
        session = new SessionOneshot(validDateTimeBegin, validDateTimeEnd, "75° 06′ 00″ S, 123° 19′ 58″ E", Sport.SKI, u, false);
    }

    @When("user {int} invites user {int} to the session in notify")
    public void userInvitesUserToTheSessionInNotify(int arg0, int arg1)
    {
        User u = userList.get(arg0 - 1);
        invitation = u.invite(session, userList.get(arg1 - 1));
    }

    @When("user {int} accepts the invitation in notify")
    public void userAcceptsTheInvitationInNotify(int arg0)
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

    @When("user {int} declines the invitation in notify")
    public void userDeclinesTheInvitation(int arg0)
    {
        User u = userList.get(arg0 - 1);
        u.declineInvitation(invitation);
    }

    @Then("user {int} can see the invitaion has been accepted through a notification")
    public void userCanSeeTheInvitaionHasBeenAcceptedThroughANotification(int arg0)
    {
        User u = userList.get(arg0 - 1);
        Assert.assertEquals(1, u.getNotifications().size());
        Assert.assertTrue(u.getNotifications().get(u.getNotifications().size() - 1) instanceof Invitation.InvitationAcceptedNotification);
    }

    @And("user {int} can see the invitation has been received through a notification")
    public void userCanSeeTheInvitationHasBeenReceivedThroughANotification(int arg0)
    {
        User u = userList.get(arg0 - 1);
        Assert.assertEquals(1, u.getNotifications().size());
        Assert.assertTrue(u.getNotifications().get(u.getNotifications().size() - 1) instanceof Invitation.InvitationReceivedNotification);
    }

    @Then("user {int} can see the invitaion has been declined through a notification")
    public void userCanSeeTheInvitaionHasBeenDeclinedThroughANotification(int arg0)
    {
        User u = userList.get(arg0 - 1);
        Assert.assertEquals(1, u.getNotifications().size());
        Assert.assertTrue(u.getNotifications().get(u.getNotifications().size() - 1) instanceof Invitation.InvitationDeclinedNotification);
    }

    @Given("user {int} has previoulsy blacklisted user {int}")
    public void userHasPrevioulsyBlacklistedUser(int arg0, int arg1)
    {
        User u = userList.get(arg0 - 1);
        User u2 = userList.get(arg1 - 1);
        u.blacklist(u2);
    }

    @Then("user {int} cannot see the invitation has been received through a notification")
    public void userCannotSeeTheInvitationHasBeenReceivedThroughANotification(int arg0)
    {
        User u = userList.get(arg0 - 1);
        Assert.assertEquals(0, u.getNotifications().size());
    }

    @Given("user {int} participating the session")
    public void userParticipatingTheSession(int arg0)
    {
        User u = userList.get(arg0 - 1);
        u.participate(session);
    }

    @When("user {int} unparticipates the session")
    public void userUnparticipatesTheSession(int arg0)
    {
        User u = userList.get(arg0 - 1);
        u.unparticipate(session);
    }

    @Then("user {int} can see the participation has been undone")
    public void userCanSeeTheParticipationHasBeenUndone(int arg0)
    {
        User u = userList.get(arg0 - 1);
        Assert.assertEquals(1, u.getNotifications().size());
        Assert.assertTrue(u.getNotifications().get(u.getNotifications().size() - 1) instanceof SessionOneshot.SessionCancelParticipationNotification);
    }

    @When("user {int} excludes user {int} from the session")
    public void userExcludesUserFromTheSession(int arg0, int arg1)
    {
        User u = userList.get(arg0 - 1);
        User u2 = userList.get(arg1 - 1);
        session.excludeUser(u2);
    }

    @Then("user {int} can see they have been excluded")
    public void userCanSeeTheyHaveBeenExcluded(int arg0)
    {
        User u = userList.get(arg0 - 1);
        Assert.assertEquals(1, u.getNotifications().size());
        Assert.assertTrue(u.getNotifications().get(u.getNotifications().size() - 1) instanceof
                SessionOneshot.SessionExcludedNotification);
    }

    @When("user {int} changes the location of the session")
    public void userChangesTheLocationOfTheSession(int arg0)
    {
        session.setAddress("30° 06′ 00″ S, 123° 19′ 58″ E");
    }

    @Then("user {int} can see a modification has occurred")
    public void userCanSeeAModificationHasOccurred(int arg0)
    {
        User u = userList.get(arg0 - 1);
        Assert.assertEquals(1, u.getNotifications().size());
        Assert.assertTrue(u.getNotifications().get(u.getNotifications().size() - 1) instanceof
                SessionOneshot.SessionEditedNotification);
    }

    @When("user {int} cancels the session")
    public void userCancelsTheSession(int arg0)
    {
        session.setCancelled(true);
    }

    @Then("user {int} can see the session has been canceled")
    public void userCanSeeTheSessionHasBeenCanceled(int arg0)
    {
        User u = userList.get(arg0 - 1);
        Assert.assertEquals(1, u.getNotifications().size());
        Assert.assertTrue(u.getNotifications().get(u.getNotifications().size() - 1) instanceof SessionOneshot.SessionCancelNotification);
    }

    @When("user {int} changes the start time of the session")
    public void userChangesTheStartTimeOfTheSession(int arg0) throws InvalidSessionDataException
    {
        session.setStart(ZonedDateTime.now().plusDays(3));
    }

    @When("user {int} changes the end time of the session")
    public void userChangesTheEndTimeOfTheSession(int arg0) throws InvalidSessionDataException
    {
        session.setEnd(ZonedDateTime.parse("2031-02-01T15:00:00.000+01:00[Europe/Paris]"));
    }

    @When("user {int} changes the maximum number of participants of the session")
    public void userChangesTheMaximumNumberOfParticipantsOfTheSession(int arg0) throws InvalidSessionDataException
    {
        session.setMaxParticipants(5);
    }
}
