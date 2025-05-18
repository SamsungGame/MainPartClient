package end.team.center.GameCore.Objects;

import com.badlogic.gdx.graphics.Texture;

public class Enemy extends Entity {

    public Enemy(Texture texture, int health, int defence, int damage, int speed) {
        super(texture, health, defence, damage, speed);
    }

    public void attack() {

    }

    @Override
    public void move() {
        super.move();
    }
}
