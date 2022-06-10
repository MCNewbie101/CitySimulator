package GameSystems.Relations;

import Actors.Human;
import Events.RandomEvents;

import java.util.ArrayList;

public class Platonic extends CloseRelation {
    public Platonic(Human self, Human person) {
        super(self, person);
        setCloseness((int) (Math.random() * self.getAttributes().getPersonality().compatibility(person.getAttributes().getPersonality()) + 30));
        if (getCloseness() > 100) {
            setCloseness(100);
        }
    }

    public Platonic(Human self, Human person, int closeness, int abusivenessFrom) {
        super(self, person, closeness, abusivenessFrom);
    }

//    public void update(Human self, int daysPerYear) {
//        int newCloseness = (int) ((-(getAbusivenessFrom() + getAbusivenessTo()) / 2 * Math.random() + Math.random() * 10 - 5) / daysPerYear);
//        setCloseness(newCloseness);
//        getPerson().getRelations().getFriendships().get(getPerson().getRelations().getFriends().indexOf(self)).setCloseness(newCloseness);
//    }

    public void update(ArrayList<Platonic> remove, int daysPerYear) {
        int newCloseness = (int) ((-getAbusivenessFrom() * Math.random() + Math.random() * 10 - 5) / daysPerYear);
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
        if (getCloseness() < 10 && Math.random() * 100 * (getSelf().getRelations().getDependentRelations().size() + 1) - 70 < getSelf().getAttributes().getHappiness() * (-getCloseness())) {
            RandomEvents.unFriend(remove, this);
        }
        // TODO: Unfriend if closeness too low
    }
}
