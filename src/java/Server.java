import java.io.*;
import java.net.*;
import java.util.logging.*;

public class Server {
	private final static int PORT = 9876;
	private final static Logger audit = Logger.getLogger("requests", null);
	private final static Logger errors = Logger.getLogger("errors", null);

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
	private void runWork() {
	    byte[] receiveData = new byte[1024];
        byte[] sendData = new byte[1024];
        try (DatagramSocket socket = new DatagramSocket(PORT)){
            while (true) {
                try {

                    DatagramPacket request = new DatagramPacket(receiveData, receiveData.length);
                    socket.receive(request);

                    int dataLength = 1024;

                    FileOutputStream fos = new FileOutputStream("receiveFile.jpg");

                    try {
                        fos.write(receiveData,12, dataLength);
                    }catch(IOException e) {
                        System.err.println("Error in writing to file from stream");
                    }

                    //String sentence = new String(request.getData());
                    //System.out.println("SERVER: Packet Received: " + sentence);

                    InetAddress IPAddress = request.getAddress();
                    int port  = request.getPort();
                    /*
                    String upperSentence = sentence.toUpperCase();

                    sendData = upperSentence.getBytes();

                    DatagramPacket response = new DatagramPacket(sendData, sendData.length, IPAddress, port);
                    socket.send(response);
                    */
                }catch(Exception ex){ //TODO, catch specific exception types IOException | RuntimeException
                    errors.log(Level.SEVERE, ex.getMessage(), ex);
                }

            }
        }catch(IOException ex){
                errors.log(Level.SEVERE, ex.getMessage(), ex);

        }
    }
}
