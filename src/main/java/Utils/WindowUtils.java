package Utils;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class WindowUtils {

    public static void changeWindow(Stage stage, URL url, String title) throws IOException {
        final Parent root = FXMLLoader.load(url);
        stage.setTitle(title);
        stage.setScene(new Scene(root));
        stage.setResizable(false);
        stage.show();
    }


    public static FXMLLoader loadFXML(Stage stage, URL url, String title) throws IOException {
        final FXMLLoader fxmlLoader = new FXMLLoader(url);
        final Parent root = fxmlLoader.load();
        stage.setTitle(title);
        stage.setScene(new Scene(root));
        stage.setResizable(false);
        stage.show();

        return fxmlLoader;
    }

    public static void handleCloseButtonAction(Button button) {
        final Stage stage = (Stage) button.getScene().getWindow();
        stage.close();
    }
}
