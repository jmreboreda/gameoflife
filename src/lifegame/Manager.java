package lifegame;

import java.util.*;

public class Manager {

    private final Integer DEAD_FOR_LIVE = 3;

    private Board board;
    private final Integer boardRow = 90;
    private final Integer boardColumn = 90;
    private final Integer initialSeeds = boardRow*boardColumn;
    //private Cell[][] boardCells = new Cell[boardRow][boardColumn];


    public void init(Board board){
        this.board = board;
        board.setBoardRow(boardRow);
        board.setBoardColumn(boardColumn);
        initialPlanting(initialSeeds);
        printBoard();
        try {
            setNextStateOfBoard();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void initialPlanting(Integer initialSeeds){
        Random random = new Random();
        for(Integer i = 0; i < boardRow; i++){
            for(Integer j = 0; j < boardColumn; j++){
                Cell cell = new Cell(i, j);
                cell.setLive(false);
                if(initialSeeds > 0) {
                    int randomNum = random.nextInt(100);
                    if (randomNum > 25 && randomNum < 50) {
                        cell.setLive(true);
                        initialSeeds--;
                    }
                }
                board.setCell(cell);
            }
        }
    }


    private void setNextStateOfBoard() throws InterruptedException {
        for(int i = 1; i < boardRow - 1; i++ ){
            for(int j = 1; j < boardColumn - 1; j++ ){
                Cell cell = new Cell(i, j);
                Map<String, Integer> neighbors = computeNeighbors(board.getCell(cell));
                //Thread.sleep(250);
                System.out.println("Cell ["+ i + "-" +  j + "]" + " --> "+ neighbors);
            }
        }


    }

    private Map<String, Integer> computeNeighbors(Cell cell){
        Map<String,Integer> neighbors = new HashMap<>();
        Integer lives = 0;
        Integer dead = 0;
        Integer x = cell.getPosX();
        Integer y =cell.getPosY();
        for(int i= x-1; i < x + 2; i++ ){
            for(int j= y-1; j < y + 2; j++ ){
                if(i == x && j == y){
                    continue;
                }
                Cell myCell = new Cell(i, j);
                if(board.getCell(myCell).isLive()){
                    lives++;
                }else{
                    dead++;
                }
            }
        }
        neighbors.put("LIVES", lives);
        neighbors.put("DEAD", dead);

        return neighbors;
    }

    private void printBoard(){
        int count = 0;
        String state;
        for(int i = 0; i < boardRow; i++) {
            for (int j = 0; j < boardColumn; j++) {
                Cell myCell = new Cell(i, j);
                if (board.getCell(myCell).isLive()) {
                    state = "*";
                } else {
                    state = "-";
                }
                System.out.print("" + state + "");
                count++;
                if (count == boardRow) {
                    System.out.print("\n");
                    count = 0;
                }
            }
        }
    }
}
