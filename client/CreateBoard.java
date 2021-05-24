package client;

import remote.iRemote;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class CreateBoard {

    public static void main(String[] args) {

        if (args.length < 2) {
            System.out.println("Usage: java JoinWhiteBoard <serverIPAddress> <serverPort>");
            return;
        }

        String serverIP = args[0];
        try {
            int serverPort = Integer.parseInt(args[1]);
        }
        catch (NumberFormatException e) {
            System.err.println("Please provide a valid port number.");
            return;
        }
        try {
            Registry registry = LocateRegistry.getRegistry(serverIP);
            iRemote remoteOjb = (iRemote) registry.lookup("object");

            Client client = new Client(remoteOjb, "manager");
        }
        catch (Exception e) {
            System.err.println("System failed to connect to the server.");
        }
    }
}
