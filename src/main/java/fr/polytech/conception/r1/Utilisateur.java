package fr.polytech.conception.r1;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Utilisateur
{
    private String pseudo;
    private String motDePasse;
    private String photo;
    private String email;
    private String adresse;
    private String prenom;
    private String nom;
    private Map<Sport, Niveau> sportFavoris;
    private List<Session> listSessionsOrganisees = new ArrayList<>();
    private List<Session> listSessions = new ArrayList<>();

    public List<Session> getListSessionsOrganisees()
    {
        return listSessionsOrganisees;
    }

    public List<Session> getListSessions()
    {
        return listSessions;
    }

    public boolean participer(Session session)
    {
        listSessions.add(session);
        session.participer(this);
        return true;
    }
}
