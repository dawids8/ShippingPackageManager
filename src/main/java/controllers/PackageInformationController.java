package controllers;

import Utils.WindowUtils;
import delivery.Package;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class PackageInformationController {

    @FXML
    private Label shippingNumberLabel, customerNameLabel, addressLabel, contentLabel;
    @FXML
    private Button closeWindow;

    void setInformationOnLabel(Package consignment) {
        shippingNumberLabel.setText(consignment.getShippingNumber());
        customerNameLabel.setText(consignment.getCustomer());
        addressLabel.setText(consignment.getAddress());
        contentLabel.setText(consignment.getContent());
    }

    public void closeInformation() {
        WindowUtils.handleCloseButtonAction(closeWindow);
    }
}
