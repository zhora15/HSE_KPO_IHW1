package opponents;

import game.Coordinates;
import game.DiskColor;
import game.Field;
import utils.Analyzer;
import utils.InputOutput;

import java.util.ArrayList;

public class Player implements Opponent{
    private Integer bestScore = 0;
    public Coordinates selectMove(Field f, DiskColor playerColor){
        ArrayList<Coordinates> points = Analyzer.PossibleMoves(f, playerColor);
        f.printField(points);
        Integer move = InputOutput.InputPlayerMove(f, playerColor, points.size());
        if (move == -1){
            if (f.isCancelPossible()){
                f.requestCancel();
                return null;
            }
        }
        return points.get(move);
    }
}
