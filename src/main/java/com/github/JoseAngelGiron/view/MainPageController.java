package com.github.JoseAngelGiron.view;

import com.github.JoseAngelGiron.model.entity.Huella;
import com.github.JoseAngelGiron.model.entity.Usuario;
import com.github.JoseAngelGiron.model.services.CategoriaServices;
import com.github.JoseAngelGiron.model.services.HuellaServices;
import com.github.JoseAngelGiron.model.services.UsuarioServices;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.io.IOException;

import java.math.BigDecimal;
import java.net.URL;
import java.util.*;

public class MainPageController extends Controller implements Initializable {

    @FXML
    private TableView<Map.Entry<Usuario, Double>> usuariosTableView;

    @FXML
    private TableColumn<Map.Entry<Usuario, Double>, String> usuarioNameColumn;

    @FXML
    private TableColumn<Map.Entry<Usuario, Double>, Double> usersAVGColumn;

    @FXML
    private TableView<Map.Entry<String, Double>> categoryTableView;

    @FXML
    private TableColumn<Map.Entry<String, Double>, String> categoryNameColumn;

    @FXML
    private TableColumn<Map.Entry<String, Double>, Double> categoryImpactoColumn;

    @FXML
    private LineChart<String, Number> huellaLineChart;

    @FXML
    private CategoryAxis xAxis;

    @FXML
    private NumberAxis yAxis;

    private List<Object[]> usersList;
    private UsuarioServices userServices;
    private CategoriaServices categoryServices;
    private HuellaServices printServices;

    @Override
    public void onOpen(Object input, Object input2) throws IOException {
        showUsers();
        showCategorias();
        loadHuellasForCurrentMonth();
    }

    @Override
    public void onClose(Object output) {
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    private void showUsers() throws IOException {
        userServices = new UsuarioServices();
        usersList = userServices.findUsersAndAVGImpact();

        List<Map.Entry<Usuario, Double>> combinedList = new ArrayList<>();

        for (Object[] result : usersList) {
            String nombreUsuario = (String) result[0];
            Double totalImpacto = (Double) result[1];

            Usuario userToAdd = new Usuario();
            userToAdd.setNombre(nombreUsuario);

            combinedList.add(new AbstractMap.SimpleEntry<>(userToAdd, totalImpacto));
        }

        ObservableList<Map.Entry<Usuario, Double>> observableUsersList = FXCollections.observableArrayList(combinedList);
        usuariosTableView.setItems(observableUsersList);

        usuarioNameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getKey().getNombre()));
        usersAVGColumn.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getValue()).asObject());
    }

    private void showCategorias() throws IOException {
        categoryServices = new CategoriaServices();
        List<Map.Entry<String, Double>> categoriesList = categoryServices.findCategoriesWithTheHigiestImpact();

        ObservableList<Map.Entry<String, Double>> observableCategoryList = FXCollections.observableArrayList(categoriesList);
        categoryTableView.setItems(observableCategoryList);

        categoryNameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getKey()));
        categoryImpactoColumn.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getValue()).asObject());
    }

    private void loadHuellasForCurrentMonth() {
        printServices = new HuellaServices();

        List<Huella> huellasDelMes = printServices.findPrintsThisMonth();

        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Huellas de este mes");

        List<String> daysOfMonth = new ArrayList<>();

        for (Huella huella : huellasDelMes) {
            int dayOfMonth = huella.getFecha().getDayOfMonth();
            BigDecimal valorHuella = huella.getValor();
            Double doubleValue = valorHuella.doubleValue();


            if (!daysOfMonth.contains(String.valueOf(dayOfMonth))) {
                daysOfMonth.add(String.valueOf(dayOfMonth));
            }


            series.getData().add(new XYChart.Data<>(String.valueOf(dayOfMonth), doubleValue));
        }


        huellaLineChart.getData().clear();
        huellaLineChart.getData().add(series);


        xAxis.setCategories(FXCollections.observableArrayList(daysOfMonth));
    }
}




