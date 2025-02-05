package com.github.JoseAngelGiron.view;

import com.github.JoseAngelGiron.model.UserSession;
import com.github.JoseAngelGiron.model.entity.Actividad;
import com.github.JoseAngelGiron.model.entity.Habito;
import com.github.JoseAngelGiron.model.entity.Usuario;
import com.github.JoseAngelGiron.model.services.HabitoServices;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ResourceBundle;

public class UpdateHabitController extends Controller implements Initializable {

    @FXML
    TableView<Habito> activityTable;
    @FXML
    TableColumn<Habito, String> categoryColumn;
    @FXML
    TableColumn<Habito, String> habitColumn;
    @FXML
    TableColumn<Habito, Integer> frequencyColumn;
    @FXML
    TableColumn<Habito, String> typeColumn;
    @FXML
    TableColumn<Habito, String> lastTimeColumn;


    @FXML
    private TextField categoryArea;
    @FXML
    private TextField nameArea;

    @FXML
    private TextField frequencyArea;
    @FXML
    private TextField typeArea;
    @FXML
    private DatePicker datePicker;

    private List<Habito> habits;
    private ObservableList<Habito> observableHabitsList;
    private Habito selectedHabit;


    @Override
    public void onOpen(Object input, Object input2) throws IOException {
        showUserHabits();
        configureListener();
    }

    @Override
    public void onClose(Object output) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
    @FXML
    public boolean updateHabit() {
        boolean updated = false;
        HabitoServices habitServices = new HabitoServices();
        //refactorizar esto a etiquetas por si falla algo, dar avisos individualizados



        if(!frequencyArea.getText().isEmpty() && !typeArea.getText().isEmpty() && datePicker.getValue() != null
        && datePicker.getValue().isBefore(LocalDate.now())) { //ver que se permita el propio día en cuestión
            //revisar el type

            selectedHabit.setFrecuencia(Integer.parseInt(frequencyArea.getText()));
            selectedHabit.setTipo(typeArea.getText());

            selectedHabit.setUltimaFecha(datePicker.getValue());
            //Usar el boolean para mostrar aviso de etiqueta
            updated = habitServices.update(selectedHabit);
        }

        return updated;
    }

    private void showUserHabits(){
        Usuario usuario = UserSession.UserSession().getUserLoggedIn();
        HabitoServices habitoServices = new HabitoServices();
        habits = habitoServices.findAllHabitsByUser(usuario);

        observableHabitsList = FXCollections.observableArrayList(habits);

        activityTable.setItems(observableHabitsList);

        categoryColumn.setCellValueFactory(cellData -> {
            Habito habit = cellData.getValue();
            String name = habit.getIdActividad().getIdCategoria().getNombre();
            return new SimpleStringProperty(name);
        });

        habitColumn.setCellValueFactory(cellData -> {
            Habito habit = cellData.getValue();
            String name = habit.getIdActividad().getNombre();
            return new SimpleStringProperty(name);
        });
        frequencyColumn.setCellValueFactory(cellData -> {
            Habito habit = cellData.getValue();
            int frequency = habit.getFrecuencia();
            return new ReadOnlyObjectWrapper<>(frequency);
        });
        typeColumn.setCellValueFactory(cellData -> {
            Habito habit = cellData.getValue();
            String type = habit.getTipo();
            return new SimpleStringProperty(type);
        });
        lastTimeColumn.setCellValueFactory(cellData -> {
            Habito habit = cellData.getValue();
            LocalDate date = habit.getUltimaFecha();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            String formattedDate = (date != null) ? date.format(formatter) : "";
            return new ReadOnlyObjectWrapper<>(formattedDate);
        });


    }

    private void configureListener(){
        activityTable.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                selectedHabit = activityTable.getSelectionModel().getSelectedItem();

                if (selectedHabit != null) {

                    categoryArea.setText(selectedHabit.getIdActividad().getIdCategoria().getNombre());
                    nameArea.setText(selectedHabit.getIdActividad().getNombre());
                    frequencyArea.setText(String.valueOf(selectedHabit.getFrecuencia()));
                    typeArea.setText(selectedHabit.getTipo());
                    datePicker.setValue(selectedHabit.getUltimaFecha());

                }
            }
        });
    }
}
