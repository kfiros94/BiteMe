package BranchManager;

import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.TextArea;

public class PerformanceReportController {

    @FXML
    private LineChart<String, Number> performanceChart;

    @FXML
    private TextArea reportTextArea;

    public void setPerformanceData(String performanceData) {
        reportTextArea.setText(performanceData);
        loadChart(performanceData);
    }

    private void loadChart(String performanceData) {
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Performance");

        String[] lines = performanceData.split("\n");
        for (String line : lines) {
            if (line.startsWith("Restaurant: ")) {
                String[] parts = line.split(" - ");
                if (parts.length >= 3) {  // Ensure there are enough parts
                    String restaurantName = parts[0].replace("Restaurant: ", "").trim();
                    String status = parts[2].replace("Status: ", "").trim();

                    int score = status.equals("Late") ? 0 : 1;
                    series.getData().add(new XYChart.Data<>(restaurantName, score));
                    System.out.println("Added data to chart: " + restaurantName + " -> " + score);
                } else {
                    System.out.println("Invalid line format: " + line);
                }
            }
        }

        // Clear existing data and add the new series
        performanceChart.getData().clear();
        performanceChart.getData().add(series);

        // Debugging: Print the series data to confirm it's being added
        for (XYChart.Data<String, Number> data : series.getData()) {
            System.out.println("Chart Data: " + data.getXValue() + " -> " + data.getYValue());
        }
    }

    @FXML
    public void onCloseButtonClick() {
        // Handle the close button action, e.g., close the window
        performanceChart.getScene().getWindow().hide();
    }
}