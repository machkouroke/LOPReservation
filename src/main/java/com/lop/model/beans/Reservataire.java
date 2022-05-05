package com.lop.model.beans;

public class Reservataire {
    private int idReservataire;
    private int idFonction;
    private String nom;
    private String surname;

    public int getIdReservataire() {
        return idReservataire;
    }

    public void setIdReservataire(int idReservataire) {
        this.idReservataire = idReservataire;
    }

    public int getIdFonction() {
        return idFonction;
    }

    public void setIdFonction(int idFonction) {
        this.idFonction = idFonction;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }
}
