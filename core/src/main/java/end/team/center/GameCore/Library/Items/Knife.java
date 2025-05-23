package end.team.center.GameCore.Library.Items;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import end.team.center.GameCore.Library.Effects;
import end.team.center.GameCore.Library.WeaponType;
import end.team.center.GameCore.Objects.InInventary.Weapon;
import end.team.center.GameCore.Library.Particles.ForwardsEffect;
import end.team.center.GameCore.Objects.OnMap.Hero;

public class Knife extends Weapon {
    protected ForwardsEffect weaponStep;
    protected float distance;

    public Knife(Texture texture, Vector2 vector, Hero hero, int width, int height, int damage, float reload, float distance) {
        super(texture, vector, hero, width, height, damage, reload);
        this.distance = distance;

        initialization();
    }

    public Knife(WeaponType type, Vector2 vector, Hero hero) {
        super(type.getTexture(), vector, hero, type.getWidth(), type.getHeight(), type.getDamage(), type.getReload());
        this.distance = type.getDistance();

        initialization();
    }

    public void initialization() {
        weaponStep = new ForwardsEffect(Effects.knifeStep, new Vector2(hero.getCenterVector()), 16, 7);
    }

    public void use(Vector2 target) {
        weaponStep.startMove(target, getRotation());
    }

    @Override
    public void showGhost() {
        super.showGhost();
    }

    @Override
    public void hideGhost() {
        super.hideGhost();
        isActivate = false;
        use(new Vector2(lastTouchpadVector.x * distance, lastTouchpadVector.y * distance));
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);

        if(show) batch.draw(new TextureRegion(texture), flyPoint.x, flyPoint.y, getOriginX(), getOriginY(), width, height, getScaleX(), getScaleY(), getRotation());
        if(weaponStep.getStart()) weaponStep.draw(batch, parentAlpha);
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
