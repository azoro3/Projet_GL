package Reseau;

import com.mycompany.mavenproject1.Jeu.Canal;
import com.mycompany.mavenproject1.Partie;
import com.mycompany.mavenproject1.PlateauController;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface InterfaceClient extends Remote{


	public String getNom()throws RemoteException;
	public InterfaceServeur getServeur()throws RemoteException;
	public void setServeur(InterfaceServeur se)throws RemoteException;

	public void setNom(String nom)throws RemoteException;

	public String getCouleur()throws RemoteException;

	public void setCouleur(String couleur)throws RemoteException;

	public Canal getCanal()throws RemoteException;

	public void setCanal(Canal canal)throws RemoteException;

	public int getSolde()throws RemoteException;

	public void setSolde(int solde)throws RemoteException;

	public int getTravailleurs()throws RemoteException;

	public void setTravailleurs(int travailleurs)throws RemoteException;

	public boolean isEstConstructeur()throws RemoteException;

	public void setEstConstructeur(boolean estConstructeur)throws RemoteException;

	public String toJSON()throws RemoteException;



}
