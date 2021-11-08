package fr.polytech.conception.r1.profile;

import org.apache.commons.validator.routines.EmailValidator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fr.polytech.conception.r1.Conversation;
import fr.polytech.conception.r1.InvalidSessionDataException;
import fr.polytech.conception.r1.Level;
import fr.polytech.conception.r1.Session;
import fr.polytech.conception.r1.Sport;

public class User
{
    private String pseudo;
    private String password;
    private String pathToProfilePicture;
    private String email;
    private String address;
    private String firstName;
    private String lastName;
    private Map<Sport, Level> favouriteSports = new HashMap<>();
    private List<Session> listSessionsOrganisees = new ArrayList<>();
    private List<Session> listSessions = new ArrayList<>();

    private List<User> friends = new ArrayList<>();
    private List<User> friendsRequested = new ArrayList<>();
    private List<User> friendsRequests = new ArrayList<>();
    private List<User> blacklistedUsers = new ArrayList<>();

    public User()
    {
    }

    public User(String pseudo, String password, String email) throws InvalidProfileDataException
    {
        this.setPseudo(pseudo);
        this.setPassword(password);
        this.setEmail(email);
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
        try
        {
            session.participer(this);
        }
        catch (InvalidSessionDataException e)
        {
            return false;
        }
        listSessions.add(session);
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

    /*
    adds this to the targetted user's friends requests, and user to this' friends requested
     */
    public void sendFriendRequest(User user) throws InvalidFriendshipException
    {
        if (friends.contains(user))
        {
            throw new InvalidFriendshipException("This user is already one of your friends.");
        }
        if (friendsRequested.contains(user))
        {
            throw new InvalidFriendshipException("You already sent a friend request to this user.");
        }
        user.getFriendsRequests().add(this);
        this.getFriendsRequested().add(user);
    }

    /*
    removes user from the friends requests list, and removes this from user's friends requested, and add each other in friends list
     */
    public void acceptFriendRequest(User user) throws InvalidFriendshipException
    {
        if (!friendsRequests.contains(user))
        {
            throw new InvalidFriendshipException("This user didn't sent a friend request to you.");
        }
        this.friendsRequests.remove(user);
        user.getFriendsRequested().remove(this);
        this.getFriends().add(user);
        user.getFriends().add(this);
    }

    /*
    removes user from the friends requests list, and removes this from user's friends requested
     */
    public void denyFriendRequest(User user) throws InvalidFriendshipException
    {
        if (!friendsRequests.contains(user))
        {
            throw new InvalidFriendshipException("This user didn't sent a friend request to you.");
        }
        this.friendsRequests.remove(user);
        user.getFriendsRequested().remove(this);
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

    public List<User> getFriends()
    {
        return friends;
    }

    public List<User> getFriendsRequested()
    {
        return friendsRequested;
    }

    public List<User> getFriendsRequests()
    {
        return friendsRequests;
    }

    public Conversation startConversation(User user2, String content)
    {
        return new Conversation(this, user2, content);
    }

    public void blacklist(User user)
    {
        if(!this.blacklistedUsers.contains(user))
        {
            this.blacklistedUsers.add(user);
        }
    }

    public void unblacklist(User user)
    {
        this.blacklistedUsers.remove(user);
    }

    public boolean haveIBlacklistedUser(User user)
    {
        return this.blacklistedUsers.contains(user);
    }
}
