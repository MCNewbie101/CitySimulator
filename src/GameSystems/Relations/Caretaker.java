package GameSystems.Relations;

import Actors.Human;

public class Caretaker extends Familial {
    public Caretaker(Human self, Human person) {
        super(self, person);
    }

    public void update(Human self, int daysPerYear) {
        if (self.getAge().getYears() >= 18) {
            self.getRelations().getCaretakerRelations().remove(this);
            self.getRelations().getFamilyRelations().add(new Familial(self, getPerson(), getCloseness()));
            return;
        }
        int newCloseness = (int) ((-(getAbusivenessFrom() + getAbusivenessTo()) / 2 * Math.random() + Math.random() * 10 - 5) / daysPerYear);
        setCloseness(newCloseness);
        getPerson().getRelations().getCaretakerRelations().get(getPerson().getRelations().getCaretakers().indexOf(self)).setCloseness(newCloseness);
    }
}
