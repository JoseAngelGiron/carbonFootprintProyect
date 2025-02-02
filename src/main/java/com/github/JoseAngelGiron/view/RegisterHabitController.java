package com.github.JoseAngelGiron.view;

import com.github.JoseAngelGiron.model.UserSession;
import com.github.JoseAngelGiron.model.entity.Actividad;
import com.github.JoseAngelGiron.model.entity.Habito;
import com.github.JoseAngelGiron.model.entity.HabitoId;
import com.github.JoseAngelGiron.model.entity.Usuario;
import com.github.JoseAngelGiron.model.services.ActividadServices;
import com.github.JoseAngelGiron.model.services.HabitoServices;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

public class RegisterHabitController extends Controller implements Initializable {

    @FXML
    private TableView<Actividad> actividadTable;
    @FXML
    private TableColumn<Actividad, String> activityNameColumn;
    @FXML
    private TableColumn<Actividad, String> activityCategoryColumn;


    @FXML
    private TextArea activityName;
    @FXML
    private TextArea category;
    @FXML
    private TextArea emisionFactor;
    @FXML
    private TextArea unity;

    @FXML
    private TextArea frecuency;

    @FXML
    private ComboBox<String> typeBox;
    @FXML
    private DatePicker datePicker;




    private List<Actividad> activities;
    private ObservableList<Actividad> observableActivities;
    private Actividad selectedActivity;


    @Override
    public void onOpen(Object input, Object input2) throws IOException {
        initializeComboBox();
        try {
            showAllActivities();
            configureListener();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClose(Object output) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
    @FXML
    public void registerHabit() throws IOException {
        Usuario currentUser = UserSession.UserSession().getUserLoggedIn();
        HabitoId habitoId = new HabitoId(selectedActivity.getId(), currentUser.getId());
        String typeFrecuency = typeBox.getValue();
        int numberOfFrequency = -1;

        try {
            numberOfFrequency = Integer.valueOf(frecuency.getText());
        } catch (NumberFormatException e) {
            //Poner ETIQUETA
            System.out.println("El valor ingresado no es un número entero válido.");
        }

        LocalDate dateSelected = datePicker.getValue();

        if(numberOfFrequency!=-1 && dateSelected!=null && typeFrecuency != null){

            Habito newHabit = new Habito(habitoId, currentUser,  selectedActivity, numberOfFrequency, typeFrecuency, dateSelected);
            HabitoServices habitoServices = new HabitoServices();
            boolean registered = habitoServices.save(newHabit);

            if(registered) {
                //Poner ETIQUETA

                //Aviso de actividad registrada con éxito
            }else{
                //Poner ETIQUETA
                //No se pudo registrar, actividad ya registrada previamente
            }
        }

    }

    private void initializeComboBox() {
        typeBox.getItems().addAll("Diaria", "Semanal", "Mensual", "Anual");
    }

    private void showAllActivities() throws IOException {
        ActividadServices actividadServices = new ActividadServices();
        activities = actividadServices.findAllActivities();

        observableActivities = FXCollections.observableArrayList(activities);

        actividadTable.setItems(observableActivities);

        activityNameColumn.setCellValueFactory(cellData -> {
            Actividad activity = cellData.getValue();
            String name = activity.getNombre();
            return new SimpleStringProperty(name);
        });

        activityCategoryColumn.setCellValueFactory(cellData -> {
            Actividad activity = cellData.getValue();
            String name = activity.getIdCategoria().getNombre();
            return new SimpleStringProperty(name);
        });

    }

    private void configureListener(){
        actividadTable.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                 selectedActivity = actividadTable.getSelectionModel().getSelectedItem();
                if (selectedActivity != null) {
                    activityName.setText(selectedActivity.getNombre());
                    category.setText(selectedActivity.getIdCategoria().getNombre());
                    emisionFactor.setText(selectedActivity.getIdCategoria().getFactorEmision().toString());
                    unity.setText(selectedActivity.getIdCategoria().getUnidad());
                }
            }
        });
    }

}
