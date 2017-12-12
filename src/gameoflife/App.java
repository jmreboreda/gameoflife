package gameoflife;

public class App {

    public static void main(String[] args){
        if (args.length != 3 || (!args[0].equals("toro") && !args[0].equals("plano"))) {
            printError("");
        } else {
            String gameType = args[0];
            try {

                Integer row = Integer.parseInt(args[1]);
                Integer col = Integer.parseInt(args[2]);
                new GameOfLife(gameType, row, col);

            }catch(NumberFormatException e){
                printError(e.getMessage());
            }
        }
    }

    private static void printError(String error){
        System.out.println("Error " + error.toLowerCase() + "\n" + "GoL - Parámetros: toro/plano filas columnas");
    }
}
