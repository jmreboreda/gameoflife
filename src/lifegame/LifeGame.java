package lifegame;

import java.awt.*;
import java.util.*;
import java.util.List;

public class LifeGame {

    private Integer boardColumn;
    private Integer boardRow;
    private Board board;
    private Board swapBoard;
    private Integer iteration = 0;

    public LifeGame(Integer boardColumn, Integer boardRow) {
        this.boardColumn = boardColumn;
        this.boardRow = boardRow;
        init();
    }

    public void init(){
        this.board = new Board(boardRow, boardColumn);
        this.swapBoard = new Board(board.getBoardColumn(), board.getBoardRow());

        while (1 < 2) {
            System.out.print("\033[H\033[2J");
            System.out.flush();
            iteration++;
            try {
                board.printBoard(iteration);
                setNextStateOfBoard();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void setNextStateOfBoard() {
        for(int row = 0; row < board.getBoardColumn(); row++ ){
            for(int col = 0; col < board.getBoardRow(); col++ ){
                Point point = new Point(row, col);
                Map<String, Integer> neighborsState = computeNeighborsStateAtCell(point);
                computeNewStateAtPoint(point, neighborsState);
            }
        }
        board.setBoardCells(swapBoard.getBoardCells());
    }

    private Map<String, Integer> computeNeighborsStateAtCell(Point point){
        List<Point> neighbors = board.getNeighbors(point);

        return board.getNeighborsState(neighbors);
    }

    private void computeNewStateAtPoint(Point point, Map neighbors){
        if(board.getCellState(point) == Cell.LIVE){
            if(neighbors.get("LIVES").equals(2) ||
                    neighbors.get("LIVES").equals(3)){
                return;
            }else{
                swapBoard.setCellState(point, Cell.DEAD);
                return;
            }
        }else if(board.getCellState(point) == Cell.DEAD) {
            if (neighbors.get("LIVES").equals(3)) {
                swapBoard.setCellState(point, Cell.LIVE);
                return;
            } else {
                return;
            }
        }
    }
}
