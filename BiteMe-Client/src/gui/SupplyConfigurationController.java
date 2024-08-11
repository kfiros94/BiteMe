package gui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.layout.VBox;
import javafx.geometry.Pos;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;

import java.io.IOException;
import java.time.LocalDate;
import gui.SelectFromRestMenuController.CartItem;

public class SupplyConfigurationController {

    @FXML private DatePicker supplyDatePicker;
    @FXML private ComboBox<String> supplyMethodComboBox;
    @FXML private TextField selfPickupNameField;
    @FXML private TextField selfPickupPhoneField;
    @FXML private TextField deliveryNameField;
    @FXML private TextField deliveryPhoneField;
    @FXML private TextField deliveryAddressField;
    @FXML private TextField additionalInfoField;
    @FXML private Label deliveryFeeLabel;
    @FXML private TableView<CartItem> orderItemsTable;
    @FXML private TableColumn<CartItem, String> itemNameColumn;
    @FXML private TableColumn<CartItem, String> changesColumn;
    @FXML private TableColumn<CartItem, Double> priceColumn;
    @FXML private Label totalPriceLabel;
    @FXML private Label timeNotSpecError;

    private ObservableList<CartItem> cartItems;
    private double deliveryFee = 25.0;
    private SelectFromRestMenuController previousController;

    @FXML
    private void initialize() {
        supplyMethodComboBox.getItems().addAll("Self Pick-Up", "Delivery");
        supplyMethodComboBox.setOnAction(e -> {
            updateFieldsAvailability();
            updateTotalPrice();
        });

        supplyDatePicker.setDayCellFactory(picker -> new DateCell() {
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                LocalDate today = LocalDate.now();
                setDisable(empty || date.compareTo(today) < 0);
            }
        });

        itemNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        changesColumn.setCellValueFactory(new PropertyValueFactory<>("changes"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

        disableAllFields();
        deliveryFeeLabel.setText(String.format("%.2f", deliveryFee));
    }

    private void disableAllFields() {
        selfPickupNameField.setDisable(true);
        selfPickupPhoneField.setDisable(true);
        deliveryNameField.setDisable(true);
        deliveryPhoneField.setDisable(true);
        deliveryAddressField.setDisable(true);
        additionalInfoField.setDisable(true);
    }

    public void initData(ObservableList<CartItem> cartItems) {
        this.cartItems = cartItems;
        orderItemsTable.setItems(this.cartItems);
        updateTotalPrice();
    }

    private void updateFieldsAvailability() {
        String selectedMethod = supplyMethodComboBox.getValue();
        if (selectedMethod == null) {
            disableAllFields();
        } else if (selectedMethod.equals("Self Pick-Up")) {
            selfPickupNameField.setDisable(false);
            selfPickupPhoneField.setDisable(false);
            deliveryNameField.setDisable(true);
            deliveryPhoneField.setDisable(true);
            deliveryAddressField.setDisable(true);
            additionalInfoField.setDisable(true);
        } else if (selectedMethod.equals("Delivery")) {
            selfPickupNameField.setDisable(true);
            selfPickupPhoneField.setDisable(true);
            deliveryNameField.setDisable(false);
            deliveryPhoneField.setDisable(false);
            deliveryAddressField.setDisable(false);
            additionalInfoField.setDisable(false);
        }
    }

    private void updateTotalPrice() {
        double totalPrice = cartItems.stream().mapToDouble(CartItem::getPrice).sum();
        
        if ("Delivery".equals(supplyMethodComboBox.getValue())) {
            totalPrice += deliveryFee;
        }

        totalPriceLabel.setText(String.format("%.2f", totalPrice));
    }

    @FXML
    private void handleNextButtonAction() {
        if (supplyDatePicker.getValue() == null) {
            timeNotSpecError.setVisible(true);
        } else {
            timeNotSpecError.setVisible(false);
            showOrderConfirmation();
        }
    }

    private void showOrderConfirmation() {
        Stage popupWindow = new Stage();
        popupWindow.initModality(Modality.APPLICATION_MODAL);
        popupWindow.setTitle("Order Confirmation");

        Label label = new Label("Your Order is waiting to be accepted by supplier ✅");
        Button closeButton = new Button("OK");
        closeButton.setOnAction(e -> popupWindow.close());

        VBox layout = new VBox(10);
        layout.getChildren().addAll(label, closeButton);
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout, 300, 150);
        popupWindow.setScene(scene);
        popupWindow.showAndWait();
    }

    @FXML
    private void handleBackButtonAction(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/SelectFromRestMenu.fxml"));
            Parent root = loader.load();

            SelectFromRestMenuController controller = loader.getController();
            controller.restoreState(previousController);

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Error", "Could not load the Select From Restaurant Menu page.");
        }
    }
    
    public void setPreviousController(SelectFromRestMenuController controller) {
        this.previousController = controller;
    }
    
    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}