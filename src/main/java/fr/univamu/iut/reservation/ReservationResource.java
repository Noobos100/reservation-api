package fr.univamu.iut.reservation;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;


/**
 * Ressource associée aux réservations de livres
 * (point d'accès de l'API REST)
 */
@Path("/reservations")
@ApplicationScoped
public class ReservationResource {

    /**
     * Service utilisé pour accéder aux données de réservations et récupérer/modifier leurs informations
     */
    private ReservationService service;

    /**
     * Constructeur par défaut
     */
    public ReservationResource(){}

    /**
     * Constructeur permettant d'initialiser le service avec les interfaces d'accès aux données
     * @param reservationRepo objet implémentant l'interface d'accès aux données des réservations
     */
    public @Inject ReservationResource(ReservationRepositoryInterface reservationRepo){
        this.service = new ReservationService( reservationRepo ) ;
    }

    /**
     * Enpoint permettant de publier de toutes les réservations enregistrées
     * @return la liste des livres (avec leurs informations) au format JSON
     */
    @GET
    @Produces("application/json")
    public String getAllReservations() {
        return service.getAllReservationsJSON();
    }

    /**
     * Endpoint permettant de publier les informations d'une réservation
     * dont la référence est passée paramètre dans le chemin
     * @param reference référence du livre recherché
     * @return les informations de réservation pour le livre recherché au format JSON
     */
    @GET
    @Path("{reference}")
    @Produces("application/json")
    public String getReservation( @PathParam("reference") String reference){

        String result = service.getReservationJSON( reference );

        // si aucune réservation n'a été trouvée
        // on retourne simplement un JSON vide
        if( result == null )
           return "{}";

        return result;
    }
}
