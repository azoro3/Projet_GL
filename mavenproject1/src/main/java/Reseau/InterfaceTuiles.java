package Reseau;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface InterfaceTuiles extends Remote{
	public String toJSON() throws RemoteException;

	public void setX(double x) throws RemoteException;

	public void setY(double y)throws RemoteException;
	public double getX()throws RemoteException;

	public double getY()throws RemoteException;


	public String getType()throws RemoteException;

	public void setType(String type)throws RemoteException;

	public int getNbTravailleurs()throws RemoteException;
	public void setNbTravailleurs(int nbTravailleurs)throws RemoteException;
	public void setIrigue(boolean isIrigue)throws RemoteException;
	public boolean getIrigue()throws RemoteException;

	int getNum()throws RemoteException;
	void setNum(int i) throws RemoteException;
}
