package Reseau;

import com.mycompany.mavenproject1.Partie;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public interface InterfaceServeur extends Remote{
	
	void enregistrer(InterfaceClient i) throws RemoteException;
	void deconnexion(String i) throws RemoteException;
	ArrayList<String> getListeNom() throws RemoteException;
	ArrayList<InterfaceClient> getListeClient() throws RemoteException;
	void setPartie(InterfacePartie  p) throws RemoteException;
	InterfacePartie getPartie()throws RemoteException;
	void setListeClient(ArrayList<InterfaceClient> liste)throws RemoteException;
	public void putEnchere(InterfaceClient ic,Integer i) throws RemoteException;
	public void setEnchere(Map<InterfaceClient,Integer> p) throws RemoteException;
	public Map<InterfaceClient,Integer> getEnchere() throws RemoteException;

    void setListeEncherClient(ArrayList<InterfaceClient> interfaceClients)throws RemoteException;
	ArrayList<InterfaceClient> getListeEncherClient()throws RemoteException;
}
