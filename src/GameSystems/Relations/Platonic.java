package GameSystems.Relations;

import Actors.Human;

public class Platonic extends CloseRelation {
    public Platonic(Human self, Human person) {
        super(self, person);
        setCloseness((int) (Math.random() * 50 + 30));
    }

    public Platonic(Human self, Human person, int closeness, int abusivenessTo, int abusivenessFrom) {
        super(self, person, closeness, abusivenessTo, abusivenessFrom);
    }

    public void update(Human self, int daysPerYear) {
        int newCloseness = (int) ((-(getAbusivenessFrom() + getAbusivenessTo()) / 2 * Math.random() + Math.random() * 10 - 5) / daysPerYear);
        setCloseness(newCloseness);
        getPerson().getRelations().getFriendships().get(getPerson().getRelations().getFriends().indexOf(self)).setCloseness(newCloseness);
    }
}
