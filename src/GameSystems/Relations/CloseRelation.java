package GameSystems.Relations;

import Actors.Human;

public abstract class CloseRelation extends Relation {
    private int abusivenessTo;
    private int abusivenessFrom;

    public CloseRelation(Human self, Human person) {
        super(self, person);
        abusivenessTo = (int) (Math.random() * person.getAttributes().getPersonality().getAggressiveness());
        abusivenessFrom = (int) (Math.random() * person.getAttributes().getPersonality().getAggressiveness());
        self.getAttributes().getPersonality().compatibility(person.getAttributes().getPersonality());
    }

    public CloseRelation(Human self, Human person, int closeness, int abusivenessTo, int abusivenessFrom) {
        super(self, person, closeness);
        this.abusivenessTo = abusivenessTo;
        this.abusivenessFrom = abusivenessFrom;
    }

    public int getAbusivenessTo() {
        return abusivenessTo;
    }

    public void setAbusivenessTo(int abusivenessTo) {
        this.abusivenessTo = abusivenessTo;
    }

    public int getAbusivenessFrom() {
        return abusivenessFrom;
    }

    public void setAbusivenessFrom(int abusivenessFrom) {
        this.abusivenessFrom = abusivenessFrom;
    }
}
