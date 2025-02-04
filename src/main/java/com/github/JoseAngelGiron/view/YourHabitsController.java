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

import java.io.IOException;
import java.net.URL;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ResourceBundle;

public class YourHabitsController extends Controller implements Initializable {

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

    private List<Habito> habits;
    private ObservableList<Habito> observableHabitsList;

    @Override
    public void onOpen(Object input, Object input2) throws IOException {
        showUserHabits();
    }

    @Override
    public void onClose(Object output) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


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

}
