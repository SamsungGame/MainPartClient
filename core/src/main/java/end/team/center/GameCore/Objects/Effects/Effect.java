package end.team.center.GameCore.Objects.Effects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;

import end.team.center.GameCore.Objects.OnMap.Hero;

public abstract class Effect extends Actor {
    protected TextureRegion texture;
    protected int height, width;
    protected float speed;

    public Effect(Texture texture, int width, int height, float speed) {
        this.texture = new TextureRegion(texture);
        this.width = width;
        this.height = height;
        this.speed = speed;

        setSize(width, height);
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
