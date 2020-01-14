package controllers;

import Utils.WindowUtils;
import delivery.Companies;
import delivery.Package;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import repository.PackagesRepository;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class PackageWindowController implements Initializable {

    private static final String WARNING_WINDOW = "/fxml/WarningDialog.fxml";
    private ObservableList<Package> list;
    private Package editedPackage;

    @FXML
    private TextField shippingNumberTF, addressTF, customerTF, contentTF, weightTF, priceTF;
    @FXML
    private DatePicker shippingDateCalendar;
    @FXML
    private Button editButton, addButton;
    @FXML
    private ComboBox<String> deliveryCompaniesCB;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        deliveryCompaniesCB.setItems(FXCollections.observableArrayList(Companies.getCompaniesList()));
    }

    public void displayPackageValues() {
        shippingNumberTF.setText(editedPackage.getShippingNumber());
        shippingNumberTF.setDisable(true);
        deliveryCompaniesCB.setValue(editedPackage.getDeliveryCompany());
        addressTF.setText(editedPackage.getAddress());
        customerTF.setText(editedPackage.getCustomer());
        contentTF.setText(editedPackage.getContent());
        weightTF.setText(String.valueOf(editedPackage.getWeight()));
        priceTF.setText(String.valueOf(editedPackage.getPrice()));
        shippingDateCalendar.setValue(editedPackage.getShippingDate());
    }

    public void editPackageValues() {
        PackagesRepository.modifyPackage(getDeliveryPackage(), editedPackage.getShippingNumber());
        list.setAll(PackagesRepository.getAllPackages(false));

        WindowUtils.handleCloseButtonAction(editButton);
    }

    public void addNewShippedPackage() {
        boolean isShippingNumberExist = PackagesRepository.checkIfShippingNumberExist(getDeliveryPackage().getShippingNumber());
        if (!fieldsAreEmpty()) {
            if (isShippingNumberExist) {
                PackagesRepository.addNewPackage(getDeliveryPackage());
                list.add(getDeliveryPackage());

                WindowUtils.handleCloseButtonAction(addButton);
            } else {
                displayWarning("This shipping number already exist!");
            }

        } else {
            displayWarning("All information needed!");
        }
    }

    private boolean fieldsAreEmpty() {
        return shippingNumberTF.getText().isEmpty()
                || addressTF.getText().isEmpty()
                || customerTF.getText().isEmpty()
                || contentTF.getText().isEmpty()
                || weightTF.getText().isEmpty()
                || priceTF.getText().isEmpty()
                || deliveryCompaniesCB.getSelectionModel().isEmpty()
                || shippingDateCalendar.getValue() == null;
    }

    private void displayWarning(String information) {
        try {
            final URL url = this.getClass().getResource(WARNING_WINDOW);
            WarningDialogController warningDialogController;
            warningDialogController = WindowUtils.loadFXML(new Stage(), url, "Warning!").getController();
            warningDialogController.setInformationLabel(information);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private Package getDeliveryPackage() {
        return new Package(shippingNumberTF.getText(),
                deliveryCompaniesCB.getSelectionModel().getSelectedItem(),
                addressTF.getText(),
                customerTF.getText(),
                contentTF.getText(),
                Double.parseDouble(weightTF.getText()),
                Double.parseDouble(priceTF.getText()),
                shippingDateCalendar.getValue(),
                false);
    }

    public void setList(ObservableList<Package> list) {
        this.list = list;
    }

    public void setEditedPackage(Package editedPackage) {
        this.editedPackage = editedPackage;
    }
}
