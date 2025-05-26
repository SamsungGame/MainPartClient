package end.team.center.GameCore.Objects.InInventary;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;

import end.team.center.GameCore.Objects.OnMap.Hero;

public abstract class Interactable extends Actor {
    protected int height, width;
    protected Hero hero;

    public Interactable(Hero hero, int width, int height) {
        this.width = width;
        this.height = height;
        this.hero = hero;

        setSize(width, height);
    }

    @Override
    public void setSize(float width, float height) {
        super.setSize(width, height);
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
