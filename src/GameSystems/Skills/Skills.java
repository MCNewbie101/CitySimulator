package GameSystems.Skills;

import GameSystems.Careers.Career;
import GameSystems.Education;

public class Skills {
    private Creativity creativity;
    private Mental mental;
    private Physical physical;
    private Social social;

    public Skills() {
        creativity = new Creativity();
        mental = new Mental();
        physical = new Physical();
        social = new Social();
    }

    public Skills(Creativity creativity, Mental mental, Physical physical, Social social) {
        this.creativity = creativity;
        this.mental = mental;
        this.physical = physical;
        this.social = social;
    }

    public Skills(Skills skills1, Skills skills2) {
        creativity = new Creativity(skills1.getCreativity(), skills2.getCreativity());
        mental = new Mental(skills1.getMental(), skills2.getMental());
        physical = new Physical(skills1.getPhysical(), skills2.getPhysical());
        social = new Social(skills1.getSocial(), skills2.getSocial());
    }

    public void update(Career career, int skillIncreaseBalancing, int daysPerYear) {
        if (career != null) {
            creativity.update(career.getSkills().getCreativity(), skillIncreaseBalancing, daysPerYear);
            mental.update(career.getSkills().getMental(), skillIncreaseBalancing, daysPerYear);
            physical.update(career.getSkills().getPhysical(), skillIncreaseBalancing, daysPerYear);
            social.update(career.getSkills().getSocial(), skillIncreaseBalancing, daysPerYear);
        }
    }

    public void update(Education education, int skillIncreaseBalancing, int daysPerYear) {
        if (education != null) {
            creativity.update(education.getSkillIncrease().getCreativity(), skillIncreaseBalancing, daysPerYear);
            mental.update(education.getSkillIncrease().getMental(), skillIncreaseBalancing, daysPerYear);
            physical.update(education.getSkillIncrease().getPhysical(), skillIncreaseBalancing, daysPerYear);
            social.update(education.getSkillIncrease().getSocial(), skillIncreaseBalancing, daysPerYear);
        }
    }

    public Creativity getCreativity() {
        return creativity;
    }

    public void setCreativity(Creativity creativity) {
        this.creativity = creativity;
    }

    public Mental getMental() {
        return mental;
    }

    public void setMental(Mental mental) {
        this.mental = mental;
    }

    public Physical getPhysical() {
        return physical;
    }

    public void setPhysical(Physical physical) {
        this.physical = physical;
    }

    public Social getSocial() {
        return social;
    }

    public void setSocial(Social social) {
        this.social = social;
    }

    public int checkSkills(Skills skills) {
        int sum = 0;
        if (skills.getCreativity() != null) {
            sum += creativity.checkSkill(skills.getCreativity());
        }
        if (skills.getMental() != null) {
            sum += mental.checkSkill(skills.getMental());
        }
        if (skills.getPhysical() != null) {
            sum += physical.checkSkill(skills.getPhysical());
        }
        if (skills.getSocial() != null) {
            sum += social.checkSkill(skills.getSocial());
        }
        return sum / 30;
    }

    public void printInfo() {
        creativity.printInfo();
        mental.printInfo();
        physical.printInfo();
        social.printInfo();
    }
}
