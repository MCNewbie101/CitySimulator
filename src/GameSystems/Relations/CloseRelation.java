package GameSystems.Relations;

import Actors.Human;

public abstract class CloseRelation extends Relation {
    private int abusivenessTo;
    private int abusivenessFrom;

    public CloseRelation(Human self, Human person) {
        super(self, person);
        abusivenessTo = (int) (Math.random() * person.getAttributes().getPersonality().getAggressiveness());
        abusivenessFrom = (int) (Math.random() * person.getAttributes().getPersonality().getAggressiveness());
    }

    public CloseRelation(Human self, Human person, int closeness) {
        super(self, person, closeness);
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
