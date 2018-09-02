package com.rachna;

import com.rachna.ServerImplementation;
import com.rachna.ServerInterface;

import java.io.IOException;
import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.*;

public class Server extends ServerImplementation {

    public static void main(String args[]) throws IOException {
//        System.setProperty("java.rmi.server.hostname","192.168.1.2");

        LocateRegistry.createRegistry(5000);

        System.out.println("Started Registry");

        try {
            ServerImplementation obj = new ServerImplementation();
            ServerInterface stub = (ServerInterface) UnicastRemoteObject.exportObject(obj, 0);
            Registry registry = LocateRegistry.getRegistry(5000);
            registry.bind("ServerInterface", stub);

            System.out.println("Server ready");
        } catch (Exception e) {
            System.err.println("Server exception: " + e.toString());
            e.printStackTrace();
        }
    }
}