package fr.polytech.conception.r1;

import java.time.ZonedDateTime;
import java.util.stream.Stream;

import fr.polytech.conception.r1.session.SessionOneshot;

public class Util
{
    public static boolean intersect(ZonedDateTime debut, ZonedDateTime fin, SessionOneshot session)
    {
        return isBetween(debut, fin, session.getDebut())
                || isBetween(debut, fin, session.getFin());
    }

    private static boolean isBetween(ZonedDateTime debut, ZonedDateTime fin, ZonedDateTime date)
    {
        return !date.isBefore(debut) && !date.isAfter(fin);
    }

    /**
     * Cast all element of a stream to a specific class.
     *
     * @param str   The stream.
     * @param clazz The wanted class.
     * @param <T>   The type of the class.
     * @return a stream of object of a specific casted type.
     */
    public static <T> Stream<T> filterType(Stream<?> str, Class<T> clazz)
    {
        return str
                .filter(clazz::isInstance)
                .map(clazz::cast);
    }
}
