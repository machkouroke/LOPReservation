package com.lop.error_event;

/**
 * Interface mentionnant qu'un objet est à l'écoute des erreurs d'un autre et déclenche une action correspondante
 * @author Machkour Oke
 */
public interface ErrorListener {
    void errorOccurred(String message);
    void noError(String message);
}
