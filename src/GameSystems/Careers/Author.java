package GameSystems.Careers;

import GameSystems.Skills.*;

public class Author extends Career{
    private final int baseSalary = 5000;
    private final int salaryGrowth = 1000;
    private final int retirementAge = 65;
    private final int careerID = 2;
    private final int gradeRequired = 70;

    public Author() {
        super();
        setBase(baseSalary);
        setSalary(baseSalary);
        setSalaryGrowth(salaryGrowth);
        setSkills(new Skills(new Creativity(0, 0, 0, 100), new Mental(0, 0, 0, 0, 0), new Physical(0, 0, 0, 0, 0), new Social(0, 0, 5, 0)));
        setRetirementAge(retirementAge);
        setCareerID(careerID);
        setAcademicRequirement(gradeRequired);
    }
}
