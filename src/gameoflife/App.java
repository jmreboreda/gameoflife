package gameoflife;

public class App {

    public static void main(String[] args){
        if (args.length != 3 || (!args[0].equals("toro") && !args[0].equals("plano"))) {
            System.out.println("GoL - Parámetros: toro/plano filas columnas");
        } else {
            String gameType = args[0];
            Integer row = Integer.parseInt(args[1]);
            Integer col = Integer.parseInt(args[2]);

            new GameOfLife(gameType, row, col);
        }
    }
}
