package gameoflife;

public class Cell {

    public static final Boolean LIVE = true;
    public static final Boolean DEAD = false;

    private Boolean live;

    public Boolean getLive() {
        return live;
    }

    public void setLive(Boolean live) {
        this.live = live;
    }
}
