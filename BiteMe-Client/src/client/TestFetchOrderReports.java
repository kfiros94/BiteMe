package client;

public class TestFetchOrderReports {
    public static void main(String[] args) {
        try {
            // Start the client and connect to the server
            ChatClient client = new ChatClient("localhost", 5555, msg -> System.out.println("Client: " + msg));
            client.openConnection();

            // Use a valid branch name that exists in your database
            String branch = "central";

            // Call fetchOrderReports and print the results
            String orderSummary = client.fetchOrderReports(branch);
            System.out.println("Order Summary for branch '" + branch + "':");
            System.out.println(orderSummary);

            // Close the connection after testing
            client.closeConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}