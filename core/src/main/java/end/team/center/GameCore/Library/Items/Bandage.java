package end.team.center.GameCore.Library.Items;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;

import java.util.Random;

import end.team.center.GameCore.Library.ItemType;
import end.team.center.GameCore.Objects.InInventary.Drops;
import end.team.center.GameCore.Objects.OnMap.Hero;
import end.team.center.Screens.Game.GameScreen;

public class Bandage extends Drops {
    public Bandage(Texture texture, Vector2 vector, Hero hero, int width, int height) {
        super(texture, vector, hero, width, height);
    }

    public Bandage(ItemType type, Vector2 vector, Hero hero) {
        super(type.getTexture(), vector, hero, type.getWidth(), type.getHeight());
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
    }

    @SuppressWarnings("NewApi")
    @Override
    public void act(float delta) {
        super.act(delta);

        if (bound.overlaps(hero.getBound())) {
            hero.setHealth(hero.getHealth() + 1);

            GameScreen.isPickupItem = true;

            remove();
            GameScreen.drop.remove(this);
        }
    }

    @Override
    public void dispose() {
        super.dispose();
    }
}
