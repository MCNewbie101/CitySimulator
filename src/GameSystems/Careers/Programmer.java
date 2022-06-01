package GameSystems.Careers;

import GameSystems.Skills.*;

public class Programmer extends Career{
    private final int baseSalary = 50000;
    private final int salaryGrowth = 100;

    public Programmer() {
        super();
        setSalary(baseSalary);
        setSalaryGrowth(salaryGrowth);
        setSkills(new Skills(new Creativity(0, 0, 0, 0), new Mental(0, 90, 0, 10, 0), new Physical(0, 0, 0, 0, 0), new Social(0, 3, 5, 0)));
    }
}
