package lifegame;

public class App {

    private static final Integer boardRow = 55;
    private static final Integer boardColumn = 160;

    public static void main(String[] args){
        new LifeGame(boardRow, boardColumn);
    }
}
