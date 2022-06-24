package GameSystems;

import GameSystems.Skills.*;

public class Education{
    private int grade;
    private final Skills skillIncrease = new Skills(new Creativity(0, 10, 10, 70), new Mental(0, 10, 10, 70, 50), new Physical(0, 10, 10, 10, 10), new Social(0, 30, 50, 10));

    public Education(Attributes attributes) {
        grade = (int) ((Math.random() * 50 - 25) + attributes.getHappiness() * attributes.getHealth() / 30);
        if (grade > 100) {
            grade = 100;
        }
        if (grade < 0) {
            grade = 0;
        }
    }

    public Education(int grade) {
        this.grade = grade;
    }

    /*
     * Update a student's grades
     */
    public void update(Skills skills, Attributes attributes, int skillIncreaseBalancing, int jobSkillIncreaseBalancing, int daysPerYear) {
        int temp = 0;
        temp += skills.getCreativity().getArt() / 50;
        temp += skills.getCreativity().getMusical() / 50;
        temp += skills.getCreativity().getWriting() / 10;
        temp += skills.getMental().getEngineering() / 50;
        temp += skills.getMental().getProgramming() / 30;
        temp += skills.getMental().getMath() / 10;
        temp += skills.getMental().getChemistry() / 15;
        temp += skills.getPhysical().getSpeed() / 50;
        temp += skills.getPhysical().getStrength() / 50;
        temp += skills.getPhysical().getDancing() / 30;
        temp += skills.getSocial().getLeadership() / 15;
        temp += skills.getSocial().getCommunication() / 15;
        temp += skills.getSocial().getActing() / 50;
        temp /= 10;
        temp += (int) (Math.random() * attributes.getHappiness() + Math.random() * attributes.getHealth() - Math.random() * 100);
        temp /= 100;
        grade += temp;
        skills.update(this, skillIncreaseBalancing, jobSkillIncreaseBalancing, daysPerYear);
        if (grade > 100) {
            grade = 100;
        }
        if (grade < 0) {
            grade = 0;
        }
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public Skills getSkillIncrease() {
        return skillIncrease;
    }
}
