package GameSystems.Relations;

import Actors.Human;
import GameSystems.Age;
import World.World;

import java.util.ArrayList;

public class Relations {
    private Romantic lover;
    private ArrayList<Caretaker> caretakers;
    private ArrayList<Dependent> dependents;
    private ArrayList<Familial> family;
    private ArrayList<Platonic> friends;

    public Relations() {
        lover = null;
        caretakers = new ArrayList<>();
        dependents = new ArrayList<>();
        family = new ArrayList<>();
        friends = new ArrayList<>();
    }

    public Relations(Human self, Human parent1, Human parent2) {
        caretakers = new ArrayList<>();
        caretakers.add(new Caretaker(self, parent1));
        caretakers.add(new Caretaker(self, parent2));
        dependents = new ArrayList<>();
        family = new ArrayList<>();
        for (Familial familial : parent1.getRelations().getFamilyRelations()) {
            if (familial.getPerson().isAlive()) {
                family.add(new Familial(self, familial.getPerson()));
            }
        }
        for (Familial familial : parent2.getRelations().getFamilyRelations()) {
            boolean key = false;
            for (CloseRelation closeRelation1 : family) {
                if (closeRelation1.getPerson() == familial.getPerson()) {
                    key = true;
                    break;
                }
            }
            if (!key && familial.getPerson().isAlive()) {
                family.add(new Familial(self, familial.getPerson()));
            }
        }
        friends = new ArrayList<>();
        lover = null;
    }

    public void update (int daysPerYear) {
        if (lover != null) {
            lover.update(daysPerYear);
        }
        if (!caretakers.isEmpty()) {
            ArrayList<Caretaker> remove = new ArrayList<>();
            for (Caretaker caretaker : caretakers) {
                if (caretaker.getSelf().getAge().getYears() >= 18) {
                    remove.add(caretaker);
                    caretaker.getSelf().getRelations().getFamilyRelations().add(new Familial(caretaker.getSelf(), caretaker.getPerson(), caretaker.getCloseness(), caretaker.getAbusivenessFrom()));
                } else {
                    caretaker.update(daysPerYear);
                }
            }
            for (Caretaker caretaker : remove) {
                caretakers.remove(caretaker);
            }
        }
        if (!dependents.isEmpty()) {
            ArrayList<Dependent> remove = new ArrayList<>();
            for (Dependent dependent : dependents) {
                if (dependent.getPerson().getAge().getYears() >= 18) {
                    remove.add(dependent);
                    dependent.getSelf().getRelations().getFamilyRelations().add(new Familial(dependent.getSelf(), dependent.getPerson(), dependent.getCloseness(), dependent.getAbusivenessFrom()));
                } else {
                    dependent.update(daysPerYear);
                }
            }
            for (Dependent dependent : remove) {
                dependents.remove(dependent);
            }
        }
        if (!family.isEmpty()) {
            for (Familial familial : family) {
                familial.update(daysPerYear);
            }
        }
        if (!friends.isEmpty()) {
            ArrayList<Platonic> remove = new ArrayList<>();
            for (Platonic platonic : friends) {
                platonic.update(remove, daysPerYear);
            }
            for (Platonic platonic : remove) {
                friends.remove(platonic);
            }
        }
    }

    public ArrayList<Caretaker> getCaretakerRelations() {
        return caretakers;
    }

    public ArrayList<Human> getCaretakers() {
        ArrayList<Human> humans = new ArrayList<>();
        for (Caretaker caretaker : caretakers) {
            humans.add(caretaker.getPerson());
        }
        return humans;
    }

    public void setCaretakers(ArrayList<Caretaker> caretakers) {
        this.caretakers = caretakers;
    }

    public ArrayList<Dependent> getDependentRelations() {
        return dependents;
    }

    public ArrayList<Human> getDependents() {
        ArrayList<Human> humans = new ArrayList<>();
        for (Dependent dependent : dependents) {
            humans.add(dependent.getPerson());
        }
        return humans;
    }

    public void setDependents(ArrayList<Dependent> dependents) {
        this.dependents = dependents;
    }

    public ArrayList<Familial> getFamilyRelations() {
        return family;
    }

    public ArrayList<Human> getFamily() {
        ArrayList<Human> humans = new ArrayList<>();
        for (Familial familial : family) {
            humans.add(familial.getPerson());
        }
        return humans;
    }

    public void setFamily(ArrayList<Familial> family) {
        this.family = family;
    }

    public ArrayList<Platonic> getFriendships() {
        return friends;
    }

    public ArrayList<Human> getFriends() {
        ArrayList<Human> humans = new ArrayList<>();
        for (Platonic friend : friends) {
            humans.add(friend.getPerson());
        }
        return humans;
    }

    public void setFriends(ArrayList<Platonic> friends) {
        this.friends = friends;
    }

    public Romantic getLover() {
        return lover;
    }

    public void setLover(Romantic lover) {
        this.lover = lover;
    }

    public void printInfo(Age age) {
        if (age.getYears() > 18) {
            if (lover == null) {
                System.out.println("Single");
            } else if (!lover.isMarried()) {
                System.out.println("Dating");
            } else {
                System.out.println("Married");
            }
            System.out.println("Number of dependants: " + dependents.size());
        } else {
            System.out.println("Number of caretakers: " + caretakers.size());
        }
        System.out.println("Number of other family members: " + family.size());
        System.out.println("Number of friends: " + friends.size());
    }

    public void printDetails(World world, Age age) {
        if (age.getYears() > 18) {
            if (lover == null) {
                System.out.println("Single");
            } else if (!lover.isMarried()) {
                System.out.println("Dating");
                System.out.println("Partner ID: " + world.getHumans().indexOf(lover.getPerson()));
                System.out.println("Closeness: " + lover.getCloseness());
            } else {
                System.out.println("Married");
                System.out.println("Partner ID: " + world.getHumans().indexOf(lover.getPerson()));
                System.out.println("Closeness: " + lover.getCloseness());
            }
            System.out.println("Number of dependants: " + dependents.size());
            for (Dependent dependent : dependents) {
                System.out.println("Dependent ID: " + world.getHumans().indexOf(dependent.getPerson()));
                System.out.println("Dependent age: " + dependent.getPerson().getAge().getYears());
                System.out.println("Closeness: " + dependent.getCloseness());
            }
        } else {
            System.out.println("Number of caretakers: " + caretakers.size());
            for (Caretaker caretaker : caretakers) {
                System.out.println("Caretaker ID: " + world.getHumans().indexOf(caretaker.getPerson()));
                System.out.println("Closeness: " + caretaker.getCloseness());
            }
        }
        System.out.println("Number of other family members: " + family.size());
        for (Familial familial : family) {
            System.out.println("Family member ID: " + world.getHumans().indexOf(familial.getPerson()));
            System.out.println("Closeness: " + familial.getCloseness());
        }
        System.out.println("Number of friends: " + friends.size());
        for (Platonic platonic : friends) {
            System.out.println("Friend ID: " + world.getHumans().indexOf(platonic.getPerson()));
            System.out.println("Closeness: " + platonic.getCloseness());
        }
    }
}
