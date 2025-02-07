package com.github.JoseAngelGiron.view;

import com.github.JoseAngelGiron.model.UserSession;
import com.github.JoseAngelGiron.model.entity.Usuario;
import com.github.JoseAngelGiron.model.services.UsuarioServices;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static com.github.JoseAngelGiron.utils.security.Security.encryptPassword;
import static com.github.JoseAngelGiron.view.RegisterController.*;

public class ProfileController extends Controller implements Initializable {

    @FXML
    private TextField usernameField ;
    @FXML
    private TextField passwordField;
    @FXML
    private TextField emailField;

    @FXML
    private Label warningLabel;

    private Usuario currentUser;





    @Override
    public void onOpen(Object input, Object input2) throws IOException {

    }

    @Override
    public void onClose(Object output) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        currentUser = UserSession.UserSession().getUserLoggedIn();
        showUserData();
    }

    private void showUserData(){

        usernameField.setText(currentUser.getNombre());
        emailField.setText(currentUser.getEmail());
    }


    @FXML
    private void updateProfile() throws IOException {

       String userName = usernameField.getText();
       String userEmail = emailField.getText();
       String password = passwordField.getText();

       if(validateNick(userName) && validateEmail(userEmail) && validatePassword(password)){

           UsuarioServices usuarioServices = new UsuarioServices();
           currentUser.setNombre(userName);
           currentUser.setEmail(userEmail);
           currentUser.setContrasena(encryptPassword(password));
           usuarioServices.update(currentUser);

           warningLabel.setVisible(true);
           warningLabel.setText("Perfil actualizado correctamente");

       }else{

           warningLabel.setVisible(true);
           warningLabel.setText("Rellene los campos correctamente");
       }


    }
}
