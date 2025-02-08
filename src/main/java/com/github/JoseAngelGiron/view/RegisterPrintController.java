package com.github.JoseAngelGiron.view;

import com.github.JoseAngelGiron.model.UserSession;
import com.github.JoseAngelGiron.model.entity.Actividad;
import com.github.JoseAngelGiron.model.entity.Huella;
import com.github.JoseAngelGiron.model.entity.Usuario;
import com.github.JoseAngelGiron.model.services.ActividadServices;
import com.github.JoseAngelGiron.model.services.HuellaServices;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;


import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

public class RegisterPrintController extends Controller implements Initializable {

    @FXML
    private TableView<Actividad> tableActivities;

    @FXML
    private TableColumn<Actividad, String> categoryColumn;
    @FXML
    private TableColumn<Actividad, String> activityColumn;

    @FXML
    private TextField activityNameField;
    @FXML
    private TextField categoryNameField;
    @FXML
    private TextField emisionFactoryField;

    @FXML
    private TextField valueField;
    @FXML
    private TextField unityField;
    @FXML
    private DatePicker datePicker;

    private List<Actividad> activities;
    private ObservableList<Actividad> observableActivities;
    private Actividad selectedActivity;

    @Override
    public void onOpen(Object input, Object input2) throws IOException {
        showActivitiesAndCategories();
        configureListener();
    }

    @Override
    public void onClose(Object output) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @FXML
    private void registerNewPrint(){
        HuellaServices printService = new HuellaServices();

        if(datePicker.getValue() != null && valueField.getText() != null){

            Usuario currentUser = UserSession.UserSession().getUserLoggedIn();
            BigDecimal value = null;
            try {
                value = new BigDecimal(valueField.getText());

            } catch (NumberFormatException e) {

                return;
            }
            String unity = unityField.getText();
            LocalDate dateOfPrint = datePicker.getValue();

            Huella print = new Huella(currentUser, selectedActivity, value, unity, dateOfPrint);

            printService.save(print);

        }
    }

    private void showActivitiesAndCategories() throws IOException {
        ActividadServices actividadServices = new ActividadServices();

        activities = actividadServices.findAllActivities();

        observableActivities = FXCollections.observableArrayList(activities);
        tableActivities.setItems(observableActivities);

        categoryColumn.setCellValueFactory( cellData -> {
            Actividad activity = cellData.getValue();
            String nameCategory = activity.getIdCategoria().getNombre();
            return new SimpleStringProperty(nameCategory);
        });

        activityColumn.setCellValueFactory( cellData -> {
            Actividad activity = cellData.getValue();
            String activityName = activity.getNombre();
            return new SimpleStringProperty(activityName);
        });


    }

    private void configureListener(){
        tableActivities.setOnMouseClicked(event -> {

            if (event.getClickCount() == 2) {
                selectedActivity = tableActivities.getSelectionModel().getSelectedItem();

                if(selectedActivity != null){
                    activityNameField.setText(selectedActivity.getNombre());
                    categoryNameField.setText(selectedActivity.getIdCategoria().getNombre());
                    emisionFactoryField.setText(String.valueOf(selectedActivity.getIdCategoria().getFactorEmision()));
                    unityField.setText(selectedActivity.getIdCategoria().getUnidad());
                }
            }
        });
    }
}
