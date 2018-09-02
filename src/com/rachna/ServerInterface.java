package com.rachna;


import java.rmi.Remote;
import java.rmi.RemoteException;

// Creating Remote interface for our application
public interface ServerInterface extends Remote {

    public Board getState() throws RemoteException;

   public Result makeMove(int id, int position) throws  RemoteException;

   public int joinGame(int id) throws RemoteException;

   public int pollsecondplayer(int id) throws RemoteException;

    public int waitforturn(int id) throws RemoteException;
}
