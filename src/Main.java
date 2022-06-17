import Actors.Human;
import World.World;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        World city = new World(1000, 1000);
        Scanner scanner = new Scanner(System.in);
        city.printInfo();
//        String inputs = scanner.nextLine();
        city.setDaysPerYear(1);
//        Human tracked1 = new Human(city);
//        Human tracked2 = new Human(city);
//        Human tracked3 = new Human(city);
//        city.getHumans().add(tracked1);
//        city.getHumans().add(tracked3);
//        city.getHumans().add(tracked3);
//        city.getTracked().add(tracked1);
//        city.getTracked().add(tracked2);
//        city.getTracked().add(tracked3);
        while (!city.getHumans().isEmpty()) {
//            city.update();
            for (int i = 0; i < 10; i++) {
                city.update();
            }
            if (city.getHumans().isEmpty()) {
                return;
            }
//            city.printInfo();
//            scanner.nextLine();
        }
    }
}
