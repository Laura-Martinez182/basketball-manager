package ui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Criteria;
import model.FIBAManager;
import model.Player;
import java.io.IOException;
import java.util.List;

public class MainGUIController {

    private FIBAManager manager;
    private EmergentGUIController EGC;

    private final String FOLDER = "fxml/";

    @FXML
    private BorderPane mainPane;

    @FXML
    private Button registerBtn;
    @FXML
    private Button searchBtn;
    @FXML
    private VBox vbox;

    //Register Player
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
    //View Player
    @FXML
    private ToggleGroup searchOpt;
    @FXML
    private RadioButton nameRB;
    @FXML
    private RadioButton inequalityRB;
    @FXML
    private RadioButton equalRB;
    @FXML
    private TextField nameSearchTxt;
    @FXML
    private ChoiceBox<Criteria> criteriaCB;
    @FXML
    private TextField minTxt;
    @FXML
    private TextField maxTxt;
    @FXML
    private TextField equalTxt;
    @FXML
    private ListView<Player> resultsList;
    @FXML
    private MenuItem visualize;
    @FXML
    private MenuItem edit;
    @FXML
    private MenuItem delete;

    public MainGUIController(FIBAManager manager, EmergentGUIController EGC) {
        this.manager = manager;
        this.EGC = EGC;
    }

    @FXML
    public void showImportPlayersScene(ActionEvent event) throws IOException {
        EGC.showImportPlayersDataScene();
    }

    @FXML
    public void showRegisterPlayerScene(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(FOLDER + "RegisterPlayerWindow.fxml"));
        fxmlLoader.setController(this);
        Parent root = fxmlLoader.load();
        mainPane.setCenter(root);
        Stage stage = (Stage) mainPane.getScene().getWindow();
        stage.setTitle("");
        vbox.setPrefWidth(130.0);
        stage.setHeight(430.0);
        stage.setWidth(425.0);
        stage.setResizable(false);
        registerBtn.setDisable(true);
        searchBtn.setDisable(false);
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

    @FXML
    public void showViewPlayersScene(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(FOLDER + "SearchPlayerWindow.fxml"));
        fxmlLoader.setController(this);
        Parent root = fxmlLoader.load();
        mainPane.setCenter(root);
        Stage stage = (Stage) mainPane.getScene().getWindow();
        stage.setTitle("");
        vbox.setPrefWidth(130.0);
        stage.setHeight(540.0);
        stage.setWidth(600.0);
        stage.setResizable(false);
        registerBtn.setDisable(false);
        searchBtn.setDisable(true);
        criteriaCB.getItems().addAll(Criteria.values());
        criteriaCB.setDisable(true);
        nameSearchTxt.setDisable(true);
        minTxt.setDisable(true);
        maxTxt.setDisable(true);
        equalTxt.setDisable(true);
    }

    @FXML
    public void checkChosenOption(ActionEvent event) {
        RadioButton rb = (RadioButton) searchOpt.getSelectedToggle();
        if(rb == nameRB) {
            criteriaCB.setDisable(true);
            minTxt.clear();
            minTxt.setDisable(true);
            maxTxt.clear();
            maxTxt.setDisable(true);
            equalTxt.clear();
            equalTxt.setDisable(true);
            nameSearchTxt.setDisable(false);
        } else if(rb == inequalityRB) {
            criteriaCB.setDisable(false);
            nameSearchTxt.clear();
            nameSearchTxt.setDisable(true);
            equalTxt.clear();
            equalTxt.setDisable(true);
            minTxt.setDisable(false);
            maxTxt.setDisable(false);
        } else {
            criteriaCB.setDisable(false);
            nameSearchTxt.clear();
            nameSearchTxt.setDisable(true);
            minTxt.clear();
            minTxt.setDisable(true);
            maxTxt.clear();
            maxTxt.setDisable(true);
            equalTxt.setDisable(false);
        }
    }

    @FXML
    public void searchPlayers(ActionEvent event) {
        RadioButton option = (RadioButton) searchOpt.getSelectedToggle();
        List<Player> playersList;
        resultsList.getItems().clear();
        if(option != null) {
            if(option == nameRB) {
                String name = nameSearchTxt.getText();
                if(!name.isEmpty()) {
                    playersList = manager.searchPlayersByName(name);
                    setResultsListElements(playersList);
                    nameSearchTxt.clear();
                    showInformationAlert(null, "Se han encontrado " + playersList.size() + " resultados\n" +
                            "Tiempo de búsqueda: " + manager.getSearchTime() + " ms" , null);
                } else
                    showInformationAlert(null, "Debe llenar el campo", null);
            } else {
                Criteria criteria = criteriaCB.getValue();
                if(criteria == null) {
                    showInformationAlert(null, "Debe seleccionar un criterio de búsqueda", null);
                    return;
                }
                if (option == inequalityRB) {
                    try {
                        double min = Double.parseDouble(minTxt.getText());
                        double max = Double.parseDouble(maxTxt.getText());
                        if (min > max || min < 0.0 || max < 0.0) {
                            showInformationAlert(null, "Revise los valores de las casillas", null);
                        } else {
                            playersList = manager.searchPlayersInRange(criteria, min, max);
                            setResultsListElements(playersList);
                            minTxt.clear();
                            maxTxt.clear();
                            showInformationAlert(null, "Se han encontrado " + playersList.size() + " resultados\n" +
                                    "Tiempo de búsqueda: " + manager.getSearchTime() + " ms" , null);
                        }
                    } catch (NumberFormatException e) {
                        showInformationAlert(null, "Los valores deben ser números", null);
                    }
                } else {
                    try {
                        double value = Double.parseDouble(equalTxt.getText());
                        if (value > 0) {
                            playersList = manager.searchPlayersByValue(criteria, value);
                            setResultsListElements(playersList);
                            equalTxt.clear();
                            showInformationAlert(null, "Se han encontrado " + playersList.size() + " resultados\n" +
                                    "Tiempo de búsqueda: " + manager.getSearchTime() + " ms" , null);
                        }
                    } catch (NumberFormatException e) {
                        showInformationAlert(null, "El valor debe ser un número", null);
                    }
                }
            }
        } else {
            showInformationAlert(null, "Debe seleccionarse una opción", null);
        }
    }

    private void setResultsListElements(List<Player> players) {
        ObservableList<Player> list = FXCollections.observableList(players);
        resultsList.setItems(list);
    }

    @FXML
    public void listenMouseEvent(MouseEvent me) {
        visualize.setDisable(false);
        edit.setDisable(false);
        delete.setDisable(false);
        if(me.getButton() == MouseButton.SECONDARY) {
            Player selection = resultsList.getSelectionModel().getSelectedItem();
            if(selection == null) {
                visualize.setDisable(true);
                edit.setDisable(true);
                delete.setDisable(true);
            }
        }
    }

    @FXML
    public void listenVisualizePlayer(ActionEvent event) throws IOException {
        Player player = resultsList.getSelectionModel().getSelectedItem();
        if(player != null) {
            EGC.showPlayerInformationWindow(false, player);
        }
    }

    @FXML
    public void listenEditPlayer(ActionEvent event) throws IOException {
        Player player = resultsList.getSelectionModel().getSelectedItem();
        if(player != null) {
            EGC.showPlayerInformationWindow(true, player);
        }
    }

    @FXML
    public void listenDeletePlayer(ActionEvent event) {
        Player player = resultsList.getSelectionModel().getSelectedItem();
        if(player != null) {

        }
    }

    private void showInformationAlert(String title,String msg,String header){
        Alert feedBack = new Alert(Alert.AlertType.INFORMATION);
        feedBack.setTitle(title);
        feedBack.setHeaderText(header);
        feedBack.setContentText(msg);
        DialogPane dp = feedBack.getDialogPane();
        dp.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
        dp.getStyleClass().add("application");
        feedBack.showAndWait();
    }

}
