package remote;

import util.MyObj;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface iRemote extends Remote {
    List<String> getAllUsers() throws RemoteException;
    boolean update(List<MyObj> whiteboard) throws RemoteException;
    List<MyObj> pull() throws RemoteException;
    boolean join(String name) throws RemoteException;
    boolean kickout(String name) throws RemoteException;
}
