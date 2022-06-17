package GameSystems;

public class Personality {
    private int aggressiveness;
    private int confidence;
    private int sociability;
    private int impulsiveness;
    private int openness;
    private int selfishness;
    private int[] careerPreferences;

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
        selfishness = gen;
        generateCareerPreferences();
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
        generateCareerPreferences();
    }

    public Personality(int aggressiveness, int confidence, int sociability, int impulsiveness, int openness, int selfishness) {
        this.aggressiveness = aggressiveness;
        this.confidence = confidence;
        this.sociability = sociability;
        this.impulsiveness = impulsiveness;
        this.openness = openness;
        this.selfishness = selfishness;
        generateCareerPreferences();
    }

    public void generateCareerPreferences() {
        careerPreferences = new int[10];
        careerPreferences[0] = (int) (confidence + sociability / 2 - Math.random() * 50);
        careerPreferences[1] = (int) (Math.random() * 90 + 10);
        careerPreferences[2] = (int) (Math.random() * 90 + 10);
        careerPreferences[3] = (int) (aggressiveness / 10 + Math.random() * 90);
        careerPreferences[4] = (int) (Math.random() * 70);
        careerPreferences[5] = (int) (Math.random() * 70);
        careerPreferences[6] = (int) (Math.random() * 90 + 10);
        careerPreferences[7] = (int) (Math.random() * 70 + 30);
        careerPreferences[8] = (int) (Math.random() * 80);
        careerPreferences[9] = (int) (Math.random() * 80);
        for (int i = 0; i < careerPreferences.length; i++) {
            if (careerPreferences[i] < 0) {
                careerPreferences[i] = 0;
            }
            if (careerPreferences[i] > 100) {
                careerPreferences[i] = 100;
            }
        }
    }

    public int compatibility(Personality personality) {
        int sum = 100;
        if (personality.getAggressiveness() + aggressiveness > 100) {
            sum -= ((personality.getAggressiveness() + aggressiveness) - 100) / 10;
        }
        if (personality.getSelfishness() + getSelfishness() > 100) {
            sum -= (personality.aggressiveness + aggressiveness) - 100;
        }
        sum -= Math.abs(sociability - personality.getSociability());
        sum -= Math.abs(impulsiveness - personality.getImpulsiveness());
        sum -= Math.abs(openness - personality.getOpenness());
        if (sum < 0) {
            sum = 0;
        }
        if (sum > 100) {
            sum = 100;
        }
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

    public int[] getCareerPreferences() {
        return careerPreferences;
    }

    public void setCareerPreferences(int[] careerPreferences) {
        this.careerPreferences = careerPreferences;
    }
}
