import java.io.*;
import java.net.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;


public class Client {
    private final static int PORT = 13;
    private static final String HOSTNAME = "localhost";
    private FileSplitter _fileSplitter = new FileSplitter();
    
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

        System.out.println("Enter a file in src to send to server: " );
        //String filePath = inFromUser.nextLine();
        String filePath = "/Users/evanwall/Desktop/ICS460/proj1/ics460project1/fileToSend.txt";
    
        // convert file to byte[]
        byte[] bFile = _fileSplitter.readBytesFromFile(filePath);

        //java nio
        //byte[] bFile = Files.readAllBytes(new File("C:\\temp\\testing1.txt").toPath());
        //byte[] bFile = Files.readAllBytes(Paths.get("C:\\temp\\testing1.txt"));

        // save byte[] into a file
        Path path = Paths.get("C:\temp\\test2.txt");
        Files.write(path, bFile);

        System.out.println("Done");

        //Print bytes[]
        for (int i = 0; i < bFile.length; i++) {
            sendData = bFile;
            DatagramPacket sendPacket =
                new DatagramPacket(sendData, sendData.length, IPAddress, 9876);
            clientSocket.send(sendPacket);
            DatagramPacket receivePacket =
                new DatagramPacket(receiveData, receiveData.length);
            clientSocket.receive(receivePacket);
            String modifiedSentence = new String(receivePacket.getData());
            System.out.println("CLIENT: FROM SERVER:" + modifiedSentence);
        }
        clientSocket.close();
    } catch ( IOException ex ) {
        ex.printStackTrace();
    }
}
}
