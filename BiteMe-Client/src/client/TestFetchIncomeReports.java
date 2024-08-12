package client;

public class TestFetchIncomeReports {
    public static void main(String[] args) {
        try {
            // Start the client and connect to the server
            ChatClient client = new ChatClient("localhost", 5555, msg -> System.out.println("Client: " + msg));
            client.openConnection();

            // Use a valid branch name that exists in your database
            String branch = "central";

            // Call fetchIncomeReports and print the results
            String incomeReport = client.fetchIncomeReports(branch);
            System.out.println("Income Report for branch " + branch + ":\n" + incomeReport);

            // Close the connection after testing
            client.closeConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}