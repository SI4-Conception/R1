package fr.polytech.conception.r1;

import java.time.ZonedDateTime;
import java.util.LinkedList;
import java.util.List;

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
    private Niveau difficulte = Niveau.INTERMEDIAIRE;
    private boolean estAnnulee = false;
    private Sport sport;
    private Utilisateur organisateur;
    private List<Utilisateur> participants = new LinkedList<>();

    public Session(ZonedDateTime debut, ZonedDateTime fin, String adresse, Sport sport) throws InvalidSessionDataException {
        // TODO: verify here than passed params are corrects
        checkDatesOrder(fin, debut);
        this.debut = debut;
        this.fin = fin;
        this.adresse = adresse;
        this.sport = sport;
        this.dateLimiteInscription = debut;
    }

    public ZonedDateTime getDebut()
    {
        return debut;
    }

    public ZonedDateTime getFin()
    {
        return fin;
    }

    public ZonedDateTime getDateLimiteInscription()
    {
        return dateLimiteInscription;
    }

    public String getAdresse()
    {
        return adresse;
    }

    public boolean isReserveAuxAmis()
    {
        return reserveAuxAmis;
    }

    public int getMinParticipants()
    {
        return minParticipants;
    }

    public int getMaxParticipants()
    {
        return maxParticipants;
    }

    public Niveau getDifficulte()
    {
        return difficulte;
    }

    public boolean isEstAnnulee()
    {
        return estAnnulee;
    }

    public Sport getSport()
    {
        return sport;
    }

    public Utilisateur getOrganisateur()
    {
        return organisateur;
    }

    public List<Utilisateur> getParticipants()
    {
        return participants;
    }

    public void setDebut(ZonedDateTime debut) throws InvalidSessionDataException
    {
        checkDatesOrder(fin, debut);
        this.debut = debut;
    }

    public void setFin(ZonedDateTime fin) throws InvalidSessionDataException
    {
        checkDatesOrder(fin, debut);
        this.fin = fin;
    }

    private void checkDatesOrder(ZonedDateTime fin, ZonedDateTime debut) throws InvalidSessionDataException
    {
        if (fin.isBefore(debut))
        {
            throw new InvalidSessionDataException("La date de fin est avant le debut.");
        }
    }

    public void setDateLimiteInscription(ZonedDateTime dateLimiteInscription) throws InvalidSessionDataException
    {
        if(dateLimiteInscription.isAfter(debut))
        {
            throw new InvalidSessionDataException("La date limite d'inscription doit etre avant le debut de la seance");
        }
        this.dateLimiteInscription = dateLimiteInscription;
    }

    public void setAdresse(String adresse)
    {
        this.adresse = adresse;
    }

    public void setReserveAuxAmis(boolean reserveAuxAmis)
    {
        this.reserveAuxAmis = reserveAuxAmis;
    }

    public void setMinParticipants(int minParticipants) throws InvalidSessionDataException
    {
        checkParticipantsBounds(minParticipants, maxParticipants);
        this.minParticipants = minParticipants;
    }

    public void setMaxParticipants(int maxParticipants) throws InvalidSessionDataException
    {
        checkParticipantsBounds(minParticipants, maxParticipants);
        this.maxParticipants = maxParticipants;
    }

    private void checkParticipantsBounds(int minParticipants, int maxParticipants) throws InvalidSessionDataException
    {
        if (minParticipants > maxParticipants || minParticipants < 0 || maxParticipants < 0)
        {
            System.out.println(maxParticipants);
            throw new InvalidSessionDataException("Le nombre min de participants doit etre <= au nombre max, et leur nombres doivent etre positifs");
        }
    }

    public void setDifficulte(Niveau difficulte)
    {
        this.difficulte = difficulte;
    }

    public void setEstAnnulee(boolean estAnnulee)
    {
        this.estAnnulee = estAnnulee;
    }

    public void setSport(Sport sport)
    {
        this.sport = sport;
    }
}
