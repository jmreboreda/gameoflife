package lifegame;

import java.awt.*;
import java.util.*;
import java.util.List;

public class LifeGame {

    private Integer boardColumn;
    private Integer boardRow;
    private Board board;
    private Board swapBoard;
    private Integer it = 0;

    public LifeGame(Integer boardColumn, Integer boardRow) {
        this.boardColumn = boardColumn;
        this.boardRow = boardRow;
        init();
    }

    public void init(){
        this.board = new Board(boardRow, boardColumn);
        initialBoardFalse();
        this.swapBoard = new Board(board.getBoardColumn(), board.getBoardRow());
        initialBoardPlanting(board.getBoardRow());

        while (1 < 2) {
            System.out.print("\033[H\033[2J");
            System.out.flush();
            it++;
            try {
                printBoard();
                setNextStateOfBoard();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void initialBoardFalse(){
        for(Integer i = 0; i < board.getBoardColumn(); i++){
            for(Integer j = 0; j < board.getBoardRow(); j++){
                board.setCellState(new Point(i, j), false);
            }
        }
    }

    private void initialBoardPlanting(Integer initialSeeds){
        Random random = new Random();
        for(Integer row = 0; row < board.getBoardColumn(); row++){
            for(Integer col = 0; col < board.getBoardRow(); col++){
                if(initialSeeds > 0 && row > board.getBoardColumn()/3) {
                    int randomNum = random.nextInt(100);
                    if (randomNum > 50 && randomNum < 75) {
                        board.setCellState(new Point(row, col), true);
                        initialSeeds--;
                    }
                }
            }
        }
    }

    private void setNextStateOfBoard() throws InterruptedException {
        for(int row = 0; row < board.getBoardColumn() - 1; row++ ){
            for(int col = 0; col < board.getBoardRow() - 1; col++ ){
                Map<String, Integer> neighbors = computeNeighborsAtCell(new Point(row, col));
                //System.out.println("Cell ["+ row + "-" +  col + "]" + " --> "+ neighbors);
                computeNewState(new Point(row, col), neighbors);
            }
        }
        board.setBoardCells(swapBoard.getBoardCells());
    }

    private Map<String, Integer> computeNeighborsAtCell(Point point){
        Map<String,Integer> neighborsState = new HashMap<>();
        Integer lives = 0;
        Integer dead = 0;
        Integer row = point.x;
        Integer col = point.y;

        List<Point> neighbors = new ArrayList<>();
        neighbors.add(new Point(point.x-1, point.y-1));
        neighbors.add(new Point(point.x-1, point.y));
        neighbors.add(new Point(point.x-1, point.y+1));
        neighbors.add(new Point(point.x, point.y-1));
        neighbors.add(new Point(point.x, point.y+1));
        neighbors.add(new Point(point.x+1, point.y-1));
        neighbors.add(new Point(point.x+1, point.y));
        neighbors.add(new Point(point.x+1, point.y+1));

        for(Point myPoint : neighbors){
            if(myPoint.x < 0 || myPoint.x > board.getBoardColumn() || myPoint.y < 0 || myPoint.y > board.getBoardRow()){
                continue;
            }
            if(board.getCellState(myPoint)){
                lives++;
            }else{
                dead++;
            }
        }
        neighborsState.put("LIVES", lives);
        neighborsState.put("DEAD", dead);

        return neighborsState;
    }

    private void computeNewState(Point point, Map neighbors){
        if(board.getCellState(point)){
            if(neighbors.get("LIVES").equals(2) ||
                    neighbors.get("LIVES").equals(3)){
                return;
            }else{
                swapBoard.setCellState(point, false);
                return;
            }
        }else {
            if (neighbors.get("LIVES").equals(3)) {
                swapBoard.setCellState(point, true);
                return;
            } else {
                return;
            }
        }
    }

    private void printBoard() throws InterruptedException {
        int count = 0;
        String state;
        for(int i = 0; i < board.getBoardColumn(); i++) {
            for (int j = 0; j < board.getBoardRow(); j++) {
                if (board.getCellState(new Point(i, j))) {
                    state = "#";
                } else {
                    state = ".";
                }
                System.out.print("" + state + "");
                count++;
                if (count == board.getBoardRow()) {
                    System.out.print("\n");
                    count = 0;
                }
            }
        }
        System.out.println("Iteración: " + it);
        Thread.sleep(125);
    }

    private Map<String, Integer> getCULNeighbors(){
        Integer lives = 0;
        Integer dead = 0;
        Map<String, Integer> neighbors = new HashMap<>();

        if (board.getCellState(new Point(0, 1))){
            lives++;
        }else{
            dead++;
        }
        if (board.getCellState(new Point(1, 0))){
            lives++;
        }else{
            dead++;
        }
        if (board.getCellState(new Point(1, 1))){
            lives++;
        }else{
            dead++;
        }
        if (board.getCellState(new Point(1, 2))){
            lives++;
        }else{
            dead++;
        }
        neighbors.put("LIVES", lives);
        neighbors.put("DEAD", dead);

        return neighbors;
    }

    private Map<String, Integer> getCURNeighbors(){
        Integer lives = 0;
        Integer dead = 0;
        Map<String, Integer> neighbors = new HashMap<>();

        if (board.getCellState(new Point(0, boardColumn - 1))){
            lives++;
        }else{
            dead++;
        }
        if (board.getCellState(new Point(1, boardColumn - 3))){
            lives++;
        }else{
            dead++;
        }
        if (board.getCellState(new Point(1, boardColumn - 2))){
            lives++;
        }else{
            dead++;
        }
        if (board.getCellState(new Point(1, boardColumn - 1))){
            lives++;
        }else{
            dead++;
        }
        neighbors.put("LIVES", lives);
        neighbors.put("DEAD", dead);

        return neighbors;
    }

    private Map<String, Integer> getCBLNeighbors(){
        Integer lives = 0;
        Integer dead = 0;
        Map<String, Integer> neighbors = new HashMap<>();

        if (board.getCellState(new Point(boardRow -1, 1))){
            lives++;
        }else{
            dead++;
        }
        if (board.getCellState(new Point(boardRow -2, 0))){
            lives++;
        }else{
            dead++;
        }
        if (board.getCellState(new Point(boardRow -2, 1))){
            lives++;
        }else{
            dead++;
        }
        if (board.getCellState(new Point(boardRow -2, 2))){
            lives++;
        }else{
            dead++;
        }
        neighbors.put("LIVES", lives);
        neighbors.put("DEAD", dead);

        return neighbors;
    }

    private Map<String, Integer> getCBRNeighbors(){
        Integer lives = 0;
        Integer dead = 0;
        Map<String, Integer> neighbors = new HashMap<>();

        if (board.getCellState(new Point(boardRow -1, boardColumn -1))){
            lives++;
        }else{
            dead++;
        }
        if (board.getCellState(new Point(boardRow -2, boardColumn -3))){
            lives++;
        }else{
            dead++;
        }
        if (board.getCellState(new Point(boardRow -2, boardColumn -2))){
            lives++;
        }else{
            dead++;
        }
        if (board.getCellState(new Point(boardRow -2, boardColumn -1))){
            lives++;
        }else{
            dead++;
        }
        neighbors.put("LIVES", lives);
        neighbors.put("DEAD", dead);

        return neighbors;
    }
}
