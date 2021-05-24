package remote;

import util.MyObj;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface iRemote extends Remote {
    boolean isServerRunning() throws RemoteException;
    boolean isBoardReady() throws RemoteException;
    List<String> getAllUsers() throws RemoteException;
    List<String> getAllMsg() throws RemoteException;
    boolean update(List<MyObj> whiteboard) throws RemoteException;
    void sendMsg(String msg) throws RemoteException;
    List<MyObj> pull() throws RemoteException;
    boolean create(String name) throws RemoteException;
    boolean join(String name) throws RemoteException;
    boolean kickout(String name) throws RemoteException;
    String getPosition(String name) throws RemoteException;
    void close() throws RemoteException;
    void leave(String name) throws RemoteException;
    String getKickedOutUser() throws RemoteException;
    void resetKickout() throws RemoteException;
}
