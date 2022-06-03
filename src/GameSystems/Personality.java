package GameSystems;

public class Personality {
    private int aggressiveness;
    private int confidence;
    private int sociability;
    private int impulsiveness;
    private int openness;
    private int selfishness;

    public Personality() {
        int gen = (int) (Math.random() * 100);
        while (Math.random() * 10 < 9) {
            gen = (int) (50 + (gen - 50) * 0.95);
        }
        aggressiveness = gen;
        gen = (int) (Math.random() * 100);
        while (Math.random() * 10 < 9) {
            gen = (int) (50 + (gen - 50) * 0.95);
        }
        confidence = gen;
        gen = (int) (Math.random() * 100);
        while (Math.random() * 10 < 9) {
            gen = (int) (50 + (gen - 50) * 0.95);
        }
        sociability = gen;
        gen = (int) (Math.random() * 100);
        while (Math.random() * 10 < 9) {
            gen = (int) (50 + (gen - 50) * 0.95);
        }
        impulsiveness = gen;
        gen = (int) (Math.random() * 100);
        while (Math.random() * 10 < 9) {
            gen = (int) (50 + (gen - 50) * 0.95);
        }
        openness = gen;
        gen = (int) (Math.random() * 100);
        while (Math.random() * 10 < 9) {
            gen = (int) (50 + (gen - 50) * 0.95);
        }
    }

    public Personality(Personality personality1, Personality personality2) {
        aggressiveness = (int) (Math.random() * 50 - 25 + (personality1.getAggressiveness() + personality2.getAggressiveness()) / 2);
        if (aggressiveness > 100) {
            aggressiveness = 100;
        } else if (aggressiveness < 0) {
            aggressiveness = 0;
        }
        confidence = (int) (Math.random() * 50 - 25 + (personality1.getConfidence() + personality2.getConfidence()) / 2);
        if (confidence > 100) {
            confidence = 100;
        } else if (confidence < 0) {
            confidence = 0;
        }
        sociability = (int) (Math.random() * 50 - 25 + (personality1.getSociability() + personality2.getSociability()) / 2);
        if (sociability > 100) {
            sociability = 100;
        } else if (sociability < 0) {
            sociability = 0;
        }
        impulsiveness = (int) (Math.random() * 50 - 25 + (personality1.getImpulsiveness() + personality2.getImpulsiveness()) / 2);
        if (impulsiveness > 100) {
            impulsiveness = 100;
        } else if (impulsiveness < 0) {
            impulsiveness = 0;
        }
        openness = (int) (Math.random() * 50 - 25 + (personality1.getImpulsiveness() + personality2.getImpulsiveness()) / 2);
        if (openness > 100) {
            openness = 100;
        } else if (openness < 0) {
            openness = 0;
        }
        selfishness = (int) (Math.random() * 50 - 25 + (personality1.getImpulsiveness() + personality2.getImpulsiveness()) / 2);
        if (selfishness > 100) {
            selfishness = 100;
        } else if (selfishness < 0) {
            selfishness = 0;
        }
    }

    public int compatibility(Personality personality) {
        int sum = 100;
        if (personality.getAggressiveness() + this.aggressiveness > 100) {
            sum -= (personality.getAggressiveness() + this.aggressiveness) - 100;
        }
        sum -= Math.abs(this.openness - personality.getOpenness());
        return sum;
    }

    public int getAggressiveness() {
        return aggressiveness;
    }

    public void setAggressiveness(int aggressiveness) {
        this.aggressiveness = aggressiveness;
    }

    public int getConfidence() {
        return confidence;
    }

    public void setConfidence(int confidence) {
        this.confidence = confidence;
    }

    public int getSociability() {
        return sociability;
    }

    public void setSociability(int sociability) {
        this.sociability = sociability;
    }

    public int getImpulsiveness() {
        return impulsiveness;
    }

    public void setImpulsiveness(int impulsiveness) {
        this.impulsiveness = impulsiveness;
    }

    public int getOpenness() {
        return openness;
    }

    public void setOpenness(int openness) {
        this.openness = openness;
    }

    public int getSelfishness() {
        return selfishness;
    }

    public void setSelfishness(int selfishness) {
        this.selfishness = selfishness;
    }
}
