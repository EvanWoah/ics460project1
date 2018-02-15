import java.io.*;
import java.net.*;
import java.nio.file.*;
import java.util.*;


public class Client {
    private final static int PORT = 13;
    private static final String HOSTNAME = "localhost";
    private FileSplitter _fileSplitter = new FileSplitter();
    private DatagramSocket clientSocket;
    private Scanner inFromUser = new Scanner(System.in);
    private InetAddress IPAddress;

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
        System.out.print("---------------------CLIENT: ");

        clientSocket = new DatagramSocket();

        IPAddress = InetAddress.getByName(HOSTNAME);

        byte[] sendData = new byte[1024];
        byte[] receiveData = new byte[1024];

        //This is going to be passed in through args when starting the program
        String filePath = "MetropolitanStateUniversityLogo.jpg";

        // convert file to byte[]
        byte[] bFile = _fileSplitter.readBytesFromFile(filePath);

        // save byte[] into a file
        Path path = Paths.get("receiveFile");
        Files.write(path, bFile);

        System.out.println("Done");
        System.out.println("Processed File: " + filePath);
        //Print bytes[]
        sendData = bFile.clone();

        //TODO find a way to reliably split a file of unknown size into 12
        //separate "chunks" and have the concatenate at the server
        byte[] buffer = new byte[1036];
        int dataLength;
        FileInputStream fis = new FileInputStream(filePath);

        int packetNum = 0;

        while(fis.available() != 0) {
            try {
                dataLength = fis.read(buffer, 12, 1024);

                DatagramPacket sendPacket =
                    new DatagramPacket(buffer, 1036, IPAddress, PORT);



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
