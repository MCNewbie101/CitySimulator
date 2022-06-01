package GameSystems.Relations;

import Actors.Human;

public class Professional extends Relation{
    private int impression;

    public Professional(Human self, Human person) {
        super(self, person);
        setCloseness((int) (Math.random() * 10));
        impression = 0;
    }

    public Professional(Human self, Human person, int closeness, int impression) {
        super(self, person, closeness);
        this.impression = impression;
    }

    public int getImpression() {
        return impression;
    }

    public void setImpression(int impression) {
        this.impression = impression;
    }
}
