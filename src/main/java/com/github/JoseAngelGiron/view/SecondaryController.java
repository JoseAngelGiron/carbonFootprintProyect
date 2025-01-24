package com.github.JoseAngelGiron.view;

import java.io.IOException;

import com.github.JoseAngelGiron.App;
import javafx.fxml.FXML;

public class SecondaryController {

    @FXML
    private void switchToPrimary() throws IOException {
        App.setRoot("primary");
    }
}