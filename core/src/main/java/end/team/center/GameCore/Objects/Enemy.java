package end.team.center.GameCore.Objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;

public class Enemy extends Entity {
    protected int level;

    public Enemy(Texture rightTurn, Texture leftTurn, Vector2 vector, int health, int damage, int defence, float speed, int level) {
        super(rightTurn, leftTurn, vector, health, damage, defence, speed);
        this.level = level;
    }


    public void attack() {

    }

    @Override
    public void move() {
        super.move();
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
    }
}
