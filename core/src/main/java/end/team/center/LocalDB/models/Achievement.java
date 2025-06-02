package end.team.center.LocalDB.models;


public class Achievement {
    public int id;
    public String code;
    public String title;
    public String description;

    public Achievement(int id, String code, String title, String description) {
        this.id = id;
        this.code = code;
        this.title = title;
        this.description = description;
    }
}
