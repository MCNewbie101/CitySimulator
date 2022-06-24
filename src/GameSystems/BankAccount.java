package GameSystems;

import Buildings.House;
import GameSystems.Careers.Career;
import GameSystems.Careers.Retired;
import World.World;

public class BankAccount {
    private double deposit;
    private final int INTEREST_RATE = 5;
    private final int[] INCOME_TAX = {3, 5, 10, 17, 30, 50};
    private final int HOUSE_TAX = 3;

    public BankAccount() {
        deposit = 0;
    }

    public BankAccount(double deposit) {
        this.deposit = deposit;
    }

    /*
     * Updates a human's deposit based on career and housing status
     */
    public void update(Career job, Retired retired, World world, House house, int daysPerYear) {
        deposit += deposit * INTEREST_RATE / daysPerYear;
        double earning = 0;
        if (job != null) {
            earning = job.getSalary() / daysPerYear;
        } else if (retired != null) {
            deposit += retired.getPension();
        }
        if (earning > 1000000.0 / daysPerYear) {
            deposit += (earning - 1000000.0 / daysPerYear) * (100 - INCOME_TAX[5]);
            world.addBudget((earning - 1000000.0 / daysPerYear) * INCOME_TAX[5]);
            earning = 1000000.0 / daysPerYear;

        }
        if (earning > 500000.0 / daysPerYear) {
            deposit += (earning - 500000.0 / daysPerYear) * (100 - INCOME_TAX[4]);
            world.addBudget((earning - 500000.0 / daysPerYear) * INCOME_TAX[4]);
            earning = 500000.0 / daysPerYear;
        }
        if (earning > 250000.0 / daysPerYear) {
            deposit += (earning - 250000.0 / daysPerYear) * (100 - INCOME_TAX[3]);
            world.addBudget((earning - 250000.0 / daysPerYear) * INCOME_TAX[3]);
            earning = 250000.0 / daysPerYear;
        }
        if (earning > 125000.0 / daysPerYear) {
            deposit += (earning - 125000.0 / daysPerYear) * (100 - INCOME_TAX[2]);
            world.addBudget((earning - 125000.0 / daysPerYear) * INCOME_TAX[2]);
            earning = 125000.0 / daysPerYear;
        }
        if (earning > 62500.0 / daysPerYear) {
            deposit += (earning - 62500.0 / daysPerYear) * (100 - INCOME_TAX[1]);
            world.addBudget((earning - 62500.0 / daysPerYear) * INCOME_TAX[1]);
            earning = 62500.0 / daysPerYear;
        }
        if (earning > 31250.0 / daysPerYear) {
            deposit += (earning - 31250.0 / daysPerYear) * (100 - INCOME_TAX[0]);
            world.addBudget((earning - 31250.0 / daysPerYear) * INCOME_TAX[0]);
            earning = 31250;
        }
        deposit += earning;
        if (house != null) {
            deposit -= house.getValue() * HOUSE_TAX / daysPerYear;
        }
        if (deposit < 0) {
            deposit = 0;
        }
        int temp = (int) (deposit * 100);
        deposit = temp / 100.0;
    }

    public void spend(double money) {
        deposit -= money;
        if (deposit < 0) {
            deposit = 0;
        }
    }

    public void deposit(double money) {
        deposit += money;
    }

    public double getDeposit() {
        return deposit;
    }

    public void setDeposit(double deposit) {
        this.deposit = deposit;
    }

    public void printInfo() {
        System.out.println("Bank balance: " + deposit);
    }
}
