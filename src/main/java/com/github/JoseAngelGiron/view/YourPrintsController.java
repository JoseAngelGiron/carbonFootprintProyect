package com.github.JoseAngelGiron.view;

import com.github.JoseAngelGiron.model.UserSession;
import com.github.JoseAngelGiron.model.entity.Huella;
import com.github.JoseAngelGiron.model.entity.Recomendacion;
import com.github.JoseAngelGiron.model.entity.Usuario;
import com.github.JoseAngelGiron.model.services.HuellaServices;
import com.github.JoseAngelGiron.model.services.RecomendacionServices;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ResourceBundle;

public class YourPrintsController extends Controller implements Initializable {

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

    private List<Huella> prints;
    private ObservableList<Huella> observablePrints;


    @FXML
    private TextFlow recommendationsTextFlow;
    private Huella printSelected;

    private List<Recomendacion> recommendationsRetrieved;
    private RecomendacionServices recommendationServices;


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
                    recommendationServices = new RecomendacionServices();
                    recommendationsRetrieved = recommendationServices.findRecommendationByPrint(printSelected);


                    recommendationsTextFlow.getChildren().clear();

                    if (recommendationsRetrieved.isEmpty()) {
                        Text noRec = new Text("❌ No hay recomendaciones disponibles para esta huella.\n");
                        noRec.setStyle("-fx-font-weight: bold; -fx-fill: red; -fx-font-size: 14px;");
                        recommendationsTextFlow.getChildren().add(noRec);
                    } else {

                        Text title = new Text("🔹 Recomendaciones para esta huella:\n\n");
                        title.setStyle("-fx-font-weight: bold; -fx-fill: #003366; -fx-font-size: 16px;");
                        recommendationsTextFlow.getChildren().add(title);

                        for (Recomendacion rec : recommendationsRetrieved) {

                            Text desc = new Text("✔️" + rec.getDescripcion() + ".\n");
                            desc.setStyle("-fx-font-size: 14px; -fx-fill: #1E3A8A; -fx-font-weight: bold;");


                            Text impact = new Text("🌱 Impacto estimado: " + rec.getImpactoEstimado() + " kg CO₂.\n");
                            impact.setStyle("-fx-font-size: 13px; -fx-fill: green; -fx-font-weight: bold;");


                            Text separator = new Text("\n━━━━━━━━━━━━━━\n");
                            separator.setStyle("-fx-font-size: 12px; -fx-fill: #444444; -fx-font-weight: bold;");


                            recommendationsTextFlow.getChildren().addAll(desc, impact, separator);
                        }
                    }
                }
            }
        });
    }


}
