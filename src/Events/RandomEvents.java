package Events;

import Actors.Human;
import Buildings.House;
import GameSystems.Age;
import GameSystems.Careers.Actor;
import GameSystems.Careers.Basketball;
import GameSystems.Careers.Career;
import GameSystems.Careers.Programmer;
import GameSystems.Position;
import GameSystems.Relations.Caretaker;
import GameSystems.Relations.Dependent;
import GameSystems.Relations.Familial;
import GameSystems.Relations.Platonic;
import World.World;

import java.util.ArrayList;

public class RandomEvents {
    public static void humanDeath(Human human, World world) {
        world.getHumans().remove(human);
        for (Human check : world.getHumans()) {
            if (check.getRelations().getLover() != null) {
                if (check.getRelations().getLover().getPerson().equals(human)) {
                    RandomEvents.partnerDeath(check);
                }
            }
            if (check.getRelations().getFamily().contains(human)) {
                RandomEvents.familyDeath(check, human);
            } else if (check.getRelations().getFriends().contains(human)) {
                RandomEvents.friendDeath(check, human);
            } else if (check.getRelations().getDependents().contains(human)) {
                RandomEvents.childDeath(check, human);
            } else if (check.getRelations().getOther().contains(human)) {
                check.getRelations().getOtherRelations().remove(check.getRelations().getOther().indexOf(human));
            }
        }
        RandomEvents.nullifyJob(human);
        human.getAddress().setOwnedBy(null);
    }

    private static void friendDeath(Human check, Human human) {
        for (Platonic friendship : check.getRelations().getFriendships()) {
            if (friendship.getPerson() == human) {
                check.getAttributes().changeHappiness(-friendship.getCloseness() * 0.7);
                check.getAttributes().changeTrauma(friendship.getCloseness() / 20);
                check.getRelations().getFriendships().remove(friendship);
                return;
            }
        }
    }

    private static void familyDeath(Human check, Human human) {
        for (Familial familial : check.getRelations().getFamilyRelations()) {
            if (familial.getPerson() == human) {
                check.getAttributes().changeHappiness(-familial.getCloseness());
                check.getAttributes().changeTrauma(familial.getCloseness() / 15);
                check.getRelations().getFamilyRelations().remove(familial);
                return;
            }
        }
    }

    private static void partnerDeath(Human check) {
        check.getAttributes().changeTrauma(check.getRelations().getLover().getCloseness() / 10);
        check.getAttributes().changeHappiness(-check.getRelations().getLover().getCloseness() * 1.05);
        check.getRelations().setLover(null);
    }

    private static void childDeath(Human check, Human human) {
        for (Dependent dependent : check.getRelations().getDependentRelations()) {
            if (dependent.getPerson() == human) {
                check.getAttributes().changeHappiness(-dependent.getCloseness() * 1.5);
                check.getAttributes().changeTrauma(dependent.getCloseness() / 5);
                check.getRelations().getFamilyRelations().remove(dependent);
                return;
            }
        }
    }

    public static void inheritance(Human human) {
        double split = 0;
        for (Dependent dependent : human.getRelations().getDependentRelations()) {
            dependent.getPerson().getAttributes().changeTrauma(dependent.getCloseness() / 5);
            split += dependent.getCloseness();
            dependent.getPerson().getAttributes().changeHappiness(-dependent.getCloseness() * 1.1);
            dependent.getPerson().getRelations().setCaretakers(new ArrayList<>());
            for (Familial familial : dependent.getPerson().getRelations().getFamilyRelations()) {
                if (familial.getPerson().getAge().getYears() > 18 && familial.getPerson().getAddress() != null && familial.getCloseness() * (100 - familial.getPerson().getAttributes().getPersonality().getSelfishness()) * familial.getPerson().getBankAccount().getDeposit() > (Math.random() + 1) * 1000000) {
                    if (!familial.getPerson().getAddress().isUsable()) {
                        continue;
                    }
                    familial.getPerson().getRelations().getDependentRelations().add(new Dependent(familial.getPerson(), dependent.getPerson(), familial.getCloseness(), familial.getAbusivenessFrom(), familial.getAbusivenessTo()));
                    dependent.getPerson().getRelations().getCaretakerRelations().add(new Caretaker(dependent.getPerson(), familial.getPerson(), familial.getCloseness(), familial.getAbusivenessTo(), familial.getAbusivenessFrom()));
                    familial.getPerson().getRelations().getFamilyRelations().remove(familial.getPerson().getRelations().getFamily().indexOf(dependent.getPerson()));
                    dependent.getPerson().getRelations().getFamilyRelations().remove(familial);
                    if (familial.getPerson().getRelations().getLover() != null) {
                        if (familial.getPerson().getRelations().getLover().isMarried()) {
                            if (dependent.getPerson().getRelations().getFamily().contains(familial.getPerson().getRelations().getLover().getPerson())) {
                                int index = dependent.getPerson().getRelations().getFamily().indexOf(familial.getPerson().getRelations().getLover().getPerson());
                                dependent.getPerson().getRelations().getCaretakerRelations().add(new Caretaker(dependent.getPerson(), dependent.getPerson().getRelations().getFamilyRelations().get(index).getPerson(), dependent.getPerson().getRelations().getFamilyRelations().get(index).getCloseness(), dependent.getPerson().getRelations().getFamilyRelations().get(index).getAbusivenessTo(), dependent.getPerson().getRelations().getFamilyRelations().get(index).getAbusivenessFrom()));
                                familial.getPerson().getRelations().getLover().getPerson().getRelations().getDependentRelations().add(new Dependent(familial.getPerson().getRelations().getLover().getPerson(), dependent.getPerson(), dependent.getPerson().getRelations().getFamilyRelations().get(index).getCloseness(), dependent.getPerson().getRelations().getFamilyRelations().get(index).getAbusivenessFrom(), dependent.getPerson().getRelations().getFamilyRelations().get(index).getAbusivenessTo()));
                                dependent.getPerson().getRelations().getFamilyRelations().remove(index);
                                familial.getPerson().getRelations().getLover().getPerson().getRelations().getFamilyRelations().removeIf(familial1 -> familial1.getPerson() == dependent.getPerson());
                            }
                        }
                    }
                }
                break;
            }
        }
        if (split != 0) {
            split = human.getBankAccount().getDeposit() / split;
            for (Dependent dependent : human.getRelations().getDependentRelations()) {
                dependent.getPerson().getBankAccount().deposit(split * dependent.getCloseness());
            }
        } else {
            for (Familial familial : human.getRelations().getFamilyRelations()) {
                split += familial.getCloseness();
            }
            if (split != 0) {
                split = human.getBankAccount().getDeposit() / split;
                for (Familial familial : human.getRelations().getFamilyRelations()) {
                    familial.getPerson().getBankAccount().deposit(split * familial.getCloseness());
                }
            }
        }
    }

    public static void houseSearch(Human human, World world) {
        for (House house : world.getHouses()) {
            if (human.getAddress() == null && human.getBankAccount().getDeposit() > house.getValue()) {
                house.setOwnedBy(human.getBankAccount());
                human.getBankAccount().spend(house.getValue());
            }
        }
    }

    public static void buildHouse(World world) {
        if (world.getCityBudget() < 100000) {
            return;
        }
        int value;
        if (world.getHumans() == null) {
            value = (int) (Math.random() * 700000) + 300000;
        } else if (world.getHumans().size() < world.getHouses().size() * 1.5) {
            value = (int) (Math.random() * 700000) + 300000;
        } else {
            value = (int) (Math.random() * 9300000) + 300000;
        }
        while (Math.random() * 10 < 9) {
            if (value <= 100000) {
                break;
            }
            value *= 0.99;
        }
        if (world.getCityBudget() < value) {
            value = (int) world.getCityBudget();
        }
        world.getHouses().add(new House(value, true, new Age(), new Position(), null));
        world.citySpending(value);
    }
    public static void jobSearch(Human human, World world) {
        for (Career career : world.getJobs()) {
            if (career.isTaken()) {
                continue;
            }
            if (human.getAge().getYears() < career.getRetirementAge()) {
                if (career.getSkills().checkSkills(human.getSkills()) > Math.random() * 50 - 25) {
                    if (human.getEducation().getGrade() > Math.random() * career.getSalary() / 300) {
                        career.setTaken(true);
                        human.setJob(career);
                        return;
                    }
                }
            }
        }
    }

    public static void nullifyJob(Human human) {
        human.getJob().setTaken(false);
        human.getJob().setPerformance(0);
        human.getJob().setSalary(human.getJob().getBase());
        human.setJob(null);
    }

    public static void fired(Human human) {
        human.getAttributes().changeHappiness(-30);
        RandomEvents.nullifyJob(human);
    }

    public static void quitJob(Human human) {
        human.getAttributes().changeHappiness(-human.getJob().getSatisfaction());
        RandomEvents.nullifyJob(human);
    }

    public static void careerOption(World world) {
        int gen = (int) (Math.random() * 3);
        if (gen == 0) {
            world.getJobs().add(new Actor());
        } else if (gen == 1) {
            world.getJobs().add(new Programmer());
        } else if (gen == 2) {
            world.getJobs().add(new Basketball());
        }
    }
}
