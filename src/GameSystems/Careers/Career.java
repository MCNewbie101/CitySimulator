package GameSystems.Careers;

import Actors.Human;
import Events.RandomEvents;
import GameSystems.Attributes;
import GameSystems.Skills.Skills;

public abstract class Career {
    private double salary;
    private int salaryGrowth;
    private int satisfaction;
    private int performance;
    private Skills skills;
    private boolean taken;
    private int retirementAge;

    public Career() {
        satisfaction = (int) (Math.random() * 100);
        performance = 0;
        taken = false;
    }

    public Career(double salary, int salaryGrowth, int satisfaction, int performance, Skills skills, boolean taken, int retirementAge) {
        this.salary = salary;
        this.salaryGrowth = salaryGrowth;
        this.satisfaction = satisfaction;
        this.performance = performance;
        this.skills = skills;
        this.taken = taken;
        this.retirementAge = retirementAge;
    }

    public void update(Human human) {
        int temp = this.skills.checkSkills(human.getSkills());
        temp += Math.random() * human.getAttributes().getHappiness() + Math.random() * human.getAttributes().getHealth();
        temp /= 300;
        performance += temp;
        if (performance < -10) {
            if (-Math.random() * 100 > performance) {
                RandomEvents.fired(human);
            }
        }
        if (performance > 0) {
            salary += salaryGrowth * performance;
        }
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
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

    public void printInfo() {
        System.out.println(salary);
        System.out.println(satisfaction);
        System.out.println(performance);
    }
}
