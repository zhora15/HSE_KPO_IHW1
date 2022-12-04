package opponents;

import game.Coordinates;
import game.DiskColor;
import game.Field;
import utils.Analyzer;

import java.util.ArrayList;

public class BaseOpponent implements Opponent{
    public Coordinates selectMove(Field f, DiskColor playerColor){
        ArrayList<Coordinates> points = Analyzer.PossibleMoves(f, playerColor);
        Double maxValue = 0.0;
        Coordinates bestPoint = null;

        if (points.size() == 0) {
            return null;
        }
        for (Coordinates point: points){
            Double value = calculateR(f, point, playerColor);
            if (value > maxValue){
                maxValue = value;
                bestPoint = point;
            }
        }

        return bestPoint;
    }
    public Double calculateR(Field f, Coordinates point, DiskColor playerColor){
        if (point == null){
            return 0.0;
        }
        ArrayList<Coordinates> disks = Analyzer.getInvertableDisks(f, point, playerColor);
        Double sum = 0.0;
        for (Coordinates disk :disks){
            if (f.onCorner(disk)){
                sum+=2;
            }
            else {
                sum+=1;
            }
        }
        if (f.onCorner(point)){
            sum+=0.8;
        } else if (f.onEdge(point)) {
            sum+=0.4;
        }
        return sum;
    }
}
