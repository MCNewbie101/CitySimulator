package GameSystems.Relations;

import Actors.Human;

public class Dependent extends Familial{
    public Dependent(Human self, Human person) {
        super(self, person);
    }

    public Dependent(Human self, Human person, int closeness, int abusivenessTo, int abusivenessFrom) {
        super(self, person, closeness, abusivenessTo, abusivenessFrom);
    }

    public void update(int daysPerYear) {
        if (getPerson().getAge().getYears() >= 18) {
            getSelf().getRelations().getDependentRelations().remove(this);
            getSelf().getRelations().getFamilyRelations().add(new Familial(getSelf(), getPerson(), getCloseness(), getAbusivenessTo(), getAbusivenessFrom()));
            return;
        }
        int newCloseness = (int) ((-getAbusivenessFrom() * Math.random() + Math.random() * 10 - 5) / daysPerYear);
        changeCloseness(newCloseness);
    }
}
