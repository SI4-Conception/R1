package fr.polytech.conception.r1;

import java.time.ZonedDateTime;

import fr.polytech.conception.r1.Session;

public class Util
{
    public static boolean intersect(ZonedDateTime debut, ZonedDateTime fin, Session session)
    {
        return isBetween(debut, fin, session.getDebut())
                || isBetween(debut, fin, session.getFin());
    }

    private static boolean isBetween(ZonedDateTime debut, ZonedDateTime fin, ZonedDateTime date)
    {
        return !date.isBefore(debut) && !date.isAfter(fin);
    }
}
