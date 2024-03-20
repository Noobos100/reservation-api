package fr.univamu.iut.user;

import java.util.ArrayList;

/**
 * Interface d'accès aux données des users
 */
public interface UserRepositoryInterface {

    /**
     *  Méthode fermant le dépôt où sont stockées les informations sur les users
     */
    public void close();

    /**
     * Méthode retournant l'user dont la référence est passée en paramètre
     * @param id identifiant de l'user recherché
     * @return un objet User représentant l'user recherché
     */
    public User getUser(String id );

    /**
     * Méthode retournant la liste des users
     * @return une liste d'objets users
     */
    public ArrayList<User> getAllUsers() ;
}
