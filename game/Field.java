package game;

import utils.Analyzer;

import java.util.ArrayList;
import java.util.Arrays;

public class Field {
    private DiskColor[][] board = new DiskColor[8][8];
    private Boolean requestCancel = Boolean.FALSE;
    private Boolean possibleCancel = Boolean.FALSE;
    public Field(){
        for (DiskColor[] row : board){
            Arrays.fill(row, DiskColor.Empty);
        }
        board[3][3] = DiskColor.White;
        board[4][4] = DiskColor.White;
        board[3][4] = DiskColor.Black;
        board[4][3] = DiskColor.Black;
    }

    public Field(Field source){
        this.board = source.board.clone();
    }

    public void printField (ArrayList<Coordinates> disks){
        System.out.print(" ");
        for (char x = 'a'; x <= 'h'; ++x){
            System.out.print(" " + x);
        }
        System.out.println();
        Integer possibleMove = 0;
        for (int y = 0; y < 8; ++y){
            System.out.print((y+1)+"|");
            for (int x = 0; x < 8; ++x){
                if ((possibleMove<disks.size())&&(disks.get(possibleMove).x == x) && (disks.get(possibleMove).y == y)){
                    System.out.print((possibleMove+1) + "|");
                    ++possibleMove;
                }else if (board[y][x] == DiskColor.Black){
                    System.out.print("B|");
                } else if (board[y][x] == DiskColor.White) {
                    System.out.print("W|");
                } else {
                    System.out.print(" |");
                }
            }
            System.out.println();
        }
    }

    public void printField (){
        System.out.print(" ");
        for (char x = 'a'; x <= 'h'; ++x){
            System.out.print(" " + x);
        }
        System.out.println();
        for (int y = 0; y < 8; ++y){
            System.out.print((y+1)+"|");
            for (int x = 0; x < 8; ++x){
                if (board[y][x] == DiskColor.Black){
                    System.out.print("B|");
                } else if (board[y][x] == DiskColor.White) {
                    System.out.print("W|");
                } else {
                    System.out.print(" |");
                }
            }
            System.out.println();
        }
    }
    public DiskColor getColor(Coordinates disk){
        return board[disk.y][disk.x];
    }
    public Boolean onEdge(Coordinates point){
        return ((point.x == 0) || (point.x == 7) || (point.y == 0) || (point.y == 7));
    }
    public Boolean onCorner(Coordinates point){
        return (((point.x == 0) || (point.x == 7)) && ((point.y == 0) || (point.y == 7)));
    }
    private void invertDisks(ArrayList<Coordinates> points){
        DiskColor invColor = this.getColor(points.get(0)) == DiskColor.White ? DiskColor.Black : DiskColor.White;
        for (Coordinates point : points){
            board[point.y][point.x] = invColor;
        }
    }
    public void makeMove(Coordinates disk, DiskColor playerColor){
        if (this.getColor(disk) == DiskColor.Empty){
            ArrayList<Coordinates> points = Analyzer.getInvertableDisks(this, disk, playerColor);
            board[disk.y][disk.x] = playerColor;
            invertDisks(points);
        }
    }
    public void requestCancel(){
        requestCancel = Boolean.TRUE;
    }
    public Boolean isCancel(){
        return requestCancel;
    }
    public void rejectCancel(){
        requestCancel = Boolean.FALSE;
    }
    public Boolean isCancelPossible(){
        return possibleCancel;
    }
    public void allowCancel(){
        possibleCancel = Boolean.TRUE;
    }
    public void blockCancel(){
        possibleCancel = Boolean.FALSE;
    }
    public void copyBoard(Field f){
        for (int y = 0; y < 8; ++y){
            System.arraycopy(f.board[y], 0, board[y], 0, 8);
        }
    }
}
