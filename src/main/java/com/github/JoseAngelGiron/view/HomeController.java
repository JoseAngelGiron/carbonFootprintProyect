package com.github.JoseAngelGiron.view;


import com.github.JoseAngelGiron.App;
import com.github.JoseAngelGiron.model.UserSession;
import com.github.JoseAngelGiron.model.entity.Huella;
import com.github.JoseAngelGiron.model.entity.Usuario;
import com.github.JoseAngelGiron.model.services.HuellaServices;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import static com.github.JoseAngelGiron.App.changeScene;


public class HomeController extends Controller implements Initializable {

    @FXML
    private Pane window;

    @FXML
    private Pane mainPane;

    private Usuario currentUser;
    private HuellaServices printService;


    @Override
    public void onOpen(Object input, Object input2) throws IOException {

    }

    @Override
    public void onClose(Object output) {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        currentUser = UserSession.UserSession().getUserLoggedIn();
        try {
            changeHome();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void changeHome() throws IOException {
        changeScene(Scenes.MAINPAGE, mainPane,null);
    }

    @FXML
    public void changeProfile() throws IOException {
        changeScene(Scenes.PROFILE, mainPane,null);
    }


    @FXML
    public void changeToRegisterHabit() throws IOException {
        changeScene(Scenes.REGISTERNEWHABIT, mainPane,null);
    }

    @FXML
    public void changeToYourHabits() throws IOException {
        changeScene(Scenes.YOURHABITS, mainPane,null);
    }

    @FXML
    public void changeToUpdateHabit() throws IOException {
        changeScene(Scenes.UPDATEHABIT, mainPane,null);
    }

    @FXML
    public void changeToDeleteHabit() throws IOException {
        changeScene(Scenes.DELETEHABIT, mainPane,null);
    }


    @FXML
    public void changeToRegisterPrint() throws IOException {
        changeScene(Scenes.REGISTERNEWPRINT, mainPane,null);
    }

    @FXML
    public void changeToYourPrints() throws IOException {
        changeScene(Scenes.YOURPRINTS, mainPane,null);
    }

    @FXML
    public void changeToUpdatePrints() throws IOException {
        changeScene(Scenes.UPDATEPRINT, mainPane,null);
    }

    @FXML
    public void changeToDeletePrints() throws IOException {
        changeScene(Scenes.DELETEPRINT, mainPane,null);
    }

    @FXML
    public void changeToImpactFootprints() throws IOException {
        changeScene(Scenes.IMPACTFOOTPRINTS, mainPane,null);
    }

    @FXML
    public void changeToComparisonUsers() throws IOException {
        changeScene(Scenes.COMPARISONUSERS, mainPane,null);
    }

    @FXML
    public void changeToLogin() throws IOException {
        changeScene(Scenes.LOGIN, window,null);

        App.scene.getWindow().setWidth(610);
        App.scene.getWindow().setHeight(480);

        App.scene.getWindow().centerOnScreen();
    }

    @FXML
    public void printYourReport() throws IOException {

        List<String> choices = Arrays.asList("PDF", "CSV");
        ChoiceDialog<String> dialog = new ChoiceDialog<>("PDF", choices);
        dialog.setTitle("Seleccionar formato");
        dialog.setHeaderText("Elige un formato para el informe");
        dialog.setContentText("Formato:");

        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()) {
            String selectedFormat = result.get();


            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Guardar Informe");

            if (selectedFormat.equals("PDF")) {
                fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PDF Files", "*.pdf"));
            } else {
                fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV Files", "*.csv"));
            }

            File file = fileChooser.showSaveDialog(new Stage());
            if (file != null) {
                if (selectedFormat.equals("PDF")) {
                    generarPDF(file.getAbsolutePath());
                } else {
                    generarCSV(file.getAbsolutePath());
                }
            }
        }
    }

    private void generarPDF(String filePath) {
        printService = new HuellaServices();
        try {

            PdfWriter writer = new PdfWriter(filePath);
            PdfDocument pdfDoc = new PdfDocument(writer);
            Document document = new Document(pdfDoc);


            document.add(new Paragraph("Informe de Usuario")
                    .setBold().setFontSize(18));


            String nombreUsuario = currentUser.getNombre();
            String emailUsuario = currentUser.getEmail();
            document.add(new Paragraph("Nombre: " + nombreUsuario));
            document.add(new Paragraph("Email: " + emailUsuario));


            document.add(new Paragraph("\nDetalles de Huellas de Carbono:\n").setBold());

            Table table = new Table(3); // 3 columnas
            table.addCell("Fecha");
            table.addCell("Categoría");
            table.addCell("Impacto (kg CO₂)");

            List<Huella> huellas = printService.findAllprintsByUser(currentUser);
            for (Huella huella : huellas) {
                table.addCell(huella.getFecha().toString());
                table.addCell(huella.getIdActividad().getIdCategoria().getNombre());
                table.addCell(String.valueOf(huella.getValor()));
            }

            document.add(table);


            document.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void generarCSV(String filePath) {
        printService = new HuellaServices();
        try (FileWriter writer = new FileWriter(filePath)) {


            writer.append("Informe de Usuario\n");
            writer.append("Nombre;").append(currentUser.getNombre()).append("\n");
            writer.append("Email;").append(currentUser.getEmail()).append("\n\n");


            writer.append("Fecha;Categoría;Impacto (kg CO₂)\n");


            List<Huella> huellas = printService.findAllprintsByUser(currentUser);
            if (huellas.isEmpty()) {
                writer.append("No hay registros de huellas para este usuario.\n");
            } else {
                for (Huella huella : huellas) {
                    writer.append("\"").append(huella.getFecha().toString()).append("\"").append(";");
                    writer.append("\"").append(huella.getIdActividad().getIdCategoria().getNombre()).append("\"").append(";");
                    writer.append(String.valueOf(huella.getValor())).append("\n");
                }
            }

            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}



