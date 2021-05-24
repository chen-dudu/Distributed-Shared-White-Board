package server;

import remote.iRemote;
import util.MyObj;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.*;

public class Remoteo extends UnicastRemoteObject implements iRemote {

    // username: position(manager or guest)
    private HashMap<String, String> users;
    private List<MyObj> whiteboard;
    private List<String> msg;

    private boolean isRunning;

    private String beingKickedOut = null;

    public Remoteo() throws RemoteException {
        this.users = null;
        this.whiteboard = null;
        this.msg = null;
        this.isRunning = true;
    }

    @Override
    public boolean isServerRunning() throws RemoteException {
        return this.isRunning;
    }

    @Override
    public boolean isBoardReady() throws RemoteException {
        return whiteboard != null;
    }

    @Override
    public List<String> getAllUsers() throws RemoteException {
        return new ArrayList<>(this.users.keySet());
    }

    @Override
    public List<String> getAllMsg() throws RemoteException {
        return this.msg;
    }

    @Override
    public boolean update(List<MyObj> whiteboard) throws RemoteException {
        this.whiteboard = whiteboard;
        return true;
    }

    @Override
    public void sendMsg(String msg) throws RemoteException {
        this.msg.add(msg);
    }

    @Override
    public List<MyObj> pull() throws RemoteException {
        return this.whiteboard;
    }

    @Override
    public boolean create(String name) throws RemoteException {
        this.users = new HashMap<>();
        this.whiteboard = new ArrayList<>();
        this.msg = new ArrayList<>();
        users.put(name, "manager");
        isRunning = true;
        return true;
    }

    @Override
    public boolean join(String name) throws RemoteException {
        if (!this.users.containsKey(name)) {
            this.users.put(name, "guest");
            return true;
        }
        return false;
    }

    @Override
    public boolean kickout(String name) throws RemoteException {
        if (this.users.containsKey(name)) {
            this.users.remove(name);
            this.beingKickedOut = name;
            return true;
        }
        return false;
    }

    @Override
    public String getPosition(String name) throws RemoteException {
        return this.users.get(name);
    }

    @Override
    public void close() throws RemoteException {
        this.whiteboard = null;
    }

    @Override
    public void leave(String name) throws RemoteException {
        users.remove(name);
    }

    @Override
    public String getKickedOutUser() throws RemoteException {
        return this.beingKickedOut;
    }

    @Override
    public void resetKickout() throws RemoteException {
        beingKickedOut = null;
    }
}
