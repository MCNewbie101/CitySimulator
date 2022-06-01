package GameSystems.Relations;

import Actors.Human;

import java.util.ArrayList;

public class Relations {
    private Romantic lover;
    private ArrayList<Caretaker> caretakers;
    private ArrayList<Dependent> dependents;
    private ArrayList<Familial> family;
    private ArrayList<Platonic> friends;
    private ArrayList<Relation> other;

    public Relations() {
        lover = null;
        caretakers = new ArrayList<>();
        dependents = new ArrayList<>();
        family = new ArrayList<>();
        friends = new ArrayList<>();
        other = new ArrayList<>();
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
        other = new ArrayList<>();
        lover = null;
    }

    public void update(Human self, int daysPerYear) {
        if (lover != null) {
            lover.update(daysPerYear);
        }
        if (!caretakers.isEmpty()) {
            for (Caretaker caretaker : caretakers) {
                caretaker.update(self, daysPerYear);
            }
        }
        if (!family.isEmpty()) {
            for (Familial familial : family) {
                familial.update(self, daysPerYear);
            }
        }
        if (!friends.isEmpty()) {
            for (Platonic platonic : friends) {
                platonic.update(self, daysPerYear);
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

    public ArrayList<Relation> getOtherRelations() {
        return other;
    }

    public ArrayList<Human> getOther() {
        ArrayList<Human> humans = new ArrayList<>();
        for (Relation relation : other) {
            humans.add(relation.getPerson());
        }
        return humans;
    }

    public void setOther(ArrayList<Relation> other) {
        this.other = other;
    }

    public Romantic getLover() {
        return lover;
    }

    public void setLover(Romantic lover) {
        this.lover = lover;
    }
}
