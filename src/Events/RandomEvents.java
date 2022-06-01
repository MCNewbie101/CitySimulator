package Events;

import Actors.Human;
import Buildings.House;
import GameSystems.Age;
import GameSystems.Careers.Actor;
import GameSystems.Careers.Career;
import GameSystems.Careers.Programmer;
import GameSystems.Position;
import World.World;

public class RandomEvents {
    public static void jobSearch(Human human, World world) {
        for (Career career : world.getJobs()) {
            if (career.isTaken()) {
                continue;
            }
            if (career.getSkills().checkSkills(human.getSkills()) > Math.random() * 50 - 25) {
                if (human.getEducation().getGrade() > Math.random() * career.getSalary() / 300) {
                    career.setTaken(true);
                    human.setJob(career);
                    return;
                }
            }
        }
    }

    public static void humanDeath(Human human, World world) {
        world.getHumans().remove(human);
        for (Human check : world.getHumans()) {
            if (check.getRelations().getLover() != null) {
                if (check.getRelations().getLover().getPerson().equals(human)) {
                    RandomEvents.partnerDeath(check, human);
                }
            }
            if (check.getRelations().getFamily().contains(human)) {
                RandomEvents.familyDeath(check, human);
            } else if (check.getRelations().getFriends().contains(human)) {
                RandomEvents.friendDeath(check, human);
            } else if (check.getRelations().getOther().contains(human)) {
                check.getRelations().getOtherRelations().remove(check.getRelations().getOther().indexOf(human));
            }
        }
    }

    private static void friendDeath(Human check, Human human) {
    }

    private static void familyDeath(Human check, Human human) {
    }

    private static void partnerDeath(Human check, Human human) {
    }

    public static void houseSearch(Human human) {
    }

    public static void inheritance(Human human) {
    }

    public static void fired(Human human) {
        human.getJob().setTaken(false);
        human.setJob(null);
    }

    public static void buildHouse(World world) {
        int value = (int) (Math.random() * 10000000);
        if (world.getCityBudget() < value) {
            value = (int) world.getCityBudget();
        }
        world.getHouses().add(new House(value, true, new Age(), new Position(), null));
        world.citySpending(value);
    }

    public static void careerOption(World world) {
        int gen = (int) (Math.random() * 2);
        if (gen == 0) {
            world.getJobs().add(new Actor());
        } else if (gen == 1) {
            world.getJobs().add(new Programmer());
        }
    }
}
