package com.lop.View.src.source;

import java.awt.event.ActionEvent;

public interface ViewToController {
    void add(ActionEvent action);
    void delete(ActionEvent action);
    void update(ActionEvent action);
    void listeSalleReservataire(ActionEvent action);
    void evtInBloc(ActionEvent action);
    void actifReservateur(ActionEvent action);
    void dayReservation(ActionEvent action);
    void pastEvent(ActionEvent action);

}
