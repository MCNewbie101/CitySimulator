package World;

import Actors.Human;
import Buildings.House;
import Events.RandomEvents;
import GameSystems.Careers.Career;

import java.util.ArrayList;

public class World {
    private int daysPerYear;
    private int secondsPerDay;
    private int skillIncreaseBalancing;

    private ArrayList<House> houses;
    private ArrayList<Human> humans;
    private ArrayList<Human> bin;
    private ArrayList<Human> toAdd;
    private ArrayList<Human> tracked;
    private ArrayList<Career> jobs;

    private double cityBudget;

    public World(int houses, int population) {
        daysPerYear = 1;
        secondsPerDay = 30;
        cityBudget = population * 1000 + houses * 500000;
        this.houses = new ArrayList<>();
        for (int i = 0; i < houses; i++) {
            RandomEvents.buildHouse(this);
        }
        humans = new ArrayList<>();
        for (int i = 0; i < population; i++) {
            humans.add(new Human(daysPerYear, this));
        }
        bin = new ArrayList<>();
        toAdd = new ArrayList<>();
        tracked = new ArrayList<>();
        jobs = new ArrayList<>();
        int jobN = (int) (Math.random() * population);
        if (jobN < population / 5) {
            jobN = population / 5;
        }
        for (int i = 0; i < jobN; i++) {
            RandomEvents.careerOption(this);
        }
        skillIncreaseBalancing = 5;
    }

    public void updateController() {
        //TODO: Run update every secondsPerDay seconds
    }

    public void update() {
        for (House house : houses) {
            house.update(daysPerYear);
        }
        for (Human human : humans) {
            human.update(daysPerYear, skillIncreaseBalancing, this);
        }
        for (Human human : bin) {
            humans.remove(human);
        }
        for (Human human : toAdd) {
            humans.add(human);
        }
        bin = new ArrayList<>();
        double gen = humans.size() * 1.0 / houses.size();
        if (humans.size() * 1.0 / houses.size() >= 2.9) {
            gen = 2.9;
        }
        while (Math.random() * 3 < gen) {
            RandomEvents.buildHouse(this);
        }
        while (Math.random() * 3 < humans.size() * 1.0 / jobs.size()) {
            RandomEvents.careerOption(this);
        }
    }

    public void aid(Human human) {
        double aid = 30000 - human.getBankAccount().getDeposit() * 0.7;
        aid += human.getRelations().getDependentRelations().size() * 3000;
        if (aid < cityBudget) {
            cityBudget -= aid;
            human.getBankAccount().deposit(aid);
        } else {
            human.getBankAccount().deposit(cityBudget);
            cityBudget = 0;
        }
    }

    public void addBudget(double cityBudget) {
        this.cityBudget += cityBudget;
    }

    public void citySpending(double money) {
        cityBudget -= money;
    }

    public int getDaysPerYear() {
        return daysPerYear;
    }

    public void setDaysPerYear(int daysPerYear) {
        this.daysPerYear = daysPerYear;
    }

    public int getSecondsPerDay() {
        return secondsPerDay;
    }

    public void setSecondsPerDay(int secondsPerDay) {
        this.secondsPerDay = secondsPerDay;
    }

    public int getSkillIncreaseBalancing() {
        return skillIncreaseBalancing;
    }

    public void setSkillIncreaseBalancing(int skillIncreaseBalancing) {
        this.skillIncreaseBalancing = skillIncreaseBalancing;
    }

    public ArrayList<House> getHouses() {
        return houses;
    }

    public void setHouses(ArrayList<House> houses) {
        this.houses = houses;
    }

    public ArrayList<Human> getHumans() {
        return humans;
    }

    public void setHumans(ArrayList<Human> humans) {
        this.humans = humans;
    }

    public ArrayList<Human> getBin() {
        return bin;
    }

    public void setBin(ArrayList<Human> bin) {
        this.bin = bin;
    }

    public ArrayList<Human> getToAdd() {
        return toAdd;
    }

    public void setToAdd(ArrayList<Human> toAdd) {
        this.toAdd = toAdd;
    }

    public ArrayList<Human> getTracked() {
        return tracked;
    }

    public void setTracked(ArrayList<Human> tracked) {
        this.tracked = tracked;
    }

    public ArrayList<Career> getJobs() {
        return jobs;
    }

    public void setJobs(ArrayList<Career> jobs) {
        this.jobs = jobs;
    }

    public double getCityBudget() {
        return cityBudget;
    }

    public void setCityBudget(double cityBudget) {
        this.cityBudget = cityBudget;
    }

    public void printInfo() {
        System.out.println(cityBudget);
        System.out.println();
        for (Human human : humans) {
            human.printInfo();
        }
        System.out.println();
    }
}
