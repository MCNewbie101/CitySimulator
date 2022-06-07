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

//    public void update(Human self, int daysPerYear) {
//        int newCloseness = (int) ((-(getAbusivenessFrom() + getAbusivenessTo()) / 2 * Math.random() + Math.random() * 10 - 5) / daysPerYear);
//        setCloseness(newCloseness);
//        getPerson().getRelations().getFriendships().get(getPerson().getRelations().getFriends().indexOf(self)).setCloseness(newCloseness);
//    }

    public void update(int daysPerYear) {
        int newCloseness = (int) ((-getAbusivenessFrom() + getAbusivenessTo() * Math.random() + Math.random() * 10 - 5) / daysPerYear);
        if (newCloseness < -100) {
            newCloseness = -100;
        }
        if (newCloseness > 100) {
            newCloseness = 100;
        }
        setCloseness(newCloseness);
        if (getAbusivenessFrom() > 50) {
            getSelf().getAttributes().changeTrauma((int) (Math.random() * (getAbusivenessFrom() - 50) / 20 / daysPerYear));
        }
        // TODO: Unfriend if closeness too low
    }
}
