package utils;

import game.Coordinates;
import game.DiskColor;
import game.Field;

import java.util.ArrayList;

public final class Analyzer {
    private Analyzer(){}
    public static ArrayList<Coordinates> PossibleMoves (Field f, DiskColor disk){
        ArrayList<Coordinates> res = new ArrayList<>();
        for (int y = 0; y < 8; ++y){
            for (int x = 0; x < 8; ++x){
                Coordinates point = new Coordinates(x, y);
                if (f.getColor(point) == DiskColor.Empty){
                    if (!getInvertableDisks(f, point, disk).isEmpty()){
                        res.add(point);
                    }
                }
            }
        }
        return res;
    }
    public static ArrayList<Coordinates> getInvertableDisks (Field f, Coordinates point, DiskColor disk){
        ArrayList<Coordinates> res  = new ArrayList<>();
        ArrayList<Coordinates> possibleDisks  = new ArrayList<>();
        Integer[] moves = new Integer[]{-1, 0, 1};
        for (Integer x_move:moves){
            for (Integer y_move:moves){
                Coordinates next_disk = new Coordinates(point.x+x_move, point.y+y_move);
                DiskColor invColor = disk == DiskColor.White ? DiskColor.Black : DiskColor.White;
                while (next_disk.isCorrect() && (f.getColor(next_disk) == invColor)){
                    possibleDisks.add(next_disk);
                    next_disk = new Coordinates(next_disk.x+x_move, next_disk.y+y_move);
                }
                if (next_disk.isCorrect() && f.getColor(next_disk) == disk){
                    res.addAll(possibleDisks);
                } else {
                    possibleDisks.clear();
                }
            }
        }
        return res;
    }
    public static Integer calculateResult (Field f, DiskColor PlayerColor){
        Integer res = 0;
        for (int y = 0; y < 8; ++y) {
            for (int x = 0; x < 8; ++x) {
                if (f.getColor(new Coordinates(x, y)) == PlayerColor) {
                    ++res;
                }
            }
        }
        return res;
    }
}
