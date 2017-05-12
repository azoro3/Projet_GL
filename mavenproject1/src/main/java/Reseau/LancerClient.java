package Reseau;

import com.mycompany.mavenproject1.Jeu.Joueur;
import com.mycompany.mavenproject1.Jeu.Plateau.Source;
import com.mycompany.mavenproject1.Partie;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;


public class LancerClient {
	private InterfaceServeur i;
	private InterfacePartie partie;
	private InterfaceSource source;
	public void run() throws RemoteException, NotBoundException, InterruptedException, MalformedURLException {
		System.setProperty( "file.encoding", "UTF-8" );
		System.setProperty("java.security.policy", "./myfile.policy");
		System.setProperty("java.rmi.server.hostname", "127.0.0.1");
		System.setSecurityManager(new SecurityManager());

		InterfaceServeur server = (InterfaceServeur) Naming.lookup("rmi://127.0.0.1:50000/Server");
		InterfacePartie part=(InterfacePartie) Naming.lookup("rmi://127.0.0.1:50000/Partie");
		InterfaceSource source=(InterfaceSource) Naming.lookup("rmi://127.0.0.1:50000/Source");
		this.source=source;
		this.i=server;
		this.partie=part;
	}
	public InterfaceServeur getServeur(){
		return this.i;
	}
	public InterfacePartie getPartie(){return this.partie;}
	public InterfaceSource getSource(){return this.source;}
}
