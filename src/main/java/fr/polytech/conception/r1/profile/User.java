package fr.polytech.conception.r1.profile;

import org.apache.commons.validator.routines.EmailValidator;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import fr.polytech.conception.r1.Level;
import fr.polytech.conception.r1.Session;
import fr.polytech.conception.r1.Sport;

public class User
{
    private final List<Session> listSessions = new ArrayList<>();
    private String pseudo;
    private String password;
    private String pathToProfilePicture;
    private String email;
    private String address;
    private String firstName;
    private String lastName;
    private Map<Sport, Level> favouriteSports;
    private List<Session> listSessionsOrganisees = new ArrayList<>();

    public User()
    {
    }

    public User(String pseudo, String password, String email) throws InvalidProfileDataException
    {
        this.setPseudo(pseudo);
        this.setPassword(password);
        this.setEmail(email);
        this.favouriteSports = new HashMap<>();
    }

    /*
    once again no real constraint other than not null
     */
    public void addSportToFavourites(Sport sport, Level level) throws InvalidProfileDataException
    {
        if (sport == null || level == null)
        {
            throw new InvalidProfileDataException("Given sport or level is null !");
        }
        else
        {
            favouriteSports.put(sport, level);
        }
    }

    public boolean participer(Session session)
    {
        listSessions.add(session);
        session.participer(this);
        return true;
    }

    public String getPseudo()
    {
        return pseudo;
    }

    /*
    pseudo rules :
        - must be at least 4 characters long
        - must be at maximum 32 characters long
     */
    public void setPseudo(String pseudo) throws InvalidProfileDataException
    {
        if (pseudo == null)
        {
            throw new InvalidProfileDataException("Given username is null !");
        }
        else
        {
            if (pseudo.length() < 4)
            {
                throw new InvalidProfileDataException("Given username is too short, it must be at least 4 characters long !");
            }
            else if (pseudo.length() > 32)
            {
                throw new InvalidProfileDataException("Given username is too long, the max allowed length is 32 characters !");
            }
            else
            {
                this.pseudo = pseudo;
            }
        }
    }

    public String getPassword()
    {
        return password;
    }

    /*
    password rules :
        - must be at least 8 characters long
     */
    public void setPassword(String password) throws InvalidProfileDataException
    {
        if (password == null)
        {
            throw new InvalidProfileDataException("Given password is null !");
        }
        else
        {
            if (password.length() < 8)
            {
                throw new InvalidProfileDataException("Given password is too short, it must be at least 8 characters long !");
            }
            else
            {
                this.password = password;
            }
        }
    }

    public String getPathToProfilePicture()
    {
        return pathToProfilePicture;
    }

    /*
    since the path to profile picture is abstract, there's no real check other than not null
     */
    public void setPathToProfilePicture(String pathToProfilePicture) throws InvalidProfileDataException
    {
        if (pathToProfilePicture == null)
        {
            throw new InvalidProfileDataException("Given path to profile picture is null !");
        }
        else
        {
            this.pathToProfilePicture = pathToProfilePicture;
        }
    }

    public String getEmail()
    {
        return email;
    }

    /*
    email must be validated via an external library (huge regex validation)
     */
    public void setEmail(String email) throws InvalidProfileDataException
    {
        if (email == null)
        {
            throw new InvalidProfileDataException("Given email is null !");
        }
        else
        {
            if (!EmailValidator.getInstance().isValid(email))
            {
                throw new InvalidProfileDataException("Invalid email format !");
            }
            else
            {
                this.email = email;
            }
        }
    }

    public String getAddress()
    {
        return address;
    }

    /*
    no real check other than adress is not null
     */
    public void setAddress(String address) throws InvalidProfileDataException
    {
        if (address == null)
        {
            throw new InvalidProfileDataException("Given address is null !");
        }
        else
        {
            this.address = address;
        }
    }

    public String getFirstName()
    {
        return firstName;
    }

    /*
    no real costraint since names are a complex thing
     */
    public void setFirstName(String firstName) throws InvalidProfileDataException
    {
        if (firstName == null)
        {
            throw new InvalidProfileDataException("Given first name is null !");
        }
        else
        {
            this.firstName = firstName;
        }
    }

    public String getLastName()
    {
        return lastName;
    }

    /*
    no real costraint since names are a complex thing
     */
    public void setLastName(String lastName) throws InvalidProfileDataException
    {
        if (lastName == null)
        {
            throw new InvalidProfileDataException("Given last name is null !");
        }
        else
        {
            this.lastName = lastName;
        }
    }

    public Map<Sport, Level> getFavouriteSports()
    {
        return favouriteSports;
    }

    public List<Session> getListSessionsOrganisees()
    {
        return listSessionsOrganisees;
    }

    public List<Session> getListSessions()
    {
        return listSessions;
    }


    public List<Session> chercherSessions(List<Session> sessions, Sport sport, String adresse, ZonedDateTime debut, ZonedDateTime fin, String organizer)
    {
        return sessions.stream()
                .filter(sport != null ? s -> s.getSport().equals(sport) : s -> true)
                .filter(adresse != null && !adresse.equals("") ? s -> s.getAdresse().contains(adresse) : s -> true)
                .filter(debut != null ? s -> s.getDebut().isAfter(debut) : s -> true)
                .filter(fin != null ? s -> s.getFin().isBefore(fin) : s -> true)
                .filter(s -> s.getDateLimiteInscription().isAfter(ZonedDateTime.now()))
                //.filter(organizer != null && !organizer.equals("") ? s -> s.getOrganisateur().getPseudo().equals(organizer) : s -> true)
                //.filter(s -> (s.getDifficulte() == Level.DEBUTANT ? true : this.favouriteSports.containsKey(s.getSport()) ? s.getDifficulte().compareTo(this.favouriteSports.get(s.getSport())) >= 0 : false))
                //todo rajouter pour les amis
                .collect(Collectors.toList());
    }

    public List<Session> chercherSessions(List<Session> sessions, String sport, String adresse, ZonedDateTime debut, ZonedDateTime fin)
    {
        return sessions.stream()
                .filter(sport != null && !sport.equals("") ? s -> s.getSport().getNom().equals(sport) : s -> true)
                .filter(adresse != null && !adresse.equals("") ? s -> s.getAdresse().contains(adresse) : s -> true)
                .filter(debut != null ? s -> s.getDebut().isAfter(debut) : s -> true)
                .filter(fin != null ? s -> s.getFin().isBefore(fin) : s -> true)
                .filter(s -> s.getDateLimiteInscription().isAfter(ZonedDateTime.now()))
                .filter(s -> (s.getDifficulte() == Level.DEBUTANT ? true : this.favouriteSports.containsKey(s.getSport()) ? s.getDifficulte().compareTo(this.favouriteSports.get(s.getSport())) >= 0 : false))
                .collect(Collectors.toList());
    }
}
