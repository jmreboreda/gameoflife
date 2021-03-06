package gameoflife;

import java.awt.*;
import java.util.*;
import java.util.List;

public class GameOfLife {

    private Integer boardRow;
    private Integer boardColumn;
    private Board viewBoard;
    private Board swapBoard;
    private String gameType;
    private Integer iteration = 0;

    public GameOfLife(String gameType, Integer boardRow, Integer boardColumn) {
        this.boardRow = boardRow;
        this.boardColumn = boardColumn;
        this.gameType = gameType;
        init();
    }

    public void init(){
        this.viewBoard = new Board(gameType, boardRow, boardColumn);
        viewBoard.initialBoardPlanting(boardRow);
        this.swapBoard = new Board(gameType, boardRow, boardColumn);

        iterate();
    }

    private void iterate(){

        while (true) {
            iteration++;
            try {
                viewBoard.printBoard(iteration);
                setNextStateOfBoard();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void setNextStateOfBoard() {
        for(int row = 0; row < viewBoard.getBoardRow(); row++ ){
            for(int col = 0; col < viewBoard.getBoardColumn(); col++ ){
                Point point = new Point(row, col);
                List<Point> neighbors = viewBoard.getNeighbors(point);
                Map<String, Integer> neighborsState = viewBoard.getNeighborsState(neighbors);
                setNextStateForPoint(point, neighborsState);
            }
        }
        viewBoard.setBoard(swapBoard.getBoard());
    }

    private void setNextStateForPoint(Point point, Map neighborsStateOfPoint){
        if(viewBoard.getCellState(point) == Cell.LIVE){
            if(neighborsStateOfPoint.get("LIVES").equals(2) ||
                    neighborsStateOfPoint.get("LIVES").equals(3)){
                swapBoard.setCellState(point, Cell.LIVE);
                return;
            }else{
                swapBoard.setCellState(point, Cell.DEAD);
                return;
            }
        }else if(viewBoard.getCellState(point) == Cell.DEAD) {
            if (neighborsStateOfPoint.get("LIVES").equals(3)) {
                swapBoard.setCellState(point, Cell.LIVE);
                return;
            } else {
                swapBoard.setCellState(point, Cell.DEAD);
                return;
            }
        }
    }
}
