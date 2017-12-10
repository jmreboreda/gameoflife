package lifegame;

import java.awt.*;
import java.util.*;
import java.util.List;

public class Board {

    private Map<Point, Cell> board;

    private Integer boardRow;
    private Integer boardColumn;


    public Board(Integer boardRow, Integer boardColumn) {
        this.boardRow = boardRow;
        this.boardColumn = boardColumn;
        board = new HashMap<>(this.getBoardRow(), this.getBoardColumn());
        setAllCellDead();
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

    public Map<Point, Cell> getBoard() {
        return board;
    }

    public void setBoard(Map<Point, Cell> board) {
        for(int i = 0; i < boardRow; i++){
            for(int j = 0; j < boardColumn; j++){
                Cell cell = board.get(new Point(i, j));
                this.setCellState(new Point(i,j), cell.getLive());
            }
        }
    }

    public void setCellState(Point point, Boolean state){
        Cell cell = new Cell();
        cell.setLive(state);
        board.put(point, cell);
    }

    public Boolean getCellState(Point point){
        return board.get(point).getLive();

    }

    public java.util.List<Point> getNeighbors(Point point) {
        List<Point> neighbors = new ArrayList<>();

        List<Point> listPoints = new ArrayList<>();
        listPoints.add(new Point(point.x - 1, point.y - 1));
        listPoints.add(new Point(point.x - 1, point.y));
        listPoints.add(new Point(point.x - 1, point.y + 1));
        listPoints.add(new Point(point.x, point.y - 1));
        listPoints.add(new Point(point.x, point.y + 1));
        listPoints.add(new Point(point.x + 1, point.y - 1));
        listPoints.add(new Point(point.x + 1, point.y));
        listPoints.add(new Point(point.x + 1, point.y + 1));

        // Init limited space






        // end limited space

        // Init toroide
        for (Point myPoint : listPoints) {
            if (myPoint.x < 0){
                myPoint.x = myPoint.x + this.getBoardRow();
            }
            if(myPoint.x > this.getBoardRow() - 1){
                myPoint.x = 0;
            }
            if(myPoint.y < 0){
                myPoint.y =myPoint.y + this.getBoardColumn();
            }
            if(myPoint.y > this.getBoardColumn() - 1) {
                myPoint.y = 0;
            }
            neighbors.add(myPoint);
        }
        // End toroide
        return neighbors;
    }

    public Map<String,Integer> getNeighborsState(List<Point> neighborsOfThePoint){
        Map<String,Integer> neighborsState = new HashMap<>();
        Integer lives = 0;
        Integer dead = 0;

        for(Point myPoint : neighborsOfThePoint){
            if(this.getCellState(myPoint) == Cell.LIVE){
                lives++;
            }else{
                dead++;
            }
        }
        neighborsState.put("LIVES", lives);
        neighborsState.put("DEAD", dead);

        return neighborsState;
    }

    public void setAllCellDead(){
        for(Integer row = 0; row < this.getBoardRow(); row++){
            for(Integer col = 0; col < this.getBoardColumn(); col++){
                this.setCellState(new Point(row, col), Cell.DEAD);
            }
        }
    }

    public void initialBoardPlanting(Integer initialSeeds){
        //r-Pentomino
        this.setCellState(new Point(15, 80), Cell.LIVE);
        this.setCellState(new Point(15, 81), Cell.LIVE);
        this.setCellState(new Point(16, 79), Cell.LIVE);
        this.setCellState(new Point(16, 80), Cell.LIVE);
        this.setCellState(new Point(17, 80), Cell.LIVE);
        // New
//        this.setCellState(new Point(30, 80), Cell.LIVE);
//        this.setCellState(new Point(30, 81), Cell.LIVE);
//        this.setCellState(new Point(31, 79), Cell.LIVE);
//        this.setCellState(new Point(31, 80), Cell.LIVE);
//        this.setCellState(new Point(32, 80), Cell.LIVE);
    }

    public void printBoard(Integer iteration) throws InterruptedException {
        int count = 0;
        int livesCount = 0;
        int deadCount  = 0;

        String character;
        for(int row = 0; row < this.getBoardRow(); row++) {
            for (int col = 0; col < this.getBoardColumn(); col++) {
                if (this.getCellState(new Point(row, col)) == Cell.LIVE) {
                    character = "#";
                    livesCount++;
                } else {
                    character = " ";
                    deadCount++;
                }
                System.out.print(character);
                count++;
                if (count == this.getBoardColumn()) {
                    System.out.print("\n");
                    count = 0;
                }
            }
        }
        System.out.print("Iteración: " + iteration + " [LIVE: " + livesCount + ", DEAD: " + deadCount + "]");
        Thread.sleep(20);
    }
}
