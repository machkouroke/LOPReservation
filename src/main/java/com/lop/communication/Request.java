package com.lop.communication;

import java.util.Map;


/**
 * Objet pour envoyer des informations depuis la vue
 * @author Machkour Oke
 */
public record Request(String message, Map<String, String> parameters) {


    /**
     * Utile pour obtenir un paramètre avec un nom donné
     * @param name nom du paramètre
     * @return valeur du paramètre
     */
    public String get(String name) {
        return parameters.get(name);
    }

    /**
     * Renvoie le message de la requête
     * @return message de la requête
     */
    public String getMessage() {
        return message;
    }

    /**
     * Renvoie une map avec les attributs et valeurs
     * @return map avec pour clé les noms des attributs et pour valeurs les valeurs de ceux ci
     */
    public Map<String, String> getParameters() {
        return parameters;
    }
}
