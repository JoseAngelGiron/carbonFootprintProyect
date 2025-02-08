package com.github.JoseAngelGiron.view;

import com.github.JoseAngelGiron.model.UserSession;
import com.github.JoseAngelGiron.model.entity.Habito;
import com.github.JoseAngelGiron.model.entity.Usuario;
import com.github.JoseAngelGiron.model.services.HabitoServices;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;


import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ResourceBundle;

public class DeleteHabitController extends Controller implements Initializable {

    @FXML
    private TableView<Habito> activityTable;
    @FXML
    private TableColumn<Habito, String> categoryColumn;
    @FXML
    private TableColumn<Habito, String> habitColumn;
    @FXML
    private TableColumn<Habito, Integer> frequencyColumn;
    @FXML
    private TableColumn<Habito, String> typeColumn;
    @FXML
    private TableColumn<Habito, String> lastTimeColumn;

    @FXML
    private TextField categoryArea;
    @FXML
    private TextField nameArea;

    @FXML
    private TextField frequencyArea;
    @FXML
    private TextField typeArea;
    @FXML
    private TextField dateArea;

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
    private void deleteHabit() {

        HabitoServices habitService = new HabitoServices();

        habitService.delete(selectedHabit);

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
                    LocalDate date = selectedHabit.getUltimaFecha();
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                    String formattedDate = (date != null) ? date.format(formatter) : "";
                    dateArea.setText(formattedDate);

                }
            }
        });
    }
}
