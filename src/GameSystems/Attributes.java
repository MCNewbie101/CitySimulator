package GameSystems;

import Actors.Human;
import GameSystems.Relations.Familial;
import GameSystems.Relations.Platonic;

public class Attributes {
    private Personality personality;
    private double happiness;
    private double health;

    public Attributes() {
        personality = new Personality();
        int gen = (int) (Math.random() * 100);
        while (Math.random() * 10 < 5) {
            gen = (int) (50 + (gen - 50) * 0.9);
        }
        happiness = gen;
        gen = (int) (Math.random() * 100);
        while (Math.random() * 10 < 9) {
            gen = (int) (90 + (gen - 90) * 0.95);
        }
        health = gen;
    }

    public Attributes(Attributes attributes1, Attributes attributes2) {
        personality = new Personality(attributes1.getPersonality(), attributes2.getPersonality());
        happiness = (int) (Math.random() * 50 + 50);
        health = (int) ((attributes1.getHealth() + attributes2.getHealth()) / 2 + Math.random() * 20 - 10);
    }

    public void update(Human human, int daysPerYear) {
        health += (Math.random() - Math.random()) * 5 / daysPerYear;
        health += Math.random() * (70 - human.getAge().getYears()) / daysPerYear;
        if (happiness < 0) {
            health -= happiness / 10 / daysPerYear;
        } else {
            health += happiness / 50 / daysPerYear;
        }
        if (health <= 0) {
            human.setAlive(false);
        }
        if (health > 100) {
            health = 100;
        }
        happiness += (Math.random() - Math.random()) * 30;
        happiness += Math.random() * (70 - health);
        if (human.getRelations().getLover() != null) {
            happiness += human.getRelations().getLover().getRomance() * human.getRelations().getLover().getCloseness() / 50.0 / daysPerYear;
            happiness -= human.getRelations().getLover().getAbusivenessFrom() * human.getRelations().getLover().getCloseness() / 5.0 / daysPerYear;
        }
        for (Familial relation : human.getRelations().getFamilyRelations()) {
            happiness += relation.getCloseness() / 10.0 / daysPerYear;
            happiness -= relation.getAbusivenessFrom() * relation.getCloseness() / 5.0 / daysPerYear;
        }
        for (Platonic relation : human.getRelations().getFriendships()) {
            happiness += relation.getCloseness() / 10.0 / daysPerYear;
            happiness -= relation.getAbusivenessFrom() * relation.getCloseness() / 5.0 / daysPerYear;
        }
        if (happiness < -100) {
            happiness = -100;
        }
        if (happiness > 100) {
            happiness = 100;
        }
    }

    public void updateHouse(Human human, int daysPerYear) {
        happiness -= 30.0 / daysPerYear;
        health -= 10.0 / daysPerYear;
        if (health <= 0) {
            human.setAlive(false);
        }
        if (health > 100) {
            health = 100;
        }
        if (happiness < -100) {
            happiness = -100;
        }
        if (happiness > 100) {
            happiness = 100;
        }
    }

    public void isOrphan(Human human, int daysPerYear) {
        happiness -= 50.0 / daysPerYear;
        health -= 15.0 / daysPerYear;
        if (health <= 0) {
            human.setAlive(false);
        }
        if (health > 100) {
            health = 100;
        }
        if (happiness < -100) {
            happiness = -100;
        }
        if (happiness > 100) {
            happiness = 100;
        }
    }

    // TODO: Makes sure this actually make sense
    public void updateMoney(BankAccount bankAccount, int daysPerYear) {
        if (bankAccount.getDeposit() < 10000.0 / daysPerYear) {
            happiness -= 50.0 / daysPerYear;
            health -= 30.0 / daysPerYear;
        } else if (bankAccount.getDeposit() < 20000.0 / daysPerYear) {
            happiness -= 30.0 / daysPerYear;
            health -= 10.0 / daysPerYear;
            bankAccount.spend(10000);
        } else if (bankAccount.getDeposit() < 30000.0 / daysPerYear) {
            happiness -= 10.0 / daysPerYear;
            health -= 3.0 / daysPerYear;
            bankAccount.spend(20000);
        } else if (bankAccount.getDeposit() < 40000.0 / daysPerYear) {
            happiness -= 5.0 / daysPerYear;
            bankAccount.spend(30000.0 / daysPerYear);
        } else if (bankAccount.getDeposit() < 100000) {
            bankAccount.spend(40000);
        } else {
            happiness += 15;
            bankAccount.spend(bankAccount.getDeposit() * Math.random() * 0.3 + 40000);
        }
    }

    public void changeHappiness(double happiness) {
        this.happiness += happiness;
    }

    public void changeHealth(double health) {
        this.health += health;
    }

    public Personality getPersonality() {
        return personality;
    }

    public void setPersonality(Personality personality) {
        this.personality = personality;
    }

    public double getHappiness() {
        return happiness;
    }

    public void setHappiness(double happiness) {
        this.happiness = happiness;
    }

    public double getHealth() {
        return health;
    }

    public void setHealth(double health) {
        this.health = health;
    }

    public void printInfo() {
        System.out.println(happiness);
        System.out.println(health);
    }
}
