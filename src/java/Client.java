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

    private int packetSize = 1024;
    private byte[] buffer = new byte[packetSize];
    private byte[] receiveData = new byte[packetSize];


    private FileInputStream fileStream;

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
    @SuppressWarnings("resource")
    private void runWork() {

        createSocket();
        assignIPAddress();

        try {

            System.out.println("Relative filepath to file you want client to send to server?");
            createFileStream(inFromUser.nextLine());

            int packetNum = 0;



            while(fileStream.available() != 0) {
                System.out.println("client- Number of bytes left to send: " + fileStream.available());
                try {

                    fileStream.read(buffer, 0, packetSize);
                    sendPacket = new DatagramPacket(buffer, packetSize, IPAddress, PORT);
                    try {
                        System.out.println("client- Sending packet: " + packetNum);
                        clientSocket.send(sendPacket);
                        packetNum++;

                    }catch(IOException io) {
                        System.err.println("client- Error in sending packet");
                    }
                }catch (IOException e) {
                    System.err.println("client- Error in reading file");
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
    private void createFileStream(String filePath) {
        try {
            FileInputStream fis = new FileInputStream(filePath);
        } catch ( FileNotFoundException x ) {
            x.printStackTrace();
        }
    }
    private void assignIPAddress() {
        try {
            IPAddress = InetAddress.getByName(HOSTNAME);
        } catch ( UnknownHostException x ) {
            x.printStackTrace();
        }
    }
    private void createSocket() {
        try {
            clientSocket = new DatagramSocket();
        } catch ( SocketException x ) {
            x.printStackTrace();
        }
    }
}
