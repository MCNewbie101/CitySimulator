package GameSystems.Careers;

import GameSystems.Skills.*;

public class Basketball extends Career{
    private final int baseSalary = 5200000;
    private final int salaryGrowth = 1000000;

    public Basketball() {
        super();
        setSalary(baseSalary);
        setSalaryGrowth(salaryGrowth);
        setSkills(new Skills(new Creativity(0, 0, 0, 0), new Mental(0, 0, 0, 0, 0), new Physical(0, 100, 100, 0, 100), new Social(0, 0, 3, 0)));
    }
}
