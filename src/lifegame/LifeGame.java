package lifegame;

import java.awt.*;
import java.util.*;
import java.util.List;

public class LifeGame {

    private Integer boardRow;
    private Integer boardColumn;
    private Board board;
    private Board swapBoard;
    private Integer iteration = 0;

    public LifeGame(Integer boardRow, Integer boardColumn) {
        this.boardRow = boardRow;
        this.boardColumn = boardColumn;
        init();
    }

    public void init(){
        this.board = new Board(boardRow, boardColumn);
        board.initialBoardPlanting(boardRow);
        this.swapBoard = new Board(board.getBoardRow(), board.getBoardColumn());
        swapBoard.initialBoardPlanting(board.getBoardRow());

        iterate();
    }

    private void iterate(){

        while (true) {
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
        for(int row = 0; row < board.getBoardRow(); row++ ){
            for(int col = 0; col < board.getBoardColumn(); col++ ){
                Point point = new Point(row, col);
                Map<String, Integer> neighborsState = computeNeighborsStateAtCell(point);
                computeNewStateAtPoint(point, neighborsState);
            }
        }
        board.setBoard(swapBoard.getBoard());
    }

    private Map<String, Integer> computeNeighborsStateAtCell(Point point){
        List<Point> neighbors = board.getNeighbors(point);

        return board.getNeighborsState(neighbors);
    }

    private void computeNewStateAtPoint(Point point, Map neighbors){
        if(board.getCellState(point) == Cell.LIVE){
            if(neighbors.get("LIVES").equals(2) ||
                    neighbors.get("LIVES").equals(3)){
                swapBoard.setCellState(point, Cell.LIVE);
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
                swapBoard.setCellState(point, Cell.DEAD);
                return;
            }
        }
    }
}
