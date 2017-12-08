package lifegame;

import java.awt.*;
import java.util.*;

public class LifeGame {

    private Integer boardRow;
    private Integer boardColumn;
    private Board board;
    private Board swapBoard;
    private Integer it = 0;

    public LifeGame(Integer boardRow, Integer boardColumn) {
        this.boardRow = boardRow;
        this.boardColumn = boardColumn;
        init();
    }

    public void init(){
        this.board = new Board(boardRow, boardColumn);
        this.swapBoard = new Board(board.getBoardRow(), board.getBoardColumn());
        initialBoardPlanting(board.getBoardRow()*10);

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

    private void initialBoardPlanting(Integer initialSeeds){
        Random random = new Random();
        for(Integer row = 0; row < board.getBoardRow(); row++){
            for(Integer col = 0; col < board.getBoardColumn(); col++){
                if(initialSeeds > 0) {
                    int randomNum = random.nextInt(100);
                    if (randomNum > 15 && randomNum < 75) {
                        board.setCellState(new Point(row, col), true);
                        initialSeeds--;
                    }
                }
            }
        }
    }

    private void setNextStateOfBoard() throws InterruptedException {
        for(int row = 1; row < board.getBoardRow() - 1; row++ ){
            for(int col = 1; col < board.getBoardColumn() - 1; col++ ){
                Map<String, Integer> neighbors = computeNeighbors(new Point(row, col));
                //System.out.println("Cell ["+ row + "-" +  col + "]" + " --> "+ neighbors);
                computeNewState(row, col, neighbors);
            }
        }
        board.setBoardCells(swapBoard.getBoardCells());
    }

    private Map<String, Integer> computeNeighbors(Point point){

        Map<String,Integer> neighbors = new HashMap<>();
        Integer lives = 0;
        Integer dead = 0;
        Integer x = point.x;
        Integer y = point.y;

        if(point == board.CUL){
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
        if(point == board.CUR){
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
        if(point == board.CBL){
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
        if(point == board.CBR){
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


        for(int row = point.x-1; row < point.x + 2; row++ ){
            for(int col= point.y-1; col < point.y + 2; col++ ){
                if(row == x && col == y){
                    continue;
                }
                if(board.getCellState(new Point(row, col))){
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

    private void computeNewState(Integer x, Integer y, Map neighbors){
        if(board.getCellState(new Point(x, y))){
            if(neighbors.get("LIVES").equals(2) ||
                    neighbors.get("LIVES").equals(3)){
                return;
            }else{
                swapBoard.setCellState(new Point(x, y), false);
                return;
            }
        }else {
            if (neighbors.get("LIVES").equals(3)) {
                swapBoard.setCellState(new Point(x, y), true);
                return;
            } else {
                return;
            }
        }
    }

    private void printBoard() throws InterruptedException {
        int count = 0;
        String state;
        for(int i = 0; i < board.getBoardRow(); i++) {
            for (int j = 0; j < board.getBoardColumn(); j++) {
                if (board.getCellState(new Point(i, j))) {
                    state = "#";
                } else {
                    state = " ";
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
        Thread.sleep(250);
    }
}
