package Reseau;

import com.mycompany.mavenproject1.Jeu.Plateau.Source;
import com.mycompany.mavenproject1.Partie;
import com.mycompany.mavenproject1.PlateauController;


import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;



public class LancerServeur {

	public static void main(String[] args) throws RemoteException, MalformedURLException {
		Serveur s=new Serveur();
		LocateRegistry.createRegistry(50000);
		System.setProperty( "file.encoding", "UTF-8" );
		System.setProperty("java.security.policy", "./myfile.policy");
		System.setProperty("java.rmi.server.hostname", "127.0.0.1");
		System.setSecurityManager(new SecurityManager());

		Naming.rebind("rmi://127.0.0.1:50000/Server",s);
		System.out.println("[System] Chat remote object is ready");
		InterfacePartie partie = new Partie();
		Source source=new Source();
		Naming.rebind("rmi://127.0.0.1:50000/Source",source);
		partie.initPartie(source);

		Naming.rebind("rmi://127.0.0.1:50000/Partie",partie);
	}
}
