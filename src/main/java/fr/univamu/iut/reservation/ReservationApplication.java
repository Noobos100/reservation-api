package fr.univamu.iut.reservation;

import fr.univamu.iut.book.BookRepositoryAPI;
import fr.univamu.iut.book.BookRepositoryInterface;
import fr.univamu.iut.user.UserRepositoryAPI;
import fr.univamu.iut.user.UserRepositoryInterface;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Disposes;
import jakarta.enterprise.inject.Produces;
import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;

@ApplicationPath("/api")
@ApplicationScoped
public class ReservationApplication extends Application {
    /**
     * Méthode appelée par l'API CDI pour injecter la connection à la base de données au moment de la création
     * de la ressource
     * @return un objet implémentant l'interface UserRepositoryInterface utilisée
     *          pour accéder aux données des users, voire les modifier
     */

    @Produces
    private ReservationRepositoryInterface openDbConnection(){
        ReservationRepositoryMariadb db = null;

        try{
            db = new ReservationRepositoryMariadb("jdbc:mariadb://mysql-cdaw.alwaysdata.net/cdaw_library_db", "cdaw_library", "vraimdp");
        }
        catch (Exception e){
            System.err.println(e.getMessage());
        }
        return db;
    }

    /**
     * Méthode permettant de fermer la connexion à la base de données lorsque l'application est arrêtée
     * @param userRepo la connexion à la base de données instanciée dans la méthode @openDbConnection
     */
    private void closeDbConnection(@Disposes ReservationRepositoryInterface userRepo ) {
        userRepo.close();
    }

    /**
     * Méthode appelée par l'API CDI pour injecter l'API Book au moment de la création de la ressource
     * @return une instance de l'API avec l'url à utiliser
     */
    @Produces
    private BookRepositoryInterface connectBookApi(){
        return new BookRepositoryAPI("http://localhost:8080/book-1.0-SNAPSHOT/api/");
    }

    /**
     * Méthode appelée par l'API CDI pour injecter l'API User au moment de la création de la ressource
     * @return une instance de l'API avec l'url à utiliser
     */
    @Produces
    private UserRepositoryInterface connectUserApi(){
        return new UserRepositoryAPI("http://localhost:8080/user-1.0-SNAPSHOT/api/");
    }

}