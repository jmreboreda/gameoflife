package lifegame;

import java.awt.*;
import java.util.*;
import java.util.List;

public class LifeGame {

    private Integer boardRow;
    private Integer boardColumn;
    private Board viewBoard;
    private Board swapBoard;
    private Integer iteration = 0;

    public LifeGame(Integer boardRow, Integer boardColumn) {
        this.boardRow = boardRow;
        this.boardColumn = boardColumn;
        init();
    }

    public void init(){
        this.viewBoard = new Board(boardRow, boardColumn);
        viewBoard.initialBoardPlanting(boardRow);
        this.swapBoard = new Board(boardRow, boardColumn);

        iterate();
    }

    private void iterate(){

        while (true) {
            System.out.print("\033[H\033[2J");
            System.out.flush();
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
                computeNewStateAtPoint(point, neighborsState);
            }
        }
        viewBoard.setBoard(swapBoard.getBoard());
    }

    private void computeNewStateAtPoint(Point point, Map neighborsOfThePoint){
        if(viewBoard.getCellState(point) == Cell.LIVE){
            if(neighborsOfThePoint.get("LIVES").equals(2) ||
                    neighborsOfThePoint.get("LIVES").equals(3)){
                swapBoard.setCellState(point, Cell.LIVE);
                return;
            }else{
                swapBoard.setCellState(point, Cell.DEAD);
                return;
            }
        }else if(viewBoard.getCellState(point) == Cell.DEAD) {
            if (neighborsOfThePoint.get("LIVES").equals(3)) {
                swapBoard.setCellState(point, Cell.LIVE);
                return;
            } else {
                swapBoard.setCellState(point, Cell.DEAD);
                return;
            }
        }
    }
}
