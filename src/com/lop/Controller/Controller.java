package com.lop.Controller;

import com.lop.ErrorEvent.ErrorInitiator;
import com.lop.ErrorEvent.ErrorListener;
import com.lop.Exception.DataBaseException;
import com.lop.Model.Beans.Evenements;
import com.lop.Model.dao.EventManager;
import com.lop.Model.dao.Factory;
import com.lop.communication.Request;
import com.lop.communication.Response;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Controller implements ErrorInitiator {
    Factory factory;
    String dataBaseException = "Une erreur s'est produite lors de la connexion à la base de données";
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
        Evenements event = new Evenements(Integer.parseInt(request.get("idReservataire")),
                Integer.parseInt(request.get("numSalle")), request.get("numBloc"), request.get("eventName"),
                request.get("eventDate"));
        try {
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
                    new EventManager(factory).listeSalleReservataire(Integer.parseInt(request.get("idReservataire"))), this);
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
                    new EventManager(factory).evtInBloc(request.get("idBloc")), this);
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
                    new EventManager(factory).actifReservateur(), this);
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
                    new EventManager(factory).dayReservation(request.get("dayReservation")), this);
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
                    new EventManager(factory).pastEvent(), this);
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
                    new EventManager(factory).getAllReservations(), this);
        } catch (SQLException e) {
            return new Response(e, this);
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
//OK
