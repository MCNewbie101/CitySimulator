package GameSystems;

public class Age {
    int years;
    int days;

    public Age() {
        years = 0;
        days = 0;
    }

    public Age(int years, int days) {
        this.years = years;
        this.days = days;
    }

    public void update(int daysPerYear) {
        this.days ++;
        if (this.days >= daysPerYear) {
            years++;
            this.days = this.days % daysPerYear;
        }
    }

    public int getYears() {
        return years;
    }

    public void printInfo() {
        System.out.println(years);
    }
}
