package fr.polytech.conception.r1;

import java.time.ZonedDateTime;

import fr.polytech.conception.r1.profile.User;
import fr.polytech.conception.r1.session.SessionOneshot;

public class Invitation
{
    private final User organizer;
    private final User guest;
    private final SessionOneshot session;

    public Invitation(User organizer, User guest, SessionOneshot session)
    {
        if (session.getStart().isBefore(ZonedDateTime.now()))
        {
            throw new IllegalArgumentException("Cannot create an invitation of a passed session");
        }
        this.organizer = organizer;
        this.guest = guest;
        this.session = session;
    }

    public User getOrganizer()
    {
        return organizer;
    }

    public User getGuest()
    {
        return guest;
    }

    public SessionOneshot getSession()
    {
        return session;
    }

    public enum Status {
        PENDING,
        //ACCEPTED,
        DECLINED,
        ARCHIVED,
        BLACKLISTED
    }

    public void notifyAccepted()
    {
        notifyOrganizer(new InvitationAcceptedNotification(this));
    }

    public void notifyDeclined()
    {
        notifyOrganizer(new InvitationDeclinedNotification(this));
    }

    public void notifyReceived()
    {
        notifyGuest(new InvitationReceivedNotification(this));
    }

    private void notifyOrganizer(Notification notification)
    {
        organizer.notify(notification);
    }

    private void notifyGuest(Notification notification)
    {
        guest.notify(notification);
    }

    public static class InvitationReceivedNotification extends Notification
    {
        private final Invitation invitation;
        public InvitationReceivedNotification(Invitation invitation)
        {
            super();
            this.invitation = invitation;
        }

        public Invitation getInvitation()
        {
            return invitation;
        }

        @Override
        public String getMessage()
        {
            return "invitation received";
        }
    }

    public static class InvitationAcceptedNotification extends Notification
    {
        private final Invitation invitation;
        public InvitationAcceptedNotification(Invitation invitation)
        {
            super();
            this.invitation = invitation;
        }

        public Invitation getInvitation()
        {
            return invitation;
        }

        @Override
        public String getMessage()
        {
            return "invitation accepted";
        }
    }

    public static class InvitationDeclinedNotification extends Notification
    {
        private final Invitation invitation;
        public InvitationDeclinedNotification(Invitation invitation)
        {
            super();
            this.invitation = invitation;
        }
        public Invitation getInvitation()
        {
            return invitation;
        }

        @Override
        public String getMessage()
        {
            return "invitation declined";
        }
    }
}
