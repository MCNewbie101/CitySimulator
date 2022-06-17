package GameSystems.Careers;

import GameSystems.Skills.*;

public class Artist extends Career{
    private final int baseSalary = 5000;
    private final int salaryGrowth = 1000;
    private final int retirementAge = 120;
    private final int careerID = 1;

    public Artist() {
        super();
        setBase(baseSalary);
        setSalary(baseSalary);
        setSalaryGrowth(salaryGrowth);
        setSkills(new Skills(new Creativity(0, 0, 100, 0), new Mental(0, 0, 0, 0, 0), new Physical(0, 0, 0, 0, 0), new Social(0, 0, 5, 0)));
        setRetirementAge(retirementAge);
        setCareerID(careerID);
    }
}
