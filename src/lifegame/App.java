package lifegame;

public class App {

    private static final Integer boardRow = 90;
    private static final Integer boardColumn = 90;

    public static void main(String[] args){
        Manager manager = new Manager();
        manager.init(new Board(boardRow, boardColumn));
    }
}
