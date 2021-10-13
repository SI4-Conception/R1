package fr.polytech.conception.r1.profile;

public class InvalidProfileDataException extends Exception
{
    public InvalidProfileDataException(String errorMessage)
    {
        super(errorMessage);
    }
}
