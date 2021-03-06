package GameSystems.Skills;

public class Physical {
    private int potential;
    private double speed;
    private double strength;
    private double dancing;
    private double basketball;

    public Physical() {
        this.potential = (int) (Math.random() * 100);
    }

    public Physical(Physical skills1, Physical skills2) {
        potential = (int) (Math.random() * 20 + (skills1.getPotential() + skills2.getPotential()) / 2 - 10);
        potential = (int) (Math.random() * 20 + (skills1.getPotential() + skills2.getPotential()) / 2 - 10);
        if (potential > 100) {
            potential = 100;
        }
        if (potential < 0) {
            potential = 0;
        }
    }

    public Physical(int p) {
        potential = p;
    }

    public Physical(int potential, double speed, double strength, double dancing, double basketball) {
        this.potential = potential;
        this.speed = speed;
        this.strength = strength;
        this.dancing = dancing;
        this.basketball = basketball;
    }

    public void update(int skillIncreaseBalancing, int daysPerYear) {
        this.speed += Math.random() * potential / skillIncreaseBalancing / daysPerYear;
        this.strength += Math.random() * potential / skillIncreaseBalancing / daysPerYear;
        this.dancing += Math.random() * potential / skillIncreaseBalancing / daysPerYear;
        this.basketball += Math.random() * potential / skillIncreaseBalancing / daysPerYear;
        updateChecker();
    }

    public void update(Physical physical, int skillIncreaseBalancing, int jobSkillIncreaseBalancing, int daysPerYear) {
        this.speed += physical.getSpeed() * potential / skillIncreaseBalancing / jobSkillIncreaseBalancing / daysPerYear;
        this.strength += physical.getStrength() * potential / skillIncreaseBalancing / jobSkillIncreaseBalancing / daysPerYear;
        this.dancing += physical.getDancing() * potential / skillIncreaseBalancing / jobSkillIncreaseBalancing / daysPerYear;
        this.basketball += physical.getBasketball() * potential / skillIncreaseBalancing / jobSkillIncreaseBalancing / daysPerYear;
        updateChecker();
    }

    public void updateChecker() {
        if (speed > 100) {
            speed = 100;
        }
        if (strength > 100) {
            strength = 100;
        }
        if (dancing > 100) {
            dancing = 100;
        }
        if (basketball > 100) {
            basketball = 100;
        }
    }

    public int checkSkill(Physical physical) {
        int sum = 0;
        if (physical.getSpeed() != 0) {
            sum += (speed - physical.getSpeed()) * physical.getSpeed();
        }
        if (physical.getStrength() != 0) {
            sum += (strength - physical.getStrength()) * physical.getStrength();
        }
        if (physical.getDancing() != 0) {
            sum += (dancing - physical.getDancing()) * physical.getDancing();
        }
        if (physical.getBasketball() != 0) {
            sum += (basketball - physical.getBasketball()) * physical.getBasketball();
        }
        return sum;
    }

    public int getPotential() {
        return potential;
    }

    public void setPotential(int potential) {
        this.potential = potential;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public double getStrength() {
        return strength;
    }

    public void setStrength(double strength) {
        this.strength = strength;
    }

    public double getDancing() {
        return dancing;
    }

    public void setDancing(double dancing) {
        this.dancing = dancing;
    }

    public double getBasketball() {
        return basketball;
    }

    public void setBasketball(double basketball) {
        this.basketball = basketball;
    }

    public void printInfo() {
        System.out.println("Physical potential: " + potential);
        System.out.println("Speed: " + (int) speed);
        System.out.println("Strength: " + (int) strength);
        System.out.println("Dancing skill: " + (int) dancing);
        System.out.println("Basketball skill: " + (int) basketball);
    }
}
