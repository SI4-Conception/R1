package fr.polytech.conception.r1;

public class InvalidProfileDataException extends Exception
{
    public InvalidProfileDataException(String errorMessage)
    {
        super(errorMessage);
    }
}
