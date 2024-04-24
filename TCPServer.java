import java.io.*;
import java.net.*;

public class TCPServer {
    public static void main(String[] args) {
        try {
            // Create a server socket bound to port 6600
            ServerSocket serverSocket = new ServerSocket(6600);
            System.out.println("Server is started");

            while (true) {
                // Accept incoming client connection
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client connected to server");

                // Handle client connection in a separate thread
                new Thread(new ClientHandler(clientSocket)).start();
            }
        } catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    // Class to handle client connection in a separate thread
    private static class ClientHandler implements Runnable {
        private Socket clientSocket;

        public ClientHandler(Socket clientSocket) {
            this.clientSocket = clientSocket;
        }

        @Override
        public void run() {
            try (
                // Create input and output streams for communication with the client
                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)
            ) {
                String receivedMessage;
                while ((receivedMessage = in.readLine()) != null) {
                    System.out.println("Message from client: " + receivedMessage);

                    // Send response based on the received message
                    String response = receivedMessage.equals("ping") ? "pong" : "Unknown command";
                    out.println(response);
                }
            } catch (IOException e) {
                System.err.println("Error handling client: " + e.getMessage());
            } finally {
                try {
                    clientSocket.close();
                } catch (IOException e) {
                    System.err.println("Error closing client socket: " + e.getMessage());
                }
            }
        }
    }
}
