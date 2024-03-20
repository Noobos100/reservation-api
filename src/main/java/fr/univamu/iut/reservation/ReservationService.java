package fr.univamu.iut.reservation;

import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;

import java.util.ArrayList;

public class ReservationService {
    protected ReservationRepositoryInterface reservationRepo ;

    public ReservationService( ReservationRepositoryInterface reservationRepo) {
        this.reservationRepo = reservationRepo;
    }

    public String getAllReservationsJSON(){
        ArrayList<Reservation> allReservations = reservationRepo.getAllReservations();
        String result = null;
        try( Jsonb jsonb = JsonbBuilder.create()){
            result = jsonb.toJson(allReservations);
        }
        catch (Exception e){
            System.err.println( e.getMessage() );
        }
        return result;
    }

    public String getReservationJSON(String reference ){
        Reservation reservation = reservationRepo.getReservation(reference);
        String result = null;
        try( Jsonb jsonb = JsonbBuilder.create()){
            result = jsonb.toJson(reservation);
        }
        catch (Exception e){
            System.err.println( e.getMessage() );
        }
        return result;
    }
}
