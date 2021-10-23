package fr.polytech.conception.r1;

public class InvalidMessageException extends Exception
{
    public InvalidMessageException(String errorMessage)
    {
        super(errorMessage);
    }
}
