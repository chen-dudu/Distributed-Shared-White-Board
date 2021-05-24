package client;

import remote.iRemote;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class create {

    public static void main(String[] args) {
        try {
            Registry registry = LocateRegistry.getRegistry("localhost");
            iRemote remoteObj = (iRemote) registry.lookup("object");

            client c = new client(remoteObj);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
