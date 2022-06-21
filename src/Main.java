import Actors.Human;
import Events.PlayerEvents;
import Events.RandomEvents;
import GameSystems.Age;
import GameSystems.BankAccount;
import GameSystems.Skills.*;
import World.World;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.out.println("Please input the initial population:");
        int population = initPopulation();
        World city = new World(population, population);
        city.printTrackedInfo();
        while (!city.getHumans().isEmpty()) {
            if (nextCommand(city)) {
                for (int i = 0; i < city.getDaysPerUpdate(); i++) {
                    city.update();
                }
                city.printTrackedInfo();
            } else {
                break;
            }
        }
    }

    public static int initPopulation() {
        Scanner scanner = new Scanner(System.in);
        String inputs = scanner.nextLine();
        if (inputs.chars().allMatch(Character::isDigit)) {
            int num = Integer.parseInt(inputs);
            if (num > 1000 || num < 10) {
                System.out.println("Please input an integer between 10 and 1000.");
                return initPopulation();
            } else {
                return num;
            }
        } else {
            System.out.println("Please input an integer between 10 and 1000.");
            return initPopulation();
        }
    }

    public static boolean nextCommand(World city) {
        String[] commands = {"+", "quit", "set days", "set update", "add tracked person", "add random tracked", "earthquake", "view info", "add tracked from city"};
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please input the next command");
        String inputs = scanner.nextLine();
        if (inputs.equals(commands[0])) {
            return true;
        } else if (inputs.equals(commands[1])) {
            System.out.println("Quitting...");
            return false;
        } else if (inputs.equals(commands[2])) {
            setDays(city);
            return nextCommand(city);
        } else if (inputs.equals(commands[3])) {
            setUpdate(city);
            return nextCommand(city);
        } else if (inputs.equals(commands[4])) {
            addTracked(city);
            return nextCommand(city);
        } else if (inputs.equals(commands[5])) {
            Human human = new Human(city);
            city.getHumans().add(human);
            city.getTracked().add(human);
            return nextCommand(city);
        } else if (inputs.equals(commands[6])) {
            System.out.println("How intense should this earthquake be?");
            int intensity = addInt0_100();
            if (intensity != -1) {
                PlayerEvents.randomEarthQuake(city, intensity);
            }
            return nextCommand(city);
        } else if (inputs.equals(commands[7])) {
            city.printTrackedInfo();
            return nextCommand(city);
        } else if (inputs.equals(commands[8])) {
            addFrom(city);
            return nextCommand(city);
        } else {
            System.out.println("Invalid command, please try again. Please keep all commands in lowercase.");
            System.out.println("Valid commands are:");
            for (String c : commands) {
                System.out.println(c);
            }
            return nextCommand(city);
        }
    }

    public static void setDays(World city) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please input how many days a year should have.");
        String inputs = scanner.nextLine();
        if (!inputs.equals("back")) {
            if (inputs.chars().allMatch( Character::isDigit)) {
                int days = Integer.parseInt(inputs);
                if (days > 10) {
                    System.out.println("Please input an integer between 1 and 10, or input \"back\" if you wish to cancel the command.");
                    setDays(city);
                } else {
                    city.setDaysPerYear(days);
                }
            } else {
                System.out.println("Please input an integer between 1 and 10, or input \"back\" if you wish to cancel the command.");
                setDays(city);
            }
        }
    }

    public static void setUpdate(World city) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please input how many days to update each time:");
        String inputs = scanner.nextLine();
        if (!inputs.equals("back")) {
            if (inputs.chars().allMatch(Character::isDigit)) {
                int days = Integer.parseInt(inputs);
                if (days > 10) {
                    System.out.println("Please input an integer between 1 and 10, or input \"back\" if you wish to cancel the command.");
                    setUpdate(city);
                } else {
                    city.setDaysPerUpdate(days);
                }
            } else {
                System.out.println("Please input an integer between 1 and 10, or input \"back\" if you wish to cancel the command.");
                setUpdate(city);
            }
        }
    }

    public static void addTracked(World city) {
        System.out.println("How old is the person you want to add?");
        int age = addInt0_100();
        if (age == -1) {
            return;
        }
        System.out.println("Is the person you want to add female or male?");
        String gender = addGender();
        if (gender.equals("b")) {
            return;
        }
        System.out.println("Please input how talented the person will be in each of the following categories:");
        System.out.println("Creativity:");
        int c = addInt0_100();
        if (c == -1) {
            return;
        }
        System.out.println("Intelligence:");
        int m = addInt0_100();
        if (m == -1) {
            return;
        }
        System.out.println("Physical talents:");
        int p = addInt0_100();
        if (p == -1) {
            return;
        }
        System.out.println("Social talents:");
        int s = addInt0_100();
        if (s == -1) {
            return;
        }
        System.out.println("How much money does this person have?");
        int money = addMoney();
        if (money == -1) {
            return;
        }
        Human human = new Human(city, new Age(age, 0), gender, new Skills(new Creativity(c), new Mental(m), new Physical(p), new Social(s)), new BankAccount(m));
        city.getTracked().add(human);
        city.getHumans().add(human);
    }

    public static int addInt0_100() {
        Scanner scanner = new Scanner(System.in);
        String inputs = scanner.nextLine();
        if (!inputs.equals("back")) {
            if (inputs.chars().allMatch(Character::isDigit)) {
                int num = Integer.parseInt(inputs);
                if (num > 100) {
                    System.out.println("Please input an integer between 0 and 100, or input \"back\" if you wish to cancel the command.");
                    return addInt0_100();
                } else {
                    return num;
                }
            } else {
                System.out.println("Please input an integer between 0 and 100, or input \"back\" if you wish to cancel the command.");
                return addInt0_100();
            }
        }
        return -1;
    }

    public static String addGender() {
        Scanner scanner = new Scanner(System.in);
        String inputs = scanner.nextLine();
        switch (inputs) {
            case "female":
                return "female";
            case "male":
                return "male";
            case "back":
                return "b";
            default:
                System.out.println("Please input \"female\" or \"male\", or input \"back\" if you wish to cancel the command.");
                return addGender();
        }
    }

    public static int addMoney() {
        Scanner scanner = new Scanner(System.in);
        String inputs = scanner.nextLine();
        if (!inputs.equals("back")) {
            if (inputs.chars().allMatch(Character::isDigit)) {
                int num = Integer.parseInt(inputs);
                if (num > 1000000000) {
                    System.out.println("Please input an integer between 0 and 1000000000, or input \"back\" if you wish to cancel the command.");
                    return addMoney();
                } else {
                    return num;
                }
            } else {
                System.out.println("Please input an integer between 0 and 1000000000, or input \"back\" if you wish to cancel the command.");
                return addMoney();
            }
        }
        return -1;
    }

    public static void addFrom(World world) {

    }
}
