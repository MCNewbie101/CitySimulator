import World.World;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        World city = new World(100, 100);
        Scanner scanner = new Scanner(System.in);
        city.printInfo();
        String inputs = scanner.nextLine();
        city.setDaysPerYear(1);
        while (!inputs.equals("!")) {
            city.update();
//            city.printInfo();
//            scanner.nextLine();
        }
    }
}
