package end.team.center;

import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class Achievement {
    public String name;
    public String description;
    public boolean isObtained;
    public Image achievementIfObtained;
    public Image achievementIfNotObtained;

    public Achievement(String name, String description, boolean isObtained, Image achievementIfObtained, Image achievementIfNotObtained) {
        this.name = name;
        this.description = description;
        this.isObtained = isObtained;
        this.achievementIfObtained = achievementIfObtained;
        this.achievementIfNotObtained = achievementIfNotObtained;
    }
}
