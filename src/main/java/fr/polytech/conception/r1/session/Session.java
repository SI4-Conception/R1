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
    private String address;

    protected boolean friendsOnly = false;
    private int minParticipants = 1;
    /**
     * Set maxParticipants to zero to specify infinite number of participants
     */
    protected int maxParticipants = 0;
    private Level difficulty = Level.BEGINNER;
    private Sport sport;
    protected User organizer;
    protected Duration minInscriptionTime = Duration.ZERO;

    public Session(String address, Sport sport, User organizer) throws InvalidSessionDataException
    {
        this.organizer = organizer;
        this.address = address;
        this.sport = sport;
        this.organizer.getOrganizedSessions().add(this);
    }

    abstract void setMinInscriptionTime(Duration d) throws InvalidSessionDataException;

    public Duration getMinInscriptionTime()
    {
        return minInscriptionTime;
    }

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
            throw new InvalidSessionDataException("La date de fin est avant le debut.");
        }
    }

    private void checkParticipantsBounds(int minParticipants, int maxParticipants) throws InvalidSessionDataException
    {
        if ((minParticipants > maxParticipants && maxParticipants != 0) || minParticipants < 0 || maxParticipants < 0)
        {
            throw new InvalidSessionDataException("Le nombre min de participants doit etre <= au nombre max, et leur nombres doivent etre positifs");
        }
    }

    public abstract Stream<? extends SessionOneshot> getOneshots(ZonedDateTime end);
}
