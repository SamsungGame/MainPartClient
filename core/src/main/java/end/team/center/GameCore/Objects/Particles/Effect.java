package end.team.center.GameCore.Objects.Particles;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;

public abstract class Effect extends Actor  {
    protected Texture texture;
    protected Vector2 vector;
    protected int width, height;
    protected float speed;

    public Effect(Texture texture, Vector2 vector, int width, int height, float speed) {
        this.texture = texture;
        this.vector = vector;
        this.width = width;
        this.height = height;
        this.speed = speed;

        setSize(width, height);
        setPosition(vector.x, vector.y);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
    }
}
