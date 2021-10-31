package ui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
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
        System.out.println("PRESSED!!!");
    }

}
