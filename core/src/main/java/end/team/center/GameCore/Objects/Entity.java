package end.team.center.GameCore.Objects;

import com.badlogic.gdx.graphics.Texture;

public class Entity extends GameObject {
    protected int health;
    protected int dameg, defence, speed;
    protected boolean isLive = true;
    public Entity(Texture texture, int health, int defence, int damage, int speed) {
        super(texture);
        this.health = health;
        this.defence = defence;
        this.dameg = damage;
        this.speed = speed;
    }

    public void move() {

    }
}
