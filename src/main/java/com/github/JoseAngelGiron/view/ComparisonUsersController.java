package com.github.JoseAngelGiron.view;


import com.github.JoseAngelGiron.model.UserSession;
import com.github.JoseAngelGiron.model.entity.Usuario;
import com.github.JoseAngelGiron.model.services.HuellaServices;
import com.github.JoseAngelGiron.model.services.UsuarioServices;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

public class ComparisonUsersController extends Controller implements Initializable {

    @FXML
    private TableView<Usuario> usersTableView;
    @FXML
    private TableColumn<Usuario, String> usersNameColumn;

    @FXML
    private BarChart<String, Number> barChartComparison;

    @FXML
    private CategoryAxis xAxis;

    @FXML
    private NumberAxis yAxis;

    private Usuario currentUser;
    private Usuario selectedUser;
    private List<Usuario> usersList;
    private ObservableList<Usuario> observableUserList;

    private HuellaServices huellaService;
    private UsuarioServices userServices;

    @Override
    public void onOpen(Object input, Object input2) throws IOException {
        showUsers();
    }

    @Override
    public void onClose(Object output) {
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        currentUser = UserSession.UserSession().getUserLoggedIn();
        compareUserWithAverage();
        configureListener();

    }

    private void showUsers() {
        userServices = new UsuarioServices();
        usersList = userServices.findAll();

        observableUserList = FXCollections.observableArrayList(usersList);
        usersTableView.setItems(observableUserList);


        usersNameColumn.setCellValueFactory(celldata ->
                new SimpleStringProperty(celldata.getValue().getNombre()));
    }

    private void configureListener(){
        usersTableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                selectedUser = newSelection;
                compareWithSelectedUser();
            }
        });
    }

    @FXML
    public void compareUserWithAverage() {
        huellaService = new HuellaServices();

        Map<String, BigDecimal> userFootprint = huellaService.findUserFootprintByCategory(currentUser);


        Map<String, BigDecimal> avgFootprint = huellaService.findAverageFootprintByCategory();


        barChartComparison.getData().clear();


        XYChart.Series<String, Number> userSeries = new XYChart.Series<>();
        userSeries.setName("Tu huella de carbono");

        XYChart.Series<String, Number> avgSeries = new XYChart.Series<>();
        avgSeries.setName("Media de todos los usuarios");

        for (String category : avgFootprint.keySet()) {

            double userValue = userFootprint.getOrDefault(category, BigDecimal.ZERO).doubleValue();
            double avgValue = avgFootprint.get(category).doubleValue();

            userSeries.getData().add(new XYChart.Data<>(category, userValue));
            avgSeries.getData().add(new XYChart.Data<>(category, avgValue));
        }

        barChartComparison.getData().addAll(userSeries, avgSeries);
    }

    private void compareWithSelectedUser() {
        huellaService = new HuellaServices();


        Map<String, BigDecimal> userFootprint = huellaService.findUserFootprintByCategory(currentUser);
        Map<String, BigDecimal> selectedUserFootprint = huellaService.findUserFootprintByCategory(selectedUser);


        barChartComparison.getData().clear();


        XYChart.Series<String, Number> currentUserSeries = new XYChart.Series<>();
        currentUserSeries.setName("Tu huella de carbono");

        XYChart.Series<String, Number> selectedUserSeries = new XYChart.Series<>();
        selectedUserSeries.setName("Huella de " + selectedUser.getNombre());


        for (String category : userFootprint.keySet()) {
            double userValue = userFootprint.getOrDefault(category, BigDecimal.ZERO).doubleValue();
            double selectedValue = selectedUserFootprint.getOrDefault(category, BigDecimal.ZERO).doubleValue();


            currentUserSeries.getData().add(new XYChart.Data<>(category, userValue));
            selectedUserSeries.getData().add(new XYChart.Data<>(category, selectedValue));
        }


        barChartComparison.getData().addAll(currentUserSeries, selectedUserSeries);
    }



}

