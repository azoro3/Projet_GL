package Reseau;

import com.mycompany.mavenproject1.Jeu.Canal;
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

	InterfaceClient getMeilleurJoueur()throws RemoteException;
	void setMeilleurJoueur(InterfaceClient ic)throws RemoteException;
	public Map<InterfaceClient, String> getPasse() throws RemoteException;

	public void setPasse(Map<InterfaceClient, String> passe)
			throws RemoteException;
	public Map<InterfaceClient, Integer> getSuivi() throws RemoteException;

	public void setSuivi(Map<InterfaceClient, Integer> suivi) throws RemoteException;

	public Map<InterfaceClient, Canal> getPropositionJoueur() throws RemoteException;

	public void setPropositionJoueur(Map<InterfaceClient, Canal> propositionJoueur) throws RemoteException;

	void passePut(InterfaceClient joueurEnCours, String res)throws RemoteException;

	void putSuivi(InterfaceClient joueurEnCours, int i)throws RemoteException;

	void putPropositionJoueur(InterfaceClient key, Canal res)throws RemoteException;

	int getTour()throws RemoteException;
	void augmentTour()throws RemoteException;
}
