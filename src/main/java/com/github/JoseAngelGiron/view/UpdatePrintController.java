package com.github.JoseAngelGiron.view;

import com.github.JoseAngelGiron.model.UserSession;
import com.github.JoseAngelGiron.model.entity.Actividad;
import com.github.JoseAngelGiron.model.entity.Huella;
import com.github.JoseAngelGiron.model.entity.Usuario;
import com.github.JoseAngelGiron.model.services.HuellaServices;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ResourceBundle;

public class UpdatePrintController extends Controller implements Initializable {

    @FXML
    private TableView<Huella> printTable;
    @FXML
    private TableColumn<Huella, Integer> numberColumn;
    @FXML
    private TableColumn<Huella, String> activityColumn;
    @FXML
    private TableColumn<Huella, String> categoryColumn;
    @FXML
    private TableColumn<Huella, BigDecimal> emisionColumn;
    @FXML
    private TableColumn<Huella, String> unityColumn;
    @FXML
    private TableColumn<Huella, String> dateColumn;


    @FXML
    private TextField numberPrintField;
    @FXML
    private TextField activityField;
    @FXML
    private TextField categoryField;
    @FXML
    private TextField emisionField;
    @FXML
    private TextField unityField;
    @FXML
    private DatePicker datePicker;


    private List<Huella> prints;
    private ObservableList<Huella> observablePrints;
    private Huella printSelected;



    @Override
    public void onOpen(Object input, Object input2) throws IOException {
        showYourPrints();
        configureListener();

    }

    @Override
    public void onClose(Object output) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
    @FXML
    public void updatePrint(){

        if(printSelected != null && !unityField.getText().isEmpty() && !(datePicker.getValue() != null) &&
        datePicker.getValue().isBefore(LocalDate.now())) {
            printSelected.setFecha(datePicker.getValue());

            //Poner control y mostrar etiquetas
            printSelected.setValor(BigDecimal.valueOf(Long.parseLong(emisionField.getText())));

            HuellaServices huellaServices = new HuellaServices();
            huellaServices.update(printSelected);
        }
    }



    private void showYourPrints(){
        Usuario currentUser = UserSession.UserSession().getUserLoggedIn();
        HuellaServices huellaServices = new HuellaServices();

        prints = huellaServices.findAllprintsByUser(currentUser);
        observablePrints = FXCollections.observableArrayList(prints);
        printTable.setItems(observablePrints);

        numberColumn.setCellValueFactory(cellData ->
                new ReadOnlyObjectWrapper<>(printTable.getItems().indexOf(cellData.getValue()) + 1));

        activityColumn.setCellValueFactory(cellData -> {
            Huella print = cellData.getValue();
            String activityName = print.getIdActividad().getNombre();
            return new SimpleStringProperty(activityName);
        });

        categoryColumn.setCellValueFactory(cellData -> {
            Huella print = cellData.getValue();
            String categoryName = print.getIdActividad().getIdCategoria().getNombre();
            return new SimpleStringProperty(categoryName);
        });

        emisionColumn.setCellValueFactory( celldata -> {
            Huella print = celldata.getValue();
            BigDecimal emision = print.getIdActividad().getIdCategoria().getFactorEmision();
            return new ReadOnlyObjectWrapper<>(emision);
        });
        unityColumn.setCellValueFactory( celldata -> {
            Huella print = celldata.getValue();
            String unityName = print.getIdActividad().getIdCategoria().getUnidad();
            return new SimpleStringProperty(unityName);
        });
        dateColumn.setCellValueFactory(cellData -> {
            Huella print = cellData.getValue();
            LocalDate date = print.getFecha();


            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            String formattedDate = date.format(formatter);

            return new SimpleStringProperty(formattedDate);
        });

    }

    private void configureListener() {

        printTable.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                printSelected = printTable.getSelectionModel().getSelectedItem();

                if (printSelected != null) {
                    int rowIndex = printTable.getItems().indexOf(printSelected) + 1;
                    numberPrintField.setText(String.valueOf(rowIndex));
                    activityField.setText(printSelected.getIdActividad().getNombre());
                    categoryField.setText(printSelected.getIdActividad().getIdCategoria().getNombre());
                    emisionField.setText(printSelected.getIdActividad().getIdCategoria().getFactorEmision().toString());
                    unityField.setText(printSelected.getUnidad());

                    datePicker.setValue(printSelected.getFecha());


                }
            }
        });
    }
}
