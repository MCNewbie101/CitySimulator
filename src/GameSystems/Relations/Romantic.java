package GameSystems.Relations;

import Actors.Human;
import GameSystems.Age;

public class Romantic extends CloseRelation {
    private boolean married;
    private int romance;
    private Age yearsTogether;

    public Romantic(Human self, Human person) {
        super(self, person);
        yearsTogether = new Age();
    }

    public Romantic(Human self, Human person, boolean married, int romance, Age yearsTogether) {
        super(self, person);
        this.married = married;
        this.romance = romance;
        this.yearsTogether = yearsTogether;
    }

    public void update(int daysPerYear) {
        int newCloseness = (int) ((-(getAbusivenessFrom() + getAbusivenessTo()) / 2 * Math.random() + Math.random() * 10 - 5) / daysPerYear);
        setCloseness(newCloseness);
        getPerson().getRelations().getLover().setCloseness(newCloseness);
        yearsTogether.update(daysPerYear);
    }

    public boolean isMarried() {
        return married;
    }

    public void setMarried(boolean married) {
        this.married = married;
    }

    public int getRomance() {
        return romance;
    }

    public void setRomance(int romance) {
        this.romance = romance;
    }

    public Age getYearsTogether() {
        return yearsTogether;
    }

    public void setYearsTogether(Age yearsTogether) {
        this.yearsTogether = yearsTogether;
    }
}
