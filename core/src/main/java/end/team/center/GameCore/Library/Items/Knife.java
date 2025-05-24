package end.team.center.GameCore.Library.Items;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import end.team.center.GameCore.Library.WeaponType;
import end.team.center.GameCore.Objects.InInventary.Weapon;
import end.team.center.GameCore.Objects.OnMap.Hero;

public class Knife extends Weapon {
    protected float distance;

    public Knife(Texture texture, Hero hero, int width, int height, int damage, float reload, float distance) {
        super(texture, hero, width, height, damage, reload);
        this.distance = distance;
    }

    public Knife(WeaponType type, Hero hero) {
        super(type.getTexture(), hero, type.getWidth(), type.getHeight(), type.getDamage(), type.getReload());
        this.distance = type.getDistance();
    }

    @Override
    public void showGhost() {
        super.showGhost();
    }

    @Override
    public void hideGhost() {
        super.hideGhost();

        isActivate = false;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);

    }

    @Override
    public void act(float delta) {
        super.act(delta);
    }

    @Override
    public void dispose() {
        super.dispose();
    }
}
