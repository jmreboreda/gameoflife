package lifegame;

public class App {

    private static final Integer boardRow = 60;
    private static final Integer boardColumn = 60;

    public static void main(String[] args){
        new LifeGame(boardRow, boardColumn);
    }
}
