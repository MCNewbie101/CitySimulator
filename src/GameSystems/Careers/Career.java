package GameSystems.Careers;

import Actors.Human;
import Events.RandomEvents;
import GameSystems.Skills.Skills;

public abstract class Career {
    private double base;
    private double salary;
    private int academicRequirement;
    private int salaryGrowth;
    private int satisfaction;
    private int performance;
    private Skills skills;
    private boolean taken;
    private int retirementAge;
    private int careerID;

    public Career() {
        performance = 0;
        taken = false;
    }

    public Career(double base, double salary, int salaryGrowth, int satisfaction, int performance, Skills skills, boolean taken, int retirementAge, int careerID) {
        this.base = base;
        this.salary = salary;
        this.salaryGrowth = salaryGrowth;
        this.satisfaction = satisfaction;
        this.performance = performance;
        this.skills = skills;
        this.taken = taken;
        this.retirementAge = retirementAge;
        this.careerID = careerID;
    }

    public void update(Human human) {
        int temp = this.skills.checkSkills(human.getSkills()) + 10;
        temp += Math.random() * human.getAttributes().getHappiness() + Math.random() * human.getAttributes().getHealth();
        temp /= 300;
        performance += temp;
        if (performance < -10) {
            if (-Math.random() * 100 > performance) {
                RandomEvents.fired(human);
                return;
            }
        }
        if (performance > 0) {
            salary += salaryGrowth * performance;
        }
        if (satisfaction < -10) {
            if (-Math.random() * 100 > satisfaction) {
                if (human.getAttributes().getHappiness() >= 0) {
                    RandomEvents.quitJob(human);
                } else {
                    if (-Math.random() * 100 < human.getAttributes().getHappiness()) {
                        RandomEvents.quitJob(human);
                    }
                }
            }
        }
    }

    public double getBase() {
        return base;
    }

    public void setBase(double base) {
        this.base = base;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public int getAcademicRequirement() {
        return academicRequirement;
    }

    public void setAcademicRequirement(int academicRequirement) {
        this.academicRequirement = academicRequirement;
    }

    public int getSatisfaction() {
        return satisfaction;
    }

    public void setSatisfaction(int satisfaction) {
        this.satisfaction = satisfaction;
    }

    public int getPerformance() {
        return performance;
    }

    public void setPerformance(int performance) {
        this.performance = performance;
    }

    public Skills getSkills() {
        return skills;
    }

    public void setSkills(Skills skills) {
        this.skills = skills;
    }

    public int getSalaryGrowth() {
        return salaryGrowth;
    }

    public void setSalaryGrowth(int salaryGrowth) {
        this.salaryGrowth = salaryGrowth;
    }

    public boolean isTaken() {
        return taken;
    }

    public void setTaken(boolean taken) {
        this.taken = taken;
    }

    public int getRetirementAge() {
        return retirementAge;
    }

    public void setRetirementAge(int retirementAge) {
        this.retirementAge = retirementAge;
    }

    public int getCareerID() {
        return careerID;
    }

    public void setCareerID(int careerID) {
        this.careerID = careerID;
    }

    public void printInfo() {
        System.out.println(salary);
        System.out.println(satisfaction);
        System.out.println(performance);
    }
}
