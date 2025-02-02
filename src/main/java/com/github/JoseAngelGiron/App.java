package com.github.JoseAngelGiron;

import com.github.JoseAngelGiron.view.Controller;
import com.github.JoseAngelGiron.view.Scenes;

import com.github.JoseAngelGiron.view.View;
import javafx.application.Application;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;


/**
 * JavaFX App
 */
public class App extends Application {

    public static Scene scene;
    public static Stage stage;

    public static Controller centerController;
    public static Controller modalController;

    /**
     * The main entry point for the JavaFX application.
     * This method is called when the application is launched.
     * It sets the initial scene, configures the stage, and displays it.
     *
     * @param stage The primary stage for this application, onto which
     *              the application scene can be set.
     * @throws IOException if the FXML file cannot be loaded.
     */
    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML(Scenes.LOGIN.getURL()), 600, 430);
        stage.setScene(scene);
        stage.setResizable(false);


        stage.setTitle("Footprint");
        stage.show();

    }

    /**
     * Changes the root of the current scene to the specified FXML file.
     *
     * @param fxml The name of the FXML file to load (without the .fxml extension).
     * @throws IOException if the FXML file cannot be loaded.
     */
    public static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));

    }

    /**
     * Loads the specified FXML file and returns the corresponding Parent node.
     *
     * @param fxml The name of the FXML file to load (without the .fxml extension).
     * @return The Parent node loaded from the FXML file.
     * @throws IOException if the FXML file cannot be loaded.
     */
    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static View loadFXML(Scenes scenes) throws IOException {
        String url = scenes.getURL();
        FXMLLoader loader = new FXMLLoader(App.class.getResource(url + ".fxml"));
        Parent p = loader.load();
        Controller c = loader.getController();
        View view = new View();
        view.scene=p;
        view.controller=c;
        return view;
    }


    /**
     * Changes the displayed scene in a Pane container with a new scene by loading an FXML file
     * and associating it with its controller.
     *
     * @param scene The scene to be loaded.
     * @param pane  The Pane container in which the new scene will be shown.
     * @param data  Optional data to be passed to the controller of the new scene.
     * @throws IOException If an error occurs while loading the FXML file.
     */
    /**
     * Changes the displayed scene in a Pane container with a new scene by loading an FXML file
     * and associating it with its controller.
     *
     * @param scene The scene to be loaded.
     * @param pane  The Pane container in which the new scene will be shown.
     * @param data  Optional data to be passed to the controller of the new scene.
     * @throws IOException If an error occurs while loading the FXML file.
     */
    public static void changeScene(Scenes scene, Pane pane, Object data) throws IOException {
        pane.getChildren().clear();
        View view = loadFXML(scene);
        pane.getChildren().add(view.scene);
        centerController = view.controller;

        centerController.onOpen(data, null);
    }

    public static void changeScene(Scenes scene, ScrollPane scrollPane, Object data) throws IOException {

        scrollPane.setContent(null);

        View view = loadFXML(scene);

        scrollPane.setContent(view.scene);

        centerController = view.controller;
        centerController.onOpen(data, null);
    }

    /**
     * Opens a modal dialog window.
     * @param scene The scene to be displayed in the modal window.
     * @param title The title of the modal window.
     * @param parent The parent controller of the modal window.
     * @param data Additional data to be passed to the modal window.
     * @throws IOException If an error occurs while loading the FXML for the modal window.
     */
    public static void openModal(Scenes scene, String title, Controller parent, Object data) throws IOException {
        View view = loadFXML(scene);
        Stage stage = new Stage();
        stage.setTitle(title);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initOwner(App.stage);
        Scene _scene = new Scene(view.scene);
        stage.setScene(_scene);
        view.controller.onOpen(parent, data);
        stage.showAndWait();
        modalController=view.controller;
    }
    /**
     * The main method for launching the application.
     *
     * @param args Command line arguments (not used).
     */
    public static void main(String[] args) {
        launch();
    }

}