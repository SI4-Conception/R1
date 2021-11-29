package fr.polytech.conception.r1;

import org.junit.Assert;

import java.time.Duration;
import java.time.Period;
import java.time.ZonedDateTime;
import java.util.Optional;

import fr.polytech.conception.r1.profile.InvalidProfileDataException;
import fr.polytech.conception.r1.profile.User;
import fr.polytech.conception.r1.session.Session;
import fr.polytech.conception.r1.session.SessionOneshot;
import fr.polytech.conception.r1.session.SessionRecurring;
import fr.polytech.conception.r1.session.SessionRecurringBuilder;
import fr.polytech.conception.r1.session.SessionRecurringInstance;
import fr.polytech.conception.r1.session.SessionsList;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class RecurringSessionSteps
{
    private SessionsList sessionsList;
    private final User theo = new User("Theo", "The0Pa55w0rd", "theo@mail.com");
    private final User paul = new User("Paul", "PaulPa55w0rd", "paul@mail.com");
    private final User jacques = new User("Jacques", "JacquesAD1t", "jacques@mail.com");

    ZonedDateTime beginDate = ZonedDateTime.parse("3000-01-01T12:00:00.000+01:00[Europe/Paris]");

    private Sport validSport = Sport.TENNIS;

    private SessionRecurring recurringSession = null;

    public RecurringSessionSteps() throws InvalidProfileDataException
    {
    }

    @Given("An empty list of sessions for searching recurring sessions")
    public void anEmptyListOfSessionsForSearchingRecurringSessions()
    {
        sessionsList=SessionsList.getInstance();
        sessionsList.cleanAllSessions();
    }

    @Given("A recurring session of {string} at {string} created by theo")
    public void aRecurringSessionOfAtCreatedByTheo(String arg0, String arg1) throws InvalidSessionDataException
    {
        SessionRecurring sessionRecurring = new SessionRecurring(ZonedDateTime.parse(arg1), Period.ofDays(2), Duration.ofHours(12), "", Sport.getByName(arg0), theo);
        sessionsList.addSession(sessionRecurring);
    }

    @When("Paul participates to the recurring session of {string} at {string}")
    public void paulParticipatesToTheRecurringSessionOfSnorkelingAt(String arg0, String arg1)
    {
        Optional<SessionOneshot> foundSession = sessionsList.defaultSessionSearch(paul).filter(sessionOneshot -> sessionOneshot.getSport().getName().equals(arg0)).filter(sessionOneshot -> sessionOneshot.getStart().isEqual(ZonedDateTime.parse(arg1))).findFirst();
        Assert.assertTrue(foundSession.isPresent());
        paul.participate(foundSession.get());
    }

    @Then("Paul should participate to the recurring session of {string} at {string}")
    public void paulShouldParticipateToTheRecurringSessionOfAt(String arg0, String arg1)
    {
        for(int i = 0; i < paul.getAttendedSessions().size(); i++)
        {
            Session session = paul.getAttendedSessions().get(i);
            if(session.getOneshots(ZonedDateTime.parse(arg1).plusDays(1)).anyMatch(sessionOneshot -> sessionOneshot.getStart().isEqual(ZonedDateTime.parse(arg1)) && sessionOneshot.getSport().getName().equals(arg0)))
            {
                return;
            }
        }
        Assert.fail();
    }

    @Then("Paul should not participate to the recurring session of {string} at {string}")
    public void paulShouldNotParticipateToTheRecurringSessionOfAt(String arg0, String arg1)
    {
        for(int i = 0; i < paul.getAttendedSessions().size(); i++)
        {
            Session session = paul.getAttendedSessions().get(i);
            if(session.getOneshots(ZonedDateTime.parse(arg1).plusDays(1)).anyMatch(sessionOneshot -> sessionOneshot.getStart().isEqual(ZonedDateTime.parse(arg1)) && sessionOneshot.getSport().getName().equals(arg0)))
            {
                Assert.fail();
            }
        }
    }

    @When("Theo invites Paul to the recurring session of {string} at {string}")
    public void theoInvitesPaulToTheRecurringSessionOfAt(String arg0, String arg1)
    {
        Optional<SessionOneshot> foundSession = sessionsList.defaultSessionSearch(paul).filter(sessionOneshot -> sessionOneshot.getSport().getName().equals(arg0)).filter(sessionOneshot -> sessionOneshot.getStart().isEqual(ZonedDateTime.parse(arg1))).findFirst();
        Assert.assertTrue(foundSession.isPresent());
        theo.invite(foundSession.get(), paul);
    }

    @Then("Paul should be invited to the session of {string} at {string}")
    public void paulShouldBeInvitedToTheSessionOfAt(String arg0, String arg1)
    {
        Assert.assertTrue(paul.getInvitationsList().map(Invitation::getSession).anyMatch(sessionOneshot -> sessionOneshot.getStart().isEqual(ZonedDateTime.parse(arg1)) && sessionOneshot.getSport().getName().equals(arg0)));

    }

    @And("Paul should not be invited to the session of {string} at {string}")
    public void paulShouldNotBeInvitedToTheSessionOfAt(String arg0, String arg1)
    {
        Assert.assertFalse(paul.getInvitationsList().map(Invitation::getSession).anyMatch(sessionOneshot -> sessionOneshot.getStart().isEqual(ZonedDateTime.parse(arg1)) && sessionOneshot.getSport().getName().equals(arg0)));
    }

    @When("Theo cancels the recurring session of {string} at {string}")
    public void theoCancelsTheRecurringSessionOfAt(String arg0, String arg1)
    {
        Optional<SessionOneshot> foundSession = sessionsList.defaultSessionSearch(paul).filter(sessionOneshot -> sessionOneshot.getSport().getName().equals(arg0)).filter(sessionOneshot -> sessionOneshot.getStart().isEqual(ZonedDateTime.parse(arg1))).findFirst();
        Assert.assertTrue(foundSession.isPresent());
        foundSession.get().setCancelled(true);
    }

    @Then("The session of {string} at {string} sould be canceled")
    public void theSessionOfAtSouldBeCanceled(String arg0, String arg1)
    {
        Optional<SessionOneshot> foundSession = sessionsList.defaultSessionSearch(paul).filter(sessionOneshot -> sessionOneshot.getSport().getName().equals(arg0)).filter(sessionOneshot -> sessionOneshot.getStart().isEqual(ZonedDateTime.parse(arg1))).findFirst();
        Assert.assertTrue(foundSession.isPresent());
        Assert.assertTrue(foundSession.get().isCancelled());
    }

    @And("The session of {string} at {string} sould be not canceled")
    public void theSessionOfAtSouldBeNotCanceled(String arg0, String arg1)
    {
        Optional<SessionOneshot> foundSession = sessionsList.defaultSessionSearch(paul).filter(sessionOneshot -> sessionOneshot.getSport().getName().equals(arg0)).filter(sessionOneshot -> sessionOneshot.getStart().isEqual(ZonedDateTime.parse(arg1))).findFirst();
        Assert.assertTrue(foundSession.isPresent());
        Assert.assertFalse(foundSession.get().isCancelled());
    }

    @Given("A recurring session of {string} beginning today + {int} days created by theo")
    public void aRecurringSessionOfBeginningTodayMinCreatedByTheo(String arg0, int arg1) throws InvalidSessionDataException
    {
        SessionRecurring sessionRecurring = new SessionRecurring(ZonedDateTime.now().plusDays(arg1).plusMinutes(1), Period.ofDays(1), Duration.ofHours(12), "", Sport.getByName(arg0), theo);
        sessionsList.addSession(sessionRecurring);
    }

    @When("Theo sets the difficulty of the session of {string} at today + {int} days to {string}")
    public void theoSetsTheDifficultyOfTheSessionOfAtTodayDaysTo(String arg0, int arg1, String arg2)
    {
        Optional<SessionOneshot> foundSession = sessionsList.defaultSessionSearch(theo).filter(sessionOneshot -> sessionOneshot.getSport().getName().equals(arg0)).filter(sessionOneshot -> sessionOneshot.getStart().isAfter(ZonedDateTime.now().plusDays(arg1).minusMinutes(1)) && sessionOneshot.getStart().isBefore(ZonedDateTime.now().plusDays(arg1).plusMinutes(1))).findFirst();
        Assert.assertTrue(foundSession.isPresent());
        foundSession.get().setDifficulty(Level.getByString(arg2));
    }

    @And("We cannot see sessions that already occured")
    public void weCannotSeeSessionsThatAlreadyOccured()
    {
        Assert.assertTrue(sessionsList.defaultSessionSearch(theo).allMatch(sessionOneshot -> sessionOneshot.getStart().isAfter(ZonedDateTime.now())));
    }

    @When("Theo sets min participants of the session of {string} at today + {int} days to {int}")
    public void theoSetsMinParticipantsOfTheSessionOfAtTodayDaysTo(String arg0, int arg1, int arg2) throws InvalidSessionDataException
    {
        Optional<SessionOneshot> foundSession = sessionsList.defaultSessionSearch(theo).filter(sessionOneshot -> sessionOneshot.getSport().getName().equals(arg0)).filter(sessionOneshot -> sessionOneshot.getStart().isAfter(ZonedDateTime.now().plusDays(arg1).minusMinutes(1)) && sessionOneshot.getStart().isBefore(ZonedDateTime.now().plusDays(arg1).plusMinutes(1))).findFirst();
        Assert.assertTrue(foundSession.isPresent());
        foundSession.get().setMinParticipants(arg2);
    }

    @Then("All the sessions of {string} from today + {int} should be set to {string} level")
    public void allTheSessionsOfFromTodayShouldBeSetToLevel(String arg0, int arg1, String arg2)
    {
        Assert.assertTrue(sessionsList.defaultSessionSearch(theo).filter(sessionOneshot -> sessionOneshot.getSport().getName().equals(arg0)).filter(sessionOneshot -> sessionOneshot.getStart().isAfter(ZonedDateTime.now().plusDays(arg1+1))).allMatch(sessionOneshot -> sessionOneshot.getDifficulty().equals(Level.getByString(arg2))));
    }

    @Then("All the sessions of {string} before today + {int} should be set to {string} level")
    public void allTheSessionsOfBeforeTodayShouldBeSetToLevel(String arg0, int arg1, String arg2)
    {
        Assert.assertTrue(sessionsList.defaultSessionSearch(theo).filter(sessionOneshot -> sessionOneshot.getSport().getName().equals(arg0)).filter(sessionOneshot -> sessionOneshot.getStart().isBefore(ZonedDateTime.now().plusDays(arg1-1))).allMatch(sessionOneshot -> sessionOneshot.getDifficulty().equals(Level.getByString(arg2))));
    }

    @Given("Theo level for {string} set to {string}")
    public void theoLevelForSetTo(String arg0, String arg1) throws InvalidProfileDataException
    {
        theo.addSportToFavourites(Sport.getByName(arg0), Level.getByString(arg1));
    }

    @Then("All the sessions of {string} from today + {int} should have {int} min participants")
    public void allTheSessionsOfFromTodayShouldHaveMinParticipants(String arg0, int arg1, int arg2)
    {
        Assert.assertTrue(sessionsList.defaultSessionSearch(theo).filter(sessionOneshot -> sessionOneshot.getSport().getName().equals(arg0)).filter(sessionOneshot -> sessionOneshot.getStart().isAfter(ZonedDateTime.now().plusDays(arg1+1))).allMatch(sessionOneshot -> sessionOneshot.getMinParticipants() == arg2));
    }

    @Then("All the sessions of {string} before today + {int} should have {int} min participants")
    public void allTheSessionsOfBeforeTodayShouldHaveMinParticipants(String arg0, int arg1, int arg2)
    {
        Assert.assertTrue(sessionsList.defaultSessionSearch(theo).filter(sessionOneshot -> sessionOneshot.getSport().getName().equals(arg0)).filter(sessionOneshot -> sessionOneshot.getStart().isBefore(ZonedDateTime.now().plusDays(arg1-1))).allMatch(sessionOneshot -> sessionOneshot.getMinParticipants() == arg2));
    }

    @Given("A recurring session of {string} beginning today + {int} days created by theo with a limit registration time of {int} days")
    public void aRecurringSessionOfBeginningTodayDaysCreatedByTheoWithALimitRegistrationTimeOfDays(String arg0, int arg1, int arg2) throws InvalidSessionDataException
    {
        SessionRecurring sessionRecurring = new SessionRecurring(ZonedDateTime.now().plusDays(arg1), Period.ofDays(1), Duration.ofHours(12), "", Sport.getByName(arg0), Duration.ofDays(arg2), theo);
        sessionsList.addSession(sessionRecurring);
    }

    @When("Paul tries to participate to the {string} session of today + {int} days")
    public void paulTriesToParticipateToTheSessionOfTodayDays(String arg0, int arg1)
    {
        Optional<SessionOneshot> foundSession = sessionsList.defaultSessionSearch(paul)
                .filter(sessionOneshot -> sessionOneshot.getSport().getName().equals(arg0))
                .filter(sessionOneshot -> sessionOneshot.getStart().isAfter(ZonedDateTime.now().plusDays(arg1-1)))
                .findFirst();
        Assert.assertTrue(foundSession.isPresent());
        paul.participate(foundSession.get());
    }

    @Then("Paul can participate to the session of {string}")
    public void paulCanParticipateToTheSessionOf(String arg0)
    {
        Assert.assertTrue(paul.getAttendedSessions().stream().anyMatch(session -> session.getSport().getName().equals(arg0)));
        Assert.assertTrue(sessionsList.defaultSessionSearch(theo).filter(session -> session.getSport().getName().equals(arg0)).anyMatch(sessionOneshot -> sessionOneshot.getParticipants().contains(paul)));
    }

    @Then("Paul cannot participate to the session of {string}")
    public void paulCannotParticipateToTheSessionOf(String arg0)
    {
        Assert.assertFalse(paul.getAttendedSessions().stream().anyMatch(session -> session.getSport().getName().equals(arg0)));
        Assert.assertFalse(sessionsList.defaultSessionSearch(theo).filter(session -> session.getSport().getName().equals(arg0)).anyMatch(sessionOneshot -> sessionOneshot.getParticipants().contains(paul)));
    }

    @When("Theo tries to create a recurring session of {string} beginning today - {int} days")
    public void theoTriesToCreateARecurringSessionOfBeginningTodayDays(String arg0, int arg1)
    {
        Assert.assertThrows(InvalidSessionDataException.class, () -> new SessionRecurring(ZonedDateTime.now().minusDays(arg1), Period.ofDays(1), Duration.ofHours(12), "", Sport.getByName(arg0), theo));
    }

    @Then("The recurring session of {string} doesn't exist")
    public void theRecurringSessionOfDoesnTExist(String arg0)
    {
        Assert.assertFalse(sessionsList.defaultSessionSearch(theo).anyMatch(sessionOneshot -> sessionOneshot.getSport().getName().equals(arg0)));
    }
    
    @Given("Valid sport for this recurring session: {string}")
    public void validSportForThisRecurringSessionArg(String arg0)
    {
        validSport = Sport.getByName(arg0);
    }

    @When("I create the recurring session with these valid data beginning today + {int} days")
    public void iCreateTheRecurringSessionWithTheseValidData(int arg0) throws InvalidSessionDataException
    {
        try
        {
            recurringSession = new SessionRecurringBuilder().setFirst(ZonedDateTime.now().plusDays(arg0).plusMinutes(1)).setPeriod(Period.ofDays(1)).setDuration(Duration.ofHours(12)).setAddress("").setSport(validSport).setOrganizer(theo).createSessionRecurring();
        }
        catch (InvalidSessionDataException e)
        {
            e.printStackTrace();
            Assert.fail();
        }
    }

    @Then("I should have a valid recurring session")
    public void iShouldHaveAValidRecurringSession()
    {
        Assert.assertNotNull(recurringSession);
    }

    @When("Theo sets the limit registration time for the recurring session of {string} to {int} days")
    public void theoSetsTheLimitRegistrationTimeForTheRecurringSessionOfToDays(String arg0, int arg1)
    {
        Optional<SessionOneshot> foundSession = sessionsList.defaultSessionSearch(theo)
                .filter(sessionOneshot -> sessionOneshot.getSport().getName().equals(arg0))
                .findFirst();
        Assert.assertTrue(foundSession.isPresent());
        Assert.assertTrue(foundSession.get() instanceof SessionRecurringInstance);
        Assert.assertTrue(((SessionRecurringInstance)foundSession.get()).setMinRegistrationTimeForSiblingSessions(Duration.ofDays(arg1)));
    }

    @When("Theo tries to set the limit registration time for the recurring session of {string} to {int} days")
    public void theoTriesToSetTheLimitRegistrationTimeForTheRecurringSessionOfToDays(String arg0, int arg1)
    {
        Optional<SessionOneshot> foundSession = sessionsList.defaultSessionSearch(theo)
                .filter(sessionOneshot -> sessionOneshot.getSport().getName().equals(arg0))
                .findFirst();
        Assert.assertTrue(foundSession.isPresent());
        Assert.assertTrue(foundSession.get() instanceof SessionRecurringInstance);
        Assert.assertFalse(((SessionRecurringInstance)foundSession.get()).setMinRegistrationTimeForSiblingSessions(Duration.ofDays(arg1)));
    }

    @Then("Paul cannot participate to the session of {string} of today + {int} days")
    public void paulCannotParticipateToTheSessionOfOfTodayDays(String arg0, int arg1)
    {
        ZonedDateTime timeToCheck = ZonedDateTime.now().plusDays(arg1);
        Assert.assertFalse(paul.getAttendedSessions()
                .stream()
                .map(session -> (SessionOneshot)session)
                .anyMatch(session -> session.getSport().getName().equals(arg0) && session.getStart().isAfter(timeToCheck.minusDays(1)) && session.getStart().isBefore(timeToCheck.plusDays(1))));
        Assert.assertFalse(sessionsList.defaultSessionSearch(theo)
                .filter(session -> session.getSport().getName().equals(arg0))
                .filter(session -> session.getStart().isAfter(timeToCheck.minusDays(1)) && session.getStart().isBefore(timeToCheck.plusDays(1)))
                .anyMatch(sessionOneshot -> sessionOneshot.getParticipants().contains(paul)));
    }

    @And("Jacques can participate to the session of {string} of today + {int} days")
    public void jacquesCanParticipateToTheSessionOfOfTodayDays(String arg0, int arg1)
    {
        Optional<SessionOneshot> foundSession = sessionsList.defaultSessionSearch(jacques)
                .filter(sessionOneshot -> sessionOneshot.getSport().getName().equals(arg0))
                .filter(sessionOneshot -> sessionOneshot.getStart().isAfter(ZonedDateTime.now().plusDays(arg1-1)))
                .findFirst();
        Assert.assertTrue(foundSession.isPresent());
        Assert.assertTrue(jacques.participate(foundSession.get()));
        Assert.assertTrue(jacques.getAttendedSessions().stream().anyMatch(session -> session.getSport().getName().equals(arg0)));
        Assert.assertTrue(sessionsList.defaultSessionSearch(theo)
                .filter(session -> session.getSport().getName().equals(arg0))
                .anyMatch(sessionOneshot -> sessionOneshot.getParticipants().contains(paul)));
    }

    @When("Jacques tries to participate to the {string} session of today + {int} days")
    public void jacquesTriesToParticipateToTheSessionOfTodayDays(String arg0, int arg1)
    {
        Optional<SessionOneshot> foundSession = sessionsList.defaultSessionSearch(jacques)
                .filter(sessionOneshot -> sessionOneshot.getSport().getName().equals(arg0))
                .filter(sessionOneshot -> sessionOneshot.getStart().isAfter(ZonedDateTime.now().plusDays(arg1-1)))
                .findFirst();
        Assert.assertTrue(foundSession.isPresent());
        jacques.participate(foundSession.get());
    }

    @And("Theo tries to set max participants of the {string} session of today + {int} days to {int}")
    public void theoTriesToSetMaxParticipantsOfTheSessionOfTodayDaysTo(String arg0, int arg1, int arg2)
    {
        Optional<SessionOneshot> foundSession = sessionsList.defaultSessionSearch(theo)
                .filter(sessionOneshot -> sessionOneshot.getSport().getName().equals(arg0))
                .filter(sessionOneshot -> sessionOneshot.getStart().isAfter(ZonedDateTime.now().plusDays(arg1 - 1)))
                .filter(sessionOneshot -> sessionOneshot.getStart().isBefore(ZonedDateTime.now().plusDays(arg1 + 1)))
                .findFirst();
        Assert.assertTrue(foundSession.isPresent());
        Assert.assertTrue(foundSession.get() instanceof SessionRecurringInstance);
        ((SessionRecurringInstance)foundSession.get()).setMaxParticipants(arg2);
    }

    @Then("The max users of the {string} session of today + {int} days should be infinite")
    public void theMaxUsersOfTheSessionOfTodayDaysShouldBeInfinite(String arg0, int arg1)
    {
        Optional<SessionOneshot> foundSession = sessionsList.defaultSessionSearch(theo)
                .filter(sessionOneshot -> sessionOneshot.getSport().getName().equals(arg0))
                .filter(sessionOneshot -> sessionOneshot.getStart().isAfter(ZonedDateTime.now().plusDays(arg1 - 1)))
                .filter(sessionOneshot -> sessionOneshot.getStart().isBefore(ZonedDateTime.now().plusDays(arg1 + 1)))
                .findFirst();
        Assert.assertTrue(foundSession.isPresent());
        Assert.assertEquals(0, foundSession.get().getMaxParticipants());
    }

    @Then("The max users of the {string} session of today + {int} days should be {int}")
    public void theMaxUsersOfTheSessionOfTodayDaysShouldBe(String arg0, int arg1, int arg2)
    {
        Optional<SessionOneshot> foundSession = sessionsList.defaultSessionSearch(theo)
                .filter(sessionOneshot -> sessionOneshot.getSport().getName().equals(arg0))
                .filter(sessionOneshot -> sessionOneshot.getStart().isAfter(ZonedDateTime.now().plusDays(arg1 - 1)))
                .filter(sessionOneshot -> sessionOneshot.getStart().isBefore(ZonedDateTime.now().plusDays(arg1 + 1)))
                .findFirst();
        Assert.assertTrue(foundSession.isPresent());
        Assert.assertEquals(arg2, foundSession.get().getMaxParticipants());
    }
}
