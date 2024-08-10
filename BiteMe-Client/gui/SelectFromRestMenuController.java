package gui;

import entities.MenuItems;  // Import your existing MenuItems entity class
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;

public class SelectFromRestMenuController {

    @FXML
    private TableView<MenuItems> menuTableView;

    @FXML
    private TableColumn<MenuItems, String> categoryColumn;

    @FXML
    private TableColumn<MenuItems, String> nameColumn;

    @FXML
    private TableColumn<MenuItems, String> changesColumn;  // Changes the type to String to match the entity class

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

    @FXML
    private void initialize() {
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

    private void setupChangesColumn() {
        // Set a custom cell factory to use CheckBoxes in the changesColumn
        changesColumn.setCellFactory(column -> new CheckBoxTableCell());
    }

    @FXML
    private void handleAddItem() {
        MenuItems selectedItem = menuTableView.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            // Add the selected item to the cart
            cartTableView.getItems().add(new CartItem(selectedItem.getName(), selectedItem.getPossible_changes(), selectedItem.getPrice()));
            updateTotalPrice();
        }
    }

    @FXML
    private void handleRemoveItem() {
        CartItem selectedItem = cartTableView.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            cartTableView.getItems().remove(selectedItem);
            updateTotalPrice();
        }
    }

    @FXML
    private void handleBack() {
        // Add logic to go back to the previous screen
    }

    @FXML
    private void handleNext() {
        // Add logic to proceed to the next screen
    }

    private void loadMenuItems() {
        // Example: Load menu items from a database or other source
        // Replace this with actual data retrieval logic.
        menuItemsList = FXCollections.observableArrayList(
            new MenuItems(1, 101, "Pizza", "Cheese Pizza", 12.99, "Main Course", "No onions"),
            new MenuItems(2, 101, "Burger", "Beef Burger", 9.99, "Main Course", "Extra cheese"),
            new MenuItems(3, 101, "Pasta", "Carbonara", 14.99, "Main Course", "No bacon")
        );

        menuTableView.setItems(menuItemsList);
    }

    private void updateTotalPrice() {
        double total = cartTableView.getItems().stream()
                .mapToDouble(CartItem::getPrice)
                .sum();
        totalPriceLabel.setText(String.format("%.2f", total));
    }

    // Custom cell factory to display CheckBoxes in a TableCell
    private static class CheckBoxTableCell extends TableCell<MenuItems, String> {
        private final CheckBox optionA;
        private final CheckBox optionB;
        private final CheckBox optionC;

        private CheckBoxTableCell() {
            optionA = new CheckBox("A");
            optionB = new CheckBox("B");
            optionC = new CheckBox("C");

            HBox hBox = new HBox(optionA, optionB, optionC);
            hBox.setSpacing(10);
            setGraphic(hBox);

            optionA.selectedProperty().addListener((obs, wasSelected, isSelected) -> updateChanges());
            optionB.selectedProperty().addListener((obs, wasSelected, isSelected) -> updateChanges());
            optionC.selectedProperty().addListener((obs, wasSelected, isSelected) -> updateChanges());
        }

        private void updateChanges() {
            if (getTableRow() != null && getTableRow().getItem() != null) {
                StringBuilder changes = new StringBuilder();
                if (optionA.isSelected()) changes.append("Option A ");
                if (optionB.isSelected()) changes.append("Option B ");
                if (optionC.isSelected()) changes.append("Option C");
                getTableRow().getItem().setPossible_changes(changes.toString().trim());
            }
        }

        @Override
        protected void updateItem(String item, boolean empty) {
            super.updateItem(item, empty);
            if (empty || getTableRow() == null || getTableRow().getItem() == null) {
                setGraphic(null);
            } else {
                MenuItems menuItem = getTableRow().getItem();
                optionA.setSelected(menuItem.getPossible_changes().contains("Option A"));
                optionB.setSelected(menuItem.getPossible_changes().contains("Option B"));
                optionC.setSelected(menuItem.getPossible_changes().contains("Option C"));
                setGraphic(new HBox(optionA, optionB, optionC));
            }
        }
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
}
