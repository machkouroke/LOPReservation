package com.lop.model.dao;

import com.lop.exception.DataBaseException;
import com.lop.model.beans.Evenements;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;

public record EventManager(Factory factory) {
    private static final String DATA_BASE_EXCEPTION =
            "Une erreur est subvenu lors de la connection à la base " +
                    "de données:";

    public boolean reservataireAllowed(int idReservataire, Connection conn)
            throws DataBaseException {
        try (PreparedStatement statement = conn.prepareStatement(
                "SELECT ID_FONCTION, QUOTA FROM SYSTEM.RESERVATAIRE WHERE ID_RESERVATAIRE=?")) {
            statement.setInt(1, idReservataire);
            ResultSet rs = statement.executeQuery();
            rs.next();
            int fonction = rs.getInt("ID_FONCTION");
            int quota = rs.getInt("QUOTA");
            /*Si le reservataire est un étudiant (id_fonction==2) on vérifie s'il lui reste un
            quota de réservation suffisant, par contre si c'est un professeur il a un quota illimité
             */
            if (fonction == 1) {
                return true;
            } else if (quota <= 0) {
                return false;
            } else {
                quotaUpdated(idReservataire, conn);
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataBaseException(DATA_BASE_EXCEPTION + e.getMessage());

        }

    }

    /**
     * Permet de diminuer le quota de réservation d'un réservataire donnée
     *
     * @param idReservataire id du réservataire à diminuer
     * @param conn           Objet de connexion vers la database
     */
    public void quotaUpdated(int idReservataire, Connection conn) {
        try (PreparedStatement statement = conn.prepareStatement(
                "UPDATE SYSTEM.Reservataire SET QUOTA = QUOTA - 1 WHERE ID_RESERVATAIRE=?")) {
            statement.setInt(1, idReservataire);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();

        }
    }

    public void add(Evenements event) throws DataBaseException, SQLException {
        Connection conn = this.factory.getConnection();
        if (reservataireAllowed(event.getIdReservataire(), conn)) {
            try (PreparedStatement request = conn.prepareStatement(
                    "insert into SYSTEM.EVENEMENTS(ID_RESERVATAIRE, NUM_SALLE, ID_BLOC, NOM, DATE_EVT) " +
                            "values(?, ?, ?, ?, TO_DATE(?, 'YYYY-MM-DD'))")) {
                requestSet(event, request);

            } catch (SQLIntegrityConstraintViolationException e) {
                throw new DataBaseException("La classe demandé n'est pas disponible en ce jour");
            } catch (SQLException e) {
                e.printStackTrace();
                throw new DataBaseException(DATA_BASE_EXCEPTION + e.getMessage());

            }
        } else {
            throw new DataBaseException("Le réservataire n'est plus autorisé a effectuer une " +
                    "réservation");
        }

    }

    public void delete(int id) throws DataBaseException, SQLException {
        Connection conn = this.factory.getConnection();
        try (PreparedStatement request = conn.prepareStatement(
                "DELETE FROM SYSTEM.EVENEMENTS WHERE ID_EVENT = ?")) {

            request.setInt(1, id);

            request.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataBaseException(DATA_BASE_EXCEPTION + e.getMessage());

        }
    }

    public void update(Evenements event) throws DataBaseException, SQLException {
        Connection conn = this.factory.getConnection();
        try (PreparedStatement request = conn.prepareStatement(
                "UPDATE SYSTEM.EVENEMENTS SET ID_RESERVATAIRE=?, NUM_SALLE=?, ID_BLOC=?, NOM=?, " +
                        "DATE_EVT=?" +
                        "WHERE ID_EVENT=? ")) {

            requestSet(event, request);
            request.setInt(6, event.getIdEvt(conn));


        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataBaseException(DATA_BASE_EXCEPTION + e.getMessage());

        }
    }

    /**
     * Set parameter to add and update requests
     *
     * @param event   Event Content
     * @param request A prepared statement with the request
     */
    private void requestSet(Evenements event, PreparedStatement request) throws SQLException {
        request.setInt(1, event.getIdReservataire());
        request.setInt(2, event.getNumSalle());
        request.setString(3, event.getIdBloc());
        request.setString(4, event.getNom());
        request.setString(5, event.getDateEvt());
        request.executeUpdate();
    }

    public List<List<String>> listeSalleReservataire(int id) throws
            SQLException {
        PrintEvent print = new PrintEvent(factory);
        return print.print(
                "SELECT NUM_SALLE, ID_BLOC FROM SYSTEM.EVENEMENTS WHERE ID_RESERVATAIRE=" +
                        id);
    }

    public List<List<String>> evtInBloc(String id) throws
            SQLException {
        PrintEvent print = new PrintEvent(factory);
        return print.print(
                "SELECT NOM , NUM_SALLE , ID_BLOC, TO_CHAR(DATE_EVT) FROM SYSTEM.EVENEMENTS WHERE ID_BLOC='" +
                        id + "'");
    }

    public List<List<String>> actifReservateur() throws
            SQLException {
        PrintEvent print = new PrintEvent(factory);
        return print.print(
                "SELECT * FROM SYSTEM.RESERVATAIRE WHERE ID_RESERVATAIRE IN (SELECT ID_RESERVATAIRE FROM SYSTEM.EVENEMENTS)");
    }

    public List<List<String>> dayReservation(String day) throws
            SQLException {
        PrintEvent print = new PrintEvent(factory);
        return print.print(
                "SELECT NOM ,  NUM_SALLE , ID_BLOC FROM SYSTEM.EVENEMENTS WHERE DATE_EVT = "
                        + "TO_DATE('" + day + "','YYYY-MM-DD')");
    }

    public List<List<String>> pastEvent() throws SQLException {
        PrintEvent print = new PrintEvent(factory);

        return print.print(
                "SELECT * FROM SYSTEM.EVENEMENTS WHERE DATE_EVT<CURRENT_DATE");
    }

    public List<List<String>> getAllReservations() throws SQLException {
        PrintEvent print = new PrintEvent(factory);

        return print.print(
                "SELECT * FROM SYSTEM.EVENEMENTS");
    }

}
