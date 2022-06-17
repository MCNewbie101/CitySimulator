package GameSystems.Careers;

import GameSystems.Skills.*;

public class Receptionist extends Career{
    private final int baseSalary = 5000;
    private final int salaryGrowth = 10;
    private final int retirementAge = 65;
    private final int careerID = 8;

    public Receptionist() {
        super();
        setBase(baseSalary);
        setSalary(baseSalary);
        setSalaryGrowth(salaryGrowth);
        setSkills(new Skills(new Creativity(0, 0, 0, 0), new Mental(0, 0, 0, 0, 0), new Physical(0, 0, 0, 0, 0), new Social(0, 0, 50, 0)));
        setRetirementAge(retirementAge);
        setCareerID(careerID);
    }
}
