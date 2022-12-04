package game;

public class Coordinates {
    public final int x;
    public final int y;
    public Coordinates(int x, int y){
        this.x = x;
        this.y = y;
    }
    public Boolean isCorrect(){
        return x >= 0 && x <= 7 && y >= 0 && y<=7;
    }
}
