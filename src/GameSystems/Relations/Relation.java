package GameSystems.Relations;

import Actors.Human;

public abstract class Relation {
    private int closeness;
    private Human person;

    public Relation(Human self, Human person) {
        this.person = person;
    }
    public Relation(Human self, Human person, int closeness) {
        this.person = person;
        this.closeness = closeness;
    }

    public void update(int daysPerYear) {
        closeness -= 10 / daysPerYear;
    }

    public void changeCloseness(int change) {
        closeness += change;
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
}
