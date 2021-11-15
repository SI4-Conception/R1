package fr.polytech.conception.r1;

import java.time.Duration;
import java.time.ZonedDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Stream;

import fr.polytech.conception.r1.profile.User;

public class SessionOneshot extends Session implements Comparable<SessionOneshot>
{
    protected boolean isSponsored = false;
    private ZonedDateTime debut;
    private ZonedDateTime fin;
    private ZonedDateTime dateLimiteInscription;
    private boolean estAnnulee = false;
    private List<User> participants = new LinkedList<>();
    private double sponsoredSessionPrice = 0d;

    public SessionOneshot(ZonedDateTime debut, ZonedDateTime fin, String adresse, Sport sport, User organisateur, boolean isSponsored) throws InvalidSessionDataException
    {
        super(adresse, sport, organisateur);
        checkDatesOrder(fin, debut);
        this.debut = debut;
        this.fin = fin;
        this.dateLimiteInscription = debut;
        if (Util.filterType(organisateur.getListSessionsOrganisees().stream(), SessionOneshot.class).anyMatch(session ->
                session != this && Util.intersect(debut, fin, session)))
            throw new InvalidSessionDataException("Création de 2 sessions se déroulant au même moment");
        this.isSponsored = isSponsored;
    }

    public ZonedDateTime getDebut()
    {
        return debut;
    }

    public void setDebut(ZonedDateTime debut) throws InvalidSessionDataException
    {
        checkDatesOrder(fin, debut);
        this.debut = debut;
    }

    public ZonedDateTime getFin()
    {
        return fin;
    }

    public void setFin(ZonedDateTime fin) throws InvalidSessionDataException
    {
        checkDatesOrder(fin, debut);
        this.fin = fin;
    }

    public ZonedDateTime getDateLimiteInscription()
    {
        return dateLimiteInscription;
    }

    public void setDateLimiteInscription(ZonedDateTime dateLimiteInscription) throws InvalidSessionDataException
    {
        if (dateLimiteInscription.isAfter(debut))
        {
            throw new InvalidSessionDataException("La date limite d'inscription doit etre avant le debut de la seance");
        }
        this.dateLimiteInscription = dateLimiteInscription;
    }

    public boolean isEstAnnulee()
    {
        return estAnnulee;
    }

    public void setEstAnnulee(boolean estAnnulee)
    {
        this.estAnnulee = estAnnulee;
    }

    public List<User> getParticipants()
    {
        return participants;
    }

    public void participer(User participant) throws InvalidSessionDataException
    {
        if(participants.contains(participant))
        {
            throw new InvalidSessionDataException("Vous participez deja a cette session.");
        }
        if(maxParticipants != 0 && participants.size() >= maxParticipants) // maxParticipants = 0 -> infinite
        {
            throw new InvalidSessionDataException("Il y a deja trop de participants a cette session.");
        }
        if(reserveAuxAmis && !organisateur.getFriends().contains(participant))
        {
            throw new InvalidSessionDataException("Cette session est reservee aux amis de l'organisateur, vous n'en faites pas partie.");
        }
        if(organisateur.haveIBlacklistedUser(participant))
        {
            throw new InvalidSessionDataException("T'es blacklist bro");
        }
        this.participants.add(participant);
    }

    @Override
    public void setMaxParticipants(int maxParticipants) throws InvalidSessionDataException
    {
        if(participants.size() > maxParticipants)
        {
            throw new InvalidSessionDataException("There are already " + participants.size() + " participants for this session");
        }
        super.setMaxParticipants(maxParticipants);
    }

    @Override
    public int compareTo(SessionOneshot other)
    {
        ZonedDateTime now = ZonedDateTime.now();
        double daysBeforeThisSession = (Duration.between(this.debut, now).getSeconds() / 86400d) + 1;
        double daysBeforeOtherSession = (Duration.between(other.getDebut(), now).getSeconds() / 86400d) + 1;
        double thisValue = 1/(2 * daysBeforeThisSession) + (isSponsored ? 0.5 : 0);
        double otherValue = 1/(2 * daysBeforeOtherSession) + (other.isSponsored() ? 0.5 : 0);
        return (int) ((thisValue - otherValue) * 1000);
    }

    public boolean excludeUser(User user)
    {
        boolean r = participants.contains(user) && user.getListSessions().contains(this);
        participants.remove(user);
        user.getListSessions().remove(this);
        return r;
    }

    @Override
    public Stream<? extends SessionOneshot> getOneshots(ZonedDateTime fin)
    {
        return Stream.of(this);
    }

    public void setSponsored(boolean sponsored)
    {
        isSponsored = sponsored;
    }

    public boolean isSponsored()
    {
        return isSponsored;
    }

    public double getPrice()
    {
        return sponsoredSessionPrice;
    }

    public void setPrice(double price) throws InvalidSessionDataException
    {
        if(!isSponsored)
        {
            throw new InvalidSessionDataException("Cannot set price of unsponsored session");
        }
        sponsoredSessionPrice = price;
    }
}
