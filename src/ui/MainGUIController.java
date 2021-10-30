package ui;

import javafx.fxml.FXML;
import javafx.scene.layout.Pane;
import model.FIBAManager;

public class MainGUIController {

    private FIBAManager manager;
    private EmergentGUIController EGC;

    @FXML
    private Pane mainPane;

    public MainGUIController(FIBAManager manager, EmergentGUIController EGC) {
        this.manager = manager;
        this.EGC = EGC;
    }

    public void showInitialScene() {

    }

}
