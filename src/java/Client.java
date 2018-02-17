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
    @SuppressWarnings("resource")
	private void runWork() {
    		try {
    			clientSocket = new DatagramSocket();
	        IPAddress = InetAddress.getByName(HOSTNAME);
	        receiveData = new byte[packetSize];
	        System.out.println("Relative filepath to file you want client to send to server?");
	        filePath = inFromUser.nextLine();
	        buffer = new byte[packetSize];
	        int packetNum = 0;
	        
	        FileInputStream fis = new FileInputStream(filePath);
	        
	        while(fis.available() != 0) {
	            System.out.println("client- Number of bytes left to send: " + fis.available());
	            try {
	
	               fis.read(buffer, 0, packetSize);
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
}
