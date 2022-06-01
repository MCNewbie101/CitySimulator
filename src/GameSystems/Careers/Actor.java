package GameSystems.Careers;

import GameSystems.Skills.*;

public class Actor extends Career{
    private final int baseSalary = 3000;
    private final int salaryGrowth = 10000;

    public Actor() {
        super();
        setSalary(baseSalary);
        setSalaryGrowth(salaryGrowth);
        setSkills(new Skills(new Creativity(0, 3, 0, 0), new Mental(0, 0, 0, 0, 0), new Physical(0, 0, 1, 10, 0), new Social(0, 0, 70, 100)));
    }
}
