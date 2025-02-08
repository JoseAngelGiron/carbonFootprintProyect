package com.github.JoseAngelGiron.view;

import com.github.JoseAngelGiron.model.UserSession;
import com.github.JoseAngelGiron.model.entity.Usuario;
import com.github.JoseAngelGiron.model.services.HuellaServices;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;

import java.time.format.DateTimeFormatter;

import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class CheckImpactFootPrintsController extends Controller implements Initializable {

    @FXML
    private DatePicker datePicker;

    @FXML
    private TextField textAreaImpact;

    private Usuario currentUser;
    private HuellaServices printServices;

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");


    @Override
    public void onOpen(Object input, Object input2) throws IOException {
        currentUser = UserSession.UserSession().getUserLoggedIn();

    }

    @Override
    public void onClose(Object output) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }





    private void updateImpactDisplay(String message, double impact) {
        textAreaImpact.setText(message);


        if (impact < 10) {
            textAreaImpact.setStyle("-fx-text-fill: green; -fx-font-weight: bold;");
        } else if (impact >= 10 && impact <= 50) {
            textAreaImpact.setStyle("-fx-text-fill: orange; -fx-font-weight: bold;");
        } else {
            textAreaImpact.setStyle("-fx-text-fill: red; -fx-font-weight: bold;");
        }
    }

    @FXML
    public void calculateImpactDaily() {
        LocalDate dateSelected = datePicker.getValue();
        printServices = new HuellaServices();

        if (dateSelected != null) {
            double impactRetrieved = printServices.findDailyImpact(currentUser, dateSelected);
            String message = String.format("Impacto diario (%s): %.2f kg CO₂",
                    dateSelected.format(DATE_FORMATTER), impactRetrieved);
            updateImpactDisplay(message, impactRetrieved);
        } else {
            updateImpactDisplay("⚠️ Por favor, selecciona una fecha.", 0);
        }
    }

    @FXML
    public void calculateImpactWeekly() {
        LocalDate dateSelected = datePicker.getValue();
        printServices = new HuellaServices();

        if (dateSelected != null) {
            LocalDate startOfWeek = dateSelected.with(java.time.DayOfWeek.MONDAY);
            LocalDate endOfWeek = startOfWeek.plusDays(6);

            double impactRetrieved = printServices.findImpactForAPeriod(currentUser, startOfWeek, endOfWeek);
            String message = String.format("Impacto semanal (%s - %s): %.2f kg CO₂",
                    startOfWeek.format(DATE_FORMATTER), endOfWeek.format(DATE_FORMATTER), impactRetrieved);
            updateImpactDisplay(message, impactRetrieved);
        } else {
            updateImpactDisplay("⚠️ Selecciona una fecha para calcular el impacto semanal.", 0);
        }
    }

    @FXML
    public void calculateImpactMonthly() {
        LocalDate dateSelected = datePicker.getValue();
        printServices = new HuellaServices();

        if (dateSelected != null) {
            LocalDate startOfMonth = dateSelected.withDayOfMonth(1);
            LocalDate endOfMonth = dateSelected.withDayOfMonth(dateSelected.lengthOfMonth());

            double impactRetrieved = printServices.findImpactForAPeriod(currentUser, startOfMonth, endOfMonth);
            String message = String.format("Impacto mensual (%s - %s): %.2f kg CO₂",
                    startOfMonth.format(DATE_FORMATTER), endOfMonth.format(DATE_FORMATTER), impactRetrieved);
            updateImpactDisplay(message, impactRetrieved);
        } else {
            updateImpactDisplay("⚠️ Selecciona una fecha para calcular el impacto mensual.", 0);
        }
    }

    @FXML
    public void calculateImpactThreeMonths() {
        LocalDate dateSelected = datePicker.getValue();
        printServices = new HuellaServices();

        if (dateSelected != null) {
            LocalDate startOfPeriod = dateSelected.minusMonths(3).withDayOfMonth(1);
            LocalDate endOfPeriod = dateSelected.withDayOfMonth(dateSelected.lengthOfMonth());

            double impactRetrieved = printServices.findImpactForAPeriod(currentUser, startOfPeriod, endOfPeriod);
            String message = String.format("Impacto en 3 meses (%s - %s): %.2f kg CO₂",
                    startOfPeriod.format(DATE_FORMATTER), endOfPeriod.format(DATE_FORMATTER), impactRetrieved);
            updateImpactDisplay(message, impactRetrieved);
        } else {
            updateImpactDisplay("⚠️ Selecciona una fecha para calcular el impacto trimestral.", 0);
        }
    }

    @FXML
    public void calculateImpactSixMonths() {
        LocalDate dateSelected = datePicker.getValue();
        printServices = new HuellaServices();

        if (dateSelected != null) {
            LocalDate startOfPeriod = dateSelected.minusMonths(6).withDayOfMonth(1);
            LocalDate endOfPeriod = dateSelected.withDayOfMonth(dateSelected.lengthOfMonth());

            double impactRetrieved = printServices.findImpactForAPeriod(currentUser, startOfPeriod, endOfPeriod);
            String message = String.format("Impacto en 6 meses (%s - %s): %.2f kg CO₂",
                    startOfPeriod.format(DATE_FORMATTER), endOfPeriod.format(DATE_FORMATTER), impactRetrieved);
            updateImpactDisplay(message, impactRetrieved);
        } else {
            updateImpactDisplay("⚠️ Selecciona una fecha para calcular el impacto semestral.", 0);
        }
    }

    @FXML
    public void calculateImpactInAYear() {
        LocalDate dateSelected = datePicker.getValue();
        printServices = new HuellaServices();

        if (dateSelected != null) {
            LocalDate startOfYear = dateSelected.withDayOfYear(1);
            LocalDate endOfYear = dateSelected.withDayOfYear(dateSelected.lengthOfYear());

            double impactRetrieved = printServices.findImpactForAPeriod(currentUser, startOfYear, endOfYear);
            String message = String.format("Impacto anual (%s - %s): %.2f kg CO₂",
                    startOfYear.format(DATE_FORMATTER), endOfYear.format(DATE_FORMATTER), impactRetrieved);
            updateImpactDisplay(message, impactRetrieved);
        } else {
            updateImpactDisplay("⚠️ Selecciona una fecha para calcular el impacto anual.", 0);
        }
    }



}
