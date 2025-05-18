package end.team.center.GameCore.Objects;

import com.badlogic.gdx.graphics.Texture;

public class Friendly extends Entity {
    public Friendly(Texture texture, int health, int defence, int damage, int speed) {
        super(texture, health, defence, damage, speed);
    }

    @Override
    public void move() {
        super.move();
    }
}
