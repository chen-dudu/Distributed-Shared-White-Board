package client;

import remote.iRemote;
import util.MyObj;
import util.Windows;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class Client {

    private iRemote remote;
    private String position;

    private Windows window;

    private String name;

    public Client(iRemote remote, String position) {
        this.remote = remote;
        this.position = position;
        window = new Windows(null, this, position);

        Timer tOjb = new Timer();
        tOjb.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                try {
                    window.updateCanvas(remote.pull());
                }
                catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        }, 0, 1000);

        Timer tUser = new Timer();
        tUser.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                try {
                    window.updateUsers(remote.getAllUsers());
                }
                catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        }, 0, 500);

        Timer tMsg = new Timer();
        tMsg.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                try {
                    window.updateMsg(remote.getAllMsg());
                }
                catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        }, 0, 500);
    }

    public void notifyNewDraw(List<MyObj> objs) {
        try {
            remote.update(objs);
        }
        catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void notifyClose() {
        try {
            remote.close();
        }
        catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void notifyLeave() {
        try {
            System.out.println(this.name + " is leaving");
            remote.leave(this.name);
        }
        catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public boolean create(String name) {
        try {
            this.name = name;
            return remote.create(name);
        }
        catch (RemoteException e) {
            System.err.println("Unknown error occurred which prevents the system from creating a white board on the server.");
            return false;
        }
    }

    public boolean join(String name) {
        try {
            this.name = name;
            return remote.join(name);
        }
        catch (RemoteException e) {
            System.err.println("Unknown error occurred which prevents the system from adding the user to the white board.");
            return false;
        }
    }

    public boolean getServerStatus() {
        try {
            return remote.isServerRunning();
        }
        catch (RemoteException e) {
            return false;
        }
    }

    public boolean getBoardStatus() {
        try {
            return remote.isBoardReady();
        }
        catch (RemoteException e) {
            return false;
        }
    }

    public void send(String msg) {
        try {
            remote.sendMsg(msg);
        }
        catch (RemoteException e) {
            System.err.println("Failed to send the chat message to the server.");
        }
    }

    public boolean kickOut(String name) {
        try {
            return remote.kickout(name);
        }
        catch (RemoteException e) {
            System.err.println("Failed to kick out user " + name);
            return false;
        }
    }

    public boolean isKickedOut() {
        try {
            boolean result = this.name.equals(remote.getKickedOutUser());
            if (result) {
                remote.resetKickout();
            }
            return result;
        }
        catch (RemoteException e) {
            System.err.println("Failed to get the info for kicked out user.");
            return false;
        }
    }
}
