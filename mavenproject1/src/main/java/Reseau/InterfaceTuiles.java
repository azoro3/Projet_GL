package Reseau;

import com.mycompany.mavenproject1.Jeu.Canal;
import com.mycompany.mavenproject1.Jeu.Joueur;
import com.mycompany.mavenproject1.Jeu.Tuiles;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public interface InterfaceTuiles extends Remote{
	public String toJSON() throws RemoteException;

	public void setX(int x) throws RemoteException;

	public void setY(int y)throws RemoteException;
	public int getX()throws RemoteException;

	public int getY()throws RemoteException;


	public String getType()throws RemoteException;

	public void setType(String type)throws RemoteException;

	public int getNbTravailleurs()throws RemoteException;
	public void setNbTravailleurs(int nbTravailleurs)throws RemoteException;
	public void setIrigue(boolean isIrigue)throws RemoteException;
	public boolean getIrigue()throws RemoteException;

}
