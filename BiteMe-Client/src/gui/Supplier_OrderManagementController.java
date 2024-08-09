package gui;

import javafx.fxml.FXML;

import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

import client.ChatClient;
import client.ClientUI;
import entities.BiteOptions;
import entities.ClientInfo;
import entities.Order;
import entities.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;

public class Supplier_OrderManagementController {
    @FXML
    private Button btnBack;
    
    @FXML
    private Button btnSearch;
    
    @FXML
    private TableView<Order> tableView;
    
    @FXML
    private TableColumn<Order, String> orderNumberColumn;
    
    @FXML
    private TableColumn<Order, String> typeColumn;
    
    @FXML
    private TableColumn<Order, String> orderEtaColumn;
    
    @FXML
    private TableColumn<Order, String> phoneNumberColumn;
    
    @FXML
    private TableColumn<Order, String> statusColumn;
    
    @FXML
    private TextField searchField;
    
    @FXML
    private Label titleLabel;
    
    private ObservableList<Order> orderList = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        // Initialize the table columns
        orderNumberColumn.setCellValueFactory(new PropertyValueFactory<>("orderNumber"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        orderEtaColumn.setCellValueFactory(new PropertyValueFactory<>("orderEta"));
        phoneNumberColumn.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
        
        // Load data into the table (example data, replace with actual data loading logic)
        orderList.addAll(
            new Order("123", "Type1", "2024-08-10", "123-456-7890", "Pending"),
            new Order("124", "Type2", "2024-08-12", "123-456-7891", "Completed")
        );
        
        tableView.setItems(orderList);
    }
    
    private String getOrderNumber() {
        return searchField.getText();
    }
    
    public void start(Stage primaryStage) throws Exception {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/Supplier_OrderManagement.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            primaryStage.setTitle("Order Manager");
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error loading FXML file.");
        }
        
        Order order = new Order();
        BiteOptions option = new BiteOptions(order , BiteOptions.Option.RETRIEVE_ORDER_LIST);//kkkkkkk
        ClientUI.chat.accept(option);
    }
    
    @FXML
    public void backButton(ActionEvent event) {
        // Handle logout action
        System.out.println("Logout button clicked");
        // Add your logout handling code here
    }
    
    @FXML
    private void getSearchButton(ActionEvent event) {
    	//TODO//
    }
    
    // Order class for table data
    public static class Order {
        private String orderNumber;
        private String type;
        private String orderEta;
        private String phoneNumber;
        private String status;
        
        public Order() {
        	
        }
        public Order(String orderNumber, String type, String orderEta, String phoneNumber, String status) {
            this.orderNumber = orderNumber;
            this.type = type;
            this.orderEta = orderEta;
            this.phoneNumber = phoneNumber;
            this.status = status;
        }

        public String getOrderNumber() {
            return orderNumber;
        }

        public void setOrderNumber(String orderNumber) {
            this.orderNumber = orderNumber;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getOrderEta() {
            return orderEta;
        }

        public void setOrderEta(String orderEta) {
            this.orderEta = orderEta;
        }

        public String getPhoneNumber() {
            return phoneNumber;
        }

        public void setPhoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }
    }
}
