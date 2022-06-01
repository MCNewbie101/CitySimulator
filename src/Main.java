import World.World;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        World city = new World(10, 5);
        Scanner scanner = new Scanner(System.in);
        String inputs = scanner.nextLine();
        city.setDaysPerYear(10);
        city.printInfo();
        while (!inputs.equals("!")) {
            city.update();
            city.printInfo();
            scanner.nextLine();
        }
    }
}
