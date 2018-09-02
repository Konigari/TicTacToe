package com.rachna;

import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class ServerImplementation implements ServerInterface {
    private static final int GAMEINPROGRESS = -1;
    private static final int WAITINGFORPLAYER = 1;
    private static final int PLAYERFOUND = 0;
    Board board;
    char currentPlayer= 'x';
    Result result;
    Map<Integer, Character> playerToId;
    private int playercount = 0;

    public ServerImplementation() {
        try {
            board = new Board();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        playerToId = new HashMap<>();
    }

    @Override
    public Board getState() throws RemoteException {

        return board;
    }

    public Result makeMove(int id, int position) throws RemoteException{
        char player = playerToId.get(id);
        boolean success = board.placeMark(convertPlace(position)[0], convertPlace(position)[1], player);
        if(!success){
            result = new Result(true, "Invalid Move", null,false);
        }
        else{
            boolean win = board.checkForWin();
            result = new Result(false,win?"You Won!":"Successfully placed move",board, win);
            currentPlayer = board.changePlayer(currentPlayer);
        }
        return result;
    }

    @Override
    public int joinGame(int id) throws RemoteException {
        if (playercount==3){
            return GAMEINPROGRESS;
        } else {
            playercount++;
            if (playercount==1){
                playerToId.put(id,((new Random()).nextInt(2)==1)?'x':'o');
            }else{
                if(playerToId.containsValue('x')){
                    playerToId.put(id,'o' );
                }
                else{
                    playerToId.put(id,'x' );

                }
            }
            return playercount;
        }
    }

    @Override
    public int pollsecondplayer(int id) {
        if (playercount==2){
            return PLAYERFOUND;
        } else {
            return WAITINGFORPLAYER;
        }
    }

    @Override
    public int waitforturn(int id) {
        if (currentPlayer == playerToId.get(id)){
            return 0;
        }
        if(board.checkForWin()){
            return 2;
        }
        return 1;
    }

    private int[] convertPlace(int position) {
        position-=1;
        int[] array = {0, 0};
        array[0] = position/3 ;
        array[1] = position%3;
        return array;
    }


}