package fr.polytech.conception.r1;

import java.util.Arrays;

public enum Level
{
    BEGINNER("BEGINNER"),
    INTERMEDIATE("INTERMEDIATE"),
    ADVANCED("ADVANCED"),
    EXPERT("EXPERT");

    private final String levelAsString;

    Level(String level)
    {
        this.levelAsString = level;
    }

    public String getLevelAsString()
    {
        return this.levelAsString;
    }

    public static Level getByString(String level)
    {
        return Arrays.stream(values())
                .filter(val -> val.getLevelAsString().equals(level))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Unknown level: " + level));
    }
}
