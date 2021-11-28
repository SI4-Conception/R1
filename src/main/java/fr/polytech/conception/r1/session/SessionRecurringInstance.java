package fr.polytech.conception.r1.session;

import java.time.Duration;
import java.time.ZonedDateTime;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Stream;

import fr.polytech.conception.r1.InvalidSessionDataException;
import fr.polytech.conception.r1.Level;
import fr.polytech.conception.r1.Sport;
import fr.polytech.conception.r1.profile.User;

public class SessionRecurringInstance extends SessionOneshot
{
    private final SessionRecurring parent;

    public SessionRecurringInstance(ZonedDateTime start, ZonedDateTime end, String address, Sport sport, User organizer, boolean isSponsored, Duration minInscriptionTime, SessionRecurring parent) throws InvalidSessionDataException
    {
        super(start, end, address, sport, organizer, isSponsored);
        try
        {
            setMinInscriptionTime(minInscriptionTime);
        }
        catch (InvalidSessionDataException e)
        {
            // Ignore exception here
        }
        this.parent = parent;
    }

    public SessionRecurring getParent()
    {
        return parent;
    }

    private Stream<SessionRecurringInstance> getSessionsToChange()
    {
        return parent.getOneshotsAfter(getStart()).map(Map.Entry::getValue);
    }

    private boolean batchEditing = false;

    private void applyToRemainingSessions(SessionRecurring.SessionModification modification)
    {
        if (batchEditing)
            return;
        batchEditing = true;
        try
        {
            getSessionsToChange().forEach(s ->
            {
                s.batchEditing = true;
                try
                {
                    modification.apply(s);
                }
                catch (Exception e)
                {
                    throw new RuntimeException("Internal error during session modifying", e);
                }
                finally
                {
                    s.batchEditing = false;
                }
            });
        }
        finally
        {
            batchEditing = false;
        }
    }

    @Override
    public void setAddress(String address)
    {
        super.setAddress(address);
        applyToRemainingSessions(s -> s.setAddress(address));
    }

    @Override
    public void setFriendsOnly(boolean friendsOnly)
    {
        super.setFriendsOnly(friendsOnly);
        applyToRemainingSessions(s -> s.setFriendsOnly(friendsOnly));
    }

    @Override
    public void setMinParticipants(int minParticipants) throws InvalidSessionDataException
    {
        super.setMinParticipants(minParticipants);
        applyToRemainingSessions(s -> s.setMinParticipants(minParticipants));
    }

    @Override
    public void setMaxParticipants(int maxParticipants) throws InvalidSessionDataException
    {
        super.setMaxParticipants(maxParticipants);
        applyToRemainingSessions(s -> s.setMaxParticipants(maxParticipants));
    }

    @Override
    public void setDifficulty(Level difficulty)
    {
        super.setDifficulty(difficulty);
        applyToRemainingSessions(s -> s.setDifficulty(difficulty));
    }

    @Override
    public void setSport(Sport sport)
    {
        super.setSport(sport);
        applyToRemainingSessions(s -> s.setSport(sport));
    }

    protected SessionRecurringInstance cloneWithTimeOffset(Duration timeOffset)
    {
        SessionRecurringInstance clonedSession;
        try
        {
            clonedSession = new SessionRecurringInstance(this.getStart().plus(timeOffset), this.getEnd().plus(timeOffset), this.getAddress(), this.getSport(), this.getOrganizer(), this.isSponsored, this.getMinInscriptionTime(), this.getParent());
            clonedSession.setMinParticipants(this.getMinParticipants());
            clonedSession.setMaxParticipants(this.getMaxParticipants());
            clonedSession.setDifficulty(this.getDifficulty());
            clonedSession.setFriendsOnly(this.friendsOnly);
            clonedSession.setEntryDeadline(this.getEntryDeadline().plus(timeOffset));
            clonedSession.setSponsored(this.isSponsored);
            if(this.isSponsored)
            {
                clonedSession.setPrice(this.getPrice());
            }
        }
        catch (InvalidSessionDataException e)
        {
            throw new RuntimeException("Internal error during cloning session", e);
        }
        return clonedSession;
    }
}
