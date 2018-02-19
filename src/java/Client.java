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
    private int packetNum = 1;
    private int totalPackets;
    private int startOffset = 0;

    private FileInputStream fileStreamIn;

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

        createSocket();
        assignIPAddress();

        System.out.println("Relative filepath to file you want client to send to server?");

        //keep attempting
        while(!createFileStream(inFromUser.nextLine()));


        try {
            totalPackets = fileStreamIn.available()/packetSize; //get number of packets to send

            while(fileStreamIn.available() != 0) {
                System.out.println("client- Number of bytes left to send: " + fileStreamIn.available());

                readFileStreamIntoBuffer();

                createClientPacket();

                sendClientPacket();

                packetNum++;

            }
        } catch ( IOException x ) {
            x.printStackTrace();
        }
        clientSocket.close();

    }

    private void createClientPacket() {
        sendPacket = new DatagramPacket(buffer, packetSize, IPAddress, PORT);
    }
    private void readFileStreamIntoBuffer() {
        try {
            fileStreamIn.read(buffer, 0, packetSize);
        } catch ( IOException x ) {
            x.printStackTrace();
        }
    }
    private void sendClientPacket() {
        try {
            System.out.println("client- Sending packet: " + packetNum + "/" + totalPackets);
            System.out.println("client- PACKET_OFFSET: " + startOffset + " - END: "+ (startOffset += packetSize));
            clientSocket.send(sendPacket);

        }catch(IOException io) {
            System.err.println("client- Error in sending packet");
        }
    }
    private boolean createFileStream(String filePath) {
        try {
            fileStreamIn = new FileInputStream(filePath);
            return true;
        } catch ( FileNotFoundException x ) {
            System.err.println("Unable to find file, please try again.");
            return false;
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
