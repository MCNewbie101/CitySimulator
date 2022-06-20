import Actors.Human;
import World.World;

import java.util.Objects;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please input the initial population:");
        int population = scanner.nextInt();
        World city = new World(population, population);
        city.printTrackedInfo();
        while (!city.getHumans().isEmpty()) {
            if (nextCommand(city)) {
                city.update();
                city.printTrackedInfo();
            } else {
                break;
            }
        }
    }

    public static boolean nextCommand(World city) {
        String[] commands = {"+", "quit", "set days", "add tracked person"};
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please input the next command");
        if (scanner.nextLine().equals(commands[0])) {
            return true;
        } else if (scanner.nextLine().equals(commands[1])) {
            return false;
        } else if (scanner.nextLine().equals(commands[2])) {
            int days = scanner.nextInt();
            if (days <= 0 || days > 10) {
                //TODO: Make this a seperate recursive function
                System.out.println("Please enter an integer between 1 and 10.");
            }
            city.setDaysPerYear(days);
            return nextCommand(city);
        } else {
            System.out.println("Invalid command, please try again.");
            System.out.println("Valid commands are:");
            for (String c : commands) {
                System.out.println(c);
            }
            return nextCommand(city);
        }
    }
}
