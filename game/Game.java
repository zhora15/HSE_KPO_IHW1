package game;

import opponents.*;
import utils.Analyzer;
import utils.InputOutput;

import java.util.Scanner;

public class Game {
    private Field f;
    private Field prev_f;
    private Player player;
    private Opponent opponent;
    private Integer bestScoreBlack = 0;
    private Integer bestScoreWhite = 0;
    private Scanner sc = new Scanner( System.in );
    private OpponentType opponentType;
    private DiskColor currentPlayerColor;

    private void cancelMove(Field f){
        f.copyBoard(prev_f);
        if (opponentType != OpponentType.PVP){
            currentPlayerColor = currentPlayerColor == DiskColor.White ? DiskColor.Black : DiskColor.White;
        }
        f.blockCancel();
        f.rejectCancel();
    }
    private void savePreviousBoard(){
        if(opponentType == OpponentType.PVP){
            prev_f.copyBoard(f);
        } else if (currentPlayerColor == DiskColor.Black){
            prev_f.copyBoard(f);
        }
    }
    private Coordinates selectMove(){
        Coordinates point;
        if (currentPlayerColor == DiskColor.Black){
            point = player.selectMove(f, currentPlayerColor);
        }else{
            point = opponent.selectMove(f, currentPlayerColor);
        }
        return point;
    }
    private void updateBestScores(){
        bestScoreBlack = Integer.max(bestScoreBlack, Analyzer.calculateResult(f, DiskColor.Black));
        bestScoreWhite = Integer.max(bestScoreWhite, Analyzer.calculateResult(f, DiskColor.White));
    }

    private void runtime(){
        Integer flag = 2;
        while(flag != 0){
            if(!Analyzer.PossibleMoves(f, currentPlayerColor).isEmpty()){
                Coordinates point = selectMove();
                if(f.isCancel()){
                    cancelMove(f);
                } else {
                    savePreviousBoard();
                    f.allowCancel();
                    InputOutput.OutputSelectedMove(currentPlayerColor, point);
                    f.makeMove(point, currentPlayerColor);
                    f.printField();
                }
                flag = 2;
            }
            else{
                flag -= 1;
                InputOutput.NoMoves(currentPlayerColor);
            }
            currentPlayerColor = currentPlayerColor == DiskColor.White ? DiskColor.Black : DiskColor.White;
        }
        updateBestScores();
        InputOutput.outputResult(f, bestScoreBlack, bestScoreWhite);
    }
    public void startGame(){
        f = new Field();
        prev_f = new Field();
        player = new Player();
        Boolean flag = Boolean.TRUE;
        InputOutput.outputWelcomeMessage();
        while(flag) {
            f = new Field();
            prev_f.copyBoard(f);
            currentPlayerColor = DiskColor.Black;
            String str = InputOutput.InputGameType();
            switch (str) {
                case "smart" -> {
                    opponentType = OpponentType.SmartComputer;
                    opponent = new SmartOpponent();
                }
                case "base" -> {
                    opponentType = OpponentType.BaseComputer;
                    opponent = new BaseOpponent();
                }
                case "pvp" -> {
                    opponentType = OpponentType.PVP;
                    opponent = new PlayerOpponent();
                }
                case "exit" -> {
                    flag = Boolean.FALSE;
                }
            }
            if (flag){
                runtime();
            }}
    }
}
