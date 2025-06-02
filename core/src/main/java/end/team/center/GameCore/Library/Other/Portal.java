package end.team.center.GameCore.Library.Other;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import end.team.center.Center;
import end.team.center.GameCore.Objects.OnMap.Hero;
import end.team.center.GameCore.Objects.OnMap.StaticObject;
import end.team.center.Screens.Game.GameScreen;
import end.team.center.Screens.Menu.MainMenuScreen;

public class Portal extends StaticObject {
    private final Center center;
    protected Hero hero;
    protected TextureRegion[] t;
    protected Animation<TextureRegion> tr;
    public float stateTime;
    public Portal(Center center, Texture texture, Texture texture2, Texture texture3, Vector2 vector, Hero hero, float height, float width) {
        super(texture, vector, height, width, true);
        this.center = center;
        this.hero = hero;
        t = new TextureRegion[] {
            new TextureRegion(texture),
            new TextureRegion(texture2),
            new TextureRegion(texture3),
        };

        stateTime = 0;

        tr = new Animation<>(0.19f, t);
    }

    @Override
    public void act(float delta) {
        super.act(delta);

        if (hero.getBound().overlaps(bound)) {
            ((Center) Gdx.app.getApplicationListener()).setScreen(new MainMenuScreen(1, center));
            GameScreen.backgroundMusic.stop();
        }

        stateTime += delta;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        TextureRegion drawRegion = tr.getKeyFrame(stateTime, true);

        batch.draw(drawRegion, getX(), getY(), getWidth(), getHeight());
    }
}
