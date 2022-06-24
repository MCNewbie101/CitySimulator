package World;

import Actors.Human;
import Buildings.House;
import Events.RandomEvents;
import GameSystems.Careers.Career;

import java.util.ArrayList;

public class World {
    private int daysPerYear;
    private int daysPerUpdate;
    private int skillIncreaseBalancing; //Balances how fast skills increase. The higher the number, the slower skills increase.
    private int jobSkillIncreaseBalancing; // Similar to the one above, but balances how fast careers increase skills

    private ArrayList<House> houses;
    private ArrayList<Human> humans;
    private ArrayList<Human> bin;
    private ArrayList<Human> toAdd;
    private ArrayList<Human> tracked;
    private ArrayList<Career> jobs;

    private double cityBudget;

    public World(int houses, int population) {
        daysPerYear = 1;
        daysPerUpdate = 1;
        skillIncreaseBalancing = 20;
        jobSkillIncreaseBalancing = 100;
        cityBudget = population * 10000 + houses * 500000;
        this.houses = new ArrayList<>();
        for (int i = 0; i < houses; i++) {
            RandomEvents.buildHouse(this);
        }
        humans = new ArrayList<>();
        for (int i = 0; i < population; i++) {
            humans.add(new Human(this));
        }
        bin = new ArrayList<>();
        toAdd = new ArrayList<>();
        tracked = new ArrayList<>();
        jobs = new ArrayList<>();
        int jobN = (int) (Math.random() * population);
        if (jobN < population / 2) {
            jobN = population / 2;
        }
        for (int i = 0; i < jobN; i++) {
            RandomEvents.careerOption(this);
        }
    }

    /*
     * Not yet implemented
     */
    public void updateController() {
        //TODO: Run update every secondsPerDay seconds
    }

    /*
     * Updates every human and house in the simulated world
     * Removes dead humans from the list of humans
     * Add new humans to the list of humans
     * Generates new houses and jobs
     */
    public void update() {
        RandomEvents.humanMoveIn(this);
        for (House house : houses) {
            house.update(this, daysPerYear);
        }
        for (Human human : humans) {
            human.update(this);
        }
        for (Human human : bin) {
            humans.remove(human);
            tracked.remove(human);
        }
        humans.addAll(toAdd);
        bin = new ArrayList<>();
        toAdd = new ArrayList<>();
        for (House house : houses) {
            if (!house.isUsable()) {
                RandomEvents.repairHouse(this, house);
            }
        }
        double gen = humans.size() * 1.0 / houses.size();
        while (Math.random() * 1.5 < gen) {
            int houseN = houses.size();
            RandomEvents.buildHouse(this);
            if (houseN == houses.size()) {
                return;
            }
        }
        while (Math.random() * 1.5 < humans.size() * 1.0 / jobs.size()) {
            RandomEvents.careerOption(this);
        }
    }

    /*
     * Simulates welfare
     */
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

    public int getDaysPerUpdate() {
        return daysPerUpdate;
    }

    public void setDaysPerUpdate(int daysPerUpdate) {
        this.daysPerUpdate = daysPerUpdate;
    }

    public int getSkillIncreaseBalancing() {
        return skillIncreaseBalancing;
    }

    public void setSkillIncreaseBalancing(int skillIncreaseBalancing) {
        this.skillIncreaseBalancing = skillIncreaseBalancing;
    }

    public int getJobSkillIncreaseBalancing() {
        return jobSkillIncreaseBalancing;
    }

    public void setJobSkillIncreaseBalancing(int jobSkillIncreaseBalancing) {
        this.jobSkillIncreaseBalancing = jobSkillIncreaseBalancing;
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
            human.printInfo(this);
        }
        System.out.println();
    }

    public void printTrackedInfo() {
        System.out.println("City Budget: " + cityBudget);
        System.out.println("Population: " + humans.size());
        System.out.println();
        for (Human human : tracked) {
            human.printInfo(this);
            System.out.println();
        }
        System.out.println();
    }
}
