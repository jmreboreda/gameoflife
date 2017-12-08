package lifegame;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class Board {

    private Integer boardRow;
    private Integer boardColumn;

    public static final Boolean LIVE = true;
    public static final Boolean DEAD = false;

    public final Point CUL;
    public final Point CUR;
    public final Point CBL;
    public final Point CBR;

    private Map<Point, Cell> boardCells = new HashMap<>();

    public Board(Integer boardRow, Integer boardColumn) {
        this.boardRow = boardRow;
        this.boardColumn = boardColumn;
        this.CUL = new Point(0, 0);
        this.CUR = new Point(0, this.getBoardColumn() -1 );
        this.CBL = new Point(this.getBoardRow() - 1, 0 );
        this.CBR = new Point(this.getBoardRow() - 1, this.getBoardColumn() -1 );
        this.fillWithFalse();
    }

    public Integer getBoardRow() {
        return boardRow;
    }

    public void setBoardRow(Integer boardRow) {
        this.boardRow = boardRow;
    }

    public Integer getBoardColumn() {
        return boardColumn;
    }

    public void setBoardColumn(Integer boardColumn) {
        this.boardColumn = boardColumn;
    }

    public Map<Point, Cell> getBoardCells() {
        return boardCells;
    }

    public void setBoardCells(Map<Point, Cell> boardCells) {
        this.boardCells = boardCells;
    }

    public void setCellState(Point point, Boolean state){
        Cell cell = new Cell();
        cell.setLive(state);
        boardCells.put(point, cell);
    }

    public Boolean getCellState(Point point){
        return boardCells.get(point).getLive();

    }

    private void fillWithFalse(){
        for(int i = 0; i < boardRow; i++) {
            for (int j = 0; j < boardColumn; j++) {
                Cell cell = new Cell();
                cell.setLive(false);
                boardCells.put(new Point(i, j), cell);
            }
        }
    }
}
