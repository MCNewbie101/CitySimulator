package Actors;

import Buildings.House;
import Events.RandomEvents;
import GameSystems.*;
import GameSystems.Careers.Career;
import GameSystems.Careers.Retired;
import GameSystems.Relations.Caretaker;
import GameSystems.Relations.Familial;
import GameSystems.Relations.Platonic;
import GameSystems.Relations.Relations;
import GameSystems.Skills.Skills;
import World.World;

import java.util.ArrayList;

public class Human {
    private Age age;
    private String gender;
    private Skills skills;
    private Career job;
    private Retired retirement;
    private Education education;
    private House address;
    private Attributes attributes;
    private Relations relations;
    private BankAccount bankAccount;
    private boolean isAlive;

    public Human(int daysPerYear, World world) {
        age = new Age((int) (Math.random() * 30) + 18, (int) (Math.random() * daysPerYear));
        if (Math.random() * 2 < 1) {
            gender = "female";
        } else {
            gender = "male";
        }
        skills = new Skills();
        int gen = (int) (Math.random() * 1000000);
        while (gen > 10000) {
            if (Math.random() * 10 < 9) {
                gen *= 0.99;
            } else {
                break;
            }
        }
        bankAccount = new BankAccount(gen);
        for (House house : world.getHouses()) {
            if (house.getOwnedBy() == null) {
                house.setOwnedBy(bankAccount);
                address = house;
                break;
            }
        }
        retirement = null;
        attributes = new Attributes();
        education = new Education(attributes);
        education.setGradeLevel(12);
        relations = new Relations();
        isAlive = true;
    }

    public Human(Human parent1, Human parent2) {
        age = new Age();
        if (Math.random() * 2 < 1) {
            gender = "female";
        } else {
            gender = "male";
        }
        skills = new Skills(parent1.getSkills(), parent2.getSkills());
        job = null;
        retirement = null;
        education = null;
        if (Math.random() * 2 < 1) {
            address = parent1.getAddress();
        } else {
            address = parent2.getAddress();
        }
        attributes = new Attributes(parent1.getAttributes(), parent2.getAttributes());
        relations = new Relations(this, parent1, parent2);
        bankAccount = new BankAccount();
        isAlive = true;
    }

    public Human(Age age, String gender, Skills skills, Career job, Education education, House address, Attributes attributes, Relations relations, BankAccount bankAccount, Retired retirement) {
        this.age = age;
        this.gender = gender;
        this.skills = skills;
        this.job = job;
        this.retirement = retirement;
        this.education = education;
        this.address = address;
        this.attributes = attributes;
        this.relations = relations;
        this.bankAccount = bankAccount;
        isAlive = true;
    }

    public void update(int daysPerYear, int skillIncreaseBalancing, World world) {
        if (!isAlive) {
            if (bankAccount != null) {
                if (!relations.getFamily().isEmpty()) {
                    if (relations.getLover() == null) {
                        RandomEvents.inheritance(this);
                    } else if (!relations.getLover().isMarried() || !relations.getLover().getPerson().isAlive) {
                        RandomEvents.inheritance(this);
                    }
                }
            }
            RandomEvents.humanDeath(this, world);
        }
        age.update(daysPerYear);
        if (age.getYears() < 18 && relations.getCaretakers().isEmpty()) {
            attributes.isOrphan(this, daysPerYear);
        }
        skills.update(job, skillIncreaseBalancing, daysPerYear);
        if (job != null) {
            job.update(this);
        } else if (age.getYears() >= 18) {
            RandomEvents.jobSearch(this, world);
        }
        if (age.getYears() == 5) {
            education = new Education(attributes);
        } else if (age.getYears() > 5 && age.getYears() <= 18) {
            education.update(skills, attributes, skillIncreaseBalancing, daysPerYear);
        }
        if (address == null) {
            if (age.getYears() >= 18) {
                RandomEvents.houseSearch(this, world);
            } else {
                for (Human caretaker : relations.getCaretakers()) {
                    if (caretaker.getAddress().isUsable()) {
                        address = caretaker.getAddress();
                    }
                    break;
                }
            }
            if (address == null) {
                attributes.updateHouse(this, daysPerYear);
            }
        } else if (!address.isUsable()) {
            if (age.getYears() >= 18) {
                RandomEvents.houseSearch(this, world);
            } else {
                for (Human caretaker : relations.getCaretakers()) {
                    if (caretaker.getAddress().isUsable()) {
                        address = caretaker.getAddress();
                    }
                    break;
                }
            }
            if (!address.isUsable()) {
                attributes.updateHouse(this, daysPerYear);
            }
        }
        attributes.update(this, daysPerYear);
        relations.update(this, daysPerYear);
        if (bankAccount != null) {
            bankAccount.update(job, world, address, daysPerYear);
        }
        if (age.getYears() >= 18) {
            if (bankAccount.getDeposit() < 30000) {
                world.aid(this);
            }
            if (bankAccount.getDeposit() < 40000) {
                for (Familial familial : relations.getFamilyRelations()) {
                    if (familial.getCloseness() - familial.getAbusivenessFrom() > Math.random() * 100) {
                        familial.getPerson().aid(this);
                    }
                }
            }
            if (bankAccount.getDeposit() < 40000) {
                for (Platonic friend : relations.getFriendships()) {
                    if (friend.getCloseness() - friend.getAbusivenessFrom() > Math.random() * 300) {
                        friend.getPerson().aid(this);
                    }
                }
            }
            attributes.updateMoney(this, bankAccount, daysPerYear);
        }
    }

    //TODO: Is this realistic? Maybe check again later?
    private void aid(Human human) {
        if (bankAccount.getDeposit() < 30000 && attributes.getPersonality().getSelfishness() > 5) {
            return;
        }
        if (bankAccount.getDeposit() < 40000 && attributes.getPersonality().getSelfishness() > 10) {
            return;
        }
        if (bankAccount.getDeposit() < 100000 && attributes.getPersonality().getSelfishness() > 50) {
            return;
        }
        double needed = 40000 - human.bankAccount.getDeposit();
        double give = bankAccount.getDeposit() * Math.random() * (100 - attributes.getPersonality().getSelfishness()) / 1000;
        if (give > needed) {
            give = needed;
        }
        human.bankAccount.deposit(give);
        bankAccount.spend(give);
    }

    public Age getAge() {
        return age;
    }

    public void setAge(Age age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Skills getSkills() {
        return skills;
    }

    public void setSkills(Skills skills) {
        this.skills = skills;
    }

    public Career getJob() {
        return job;
    }

    public void setJob(Career job) {
        this.job = job;
    }

    public Education getEducation() {
        return education;
    }

    public void setEducation(Education education) {
        this.education = education;
    }

    public House getAddress() {
        return address;
    }

    public void setAddress(House address) {
        this.address = address;
    }

    public Attributes getAttributes() {
        return attributes;
    }

    public void setAttributes(Attributes attributes) {
        this.attributes = attributes;
    }

    public Relations getRelations() {
        return relations;
    }

    public void setRelations(Relations relations) {
        this.relations = relations;
    }

    public BankAccount getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(BankAccount bankAccount) {
        this.bankAccount = bankAccount;
    }

    public boolean isAlive() {
        return isAlive;
    }

    public void setAlive(boolean alive) {
        isAlive = alive;
    }

    public void printInfo() {
        age.printInfo();
        System.out.println(gender);
//        skills.printInfo();
        if (job != null) {
            job.printInfo();
        } else {
            System.out.println("Jobless");
        }
        if (address != null) {
            System.out.println("Has house");
        } else {
            System.out.println("No house");
        }
        attributes.printInfo();
        bankAccount.printInfo();
    }
}
