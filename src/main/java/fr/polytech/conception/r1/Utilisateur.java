package fr.polytech.conception.r1;

import org.apache.commons.validator.routines.EmailValidator;

import java.util.Map;
import java.util.regex.Pattern;

public class Utilisateur
{
    private String pseudo;
    private String password;
    private String pathToProfilePicture;
    private String email;
    private String adresse;
    private String prenom;
    private String nom;
    private Map<Sport, Niveau> sportFavoris;

    public Utilisateur(String pseudo, String password, String email) throws InvalidProfileDataException
    {
        this.setPseudo(pseudo);
        this.setPassword(password);
        this.email = email;
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

}
