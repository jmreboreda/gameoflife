package lifegame;

public class App {

    private static final Integer boardRow = 50;
    private static final Integer boardColumn = 140;

    public static void main(String[] args){
        new LifeGame(boardRow, boardColumn);
    }
}
