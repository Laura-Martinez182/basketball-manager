package ui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import model.FIBAManager;
import java.io.IOException;

public class MainGUIController {

    private FIBAManager manager;
    private EmergentGUIController EGC;

    private final String FOLDER = "fxml/";

    @FXML
    private BorderPane mainPane;

    //player
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

    public MainGUIController(FIBAManager manager, EmergentGUIController EGC) {
        this.manager = manager;
        this.EGC = EGC;
    }

    public void showInitialScene() {

    }

    public void showRegisterPlayerScene() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(FOLDER + "RegisterPlayerWindow.fxml"));
        fxmlLoader.setController(this);
        Parent root = fxmlLoader.load();
        mainPane.setCenter(root);
        Stage stage = (Stage) mainPane.getScene().getWindow();
        stage.setTitle("");
        stage.setHeight(425.0);
        stage.setWidth(300.0);
        stage.setResizable(false);
    }

    @FXML
    public void registerPlayer(ActionEvent event) {
        String name = nameTxt.getText().trim();
        String team = teamTxt.getText().trim();
        if(name == null || team == null || name.isEmpty() || team.isEmpty()) {
            showInformationAlert(null, "Llene todos los campos para poder continuar", null);
            return;
        }
        try {
            int age = Integer.parseInt(ageTxt.getText().trim());
            double points = Double.parseDouble(pointsTxt.getText().trim());
            double rebounds = Double.parseDouble(reboundsTxt.getText().trim());
            double assists = Double.parseDouble(assistsTxt.getText().trim());
            double steals = Double.parseDouble(stealsTxt.getText().trim());
            double blocks = Double.parseDouble(blocksTxt.getText().trim());
            if(manager.addPlayer(name, age, team, points, rebounds, assists, steals, blocks)) {
                showInformationAlert(null, "Se ha registrado al jugado exitosamente", null);
                nameTxt.clear();
                ageTxt.clear();
                teamTxt.clear();
                pointsTxt.clear();
                reboundsTxt.clear();
                assistsTxt.clear();
                stealsTxt.clear();
                blocksTxt.clear();
            } else
                showInformationAlert(null, "Ocurrio un error durante el regitrso, no se pudo registrar al jugador" , null);
        } catch(NumberFormatException e) {
            showInformationAlert(null, "No se pudo registrar al jugador porque hay campos númericos que contienen caracteres o están vacíos", null);
        }
    }

    private void showInformationAlert(String title,String msg,String header){
        Alert feedBack = new Alert(Alert.AlertType.INFORMATION);
        feedBack.setTitle(title);
        feedBack.setHeaderText(header);
        feedBack.setContentText(msg);
        feedBack.showAndWait();
    }

}
