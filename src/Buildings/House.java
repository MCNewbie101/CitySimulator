package Buildings;

import GameSystems.BankAccount;
import GameSystems.Position;
import GameSystems.Age;

public class House extends Building{
    private BankAccount ownedBy;

    public House() {
        super();
    }

    public House(int value, boolean inhabitable, Age age, Position position, BankAccount ownedBy) {
        super(value, inhabitable, age, position);
        this.ownedBy = ownedBy;
    }

    public void buy(BankAccount ownedBy) {
        this.ownedBy = ownedBy;
        ownedBy.spend(getValue());
    }

    public BankAccount getOwnedBy() {
        return ownedBy;
    }

    public void setOwnedBy(BankAccount ownedBy) {
        this.ownedBy = ownedBy;
    }
}
