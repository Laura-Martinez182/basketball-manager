package ui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.FIBAManager;

import java.io.File;
import java.io.IOException;

public class EmergentGUIController {

    private FIBAManager manager;

    private final String FOLDER = "fxml/";

    @FXML
    private TextField filePathTxt;
    @FXML
    private TextField separatorTxt;
    @FXML
    private ToggleGroup headerOpt;

    public EmergentGUIController(FIBAManager manager) {
        this.manager = manager;
    }

    public void showImportPlayersDataScene() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(FOLDER + "importPlayersData.fxml"));
        fxmlLoader.setController(this);
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root, null);
        Stage form = new Stage();
        form.initModality(Modality.APPLICATION_MODAL);
        form.setScene(scene);
        form.setTitle("");
        form.setHeight(285.0);
        form.setWidth(440.0);
        form.setResizable(false);
        form.showAndWait();
    }

    @FXML
    public void openFileChooser(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Seleccione la ruta del archivo");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV", "*.csv"));
        File file = fileChooser.showOpenDialog(null);
        if(file != null)
            filePathTxt.setText(file.getAbsolutePath());
    }

    @FXML
    public void importPlayersData(ActionEvent event) {
        String path = filePathTxt.getText();
        String separator = separatorTxt.getText();
        RadioButton selected = (RadioButton) headerOpt.getSelectedToggle();
        boolean header = selected.getText().equals("SI");
        if(!path.isEmpty() && !separator.isEmpty()) {
            if(manager.addPlayer(header, path, separator)) {
                showInformationAlert(null, "Se han importado los datos correctamente", null);
                closeEmergentWindow(event);
            } else
                showInformationAlert(null, "Ha ocurrido un error al momento de importar", null);
        } else {
            showInformationAlert(null, "Deben llenarse todos los campos", null);
        }
    }

    private void showInformationAlert(String title,String msg,String header){
        Alert feedBack = new Alert(Alert.AlertType.INFORMATION);
        feedBack.setTitle(title);
        feedBack.setHeaderText(header);
        feedBack.setContentText(msg);
        feedBack.showAndWait();
    }

    private void closeEmergentWindow(ActionEvent event) {
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }

}
