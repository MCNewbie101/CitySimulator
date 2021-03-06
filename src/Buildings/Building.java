package Buildings;

import GameSystems.Position;
import GameSystems.Age;
import World.World;

/*
 * Keeps track of the value and age of a building, and whether it is usable
 */
public abstract class Building {
    private double value;
    private boolean usable;
    private Age age;
    private Position position;

    /*
     * Generate a building randomly
     */
    public Building() {
        value = Math.random() * 10000000;
        while (value < 500000 || value > 800000) {
            if (Math.random() * 10 < 5) {
                value = 650000 + (value - 650000) * 0.95;
            }
        }
        usable = true;
        age = new Age(0, 0);
        position = new Position();
    }

    /*
     * Generate a building using inputted values
     */
    public Building(double value, boolean usable, Age age, Position position) {
        this.value = value;
        this.usable = usable;
        this.age = age;
        this.position = position;
    }

    /*
     * Updates a building
     */
    public void update(World world, int daysPerYear) {
        age.update(daysPerYear);
        double gen = Math.random();
        while(Math.random() * 10 < 7) {
            gen *= 0.9;
        }
        value *= gen / daysPerYear + 1;
        if (Math.random() * 1000 < age.getYears()) {
            usable = false;
        }
        if (!usable) {
            if (Math.random() * 1000000000 < world.getCityBudget() - value) {
                world.citySpending(value);
                usable = true;
            }
        }
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public boolean isUsable() {
        return usable;
    }

    public void setUsable(boolean usable) {
        this.usable = usable;
    }

    public Age getAge() {
        return age;
    }

    public void setAge(Age age) {
        this.age = age;
    }

    public Position getAddress() {
        return position;
    }

    public void setAddress(Position position) {
        this.position = position;
    }

    public void printInfo() {
        System.out.println("Value: " + value);
        System.out.println("Age: " + age.getYears());
        System.out.println("Usable: " + usable);
    }
}
