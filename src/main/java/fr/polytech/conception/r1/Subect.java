package fr.polytech.conception.r1;

public interface Subect
{
    public void attach(Observer o);
    public void detach(Observer o);
    public void notifyUpdate(String m);
}
