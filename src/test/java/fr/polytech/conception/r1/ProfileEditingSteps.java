package fr.polytech.conception.r1;

import org.junit.Assert;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class ProfileEditingSteps
{

    private String validUsername;
    private String validPassword;
    private String validEmail;

    User validUser;

    @Given("a username {string}, a password {string} and an email address {string}")
    public void aUsernameAPasswordAndAnEmailAddress(String username, String password, String email)
    {
        validUsername = username;
        validPassword = password;
        validEmail = email;
    }

    @When("I try to create an account")
    public void iTryToCreateAnAccount()
    {
        try
        {
            validUser = new User(validUsername, validPassword, validEmail);
        }
        catch(InvalidProfileDataException e)
        {
            e.printStackTrace();
            Assert.fail();
        }
    }

    @Then("the account should be created")
    public void theAccountShouldBeCreated()
    {
        Assert.assertNotNull(validUser);
        Assert.assertNotNull(validUser.getPseudo());
        Assert.assertNotNull(validUser.getPassword());
        Assert.assertNotNull(validUser.getEmail());
    }

    String newUsername;

    @Given("an existing account and a valid username {string}")
    public void anExistingAccountAndAValidUsername(String username)
    {
        try
        {
            validUser = new User("validUsername", "validPassword", "validEmail@gmail.com");
        }
        catch(InvalidProfileDataException e)
        {
            e.printStackTrace();
            Assert.fail();
        }

        newUsername = username;
    }

    @When("I change my username to the valid username")
    public void iUpdateMyUsername()
    {
        try
        {
            validUser.setPseudo(newUsername);
        }
        catch (InvalidProfileDataException e)
        {
            e.printStackTrace();
            Assert.fail();
        }
    }

    @Then("My username should be updated")
    public void myUsernameShouldBeUpdated()
    {
        Assert.assertEquals(validUser.getPseudo(), newUsername);
    }

    String tooShortUsername;
    boolean errorRaised = false;

    @Given("an existing account and a too short username {string}")
    public void aTooShortUsername(String username)
    {
        try
        {
            validUser = new User("validUsername", "validPassword", "validEmail@gmail.com");
        }
        catch(InvalidProfileDataException e)
        {
            e.printStackTrace();
            Assert.fail();
        }

        tooShortUsername = username;
    }

    @When("I change my username to the too short username")
    public void iUpdateMyUsername1()
    {
        try
        {
            validUser.setPseudo(tooShortUsername);
        }
        catch (InvalidProfileDataException e)
        {
            errorRaised = true;
        }
    }

    @Then("An error should occur because the name is too short")
    public void anErrorOccurs()
    {
        Assert.assertTrue(errorRaised);
        Assert.assertNotEquals(validUser.getPseudo(), tooShortUsername);
    }

    String tooLongUsername;

    @Given("an existing account and a too long username {string}")
    public void anExistingAccountAndATooLongUsername(String username)
    {
        try
        {
            validUser = new User("validUsername", "validPassword", "validEmail@gmail.com");
        }
        catch(InvalidProfileDataException e)
        {
            e.printStackTrace();
            Assert.fail();
        }

        tooLongUsername = username;
    }

    @When("I change my username to the too long username")
    public void iChangeMyUsernameToTheTooLongUsername()
    {
        try
        {
            validUser.setPseudo(tooLongUsername);
        }
        catch (InvalidProfileDataException e)
        {
            errorRaised = true;
        }
    }

    @Then("An error occurs because the name is too long")
    public void anErrorOccursBecauseTheNameIsTooLong()
    {
        Assert.assertTrue(errorRaised);
        Assert.assertNotEquals(validUser.getPseudo(), tooLongUsername);
    }
}
