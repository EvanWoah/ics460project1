import java.util.*;

public class Driver {
	static Scanner clientOrServerInput = new Scanner(System.in);

	public static void main(String args[]) {
	    //Server server = new Server();
	    //Client client = new Client();
		System.out.println("Enter 'client' to run client instance, or 'server' to run server instance. ");
		String cliOrServ = clientOrServerInput.nextLine();
		clientOrServerMethod(cliOrServ);
	}
	// TODO exception handling

	private static void clientOrServerMethod(String cliOrServ) {
	    while(!cliOrServ.toLowerCase().equals("client") || !cliOrServ.toLowerCase().equals("server")) {
	    		switch (cliOrServ) {
	    		case "client":
	        			Client client = new Client();
	        			return;
	            case "server":
	            	 	Server server = new Server();
	            	 	return;
	            default:
	            	 	System.out.println("Invalid entry. \nEnter 'client' to run client instance, or 'server' to run server instance. ");
	            	 	cliOrServ = clientOrServerInput.nextLine();
	        			break;
	    		}
	    }
	}
}
