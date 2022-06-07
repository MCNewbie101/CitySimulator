package GameSystems.Relations;

import Actors.Human;
import Events.RandomEvents;
import GameSystems.Age;

public class Romantic extends CloseRelation {
    private boolean married;
    private Age yearsTogether;

    public Romantic(Human self, Human person) {
        super(self, person);
        yearsTogether = new Age();
    }

    public Romantic(Human self, Human person, boolean married, Age yearsTogether) {
        super(self, person);
        this.married = married;
        this.yearsTogether = yearsTogether;
    }

//    public void update(int daysPerYear) {
//        int newCloseness = (int) ((-(getAbusivenessFrom() + getAbusivenessTo()) / 2 * Math.random() + Math.random() * 10 - 5) / daysPerYear);
//        if (newCloseness < 0) {
//            newCloseness = 0;
//        }
//        setCloseness(newCloseness);
//        getPerson().getRelations().getLover().setCloseness(newCloseness);
//        yearsTogether.update(daysPerYear);
//    }

    public void update(int daysPerYear) {
        int newCloseness = (int) ((-getAbusivenessFrom() * Math.random() + Math.random() * 10 - 5) / daysPerYear);
        if (newCloseness < -100) {
            newCloseness = -100;
        }
        if (getCloseness() < 50) {
            if (Math.random() * 100 * (getSelf().getRelations().getDependentRelations().size() + 1) - 70 < getSelf().getAttributes().getHappiness() * (-getCloseness())) {
                RandomEvents.breakup(this);
            }
        }
        setCloseness(newCloseness);
        yearsTogether.update(daysPerYear);
    }

    public boolean isMarried() {
        return married;
    }

    public void setMarried(boolean married) {
        this.married = married;
    }

    public Age getYearsTogether() {
        return yearsTogether;
    }

    public void setYearsTogether(Age yearsTogether) {
        this.yearsTogether = yearsTogether;
    }
}
