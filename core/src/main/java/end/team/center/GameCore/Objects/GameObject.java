package end.team.center.GameCore.Objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class GameObject extends Actor {
    protected Vector2 vector;
    protected float height, width;
    public GameObject(Vector2 vector, float height, float width) {
        this.vector = vector;
        this.width = width;
        this.height = height;
    }

    public void act(float delta){}
    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
    }

    public float getX() {
        return vector.x;
    }

    public float getY() {
        return vector.y;
    }
}
