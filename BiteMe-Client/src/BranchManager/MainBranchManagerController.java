package BranchManager;

import java.io.IOException;
import java.util.List;

import client.ChatClient;
import entities.RestaurantPerformance;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

public class MainBranchManagerController {

    @FXML
    private Label managerNameLabel;

    @FXML
    private Label branchNameLabel;

    @FXML
    private Button incomeReportsButton; // Ensure this matches the FXML

    @FXML
    private Button orderReportsButton; // Ensure this matches the FXML

    @FXML
    private Button performanceReportsButton; // Ensure this matches the FXML

    @FXML
    private TextArea reportTextArea;

    private ChatClient chatClient;

    @FXML
    public void initialize() {
        // Set the manager name and branch name
        managerNameLabel.setText(ChatClient.user1.getUsername());
        branchNameLabel.setText(ChatClient.user1.getBranch());
    }

    // Method to set the ChatClient instance
    public void setChatClient(ChatClient chatClient) {
        this.chatClient = chatClient;
    }

    @FXML
    public void onIncomeReportsButtonClick() {
        if (chatClient != null) {
            // Run the server request in a background thread
            new Thread(() -> {
                System.out.println("Income Reports Button Clicked");
                System.out.println("Chat client is not null, updating TextArea...");
                
                // Fetch income reports from the server
                String branch = ChatClient.user1.getBranch();
                String incomeReport = chatClient.fetchIncomeReports(branch);

                // Debugging: Print the income report data before updating the TextArea
                System.out.println("Fetched income report: " + incomeReport);
                
                // Ensure incomeReport is not null or empty before updating the TextArea
                if (incomeReport != null && !incomeReport.isEmpty()) {
                    Platform.runLater(() -> {
                        System.out.println("Updating TextArea with the fetched income report data");
                        reportTextArea.setText(incomeReport);
                    });
                } else {
                    Platform.runLater(() -> reportTextArea.setText("No income report data available."));
                }
            }).start();
        } else {
            reportTextArea.setText("Error: No connection to the server.");
        }
    }

    @FXML
    public void onOrderReportsButtonClick() {
        if (chatClient != null) {
            String branch = ChatClient.user1.getBranch();
            String orderReport = chatClient.fetchOrderReports(branch);
            reportTextArea.setText(orderReport);
        } else {
            reportTextArea.setText("Error: No connection to the server.");
        }
    }

    @FXML
    public void onPerformanceReportsButtonClick() {
        if (chatClient != null) {
            new Thread(() -> {
                String branch = ChatClient.user1.getBranch();
                String performanceData = chatClient.fetchPerformanceReports(branch);

                Platform.runLater(() -> {
                    try {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/BranchManager/PerformanceReportPage.fxml"));
                        Parent root = loader.load();

                        PerformanceReportController controller = loader.getController();
                        controller.setPerformanceData(performanceData);

                        Stage stage = new Stage();
                        stage.setTitle("Performance Report");
                        stage.setScene(new Scene(root));
                        stage.show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
            }).start();
        } else {
            reportTextArea.setText("Error: No connection to the server.");
        }
    }
}