package com.github.JoseAngelGiron.view;


import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static com.github.JoseAngelGiron.App.changeScene;


public class HomeController extends Controller implements Initializable {

    @FXML
    private Pane mainPane;



    @Override
    public void onOpen(Object input, Object input2) throws IOException {

    }

    @Override
    public void onClose(Object output) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @FXML
    public void changeProfile() throws IOException {
        changeScene(Scenes.PROFILE, mainPane,null);

    }

    @FXML
    public void changeRegisterHabit() throws IOException {
        changeScene(Scenes.REGISTERNEWHABIT, mainPane,null);

    }

}



