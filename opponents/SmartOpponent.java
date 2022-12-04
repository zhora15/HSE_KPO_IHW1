package opponents;

import game.Coordinates;
import game.DiskColor;
import game.Field;
import utils.Analyzer;

import java.util.ArrayList;

public class SmartOpponent extends BaseOpponent implements Opponent{
    public Coordinates selectMove(Field f, DiskColor playerColor){
        ArrayList<Coordinates> points = Analyzer.PossibleMoves(f, playerColor);
        Double maxValue = 0.0;
        Coordinates bestPoint = points.get(0);
        Field f_next = new Field();
        for (Coordinates point: points){
            f_next.copyBoard(f);
            f_next.makeMove(point, playerColor);
            DiskColor invertColor = playerColor == DiskColor.White ? DiskColor.Black : DiskColor.White;
            Double maxEnemyPoints = super.calculateR(f_next, super.selectMove(f_next, invertColor), invertColor);
            Double value = super.calculateR(f, point, playerColor) - maxEnemyPoints;
            if (value > maxValue){
                maxValue = value;
                bestPoint = point;
            }
        }
        return bestPoint;
    }
}
