package com.github.JoseAngelGiron.view;

import com.github.JoseAngelGiron.model.services.ActividadServices;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;

import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class RegisterHabitController extends Controller implements Initializable {

    @FXML
    private ListView<TextField> listViewCampos;

    @Override
    public void onOpen(Object input, Object input2) throws IOException {

    }

    @Override
    public void onClose(Object output) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }


    private void retrieveActivitys() throws IOException {
        ActividadServices actividadServices = new ActividadServices();



    }
}
