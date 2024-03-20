package fr.univamu.iut.reservation;
import java.util.ArrayList;
public interface ReservationRepositoryInterface {
    public void close();
    public Reservation getReservation(String reference );
    public ArrayList<Reservation> getAllReservations() ;
}
