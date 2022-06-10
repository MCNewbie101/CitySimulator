package Events;

import Actors.Human;
import Buildings.House;
import GameSystems.Age;
import GameSystems.BankAccount;
import GameSystems.Careers.Actor;
import GameSystems.Careers.Basketball;
import GameSystems.Careers.Career;
import GameSystems.Careers.Programmer;
import GameSystems.Position;
import GameSystems.Relations.*;
import World.World;

import java.util.ArrayList;
import java.util.Objects;

public class RandomEvents {
    public static void humanDeath(Human human, World world) {
        world.getBin().add(human);
        for (Human check : world.getHumans()) {
            if (!check.isAlive()) {
                continue;
            }
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
            }
        }
        if (human.getJob() != null) {
            RandomEvents.nullifyJob(human);
        }
        if (human.getAddress() != null) {
            human.getAddress().setOwnedBy(null);
        }
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

    public static void inheritance(Human human, World world) {
        double split = 0;
        for (Dependent dependent : human.getRelations().getDependentRelations()) {
            if (!dependent.getPerson().isAlive()) {
                continue;
            }
            dependent.getPerson().getAttributes().changeTrauma(dependent.getCloseness() / 5);
            split += dependent.getCloseness();
            dependent.getPerson().getAttributes().changeHappiness(-dependent.getCloseness() * 1.1);
            dependent.getPerson().getRelations().setCaretakers(new ArrayList<>());
            for (Familial familial : dependent.getPerson().getRelations().getFamilyRelations()) {
                if (!familial.getPerson().isAlive()) {
                    continue;
                }
                if (familial.getPerson().getAge().getYears() > 18 && familial.getPerson().getAddress() != null && familial.getCloseness() * (100 - familial.getPerson().getAttributes().getPersonality().getSelfishness()) * familial.getPerson().getBankAccount().getDeposit() > (Math.random() + 1) * 1000000) {
                    if (!familial.getPerson().getAddress().isUsable()) {
                        continue;
                    }
                    familial.getPerson().getRelations().getDependentRelations().add(new Dependent(familial.getPerson(), dependent.getPerson(), familial.getCloseness(), familial.getPerson().getRelations().getDependentRelations().get(familial.getPerson().getRelations().getDependents().indexOf(dependent.getPerson())).getAbusivenessFrom()));
                    dependent.getPerson().getRelations().getCaretakerRelations().add(new Caretaker(dependent.getPerson(), familial.getPerson(), familial.getCloseness(), familial.getAbusivenessFrom()));
                    familial.getPerson().getRelations().getFamilyRelations().remove(familial.getPerson().getRelations().getFamily().indexOf(dependent.getPerson()));
                    dependent.getPerson().getRelations().getFamilyRelations().remove(familial);
                    if (familial.getPerson().getRelations().getLover() != null) {
                        if (familial.getPerson().getRelations().getLover().isMarried()) {
                            if (dependent.getPerson().getRelations().getFamily().contains(familial.getPerson().getRelations().getLover().getPerson())) {
                                int index = dependent.getPerson().getRelations().getFamily().indexOf(familial.getPerson().getRelations().getLover().getPerson());
                                dependent.getPerson().getRelations().getCaretakerRelations().add(new Caretaker(dependent.getPerson(), dependent.getPerson().getRelations().getFamilyRelations().get(index).getPerson(), dependent.getPerson().getRelations().getFamilyRelations().get(index).getCloseness(), dependent.getPerson().getRelations().getFamilyRelations().get(index).getAbusivenessFrom()));
                                familial.getPerson().getRelations().getLover().getPerson().getRelations().getDependentRelations().add(new Dependent(familial.getPerson().getRelations().getLover().getPerson(), dependent.getPerson(), dependent.getPerson().getRelations().getFamilyRelations().get(index).getCloseness(), familial.getPerson().getRelations().getLover().getPerson().getRelations().getFamilyRelations().get(familial.getPerson().getRelations().getLover().getPerson().getRelations().getFamily().indexOf(dependent.getPerson())).getAbusivenessFrom()));
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
                if (!dependent.getPerson().isAlive()) {
                    continue;
                }
                dependent.getPerson().getBankAccount().deposit(split * dependent.getCloseness());
            }
        } else {
            for (Familial familial : human.getRelations().getFamilyRelations()) {
                if (!familial.getPerson().isAlive()) {
                    continue;
                }
                split += familial.getCloseness();
            }
            if (split != 0) {
                split = human.getBankAccount().getDeposit() / split;
                for (Familial familial : human.getRelations().getFamilyRelations()) {
                    if (!familial.getPerson().isAlive()) {
                        continue;
                    }
                    familial.getPerson().getBankAccount().deposit(split * familial.getCloseness());
                }
            } else {
                for (Platonic friend : human.getRelations().getFriendships()) {
                    if (friend.getCloseness() > 30 && friend.getPerson().isAlive()) {
                        split += friend.getCloseness();
                    }
                }
                if (split != 0) {
                    split = human.getBankAccount().getDeposit() / split;
                    if (split > 1000) {
                        split = 1000;
                    }
                    for (Platonic friend : human.getRelations().getFriendships()) {
                        if (friend.getCloseness() > 30 && friend.getPerson().isAlive()) {
                            friend.getPerson().getBankAccount().deposit(split * friend.getCloseness());
                            human.getBankAccount().spend(split);
                        }
                    }
                }
                world.addBudget(human.getBankAccount().getDeposit());
            }
        }
    }

    public static void houseSearch(Human human, World world) {
        if (!human.isAlive()) {
            return;
        }
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
        if (!human.isAlive()) {
            return;
        }
        for (Career career : world.getJobs()) {
            if (career.isTaken()) {
                continue;
            }
            if (human.getAge().getYears() < career.getRetirementAge()) {
                if (career.getSkills().checkSkills(human.getSkills()) > Math.random() * 130 - 30) {
                    if (human.getEducation().getGrade() > Math.random() * career.getAcademicRequirement()) {
                        career.setTaken(true);
                        human.setJob(career);
                        career.setSatisfaction((int) (human.getAttributes().getPersonality().getCareerPreferences()[career.getCareerID()] * 2 - 100 + Math.random() * 30 - Math.random() * 30));
                        if (career.getSatisfaction() < -100) {
                            career.setSatisfaction(-100);
                        }
                        if (career.getSatisfaction() > 100) {
                            career.setSatisfaction(100);
                        }
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
            world.getJobs().add(new Basketball());
        } else if (gen == 2) {
            world.getJobs().add(new Programmer());
        }
    }

    public static void findDate(Human human, World world) {
        if (!human.isAlive()) {
            return;
        }
        if (human.getRelations().getLover() != null) {
            return;
        }
        if (Math.random() * world.getDaysPerYear() > 3) {
            return;
        }
        for (Human human1 : world.getHumans()) {
            if (!human1.isAlive()) {
                continue;
            }
            if (human.getRelations().getFamily().contains(human1) || human.getRelations().getDependents().contains(human1) || human.getRelations().getCaretakers().contains(human1) || human == human1) {
                continue;
            }
            if (human1.getAge().getYears() < 15 || human1.getAge().getYears() / 2 + 7 > human.getAge().getYears() || human.getAge().getYears() / 2 + 7 > human1.getAge().getYears() || human1.getRelations().getLover() != null) {
                continue;
            }
            if (Math.random() * Math.abs(human.getAge().getYears() - human1.getAge().getYears()) > 3) {
                continue;
            }
            if (!Objects.equals(human1.getGender(), human.getGender())) {
                if ((human.getS() == 0 || human.getS() == 2) && (human1.getS() == 0 || human1.getS() == 2)) {
                    if (addRomance(human, human1)) return;
                }
            } else {
                if ((human.getS() == 1 || human.getS() == 2) && (human1.getS() == 1 || human1.getS() == 2)) {
                    if (addRomance(human, human1)) return;
                }
            }
        }
    }

    private static boolean addRomance(Human human, Human human1) {
        if (human.getAttributes().getPersonality().compatibility(human1.getAttributes().getPersonality()) > Math.random() * 100) {
            human.getRelations().setLover(new Romantic(human, human1));
            human1.getRelations().setLover(new Romantic(human1, human));
            if (human.getRelations().getFriends().contains(human1)) {
                human.getRelations().getFriendships().remove(human.getRelations().getFriends().indexOf(human1));
                human1.getRelations().getFriendships().remove(human1.getRelations().getFriends().indexOf(human));
            }
            return true;
        }
        return false;
    }

    public static void marriage(Human human, int daysPerYear) {
        if (Math.random() * daysPerYear > 3) {
            return;
        }
        if (human.getRelations().getLover() == null || human.getAge().getYears() <= 18) {
            return;
        }
        if (!human.isAlive() || !human.getRelations().getLover().getPerson().isAlive()) {
            return;
        }
        if (human.getRelations().getLover().getCloseness() > Math.random() * 50 + 50 && human.getRelations().getLover().getPerson().getAge().getYears() > 18) {
            if (human.getRelations().getLover().getYearsTogether().getYears() > 3) {
                Human human1 = human.getRelations().getLover().getPerson();
                human.getRelations().getLover().setMarried(true);
                human1.getRelations().getLover().setMarried(true);
                human.getBankAccount().deposit(human1.getBankAccount().getDeposit());
                human1.setBankAccount(human.getBankAccount());
                if (human.getAddress() != null && human1.getAddress() != null && human.getAddress().isUsable() && human1.getAddress().isUsable()) {
                    if (human1.getAddress().getValue() > human.getAddress().getValue()) {
                        human.getBankAccount().deposit(human.getAddress().getValue());
                        human.getAddress().setOwnedBy(null);
                        human.setAddress(human1.getAddress());
                        for (Human dependent : human.getRelations().getDependents()) {
                            dependent.setAddress(human1.getAddress());
                        }
                    } else {
                        human.getBankAccount().deposit(human1.getAddress().getValue());
                        human1.getAddress().setOwnedBy(null);
                        human1.setAddress(human.getAddress());
                        for (Human dependent : human1.getRelations().getDependents()) {
                            dependent.setAddress(human.getAddress());
                        }
                    }
                }
                ArrayList<Human> f1 = human1.getRelations().getFamily();
                ArrayList<Human> f = human.getRelations().getFamily();
                for (Human familial : f) {
                    if (!f1.contains(familial)) {
                        human1.getRelations().getFamilyRelations().add(new Familial(human1, familial));
                    }
                }
                for (Human familial : f1) {
                    if (!f.contains(familial)) {
                        human.getRelations().getFamilyRelations().add(new Familial(human, familial));
                    }
                }
                marriageAddDependents(human1, human);
                marriageAddDependents(human, human1);
            }
        }
    }

    private static void marriageAddDependents(Human human, Human human1) {
        for (Human dependent : human1.getRelations().getDependents()) {
            if (!dependent.isAlive()) {
                continue;
            }
            if (dependent.getRelations().getFriends().contains(human)) {
                Platonic dSide = dependent.getRelations().getFriendships().get(dependent.getRelations().getFriends().indexOf(human));
                dependent.getRelations().getCaretakerRelations().add(new Caretaker(dependent, human, dSide.getCloseness(),  dSide.getAbusivenessFrom()));
                Platonic hSide = human.getRelations().getFriendships().get(human.getRelations().getFriends().indexOf(dependent));
                human.getRelations().getDependentRelations().add(new Dependent(human, dependent, hSide.getCloseness(), hSide.getAbusivenessFrom()));
                dependent.getRelations().getFriendships().remove(dSide);
                human.getRelations().getFriendships().remove(hSide);
            } else {
                dependent.getRelations().getCaretakerRelations().add(new Caretaker(dependent, human));
                human.getRelations().getDependentRelations().add(new Dependent(human, dependent));
            }
        }
    }

    public static void childBirth(Human human, World world) {
        if (!human.isAlive() || ! human.getRelations().getLover().getPerson().isAlive()) {
            return;
        }
        if (Math.random() * world.getDaysPerYear() > 3) {
            return;
        }
        if (!human.getGender().equals("female") || human.getRelations().getLover() == null || human.getAge().getYears() <= 18 || human.getAge().getYears() >= 40) {
            return;
        }
        if (!human.getRelations().getLover().isMarried() || human.getRelations().getLover().getPerson().getAge().getYears() <= 18 || human.getRelations().getLover().getPerson().getAge().getYears() >= 40) {
            return;
        }
        for (Human dependent : human.getRelations().getDependents()) {
            if (dependent.getAge().getYears() == 0) {
                return;
            }
        }
        double gen = 10;
        if (human.getAge().getYears() < 25) {
            gen =  25 - human.getAge().getYears();
        } else if (human.getAge().getYears() > 30) {
            gen = human.getAge().getYears() - 30;
        }
        if (human.getRelations().getLover().getPerson().getAge().getYears() < 25) {
            gen -= 25 - human.getRelations().getLover().getPerson().getAge().getYears();
        } else if (human.getRelations().getLover().getPerson().getAge().getYears() > 30) {
            gen -= human.getRelations().getLover().getPerson().getAge().getYears() - 30;
        }
        if (gen <= 0.001) {
            gen = 0.001;
        }
        gen /= 10;
        gen *= human.getBankAccount().getDeposit() / 100000;
        gen *= human.getRelations().getLover().getCloseness() / 100.0;
        if (!human.getRelations().getDependentRelations().isEmpty()) {
            gen /= human.getRelations().getDependentRelations().size();
        }
        if (gen > Math.random()) {
            Human child = new Human(human, human.getRelations().getLover().getPerson());
            human.getRelations().getDependentRelations().add(new Dependent(human, child));
            human.getRelations().getLover().getPerson().getRelations().getDependentRelations().add(new Dependent(human.getRelations().getLover().getPerson(), child));
            world.getToAdd().add(child);
        }
    }

    public static void breakup(Romantic romantic) {
        if (!romantic.getSelf().isAlive() || !romantic.getPerson().isAlive()) {
            return;
        }
        if (romantic.isMarried()) {
            if (romantic.getSelf().getAddress() != null) {
                romantic.getSelf().getBankAccount().deposit(romantic.getSelf().getAddress().getValue());
                romantic.getSelf().getAddress().setOwnedBy(null);
                romantic.getSelf().setAddress(null);
                romantic.getPerson().setAddress(null);
                for (Dependent dependent : romantic.getSelf().getRelations().getDependentRelations()) {
                    if (dependent.getPerson().isAlive()) {
                        dependent.getPerson().setAddress(null);
                    }
                }
            }
            romantic.getPerson().setBankAccount(new BankAccount(romantic.getSelf().getBankAccount().getDeposit() / 2));
            romantic.getPerson().getBankAccount().setDeposit(romantic.getSelf().getBankAccount().getDeposit() / 2);
        }
        romantic.getSelf().getAttributes().changeHappiness(-romantic.getCloseness() * (Math.random() / 1.5 + 0.5));
        romantic.getPerson().getAttributes().changeHappiness(-romantic.getPerson().getRelations().getLover().getCloseness() * (Math.random() / 1.5 + 0.5));
        romantic.getSelf().getRelations().setLover(null);
        romantic.getPerson().getRelations().setLover(null);
    }

    public static void newFriend(Human human, World world) {
        if (!human.isAlive()) {
            return;
        }
        if (Math.random() * world.getDaysPerYear() > 5) {
            return;
        }
        for (Human human1 : world.getHumans()) {
            if (!human1.isAlive()) {
                continue;
            }
            if (human.getRelations().getFamily().contains(human1) || human.getRelations().getDependents().contains(human1) || human.getRelations().getCaretakers().contains(human1) || human == human1) {
                continue;
            }
            if (human.getRelations().getLover() != null) {
                if (human.getRelations().getLover().getPerson() == human1) {
                    return;
                }
            }
            if (Math.random() * Math.abs(human.getAge().getYears() - human1.getAge().getYears()) > 5) {
                continue;
            }
            if (Math.random() * human.getAttributes().getPersonality().compatibility(human1.getAttributes().getPersonality()) > 50) {
                human.getRelations().getFriendships().add(new Platonic(human, human1));
                human1.getRelations().getFriendships().add(new Platonic(human1, human));
            }
        }
    }

    public static void unFriend(ArrayList<Platonic> remove, Platonic platonic) {
        if (!platonic.getSelf().isAlive() || !platonic.getPerson().isAlive()) {
            return;
        }
        platonic.getSelf().getAttributes().changeHappiness(-platonic.getCloseness() * (Math.random() / 2 + 0.5));
        platonic.getPerson().getAttributes().changeHappiness(-platonic.getCloseness() * (Math.random() / 2 + 0.5));
        remove.add(platonic);
        platonic.getPerson().getRelations().getFriendships().remove(platonic.getPerson().getRelations().getFriends().indexOf(platonic.getSelf()));
    }
}
