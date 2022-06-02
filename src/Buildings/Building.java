package Buildings;

import GameSystems.Position;
import GameSystems.Age;

public abstract class Building {
    private double value;
    private boolean usable;
    private Age age;
    private Position position;

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

    public Building(double value, boolean usable, Age age, Position position) {
        this.value = value;
        this.usable = usable;
        this.age = age;
        this.position = position;
    }

    public void update(int daysPerYear) {
        age.update(daysPerYear);
        double gen = Math.random();
        while(Math.random() * 10 < 7) {
            gen *= 0.9;
        }
        value *= gen / daysPerYear + 1;
        if (Math.random() * 1000 < age.getYears()) {
            usable = false;
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
}
