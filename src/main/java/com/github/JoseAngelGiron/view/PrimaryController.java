package com.github.JoseAngelGiron.view;

import java.io.IOException;

import com.github.JoseAngelGiron.App;
import javafx.fxml.FXML;

public class PrimaryController {

    @FXML
    private void switchToSecondary() throws IOException {
        App.setRoot("secondary");
    }
}
