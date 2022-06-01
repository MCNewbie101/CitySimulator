package GameSystems;

import GameSystems.Skills.*;

public class Education{
    private int grade;
    private int gradeLevel;
    private final Skills skillIncrease = new Skills(new Creativity(0, 10, 10, 70), new Mental(0, 10, 10, 70, 50), new Physical(0, 10, 10, 10, 10), new Social(0, 30, 50, 10));

    public Education(Attributes attributes) {
        grade = (int) (Math.random() * attributes.getHappiness() * attributes.getHealth());
        gradeLevel = 0;
    }

    public Education(int grade, int gradeLevel) {
        this.grade = grade;
        this.gradeLevel = gradeLevel;
    }

    public void update(Skills skills, Attributes attributes, int skillIncreaseBalancing, int daysPerYear) {
        grade += skills.getCreativity().getArt() / 50;
        grade += skills.getCreativity().getMusical() / 50;
        grade += skills.getCreativity().getWriting() / 10;
        grade += skills.getMental().getEngineering() / 50;
        grade += skills.getMental().getProgramming() / 30;
        grade += skills.getMental().getMath() / 10;
        grade += skills.getMental().getChemistry() / 15;
        grade += skills.getPhysical().getSpeed() / 50;
        grade += skills.getPhysical().getStrength() / 50;
        grade += skills.getPhysical().getDancing() / 30;
        grade += skills.getSocial().getLeadership() / 15;
        grade += skills.getSocial().getCommunication() / 15;
        grade += skills.getSocial().getActing() / 50;
        int temp = (int) (Math.random() * attributes.getHappiness() + Math.random() * attributes.getHealth());
        temp /= 300;
        grade += temp;
        skills.update(this, skillIncreaseBalancing, daysPerYear);
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public int getGradeLevel() {
        return gradeLevel;
    }

    public void setGradeLevel(int gradeLevel) {
        this.gradeLevel = gradeLevel;
    }

    public Skills getSkillIncrease() {
        return skillIncrease;
    }
}
