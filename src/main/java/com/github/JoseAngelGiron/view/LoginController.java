package com.github.JoseAngelGiron.view;

import com.github.JoseAngelGiron.App;
import com.github.JoseAngelGiron.model.UserSession;
import com.github.JoseAngelGiron.model.entity.Usuario;
import com.github.JoseAngelGiron.model.services.UsuarioServices;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static com.github.JoseAngelGiron.utils.security.Security.encryptPassword;

public class LoginController extends Controller implements Initializable {
    @FXML
    private TextField emailText;
    @FXML
    private TextField passwordText;

    private Usuario userToLogin;

    @FXML
    private Label loginError;


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
    private void LogUser() throws IOException {
        userToLogin = new Usuario();
        String email = emailText.getText();
        String password = encryptPassword(passwordText.getText());

        UsuarioServices usuarioService = new UsuarioServices();
        userToLogin = usuarioService.findUsuarioByEmail(email);

        if(userToLogin.getId() != null && userToLogin.getContrasena().equals(password)){
            UserSession session = UserSession.UserSession();
            session.setUserIntoSession(userToLogin);
            changeToMainWindow();

        }else{

            System.out.println("No existe ningún usuario");
        }

    }

    /**
     * Changes the current scene to the registration scene.
     * @throws IOException If an I/O error occurs while setting the root scene.
     */
    @FXML
    private void changeToRegister() throws IOException {
        App.setRoot(Scenes.REGISTER.getURL());

        App.scene.getWindow().setWidth(500);
        App.scene.getWindow().setHeight(580);

    }

    /**
     * Changes the current scene to the mainWindow scene.
     * @throws IOException If an I/O error occurs while setting the root scene.
     */
    @FXML
    private void changeToMainWindow() throws IOException {
        Stage stage = (Stage) App.scene.getWindow();

        Screen screen = Screen.getPrimary();
        Rectangle2D bounds = screen.getVisualBounds();


        stage.setWidth(bounds.getWidth());
        stage.setHeight(bounds.getHeight());


        stage.setX(bounds.getMinX());
        stage.setY(bounds.getMinY());


        App.setRoot(Scenes.ROOT.getURL());

    }

    /**
     * Resizes the application window to fill the entire screen.
     * It sets the stage position to the top-left corner and adjusts the width and height to match the screen size.
     * The window becomes resizable after resizing.
     */
    public static void resizeWindow() {

        Stage stage = (Stage) App.scene.getWindow();

        Rectangle2D screenSize = Screen.getPrimary().getVisualBounds();

        stage.setX(screenSize.getMinX());
        stage.setY(screenSize.getMinY());
        stage.setWidth(screenSize.getWidth());
        stage.setHeight(screenSize.getHeight());

        stage.setResizable(true);
    }
}
