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
    private int s;
    private boolean isAlive;

    public Human(World world) {
        age = new Age((int) (Math.random() * 12) + 18, (int) (Math.random() * world.getDaysPerYear()));
        if (Math.random() * 2 < 1) {
            gender = "female";
        } else {
            gender = "male";
        }
        skills = new Skills();
        for (int i = 0; i < age.getYears(); i++) {
            skills.update(world.getSkillIncreaseBalancing(), 1);
        }
        int gen = (int) (Math.random() * 3000000);
        while (gen > 100000) {
            if (Math.random() * 10 < 9) {
                gen *= 0.9;
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
        for (int i = 5; i < 18; i++) {
            skills.update(education, world.getSkillIncreaseBalancing(), world.getJobSkillIncreaseBalancing(), world.getDaysPerYear());
        }
        relations = new Relations();
        gen = (int) (Math.random() * 100);
        if (gen < 90) {
            s = 0;
        } else if (gen < 93) {
            s = 1;
        } else if (gen < 97) {
            s = 2;
        } else {
            s = 3;
        }
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

    public Human(World world, Age age, String gender, Skills skills, Education education, Attributes attributes, BankAccount bankAccount) {
        this.age = age;
        this.gender = gender;
        this.skills = skills;
        for (int i = 0; i < age.getYears(); i++) {
            skills.update(world.getSkillIncreaseBalancing(), 1);
        }
        this.job = null;
        this.retirement = null;
        this.education = education;
        this.address = null;
        this.attributes = attributes;
        this.relations = new Relations();
        this.bankAccount = bankAccount;
        isAlive = true;
    }

    public Human(World world, Age age, String gender, Skills skills,  BankAccount bankAccount) {
        this.age = age;
        this.gender = gender;
        this.skills = skills;
        for (int i = 0; i < age.getYears(); i++) {
            skills.update(world.getSkillIncreaseBalancing(), 1);
        }
        this.job = null;
        this.retirement = null;
        this.address = null;
        attributes = new Attributes();
        education = new Education(attributes);
        this.relations = new Relations();
        this.bankAccount = bankAccount;
        isAlive = true;
    }

    public void update(World world) {
        if (!isAlive) {
            if (bankAccount != null) {
                if (!relations.getFamily().isEmpty()) {
                    if (relations.getLover() == null) {
                        RandomEvents.inheritance(this, world);
                    } else if (!relations.getLover().isMarried() || !relations.getLover().getPerson().isAlive) {
                        RandomEvents.inheritance(this, world);
                    }
                }
            }
            RandomEvents.humanDeath(this, world);
            return;
        }
        age.update(world.getDaysPerYear());
        if (age.getYears() < 18 && relations.getCaretakers().isEmpty()) {
            attributes.isOrphan(this, world.getDaysPerYear());
        }
        if (age.getYears() == 18) {
            address = null;
        }
        skills.update(world.getSkillIncreaseBalancing(), world.getDaysPerYear());
        if (age.getYears() >= 18) {
            skills.update(job, world.getSkillIncreaseBalancing(), world.getJobSkillIncreaseBalancing(), world.getDaysPerYear());
        } else {
            skills.update(education, world.getSkillIncreaseBalancing(), world.getJobSkillIncreaseBalancing(), world.getDaysPerYear());
        }
        if (job != null) {
            job.update(this);
        } else if (age.getYears() >= 18 && retirement == null) {
            RandomEvents.jobSearch(this, world);
        }
        if (age.getYears() == 5) {
            education = new Education(attributes);
        } else if (age.getYears() > 5 && age.getYears() <= 18) {
            education.update(skills, attributes, world.getSkillIncreaseBalancing(), world.getJobSkillIncreaseBalancing(), world.getDaysPerYear());
        }
        if (address == null) {
            if (age.getYears() >= 18) {
                RandomEvents.houseSearch(this, world);
            } else {
                for (Human caretaker : relations.getCaretakers()) {
                    if (caretaker.getAddress() != null) {
                        if (caretaker.getAddress().isUsable()) {
                            address = caretaker.getAddress();
                        }
                    }
                    break;
                }
            }
            if (address == null) {
                attributes.updateHouse(this, world.getDaysPerYear());
            }
        } else if (!address.isUsable()) {
            if (age.getYears() >= 18) {
                RandomEvents.houseSearch(this, world);
            } else {
                for (Human caretaker : relations.getCaretakers()) {
                    if (caretaker.getAddress() != null) {
                        if (caretaker.getAddress().isUsable()) {
                            address = caretaker.getAddress();
                        }
                        break;
                    }
                }
            }
            if (!address.isUsable()) {
                attributes.updateHouse(this, world.getDaysPerYear());
            }
        }
        attributes.update(this, world.getDaysPerYear());
        relations.update(world.getDaysPerYear());
        if (relations.getLover() == null) {
            if (age.getYears() >= 16 && s != 4) {
                RandomEvents.findDate(this, world);
            }
        } else if (!relations.getLover().isMarried()) {
            if (age.getYears() > 18 && relations.getLover().getPerson().getAge().getYears() > 18) {
                RandomEvents.marriage(this, world.getDaysPerYear());
            }
        } else {
            if (gender.equals("female")) {
                RandomEvents.childBirth(this, world);
            }
        }
        RandomEvents.newFriend(this, world);
        if (bankAccount != null) {
            bankAccount.update(job, retirement, world, address, world.getDaysPerYear());
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
            attributes.updateMoney(this, bankAccount, world.getDaysPerYear());
        } else {
            BankAccount parentAccount = null;
            for (Human human : relations.getCaretakers()) {
                if (parentAccount == null) {
                    parentAccount = human.getBankAccount();
                } else if (human.getBankAccount().getDeposit() > parentAccount.getDeposit()) {
                    parentAccount = human.getBankAccount();
                }
            }
            if (parentAccount == null) {
                attributes.updateMoney(this, null, world.getDaysPerYear());
            }
        }
        if (age.getYears() >= 16 && s != 4 && relations.getLover() == null) {
            RandomEvents.findDate(this, world);
        }
        if (age.getYears() >= 18) {
            while (true) {
                if (attributes.getHealth() < 90 && bankAccount.getDeposit() > 100000) {
                    if (attributes.getHappiness() > Math.random() * 200) {
                        RandomEvents.hospital(this, bankAccount);
                    } else {
                        break;
                    }
                } else if (attributes.getHealth() < 80 && bankAccount.getDeposit() > 70000) {
                    if (attributes.getHappiness() > Math.random() * 200 - 10) {
                        RandomEvents.hospital(this, bankAccount);
                    } else {
                        break;
                    }
                } else if (attributes.getHealth() < 70 && bankAccount.getDeposit() > 49000) {
                    if (attributes.getHappiness() > Math.random() * 200 - 20) {
                        RandomEvents.hospital(this, bankAccount);
                    } else {
                        break;
                    }
                } else if (attributes.getHealth() < 60 && bankAccount.getDeposit() > 34300) {
                    if (attributes.getHappiness() > Math.random() * 200 - 30) {
                        RandomEvents.hospital(this, bankAccount);
                    } else {
                        break;
                    }
                } else if (attributes.getHealth() < 50 && bankAccount.getDeposit() > 240100) {
                    if (attributes.getHappiness() > Math.random() * 200 - 40) {
                        RandomEvents.hospital(this, bankAccount);
                    } else {
                        break;
                    }
                } else if (attributes.getHealth() < 40 && bankAccount.getDeposit() > 168070) {
                    if (attributes.getHappiness() > Math.random() * 200 - 50) {
                        RandomEvents.hospital(this, bankAccount);
                    } else {
                        break;
                    }
                } else if (attributes.getHealth() < 30 && bankAccount.getDeposit() > 117649) {
                    if (attributes.getHappiness() > Math.random() * 200 - 60) {
                        RandomEvents.hospital(this, bankAccount);
                    } else {
                        break;
                    }
                } else if (attributes.getHealth() < 20) {
                    if (Math.random() * 10 < 1) {
                        break;
                    }
                    RandomEvents.hospital2(this, world);
                } else {
                    break;
                }
            }
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

    public Retired getRetirement() {
        return retirement;
    }

    public void setRetirement(Retired retirement) {
        this.retirement = retirement;
    }

    public int getS() {
        return s;
    }

    public void setS(int s) {
        this.s = s;
    }

    public void printInfo() {
        //TODO: Make this more readable
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
