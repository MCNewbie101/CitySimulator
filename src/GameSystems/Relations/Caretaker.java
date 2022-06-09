package GameSystems.Relations;

import Actors.Human;

public class Caretaker extends Familial {
    public Caretaker(Human self, Human person) {
        super(self, person);
    }

    public Caretaker(Human self, Human person, int closeness, int abusivenessTo, int abusivenessFrom) {
        super(self, person, closeness, abusivenessTo, abusivenessFrom);
    }

    public void update(int daysPerYear) {
        int newCloseness = (int) ((-getAbusivenessFrom() * Math.random() + Math.random() * 10 - 5) / daysPerYear);
        changeCloseness(newCloseness);
        if (getAbusivenessFrom() > 0) {
            getSelf().getAttributes().changeHealth(-getAbusivenessFrom() * Math.random() / 10);
            getSelf().getAttributes().changeTrauma((int) (getAbusivenessFrom() * Math.random() / 30));
        }
    }
}
