package ui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.FIBAManager;
import model.Player;

import java.io.File;
import java.io.IOException;

public class EmergentGUIController {

    private FIBAManager manager;

    private final String FOLDER = "fxml/";
    //Import
    @FXML
    private TextField filePathTxt;
    @FXML
    private TextField separatorTxt;
    @FXML
    private ToggleGroup headerOpt;
    //Player Info
    @FXML
    private Button editBtn;
    @FXML
    private TextField nameTxt;
    @FXML
    private TextField ageTxt;
    @FXML
    private TextField teamTxt;
    @FXML
    private TextField pointsTxt;
    @FXML
    private TextField reboundsTxt;
    @FXML
    private TextField assistsTxt;
    @FXML
    private TextField stealsTxt;
    @FXML
    private TextField blocksTxt;
    private Player current;

    public EmergentGUIController(FIBAManager manager) {
        this.manager = manager;
        current = null;
    }

    public void showImportPlayersDataScene() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(FOLDER + "importPlayersData.fxml"));
        fxmlLoader.setController(this);
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root, null);
        scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
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

    public void showPlayerInformationWindow(boolean edit, Player player) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(FOLDER + "VisualizePlayerWindow.fxml"));
        fxmlLoader.setController(this);
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root, null);
        scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
        Stage form = new Stage();
        form.initModality(Modality.APPLICATION_MODAL);
        form.setScene(scene);
        form.setTitle("");
        form.setHeight(450.0);
        form.setWidth(295.0);
        form.setResizable(false);
        current = player;
        setTextFieldsData(edit);
        form.showAndWait();
    }

    private void setTextFieldsData(boolean edit) {
        nameTxt.setText(current.getName());
        nameTxt.setEditable(edit);
        ageTxt.setText(current.getAge() + "");
        ageTxt.setEditable(edit);
        teamTxt.setText(current.getTeam());
        teamTxt.setEditable(edit);
        pointsTxt.setText(current.getPoints() + "");
        pointsTxt.setEditable(edit);
        reboundsTxt.setText(current.getRebounds() + "");
        reboundsTxt.setEditable(edit);
        assistsTxt.setText(current.getAssists() + "");
        assistsTxt.setEditable(edit);
        stealsTxt.setText(current.getSteals() + "");
        stealsTxt.setEditable(edit);
        blocksTxt.setText(current.getBlocks() + "");
        blocksTxt.setEditable(edit);
        editBtn.setDisable(!edit);
    }

    @FXML
    public void changePlayerData(ActionEvent event) {
        String name = nameTxt.getText();
        String team = teamTxt.getText();
        try {
            int age = Integer.parseInt(ageTxt.getText());
            double points = Double.parseDouble(pointsTxt.getText());
            double rebounds = Double.parseDouble(reboundsTxt.getText());
            double assists = Double.parseDouble(assistsTxt.getText());
            double steals = Double.parseDouble(stealsTxt.getText());
            double blocks = Double.parseDouble(blocksTxt.getText());
            if(!name.isEmpty() && !team.isEmpty() && age > 0 && points > 0 && rebounds > 0 && assists > 0 && steals > 0 && blocks > 0) {
                if(manager.changePlayer(current, name, age, team, points, rebounds, assists, steals, blocks))
                    showInformationAlert(null, "Se realizo el cambio exitosamente", null);
                else
                    showInformationAlert(null, "Ocurrio un error, no se pudo realizar el cambio", null);
            } else {
                showInformationAlert(null, "No se pudo hacer el cambio, revise los campos", null);
            }
        } catch(NumberFormatException e) {
            showInformationAlert(null, "Los campos numericos no pueden contener caracteres o estar vacíos", null);
        }
        current = null;
        closeEmergentWindow(event);
    }

    private void showInformationAlert(String title, String msg, String header){
        Alert feedBack = new Alert(Alert.AlertType.INFORMATION);
        feedBack.setTitle(title);
        feedBack.setHeaderText(header);
        feedBack.setContentText(msg);
        DialogPane dp = feedBack.getDialogPane();
        dp.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
        dp.getStyleClass().add("application");
        feedBack.showAndWait();
    }

    private void closeEmergentWindow(ActionEvent event) {
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }

}
