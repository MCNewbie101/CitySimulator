package GameSystems.Relations;

import Actors.Human;

public class Acquaintance extends Relation {
    public Acquaintance(Human self, Human person) {
        super(self, person);
    }

    public Acquaintance(Human self, Human person, int closeness) {
        super(self, person, closeness);
    }
}
