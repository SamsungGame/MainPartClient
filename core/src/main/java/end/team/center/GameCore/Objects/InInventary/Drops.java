package end.team.center.GameCore.Objects.InInventary;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import end.team.center.GameCore.Objects.OnMap.Hero;

public abstract class Drops extends Interactable {
    protected Texture texture;
    protected Vector2 vector;
    protected Rectangle bound;
    public Drops(Texture texture, Vector2 vector, Hero hero, int width, int height) {
        super(hero, width, height);
        this.texture = texture;
        this.vector = vector;

        bound = new Rectangle(vector.x, vector.y, width, height);
        setPosition(vector.x, vector.y);
    }

    public void updateBound() {
        bound.x = vector.x;
        bound.y = vector.y;
    }

    public Vector2 getCenterVector() {
        return new Vector2(vector.x + (float) width / 2, vector.y + (float) height / 2);
    }
    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);

        batch.draw(texture, getX(), getY(), getWidth(), getHeight());
    }

    @Override
    public void act(float delta) {
        super.act(delta);
    }

    public Rectangle getBound() {
        return bound;
    }

    @Override
    public void dispose() {
        super.dispose();
    }
}
