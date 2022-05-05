package com.lop.communication;

import com.lop.controller.Controller;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Response {
    private final Map<String, String> attribute = new HashMap<>();
    private final List<List<String>> data = new ArrayList<>();

    public Response(String message, List<List<String>> data, Controller controller) {
        this.data.addAll(data);
        this.attribute.put("Error", null);
        this.attribute.put("Message", message);
        controller.noError(message);
    }

    public Response(String message, Controller controller) {
        this.attribute.put("Message", message);
        this.attribute.put("Error", null);
        controller.noError(message);
    }

    public Response(Exception e, Controller controller) {
        this.attribute.put("Error", "Erreur: " + e.getMessage());
        this.attribute.put("Message", null);
        controller.errorOccurred(e.getMessage());
    }


    /**
     * Obtient la liste de tous les attributs de l'objet
     *
     * @return Dico avec pour clé le nom de l'attribut et valeur la valeur de celui ci
     */
    public List<List<String>> getData() {
        return data;
    }


    /**
     * Retourne le message de l'attribut
     */
    public String getMessage() {
        return attribute.get("Message");
    }

    /**
     * Retourne le message de l'attribut
     */
    public String getError() {
        return attribute.get("Error");
    }

}
