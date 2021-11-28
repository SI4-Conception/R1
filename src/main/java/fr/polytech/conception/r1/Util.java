package fr.polytech.conception.r1;

import java.time.ZonedDateTime;
import java.util.stream.Stream;

import fr.polytech.conception.r1.session.SessionOneshot;

public class Util
{
    public static boolean intersect(ZonedDateTime start, ZonedDateTime end, SessionOneshot session)
    {
        return isBetween(start, end, session.getStart())
                || isBetween(start, end, session.getEnd());
    }

    private static boolean isBetween(ZonedDateTime start, ZonedDateTime end, ZonedDateTime date)
    {
        return !date.isBefore(start) && !date.isAfter(end);
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
