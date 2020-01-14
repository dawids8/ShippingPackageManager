package controllers;

import Utils.WindowUtils;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class WarningDialogController {

    @FXML
    private Button closeButton;
    @FXML
    private Label informationLabel;

    public void handleCloseButtonAction() {
        WindowUtils.handleCloseButtonAction(closeButton);
    }

    public void setInformationLabel(String information) {
        informationLabel.setText(information);
    }
}
