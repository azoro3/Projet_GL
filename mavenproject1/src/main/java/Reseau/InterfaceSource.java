package Reseau;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Created by Bastien on 10/05/2017.
 */
public interface InterfaceSource extends Remote {
    public int rand3(int min, int max) throws RemoteException;



    public String tostring()  throws RemoteException;

    public int getX() throws RemoteException;

    public int getY() throws RemoteException;

}
