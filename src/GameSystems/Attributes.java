package GameSystems;

import Actors.Human;
import GameSystems.Relations.Familial;
import GameSystems.Relations.Platonic;

public class Attributes {
    private Personality personality;
    private double happiness;
    private double health;
    private int trauma;

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
        trauma = 0;
    }

    public Attributes(Attributes attributes1, Attributes attributes2) {
        personality = new Personality(attributes1.getPersonality(), attributes2.getPersonality());
        happiness = (int) (Math.random() * 50 + 50);
        health = (int) ((attributes1.getHealth() + attributes2.getHealth()) / 2 + Math.random() * 20 - 10);
        trauma = 0;
    }

    public Attributes(Personality personality, double happiness, double health, int trauma) {
        this.personality = personality;
        this.happiness = happiness;
        this.health = health;
        this.trauma = trauma;
    }

    /*
     * Updates a human's attributes
     */
    public void update(Human human, int daysPerYear) {
        health += (Math.random() - Math.random()) * 5 / daysPerYear;
        health += Math.random() * 0.3 * (70 - human.getAge().getYears()) / daysPerYear;
        if (happiness < 0) {
            health += happiness / 10 / daysPerYear;
        } else {
            health += happiness / 50 / daysPerYear;
        }
        if (health <= 0) {
            human.setAlive(false);
            return;
        }
        if (health > 100) {
            health = 100;
        }
        happiness += (Math.random() - Math.random()) * 30;
        happiness += Math.random() * (health - 70);
        if (human.getRelations().getLover() != null) {
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
        happiness -= trauma;
        if (happiness < -100) {
            happiness = -100;
        }
        if (happiness > 100) {
            happiness = 100;
        }
        if (happiness < -70) {
            trauma++;
        }
        if (health < 20) {
            trauma++;
        }
        if (human.getAge().getYears() < 3) {
            trauma *= 2.1;
        } else if (human.getAge().getYears() < 6) {
            trauma *= 1.8;
        } else if (human.getAge().getYears() < 9) {
            trauma *= 1.55;
        } else if (human.getAge().getYears() < 12) {
            trauma *= 1.35;
        } else if (human.getAge().getYears() < 15) {
            trauma *= 1.2;
        } else if (human.getAge().getYears() < 19) {
            trauma *= 1.1;
        } else {
            trauma *= 1.05;
        }
    }

    public void updateHouse(Human human, int daysPerYear) {
        happiness -= 30.0 / daysPerYear;
        health -= 10.0 / daysPerYear;
        checkInBounds(human);
        trauma += 2;
    }

    /*
     * Checks that attributes are within bounds
     */
    private void checkInBounds(Human human) {
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
        checkInBounds(human);
        trauma += 3;
    }

    /*
     * Change attributes depending on how much money a human has
     */
    public void updateMoney(Human human, BankAccount bankAccount, int daysPerYear) {
        if (bankAccount == null) {
            happiness -= 50.0 / daysPerYear;
            health -= 30.0 / daysPerYear;
            trauma += 2;
            return;
        }
        if (bankAccount.getDeposit() < 10000.0 / daysPerYear) {
            happiness -= 50.0 / daysPerYear;
            health -= 30.0 / daysPerYear;
            trauma += 2;
        } else if (bankAccount.getDeposit() < 20000.0 / daysPerYear) {
            happiness -= 30.0 / daysPerYear;
            health -= 10.0 / daysPerYear;
            bankAccount.spend(10000);
            trauma++;
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
        checkInBounds(human);
    }

    public void changeHappiness(double happiness) {
        this.happiness += happiness;
    }

    public void changeHealth(double health) {
        this.health += health;
    }

    public void changeTrauma(int trauma) {
        this.trauma += trauma;
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

    public int getTrauma() {
        return trauma;
    }

    public void setTrauma(int trauma) {
        this.trauma = trauma;
    }

    public void printInfo() {
        System.out.println("Attributes:");
        System.out.println("Happiness: " + happiness);
        System.out.println("Health: " + health);
    }
}
