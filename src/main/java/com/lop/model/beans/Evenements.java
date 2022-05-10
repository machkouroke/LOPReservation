package com.lop.model.beans;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class Evenements {
    private int idEvent = 0;
    private int idReservataire;
    private int numSalle;
    private String idBloc;
    private String nom;
    private String dateEvt;

    public Evenements(int idReservataire, int numSalle, String idBloc, String nom,
                      String dateEvt) {
        this.idReservataire = idReservataire;
        this.numSalle = numSalle;
        this.idBloc = idBloc;
        this.nom = nom;
        this.dateEvt = dateEvt;
    }
    public Evenements(int idEvent, int idReservataire, int numSalle, String idBloc, String nom,
                      String dateEvt) {
        this.idEvent = idEvent;
        this.idReservataire = idReservataire;
        this.numSalle = numSalle;
        this.idBloc = idBloc;
        this.nom = nom;
        this.dateEvt = dateEvt;
    }

    public int getIdReservataire() {
        return idReservataire;
    }

    public void setIdReservataire(int idReservataire) {
        this.idReservataire = idReservataire;
    }

    public int getNumSalle() {
        return numSalle;
    }

    public void setNumSalle(int numSalle) {
        this.numSalle = numSalle;
    }

    public String getIdBloc() {
        return idBloc;
    }

    public void setIdBloc(String idBloc) {
        this.idBloc = idBloc;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDateEvt() {
        return dateEvt;
    }
    public int getIdEvt(Connection conn) throws SQLException {
        if(idEvent == 0) {
            try(Statement statement = conn.createStatement()) {
                ResultSet answer = statement.executeQuery("SELECT ID_EVENT FROM SYSTEM.EVENEMENTS " +
                        "WHERE NUM_SALLE=? AND ID_BLOC=? AND DATE_EVT=?");
                answer.next();
                idEvent = answer.getInt(1);
            }
        }
       return idEvent;
    }
    public void setDateEvt(String dateEvt) {
        this.dateEvt = dateEvt;
    }
    public List<String> eventAttributes() {
        return List.of(Integer.toString(idReservataire), Integer.toString(numSalle), idBloc, nom,
                dateEvt, "DD/MM/YYYY");
    }
}
