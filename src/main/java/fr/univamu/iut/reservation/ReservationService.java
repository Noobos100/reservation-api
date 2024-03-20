package fr.univamu.iut.reservation;

import fr.univamu.iut.book.Book;
import fr.univamu.iut.book.BookRepositoryInterface;
import fr.univamu.iut.user.User;
import fr.univamu.iut.user.UserRepositoryInterface;
import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.ws.rs.NotFoundException;

import java.util.ArrayList;

public class ReservationService {
    protected ReservationRepositoryInterface reservationRepo ;
    protected BookRepositoryInterface bookRepo;
    protected UserRepositoryInterface userRepo;

    /**
     * Constructeur permettant d'injecter l'accès aux données
     * @param reservationRepo objet implémentant l'interface d'accès aux données des réservations
     * @param bookRepo  objet implémentant l'interface d'accès aux données des livres
     * @param userRepo  objet implémentant l'interface d'accès aux données des utilisateurs
     */
    public ReservationService(ReservationRepositoryInterface reservationRepo, BookRepositoryInterface bookRepo,
                              UserRepositoryInterface userRepo) {
        this.reservationRepo = reservationRepo;
        this.bookRepo = bookRepo ;
        this.userRepo = userRepo ;
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

    /**
     * Méthode permettant d'enregistrer une réservation (la date est définie automatiquement)
     * @param id identifiant de l'utilisateur
     * @param reference référence du livre à réserver
     * @return true si le livre a pu être réservé, false sinon
     */
    public boolean registerReservation(String id, String reference) {
        boolean result = false;

        // récupération des informations du livre
        Book myBook = bookRepo.getBook( reference );

        //si le livre n'est pas trouvé
        if( myBook == null )
            throw  new NotFoundException("book not exists");

        // si le livre est disponible
        if( myBook.getStatus() == 'd')
        {
            // recherche l'utilisateur
            User myUser = userRepo.getUser(id);

            // si l'utilisateur n'existe pas
            if( myUser == null)
                throw  new NotFoundException("user not exists");

            // modifier le statut du livre à "r" (réservé) dans le dépôt
            result = bookRepo.updateBook(myBook.getReference(), myBook.getTitle(), myBook.getAuthors(), 'r');

            // faire la réservation
            if( result )
                result = reservationRepo.registerReservation(id, reference);
        }
        return result;
    }
    /**
     * Méthode supprimant une réservation
     * @param reference référence du livre à libérer
     * @return true si la réservation a été supprimée, false sinon
     */
    boolean removeReservation(String reference){
        boolean result = false;

        // récupération des informations du livre
        Book myBook = bookRepo.getBook( reference );

        //si le livre n'est pas trouvé
        if( myBook == null )
            throw  new NotFoundException("book not exists");
        else
        {
            // modifier le statut du livre à "d" (disponible) dans le dépôt des livres
            result = bookRepo.updateBook(myBook.getReference(), myBook.getTitle(), myBook.getAuthors(), 'd');

            // supprimer la réservation
            if(result ) result = reservationRepo.releaseReservation( reference );
        }
        return result;
    }
}
