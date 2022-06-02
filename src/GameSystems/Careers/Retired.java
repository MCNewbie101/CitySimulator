package GameSystems.Careers;

public class Retired {
    private double pension;

    public Retired(double salary) {
        this.pension = salary / 10;
    }

    public double getPension() {
        return pension;
    }

    public void setPension(double pension) {
        this.pension = pension;
    }
}
