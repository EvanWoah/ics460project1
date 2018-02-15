import java.io.*;

public class Driver {
    public static void main(String args[]) {
        Client client = new Client();
        Server server = new Server();
        System.out.println("File path: " + new File("fileToSend.txt").getAbsolutePath());
    }
}
