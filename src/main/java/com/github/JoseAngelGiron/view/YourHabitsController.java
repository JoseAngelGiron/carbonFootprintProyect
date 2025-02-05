package com.github.JoseAngelGiron.view;

import com.github.JoseAngelGiron.model.UserSession;

import com.github.JoseAngelGiron.model.entity.Habito;
import com.github.JoseAngelGiron.model.entity.Recomendacion;
import com.github.JoseAngelGiron.model.entity.Usuario;
import com.github.JoseAngelGiron.model.services.HabitoServices;
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
import java.net.URL;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ResourceBundle;

public class YourHabitsController extends Controller implements Initializable {

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
    private TextFlow textFlowRecommendations;


    private List<Habito> habits;
    private ObservableList<Habito> observableHabitsList;
    private Usuario currentUser;
    private RecomendacionServices recommendationServices;
    private List<Recomendacion> recommendationsRetrieved;

    @Override
    public void onOpen(Object input, Object input2) throws IOException {
        currentUser = UserSession.UserSession().getUserLoggedIn();
        showUserHabits();
    }

    @Override
    public void onClose(Object output) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


    }

    @FXML
    public void dailyRecomendation() {
        recommendationServices = new RecomendacionServices();
        recommendationsRetrieved = recommendationServices.findRecommendationsDailyByUser(currentUser, "Diaria");

        textFlowRecommendations.getChildren().clear();

        // Verificar si la lista está vacía
        if (recommendationsRetrieved.isEmpty()) {
            Text noRecommendations = new Text("⚠️ No hay hábitos asociados a recomendaciones diarias.\n");
            noRecommendations.setStyle("-fx-font-weight: bold; -fx-fill: #FF6347; -fx-font-size: 14px;");
            textFlowRecommendations.getChildren().add(noRecommendations);
        } else {
            Text title = new Text("🔹 En función del hábito con mayor frecuencia (Diario) las recomendaciones son:\n\n");
            title.setStyle("-fx-font-weight: bold; -fx-fill: #1E90FF; -fx-font-size: 16px;");
            textFlowRecommendations.getChildren().add(title);

            for (Recomendacion rec : recommendationsRetrieved) {
                Text desc = new Text("✔️ " + rec.getDescripcion() + ".\n");
                desc.setStyle("-fx-font-size: 14px; -fx-fill: #2E8B57; -fx-font-weight: bold;");

                Text impact = new Text("🌱 Impacto estimado: " + rec.getImpactoEstimado() + " kg CO₂.\n");
                impact.setStyle("-fx-font-size: 13px; -fx-fill: #32CD32; -fx-font-weight: bold;");

                Text separator = new Text("\n━━━━━━━━━━━━━━━━\n");
                separator.setStyle("-fx-font-size: 12px; -fx-fill: #444444; -fx-font-weight: bold;");

                textFlowRecommendations.getChildren().addAll(desc, impact, separator);
            }
        }
    }

    @FXML
    public void weekRecomendation() {
        recommendationServices = new RecomendacionServices();
        recommendationsRetrieved = recommendationServices.findRecommendationsDailyByUser(currentUser, "Semanal");

        textFlowRecommendations.getChildren().clear();

        // Verificar si la lista está vacía
        if (recommendationsRetrieved.isEmpty()) {
            Text noRecommendations = new Text("⚠️ No hay hábitos asociados a recomendaciones semanales.\n");
            noRecommendations.setStyle("-fx-font-weight: bold; -fx-fill: #FF6347; -fx-font-size: 14px;");
            textFlowRecommendations.getChildren().add(noRecommendations);
        } else {
            Text title = new Text("🔸 En función del hábito con mayor frecuencia (Semanal) las recomendaciones son:\n\n");
            title.setStyle("-fx-font-weight: bold; -fx-fill: #FF6347; -fx-font-size: 16px;");
            textFlowRecommendations.getChildren().add(title);

            for (Recomendacion rec : recommendationsRetrieved) {
                Text desc = new Text("✔️ " + rec.getDescripcion() + ".\n");
                desc.setStyle("-fx-font-size: 14px; -fx-fill: #B22222; -fx-font-weight: bold;");

                Text impact = new Text("🌱 Impacto estimado: " + rec.getImpactoEstimado() + " kg CO₂.\n");
                impact.setStyle("-fx-font-size: 13px; -fx-fill: #228B22; -fx-font-weight: bold;");

                Text separator = new Text("\n━━━━━━━━━━━━━━━━\n");
                separator.setStyle("-fx-font-size: 12px; -fx-fill: #444444; -fx-font-weight: bold;");

                textFlowRecommendations.getChildren().addAll(desc, impact, separator);
            }
        }
    }

    @FXML
    public void monthRecomendation() {
        recommendationServices = new RecomendacionServices();
        recommendationsRetrieved = recommendationServices.findRecommendationsDailyByUser(currentUser, "Mensual");

        textFlowRecommendations.getChildren().clear();

        // Verificar si la lista está vacía
        if (recommendationsRetrieved.isEmpty()) {
            Text noRecommendations = new Text("⚠️ No hay hábitos asociados a recomendaciones mensuales.\n");
            noRecommendations.setStyle("-fx-font-weight: bold; -fx-fill: #FF6347; -fx-font-size: 14px;");
            textFlowRecommendations.getChildren().add(noRecommendations);
        } else {
            Text title = new Text("🔶 En función del hábito con mayor frecuencia (Mensual) las recomendaciones son:\n\n");
            title.setStyle("-fx-font-weight: bold; -fx-fill: #FFA500; -fx-font-size: 16px;");
            textFlowRecommendations.getChildren().add(title);

            for (Recomendacion rec : recommendationsRetrieved) {
                Text desc = new Text("✔️ " + rec.getDescripcion() + ".\n");
                desc.setStyle("-fx-font-size: 14px; -fx-fill: #FF4500; -fx-font-weight: bold;");

                Text impact = new Text("🌱 Impacto estimado: " + rec.getImpactoEstimado() + " kg CO₂.\n");
                impact.setStyle("-fx-font-size: 13px; -fx-fill: #FFD700; -fx-font-weight: bold;");

                Text separator = new Text("\n━━━━━━━━━━━━━━━━\n");
                separator.setStyle("-fx-font-size: 12px; -fx-fill: #444444; -fx-font-weight: bold;");

                textFlowRecommendations.getChildren().addAll(desc, impact, separator);
            }
        }
    }

    @FXML
    public void yearRecomendation() {
        recommendationServices = new RecomendacionServices();
        recommendationsRetrieved = recommendationServices.findRecommendationsDailyByUser(currentUser, "Anual");

        textFlowRecommendations.getChildren().clear();


        if (recommendationsRetrieved.isEmpty()) {
            Text noRecommendations = new Text("⚠️ No hay hábitos asociados a recomendaciones anuales.\n");
            noRecommendations.setStyle("-fx-font-weight: bold; -fx-fill: #FF6347; -fx-font-size: 14px;");
            textFlowRecommendations.getChildren().add(noRecommendations);
        } else {
            Text title = new Text("🔹 En función del hábito con mayor frecuencia (Anual) las recomendaciones son:\n\n");
            title.setStyle("-fx-font-weight: bold; -fx-fill: #8A2BE2; -fx-font-size: 16px;");
            textFlowRecommendations.getChildren().add(title);

            for (Recomendacion rec : recommendationsRetrieved) {
                Text desc = new Text("✔️ " + rec.getDescripcion() + ".\n");
                desc.setStyle("-fx-font-size: 14px; -fx-fill: #6A5ACD; -fx-font-weight: bold;");

                Text impact = new Text("🌱 Impacto estimado: " + rec.getImpactoEstimado() + " kg CO₂.\n");
                impact.setStyle("-fx-font-size: 13px; -fx-fill: #8A2BE2; -fx-font-weight: bold;");

                Text separator = new Text("\n━━━━━━━━━━━━━━━━\n");
                separator.setStyle("-fx-font-size: 12px; -fx-fill: #444444; -fx-font-weight: bold;");

                textFlowRecommendations.getChildren().addAll(desc, impact, separator);
            }
        }
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
