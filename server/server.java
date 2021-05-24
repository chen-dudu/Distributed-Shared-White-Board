package server;

import remote.iRemote;
import util.MyObj;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class server {

    public static void main(String[] args) {
        try {

            List<MyObj> canvasObjs = new ArrayList<>();
            List<String> guests = new ArrayList<>();

            iRemote remoteo = new Remoteo(guests, canvasObjs);

            Registry registry = LocateRegistry.getRegistry();
            // registry.unbind("object");
            registry.rebind("object", remoteo);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
