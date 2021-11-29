package fr.polytech.conception.r1.session;

import java.time.Duration;
import java.time.ZonedDateTime;
import java.util.stream.Stream;

import fr.polytech.conception.r1.InvalidSessionDataException;
import fr.polytech.conception.r1.Level;
import fr.polytech.conception.r1.Sport;
import fr.polytech.conception.r1.profile.User;

public abstract class Session
{
    protected boolean friendsOnly = false;
    /**
     * Set maxParticipants to zero to specify infinite number of participants
     */
    protected int maxParticipants = 0;
    protected User organizer;
    protected Duration minRegistrationTime = Duration.ZERO;
    private String address;
    private int minParticipants = 1;
    private Level difficulty = Level.BEGINNER;
    private Sport sport;

    public Session(String address, Sport sport, User organizer) throws InvalidSessionDataException
    {
        this.organizer = organizer;
        this.address = address;
        this.sport = sport;
        this.organizer.getOrganizedSessions().add(this);
    }

    public Duration getMinRegistrationTime()
    {
        return minRegistrationTime;
    }

    abstract void setMinRegistrationTime(Duration d) throws InvalidSessionDataException;

    public String getAddress()
    {
        return address;
    }

    public void setAddress(String address)
    {
        this.address = address;
    }

    public boolean isFriendsOnly()
    {
        return friendsOnly;
    }

    public void setFriendsOnly(boolean friendsOnly)
    {
        this.friendsOnly = friendsOnly;
    }

    public int getMinParticipants()
    {
        return minParticipants;
    }

    public void setMinParticipants(int minParticipants) throws InvalidSessionDataException
    {
        checkParticipantsBounds(minParticipants, maxParticipants);
        this.minParticipants = minParticipants;
    }

    public int getMaxParticipants()
    {
        return maxParticipants;
    }

    public void setMaxParticipants(int maxParticipants) throws InvalidSessionDataException
    {
        checkParticipantsBounds(minParticipants, maxParticipants);
        this.maxParticipants = maxParticipants;
    }

    public Level getDifficulty()
    {
        return difficulty;
    }

    public void setDifficulty(Level difficulty)
    {
        this.difficulty = difficulty;
    }

    public Sport getSport()
    {
        return sport;
    }

    public void setSport(Sport sport)
    {
        this.sport = sport;
    }

    public User getOrganizer()
    {
        return organizer;
    }

    void checkDatesOrder(ZonedDateTime end, ZonedDateTime start) throws InvalidSessionDataException
    {
        if (end.isBefore(start))
        {
            throw new InvalidSessionDataException("Session must start before it ends");
        }
    }

    private void checkParticipantsBounds(int minParticipants, int maxParticipants) throws InvalidSessionDataException
    {
        if ((minParticipants > maxParticipants && maxParticipants != 0) || minParticipants < 0 || maxParticipants < 0)
        {
            throw new InvalidSessionDataException("Amount of participants must be <= to max number, and there must be a positive amount of them");
        }
    }

    public abstract Stream<? extends SessionOneshot> getOneshots(ZonedDateTime end);
}
