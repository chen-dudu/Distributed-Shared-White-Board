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

public class client {

    private iRemote remoteObj;
    private Windows w;

    public client(iRemote remoteObj) {
        this.remoteObj = remoteObj;
        try {
            w = new Windows(remoteObj.pull(), this);
        }
        catch (RemoteException e) {
            e.printStackTrace();
        }
        Timer t = new Timer();
        t.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                try {
                    w.updateCanvas(remoteObj.pull());
                }
                catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        }, 0, 2000);
    }

    public void notify(List<MyObj> objs) {
        try {
            remoteObj.update(objs);
        }
        catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}
