package Reseau;

import com.mycompany.mavenproject1.Jeu.Canal;
import com.mycompany.mavenproject1.Jeu.Plateau.Source;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.*;

public interface InterfacePartie extends Remote{
	InterfaceServeur getServeur()throws RemoteException;
	void setServeur(InterfaceServeur s)throws RemoteException;

	 void initPartie(Source s) throws RemoteException;

InterfaceTuiles[] getFirstCarte() throws RemoteException;



	public void changerConstructeur(Map<InterfaceClient, Integer> enchere) throws RemoteException;

	ArrayList<InterfaceClient> getListeJoueurs()throws RemoteException;

	 List<InterfaceTuiles> getPile1()throws RemoteException;

	 List<InterfaceTuiles> getPile2()throws RemoteException;

	List<InterfaceTuiles> getPile3()throws RemoteException;

	 List<InterfaceTuiles> getPile4()throws RemoteException;
	 List<InterfaceTuiles> getPile5()throws RemoteException;

	 List<Canal> getListeCanal()throws RemoteException;

	List<Canal> getListeCanalPose()throws RemoteException;
	 List<InterfaceTuiles> getTuilesJoue()throws RemoteException;

}
