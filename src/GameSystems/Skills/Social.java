package GameSystems.Skills;

public class Social {
    private int potential;
    private double leadership;
    private double communication;
    private double acting;

    public Social() {
        this.potential = (int) (Math.random() * 100);
    }

    public Social(Social skills1, Social skills2) {
        potential = (int) (Math.random() * 20 + (skills1.getPotential() + skills2.getPotential()) / 2 - 10);
        if (potential > 100) {
            potential = 100;
        }
        if (potential < 0) {
            potential = 0;
        }
    }

    public Social(int s) {
        potential = s;
    }

    public Social(int potential, int leadership, int communication, int acting) {
        this.potential = potential;
        this.leadership = leadership;
        this.communication = communication;
        this.acting = acting;
    }

    public void update(int skillIncreaseBalancing, int daysPerYear) {
        this.leadership += potential * Math.random() / skillIncreaseBalancing / daysPerYear;
        this.communication += potential * Math.random() / skillIncreaseBalancing / daysPerYear;
        this.acting += potential * Math.random() / skillIncreaseBalancing / daysPerYear;
        updateChecker();
    }

    public void update(Social social, int skillIncreaseBalancing, int jobSkillIncreaseBalancing, int daysPerYear) {
        this.leadership += potential * social.getLeadership() / skillIncreaseBalancing / jobSkillIncreaseBalancing / daysPerYear;
        this.communication += potential * social.getCommunication() / skillIncreaseBalancing / jobSkillIncreaseBalancing / daysPerYear;
        this.acting += potential * social.getCommunication() / skillIncreaseBalancing / jobSkillIncreaseBalancing / daysPerYear;
        updateChecker();
    }

    public void updateChecker() {
        if (leadership > 100) {
            leadership = 100;
        }
        if (communication > 100) {
            communication = 100;
        }
        if (acting > 100) {
            acting = 100;
        }
    }

    public int checkSkill(Social social) {
        int sum = 0;
        sum += (int) ((leadership - social.getLeadership()) * social.getLeadership());
        sum += (int) ((communication - social.getCommunication()) * social.getCommunication());
        sum += (int) ((acting - social.getActing()) * social.getActing());
        return sum;
    }

    public int getPotential() {
        return potential;
    }

    public void setPotential(int potential) {
        this.potential = potential;
    }

    public double getLeadership() {
        return leadership;
    }

    public void setLeadership(double leadership) {
        this.leadership = leadership;
    }

    public double getCommunication() {
        return communication;
    }

    public void setCommunication(double communication) {
        this.communication = communication;
    }

    public double getActing() {
        return acting;
    }

    public void setActing(double acting) {
        this.acting = acting;
    }

    public void printInfo() {
        System.out.println(potential);
        System.out.println(leadership);
        System.out.println(communication);
        System.out.println(acting);
    }
}
