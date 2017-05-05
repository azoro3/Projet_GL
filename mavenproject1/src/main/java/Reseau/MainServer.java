/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Reseau;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.util.Scanner;

/**
 *
 * @author Arthur
 */
public class MainServer {
    public static void main (String args[]) throws RemoteException, MalformedURLException{
        LocateRegistry.createRegistry(50000);
        System.setProperty( "file.encoding", "UTF-8" );
        System.setProperty("java.security.policy", "file:./myfile.policy");
        System.setProperty("java.rmi.server.hostname", "127.0.0.1");
        System.setSecurityManager(new SecurityManager());
        Scanner s = new Scanner(System.in);
        System.out.println("Enter your name and press Enter : ");
        String name = s.nextLine().trim();
        Reseau.Chat server = new Reseau.Chat(name);
        Naming.rebind("rmi://127.0.0.1:50000/ABC",server);
        System.out.println("[System] Chat remote object is ready");
        while(true){
            String msg = s.nextLine().trim();
            if(server.getClient()!=null){
                ChatInterface client = server.getClient();
                msg="["+server.getName()+"] "+msg;
                client.send(msg);
            }
        }
    }

}
