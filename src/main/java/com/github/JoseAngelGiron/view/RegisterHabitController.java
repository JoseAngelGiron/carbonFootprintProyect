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
    private TextField activityName;
    @FXML
    private TextField category;
    @FXML
    private TextField emisionFactor;
    @FXML
    private TextField unity;

    @FXML
    private TextField frecuency;

    @FXML
    private ComboBox<String> typeBox;
    @FXML
    private DatePicker datePicker;

    @FXML
    private Label errorFrecuencyLabel;
    @FXML
    private Label errorDateLabel;
    @FXML
    private Label errorRegisteredLabel;




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

        if(selectedActivity == null){
            errorRegisteredLabel.setVisible(true);
            errorRegisteredLabel.setText("Debe seleccionar un actividad previamente");
            return;
        }
        if(frecuency.getText() ==null){
            errorFrecuencyLabel.setVisible(true);
            errorDateLabel.setText("Inserte una frecuencia para la actividad");
        }
        if(datePicker.getValue() == null) {
            errorDateLabel.setVisible(true);
            errorDateLabel.setText("Inserte una fecha valida");
            return;
        }

        LocalDate dateSelected = datePicker.getValue();
        if(dateSelected.isAfter(LocalDate.now())) {
            errorDateLabel.setText("La fecha no puede ser superior a la fecha actual");
            return;
        }


        Usuario currentUser = UserSession.UserSession().getUserLoggedIn();
        HabitoId habitoId = new HabitoId(selectedActivity.getId(), currentUser.getId());
        String typeFrecuency = typeBox.getValue();
        int numberOfFrequency = -1;

        if(typeFrecuency == null){
            errorFrecuencyLabel.setVisible(true);
            errorFrecuencyLabel.setText("Seleccione un tipo de frecuencia");
        }

        try {
            numberOfFrequency = Integer.valueOf(frecuency.getText());
        } catch (NumberFormatException e) {
            errorFrecuencyLabel.setVisible(true);
            errorFrecuencyLabel.setText("Inserte un valor valido");
        }

        if(dateSelected.isAfter(LocalDate.now())) {
            errorDateLabel.setVisible(true);
            errorDateLabel.setText("La fecha actual no puede ser superior a la fecha actual");
            return;
        }



        if(numberOfFrequency!=-1 && dateSelected!=null && typeFrecuency != null){

            Habito newHabit = new Habito(habitoId, currentUser,  selectedActivity, numberOfFrequency, typeFrecuency, dateSelected);
            HabitoServices habitoServices = new HabitoServices();

            boolean registered = habitoServices.save(newHabit);

            if(registered) {

                errorRegisteredLabel.setVisible(true);
                errorRegisteredLabel.setText("Habito registrado con éxito");

            }else{

                errorRegisteredLabel.setVisible(true);
                errorRegisteredLabel.setText("No se pudo registrar el hábito, ya esta registrado");
            }

        }else {

            if (numberOfFrequency == -1){
                errorFrecuencyLabel.setVisible(true);
                errorFrecuencyLabel.setText("Inserte un valor valido");
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
