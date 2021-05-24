package server;

import org.omg.CORBA.PRIVATE_MEMBER;
import remote.iRemote;
import util.MyObj;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Remoteo extends UnicastRemoteObject implements iRemote {

    private List<String> users;
    private List<MyObj> whiteboard;

    public Remoteo(List<String> users, List<MyObj> whiteboard) throws RemoteException {
        this.users = users;
        this.whiteboard = whiteboard;
    }

    @Override
    public List<String> getAllUsers() throws RemoteException {
        return this.users;
    }

    @Override
    public boolean update(List<MyObj> whiteboard) throws RemoteException {
        this.whiteboard = whiteboard;
        return true;
    }

    @Override
    public List<MyObj> pull() throws RemoteException {
        return this.whiteboard;
    }

    @Override
    public boolean join(String name) throws RemoteException {
        if (!this.users.contains(name)) {
            this.users.add(name);
            return true;
        }
        return false;
    }

    @Override
    public boolean kickout(String name) throws RemoteException {
        if (this.users.contains(name)) {
            this.users.remove(name);
            return true;
        }
        return false;
    }
}
