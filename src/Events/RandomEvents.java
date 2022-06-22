package Events;

import Actors.Human;
import Buildings.House;
import GameSystems.Age;
import GameSystems.BankAccount;
import GameSystems.Careers.*;
import GameSystems.Position;
import GameSystems.Relations.*;
import World.World;

import java.util.ArrayList;
import java.util.Objects;

public class RandomEvents {
    public static void humanMoveIn(World world) {
        int peopleN = (int) (Math.random() * world.getHumans().size() / 100);
        for (int i = 0; i < peopleN; i++) {
            world.getHumans().add(new Human(world));
        }
    }

    public static void humanDeath(Human human, World world) {
        world.getBin().add(human);
        if (human.getRelations().getLover() != null) {
            RandomEvents.partnerDeath(human.getRelations().getLover().getPerson());
        }
        for (Human check : human.getRelations().getCaretakers()) {
            RandomEvents.childDeath(check, human);
        }
        for (Human check : human.getRelations().getDependents()) {
            if (check.getRelations().getCaretakerRelations().size() > 1) {
                RandomEvents.parentDeath(check, human);
            }
        }
        for (Human check : human.getRelations().getFamily()) {
            RandomEvents.familyDeath(check, human);
        }
        for (Human check : human.getRelations().getFriends()) {
            RandomEvents.friendDeath(check, human);
        }
        if (human.getJob() != null) {
            RandomEvents.nullifyJob(human);
        }
        if (human.getAddress() != null) {
            human.getAddress().setOwnedBy(null);
        }
    }

    private static void parentDeath(Human check, Human human) {
        if (human.getAge().getYears() >= 18) {
            return;
        }
        Caretaker parent = check.getRelations().getCaretakerRelations().get(check.getRelations().getCaretakers().indexOf(human));
        check.getAttributes().changeHappiness(-parent.getCloseness() * 1.5);
        check.getAttributes().changeTrauma(parent.getCloseness() / 5);
        check.getRelations().getCaretakerRelations().remove(parent);
    }

    private static void friendDeath(Human check, Human human) {
        if (!check.getRelations().getFriends().contains(human)) {
            return;
        }
        Platonic friendship = check.getRelations().getFriendships().get(check.getRelations().getFriends().indexOf(human));
        check.getAttributes().changeHappiness(-friendship.getCloseness() * 0.7);
        check.getAttributes().changeTrauma(friendship.getCloseness() / 20);
        check.getRelations().getFriendships().remove(friendship);
    }

    private static void familyDeath(Human check, Human human) {
        if (!check.getRelations().getFamily().contains(human)) {
            return;
        }
        Familial familial = check.getRelations().getFamilyRelations().get(check.getRelations().getFamily().indexOf(human));
        check.getAttributes().changeHappiness(-familial.getCloseness());
        check.getAttributes().changeTrauma(familial.getCloseness() / 15);
        check.getRelations().getFamilyRelations().remove(familial);
    }

    private static void partnerDeath(Human check) {
        if (check.getRelations().getLover() == null) {
            return;
        }
        check.getAttributes().changeTrauma(check.getRelations().getLover().getCloseness() / 10);
        check.getAttributes().changeHappiness(-check.getRelations().getLover().getCloseness() * 1.05);
        check.getRelations().setLover(null);
    }

    private static void childDeath(Human check, Human human) {
        Dependent dependent = check.getRelations().getDependentRelations().get(check.getRelations().getDependents().indexOf(human));
        check.getAttributes().changeHappiness(-dependent.getCloseness() * 1.5);
        check.getAttributes().changeTrauma(dependent.getCloseness() / 5);
        check.getRelations().getDependentRelations().remove(dependent);
    }

    public static void inheritance(Human human, World world) {
        double split = 0;
        for (Dependent dependent : human.getRelations().getDependentRelations()) {
            if (!dependent.getPerson().isAlive() || !dependent.getPerson().getRelations().getCaretakers().contains(human)) {
                continue;
            }
            if (dependent.getPerson().getRelations().getCaretakerRelations().get(dependent.getPerson().getRelations().getCaretakers().indexOf(human)).getCloseness() >= 0) {
                dependent.getPerson().getAttributes().changeTrauma(dependent.getCloseness() / 5);
                split += dependent.getCloseness();
            }
            dependent.getPerson().getAttributes().changeHappiness(-dependent.getPerson().getRelations().getCaretakerRelations().get(dependent.getPerson().getRelations().getCaretakers().indexOf(human)).getCloseness() * 1.1);
            dependent.getPerson().getRelations().getCaretakerRelations().remove(dependent.getPerson().getRelations().getCaretakers().indexOf(human));
            if (dependent.getPerson().getRelations().getCaretakerRelations().isEmpty()) {
                for (Familial familial : dependent.getPerson().getRelations().getFamilyRelations()) {
                    if (!familial.getPerson().isAlive()) {
                        continue;
                    }
                    if (familial.getPerson().getAge().getYears() > 18 && familial.getPerson().getAddress() != null && familial.getCloseness() * (100 - familial.getPerson().getAttributes().getPersonality().getSelfishness()) * familial.getPerson().getBankAccount().getDeposit() > (Math.random() + 1) * 1000000) {
                        if (!familial.getPerson().getAddress().isUsable()) {
                            continue;
                        }
                        if (!familial.getPerson().getRelations().getFamily().contains(dependent.getPerson())) {
                            continue;
                        }
                        if (familial.getPerson().getRelations().getDependents().contains(dependent.getPerson().getRelations().getLover().getPerson()) || familial.getPerson().getRelations().getFamily().contains((dependent.getPerson().getRelations().getLover().getPerson()))) {
                            continue;
                        }
                        for (Human sibling : familial.getPerson().getRelations().getDependents()) {
                            if (dependent.getPerson().getRelations().getFamily().contains(sibling) || dependent.getPerson() == sibling) {
                                continue;
                            }
                            if (dependent.getPerson().getRelations().getFriends().contains(sibling)) {
                                dependent.getPerson().getRelations().getFamilyRelations().add(new Familial(dependent.getPerson(), sibling, dependent.getPerson().getRelations().getFriendships().get(dependent.getPerson().getRelations().getFriends().indexOf(sibling)).getCloseness(), dependent.getPerson().getRelations().getFriendships().get(dependent.getPerson().getRelations().getFriends().indexOf(sibling)).getAbusivenessFrom()));
                                sibling.getRelations().getFamilyRelations().add(new Familial(sibling, dependent.getPerson(), sibling.getRelations().getFriendships().get(sibling.getRelations().getFriends().indexOf(dependent.getPerson())).getCloseness(), sibling.getRelations().getFriendships().get(sibling.getRelations().getFriends().indexOf(dependent.getPerson())).getAbusivenessFrom()));
                                dependent.getPerson().getRelations().getFriendships().remove(dependent.getPerson().getRelations().getFriends().indexOf(sibling));
                                sibling.getRelations().getFriendships().remove(sibling.getRelations().getFriends().indexOf(dependent.getPerson()));
                            } else {
                                dependent.getPerson().getRelations().getFamilyRelations().add(new Familial(dependent.getPerson(), sibling));
                                sibling.getRelations().getFamilyRelations().add(new Familial(sibling, dependent.getPerson()));
                            }
                        }
                        familial.getPerson().getRelations().getDependentRelations().add(new Dependent(familial.getPerson(), dependent.getPerson(), familial.getPerson().getRelations().getFamilyRelations().get(familial.getPerson().getRelations().getFamily().indexOf(dependent.getPerson())).getCloseness(), familial.getPerson().getRelations().getFamilyRelations().get(familial.getPerson().getRelations().getFamily().indexOf(dependent.getPerson())).getAbusivenessFrom()));
                        dependent.getPerson().getRelations().getCaretakerRelations().add(new Caretaker(dependent.getPerson(), familial.getPerson(), familial.getCloseness(), familial.getAbusivenessFrom()));
                        familial.getPerson().getRelations().getFamilyRelations().remove(familial.getPerson().getRelations().getFamily().indexOf(dependent.getPerson()));
                        dependent.getPerson().getRelations().getFamilyRelations().remove(familial);
                        if (familial.getPerson().getRelations().getLover() != null) {
                            if (familial.getPerson().getRelations().getLover().isMarried()) {
                                if (dependent.getPerson().getRelations().getFamily().contains(familial.getPerson().getRelations().getLover().getPerson())) {
                                    int index = dependent.getPerson().getRelations().getFamily().indexOf(familial.getPerson().getRelations().getLover().getPerson());
                                    dependent.getPerson().getRelations().getCaretakerRelations().add(new Caretaker(dependent.getPerson(), dependent.getPerson().getRelations().getFamilyRelations().get(index).getPerson(), dependent.getPerson().getRelations().getFamilyRelations().get(index).getCloseness(), dependent.getPerson().getRelations().getFamilyRelations().get(index).getAbusivenessFrom()));
                                    familial.getPerson().getRelations().getLover().getPerson().getRelations().getDependentRelations().add(new Dependent(familial.getPerson().getRelations().getLover().getPerson(), dependent.getPerson(), familial.getPerson().getRelations().getLover().getPerson().getRelations().getFamilyRelations().get(familial.getPerson().getRelations().getLover().getPerson().getRelations().getFamily().indexOf(dependent.getPerson())).getCloseness(), familial.getPerson().getRelations().getLover().getPerson().getRelations().getFamilyRelations().get(familial.getPerson().getRelations().getLover().getPerson().getRelations().getFamily().indexOf(dependent.getPerson())).getAbusivenessFrom()));
                                    dependent.getPerson().getRelations().getFamilyRelations().remove(index);
                                    familial.getPerson().getRelations().getLover().getPerson().getRelations().getFamilyRelations().removeIf(familial1 -> familial1.getPerson() == dependent.getPerson());
                                }
                            }
                        }
                        for (Human family : familial.getPerson().getRelations().getFamily()) {
                            if (!dependent.getPerson().getRelations().getFamily().contains(family)) {
                                if (dependent.getPerson() == family) {
                                    continue;
                                }
                                if (dependent.getPerson().getRelations().getFriends().contains(family)) {
                                    dependent.getPerson().getRelations().getFamilyRelations().add(new Familial(dependent.getPerson(), family, dependent.getPerson().getRelations().getFriendships().get(dependent.getPerson().getRelations().getFriends().indexOf(family)).getCloseness(), dependent.getPerson().getRelations().getFriendships().get(dependent.getPerson().getRelations().getFriends().indexOf(family)).getAbusivenessFrom()));
                                    family.getRelations().getFamilyRelations().add(new Familial(family, dependent.getPerson(), family.getRelations().getFriendships().get(family.getRelations().getFriends().indexOf(dependent.getPerson())).getCloseness(), family.getRelations().getFriendships().get(family.getRelations().getFriends().indexOf(dependent.getPerson())).getAbusivenessFrom()));
                                    dependent.getPerson().getRelations().getFriendships().remove(dependent.getPerson().getRelations().getFriends().indexOf(family));
                                    family.getRelations().getFriendships().remove(family.getRelations().getFriends().indexOf(dependent.getPerson()));
                                } else {
                                    dependent.getPerson().getRelations().getFamilyRelations().add(new Familial(dependent.getPerson(), family));
                                    family.getRelations().getFamilyRelations().add(new Familial(family, dependent.getPerson()));
                                }
                            }
                        }
                        break;
                    }
                }
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
            if (human.getBankAccount().getDeposit() > house.getValue() && house.getOwnedBy() == null) {
                house.buy(human.getBankAccount());
                if (human.getAddress() != null) {
                    human.getAddress().setOwnedBy(null);
                    for (Human human1 : human.getRelations().getDependents()) {
                        if (human1.getAddress() == human.getAddress()) {
                            human1.setAddress(null);
                        }
                    }
                }
                human.setAddress(house);
                if (human.getRelations().getLover() != null) {
                    if (human.getRelations().getLover().isMarried()) {
                        human.getRelations().getLover().getPerson().setAddress(house);
                    }
                }
                for (Human human1 : human.getRelations().getDependents()) {
                    if (human1.getAddress() == null) {
                        human1.setAddress(house);
                    }
                }
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
            value = (int) (Math.random() * 9700000) + 300000;
        }
        while (Math.random() * 10 < 9) {
            if (value <= 100000) {
                break;
            }
            value *= 0.9;
        }
        if (world.getCityBudget() < value) {
            value = (int) world.getCityBudget();
        }
        world.getHouses().add(new House(value, true, new Age(), new Position(), null));
        world.citySpending(value);
    }

    public static void repairHouse(World world, House house) {
        if (house.isUsable()) {
            return;
        }
        if (house.getOwnedBy() != null) {
            if (house.getOwnedBy().getDeposit() > house.getValue()) {
                if (house.getOwnedBy().getDeposit() - house.getValue() > Math.random() * 100000) {
                    house.getOwnedBy().spend(house.getValue());
                    house.setUsable(true);
                }
            }
        } else {
            if (world.getCityBudget() > house.getValue()) {
                if (world.getCityBudget() - house.getValue() > Math.random() * 1000000000) {
                    world.citySpending(house.getValue());
                    house.setUsable(true);
                }
            }
        }
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
                if (human.getSkills().checkSkills(career.getSkills()) > Math.random() * 100 - 30) {
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
        int gen = (int) (Math.random() * 22);
        if (gen == 0) {
            world.getJobs().add(new Actor());
        } else if (gen == 1) {
            world.getJobs().add(new Artist());
        } else if (gen == 2) {
            world.getJobs().add(new Author());
        } else if (gen == 3) {
            world.getJobs().add(new Basketball());
        } else if (gen >= 4 && gen <= 9) {
            world.getJobs().add(new ConstructionWorker());
        } else if (gen >= 10 && gen <= 15) {
            world.getJobs().add(new DeliveryPerson());
        } else if (gen == 16) {
            world.getJobs().add(new Musician());
        } else if (gen >= 17 && gen <= 22) {
            world.getJobs().add(new Programmer());
        } else if (gen >= 23 && gen <= 26) {
            world.getJobs().add(new Receptionist());
        } else if (gen >= 27 && gen <= 32) {
            world.getJobs().add(new Waiter());
        }
    }

    public static void findDate(Human human, World world) {
        if (!human.isAlive()) {
            return;
        }
        if (human.getRelations().getLover() != null) {
            return;
        }
        if (human.getAttributes().getHappiness() < Math.random() * 100 - 50) {
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
            }
            if (human1.getRelations().getFriends().contains(human)) {
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
            for (Human dependent1 : human.getRelations().getDependents()) {
                if (!dependent1.isAlive() || dependent.getRelations().getFamily().contains(dependent1) || dependent == dependent1) {
                    continue;
                }
                if (dependent.getRelations().getFriends().contains(dependent1)) {
                    if (!dependent1.getRelations().getFriends().contains(dependent)) {
                        continue;
                    }
                    Platonic dSide = dependent.getRelations().getFriendships().get(dependent.getRelations().getFriends().indexOf(dependent1));
                    dependent.getRelations().getFamilyRelations().add(new Familial(dependent, dependent1, dSide.getCloseness(),  dSide.getAbusivenessFrom()));
                    Platonic d1Side = dependent1.getRelations().getFriendships().get(dependent1.getRelations().getFriends().indexOf(dependent));
                    human.getRelations().getFamilyRelations().add(new Dependent(dependent1, dependent, d1Side.getCloseness(), d1Side.getAbusivenessFrom()));
                    dependent.getRelations().getFriendships().remove(dSide);
                    human.getRelations().getFriendships().remove(d1Side);
                } else {
                    dependent.getRelations().getFamilyRelations().add(new Familial(dependent, dependent1));
                    human.getRelations().getFamilyRelations().add(new Familial(dependent1, dependent));
                }
            }
            for (Human familial : human.getRelations().getFamily()) {
                if (!familial.isAlive() || dependent.getRelations().getFamily().contains(familial) || dependent == familial) {
                    continue;
                }
                if (dependent.getRelations().getFriends().contains(familial)) {
                    Platonic dSide = dependent.getRelations().getFriendships().get(dependent.getRelations().getFriends().indexOf(familial));
                    dependent.getRelations().getFamilyRelations().add(new Familial(dependent, familial, dSide.getCloseness(),  dSide.getAbusivenessFrom()));
                    if (familial.getRelations().getFriends().contains(dependent)) {
                        Platonic fSide = familial.getRelations().getFriendships().get(familial.getRelations().getFriends().indexOf(dependent));
                        human.getRelations().getFamilyRelations().add(new Dependent(familial, dependent, fSide.getCloseness(), fSide.getAbusivenessFrom()));
                        human.getRelations().getFriendships().remove(fSide);
                    }
                    dependent.getRelations().getFriendships().remove(dSide);
                } else {
                    dependent.getRelations().getFamilyRelations().add(new Familial(dependent, familial));
                    human.getRelations().getFamilyRelations().add(new Familial(familial, dependent));
                }
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
        double gen = 100;
        if (human.getAge().getYears() < 25) {
            gen /=  25 - human.getAge().getYears();
        }
        if (human.getRelations().getLover().getPerson().getAge().getYears() < 25) {
            gen /= 25 - human.getRelations().getLover().getPerson().getAge().getYears();
        }
        if (gen <= 0.001) {
            gen = 0.001;
        }
        gen *= human.getBankAccount().getDeposit() / 50000;
        gen *= human.getRelations().getLover().getCloseness() / 100.0;
        gen /= (human.getRelations().getDependentRelations().size() + 1) * (human.getRelations().getDependentRelations().size() + 1) * (human.getRelations().getDependentRelations().size() + 1);
        if (gen > Math.random()) {
            Human child = new Human(human, human.getRelations().getLover().getPerson());
            for (Human sibling : human.getRelations().getDependents()) {
                if (child == sibling) {
                    continue;
                }
                child.getRelations().getFamilyRelations().add(new Familial(child, sibling));
                sibling.getRelations().getFamilyRelations().add(new Familial(sibling, child));
            }
            human.getRelations().getDependentRelations().add(new Dependent(human, child));
            human.getRelations().getLover().getPerson().getRelations().getDependentRelations().add(new Dependent(human.getRelations().getLover().getPerson(), child));
            world.getToAdd().add(child);
            child.getRelations().getFamilyRelations().addAll(human.getRelations().getFamilyRelations());
            ArrayList<Human> family = child.getRelations().getFamily();
            for (Familial familial : human.getRelations().getLover().getPerson().getRelations().getFamilyRelations()) {
                if (!family.contains(familial.getPerson())) {
                    if (child == familial.getPerson()) {
                        continue;
                    }
                    child.getRelations().getFamilyRelations().add(new Familial(child, familial.getPerson()));
                }
            }
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
                        dependent.getPerson().getAttributes().changeTrauma(5);
                        dependent.getPerson().getAttributes().changeHappiness(-50);
                    }
                }
            }
            romantic.getPerson().setBankAccount(new BankAccount(romantic.getSelf().getBankAccount().getDeposit() / 2));
            romantic.getPerson().getBankAccount().setDeposit(romantic.getSelf().getBankAccount().getDeposit() / 2);
        }
        romantic.getSelf().getAttributes().changeHappiness(-romantic.getCloseness() * (Math.random() / 1.5 + 0.5));
        romantic.getSelf().getRelations().setLover(null);
        if (romantic.getPerson().getRelations().getLover() != null) {
            romantic.getPerson().getAttributes().changeHappiness(-romantic.getPerson().getRelations().getLover().getCloseness() * (Math.random() / 1.5 + 0.5));
            romantic.getPerson().getRelations().setLover(null);
        }
    }

    public static void newFriend(Human human, World world) {
        if (!human.isAlive()) {
            return;
        }
        if (Math.random() * world.getDaysPerYear() > 5) {
            return;
        }
        if (human.getAttributes().getHappiness() < Math.random() * 100 - 50) {
            return;
        }
        if (human.getRelations().getFriendships().size() / (Math.random() + 0.0001) / human.getAttributes().getPersonality().getSociability() > 30) {
            return;
        }
        for (Human human1 : world.getHumans()) {
            //TODO: Make it so that more friends = less likely to get even more friends
            if (!human1.isAlive()) {
                continue;
            }
            if (Math.random() * Math.abs(human.getAge().getYears() - human1.getAge().getYears()) > 1) {
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
        if (!platonic.getPerson().getRelations().getFriends().contains(platonic.getSelf())) {
            return;
        }
        platonic.getPerson().getRelations().getFriendships().remove(platonic.getPerson().getRelations().getFriends().indexOf(platonic.getSelf()));
    }

    public static void hospital(Human human, BankAccount bankAccount) {
        human.getAttributes().changeHealth(Math.random() * 10);
        bankAccount.spend(5000);
    }

    public static void hospital2(Human human, World world) {
        if (world.getCityBudget() >= 10000) {
            human.getAttributes().changeHealth(Math.random() * 20);
            world.citySpending(10000);
        }
    }
}
