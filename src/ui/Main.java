package ui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.FIBAManager;

import java.io.IOException;

public class Main extends Application {

    private static final String FOLDER = "fxml/";

    private FIBAManager manager;
    private MainGUIController MGC;
    private EmergentGUIController EGC;

    public Main() throws IOException {
        manager = new FIBAManager();
        EGC = new EmergentGUIController(manager);
        MGC = new MainGUIController(manager, EGC);
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage window) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(FOLDER + "MainWindow.fxml"));
        fxmlLoader.setController(MGC);
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root, null);
        window.setTitle("");
        window.setScene(scene);
        window.setResizable(false);
        window.show();
    }

}
