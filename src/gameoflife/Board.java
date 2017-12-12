package gameoflife;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Board {

    private Map<Point, Cell> board;

    private Integer boardRow;
    private Integer boardColumn;
    private String gameType;
    private final String os = System.getProperty("os.name").toLowerCase();



    public Board(String gameType, Integer boardRow, Integer boardColumn) {
        this.boardRow = boardRow;
        this.boardColumn = boardColumn;
        this.gameType = gameType;
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

        List<Point> neighborsLocation = new ArrayList<>();
        neighborsLocation.add(new Point(point.x - 1, point.y - 1));
        neighborsLocation.add(new Point(point.x - 1, point.y));
        neighborsLocation.add(new Point(point.x - 1, point.y + 1));
        neighborsLocation.add(new Point(point.x, point.y - 1));
        neighborsLocation.add(new Point(point.x, point.y + 1));
        neighborsLocation.add(new Point(point.x + 1, point.y - 1));
        neighborsLocation.add(new Point(point.x + 1, point.y));
        neighborsLocation.add(new Point(point.x + 1, point.y + 1));

        if(this.gameType.equals("plano")) {
        for (Point myPoint : neighborsLocation) {
            if (myPoint.x < 0 || myPoint.x > this.getBoardRow() - 1
                    || myPoint.y < 0 || myPoint.y > this.getBoardColumn() - 1) {
                continue;
            } else {
                neighbors.add(myPoint);
                }
            }
        } else if(this.gameType.equals("toro")) {
            for (Point myPoint : neighborsLocation) {
                if (myPoint.x < 0) {
                    myPoint.x = myPoint.x + this.getBoardRow();
                }
                if (myPoint.x > this.getBoardRow() - 1) {
                    myPoint.x = 0;
                }
                if (myPoint.y < 0) {
                    myPoint.y = myPoint.y + this.getBoardColumn();
                }
                if (myPoint.y > this.getBoardColumn() - 1) {
                    myPoint.y = 0;
                }
                neighbors.add(myPoint);
            }
        }
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
        Integer x = this.getBoardRow()/2;
        Integer y = this.getBoardColumn()/2;
        this.setCellState(new Point(x, y), Cell.LIVE);
        this.setCellState(new Point(x, y+1), Cell.LIVE);
        this.setCellState(new Point(x+1, y-1), Cell.LIVE);
        this.setCellState(new Point(x+1, y), Cell.LIVE);
        this.setCellState(new Point(x+2, y), Cell.LIVE);
        // New r-Pentomino
//        this.setCellState(new Point(30, 80), Cell.LIVE);
//        this.setCellState(new Point(30, 81), Cell.LIVE);
//        this.setCellState(new Point(31, 79), Cell.LIVE);
//        this.setCellState(new Point(31, 80), Cell.LIVE);
//        this.setCellState(new Point(32, 80), Cell.LIVE);
    }

    public void printBoard(Integer iteration) throws InterruptedException {
        if (os.contains("windows")) {
            try {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else {
            try {
                Runtime.getRuntime().exec("clear");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        int count = 0;
        int livesCount = 0;
        int deadCount  = 0;

        char escCode = 0x1B;
        int iRow = 0; int iColumn = 0;
        System.out.print(String.format("%c[%d;%df",escCode,iRow,iColumn));

        String character;
        for(int row = 0; row < this.getBoardRow(); row++) {
            for (int col = 0; col < this.getBoardColumn(); col++) {
                if (this.getCellState(new Point(row, col)) == Cell.LIVE) {
                    character = "\u25A0";
                    livesCount++;
                } else {
                    character = " ";//"\u00B7";
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
        int row = this.getBoardRow() + 1; int column = 0;
        System.out.print(String.format("%c[%d;%df",escCode,row,column));
        System.out.print("Iteración: " + iteration + " [LIVE: " + livesCount + ", DEAD: " + deadCount + "]");
        Thread.sleep(10);
    }
}
