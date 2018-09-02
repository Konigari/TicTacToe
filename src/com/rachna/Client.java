package com.rachna;

import java.rmi.AccessException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.io.*;
import java.util.*;
import java.text.*;

public class Client {
    private static final int WAITINGFOROPPONENT = 1;
    private static DataInputStream input;

    public static void main(String args[]) throws AccessException, InterruptedException, NotBoundException, RemoteException {
        Random rand = new Random();
        int id = rand.nextInt();
        input = new DataInputStream(System.in);

        Registry registry = LocateRegistry.getRegistry(5000);
        ServerInterface stub = (ServerInterface) registry.lookup("ServerInterface");
        int status = stub.joinGame(id);
        while(true){
            System.out.println("Welcome Player ");

            while (status == WAITINGFOROPPONENT){
                System.out.println("Waiting for a Partner");
                status = stub.pollsecondplayer(id);
                Thread.sleep(2000);
            }
            System.out.println("Found Partner");
            if(status == -1){
                System.out.println("Game In Progress, Quitting...");
                break;
            }
            boolean gameloop = true;
            System.out.println("Starting Game");

            while(gameloop == true){
                int x;
                while(!((x = stub.waitforturn(id)) == 0)){
                    System.out.println("Waiting For Opponent's Move");

                    if(x == 1){
                        Thread.sleep(10000);
                    }
                    if(x == 2){
                        System.out.print("You Lost");
                    }
                }

                int position = ask();
                Result result = stub.makeMove(id, position);
                while(result.invalid){
                    System.out.print(result.message);
                    position = ask();
                    result = stub.makeMove(id, position);
                }
                if(result.win){
                    result.board.print();
                    System.out.print("Waiting for a Partner");

                }
                result.board.print();
                System.out.print(result.message + "\n");

                gameloop = !result.win;
            }

        }

    }

    private static int ask() {
        System.out.print("Waiting for your move: >> ");
        String action = null;
        try {
            action = input.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Integer.parseInt(action);
    }
}
