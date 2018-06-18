package sample.entity;

public class Reader {
    private String id;
    private String name;
    private String password;
    private String type;
    private String sex;
    private int max_num;
    private int days_num;
    private double forfeit;

    public Reader(String id, String name, String type, String sex, int max_num, int days_num, double forfeit) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.sex = sex;
        this.max_num = max_num;
        this.days_num = days_num;
        this.forfeit = forfeit;
    }

    public Reader() {

    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public int getMax_num() {
        return max_num;
    }

    public void setMax_num(int max_num) {
        this.max_num = max_num;
    }

    public int getDays_num() {
        return days_num;
    }

    public void setDays_num(int days_num) {
        this.days_num = days_num;
    }

    public double getForfeit() {
        return forfeit;
    }

    public void setForfeit(double forfeit) {
        this.forfeit = forfeit;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
