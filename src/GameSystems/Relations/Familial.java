package GameSystems.Relations;

import Actors.Human;

public class Familial extends CloseRelation {
    public Familial(Human self, Human person) {
        super(self, person);
        setCloseness((int) (Math.random() * 50 + 50));
    }

    public Familial(Human self, Human person, int closeness, int abusivenessTo, int abusivenessFrom) {
        super(self, person, closeness, abusivenessTo, abusivenessFrom);
    }

//    public void update(Human self, int daysPerYear) {
//        int newCloseness = (int) ((-(getAbusivenessFrom() + getAbusivenessTo()) / 2 * Math.random() + Math.random() * 10 - 5) / daysPerYear);
//        setCloseness(newCloseness);
//        getPerson().getRelations().getFamilyRelations().get(getPerson().getRelations().getFamily().indexOf(self)).setCloseness(newCloseness);
//    }

    public void update(Human self, int daysPerYear) {
        int newCloseness = (int) ((-getAbusivenessFrom() * Math.random() + Math.random() * 10 - 5) / daysPerYear);
        setCloseness(newCloseness);
    }
}
