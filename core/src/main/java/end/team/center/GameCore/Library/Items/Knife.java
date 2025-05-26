package end.team.center.GameCore.Library.Items;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

import org.w3c.dom.Text;

import end.team.center.GameCore.Library.WeaponType;
import end.team.center.GameCore.Objects.Effects.Death;
import end.team.center.GameCore.Objects.Effects.ForwardEffect;
import end.team.center.GameCore.Objects.InInventary.Weapon;
import end.team.center.GameCore.Objects.OnMap.Hero;

public class Knife extends Weapon {
    protected Texture texture;
    protected float distance;
    protected boolean startAnim = false;
    protected Vector2 aniPos;
    protected ForwardEffect fe;

    public Knife(Texture canTexture, Texture cantTexture, Texture texture, Hero hero, int width, int height, int damage, float reload, float distance) {
        super(canTexture, cantTexture, hero, width, height, damage, reload);
        this.distance = distance;
        this.texture = texture;

        initialization();
    }

    public Knife(WeaponType type, Hero hero) {
        super(type.getCanTexture(), type.getCantTexture(), hero, type.getWidth(), type.getHeight(), type.getDamage(), type.getReload());
        this.distance = type.getDistance();
        this.texture = type.getTexture();

        initialization();
    }

    public void initialization() {

    }

    @Override
    public void setSize(float width, float height) {
        super.setSize(width, height);
    }

    public void startAnimation() {
        startAnim = true;
        aniPos = new Vector2(hero.getCenterVector());

        System.out.println("Поворот: " + getRotation());

        float angleRad = MathUtils.degreesToRadians * (getRotation() + 90);

        float x = hero.getCenterVector().x + 1000 * MathUtils.cos(angleRad);
        float y = hero.getCenterVector().y + 1000 * MathUtils.sin(angleRad);

        System.out.println("Позиция: " + x + "/" + y);

        fe = new ForwardEffect(texture, (int) getWidth(), (int) getHeight(), 600, 0.3f);
        fe.go(new Death() {
            @Override
            public void die() {
                stopAnimation();
            }
        }, hero.getCenterVector(),
                new Vector2(x, y),
                getRotation() + 90);
    }

    public void stopAnimation() {
        startAnim = false;
        fe = null;
    }

    @Override
    public void showGhost(float x, float y) {
        super.showGhost(x, y);
    }

    @Override
    public void hideGhost() {
        super.hideGhost();
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);

        if (startAnim) fe.draw(batch, parentAlpha);
    }

    @Override
    public void act(float delta) {
        super.act(delta);

        if (startAnim) fe.act(delta);
    }

    @Override
    public void dispose() {
        super.dispose();
    }
}
