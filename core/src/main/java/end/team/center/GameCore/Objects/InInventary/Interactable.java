package end.team.center.GameCore.Objects.InInventary;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;

public abstract class Interactable extends Actor {
    protected Texture texture;
    protected Vector2 vector;
    protected int height, width;

    public Interactable(Texture texture, Vector2 vector, int width, int height) {
        this.texture = texture;
        this.vector = vector;
        this.width = width;
        this.height = height;

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

    public void dispose() {

    }
}
