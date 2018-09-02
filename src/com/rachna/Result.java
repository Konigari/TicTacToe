package com.rachna;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class Result implements Serializable {
    public boolean invalid;
    public String message;
    public Board board;
    public boolean win;

    public Result(boolean invalida, String messagea, Board boarda, boolean b){
        super();
        invalid = invalida;
        message = messagea;
        board = boarda;
        win = b;
    }
}

