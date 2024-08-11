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
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.stream.Collectors;

import client.ClientUI;
import entities.BiteOptions;
import entities.RestaurantOrders;
import entities.User;
import gui.SelectFromRestMenuController.CartItem;




public class SupplyConfigurationController {

    @FXML private DatePicker supplyDatePicker;
    @FXML private Spinner supplyHourSpinner;
    @FXML private Spinner supplyMinuteSpinner;
    @FXML private ComboBox<String> supplyMethodComboBox;
    @FXML private TextField selfPickupNameField;
    @FXML private TextField selfPickupPhoneField;

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
    private RestaurantOrders restaurantOrders = new RestaurantOrders();

    private double totalPrice;
    
    

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
        
        
        //aaaaaaaaaaaaaaaa
        // Initialize the hour spinner with a range from 10 to 22
        SpinnerValueFactory<Integer> hourValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(10, 22);
        supplyHourSpinner.setValueFactory(hourValueFactory);

        // Initialize the minute spinner with a range from 0 to 59
        SpinnerValueFactory<Integer> minuteValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 59);
        supplyMinuteSpinner.setValueFactory(minuteValueFactory);
        
        //aaaaaaaaaaaaaaa
        
    }
    
    
    

    private void disableAllFields() {

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
        } else if (selectedMethod.equals("Self Pick-Up")) 
        {

            deliveryAddressField.setDisable(true);
            additionalInfoField.setDisable(true);
        } 
        else if (selectedMethod.equals("Delivery")) 
        {

            deliveryAddressField.setDisable(false);
            additionalInfoField.setDisable(false);
        }
    }

    private void updateTotalPrice() {
         totalPrice = cartItems.stream().mapToDouble(CartItem::getPrice).sum();
        
        if ("Delivery".equals(supplyMethodComboBox.getValue())) {
            totalPrice += deliveryFee;
        }

        totalPriceLabel.setText(String.format("%.2f", totalPrice));
    }


    
    @FXML
    private void handleNextButtonAction() 
    {
        if (supplyDatePicker.getValue() == null) 
        {
            timeNotSpecError.setVisible(true);
        } 
        else 
        {
            timeNotSpecError.setVisible(false);

            // Get the DATETIME string
            String dateTimeString = generateDateTimeString();
            if (dateTimeString != null) 
            {
                System.out.println("Generated DATETIME string: " + dateTimeString);
                // Use dateTimeString as needed (e.g., save it to the database)
            }
            
            restaurantOrders.setTotal_price(totalPrice);
            restaurantOrders.setOrder_list(this.getcartItems());
            restaurantOrders.setFull_name(selfPickupNameField.getText());
            restaurantOrders.setPhone_number(selfPickupPhoneField.getText());
            restaurantOrders.setDelivery_type(supplyMethodComboBox.getValue());
            restaurantOrders.setStatus("pending");
            restaurantOrders.setPlacing_order_date(dateTimeString);
            
            
            if(supplyMethodComboBox.getValue().equals("Delivery"))
            {
            	restaurantOrders.setOrder_address(deliveryAddressField.getText());
            }
            
            System.out.println("GGGGGGGGGGGGGGGGG " + restaurantOrders);

            

            showOrderConfirmation();
        }
    }
    

    /*
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
   */
    
    
    //aaaaaaaaaaaaaaa
    
    private void showOrderConfirmation() {
        Stage popupWindow = new Stage();
        popupWindow.initModality(Modality.APPLICATION_MODAL);
        popupWindow.setTitle("Order Confirmation");

        Label label = new Label("Your Order is waiting to be accepted by supplier ✅");
        Button closeButton = new Button("OK");
        closeButton.setOnAction(e -> {
            popupWindow.close();
            loadMainPagesClient();
        });

        VBox layout = new VBox(10);
        layout.getChildren().addAll(label, closeButton);
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout, 300, 150);
        popupWindow.setScene(scene);
        popupWindow.showAndWait();
    }

    private void loadMainPagesClient() {
        try {
        	
		    User user = new User(restaurantOrders.getUser_id(),null,null,null,null,null,null,false,0,null);//kkkkkkk
			BiteOptions option = new BiteOptions(user.toString(), BiteOptions.Option.BACK_HOME_CUSTOMER_PAGE);//kkkkkkk

			ClientUI.chat.accept(option);//kkkkkkk


            FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/MainPagesClient.fxml"));
            Parent root = loader.load();
            
            // Create and show the new stage
            Stage stage = new Stage();
            stage.setTitle("Main Pages Client");
            stage.setScene(new Scene(root));
            stage.show();
            
            // Close the current window
            Stage currentStage = (Stage) supplyDatePicker.getScene().getWindow(); // Assuming `supplyDatePicker` is a node in the current window
            currentStage.close();
            
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Error", "Could not load the Main Pages Client page.");
        }
    }
    //aaaaaaaaaaaaaa
    
    
    
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
    
    


    
    
    private String formatChanges(String changes) {
        if (changes == null || changes.isEmpty()) {
            return "[]"; // Return an empty array if there are no changes
        }

        // Split the changes string by comma and wrap each change in quotes
        String[] changesArray = changes.split(",");
        String formattedChanges = Arrays.stream(changesArray)
                                        .map(change -> "\"" + change.trim() + "\"")
                                        .collect(Collectors.joining(", "));

        return "[" + formattedChanges + "]";
    }

    public String getcartItems() {
        if (cartItems == null || cartItems.isEmpty()) {
            return "[]"; // Return an empty JSON array if there are no items
        }

        return cartItems.stream()
                .map(item -> String.format(
                        "{\"item\": \"%s\", \"changes\": %s, \"Price\": %.2f}",
                        item.getName(),                          // The item name
                        formatChanges(item.getChanges()),        // Format the changes list
                        item.getPrice()                          // The price of the item
                ))
                .collect(Collectors.joining(", ", "[", "]"));
    }

    
    
    
    
    
    
    public void loadRestaurantOrders(RestaurantOrders restaurantOrders) 
    {
        this.restaurantOrders = restaurantOrders;
		System.out.println("OOOOOOOOOOOO"+ this.restaurantOrders.toString());

    }
    

    //aaaaaaaaaaaaaaa
    

    /**
     * Helper method to generate a DATETIME string from the selected date, hour, and minute.
     * @return A string in the format 'YYYY-MM-DD HH:MM:SS' representing the selected date and time.
     */
    private String generateDateTimeString() {
        LocalDate selectedDate = supplyDatePicker.getValue();
        int selectedHour = (int) supplyHourSpinner.getValue();
        int selectedMinute = (int) supplyMinuteSpinner.getValue();

        if (selectedDate == null) {
            // Handle the case where the date is not selected (e.g., show an error message)
            return null;  // or throw an exception
        }

        // Combine date and time components into a single string in the desired format
        String datePart = selectedDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        String timePart = String.format("%02d:%02d:00", selectedHour, selectedMinute); // HH:MM:SS

        return datePart + " " + timePart;  // 'YYYY-MM-DD HH:MM:SS'
    }
    
    //aaaaaaaaaaaaaaaa
    
    
    
    
}