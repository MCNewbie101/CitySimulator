package GameSystems.Relations;

import Actors.Human;

public class Dependent extends Familial{
    public Dependent(Human self, Human person) {
        super(self, person);
    }

    public Dependent(Human self, Human person, int closeness, int abusivenessTo, int abusivenessFrom) {
        super(self, person, closeness, abusivenessTo, abusivenessFrom);
    }

    public void update(Human human) {
        for (Caretaker caretaker : getPerson().getRelations().getCaretakerRelations()) {
            if (caretaker.getPerson() == human) {
                setCloseness(caretaker.getCloseness());
                return;
            }
        }
        if (getPerson().getAge().getYears() >= 18) {
            human.getRelations().getDependentRelations().remove(this);
            human.getRelations().getFamilyRelations().add(new Familial(human, getPerson(), getCloseness(), getAbusivenessTo(), getAbusivenessFrom()));
        }
    }
}
