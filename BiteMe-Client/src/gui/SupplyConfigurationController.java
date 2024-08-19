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
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.stream.Collectors;

import client.ChatClient;
import client.ClientUI;
import entities.BiteOptions;
import entities.RestaurantOrders;
import entities.User;
import gui.SelectFromRestMenuController.CartItem;


/**
 * Controller class for handling the supply configuration view in the GUI.
 * This class manages the user interface components related to setting up the 
 * delivery or pickup options, as well as processing the order.
 * 
 * @author Kfir Amoyal
 * @author Noam Furman
 * @author Israel Ohayon
 * @author Eitan Zerbel
 * @author Yaniv Shatil
 * @author Omri Heit

 * @version August 2024
 */
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
    
    @FXML private Button applyDiscountButton;
    @FXML private Label discountMessageLabel;
    @FXML private Label discounLabel;
    @FXML private Label discountnumLabel;
    @FXML private Label FinalPriceLabel;
    @FXML private Label FinalPriceNumLabel;
    @FXML private Label DiscountApleyLabel;

    private ObservableList<CartItem> cartItems;
    private double deliveryFee = 25.0;
    private SelectFromRestMenuController previousController;
    private RestaurantOrders restaurantOrders = new RestaurantOrders();

    private double totalPrice;
    
    private User UserDitales;  
    private int discountAmount = 0;
    private int discountCount = 0;
    private LocalDate selectedDate;
    
    
    /**
     * Initializes the controller class. This method is automatically called after the 
     * FXML file has been loaded. It sets up the combo box, spinners, date picker, 
     * and table columns, and disables certain fields based on the initial configuration.
     */
    @FXML
    private void initialize() {
        applyDiscountButton.setDisable(true);
        discounLabel.setVisible(false);
        FinalPriceLabel.setVisible(false);
        discountnumLabel.setText("");
        FinalPriceNumLabel.setText("");

    	supplyMethodComboBox.setValue("pickup");  // Set initial value
        supplyMethodComboBox.getItems().addAll("pickup", "Delivery");
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
        
        // Initialize the hour spinner with a range from 10 to 22
        SpinnerValueFactory<Integer> hourValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(10, 22);
        supplyHourSpinner.setValueFactory(hourValueFactory);

        // Initialize the minute spinner with a range from 0 to 59
        SpinnerValueFactory<Integer> minuteValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 59);
        supplyMinuteSpinner.setValueFactory(minuteValueFactory);
        
        
    }
    

    /**
     * Set the CustomerDitales/UserCustomer information passed from RestaurantSelectionController.
     * 
     * @param userCustomer The User object containing the customer details.
     */
    public void seUserDitales(User userCustomer) {
        this.UserDitales = userCustomer;
        System.out.println("Customer Details set: " + this.UserDitales);
        
        if (UserDitales.isHasDiscountCode())
        {
        	  // Check and load discount count when setting the user
            	checkAndEnableDiscountButton();
        }
    }
    
    /**
     * Checks if the user has discount codes available and enables the apply button if they do.
     */
    private void checkAndEnableDiscountButton() {
        // Fetch the number of discount codes from the server or database
        BiteOptions option = new BiteOptions(UserDitales.toString(), BiteOptions.Option.GET_DISCOUNT_COUNT);
        ClientUI.chat.accept(option);
        
        discountCount = ChatClient.discountCount;
        System.out.println("SupplyConfigurationController user has discountCount: "+discountCount);


        if (discountCount > 0) {
            applyDiscountButton.setDisable(false);
            discountMessageLabel.setText("You have "+discountCount+" discounts to redeem");

        } else {
            applyDiscountButton.setDisable(true);
        }
    }
        
       
    /**
     * Disables all fields related to delivery information.
     */
    private void disableAllFields() {

        deliveryAddressField.setDisable(true);
        additionalInfoField.setDisable(true);
    }
    /**
     * Initializes the data for the order items table.
     * 
     * @param cartItems The list of items added to the cart.
     */
    public void initData(ObservableList<CartItem> cartItems) {
        this.cartItems = cartItems;
        orderItemsTable.setItems(this.cartItems);
        updateTotalPrice();
    }
    /**
     * Updates the availability of fields based on the selected supply method.
     * If "pickup" is selected, delivery-related fields are disabled.
     * If "Delivery" is selected, these fields are enabled.
     */
    private void updateFieldsAvailability() {
        String selectedMethod = supplyMethodComboBox.getValue();
        if (selectedMethod == null) {
            disableAllFields();
        } else if (selectedMethod.equals("pickup")) 
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
    /**
     * Updates the total price label based on the items in the cart and the selected 
     * supply method. Adds the delivery fee if "Delivery" is selected.
     */
    private void updateTotalPrice() {
         totalPrice = cartItems.stream().mapToDouble(CartItem::getPrice).sum();
        
        if ("Delivery".equals(supplyMethodComboBox.getValue())) {
            totalPrice += deliveryFee;
        }

        totalPriceLabel.setText(String.format("%.2f", totalPrice));
    }


    /**
     * Handles the action when the "Next" button is clicked. 
     * Validates the selected date and time, and proceeds to place the order if valid.
     */ 
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
            
                restaurantOrders.setTotal_price(totalPrice);
                restaurantOrders.setOrder_list(this.getcartItems());
                restaurantOrders.setFull_name(selfPickupNameField.getText());
                restaurantOrders.setPhone_number(selfPickupPhoneField.getText());
                restaurantOrders.setDelivery_type(supplyMethodComboBox.getValue());
                restaurantOrders.setStatus("pending");
                restaurantOrders.setPlacing_order_date(dateTimeString);
                restaurantOrders.setOrder_address("no address");

                if(supplyMethodComboBox.getValue().equals("Delivery"))
                {
                    restaurantOrders.setOrder_address(deliveryAddressField.getText());
                }
                
                System.out.println("Print restaurantOrders: " + restaurantOrders);

                // Send request to server to load MainPagesClient
                // Convert int to String using Integer.toString()
                BiteOptions option = new BiteOptions(Integer.toString(restaurantOrders.getUser_id()), BiteOptions.Option.BACK_HOME_CUSTOMER_PAGE);
                ClientUI.chat.accept(option);
                
                BiteOptions option2 = new BiteOptions(restaurantOrders, BiteOptions.Option.CREATE_ORDER);
                ClientUI.chat.accept(option2);
                

                showOrderConfirmation();
            }
        }
    }
    

    /**
     * Shows a confirmation dialog to the user after the order is placed.
     * This method creates a new stage with a confirmation message.
     */
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
    /**
     * Loads the main client page after the order confirmation is closed.
     * This method switches the scene to the MainPagesClient view.
     */
    private void loadMainPagesClient() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/MainPagesClient.fxml"));
            Parent root = loader.load();
            
            MainPagesClientController controller = loader.getController();
            controller.initialize(ChatClient.user1.getUsername(), ChatClient.user1.getaccountStatus(), ChatClient.user1.getBranch());
            
            Stage stage = new Stage();
            stage.setTitle("Main Pages Client");
            stage.setScene(new Scene(root));
            stage.show();
            
            // Close the current window
            Stage currentStage = (Stage) supplyDatePicker.getScene().getWindow();
            currentStage.close();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Error", "Could not load the Main Pages Client page.");
        }
    }

    /**
     * Handles the action when the "Back" button is clicked. 
     * Restores the previous controller state and loads the restaurant menu view.
     * 
     * @param event The event triggered by the button click.
     */ 
    @FXML
    private void handleBackButtonAction(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/SelectFromRestMenu.fxml"));
            Parent root = loader.load();

            SelectFromRestMenuController controller = loader.getController();
            controller.restoreState(previousController);
            controller.loadUserCustomer(this.UserDitales);

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Error", "Could not load the Select From Restaurant Menu page.");
        }
    }
    
    /**
     * Sets the previous controller to restore state when navigating back.
     * 
     * @param controller The previous controller instance.
     */
    public void setPreviousController(SelectFromRestMenuController controller) {
        this.previousController = controller;
    }
    /**
     * Displays an alert dialog with the given title and content.
     * 
     * @param title   The title of the alert dialog.
     * @param content The content of the alert message.
     */
    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
    
    


    
    /**
     * Formats the changes in the cart items for display.
     * 
     * @param changes The changes string to be formatted.
     * @return The formatted changes string.
     */
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
    
    
    /**
     * Generates a JSON representation of the cart items.
     * 
     * @return A JSON string representing the items in the cart.
     */
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

    
    
    /**
     * Loads the existing restaurant order details into the controller.
     * 
     * @param restaurantOrders The restaurant order details to load.
     */
    public void loadRestaurantOrders(RestaurantOrders restaurantOrders) 
    {
        this.restaurantOrders = restaurantOrders;
		System.out.println("OOOOOOOOOOOO"+ this.restaurantOrders.toString());
		

    }
    

    
    

    /**
     * Helper method to generate a DATETIME string from the selected date, hour, and minute.
     * @return A string in the format 'YYYY-MM-DD HH:MM:SS' representing the selected date and time.
     */
    private String generateDateTimeString() {
        selectedDate = supplyDatePicker.getValue();
        int selectedHour = (int) supplyHourSpinner.getValue();
        int selectedMinute = (int) supplyMinuteSpinner.getValue();

        if (selectedDate == null) {
            // Handle the case where the date is not selected (e.g., show an error message)
            return null;  // or throw an exception
        }

        // Combine date and time components into a single string in the desired format
        String datePart = selectedDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        String timePart = String.format("%02d:%02d:00", selectedHour, selectedMinute); // HH:MM:SS
        
     // Calculate the difference between the current time and the selected time
        LocalDate currentDate = LocalDate.now();
        LocalTime currentTime = LocalTime.now();
        LocalTime selectedTime = LocalTime.of(selectedHour, selectedMinute);

     // Check if the selected date is in the future
        if (selectedDate.isAfter(currentDate)) {
            applyTimeBasedDiscount();
        } else if (selectedDate.isEqual(currentDate)) {
            // If the selected date is today, calculate the time difference in minutes
            long timeDifferenceInMinutes = ChronoUnit.MINUTES.between(currentTime, selectedTime);

            // If the selected time is 2 hours or more in the future, apply the discount
            if (timeDifferenceInMinutes >= 120) {
                applyTimeBasedDiscount();
            }
        }


        return datePart + " " + timePart;  // 'YYYY-MM-DD HH:MM:SS'
    }
    
    private void applyTimeBasedDiscount() {
        double orignalPrice = totalPrice;
        this.totalPrice = totalPrice * 0.9;

        // Update the UI to show the discount applied
        discounLabel.setVisible(true);
        discounLabel.setText("10% Time-Based Discount Applied!");
        FinalPriceLabel.setVisible(true);
        FinalPriceNumLabel.setText(String.format("%.2f", orignalPrice));
        discountnumLabel.setText("-" + String.format("%.2f", orignalPrice * 0.1));
        totalPriceLabel.setText(String.format("%.2f", totalPrice));
        
        System.out.println("10% time-based discount applied. New total price: " + totalPrice);
    }

    
    
    @FXML
    private void handleApplyDiscount(ActionEvent event) {
    	 //double discountedPrice = totalPrice * 0.5;
    	 double orignalPrice =totalPrice;
    	 this.totalPrice=totalPrice * 0.5;
         // Decrease the discount count by 1 as the discount has been used
         discountCount--;
         
         // Disable the discount button 
      
         applyDiscountButton.setDisable(true);
        

         // Update the user on the discount being applied
         discountMessageLabel.setText("You have "+discountCount+" discounts to redeem");

         DiscountApleyLabel.setText("A 50% discount has been applied!");
         
         discounLabel.setVisible(true);
         FinalPriceLabel.setVisible(true);
         discountnumLabel.setText("-"+String.format("%.2f", totalPrice));
         FinalPriceNumLabel.setText(String.format("%.2f",orignalPrice));
         totalPriceLabel.setText(String.format("%.2f",totalPrice));
         
         System.out.println("Discount applied. New total price: " + totalPrice);
         
         BiteOptions option = new BiteOptions(UserDitales.toString(), BiteOptions.Option.UPDATE_DISCOUNT_COUNT);

     
         // Send the request to the server to update the discount count in the database
         ClientUI.chat.accept(option);

    
    }

    

}