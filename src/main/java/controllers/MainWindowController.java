package controllers;

import utils.WindowUtils;
import delivery.Package;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import repository.PackagesRepository;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.util.ResourceBundle;

public class MainWindowController implements Initializable {

    private final static String NEW_PACKAGE_WINDOW = "/fxml/NewPackage.fxml";
    private final static String PACKAGE_EDITOR_WINDOW = "/fxml/PackageEditor.fxml";
    private final static String INFORMATION_WINDOW = "/fxml/PackageInformation.fxml";
    private ObservableList<Package> listOfPackagesShipped;
    private ObservableList<Package> listOfPackagesDelivered;

    @FXML
    private TableView<Package> sentTableView, deliveredTableView;
    @FXML
    private TableColumn<Package, String> shippingNumberColumnShipped, shippingNumberColumnDelivered,
            companyColumnShipped, companyColumnDelivered,
            addressColumnShipped, addressColumnDelivered,
            customerColumnShipped, customerColumnDelivered,
            contentColumnShipped, contentColumnDelivered;
    @FXML
    private TableColumn<Package, Double> weightColumnShipped, weightColumnDelivered,
            priceColumnShipped, priceColumnDelivered;
    @FXML
    private TableColumn<Package, Date> dateColumnShipped, dateColumnDelivered;
    @FXML
    private MenuItem editMenuItem, setAsDeliveredMenuItem, deleteMenuItem,
            deleteDeliveredMenuItem, setNotDeliveredMenuItem;
    @FXML
    private TextField fieldSearch;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        buildTable();
        initializeTable();
    }

    private void buildTable() {
        shippingNumberColumnShipped.setCellValueFactory(new PropertyValueFactory<>("shippingNumber"));
        shippingNumberColumnDelivered.setCellValueFactory(new PropertyValueFactory<>("shippingNumber"));

        companyColumnShipped.setCellValueFactory(new PropertyValueFactory<>("deliveryCompany"));
        companyColumnDelivered.setCellValueFactory(new PropertyValueFactory<>("deliveryCompany"));

        addressColumnShipped.setCellValueFactory(new PropertyValueFactory<>("address"));
        addressColumnDelivered.setCellValueFactory(new PropertyValueFactory<>("address"));

        customerColumnShipped.setCellValueFactory(new PropertyValueFactory<>("customer"));
        customerColumnDelivered.setCellValueFactory(new PropertyValueFactory<>("customer"));

        contentColumnShipped.setCellValueFactory(new PropertyValueFactory<>("content"));
        contentColumnDelivered.setCellValueFactory(new PropertyValueFactory<>("content"));

        weightColumnShipped.setCellValueFactory(new PropertyValueFactory<>("weight"));
        weightColumnDelivered.setCellValueFactory(new PropertyValueFactory<>("weight"));

        priceColumnShipped.setCellValueFactory(new PropertyValueFactory<>("price"));
        priceColumnDelivered.setCellValueFactory(new PropertyValueFactory<>("price"));

        dateColumnShipped.setCellValueFactory(new PropertyValueFactory<>("shippingDate"));
        dateColumnDelivered.setCellValueFactory(new PropertyValueFactory<>("shippingDate"));
    }

    private void initializeTable() {
        listOfPackagesShipped = FXCollections.observableArrayList(PackagesRepository.getAllPackages(false));
        listOfPackagesDelivered = FXCollections.observableArrayList(PackagesRepository.getAllPackages(true));
        sentTableView.setItems(listOfPackagesShipped);
        deliveredTableView.setItems(listOfPackagesDelivered);
    }

    public void goToNewPackageWindow() throws IOException {
        final URL url = this.getClass().getResource(NEW_PACKAGE_WINDOW);
        final PackageWindowController packageWindowController;
        packageWindowController = WindowUtils.loadFXML(new Stage(), url, "Add new package").getController();

        packageWindowController.setList(sentTableView.getItems());
    }

    public void editPackage() throws IOException {
        final URL url = this.getClass().getResource(PACKAGE_EDITOR_WINDOW);
        final Package selectedPackage = sentTableView.getSelectionModel().getSelectedItem();
        final PackageWindowController packageWindowController;
        packageWindowController = WindowUtils.loadFXML(new Stage(), url, "Modify package").getController();

        packageWindowController.setEditedPackage(selectedPackage);
        packageWindowController.displayPackageValues();
        packageWindowController.setList(sentTableView.getItems());
    }


    public void showSelectedPackageInformation(MouseEvent mouseEvent) {
        if (mouseEvent.getClickCount() == 2) {
            if (sentTableView.isFocused()) {
                Package selectedPackage = sentTableView.getSelectionModel().getSelectedItem();
                displayInformation(selectedPackage);
            } else if (deliveredTableView.isFocused()) {
                Package selectedPackage = deliveredTableView.getSelectionModel().getSelectedItem();
                displayInformation(selectedPackage);
            }
        }
    }

    private void displayInformation(Package selectedPackage) {
        try {
            final URL url = this.getClass().getResource(INFORMATION_WINDOW);
            final PackageInformationController packageInformationController;
            packageInformationController = WindowUtils.loadFXML(new Stage(), url, "Package Information").getController();

            packageInformationController.setInformationOnLabel(selectedPackage);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void deletePackage() {
        if (sentTableView.isFocused()) {
            removeFromData(sentTableView);
        } else if (deliveredTableView.isFocused()) {
            removeFromData(deliveredTableView);
        }
    }

    private void removeFromData(TableView<Package> tableView) {
        final Package selectedPackage = tableView.getSelectionModel().getSelectedItem();
        tableView.getItems().remove(selectedPackage);
        PackagesRepository.deletePackage(selectedPackage.getShippingNumber());
    }

    public void setPackageAsDelivered() {
        final Package selectedPackage = sentTableView.getSelectionModel().getSelectedItem();
        PackagesRepository.setPackageStatus(selectedPackage.getShippingNumber(), true);

        listOfPackagesShipped.remove(selectedPackage);
        listOfPackagesDelivered.add(selectedPackage);
    }

    public void setPackageAsNotDelivered() {
        final Package selectedPackage = deliveredTableView.getSelectionModel().getSelectedItem();
        PackagesRepository.setPackageStatus(selectedPackage.getShippingNumber(), false);

        listOfPackagesDelivered.remove(selectedPackage);
        listOfPackagesShipped.add(selectedPackage);
    }

    public void getPackageFromList() {
        if (sentTableView.isVisible()) {
            searchForPackage(listOfPackagesShipped, sentTableView);
        } else if (deliveredTableView.isVisible()) {
            searchForPackage(listOfPackagesDelivered, deliveredTableView);
        }
    }

    private void searchForPackage(ObservableList<Package> list, TableView<Package> tableView) {
        final FilteredList<Package> filteredList = new FilteredList<>(list, p -> true);

        fieldSearch.textProperty().addListener((observable, oldValue, newValue) -> filteredList.setPredicate(pack -> {
            if (newValue == null || newValue.isEmpty()) {
                return true;
            }

            final String lowerCaseFilter = newValue.toLowerCase();
            if (pack.getShippingNumber().toLowerCase().contains(lowerCaseFilter)) {
                return true;
            } else {
                return pack.getCustomer().toLowerCase().contains(lowerCaseFilter);
            }

        }));

        SortedList<Package> sortedList = new SortedList<>(filteredList);
        sortedList.comparatorProperty().bind(tableView.comparatorProperty());
        tableView.setItems(sortedList);
    }
}