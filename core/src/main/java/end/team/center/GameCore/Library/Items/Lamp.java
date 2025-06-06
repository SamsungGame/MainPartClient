package end.team.center.GameCore.Library.Items;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;

import end.team.center.GameCore.Library.ItemType;
import end.team.center.GameCore.Logic.ShaderManager;
import end.team.center.GameCore.Objects.InInventary.Drops;
import end.team.center.GameCore.Objects.OnMap.Hero;
import end.team.center.Screens.Game.GameScreen;

public class Lamp extends Drops {
    public Lamp(Texture texture, Vector2 vector, Hero hero, int width, int height) {
        super(texture, vector, hero, width, height);
    }

    public Lamp(ItemType type, Vector2 vector, Hero hero) {
        super(type.getTexture(), vector, hero, type.getWidth(), type.getHeight());
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);

        batch.draw(texture, getX(), getY(), getWidth(), getHeight());
    }

    @SuppressWarnings("NewApi")
    @Override
    public void act(float delta) {
        super.act(delta);

        if (bound.overlaps(hero.getBound())) {
            if (!hero.shyne) {
                ShaderManager.radiusView1 *= 1.1f;
                ShaderManager.radiusView2 *= 1.1f;
                ShaderManager.radiusView3 *= 1.1f;

                hero.startShyne();
            } else {
                int d = hero.getDuration();

                d += 5;
                if (d > 30) d = 30;

                hero.setDuration(d);
            }
            GameScreen.isPickupItem = true;

            remove();
        }
    }

    @Override
    public void dispose() {
        super.dispose();
    }
}
