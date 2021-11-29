package fr.polytech.conception.r1;

public class InvalidSessionDataException extends Exception
{
    public InvalidSessionDataException(String errorMessage)
    {
        super(errorMessage);
    }
}
