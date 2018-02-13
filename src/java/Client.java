import java.io.*;
import java.net.*;
import java.nio.file.*;
import java.util.*;


public class Client {
    private final static int PORT = 13;
    private static final String HOSTNAME = "localhost";
    private FileSplitter _fileSplitter = new FileSplitter();
    private DatagramSocket clientSocket;
    private Scanner inFromUser;
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
        inFromUser = new Scanner(System.in);
        clientSocket = new DatagramSocket();

        IPAddress = InetAddress.getByName(HOSTNAME);

        byte[] sendData = new byte[1024];
        byte[] receiveData = new byte[1024];

        //could try and fix this to be more generic, maybe first file in folder?
        String filePath = "C:\\Users\\Brice\\git\\ics460project1\\src\\fileToSend";

        // convert file to byte[]
        byte[] bFile = _fileSplitter.readBytesFromFile(filePath);

        // save byte[] into a file
        Path path = Paths.get("C:\\Users\\Brice\\git\\ics460project1\\test2");
        Files.write(path, bFile);

        System.out.println("Done");
        System.out.println("Processed File: " + filePath);
        //Print bytes[]
        sendData = bFile.clone();

        //TODO find a way to reliably split a file of unknown size into 12
        //separate "chunks" and have the concatenate at the server
        System.out.println(bFile.length/12);

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
