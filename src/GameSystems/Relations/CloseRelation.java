package GameSystems.Relations;

import Actors.Human;

public abstract class CloseRelation{
    private Human self;
    private int closeness;
    private Human person;
    private int abusivenessTo;
    private int abusivenessFrom;

    public CloseRelation(Human self, Human person) {
        this.person = person;
        this.self = self;
        closeness = (int) (Math.random() * 10);
        abusivenessTo = (int) (Math.random() * person.getAttributes().getPersonality().getAggressiveness() * 130 / 100 - 30);
        abusivenessFrom = (int) (Math.random() * person.getAttributes().getPersonality().getAggressiveness() * 130 / 100 - 30);
        self.getAttributes().getPersonality().compatibility(person.getAttributes().getPersonality());
    }

    public CloseRelation(Human self, Human person, int closeness, int abusivenessTo, int abusivenessFrom) {
        this.self = self;
        this.person = person;
        this.closeness = closeness;
        this.abusivenessTo = abusivenessTo;
        this.abusivenessFrom = abusivenessFrom;
    }

    public void changeCloseness(int change) {
        closeness += change;
        if (closeness > 100) {
            closeness = 100;
        }
        if (closeness < -100) {
            closeness = -100;
        }
    }

    public int getCloseness() {
        return closeness;
    }

    public void setCloseness(int closeness) {
        this.closeness = closeness;
    }

    public Human getPerson() {
        return person;
    }

    public void setPerson(Human person) {
        this.person = person;
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

    public Human getSelf() {
        return self;
    }

    public void setSelf(Human self) {
        this.self = self;
    }
}
