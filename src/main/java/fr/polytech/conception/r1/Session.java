package fr.polytech.conception.r1;

import java.time.ZonedDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import fr.polytech.conception.r1.profile.User;

public class Session
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

    public Session(ZonedDateTime debut, ZonedDateTime fin, String adresse, Sport sport, User organisateur) throws InvalidSessionDataException
    {
        // TODO: verify here than passed params are corrects

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
        if (minParticipants > maxParticipants || minParticipants < 0 || maxParticipants < 0)
        {
            System.out.println(maxParticipants);
            throw new InvalidSessionDataException("Le nombre min de participants doit etre <= au nombre max, et leur nombres doivent etre positifs");
        }
    }

    public void participer(User participant)
    {
        this.participants.add(participant);
    }
}
