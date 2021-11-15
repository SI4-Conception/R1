package fr.polytech.conception.r1;

import java.time.Duration;
import java.time.ZonedDateTime;
import java.util.LinkedList;
import java.util.List;

import fr.polytech.conception.r1.profile.User;

public class Session implements Comparable<Session>
{
    private ZonedDateTime debut;
    private ZonedDateTime fin;
    private ZonedDateTime dateLimiteInscription;
    private String adresse;

    private boolean reserveAuxAmis = false;
    private int minParticipants = 1;
    /**
     * Set maxParticipants to zero to specify infinite number of participants
     */
    private int maxParticipants = 0;
    private Level difficulte = Level.DEBUTANT;
    private boolean estAnnulee = false;
    private Sport sport;
    private User organisateur;
    private List<User> participants = new LinkedList<>();
    private boolean isSponsored = false;
    private double sponsoredSessionPrice = 0d;

    public Session(ZonedDateTime debut, ZonedDateTime fin, String adresse, Sport sport, User organisateur) throws InvalidSessionDataException
    {
        this.organisateur = organisateur;
        if (organisateur.getListSessionsOrganisees().stream().anyMatch(session ->
                Util.intersect(debut, fin, session)))
            throw new InvalidSessionDataException("Création de 2 sessions se déroulant au même moment");
        checkDatesOrder(fin, debut);
        this.debut = debut;
        this.fin = fin;
        this.adresse = adresse;
        this.sport = sport;
        this.dateLimiteInscription = debut;
        this.organisateur.getListSessionsOrganisees().add(this);
    }

    public Session(ZonedDateTime debut, ZonedDateTime fin, String adresse, Sport sport, User organisateur, boolean sponsored) throws InvalidSessionDataException
    {
        this(debut, fin, adresse, sport, organisateur);
        isSponsored = sponsored;
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

    public String getAdresse()
    {
        return adresse;
    }

    public void setAdresse(String adresse)
    {
        this.adresse = adresse;
    }

    public boolean isReserveAuxAmis()
    {
        return reserveAuxAmis;
    }

    public void setReserveAuxAmis(boolean reserveAuxAmis)
    {
        this.reserveAuxAmis = reserveAuxAmis;
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
        if(participants.size() > maxParticipants)
        {
            throw new InvalidSessionDataException("There are already " + participants.size() + " participants for this session");
        }
        checkParticipantsBounds(minParticipants, maxParticipants);
        this.maxParticipants = maxParticipants;
    }

    public Level getDifficulte()
    {
        return difficulte;
    }

    public void setDifficulte(Level difficulte)
    {
        this.difficulte = difficulte;
    }

    public boolean isEstAnnulee()
    {
        return estAnnulee;
    }

    public void setEstAnnulee(boolean estAnnulee)
    {
        this.estAnnulee = estAnnulee;
    }

    public Sport getSport()
    {
        return sport;
    }

    public void setSport(Sport sport)
    {
        this.sport = sport;
    }

    public User getOrganisateur()
    {
        return organisateur;
    }

    public List<User> getParticipants()
    {
        return participants;
    }

    private void checkDatesOrder(ZonedDateTime fin, ZonedDateTime debut) throws InvalidSessionDataException
    {
        if (fin.isBefore(debut))
        {
            throw new InvalidSessionDataException("La date de fin est avant le debut.");
        }
    }

    private void checkParticipantsBounds(int minParticipants, int maxParticipants) throws InvalidSessionDataException
    {
        if (minParticipants > maxParticipants || minParticipants < 0 || maxParticipants <= 0)
        {
            System.out.println(maxParticipants);
            throw new InvalidSessionDataException("Le nombre min de participants doit etre <= au nombre max, et leur nombres doivent etre positifs");
        }
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

    public boolean excludeUser(User user)
    {
        boolean r = participants.contains(user) && user.getListSessions().contains(this);
        participants.remove(user);
        user.getListSessions().remove(this);
        return r;
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

    @Override
    public int compareTo(Session other)
    {
        ZonedDateTime now = ZonedDateTime.now();
        if(now.isAfter(this.debut) || now.isAfter(other.getDebut()))
        {
            return 0;
        }
        double daysBeforeThisSession = (Duration.between(now, this.debut).getSeconds() / 86400d) + 1d;
        double daysBeforeOtherSession = (Duration.between(now, other.getDebut()).getSeconds() / 86400d) + 1d;
        double thisValue = 1d/(2d * daysBeforeThisSession) + (isSponsored ? 0.5 : 0);
        double otherValue = 1d/(2d * daysBeforeOtherSession) + (other.isSponsored() ? 0.5 : 0);
        double difference = otherValue - thisValue;
        while(difference < 10d && difference > -10d)
        {
            difference = difference * 10d;
        }
        return (int) difference;
    }
}
