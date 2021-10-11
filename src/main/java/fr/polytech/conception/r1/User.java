package fr.polytech.conception.r1;

import org.apache.commons.validator.routines.EmailValidator;

import java.util.HashMap;
import java.util.Map;

public class User
{
    private String pseudo;
    private String password;
    private String pathToProfilePicture;
    private String email;
    private String address;
    private String firstName;
    private String lastName;
    private Map<Sport, Level> favouriteSports;

    public User(String pseudo, String password, String email) throws InvalidProfileDataException
    {
        this.setPseudo(pseudo);
        this.setPassword(password);
        this.setEmail(email);
        this.favouriteSports = new HashMap<>();
    }

    /*
    pseudo rules :
        - must be at least 4 characters long
        - must be at maximum 32 characters long
     */
    public void setPseudo(String pseudo) throws InvalidProfileDataException
    {
        if(pseudo == null)
        {
            throw new InvalidProfileDataException("Given username is null !");
        }
        else
        {
            if(pseudo.length() < 4)
            {
                throw new InvalidProfileDataException("Given username is too short, it must be at least 4 characters long !");
            }
            else if(pseudo.length() > 32)
            {
                throw new InvalidProfileDataException("Given username is too long, the max allowed length is 32 characters !");
            }
            else
            {
                this.pseudo = pseudo;
            }
        }
    }

    /*
    password rules :
        - must be at least 8 characters long
     */
    public void setPassword(String password) throws InvalidProfileDataException
    {
        if(password == null)
        {
            throw new InvalidProfileDataException("Given password is null !");
        }
        else
        {
            if(password.length() < 8)
            {
                throw new InvalidProfileDataException("Given username is too short, it must be at least 4 characters long !");
            }
            else
            {
                this.password = password;
            }
        }
    }

    /*
    since the path to profile picture is abstract, there's no real check other than not null
     */
    public void setPathToProfilePicture(String pathToProfilePicture) throws InvalidProfileDataException
    {
        if(pathToProfilePicture == null)
        {
            throw new InvalidProfileDataException("Given path to profile picture is null !");
        }
        else
        {
            this.pathToProfilePicture = pathToProfilePicture;
        }
    }

    /*
    email must be validated via an external library (huge regex validation)
     */
    public void setEmail(String email) throws InvalidProfileDataException
    {
        if(email == null)
        {
            throw new InvalidProfileDataException("Given email is null !");
        }
        else
        {
            if(!EmailValidator.getInstance().isValid(email))
            {
                throw new InvalidProfileDataException("Invalid email format !");
            }
            else
            {
                this.email = email;
            }
        }
    }

    /*
    no real check other than adress is not null
     */
    public void setAddress(String address) throws InvalidProfileDataException
    {
        if(address == null)
        {
            throw new InvalidProfileDataException("Given address is null !");
        }
        else
        {
            this.address = address;
        }
    }

    /*
    no real costraint since names are a complex thing
     */
    public void setFirstName(String firstName) throws InvalidProfileDataException
    {
        if(firstName == null)
        {
            throw new InvalidProfileDataException("Given first name is null !");
        }
        else
        {
            this.firstName = firstName;
        }
    }

    /*
    no real costraint since names
     */
    public void setLastName(String lastName) throws InvalidProfileDataException
    {
        if(lastName == null)
        {
            throw new InvalidProfileDataException("Given last name is null !");
        }
        else
        {
            this.lastName = lastName;
        }
    }

    /*
    once again no real constraint other than not null
     */
    public void addSportToFavourites(Sport sport, Level level) throws InvalidProfileDataException
    {
        if(sport == null || level == null)
        {
            throw new InvalidProfileDataException("Given sport or level is null !");
        }
        else
        {
            favouriteSports.put(sport, level);
        }
    }

    public String getPseudo()
    {
        return pseudo;
    }

    public String getPassword()
    {
        return password;
    }

    public String getPathToProfilePicture()
    {
        return pathToProfilePicture;
    }

    public String getEmail()
    {
        return email;
    }

    public String getAddress()
    {
        return address;
    }

    public String getFirstName()
    {
        return firstName;
    }

    public String getLastName()
    {
        return lastName;
    }

    public Map<Sport, Level> getFavouriteSports()
    {
        return favouriteSports;
    }
}
