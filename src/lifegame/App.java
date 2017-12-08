package lifegame;

public class App {

    private static final Integer boardRow = 80;
    private static final Integer boardColumn = 80;

    public static void main(String[] args){
        new LifeGame(boardRow, boardColumn);
    }
}
