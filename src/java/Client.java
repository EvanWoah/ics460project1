import java.io.*;
import java.net.*;
import java.util.*;


public class Client {
    private final static int PORT = 9876;
    private static final String HOSTNAME = "localhost";
    private DatagramSocket clientSocket;
    private Scanner inFromUser = new Scanner(System.in);
    private InetAddress IPAddress;

    private DatagramPacket sendPacket;
    private String filePath;

    private byte[] buffer;
    private byte[] receiveData;

    private int packetSize = 1024;

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
    try {
        clientSocket = new DatagramSocket();
        IPAddress = InetAddress.getByName(HOSTNAME);

        receiveData = new byte[packetSize];

        filePath = "fileToSend.txt";

        buffer = new byte[packetSize];

        FileInputStream fis = new FileInputStream("MetropolitanStateUniversityLogo.jpg");

        int packetNum = 0;

        while(fis.available() != 0) {
            System.out.println("Number of bytes left to send: " + fis.available());
            try {

               fis.read(buffer, 0, packetSize);

               sendPacket = new DatagramPacket(buffer, packetSize, IPAddress, PORT);

                try {
                    System.out.println("Sending packet: " + packetNum);
                    clientSocket.send(sendPacket);

                    packetNum++;
                }catch(IOException io) {
                    System.err.println("Error in sending packet");
                }

            }catch (IOException e) {
                System.err.println("Error in reading file");
            }
        }


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
