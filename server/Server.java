package server;

import remote.iRemote;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Server {

    public static void main(String[] args) {
        try {

            iRemote remoteo = new Remoteo();

            Registry registry = LocateRegistry.getRegistry();
            // registry.unbind("object");
            registry.rebind("object", remoteo);
            System.out.println("Server is running normally");
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
