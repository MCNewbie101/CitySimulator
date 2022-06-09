package GameSystems.Skills;

public class Creativity {
    private int potential;
    private double musical;
    private double art;
    private double writing;

    public Creativity() {
        this.potential = (int) (Math.random() * 100);
    }

    public Creativity(Creativity skills1, Creativity skills2) {
        potential = (int) (Math.random() * 20 + (skills1.getPotential() + skills2.getPotential()) / 2 - 10);
        if (potential > 100) {
            potential = 100;
        }
        if (potential < 0) {
            potential = 0;
        }
    }

    public Creativity(int potential, double musical, double art, double writing) {
        this.potential = potential;
        this.musical = musical;
        this.art = art;
        this.writing = writing;
    }

    public void update(int skillIncreaseBalancing, int daysPerYear) {
        this.musical += Math.random() * potential / skillIncreaseBalancing / daysPerYear;
        this.art += Math.random() * potential / skillIncreaseBalancing / daysPerYear;
        this.writing += Math.random() * potential / skillIncreaseBalancing / daysPerYear;
    }

    public void update(Creativity creativity, int skillIncreaseBalancing, int daysPerYear, int jobSkillIncreaseBlancing) {
        this.musical += creativity.getMusical() * potential / skillIncreaseBalancing / jobSkillIncreaseBlancing / daysPerYear;
        this.art += creativity.getArt() * potential / skillIncreaseBalancing / jobSkillIncreaseBlancing / daysPerYear;
        this.writing += creativity.getWriting() * potential / skillIncreaseBalancing / jobSkillIncreaseBlancing / daysPerYear;
    }

    public int checkSkill(Creativity creativity) {
        int sum = 0;
        sum += (int) ((musical - creativity.getMusical()) * creativity.getMusical());
        sum += (int) ((art - creativity.getArt()) * creativity.getArt());
        sum += (int) ((writing - creativity.getWriting()) * creativity.getWriting());
        return sum;
    }

    public int getPotential() {
        return potential;
    }

    public void setPotential(int potential) {
        this.potential = potential;
    }

    public double getMusical() {
        return musical;
    }

    public void setMusical(double musical) {
        this.musical = musical;
    }

    public double getArt() {
        return art;
    }

    public void setArt(double art) {
        this.art = art;
    }

    public double getWriting() {
        return writing;
    }

    public void setWriting(double writing) {
        this.writing = writing;
    }

    public void printInfo() {
        System.out.println(potential);
        System.out.println(musical);
        System.out.println(art);
        System.out.println(writing);
    }
}
