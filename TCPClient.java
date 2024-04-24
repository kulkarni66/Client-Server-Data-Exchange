import java.io.*;
import java.net.*;

public class TCPClient {
    public static void main(String[] args) {
        try {
            // Connect to the server running on localhost at port 6600
            Socket socket = new Socket("localhost", 6600);
            System.out.println("Connected to server");

            // Create input and output streams for communication with the server
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

            // Continuously send "ping" messages to the server
            while (true) {
                out.println("ping");
                System.out.println("Sent ping to server");

                // Receive and print response from server
                String response = in.readLine();
                if (response != null) {
                    System.out.println("Received response from server: " + response);
                }

                // Wait for 1 second before sending the next ping
                Thread.sleep(1000);
            }
        } catch (IOException | InterruptedException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}
