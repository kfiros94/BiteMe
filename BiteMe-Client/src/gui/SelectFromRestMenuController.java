package gui;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import client.ChatClient;
import entities.MenuItems;
import entities.Restaurant;
import entities.RestaurantOrders;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
/**
 * Controller class for the restaurant menu selection screen.
 * Handles interactions with the menu, cart, and navigation buttons.
 * 
 * @author Kfir Amoyal
 * @author Noam Furman
 * @author Israel Ohayon
 * @author Eitan Zerbel
 * @author Yaniv Shatil
 * @author Omri Heit

 * @version August 2024
 */
public class SelectFromRestMenuController 
{

    @FXML
    private TableView<MenuItems> menuTableView;

    @FXML
    private TableColumn<MenuItems, String> categoryColumn;

    @FXML
    private TableColumn<MenuItems, String> nameColumn;

    @FXML
    private TableColumn<MenuItems, ArrayList<String>> changesColumn;

    @FXML
    private TableColumn<MenuItems, Double> priceColumn;

    @FXML
    private TableView<CartItem> cartTableView;

    @FXML
    private TableColumn<CartItem, String> cartItemNameColumn;

    @FXML
    private TableColumn<CartItem, String> cartChangesColumn;

    @FXML
    private TableColumn<CartItem, Double> cartPriceColumn;

    @FXML
    private Label totalPriceLabel;

    @FXML
    private Button addItemButton;

    @FXML
    private Button removeItemButton;

    @FXML
    private Button backButton;

    @FXML
    private Button nextButton;
    

    private ObservableList<MenuItems> menuItemsList;
    private ObservableList<CartItem> cartItems;
    private ArrayList<MenuItems> menuItemsArrayList;
    private Map<MenuItems, CheckBoxTableCell> cellMap = new HashMap<>();
    
    private ArrayList<Restaurant> restaurantslocal = new  ArrayList<Restaurant>();
    private RestaurantOrders restaurantOrders = new RestaurantOrders();



    /**
    * Initializes the controller after its root element has been completely processed.
    */
    @FXML
    private void initialize() 
    {
    	
        System.out.println("print MenuITTTTems:"+ChatClient.menuItems);
        

    	
        // Initialize the menu table columns
        categoryColumn.setCellValueFactory(new PropertyValueFactory<>("category"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        changesColumn.setCellValueFactory(new PropertyValueFactory<>("possible_changes"));

        // Set up the custom cell for the changes column
        setupChangesColumn();

        // Initialize the cart table columns
        cartItemNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        cartChangesColumn.setCellValueFactory(new PropertyValueFactory<>("changes"));
        cartPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

        // Initialize the ObservableList for cart items
        cartItems = FXCollections.observableArrayList();
        cartTableView.setItems(cartItems);

        // Create ArrayList of MenuItems
        menuItemsArrayList = new ArrayList<>();
        menuItemsArrayList.addAll(0, ChatClient.menuItems);
        
     //   menuItemsArrayList.add(new MenuItems(1, 101, "Cheese Pizza", "Delicious cheese pizza", 12.99, "Pizza", new ArrayList<>(List.of("No onions", "No Broccoli", "No olives"))));
      //  menuItemsArrayList.add(new MenuItems(2, 101, "Beef Burger", "Juicy beef burger", 9.99, "Burger", new ArrayList<>(List.of("Extra cheese", "No lettuce", "No tomato"))));
     //   menuItemsArrayList.add(new MenuItems(3, 101, "Carbonara", "Classic carbonara pasta", 14.99, "Pasta", new ArrayList<>(List.of("No bacon", "Extra cheese", "Gluten-free pasta"))));

        // Load menu items
        loadMenuItems();

        // Set up listeners
        menuTableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            addItemButton.setDisable(newSelection == null);
        });

        cartTableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            removeItemButton.setDisable(newSelection == null);
        });
    }

    /**
    * Sets up the changes column to display CheckBoxes in a TableCell.
    */
    private void setupChangesColumn() 
    {
        changesColumn.setCellFactory(column -> {
            CheckBoxTableCell cell = new CheckBoxTableCell();
            cell.setOnUpdateItem((item, empty) -> {
                if (!empty) {
                    cellMap.put(item, cell);
                }
            });
            return cell;
        });
    }

    /**
     * Handles the addition of a selected menu item to the cart.
     */
    @FXML
    private void handleAddItem() 
    {
        MenuItems selectedItem = menuTableView.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            // Get the selected changes
            ArrayList<String> selectedChanges = getSelectedChangesForItem(selectedItem);
            // Add the selected item to the cart
            cartItems.add(new CartItem(selectedItem.getName(), String.join(", ", selectedChanges), selectedItem.getPrice()));
            updateTotalPrice();
        }
    }

    /**
     * Retrieves the selected changes for a given menu item.
     * 
     * @param item the menu item to get changes for
     * @return a list of selected changes
     */
    private ArrayList<String> getSelectedChangesForItem(MenuItems item)
    {
        CheckBoxTableCell cell = cellMap.get(item);
        return cell != null ? cell.getSelectedChanges() : new ArrayList<>();
    }

    /**
     * Handles the removal of a selected item from the cart.
     */
    @FXML
    private void handleRemoveItem() {
        CartItem selectedItem = cartTableView.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            cartItems.remove(selectedItem);
            updateTotalPrice();
        }
    }

    /**
     * Handles the action of the "Back" button, returning to the previous screen.
     * 
     * @param event the action event triggered by the "Back" button
     */
    @FXML
    private void handleBack(ActionEvent event) {
        System.out.println("Back button clicked");
        System.out.println("print MenuITtttTTems:" + restaurantslocal);

        FXMLLoader loader = new FXMLLoader();
        Pane root = null;
        Stage primaryStage = new Stage();
        try {
            loader.setLocation(getClass().getResource("/gui/RestaurantSelection.fxml"));
            root = loader.load();
            
            ((Node) event.getSource()).getScene().getWindow().hide();

            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.show();
            primaryStage.setTitle("User-Portal -> New Order -> Select Restaurant");

            RestaurantSelectionController restaurantSelectionController = loader.getController();
            restaurantSelectionController.loadUserCustomer(ChatClient.user1);
            restaurantSelectionController.reloadRestaurantsAndSetBranch();
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Error", "Could not load the Restaurant Selection page.");
        }
    }
    
    
    
    /**
     * Displays an alert with the given title and content.
     * 
     * @param title the title of the alert
     * @param content the content of the alert
     */ 
    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
    
    /**
     * Handles the action of the "Next" button, proceeding to the Supply Configuration screen.
     * 
     * @param event the action event triggered by the "Next" button
     */
	@FXML
	private void handleNext(ActionEvent event) {
	    // Check if the cart is empty
	    if (cartItems.isEmpty()) {
	        showAlert("Cart Empty", "Your cart is empty. Please add items to your cart before proceeding.");
	    } else {
	        try {
	            FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/SupplyConfiguration.fxml"));
	            Parent root = loader.load();

	            SupplyConfigurationController supplyConfigurationController = loader.getController();
	            supplyConfigurationController.initData(cartItems);

	            supplyConfigurationController.loadRestaurantOrders(restaurantOrders);

	            // Debugging print to understand how the cart items array looks like
	            System.out.println("print cartItemsSSSSSSSSSSS:" + supplyConfigurationController.getcartItems());

	            supplyConfigurationController.setPreviousController(this);

	            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
	            Scene scene = new Scene(root);
	            stage.setScene(scene);
	            stage.show();
	        } catch (IOException e) {
	            e.printStackTrace();
	            showAlert("Error", "Could not load the Supply Configuration page.");
	        }
	    }
	}

  

    

    /**
     * Restores the state of the controller from a previous instance.
     * 
     * @param previousState the previous state of the controller
     */
    public void restoreState(SelectFromRestMenuController previousState) {
        this.cartItems = previousState.cartItems;
        this.menuItemsList = previousState.menuItemsList;
        // Restore any other necessary state

        // Refresh the UI
        refreshUI();
    }
    
    private void refreshUI() {
        // Update TableViews, labels, or any other UI elements
        menuTableView.setItems(menuItemsList);
        cartTableView.setItems(cartItems);
        updateTotalPrice();
    }
    
    /**
     * Loads menu items into the table view.
     */
    private void loadMenuItems() {
        // Convert the ArrayList to an ObservableList and set it to the TableView
        menuItemsList = FXCollections.observableArrayList(menuItemsArrayList);
        menuTableView.setItems(menuItemsList);
    }
    /**
     * Updates the total price label based on the items in the cart.
     */
    private void updateTotalPrice() {
        double total = cartItems.stream()
                .mapToDouble(CartItem::getPrice)
                .sum();
        totalPriceLabel.setText(String.format("%.2f", total));
    }

    /**
     * Custom cell factory to display CheckBoxes in a TableCell.
     */
    private static class CheckBoxTableCell extends TableCell<MenuItems, ArrayList<String>> {
        private final List<CheckBox> checkBoxes = new ArrayList<>();
        private final HBox hbox = new HBox(10);
        private MenuItems currentItem;
        private OnUpdateItemCallback onUpdateItem;

        
        private CheckBoxTableCell() {
            setGraphic(hbox);
        }

        @Override
        protected void updateItem(ArrayList<String> item, boolean empty) {
            super.updateItem(item, empty);
            hbox.getChildren().clear();
            checkBoxes.clear();

            if (empty || item == null) {
                setGraphic(null);
                currentItem = null;
            } else {
                for (String change : item) {
                    CheckBox checkBox = new CheckBox(change.trim());
                    checkBoxes.add(checkBox);
                    hbox.getChildren().add(checkBox);
                }
                setGraphic(hbox);
                currentItem = getTableView().getItems().get(getIndex());
            }

            if (onUpdateItem != null) {
                onUpdateItem.onUpdateItem(currentItem, empty);
            }
        }

        public ArrayList<String> getSelectedChanges() {
            ArrayList<String> selectedChanges = new ArrayList<>();
            for (CheckBox checkBox : checkBoxes) {
                if (checkBox.isSelected()) {
                    selectedChanges.add(checkBox.getText());
                }
            }
            return selectedChanges;
        }

        public void setOnUpdateItem(OnUpdateItemCallback callback) {
            this.onUpdateItem = callback;
        }
    }

    @FunctionalInterface
    private interface OnUpdateItemCallback {
        void onUpdateItem(MenuItems item, boolean empty);
    }

    // CartItem class to represent items in the cart
    public static class CartItem {
        private final String name;
        private final String changes;
        private final double price;

        public CartItem(String name, String changes, double price) {
            this.name = name;
            this.changes = changes;
            this.price = price;
        }

        public String getName() {
            return name;
        }

        public String getChanges() {
            return changes;
        }

        public double getPrice() {
            return price;
        }
    }
    
    /**
     * Loads the restaurants into the controller.
     * This method is used to pass restaurant data from a previous screen.
     *
     * @param restaurants An ArrayList of Restaurant objects to be loaded into the controller.
     */
    public void loadRestaurant(ArrayList<Restaurant> restaurants) 
    {
        this.restaurantslocal = restaurants;
		System.out.println("loaded restaurant: "+ this.restaurantslocal.toString());

    }
    
    
   
    /**
     * Loads the restaurant orders into the controller.
     * This method is used to pass restaurant order data from a previous screen.
     *
     * @param restaurantOrders A RestaurantOrders object to be loaded into the controller.
     */
    public void loadRestaurantOrders(RestaurantOrders restaurantOrders) 
    {
        this.restaurantOrders = restaurantOrders;
		System.out.println("loades restaurant's orders: "+ this.restaurantOrders.toString());

    }
    

    
    /**
     * Retrieves the current items in the cart.
     *
     * @return An ObservableList of CartItem objects representing the items currently in the cart.
     */
    public ObservableList<CartItem> getCartItems() {
        return cartItems;
    }
    

    
}