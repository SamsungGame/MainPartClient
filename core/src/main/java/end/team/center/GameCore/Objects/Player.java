package end.team.center.GameCore.Objects;

import com.badlogic.gdx.graphics.Texture;

public class Player extends Friendly {
    public Texture front, back;
    public Player(Texture front, Texture back, int health, int defence, int damage, int speed) {
        super(front, health, defence, damage, speed);
        this.front = front;
        this.back = back;
    }

    @Override
    public void move() {
        super.move();
    }
}
