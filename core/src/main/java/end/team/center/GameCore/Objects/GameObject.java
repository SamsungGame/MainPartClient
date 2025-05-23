package end.team.center.GameCore.Objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;

public abstract class GameObject extends Actor {
    protected Texture texture;
    protected Rectangle bound;
    protected Vector2 vector;
    protected float height, width;
    public GameObject(Texture texture, Vector2 vector, float height, float width) {
        this.texture = texture;
        this.vector = vector;
        this.width = width;
        this.height = height;

        setSize(width, height);
        setPosition(vector.x, vector.y);
        setBounds(vector.x, vector.y, width, height);

        bound = new Rectangle(vector.x, vector.y, width, height);
    }

    public Vector2 getVector() {
        return vector;
    }
    public Vector2 getCenterVector() {
        return new Vector2(vector.x + width / 2, vector.y + height / 2);
    }
    public float getHeight() {
        return height;
    }
    public float getWidth() {
        return width;
    }
    public Rectangle getBound() {
        return bound;
    }
    public void reSizeBound() {
        bound.height = height;
        bound.width = width;
    }

    @Override
    public void act(float delta) {
        super.act(delta);
    }
    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
    }
    public void dispose() {

    }
}
