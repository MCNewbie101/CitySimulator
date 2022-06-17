package GameSystems.Careers;

import GameSystems.Skills.*;

public class Waiter extends Career{
    private final int baseSalary = 5000;
    private final int salaryGrowth = 10;
    private final int retirementAge = 65;
    private final int careerID = 9;

    public Waiter() {
        super();
        setBase(baseSalary);
        setSalary(baseSalary);
        setSalaryGrowth(salaryGrowth);
        setSkills(new Skills(new Creativity(0, 0, 0, 0), new Mental(0, 0, 0, 0, 0), new Physical(0, 10, 10, 0, 0), new Social(0, 0, 30, 0)));
        setRetirementAge(retirementAge);
        setCareerID(careerID);
    }
}
