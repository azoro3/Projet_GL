/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Reseau;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Scanner;

/**
 *
 * @author Arthur
 */
public class MainClient {
    public static void main (String args[]) throws RemoteException, NotBoundException, MalformedURLException{
        System.setProperty( "file.encoding", "UTF-8" );
        System.setProperty("java.security.policy", "file:./myfile.policy");
        System.setProperty("java.rmi.server.hostname", "127.0.0.1");
        System.setSecurityManager(new SecurityManager());
        Scanner s = new Scanner(System.in);
        System.out.println("Enter your name and press Enter");
        String name = s.nextLine().trim();
        ChatInterface client = new Chat(name);
        ChatInterface server = (ChatInterface)Naming.lookup("rmi://127.0.0.1:50000/ABC");
        String msg = "["+client.getName()+"] got connected";
        server.send(msg);
        System.out.println("[System] Chat Remote Object is ready:");
        server.setClient(client);
        while(true){
            msg=s.nextLine().trim();
            msg="["+client.getName()+"] "+msg;
            server.send(msg);
        }
    }
}
