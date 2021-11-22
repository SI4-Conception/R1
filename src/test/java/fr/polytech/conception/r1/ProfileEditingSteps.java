package fr.polytech.conception.r1;

import org.junit.Assert;

import fr.polytech.conception.r1.profile.InvalidProfileDataException;
import fr.polytech.conception.r1.profile.User;
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
        Assert.assertNotNull(validUser.getNickname());
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
            validUser.setNickname(newUsername);
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
        Assert.assertEquals(validUser.getNickname(), newUsername);
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
            validUser.setNickname(tooShortUsername);
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
        Assert.assertNotEquals(validUser.getNickname(), tooShortUsername);
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
        errorRaised = false;
    }

    @When("I change my username to the too long username")
    public void iChangeMyUsernameToTheTooLongUsername()
    {
        try
        {
            validUser.setNickname(tooLongUsername);
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
        Assert.assertNotEquals(validUser.getNickname(), tooLongUsername);
    }

    String newPassword;

    @Given("an existing account and a valid passsword {string}")
    public void anExistingAccountAndAValidPasssword(String password)
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

        newPassword = password;
    }

    @When("I change my password to the valid password")
    public void iChangeMyPasswordToTheValidPassword()
    {
        try
        {
            validUser.setPassword(newPassword);
        }
        catch (InvalidProfileDataException e)
        {
            e.printStackTrace();
            Assert.fail();
        }
    }

    @Then("My password should be updated")
    public void myPasswordShouldBeUpdated()
    {
        Assert.assertEquals(validUser.getPassword(), newPassword);
    }

    String tooShortPassword;

    @Given("an existing account and a too short passsword {string}")
    public void anExistingAccountAndATooShortPasssword(String password)
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

        tooShortPassword = password;
        errorRaised = false;
    }

    @When("I change my password to the too short password")
    public void iChangeMyPasswordToTheTooShortPassword()
    {
        try
        {
            validUser.setPassword(tooShortPassword);
        }
        catch (InvalidProfileDataException e)
        {
            errorRaised = true;
        }
    }

    @Then("An errors should occur because the password is too short")
    public void anErrorsShouldOccurBecauseThePasswordIsTooShort()
    {
        Assert.assertTrue(errorRaised);
        Assert.assertNotEquals(validUser.getPassword(), tooShortPassword);
    }

    String newPathToProfilePicture;

    @Given("an existing account and a valid profile picture path {string}")
    public void anExistingAccountAndAValidProfilePicturePath(String pathToProfilePicture)
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

        newPathToProfilePicture = pathToProfilePicture;
    }

    @When("I change my profile picture to the valid profile picture")
    public void iChangeMyProfilePictureToTheValidProfilePicture()
    {
        try
        {
            validUser.setPathToProfilePicture(newPathToProfilePicture);
        }
        catch (InvalidProfileDataException e)
        {
            e.printStackTrace();
            Assert.fail();
        }
    }

    @Then("My profile picture should be updated")
    public void myProfilePictureShouldBeUpdated()
    {
        Assert.assertEquals(validUser.getPathToProfilePicture(), newPathToProfilePicture);
    }

    String newEmail;

    @Given("an existing account and a valid email address {string}")
    public void anExistingAccountAndAValidEmailAddress(String email)
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

        newEmail = email;
    }

    @When("I change my email to the valid email address")
    public void iChangeMyPasswordToTheValidEmailAddress()
    {
        try
        {
            validUser.setEmail(newEmail);
        }
        catch (InvalidProfileDataException e)
        {
            e.printStackTrace();
            Assert.fail();
        }
    }

    @Then("My email address should be updated")
    public void myEmailAddressShouldBeUpdated()
    {
        Assert.assertEquals(validUser.getEmail(), newEmail);
    }

    String wrongEmail;

    @Given("an existing account and a wrong email {string}")
    public void anExistingAccountAndAWrongEmail(String email)
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

        wrongEmail = email;
        errorRaised = false;
    }

    @When("I change my email to the wrond email")
    public void iChangeMyEmailToTheWrondEmail()
    {
        try
        {
            validUser.setEmail(wrongEmail);
        }
        catch (InvalidProfileDataException e)
        {
            errorRaised = true;
        }
    }

    @Then("An error should occur because the email is wrong")
    public void anErrorShouldOccurBecauseTheEmailIsWrong()
    {
        Assert.assertTrue(errorRaised);
        Assert.assertNotEquals(validUser.getEmail(), wrongEmail);
    }

    String newAddress;

    @Given("an existing account and a valid address {string}")
    public void anExistingAccountAndAValidAddress(String address)
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

        newAddress = address;
    }

    @When("I change my address to the valid address")
    public void iChangeMyAddressToTheValidAddress()
    {
        try
        {
            validUser.setAddress(newAddress);
        }
        catch (InvalidProfileDataException e)
        {
            e.printStackTrace();
            Assert.fail();
        }
    }

    @Then("My address should be updated")
    public void myAddressShouldBeUpdated()
    {
        Assert.assertEquals(validUser.getAddress(), newAddress);
    }

    String newFirstName;

    @Given("an existing account and a valid first name {string}")
    public void anExistingAccountAndAValidFirstName(String firstName)
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

        newFirstName = firstName;
    }

    @When("I change my first name to the valid first name")
    public void iChangeMyAddressToTheValidFirstName()
    {
        try
        {
            validUser.setFirstName(newFirstName);
        }
        catch (InvalidProfileDataException e)
        {
            e.printStackTrace();
            Assert.fail();
        }
    }

    @Then("My first name should be updated")
    public void myFirstNameShouldBeUpdated()
    {
        Assert.assertEquals(validUser.getFirstName(), newFirstName);
    }

    String newLastName;

    @Given("an existing account and a valid last name {string}")
    public void anExistingAccountAndAValidLastName(String lastName)
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

        newLastName = lastName;
    }

    @When("I change my address to the valid last name")
    public void iChangeMyAddressToTheValidLastName()
    {
        try
        {
            validUser.setLastName(newLastName);
        }
        catch (InvalidProfileDataException e)
        {
            e.printStackTrace();
            Assert.fail();
        }
    }

    @Then("My last name should be updated")
    public void myLastNameShouldBeUpdated()
    {
        Assert.assertEquals(validUser.getLastName(), newLastName);
    }

    Sport favouriteSport;

    @Given("an existing account and a sport {sport}")
    public void anExistingAccountAndASport(Sport sport)
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

        favouriteSport = sport;
    }

    @When("I add this sport to my favourites sports")
    public void iAddThisSportToMyFavouritesSports()
    {
        try
        {
            validUser.addSportToFavourites(favouriteSport, Level.ADVANCED);
        }
        catch (InvalidProfileDataException e)
        {
            e.printStackTrace();
            Assert.fail();
        }
    }

    @Then("The sport should appear as favourite in my profile")
    public void theSportShouldAppearAsFavouriteInMyProfile()
    {
        Assert.assertTrue(validUser.getFavouriteSports().containsKey(favouriteSport));
    }

    Sport favouriteSport2, favouriteSport3;

    @Given("an existing account and some sports {sport} {sport} {sport}")
    public void anExistingAccountAndSomeSports(Sport sport, Sport sport2, Sport sport3)
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

        favouriteSport = sport;
        favouriteSport2 = sport2;
        favouriteSport3 = sport3;
    }

    @When("I add these sports to my favourites sports")
    public void iAddTheseSportsToMyFavouritesSports()
    {
        try
        {
            validUser.addSportToFavourites(favouriteSport, Level.INTERMEDIATE);
            validUser.addSportToFavourites(favouriteSport2, Level.ADVANCED);
            validUser.addSportToFavourites(favouriteSport3, Level.INTERMEDIATE);
        }
        catch (InvalidProfileDataException e)
        {
            e.printStackTrace();
            Assert.fail();
        }
    }

    @Then("The sports should appear as favourites in my profile")
    public void theSportsShouldAppearAsFavouritesInMyProfile()
    {
        Assert.assertTrue(validUser.getFavouriteSports().containsKey(favouriteSport));
        Assert.assertTrue(validUser.getFavouriteSports().containsKey(favouriteSport2));
        Assert.assertTrue(validUser.getFavouriteSports().containsKey(favouriteSport3));
    }
}
