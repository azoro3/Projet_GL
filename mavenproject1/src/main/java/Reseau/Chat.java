/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Reseau;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 *
 * @author Arthur
 */
public class Chat extends UnicastRemoteObject implements Reseau.ChatInterface {
    private String name;
    private Reseau.ChatInterface client = null;
    public Chat(String n) throws RemoteException {
        this.name=n;
    }
    public String getName() throws RemoteException {
        return this.name;
    }
    public void send(String msg) throws RemoteException {
        System.out.println(msg);
    }
    public void setClient(Reseau.ChatInterface c) throws RemoteException {
        client = c;
    }
    public Reseau.ChatInterface getClient() throws RemoteException {
        return client;
    }
}
