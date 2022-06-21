package GameSystems.Skills;

public class Mental {
    private int potential;
    private double programming;
    private double engineering;
    private double math;
    private double chemistry;

    public Mental() {
        potential = (int) (Math.random() * 100);
    }

    public Mental(Mental skills1, Mental skills2) {
        potential = (int) (Math.random() * 20 + (skills1.getPotential() + skills2.getPotential()) / 2 - 10);
        if (potential > 100) {
            potential = 100;
        }
        if (potential < 0) {
            potential = 0;
        }
    }

    public Mental(int m) {
        potential = m;
    }

    public Mental(int potential, double programming, double engineering, double math, double chemistry) {
        this.potential = potential;
        this.programming = programming;
        this.engineering = engineering;
        this.math = math;
        this.chemistry = chemistry;
    }

    public void update(int skillIncreaseBalancing, int daysPerYear) {
        this.engineering += Math.random() * potential / skillIncreaseBalancing / daysPerYear;
        this.programming += Math.random() * potential / skillIncreaseBalancing / daysPerYear;
        this.math += Math.random() * potential / skillIncreaseBalancing / daysPerYear;
        this.chemistry += Math.random() * potential / skillIncreaseBalancing / daysPerYear;
        updateChecker();
    }

    public void update(Mental skills, int skillIncreaseBalancing, int jobSkillIncreaseBalancing, int daysPerYear) {
        this.engineering += skills.getEngineering() * potential / skillIncreaseBalancing / jobSkillIncreaseBalancing / daysPerYear;
        this.programming += skills.getProgramming() * potential / skillIncreaseBalancing / jobSkillIncreaseBalancing / daysPerYear;
        this.math += skills.getMath() * potential / skillIncreaseBalancing / jobSkillIncreaseBalancing / daysPerYear;
        this.chemistry += skills.getChemistry() * potential / skillIncreaseBalancing / jobSkillIncreaseBalancing / daysPerYear;
        updateChecker();
    }

    public void updateChecker() {
        if (engineering > 100) {
            engineering = 100;
        }
        if (programming > 100) {
            programming = 100;
        }
        if (math > 100) {
            math = 100;
        }
        if (chemistry > 100) {
            chemistry = 100;
        }
    }

    public int checkSkill(Mental mental) {
        int sum = 0;
        sum += (int) ((programming - mental.getProgramming()) * mental.getProgramming());
        sum += (int) ((engineering - mental.getEngineering()) * mental.getEngineering());
        sum += (int) ((math - mental.getMath()) * mental.getMath());
        sum += (int) ((chemistry - mental.getChemistry()) * mental.getChemistry());
        return sum;
    }

    public int getPotential() {
        return potential;
    }

    public void setPotential(int potential) {
        this.potential = potential;
    }

    public double getProgramming() {
        return programming;
    }

    public void setProgramming(double programming) {
        this.programming = programming;
    }

    public double getEngineering() {
        return engineering;
    }

    public void setEngineering(double engineering) {
        this.engineering = engineering;
    }

    public double getMath() {
        return math;
    }

    public void setMath(double math) {
        this.math = math;
    }

    public double getChemistry() {
        return chemistry;
    }

    public void setChemistry(double chemistry) {
        this.chemistry = chemistry;
    }

    public void printInfo() {
        System.out.println(potential);
        System.out.println(programming);
        System.out.println(engineering);
        System.out.println(math);
        System.out.println(chemistry);
    }
}
