package Reseau;

import com.mycompany.mavenproject1.Partie;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JList;

public class Serveur extends UnicastRemoteObject implements InterfaceServeur {

	private JList<String> list;
	private ArrayList<InterfaceClient> tab;
	private JFrame jf;
	private ArrayList<String> tabNom;
	private InterfacePartie partie;
	private Map<InterfaceClient, Integer> enchere;
	public Serveur() throws RemoteException {
		super();
		this.list = new JList();
		tabNom=new ArrayList<String>();
		enchere=new HashMap<>();
		list.setName("liste des personnages connectes : ");
		jf = new JFrame();
		jf.getContentPane().add(list);
		tab = new ArrayList<InterfaceClient>();
		jf.setSize(400, 500);
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jf.setVisible(true);
	}

	public void enregistrer(InterfaceClient i) throws RemoteException {
		tab.add(i);
		String tabNom[] = new String[1000];
		for (int j = 0; j < tab.size(); j++) {
			tabNom[j] = tab.get(j).getNom();
		}
		list.setListData(tabNom);


	}

	public void deconnexion(String i) throws RemoteException {
		String n = i;
		String m;
		for (int j = 0; j < tab.size(); j++) {
			m = tab.get(j).getNom();
			if (n.equals(m)) {

				tab.remove(j);
			}
		}
		String tabNom[] = new String[1000];
		for (int j = 0; j < tab.size(); j++) {
			tabNom[j] = tab.get(j).getNom();
		}
		list.setListData(tabNom);
	}
	
	public ArrayList<String> getListeNom() throws RemoteException{
		for(int j=0;j<tab.size();j++){
			tabNom.add(tab.get(j).getNom());
		}
		return  tabNom;
	}

	public ArrayList<InterfaceClient> getListeClient(){
		return this.tab;
	}
	public void setListeClient(ArrayList<InterfaceClient> liste){
		this.tab=liste;
	}

	public void setPartie(InterfacePartie p) throws RemoteException {
		this.partie=p;
	}


	public InterfacePartie getPartie() throws RemoteException {
		return this.partie;
	}
	public void setEnchere(Map<InterfaceClient,Integer> p) throws RemoteException {
		this.enchere=p;
	}


	public Map<InterfaceClient,Integer> getEnchere() throws RemoteException {
		return this.enchere;
	}
	public void putEnchere(InterfaceClient ic,Integer i) throws RemoteException {
		this.enchere.put(ic,i);
	}
}
