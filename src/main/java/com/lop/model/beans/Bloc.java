package com.lop.model.beans;

/**
 * Mot de passe de connexion de l'application incorrect
 * @author Machkour Oke
 */
public class Bloc {
    private int idBloc;
    private String nomBoc;
    private String etage;

    public int getIdBloc() {
        return idBloc;
    }

    public void setIdBloc(int idBloc) {
        this.idBloc = idBloc;
    }

    public String getNomBoc() {
        return nomBoc;
    }

    public void setNomBoc(String nomBoc) {
        this.nomBoc = nomBoc;
    }

    public String getEtage() {
        return etage;
    }

    public void setEtage(String etage) {
        this.etage = etage;
    }
}
