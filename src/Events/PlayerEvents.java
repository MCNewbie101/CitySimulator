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
}
