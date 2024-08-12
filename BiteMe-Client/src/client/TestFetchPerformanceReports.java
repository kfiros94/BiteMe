package client;

public class TestFetchPerformanceReports {
    public static void main(String[] args) {
        try {
            // Start the client and connect to the server
            ChatClient client = new ChatClient("localhost", 5555, msg -> System.out.println("Client: " + msg));
            client.openConnection();

            // Use a valid branch name that exists in your database
            String branch = "north";

            // Call fetchPerformanceReports and print the results
            
           

            // Close the connection after testing
            client.closeConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
