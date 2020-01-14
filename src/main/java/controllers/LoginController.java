package controllers;

import Utils.WindowUtils;
import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import repository.LoginRepository;

import java.io.IOException;
import java.net.URL;

public class LoginController {

    private static final String MAIN_WINDOW = "/fxml/MainWindow.fxml";
    private static final String NEW_USER_WINDOW = "/fxml/NewUser.fxml";
    private static final String LOGO_WINDOW = "/fxml/Logo.fxml";
    private final Stage stage = new Stage();

    @FXML
    private TextField user;
    @FXML
    private TextField password;
    @FXML
    private Button login;
    @FXML
    private Label loginLabel;

    public void login() throws Exception {
        boolean correctData = LoginRepository.checkIfUserDataIsCorrect(user.getText(), password.getText());
        if (correctData) {
            WindowUtils.handleCloseButtonAction(login);
            pauseAction();
        } else {
            loginLabel.setText("Login failed, try again.");
        }
    }

    public void goToSignUpWindow() throws Exception {
        WindowUtils.handleCloseButtonAction(login);
        newUserWindow();
    }

    private void newUserWindow() throws IOException {
        final URL url = this.getClass().getResource(NEW_USER_WINDOW);
        WindowUtils.changeWindow(stage, url, "Sign up");
    }

    private void mainWindow() throws IOException {
        final URL url = this.getClass().getResource(MAIN_WINDOW);
        WindowUtils.changeWindow(new Stage(StageStyle.DECORATED), url, "Manage your packages");
    }

    private void pauseAction() throws IOException {
        final URL url = this.getClass().getResource(LOGO_WINDOW);

        stage.initStyle(StageStyle.UNDECORATED);
        WindowUtils.changeWindow(stage, url, "");

        PauseTransition pause = new PauseTransition(Duration.seconds(1));
        pause.setOnFinished(event -> {
            stage.close();
            try {
                mainWindow();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        pause.play();
    }
}