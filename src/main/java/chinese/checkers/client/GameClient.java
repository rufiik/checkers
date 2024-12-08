package chinese.checkers.client;
import java.io.*;
import java.net.*;
import java.util.Scanner;

public class GameClient {
    private final String host;
    private final int port;
    public GameClient(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public void start() {
        try (Socket socket = new Socket(host, port);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {

            Scanner scanner = new Scanner(System.in);
            System.out.println("Połączono z serwerem!");

            new Thread(() -> {
                try {
                    String serverMessage;
                    while ((serverMessage = in.readLine()) != null) {
                        System.out.println("Serwer: " + serverMessage);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).start();

            while (true) {

                String move = scanner.nextLine();
                out.println(move);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        GameClient client = new GameClient("localhost", 12345);
        client.start();
    }
}