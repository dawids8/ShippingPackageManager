package controllers;

import Utils.WindowUtils;
import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;
import repository.LoginRepository;

import java.io.IOException;
import java.net.URL;

public class NewUserController {

    private static final String LOGIN_WINDOW = "/fxml/Login.fxml";
    private final URL url = this.getClass().getResource(LOGIN_WINDOW);
    private final Stage stage = new Stage();

    @FXML
    private TextField setUserName, setPassword;
    @FXML
    private Button createNewUser, cancelCreatingNewUser;
    @FXML
    private Label isUserExist;

    public void createNewUser() {
        if (!(setUserName.getText().isEmpty() || setPassword.getText().isEmpty())) {

            if (LoginRepository.checkIfUserNameExist(setUserName.getText())) {
                LoginRepository.addNewUser(setUserName.getText(), setPassword.getText());
                setLabelText("New account has been created", "GREEN");
                pauseAction();
            } else {
                setLabelText("This user name already exist.", "#EA5757");
            }

        } else {
            setLabelText("Invalid entry", "#EA5757");
        }
    }

    private void setLabelText(String text, String color) {
        final PauseTransition pause = new PauseTransition(Duration.seconds(1.5));
        isUserExist.setText(text);
        isUserExist.setTextFill(Color.web(color));
        pause.setOnFinished(event -> isUserExist.setVisible(true));
        pause.play();
    }

    public void backToLoginWindow() {
        WindowUtils.handleCloseButtonAction(cancelCreatingNewUser);
        displayLoginWindow();
    }

    private void pauseAction() {
        final PauseTransition pause = new PauseTransition(Duration.seconds(1.5));
        pause.setOnFinished(event -> {
            WindowUtils.handleCloseButtonAction(createNewUser);
            displayLoginWindow();
        });

        pause.play();
    }

    public void displayLoginWindow() {
        try {
            WindowUtils.changeWindow(stage, url, "Login to manage you packages");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
