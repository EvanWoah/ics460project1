import java.io.*;
import java.net.*;
import java.util.*;

public class Client {
    private final static int PORT = 13;
    private static final String HOSTNAME = "localhost";

   public Client() {
       Runnable r = new Runnable() {
           @Override
           public void run() {
               runWork();
           }
       };
       Thread t = new Thread(r);
       t.start();
    }
private void runWork() {
    try (DatagramSocket socket = new DatagramSocket()) {
        System.out.print("CLIENT: ");
        Scanner inFromUser = new Scanner(System.in);
        DatagramSocket clientSocket = new DatagramSocket();
        InetAddress IPAddress = InetAddress.getByName(HOSTNAME);
        byte[] sendData = new byte[1024];
        byte[] receiveData = new byte[1024];

        System.out.println("Enter a sentence to send to server: " );
        String sentence = inFromUser.nextLine();

        sendData = sentence.getBytes();
        DatagramPacket sendPacket =
            new DatagramPacket(sendData, sendData.length, IPAddress, 9876);
        clientSocket.send(sendPacket);
        DatagramPacket receivePacket =
            new DatagramPacket(receiveData, receiveData.length);
        clientSocket.receive(receivePacket);
        String modifiedSentence = new String(receivePacket.getData());
        System.out.println("CLIENT: FROM SERVER:" + modifiedSentence);
        clientSocket.close();

    } catch ( IOException ex ) {
        ex.printStackTrace();
    }
}
}
