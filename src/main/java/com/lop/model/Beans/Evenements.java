package com.lop.model.Beans;

import java.util.List;

public class Evenements {
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

    public void setDateEvt(String dateEvt) {
        this.dateEvt = dateEvt;
    }
    public List<String> eventAttributes() {
        return List.of(Integer.toString(idReservataire), Integer.toString(numSalle), idBloc, nom,
                dateEvt, "DD/MM/YYYY");
    }
}
