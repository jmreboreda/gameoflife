package lifegame;

import java.awt.*;
import java.util.*;
import java.util.List;

public class Board {

    private Integer boardRow;
    private Integer boardColumn;

    private Map<Point, Cell> boardCells = new HashMap<>();

    public Board(Integer boardRow, Integer boardColumn) {
        this.boardRow = boardRow;
        this.boardColumn = boardColumn;
        initialBoardDead();
        fillMapWithDead();
        initialBoardPlanting(boardRow);
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
        for (Point myPoint : listPoints) {
            if (myPoint.x < 0 || myPoint.x > this.getBoardColumn() - 1 || myPoint.y < 0 || myPoint.y > this.getBoardRow() - 1) {
                continue;
            } else {
                neighbors.add(myPoint);
            }
        }
        return neighbors;
    }

    public Map<String,Integer> getNeighborsState(List<Point> neighbors){
        Map<String,Integer> neighborsState = new HashMap<>();
        Integer lives = 0;
        Integer dead = 0;

        for(Point myPoint : neighbors){
            if(this.getCellState(myPoint)){
                lives++;
            }else{
                dead++;
            }
        }
        neighborsState.put("LIVES", lives);
        neighborsState.put("DEAD", dead);

        return neighborsState;
    }

    public void initialBoardDead(){
        for(Integer i = 0; i < this.getBoardColumn(); i++){
            for(Integer j = 0; j < this.getBoardRow(); j++){
                this.setCellState(new Point(i, j), false);
            }
        }
    }

    private void fillMapWithDead(){
        for(int i = 0; i < boardRow; i++) {
            for (int j = 0; j < boardColumn; j++) {
                Cell cell = new Cell();
                cell.setLive(Cell.DEAD);
                boardCells.put(new Point(i, j), cell);
            }
        }
    }

    private void initialBoardPlanting(Integer initialSeeds){
        Random random = new Random();
        for(Integer row = 0; row < this.getBoardColumn(); row++){
            for(Integer col = 0; col < this.getBoardRow(); col++){
                if(initialSeeds > 0 && row > this.getBoardColumn()/3) {
                    int randomNum = random.nextInt(100);
                    if (randomNum > 50 && randomNum < 75) {
                        this.setCellState(new Point(row, col), Cell.LIVE);
                        initialSeeds--;
                    }
                }
            }
        }
    }

    public void printBoard(Integer iteration) throws InterruptedException {
        int count = 0;
        String state;
        for(int i = 0; i < this.getBoardColumn(); i++) {
            for (int j = 0; j < this.getBoardRow(); j++) {
                if (this.getCellState(new Point(i, j))) {
                    state = "#";
                } else {
                    state = ".";
                }
                System.out.print("" + state + "");
                count++;
                if (count == this.getBoardRow()) {
                    System.out.print("\n");
                    count = 0;
                }
            }
        }
        System.out.println("Iteración: " + iteration);
        Thread.sleep(50);
    }
}
