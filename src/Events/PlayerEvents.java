package Events;

import Actors.Human;
import Buildings.House;
import GameSystems.*;
import GameSystems.Skills.*;
import World.World;

public class PlayerEvents {
    public static void earthQuake(World world, Position position, int intensity) {
        for (House house : world.getHouses()) {
            if (Math.random() < house.getAddress().distance(position) * intensity) {
                house.setUsable(false);
            }
        }
    }

    public static void randomEarthQuake(World world, int intensity) {
        for (House house : world.getHouses()) {
            if (Math.random() * intensity > 30) {
                house.setUsable(false);
            }
        }
    }

    public static void killCommand(World world, Human human) {
        RandomEvents.humanDeath(human, world);
    }

    public static void destroyProperty(Human human) {
        if (human.getAddress() != null) {
            human.getAddress().setUsable(false);
        }
    }

    public static void makeIll(Human human, int severity) {
        human.getAttributes().changeHealth(-severity);
    }

    public static void addNewTracked(World world, int age, String gender, int c, int m, int p, int s, int grade, int happiness, int health, int trauma, int aggressiveness, int confidence, int sociability, int impulsiveness, int openness, int selfishness, int deposit) {
        world.getHumans().add(new Human(world, new Age(age, 0), gender, new Skills(new Creativity(c, 0, 0, 0), new Mental(m, 0, 0, 0, 0), new Physical(p, 0, 0, 0, 0), new Social(s, 0, 0, 0)), new Education(grade), new Attributes(new Personality(aggressiveness, confidence, sociability, impulsiveness, openness, selfishness), happiness, health, trauma), new BankAccount(deposit)));
    }
}
