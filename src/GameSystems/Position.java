package GameSystems;

public class Position {
    private int x;
    private int y;

    //TODO: GUI stuff
    //TODO: Earthquakes affect houses at certain positions, illnesses spread through ppl at same positions, etc
    public Position() {

    }

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public double distance(Position position) {
        return Math.sqrt((x - position.getX()) * (x - position.getX()) + (y - position.getY()) * (y - position.getY()));
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}
