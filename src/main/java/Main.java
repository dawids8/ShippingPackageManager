import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    private static final String LOGIN_WINDOW = "/fxml/Login.fxml";

    @Override
    public void start(Stage primaryStage) throws IOException {
        final Parent root = FXMLLoader.load(this.getClass().getResource(LOGIN_WINDOW));
        final Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Login to manage you packages");
        primaryStage.setResizable(false);
        primaryStage.show();
    }
}