package com.rachna;

public class Result {
    public boolean invalid;
    public String message;
    public Board board;
    public boolean win;

    public Result(boolean invalida, String messagea, Board boarda, boolean b) {
        invalid = invalida;
        message = messagea;
        board = boarda;
        win = b;
    }
//
//    public void printResult() {
//        system.out().print("");
//    }

}

