package com.lop.controller;

import com.lop.error_event.ErrorInitiator;
import com.lop.error_event.ErrorListener;
import com.lop.exception.DataBaseException;
import com.lop.exception.PasswordIncorrectException;
import com.lop.exception.UnknownUserNameException;
import com.lop.model.beans.Evenements;
import com.lop.model.dao.EventManager;
import com.lop.model.dao.Factory;
import com.lop.communication.Request;
import com.lop.communication.Response;
import com.lop.model.dao.UserConnection;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Controller implements ErrorInitiator {
    Factory factory;
    private final List<ErrorListener> listeners = new ArrayList<>();

    /**
     * @param factory Object permettant d'effectuer la connexion avec la base de données et ainsi d'effectuer la manipulation de modèle
     */
    public Controller(Factory factory) {
        this.factory = factory;
    }


    /**
     * Permet d'ajouter un nouvel élément dans la base de données
     *
     * @param request objet request qui doit contenir les informations de la réservation comme suit :
     *                idReservataire, numSalle, numBloc, eventName, eventDate
     * @return un objet Responses qui retourne juste un message si tout est correcte et un message d'erreur sinon
     */
    public Response add(Request request) {

        System.out.println(request.getParameters());
        Evenements event = new Evenements(Integer.parseInt(request.get("idReservataire")),
                Integer.parseInt(request.get("numSalle")), request.get("numBloc"), request.get("eventName"),
                request.get("eventDate"));
        try {
            new EventManager(this.factory).add(event);
            return new Response("L'ajout de la réservation a bien été effectué", this);
        } catch (DataBaseException | SQLException e) {
            return new Response(e, this);
        }
    }

    /**
     * Permet de supprimer une réservation dans la base de données
     *
     * @param request objet request qui doit contenir l'id de la réservation à supprimer sous le nom idEvent
     * @return un objet Responses qui retourne juste un message si tout est correcte et un message d'erreur sinon
     */
    public Response delete(Request request)  {
        int idEvent = Integer.parseInt(request.get("idEvent"));
        try {
            new EventManager(this.factory).delete(idEvent);
            return new Response("La réservation a bien été supprimé", this);
        } catch (SQLException | DataBaseException e) {
            return new Response(e, this);
        }
    }

    /**
     * Permet de mettre à jour un élément dans la base de données
     *
     * @param request objet request qui doit contenir les informations de la réservation comme suit :
     *                idReservataire, numSalle, numBloc, eventName, eventDate
     * @return un objet Responses qui retourne juste un message si tout est correcte et un message d'erreur sinon
     */
    public Response update(Request request)  {
        Evenements event = new Evenements(Integer.parseInt(request.get("idEvent")),Integer.parseInt(request.get("idReservataire")),
                Integer.parseInt(request.get("numSalle")), request.get("numBloc"), request.get("eventName"),
                request.get("eventDate"));
        try {
            System.out.println(event);
            new EventManager(this.factory).update(event);
            return new Response("La réservation  a bien été mise à jour", this);
        } catch (DataBaseException | SQLException e) {
            return new Response(e, this);
        }
    }

    /**
     * Permet d'afficher les réservations des réservataires données
     *
     * @param request objet request sans paramètre particulier
     * @return un objet Responses qui retourne juste un message si tout est correcte et un message d'erreur sinon
     */
    public Response listeSalleReservataire(Request request)  {
        try {
            return new Response("Liste des Réservation d'un réservataires données",
                    new EventManager(factory).listeSalleReservataire(Integer.parseInt(request.get("idReservataire"))));
        } catch (SQLException e) {
            return new Response(e, this);
        }
    }

    /**
     * Permet d'afficher les évènements actifs dans un bloc donné
     *
     * @param request objet request qui doit contenir le numéro du bloc : idBloc
     * @return un objet Responses qui retourne juste un message si tout est correcte et un message d'erreur sinon
     */
    public Response evtInBloc(Request request)  {

        try {
            return new Response("Évènements dans un bloc données",
                    new EventManager(factory).evtInBloc(request.get("idBloc")));
        } catch (SQLException e) {
            return new Response(e, this);
        }
    }

    /**
     * Permet d'afficher les réservateurs avec une réservation en cours
     *
     * @return un objet Responses qui retourne juste un message si tout est correcte et un message d'erreur sinon
     */
    public Response actifReservateur() {
        try {
            return new Response("Réservateurs avec une réservation en cours",
                    new EventManager(factory).actifReservateur());
        } catch (SQLException e) {
            return new Response(e, this);
        }
    }


    /**
     * Permet d'afficher les réservations d'un jour données
     *
     * @param request objet request qui doit contenir le jour de la réservation : dayReservation
     * @return un objet Responses qui retourne juste un message si tout est correcte et un message d'erreur sinon
     */
    public Response dayReservation(Request request) {
        try {
            return new Response("Réservation d'une journées données",
                    new EventManager(factory).dayReservation(request.get("dayReservation")));
        } catch (SQLException e) {
            return new Response(e, this);
        }
    }

    /**
     * Permet d'afficher les évènements qui se sont deja déroulé
     *
     * @return un objet Responses qui retourne juste un message si tout est correcte et un message d'erreur sinon
     */
    public Response pastEvent() {
        try {
            return new Response("Évènements passé",
                    new EventManager(factory).pastEvent());
        } catch (SQLException e) {
            return new Response(e, this);
        }
    }

    /**
     * Renvoie une liste de tous les évènements
     */
    public Response getAllReservations() {
        try {
            return new Response("Liste de tous les évènements",
                    new EventManager(factory).getAllReservations());
        } catch (SQLException e) {
            return new Response(e, this);
        }
    }

    /**
     * Effectue l'authentification de l'utilisateur
     * @param request contient les informations de connexions: userName et password
     * @return Un objet response avec allowed en message si l'utilisateur est autorisé et denied sinon
     */
    public Response authenticate(Request request) {
        UserConnection connection = new UserConnection(this.factory);
        try {
            if (connection.connexionValidate(request.get("userName"), request.get(
                    "password"))) {
                return new Response("allowed",
                        this);
            }
            return new Response("denied",
                    this);
        } catch (DataBaseException |  PasswordIncorrectException | UnknownUserNameException e) {
            return new Response(e.getMessage(), this);
        }
    }

    /**
     * permet d'ajouter des écouteurs d'évènements au controller
     * @param listener écouteur
     */
    @Override
    public void addListener(ErrorListener listener) {
        listeners.add(listener);
    }

    /**
     * Permet de déclencher l'évènement une erreur est apparue chez l'écouteur
     * @param message Message de l'erreur
     */
    public void errorOccurred(String message) {
        for (ErrorListener listener : listeners) {
            listener.errorOccurred(message);
        }
    }

    /**
     * Permet de déclencher l'évènement aucune erreur n'est apparue chez l'écouteur
     * @param message Message de finalisation ou autre
     */
    public void noError(String message) {
        for (ErrorListener listener : listeners) {
            listener.noError(message);
        }
    }
}
