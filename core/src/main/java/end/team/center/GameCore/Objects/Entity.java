package end.team.center.GameCore.Objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;

import org.w3c.dom.Text;

public class Entity extends GameObject {
    Texture rightTurn, leftTurn;
    protected int health;
    protected int damage, defence;
    protected float speed;
    protected boolean isLive = true;

    public Entity(Texture rightTurn, Texture leftTurn, Vector2 vector, int health, int damage, int defence, float speed) {
        super(vector);
        this.rightTurn = rightTurn;
        this.leftTurn = leftTurn;
        this.health = health;
        this.damage = damage;
        this.defence = defence;
        this.speed = speed;
    }

    public void move() {

    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
    }
}
