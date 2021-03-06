package GameSystems.Relations;

import Actors.Human;

public class Familial extends CloseRelation {
    public Familial(Human self, Human person) {
        super(self, person);
        setCloseness((int) (Math.random() * self.getAttributes().getPersonality().compatibility(person.getAttributes().getPersonality()) + 50));
        if (getCloseness() > 100) {
            setCloseness(100);
        }
    }

    public Familial(Human self, Human person, int closeness, int abusivenessFrom) {
        super(self, person, closeness, abusivenessFrom);
    }

//    public void update(Human self, int daysPerYear) {
//        int newCloseness = (int) ((-(getAbusivenessFrom() + getAbusivenessTo()) / 2 * Math.random() + Math.random() * 10 - 5) / daysPerYear);
//        setCloseness(newCloseness);
//        getPerson().getRelations().getFamilyRelations().get(getPerson().getRelations().getFamily().indexOf(self)).setCloseness(newCloseness);
//    }

    public void update(int daysPerYear) {
        int newCloseness = (int) ((-getAbusivenessFrom() * Math.random() + Math.random() * 10 - 5) / daysPerYear);
        if (newCloseness < -100) {
            newCloseness = -100;
        }
        if (newCloseness > 100) {
            newCloseness = 100;
        }
        changeCloseness(newCloseness);
        if (getAbusivenessFrom() > 50) {
            getSelf().getAttributes().changeTrauma((int) (Math.random() * (getAbusivenessFrom() - 50) / 10 / daysPerYear));
        }
    }
}
