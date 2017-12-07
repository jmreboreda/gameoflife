package lifegame;

import java.util.*;

public class Manager {

    private Board board;
    private Integer initialSeeds;
    private Board destiny;


    public void init(Board board){
        this.board = board;
        initialSeeds = board.getBoardRow()*board.getBoardColumn();
        initialPlanting(initialSeeds);
        destiny = new Board(board.getBoardRow(), board.getBoardColumn());
        while(1 < 2) {
            printBoard();
            try {
                setNextStateOfBoard();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void initialPlanting(Integer initialSeeds){
        Random random = new Random();
        for(Integer i = 0; i < board.getBoardRow(); i++){
            for(Integer j = 0; j < board.getBoardColumn(); j++){
                Boolean state = false;
                Cell cell = new Cell();
                cell.setLive(state);
                if(initialSeeds > 0) {
                    int randomNum = random.nextInt(100);
                    if (randomNum > 25 && randomNum < 50) {
                        state = true;
                        cell.setLive(state);
                        initialSeeds--;
                    }
                }
                board.setCell(i, j, state);
            }
        }
    }


    private void setNextStateOfBoard() throws InterruptedException {
        for(int i = 1; i < board.getBoardRow() - 1; i++ ){
            for(int j = 1; j < board.getBoardColumn() - 1; j++ ){
                Cell cell = new Cell();
                Map<String, Integer> neighbors = computeNeighbors(i, j);
                //Thread.sleep(250);
                //System.out.println("Cell ["+ i + "-" +  j + "]" + " --> "+ neighbors);
                computeNewState(i, j, neighbors);
            }
        }
        board.setBoardCells(destiny.getBoardCells());
    }

    private Map<String, Integer> computeNeighbors(Integer X, Integer Y){
        Map<String,Integer> neighbors = new HashMap<>();
        if(X == 0 && Y == 0){

        }
        if(X == 0 && Y > 0){

        }

        Integer lives = 0;
        Integer dead = 0;
        for(int i= X-1; i < X + 2; i++ ){
            for(int j= Y-1; j < Y + 2; j++ ){
                if(i == X && j == Y){
                    continue;
                }
                if(board.getCell(i, j).isLive()){
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

    private void computeNewState(Integer X, Integer Y, Map neighbors){
        if(board.getCell(X, Y).isLive()){
            if(neighbors.get("LIVES").toString().equals("2") ||
                    neighbors.get("LIVES").toString().equals("3")){
                return;
            }else{
                destiny.setCell(X, Y, false);
            }
        }else {
            if (neighbors.get("LIVES").toString().equals("3")) {
                destiny.setCell(X, Y, true);
            } else {
                return;
            }
        }
    }

    private void printBoard(){
        System.out.print("\033[H\033[2J");
        System.out.flush();
        int count = 0;
        String state;
        for(int i = 0; i < board.getBoardRow(); i++) {
            for (int j = 0; j < board.getBoardColumn(); j++) {
                if (board.getCell(i,j).isLive()) {
                    state = "*";
                } else {
                    state = "-";
                }
                System.out.print("" + state + "");
                count++;
                if (count == board.getBoardRow()) {
                    System.out.print("\n");
                    count = 0;
                }
            }
        }
    }
}
