package gui;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import client.ChatClient;
import entities.MenuItems;
import entities.Restaurant;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class SelectFromRestMenuController {

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


    
    @FXML
    private void initialize() {
    	
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

    private ArrayList<String> getSelectedChangesForItem(MenuItems item)
    {
        CheckBoxTableCell cell = cellMap.get(item);
        return cell != null ? cell.getSelectedChanges() : new ArrayList<>();
    }

    @FXML
    private void handleRemoveItem() {
        CartItem selectedItem = cartTableView.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            cartItems.remove(selectedItem);
            updateTotalPrice();
        }
    }

    /*
    //KKKKKKKKKKKKKKKK
    @FXML
    private void handleBack(ActionEvent event) 
    {

        // Logic for back button
        System.out.println("Back button clicked");
        System.out.println("print MenuITtttTTems:"+restaurantslocal);

        
        
        //aaaaaaaaaaaaaaaaaaaa
        FXMLLoader loader = new FXMLLoader();
        Pane root = null;
        Stage primaryStage = new Stage();
        try 
        {
            // Load the FXML file
            loader.setLocation(getClass().getResource("/gui/RestaurantSelection.fxml"));

            root = loader.load();
            
            // Hide the current window
            ((Node) event.getSource()).getScene().getWindow().hide();

            // Set the new stage
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.show();
			primaryStage.setTitle("User-Portal -> New Order -> Select Restaurant");

            
			RestaurantSelectionController RestaurantSelectionController = loader.getController();
			RestaurantSelectionController.loadRestaurant(restaurantslocal);
            // Uncomment and use if needed
        } 
        catch (IOException e) 
        {
            // Print the stack trace and show an error dialog
            e.printStackTrace();
            showAlert("Error", "Could not load the Start Order page.");
        }
        
        //aaaaaaaaaaaaaaaaaaaaaa
        
        
    }
    //KKKKKKKKKKKKKKKKKKKKK
    */
    
    
    //LLLLLLLLLLLLLLLLLLL
    
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
    
    //LLLLLLLLLLLLLLLLLLLL
    
    
    private void showAlert(String title, String message) 
    {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
    
    
    

    @FXML
    private void handleNext(ActionEvent event) 
    {

        FXMLLoader loader = new FXMLLoader();
        Pane root = null;
        Stage primaryStage = new Stage();
        try 
        {
            // Load the FXML file
            loader.setLocation(getClass().getResource("/gui/SupplyConfiguration.fxml"));

            root = loader.load();
            
            // Hide the current window
            ((Node) event.getSource()).getScene().getWindow().hide();

            // Set the new stage
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.show();
			primaryStage.setTitle("User-Portal -> New Order -> Select Restaurant -> Select Menu Items -> Supply Configuration");

            
			SupplyConfigurationController SupplyConfigurationController = loader.getController();
			//SelectFromRestMenuController.loadRestaurant(restaurantslocal);

            // Uncomment and use if needed
        } 
        catch (IOException e) 
        {
            // Print the stack trace and show an error dialog
            e.printStackTrace();
            showAlert("Error", "Could not load the Start Order page.");
        }
    
    
    
    }

    
    
    
    
    private void loadMenuItems() {
        // Convert the ArrayList to an ObservableList and set it to the TableView
        menuItemsList = FXCollections.observableArrayList(menuItemsArrayList);
        menuTableView.setItems(menuItemsList);
    }

    private void updateTotalPrice() {
        double total = cartItems.stream()
                .mapToDouble(CartItem::getPrice)
                .sum();
        totalPriceLabel.setText(String.format("%.2f", total));
    }

    // Custom cell factory to display CheckBoxes in a TableCell
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
    
    
    public void loadRestaurant(ArrayList<Restaurant> restaurants) 
    {
        this.restaurantslocal = restaurants;
		System.out.println("KKKKKKKKKKKKKKKKKKKKKKKK"+ this.restaurantslocal.toString());

    }
    
    

    
}