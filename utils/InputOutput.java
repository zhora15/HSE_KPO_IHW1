package utils;

import game.Coordinates;
import game.DiskColor;
import game.Field;
import opponents.OpponentType;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class InputOutput {
    private static final Scanner sc = new Scanner( System.in );
    private static String GetPlayerColor(DiskColor playerColor){
        String res = "";
        switch (playerColor){
            case Black:
                res = "Black";
                break;
            case White:
                res = "White";
                break;
        }
        return res;
    }

    public static void outputWelcomeMessage(){
        System.out.println("It's a reversi game. Type \"exit\" to leave game or \"cancel\" to return move back.");
    }
    public static void NoMoves(DiskColor playerColor){
        System.out.println("No possible moves for " + GetPlayerColor(playerColor));
    }
    public static void OutputSelectedMove(DiskColor playerColor, Coordinates point){
        System.out.print(GetPlayerColor(playerColor));
        System.out.print(" at ");
        System.out.print((char)('A'+point.x));
        System.out.println(point.y+1);
    }
    public static Integer InputPlayerMove(Field f, DiskColor playerColor, int maxv) {
        System.out.print("Enter number that match possible move: ");
        Integer move = -1;
        String str;
        while (move == -1) {
            str = sc.nextLine();
            if (str.equals("cancel")){
                if (f.isCancelPossible()){
                    return -1;
                } else {
                    System.out.println("It's not possible to reverse move");
                }
            }
            try {
               move = Integer.parseInt(str);
            } catch (NumberFormatException nfe) {
                System.out.print("Enter correct number that match possible move: ");
            }
            if (move > maxv){
                System.out.print("Enter correct number that match possible move: ");
                move = -1;
            }
        }
        return move - 1;
    }
    public static String InputGameType(){
        OpponentType res = OpponentType.BaseComputer;
        Boolean isCorrect = Boolean.FALSE;
        List<String> correctInputs = Arrays.asList("smart", "base", "pvp", "exit");
        String str = null;
        while (!isCorrect){
            isCorrect = Boolean.TRUE;
            System.out.println("Select opponent type: smart/base/pvp");
            str = sc.nextLine().toLowerCase();
            if (!correctInputs.contains(str)){
                isCorrect = Boolean.FALSE;
            }
        }
        return str;
    }
    public static void outputResult(Field f, Integer bestScoreBlack, Integer bestScoreWhite){
        Integer blackScore = Analyzer.calculateResult(f, DiskColor.Black);
        Integer whiteScore = Analyzer.calculateResult(f, DiskColor.White);
        if (blackScore > whiteScore){
            System.out.println("Black won");
        } else if (blackScore.equals(whiteScore)){
            System.out.println("Draw");
        } else {
            System.out.println("White won");
        }
        System.out.println("Black score: " + blackScore);
        System.out.println("White score: " + whiteScore);
        System.out.println();

        System.out.println("Best Black score: " + bestScoreBlack);
        System.out.println("Best White score: " + bestScoreWhite);
    }
}
