package lifegame;

public class Board {

    private Integer boardRow;
    private Integer boardColumn;
    private Cell[][] boardCells;

    public Board(Integer boardRow, Integer boardColumn) {
        this.boardRow = boardRow;
        this.boardColumn = boardColumn;
        this.boardCells = new Cell[boardRow][boardColumn];
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

    public Cell[][] getBoardCells() {
        return boardCells;
    }

    public void setBoardCells(Cell[][] boardCells) {
        this.boardCells = boardCells;
    }

    public void setCell(Cell cell){
        boardCells[cell.getPosX()][cell.getPosY()] = cell;
    }

    public Cell getCell(Cell cell){
        return boardCells[cell.getPosX()][cell.getPosY()];
    }
}
