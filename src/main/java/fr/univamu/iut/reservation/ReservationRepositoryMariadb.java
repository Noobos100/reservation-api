package fr.univamu.iut.reservation;

import java.io.Closeable;
import java.sql.*;
import java.util.ArrayList;

/**
 * Classe permettant d'accéder aux users stockés dans une base de données Mariadb
 */
public class ReservationRepositoryMariadb implements ReservationRepositoryInterface, Closeable {

    /**
     * Accès à la base de données (session)
     */
    protected Connection dbConnection;

    /**
     * Constructeur de la classe
     *
     * @param infoConnection chaîne de caractères avec les informations de connexion
     *                       (p. Ex. jdbc:mariadb://mysql-[compte].alwaysdata.net/[compte]_library_db
     * @param user           chaîne de caractères contenant l'identifiant de connexion à la base de données
     * @param pwd            chaîne de caractères contenant le mot de passe à utiliser
     */
    public ReservationRepositoryMariadb(String infoConnection, String user, String pwd) throws SQLException, ClassNotFoundException {
        Class.forName("org.mariadb.jdbc.Driver");
        dbConnection = DriverManager.getConnection(infoConnection, user, pwd);
    }

    @Override
     public void close() {
        try {
            dbConnection.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    @Override
    public Reservation getReservation(String reference) {
        try {
            PreparedStatement stmt = dbConnection.prepareStatement("SELECT * FROM Reservation WHERE reference = ?");
            stmt.setString(1, reference);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Reservation(rs.getString("id"), rs.getString("date"), rs.getString("reference"));
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return null;
    }

    @Override
    public ArrayList<Reservation> getAllReservations() {
        ArrayList<Reservation> reservations = new ArrayList<>();
        try {
            Statement stmt = dbConnection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM Reservation");
            while (rs.next()) {
                reservations.add(new Reservation(rs.getString("id"), rs.getString("date"), rs.getString("reference")));
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return reservations;
    }

    @Override
    public boolean registerReservation(String id, String reference) {
        try {
            PreparedStatement stmt = dbConnection.prepareStatement("INSERT INTO Reservation (id, date, reference) VALUES (?, ?, ?)");
            stmt.setString(1, id);
            stmt.setString(2, new Date(System.currentTimeMillis()).toString());
            stmt.setString(3, reference);
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return false;
    }

    @Override
    public boolean releaseReservation(String reference) {
        try {
            PreparedStatement stmt = dbConnection.prepareStatement("DELETE FROM Reservation WHERE reference = ?");
            stmt.setString(1, reference);
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return false;
    }

}