package GameSystems.Relations;

import Actors.Human;

public class Romantic extends CloseRelation {
    private boolean married;
    private int romance;

    public Romantic(Human self, Human person) {
        super(self, person);
    }

    public Romantic(Human self, Human person, boolean married, int romance) {
        super(self, person);
        this.married = married;
        this.romance = romance;
    }

    public void update(int daysPerYear) {
        int newCloseness = (int) ((-(getAbusivenessFrom() + getAbusivenessTo()) / 2 * Math.random() + Math.random() * 10 - 5) / daysPerYear);
        setCloseness(newCloseness);
        getPerson().getRelations().getLover().setCloseness(newCloseness);
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
}
