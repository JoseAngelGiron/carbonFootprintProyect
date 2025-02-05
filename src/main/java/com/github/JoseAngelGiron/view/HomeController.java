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
    public void changeToRegisterHabit() throws IOException {
        changeScene(Scenes.REGISTERNEWHABIT, mainPane,null);
    }

    @FXML
    public void changeToYourHabits() throws IOException {
        changeScene(Scenes.YOURHABITS, mainPane,null);
    }

    @FXML
    public void changeToUpdateHabit() throws IOException {
        changeScene(Scenes.UPDATEHABIT, mainPane,null);
    }

    @FXML
    public void changeToDeleteHabit() throws IOException {
        changeScene(Scenes.DELETEHABIT, mainPane,null);
    }


    @FXML
    public void changeToRegisterPrint() throws IOException {
        changeScene(Scenes.REGISTERNEWPRINT, mainPane,null);
    }

    @FXML
    public void changeToYourPrints() throws IOException {
        changeScene(Scenes.YOURPRINTS, mainPane,null);
    }

    @FXML
    public void changeToUpdatePrints() throws IOException {
        changeScene(Scenes.UPDATEPRINT, mainPane,null);
    }

    @FXML
    public void changeToDeletePrints() throws IOException {
        changeScene(Scenes.DELETEPRINT, mainPane,null);
    }

    @FXML
    public void changeToImpactFootprints() throws IOException {
        changeScene(Scenes.IMPACTFOOTPRINTS, mainPane,null);
    }


}



