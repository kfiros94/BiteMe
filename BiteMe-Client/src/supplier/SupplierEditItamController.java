package supplier;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.converter.FloatStringConverter;
import javafx.geometry.Insets;

import entities.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import client.ClientUI;


public class SupplierEditItamController {

	@FXML
	private Label restaurantlbl;
	@FXML
	private Label ItamNotSelectedlbl;

	@FXML
	private Button backButton;
	@FXML
	private Button AddItamButton;
	@FXML
	private Button RemoveItamButton;
	@FXML
	private Button EditButton;

	@FXML
	private TableView<MenuItem> menuItemsTable;
	@FXML
	private TableColumn<MenuItem, Integer> itemIdColumn;
	@FXML
	private TableColumn<MenuItem, String> nameColumn;
	@FXML
	private TableColumn<MenuItem, String> descriptionColumn;
	@FXML
	private TableColumn<MenuItem, Float> priceColumn;
	@FXML
	private TableColumn<MenuItem, String> categoryColumn;
	@FXML
	private TableColumn<MenuItem, String> possibleChangesColumn;

	// Static reference to the controller instance
	public static SupplierEditItamController instance;
	public static List<MenuItem> menuItemsList;
	private Restaurant restaurant;
	private User usupplier;
    private Stage newItemFormStage;
    
	public static SupplierEditItamController getController() {
		return instance;
	}

	
	@FXML
	public void initialize() {
		instance = this;

		// Initialize table columns
		itemIdColumn.setCellValueFactory(new PropertyValueFactory<>("itemId"));
		nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
		descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
		priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
		categoryColumn.setCellValueFactory(new PropertyValueFactory<>("category"));
		possibleChangesColumn.setCellValueFactory(new PropertyValueFactory<>("possibleChanges"));

		// Enable cell editing
		
		nameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
		descriptionColumn.setCellFactory(TextFieldTableCell.forTableColumn());
		priceColumn.setCellFactory(TextFieldTableCell.forTableColumn(new FloatStringConverter()));
		categoryColumn.setCellFactory(ComboBoxTableCell.forTableColumn("appetizer", "main course", "dessert"));
		possibleChangesColumn.setCellFactory(TextFieldTableCell.forTableColumn());

		menuItemsTable.setEditable(true);

		 }
	
	
	public void setUserRestaurant(Restaurant restaurant, User userSupplier) {
		this.restaurant = restaurant;
		restaurantlbl.setText(restaurant.getName());
		this.usupplier = userSupplier;

		loadMenuItems();

	}

	//requset from db to manue 
	public void loadMenuItems() {
		// Create a MenuItem with only the restaurantId set
		MenuItem menuItemRequest = new MenuItem();
		menuItemRequest.setRestaurantId(restaurant.getRestaurantID());

		BiteOptions RequestRestaurantMenu = new BiteOptions(menuItemRequest.toString(),BiteOptions.Option.SHOW_MENU_RESTAURANT);
		ClientUI.chat.accept(RequestRestaurantMenu);
	}
	
	public static void GetMessageFromServer(String msg) {
		System.out.println("SupplierEditItamController- Message From Server: "+msg );
	}
	
	public static void setMenuItemsList(BiteOptions BiteItems) {
	    if (instance != null) {
	        List<String> menuItemStrings = new ArrayList<>();
	        Pattern pattern = Pattern.compile("MenuItem\\{[^}]+\\}");
	        Matcher matcher = pattern.matcher(BiteItems.getData().toString());

	        while (matcher.find()) {
	            menuItemStrings.add(matcher.group());
	        }

	        List<MenuItem> menuItems = new ArrayList<>();
	       // List<Integer> itemIds = new ArrayList<>(); // List to store item IDs

	        for (String menuItemString : menuItemStrings) {
	            try {
	                MenuItem menuItem = MenuItem.fromString(menuItemString);
	                menuItems.add(menuItem);
	                //itemIds.add(menuItem.getItemId()); // Collecting the item ID
	            } catch (IllegalArgumentException e) {
	                System.err.println("Error parsing menu item: " + menuItemString);
	                e.printStackTrace();
	            }
	        }

	        instance.menuItemsList = new ArrayList<>(menuItems); // Save the list in menuItemsList
	        //instance.menuItemsTable.setItems(FXCollections.observableArrayList(menuItems));
	        instance.menuItemsTable.setItems(FXCollections.observableArrayList(instance.menuItemsList));

	        System.out.println("menuItemsList: " + menuItemsList);

	        // Output the list of item IDs
	       // System.out.println("List of all item IDs: " + itemIds);
	        
	     // Update existingItemIds list
	      //  instance.existingItemIds = new ArrayList<>(itemIds); 
	        //System.out.println("List of existing item IDs: " + instance.existingItemIds);
	    }
	    
	 
	}


/*	
	public static void setMenuItemsList(BiteOptions BiteItems) {
	    if (instance != null) {
	        List<String> menuItemStrings = new ArrayList<>();
	        Pattern pattern = Pattern.compile("MenuItem\\{[^}]+\\}"); 
	        Matcher matcher = pattern.matcher(BiteItems.getData().toString());

	        while (matcher.find()) {
	            menuItemStrings.add(matcher.group());
	        }

	        List<MenuItem> menuItems = new ArrayList<>();
	        for (String menuItemString : menuItemStrings) {
	            try {
	                MenuItem menuItem = MenuItem.fromString(menuItemString);
	                menuItems.add(menuItem);
	            } catch (IllegalArgumentException e) {
	                System.err.println("Error parsing menu item: " + menuItemString);
	                e.printStackTrace();
	            }
	        }

	        instance.menuItemsList = new ArrayList<>(menuItems); // Save the list in menuItemsList
	        instance.menuItemsTable.setItems(FXCollections.observableArrayList(instance.menuItemsList));

	        System.out.println("menuItemsList: " + menuItemsList);
   
	    }   	 
	}
*/
	

	@FXML
	private void handleBack(ActionEvent event) {
		
		// Close the NewItemFormController window if it's open
        if (newItemFormStage != null) {
            newItemFormStage.close();
        }
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/Supplier/MainPageSupplier.fxml"));
			Parent root = loader.load();

			// Get the controller of MainPageSupplierController and set the data
			MainPageSupplierController mainPageSupplierController = loader.getController();

			mainPageSupplierController.setRestaurant(restaurant);
			mainPageSupplierController.setUserSupplier(usupplier);

			Scene scene = new Scene(root);
			Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
			stage.setScene(scene);
			stage.setTitle("Supplier Main Page - BiteMe");
			stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	
	 @FXML
	    private void handleAddItam(ActionEvent event) {
		 ItamNotSelectedlbl.setVisible(false);
		 try {
	            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Supplier/NewItemForm.fxml"));
	            Parent root = loader.load();

	            NewItemFormController controller = loader.getController();
	            controller.setMainController(this);
	            controller.SetMenuItemsList(menuItemsList); // Pass the existing menu
	            controller.SetpageTitlelabel("New Itam");

	            Stage stage = new Stage();
	            stage.setTitle("Add New Item");
	            stage.setScene(new Scene(root));

	            // Store the stage reference
	            newItemFormStage = stage;

	            stage.show();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }


	// Handle response from ChatClient
	 public void MenuItemAddOrupdatedFromChat(MenuItem updatedItem) {
		    // Check if the item already exists in the list
		 ItamNotSelectedlbl.setVisible(false);
		    boolean itemExists = false;
		    
		    for (int i = 0; i < menuItemsList.size(); i++) {
		        MenuItem currentItem = menuItemsList.get(i);
		        
		        if (currentItem.getItemId() == updatedItem.getItemId()) {
		            // Update the existing item
		            menuItemsList.set(i, updatedItem);
		            itemExists = true;
		            break;
		        }
		    }
		    
		    if (!itemExists) {
		        // If the item does not exist, add it as a new item
		        menuItemsList.add(updatedItem);
		    }

		    // Refresh the table to display the updated/added item
		    menuItemsTable.setItems(FXCollections.observableArrayList(menuItemsList));
		    menuItemsTable.refresh();

		    System.out.println("MenuItem added/updated: " + updatedItem);
		}



	@FXML // DELETE_ITEM_MENU
	private void handleRemoveItam(ActionEvent event) {
		System.out.println("Remove item button clicked");

		MenuItem selectedItem = menuItemsTable.getSelectionModel().getSelectedItem();
		if (selectedItem != null) {
			System.out.println("Selected item: " + selectedItem);
			

			if (menuItemsList != null) {
				System.out.println("Removing item from menuItemsList: " + selectedItem);
				menuItemsList.remove(selectedItem);
			
				
				BiteOptions RequestDelete = new BiteOptions(selectedItem.toString(),BiteOptions.Option.DELETE_ITEM_MENU);
				System.out.println("Supplier send to server "+RequestDelete);

				ClientUI.chat.accept(RequestDelete);
			
				System.out.println("Removing item from menuItemsTable");
				menuItemsTable.getItems().remove(selectedItem);

			}
		}
		else {
			ItamNotSelectedlbl.setText("Please select itam.");

		}

	}


	@FXML
	private void handleEdit(ActionEvent event) {
	    // Get the selected item from the table
	    MenuItem selectedItem = menuItemsTable.getSelectionModel().getSelectedItem();

	    if (selectedItem != null) {
	        try {
	            // Load the NewItemForm.fxml
	            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Supplier/NewItemForm.fxml"));
	            Parent root = loader.load();

	            // Get the controller of NewItemFormController and set the data
	            NewItemFormController controller = loader.getController();
	            controller.setMainController(this);
	            controller.SetMenuItemsList(menuItemsList); // Pass the existing menu
	            controller.SetpageTitlelabel("Edit Itam");


	            // Set the fields in the NewItemForm with the selected item's data
	            controller.IDFiled.setText(String.valueOf(selectedItem.getItemId()));
	            controller.nameField.setText(selectedItem.getName());
	            controller.descriptionField.setText(selectedItem.getDescription());
	            controller.priceField.setText(String.valueOf(selectedItem.getPrice()));
	            controller.categoryField.setValue(selectedItem.getCategory());
	            controller.possibleChangesField.setText(selectedItem.getPossibleChanges());

	            // Open the NewItemForm window
	            Stage stage = new Stage();
	            stage.setTitle("Edit Item");
	            stage.setScene(new Scene(root));

	            // Store the stage reference if needed
	            newItemFormStage = stage;

	            stage.show();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    } else {
	        System.out.println("No item selected for editing.");
	        ItamNotSelectedlbl.setText("No item selected for editing.");
	    }
	}



}
