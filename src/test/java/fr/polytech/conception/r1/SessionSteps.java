package fr.polytech.conception.r1;

import org.junit.Assert;

import fr.polytech.conception.r1.profile.User;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import java.time.ZonedDateTime;

public class SessionSteps
{
    private ZonedDateTime validDateTimeBegin = ZonedDateTime.parse("2030-02-01T12:00:00.000+01:00[Europe/Paris]");
    private ZonedDateTime validDateTimeEnd = ZonedDateTime.parse("2030-02-01T15:00:00.000+01:00[Europe/Paris]");
    private int validMinParticipants = 4;
    private int validMaxParticipants = 55;
    private final ZonedDateTime invalidDateTimeBegin = ZonedDateTime.parse("2030-02-02T12:00:00.000+01:00[Europe/Paris]");
    private final ZonedDateTime invalidDateTimeEnd = ZonedDateTime.parse("2030-02-01T15:00:00.000+01:00[Europe/Paris]");
    private final ZonedDateTime validDateTimeEndSubscription = ZonedDateTime.parse("2030-01-01T12:00:00.000+01:00[Europe/Paris]");
    private final ZonedDateTime invalidDateTimeEndSubscription = ZonedDateTime.parse("2035-01-01T12:00:00.000+01:00[Europe/Paris]");
    private String validAddress = "14 rue Bolchaia Loubianka";
    private Sport validSport = Sport.KAYAK;
    private final User julien = new User();
    private final User louis = new User();

    private Session session = null;

    @Given("Valid begin time for the session: {string}")
    public void validBeginTimeForTheSession(String arg0)
    {
        validDateTimeBegin = ZonedDateTime.parse(arg0);
    }

    @Given("Valid end time for the session: {string}")
    public void validEndTimeForTheSession(String arg0)
    {
        validDateTimeEnd = ZonedDateTime.parse(arg0);
    }

    @Given("Valid address for the session {string}")
    public void validAddressForTheSession(String arg0)
    {
        validAddress = arg0;
    }

    @Given("Valid sport for the session {string}")
    public void validSportForTheSession(String arg0)
    {
        validSport = Sport.getByName(arg0);
    }

    @Given("Valid number of min participants: {int}")
    public void validNumberOfMinParticipants(int arg0)
    {
        validMinParticipants = arg0;
    }

    @Given("Valid number of max participants: {int}")
    public void validNumberOfMaxParticipants(int arg0)
    {
        validMaxParticipants = arg0;
    }

    @When("I create the session with these valid data")
    public void iCreateTheSession()
    {
        try
        {
            session = new Session(validDateTimeBegin, validDateTimeEnd, validAddress, validSport, julien);
        }
        catch (InvalidSessionDataException e)
        {
            e.printStackTrace();
            Assert.fail();
        }
    }

    @Then("I should have a valid session")
    public void iShouldHaveAValidSession()
    {
        Assert.assertNotNull(session);
    }


    @Given("Previously created session without any condition for participants")
    public void previouslyCreatedSession()
    {
        try
        {
            session = new Session(validDateTimeBegin, validDateTimeEnd, validAddress, validSport, julien);
        }
        catch (InvalidSessionDataException e)
        {
            e.printStackTrace();
            Assert.fail();
        }
    }

    @When("I change the session settings with valid datas")
    public void iChangeTheSessionSettingsWithValidDatas() throws InvalidSessionDataException
    {
        session.setMaxParticipants(validMaxParticipants);
        session.setMinParticipants(validMinParticipants);
        session.setFin(validDateTimeEnd);
        session.setDebut(validDateTimeBegin);
    }

    @Then("I should have the changed valid session")
    public void iShouldHaveTheChangedValidSession()
    {
        Assert.assertNotNull(session);
        Assert.assertEquals(validMinParticipants, session.getMinParticipants());
        Assert.assertEquals(validMaxParticipants, session.getMaxParticipants());
        Assert.assertEquals(validDateTimeEnd, session.getFin());
        Assert.assertEquals(validDateTimeBegin, session.getDebut());
    }

    @Given("Invalid time data for the session")
    public void invalidTimeDataForTheSession()
    {
    }

    @When("I try to create the session with invalid dates")
    public void iTryToCreateTheSessionWithInvalidDates()
    {
        try
        {
            session = new Session(invalidDateTimeBegin, invalidDateTimeEnd, validAddress, validSport, julien);
            Assert.fail();
        }
        catch (InvalidSessionDataException e)
        {
            // Success
        }
    }

    @Then("I should fail and should't have a session")
    public void iShouldFailAndShouldTHaveASession()
    {
        Assert.assertNull(session);
    }

    @Given("{string} wants to create a session at the same time as another session he has already created")
    public void wantsToCreateASessionAtTheSameTimeAsAnotherSessionHeHasAlreadyCreated(String arg0)
    {
        try {
            session = new Session(validDateTimeBegin, validDateTimeEnd, validAddress, validSport, julien);
        }
        catch (InvalidSessionDataException e)
        {
            e.printStackTrace();
            Assert.fail();
        }
    }

    @When("I try to create a session at the same time as another session I have already created")
    public void iTryToCreateASessionAtTheSameTimeAsAnotherSessionIHaveAlreadyCreated()
    {
        try {
            session = new Session(validDateTimeBegin, validDateTimeEnd, validAddress, validSport, julien);
            Assert.fail();
        }
        catch (InvalidSessionDataException e)
        {
            // Success
        }
    }

    @Then("the session is not created")
    public void theSessionIsNotCreated()
    {
    }

    @Given("Previously created session with correct {int} min and {int} max users")
    public void previouslyCreatedSessionWithCorrectMinAndMaxUsers(int arg0, int arg1)
    {
        try
        {
            session = new Session(validDateTimeBegin, validDateTimeEnd, validAddress, validSport, julien);
            session.setMaxParticipants(arg1);
            session.setMinParticipants(arg0);
        }
        catch (InvalidSessionDataException e)
        {
            e.printStackTrace();
            Assert.fail();
        }
    }

    @When("I try to set {int} min users")
    public void iTryToSetMinUsers(int arg0)
    {
        try
        {
            session.setMinParticipants(arg0);
        }
        catch (InvalidSessionDataException ignored)
        {
        }
    }

    @When("I try to set {int} max users")
    public void iTryToSetMaxUsers(int arg0)
    {
        try
        {
            session.setMaxParticipants(arg0);
        }
        catch (InvalidSessionDataException ignored)
        {
        }
    }

    @Then("I should have a session with {int} min and {int} max users")
    public void iShouldHaveASessionWithMinAndMaxUsers(int arg0, int arg1)
    {
        Assert.assertNotNull(session);
        Assert.assertEquals(arg0, session.getMinParticipants());
        Assert.assertEquals(arg1, session.getMaxParticipants());
    }

    @When("I try to set a valid end subscription date")
    public void iTryToSetAValidEndSubscriptionDate()
    {
        try
        {
            session.setDateLimiteInscription(validDateTimeEndSubscription);
        }
        catch (InvalidSessionDataException ignored)
        {
            //
        }
    }

    @Then("I should have a session with this new end subscription date")
    public void iShouldHaveASessionWithThisNewEndSubscriptionDate()
    {
        Assert.assertTrue(validDateTimeEndSubscription.isEqual(session.getDateLimiteInscription()));
    }

    @Given("Previously created session with valid end subscription date")
    public void previouslyCreatedSessionWithValidEndSubscriptionDate()
    {
        try
        {
            session = new Session(validDateTimeBegin, validDateTimeEnd, validAddress, validSport,julien);
            session.setDateLimiteInscription(validDateTimeEndSubscription);
        }
        catch (InvalidSessionDataException e)
        {
            e.printStackTrace();
            Assert.fail();
        }
    }

    @When("I try to set a invalid end subscription date")
    public void iTryToSetAInvalidEndSubscriptionDate()
    {
        try
        {
            session.setDateLimiteInscription(invalidDateTimeEndSubscription);
        }
        catch (InvalidSessionDataException ignored)
        {
        }
    }

    @Then("I should have a session with old end subscription date")
    public void iShouldHaveASessionWithOldEndSubscriptionDate()
    {
        Assert.assertTrue(validDateTimeEndSubscription.isEqual(session.getDateLimiteInscription()));
    }

    @When("I try to participate in a session found")
    public void iTryToParticipateInASessionFound()
    {
        louis.participer(session);
    }

    @Then("The registration is taken into account by the session")
    public void theRegistrationIsTakenIntoAccountByTheSession()
    {
        Assert.assertTrue(session.getParticipants().contains(louis));
        Assert.assertTrue(louis.getListSessions().contains(session));
    }

    @Given("Previously created session with {int} users at max and already {int} participants")
    public void previouslyCreatedSessionWithUsersAtMaxAndAlreadyParticipants(int arg0, int arg1)
    {
        try
        {
            session = new Session(validDateTimeBegin, validDateTimeEnd, validAddress, validSport, julien);
            session.setMaxParticipants(arg0);
            for(int i=0; i<arg1; i++)
            {
                User u = new User();
                u.participer(session);
            }
        }
        catch (InvalidSessionDataException e)
        {
            e.printStackTrace();
            Assert.fail();
        }
    }

    @Then("The registration is not taken into account by the session")
    public void theRegistrationIsNotTakenIntoAccountByTheSession()
    {
        Assert.assertFalse(session.getParticipants().contains(louis));
        Assert.assertFalse(louis.getListSessions().contains(session));
    }

    @Then("I cannot participate a second time to the session")
    public void iCannotParticipateASecondTimeToTheSession()
    {
        Assert.assertFalse(louis.participer(session));
    }

    @When("{int} users participate to the session")
    public void usersParticipateToTheSession(int arg0)
    {
        for(int i = 0; i < arg0; i++)
        {
            User u = new User();
            Assert.assertTrue(u.participer(session));
        }
    }

    @Then("I cannot set the max number of participants to {int}")
    public void iCannotSetTheMaxNumberOfParticipantsTo(int arg0)
    {
        Assert.assertThrows(InvalidSessionDataException.class, () -> session.setMaxParticipants(arg0));
    }
}
