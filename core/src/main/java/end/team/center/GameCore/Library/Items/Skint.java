package end.team.center.GameCore.Library.Items;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;

import java.util.Random;

import end.team.center.GameCore.Library.ItemType;
import end.team.center.GameCore.Objects.InInventary.Drops;
import end.team.center.GameCore.Objects.OnMap.Hero;

public class Skint extends Drops {
    public Skint(Texture texture, Vector2 vector, Hero hero, int width, int height) {
        super(texture, vector, hero, width, height);
    }

    public Skint(ItemType type, Vector2 vector, Hero hero) {
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
            Random random = new Random();

            hero.addCostumePower(random.nextInt(10, 50));
            remove();
        }
    }

    @Override
    public void dispose() {
        super.dispose();
    }
}
