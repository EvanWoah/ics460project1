import java.util.Scanner;

public class Driver {
	static Scanner clientOrServerInput = new Scanner(System.in);

	public static void main(String args[]) {
		System.out.println("Enter 'client' to run client instance, or 'server' to run server instance. ");
		String cliOrServ = clientOrServerInput.nextLine();
		clientOrServerMethod(cliOrServ);
	}
	// TODO exception handling
	private static void clientOrServerMethod(String cliOrServ) {
	    	try {
	    		switch (cliOrServ) {
	            case "client":
	        			Client client = new Client();
	        			break;
	            case "server":
	            	 	Server server = new Server();
	            	 	break;
	            default:
	            	 	System.out.println("Invlid entry. \nEnter 'client' to run client instance, or 'server' to run server instance. ");
	            	 	String cliOrServAgain = clientOrServerInput.nextLine();
	        			clientOrServerMethod(cliOrServAgain);
	        			break;		
	    		}   			
		}catch (Exception e){
			System.out.println("Invlid entry. \nEnter 'client' to run client instance, or 'server' to run server instance. ");
			String cliOrServAgain = clientOrServerInput.nextLine();    			
			clientOrServerMethod(cliOrServAgain);
		}
	}
	// System.out.println("File path: " + new
	// File("fileToSend.txt").getAbsolutePath());
}
