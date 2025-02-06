package com.github.JoseAngelGiron.view;


import com.github.JoseAngelGiron.model.UserSession;
import com.github.JoseAngelGiron.model.entity.Usuario;
import com.github.JoseAngelGiron.model.services.HuellaServices;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;

public class ComparisonUsersController extends Controller implements Initializable {



    @FXML
    private BarChart<String, Number> barChartComparison;

    @FXML
    private CategoryAxis xAxis;

    @FXML
    private NumberAxis yAxis;

    private Usuario currentUser;
    private HuellaServices huellaService;

    @Override
    public void onOpen(Object input, Object input2) throws IOException {
    }

    @Override
    public void onClose(Object output) {
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        currentUser = UserSession.UserSession().getUserLoggedIn();
        compareUserWithAverage();
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

}

