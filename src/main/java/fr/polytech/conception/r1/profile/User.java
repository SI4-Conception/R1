package fr.polytech.conception.r1.profile;

import org.apache.commons.validator.routines.EmailValidator;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import fr.polytech.conception.r1.Conversation;
import fr.polytech.conception.r1.InvalidSessionDataException;
import fr.polytech.conception.r1.Invitation;
import fr.polytech.conception.r1.Level;
import fr.polytech.conception.r1.Notification;
import fr.polytech.conception.r1.session.Session;
import fr.polytech.conception.r1.session.SessionOneshot;
import fr.polytech.conception.r1.Sport;

public class User
{
    private String nickname;
    private String password;
    private String pathToProfilePicture;
    private String email;
    private String address;
    private String firstName;
    private String lastName;
    private boolean isSpecialUser = false;
    private final Map<Sport, Level> favouriteSports = new HashMap<>();
    private final List<Session> organizedSessions = new ArrayList<>();
    private final List<Session> attendedSessions = new ArrayList<>();

    private final List<User> friends = new ArrayList<>();
    private final List<User> friendsRequested = new ArrayList<>();
    private final List<User> friendsRequests = new ArrayList<>();
    private final List<User> blacklistedUsers = new ArrayList<>();

    private final Set<Invitation> invitationSent = new HashSet<>();
    private final Map<Invitation.Status, Set<Invitation>> invitationReceived =
            Arrays.stream(Invitation.Status.values())
                    .collect(Collectors.toUnmodifiableMap(v -> v, v -> new HashSet<>()));

    private final ArrayList<Notification> notifications = new ArrayList<>();

    public void notify(Notification notification)
    {
        notifications.add(notification);
    }

    public User()
    {
    }

    public User(String nickname, String password, String email) throws InvalidProfileDataException
    {
        this.setNickname(nickname);
        this.setPassword(password);
        this.setEmail(email);
    }

    public ArrayList<Notification> getNotifications()
    {
        return notifications;
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

    public boolean participer(SessionOneshot session)
    {
        try
        {
            session.participer(this);
        }
        catch (InvalidSessionDataException e)
        {
            return false;
        }
        attendedSessions.add(session);
        return true;
    }

    public String getNickname()
    {
        return nickname;
    }

    /*
    pseudo rules :
        - must be at least 4 characters long
        - must be at maximum 32 characters long
     */
    public void setNickname(String nickname) throws InvalidProfileDataException
    {
        if (nickname == null)
        {
            throw new InvalidProfileDataException("Given username is null !");
        }
        else
        {
            if (nickname.length() < 4)
            {
                throw new InvalidProfileDataException("Given username is too short, it must be at least 4 characters long !");
            }
            else if (nickname.length() > 32)
            {
                throw new InvalidProfileDataException("Given username is too long, the max allowed length is 32 characters !");
            }
            else
            {
                this.nickname = nickname;
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

    public List<Session> getOrganizedSessions()
    {
        return organizedSessions;
    }

    public List<Session> getAttendedSessions()
    {
        return attendedSessions;
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
        if (!this.blacklistedUsers.contains(user))
        {
            this.blacklistedUsers.add(user);
            Set<Invitation> inv = this.invitationReceived.get(Invitation.Status.PENDING).stream().filter(i -> i.getOrganizer().equals(user)).collect(Collectors.toSet());
            if (!inv.isEmpty())
            {
                this.invitationReceived.get(Invitation.Status.PENDING).removeAll(inv);
                this.invitationReceived.get(Invitation.Status.BLACKLISTED).addAll(inv);
            }
        }
    }

    public void unblacklist(User user)
    {
        this.blacklistedUsers.remove(user);
        Set<Invitation> inv = this.invitationReceived.get(Invitation.Status.BLACKLISTED).stream().filter(i -> i.getOrganizer().equals(user)).collect(Collectors.toSet());
        if (!inv.isEmpty())
        {
            this.invitationReceived.get(Invitation.Status.BLACKLISTED).removeAll(inv);
            this.invitationReceived.get(Invitation.Status.PENDING).addAll(inv);
        }
    }

    public boolean haveIBlacklistedUser(User user)
    {
        return this.blacklistedUsers.contains(user);
    }

    public boolean hasSentInvitation(Invitation invitation)
    {
        return this.invitationSent.contains(invitation);
    }

    public int numberInvitationSent()
    {
        return this.invitationSent.size();
    }

    public Invitation invite(SessionOneshot session, User guest)
    {
        if (!this.getOrganizedSessions().contains(session))
        {
            throw new IllegalArgumentException("Given session not organized by inviting user");
        }

        if (session.getStart().isBefore(ZonedDateTime.now()))
        {
            throw new IllegalArgumentException("The invitation must occur before the session starts");
        }

        if (this.invitationSent.stream().anyMatch(i -> i.getGuest().equals(guest) && i.getSession().equals(session)))
        {
            throw new IllegalArgumentException("Invitation already sent");
        }

        if (session.getParticipants().contains(guest))
        {
            throw new IllegalArgumentException("Guest already participating session");
        }


        Invitation inv = new Invitation(this, guest, session);
        this.invitationSent.add(inv);
        guest.receiveInvitation(inv);
        return inv;
    }

    private void receiveInvitation(Invitation invitation)
    {
        if (this.haveIBlacklistedUser(invitation.getOrganizer()))
        {
            invitationReceived.get(Invitation.Status.BLACKLISTED).add(invitation);
        }
        else
        {
            invitationReceived.get(Invitation.Status.PENDING).add(invitation);
            invitation.notifyReceived();
        }
    }

    public void acceptInvitation(Invitation invitation) throws InvalidSessionDataException
    {
        if (!invitationReceived.get(Invitation.Status.PENDING).contains(invitation))
        {
            throw new IllegalArgumentException("Invitation to accept is not pending");
        }
        try
        {
            invitation.getSession().participer(this);
        }
        catch (InvalidSessionDataException e)
        {
            throw new InvalidSessionDataException("User cannot participate to session");
        }
        invitationReceived.get(Invitation.Status.PENDING).remove(invitation);
        invitation.notifyAccepted();
    }

    public void declineInvitation(Invitation invitation)
    {
        if (!invitationReceived.get(Invitation.Status.PENDING).contains(invitation))
        {
            throw new IllegalArgumentException("Invitation to decline is not pending");
        }
        invitationReceived.get(Invitation.Status.PENDING).remove(invitation);
        invitationReceived.get(Invitation.Status.DECLINED).add(invitation);
        invitation.notifyDeclined();
    }

    public boolean isInvitationPending(Invitation invitation)
    {
        return this.invitationReceived.get(Invitation.Status.PENDING).contains(invitation);
    }

    public boolean isInvitationSentPending(Invitation invitation)
    {
        if (invitation.getSession().getStart().isBefore(ZonedDateTime.now()))
        {
            throw new IllegalArgumentException("Invitation can only be checked if it hasn't occured yet");
        }
        return invitation.getGuest().invitationReceived.get(Invitation.Status.PENDING).contains(invitation) || invitation.getGuest().invitationReceived.get(Invitation.Status.BLACKLISTED).contains(invitation);
    }

    public boolean isInvitationDeclined(Invitation invitation)
    {
        return this.invitationReceived.get(Invitation.Status.DECLINED).contains(invitation);
    }

    public boolean isInvitationSentDeclined(Invitation invitation)
    {
        if (invitation.getSession().getStart().isBefore(ZonedDateTime.now()))
        {
            throw new IllegalArgumentException("Invitation can only be checked if it hasn't occured yet");
        }
        return invitation.getGuest().invitationReceived.get(Invitation.Status.DECLINED).contains(invitation);
    }

    public boolean isSpecialUser()
    {
        return isSpecialUser;
    }

    public void setSpecialUser(boolean specialUser)
    {
        isSpecialUser = specialUser;
    }

    public Stream<Invitation> getInvitationsList()
    {
        final List<Invitation> invitations = new ArrayList<>();
        invitationReceived.values().forEach(invitations::addAll);
        return invitations.stream();
    }
}
