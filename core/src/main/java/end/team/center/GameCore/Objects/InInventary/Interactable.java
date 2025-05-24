package end.team.center.GameCore.Objects.InInventary;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;

import end.team.center.GameCore.Objects.OnMap.Hero;

public abstract class Interactable extends Actor {
    protected Texture texture;
    protected Vector2 vector;
    protected int height, width;
    protected Hero hero;

    public Interactable(Texture texture, Hero hero, int width, int height) {
        this.texture = texture;
        this.width = width;
        this.height = height;
        this.hero = hero;

        vector = new Vector2(hero.getCenterVector());

        setSize(width, height);
        setPosition(vector.x, vector.y);
        setBounds(vector.x, vector.y, width, height);
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

    @Override
    public Vector2 stageToLocalCoordinates(Vector2 stageCoords) {
        return super.stageToLocalCoordinates(stageCoords);
    }
}
