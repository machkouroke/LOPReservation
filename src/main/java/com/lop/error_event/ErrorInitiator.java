package com.lop.error_event;

/**
 * Interface mentionnant qu'un objet peut déclencher une erreur données
 * @author Machkour Oke
 */
public interface ErrorInitiator {
    void addListener(ErrorListener toAdd);
}
