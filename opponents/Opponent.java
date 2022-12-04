package opponents;

import game.Coordinates;
import game.DiskColor;
import game.Field;

public interface Opponent {
    public Coordinates selectMove(Field f, DiskColor playerColor);
}
