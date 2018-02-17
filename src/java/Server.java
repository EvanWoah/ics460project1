import java.io.*;
import java.net.*;
import java.util.logging.*;

public class Server {
	private final static int PORT = 9876;
//	private final static Logger audit = Logger.getLogger("requests", null);
	private final static Logger errors = Logger.getLogger("errors", null);
	private DatagramSocket serverSocket;
	private int packetNumber = 0;

	public Server() {
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
	    byte[] receiveData = new byte[1024];

        try {
            serverSocket = new DatagramSocket(PORT);
            FileOutputStream fos = new FileOutputStream("receiveFile.jpg");

            while (true) {
                try {
                    DatagramPacket request = new DatagramPacket(receiveData, receiveData.length);
                    serverSocket.receive(request);                    
                    try {
                    		System.out.println("\n server- PACKET RECEIVED. INFO: \n"
                    				+ "srv- PACKET_NUMBER: " 
                    				+ packetNumber 
                    				+ "\n"
                    				+ "srv- PACKET_LENGTH: " 
                    				+ request.getLength() 
                    				+ "\n" 
                    				+ "srv- PACKET_OFFSET: " 
                    				+ request.getOffset() 
                    				+ "\n"
                    				);
                        fos.write(receiveData, 0, receiveData.length);
                        packetNumber++;
                        
                        // this code is kinda trashy 
                        if (packetNumber == 63) {
                        		System.out.println("Received file written in src's parent directory.");;
                        }
                    }catch(IOException e) {
                        System.err.println("srv- Error in writing to file from stream");
                    }                

                 //   DatagramPacket response = new DatagramPacket(receiveData, receiveData.length);
                 //   serverSocket.send(response);
                    
                }catch(Exception ex){ //TODO, catch specific exception types IOException | RuntimeException
                    errors.log(Level.SEVERE, ex.getMessage(), ex);
                }

            }
        }catch(IOException ex){
                errors.log(Level.SEVERE, ex.getMessage(), ex);

        }
    }
}
