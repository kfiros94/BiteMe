package supplier;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.util.List;

import client.ClientUI;
import entities.BiteOptions;
import entities.MenuItem;

public class NewItemFormController {

    @FXML
    private Label errorlabel;
    @FXML
    private Label pageTitlelabel;

    @FXML
    public TextField nameField;
    
    @FXML
    public TextField IDFiled; //private and non-editable

    @FXML
    public TextField descriptionField;

    @FXML
    public TextField priceField;

    @FXML
    public ComboBox<String> categoryField;

    @FXML
    public TextField possibleChangesField;

    private SupplierEditItamController mainController;
    public static List<MenuItem> menuItemsList;

    public void SetMenuItemsList(List<MenuItem> menuItemsList) {
        this.menuItemsList = menuItemsList;
        System.out.println("NewItemFormController--> menuItemsList: " + menuItemsList);
    }
    
    public void SetpageTitlelabel(String title) {
    	pageTitlelabel.setText(title);
    }

    @FXML
    public void initialize() {
        // Populate the ComboBox with options
        categoryField.getItems().addAll("appetizer", "main Course", "dessert");

        // Optionally, set a default value
        categoryField.setValue("Main Course"); // Set the default value if needed

        // Make the ID field non-editable
        IDFiled.setEditable(false);
        IDFiled.setVisible(false); // Hide the ID field if it's not needed for display
    }

    public void setMainController(SupplierEditItamController mainController) {
        this.mainController = mainController;
    }

    @FXML
    private void handleCancel() {
        // Get the current stage (window) and close it
        Stage stage = (Stage) nameField.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void handleSave(ActionEvent event) {
        // Retrieve and validate the input values
        String name = nameField.getText();
        String description = descriptionField.getText();
        String priceText = priceField.getText();
        String category = categoryField.getValue();
        String possibleChanges = possibleChangesField.getText();

        // Check if required fields are empty
        if (name.isEmpty() || description.isEmpty() || priceText.isEmpty() || category == null) {
            System.out.println("Please fill in all required fields.");
            errorlabel.setText("Please fill in all fields.");
            return;
        }
        int maxNameLength = 20;
        int maxDescriptionLength = 100;
        int maxPriceLength = 5;  // Including decimal point
        int maxPossibleChangesLength = 100;

        // Check if required fields are empty
        if (name.isEmpty() || description.isEmpty() || priceText.isEmpty() || category == null) {
            System.out.println("Please fill in all required fields.");
            errorlabel.setText("Please fill in all fields.");
            return;
        }

        // Check field lengths
        if (name.length() > maxNameLength) {
            System.out.println("Name exceeds maximum length of " + maxNameLength + " characters.");
            errorlabel.setText("Name exceeds maximum length of " + maxNameLength + " characters.");
            return;
        }

        if (description.length() > maxDescriptionLength) {
            System.out.println("Description exceeds maximum length of " + maxDescriptionLength + " characters.");
            errorlabel.setText("Description exceeds maximum length of " + maxDescriptionLength + " characters.");
            return;
        }

        if (priceText.length() > maxPriceLength+1) {
            System.out.println("Price exceeds maximum length of " + maxPriceLength + " characters.");
            errorlabel.setText("Price exceeds maximum length of " + maxPriceLength + " characters.");
            return;
        }

        if (possibleChanges.length() > maxPossibleChangesLength) {
            System.out.println("Possible changes exceed maximum length of " + maxPossibleChangesLength + " characters.");
            errorlabel.setText("Possible changes exceed maximum length of " + maxPossibleChangesLength + " characters.");
            return;
        }

        try {
            // Validate price
            float price = Float.parseFloat(priceText);
            if (price <= 0) {
                System.out.println("Price must be a positive number.");
                errorlabel.setText("Price must be a positive number.");
                return;
            }

            if (possibleChanges.isEmpty()) {
                possibleChanges = "[]"; // Set to empty JSON array
            } else {
                // Convert to JSON format if not already
                if (!possibleChanges.trim().startsWith("[") || !possibleChanges.trim().endsWith("]")) {
                    String[] changesArray = possibleChanges.split(",");
                    StringBuilder jsonFormat = new StringBuilder("[");
                    for (int i = 0; i < changesArray.length; i++) {
                        jsonFormat.append("\"").append(changesArray[i].trim()).append("\"");
                        if (i < changesArray.length - 1) {
                            jsonFormat.append(", ");
                        }
                    }
                    jsonFormat.append("]");
                    possibleChanges = jsonFormat.toString();
                }
            }

            // Check if this is a new item or an update
            if (IDFiled.getText().isEmpty()) {
                // This is a new item, check if the name already exists
                for (MenuItem item : menuItemsList) {
                    if (item.getName().equalsIgnoreCase(name)) {
                        System.out.println("An item with the name '" + name + "' already exists.");
                        errorlabel.setText("An item with the name '" + name + "' already exists.");
                        return;
                    }
                }
                // No duplicate name found, create new item
                MenuItem newItem = new MenuItem(0, menuItemsList.get(0).getRestaurantItamId(), name, description, price, category, possibleChanges);
                BiteOptions newItemUpdate = new BiteOptions(newItem.toString(), BiteOptions.Option.UPDATE_MENU);
                ClientUI.chat.accept(newItemUpdate);
            } else {
                // This is an update to an existing item
                int itemId = Integer.parseInt(IDFiled.getText());

                // Check if the new name already exists in another item
                for (MenuItem item : menuItemsList) {
                    if (item.getName().equalsIgnoreCase(name) && item.getItemId() != itemId) {
                        System.out.println("An item with the name '" + name + "' already exists.");
                        errorlabel.setText("An item with the name '" + name + "' already exists.");
                        return;
                    }
                }

                // Proceed with updating the item
                MenuItem newItem = new MenuItem(itemId, menuItemsList.get(0).getRestaurantItamId(), name, description, price, category, possibleChanges);
                BiteOptions newItemUpdate = new BiteOptions(newItem.toString(), BiteOptions.Option.UPDATE_MENU);
                ClientUI.chat.accept(newItemUpdate);
            }

            // Close the window
            Stage stage = (Stage) nameField.getScene().getWindow();
            stage.close();

        } catch (NumberFormatException e) {
            System.out.println("Invalid number format. Please enter a valid float for price.");
            errorlabel.setText("Price must be a positive number.");
        }
    }

}
