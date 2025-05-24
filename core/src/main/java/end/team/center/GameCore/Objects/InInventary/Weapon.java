package end.team.center.GameCore.Objects.InInventary;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import end.team.center.GameCore.Objects.OnMap.Hero;

public abstract class Weapon extends Interactable {
    protected TextureRegion textureR;
    protected int damage;
    protected float reload;
    protected Hero hero;
    protected boolean show = false, isActivate = false;
    protected Vector2 flyPoint;
    protected Vector2 lastTouchpadVector;

    public Weapon(Texture texture, Vector2 vector, Hero hero, int width, int height, int damage, float reload) {
        super(texture, vector, width, height);
        this.damage = damage;
        this.reload = reload;
        this.hero = hero;
        flyPoint = new Vector2(hero.getCenterVector().x, (hero.getHeight() + hero.getWidth()) / 2 + hero.getVector().y);
        lastTouchpadVector = new Vector2();
        textureR = new TextureRegion(texture);

        setPosition(flyPoint.x, flyPoint.y);
    }

    public void showGhost() {
        show = true;
        isActivate = true;
    }

    public void hideGhost() {
        show = false;
    }

    public boolean getShow() {
        return show;
    }

    public void setRotationAroundPlayer(float x, float y) {
        // Находим угол в радианах
        float angleRad = (float) Math.atan2(y, x);

        // Переводим в градусы
        float angleDeg = (float) Math.toDegrees(angleRad);

        // Корректируем диапазон [0,360)
        if (angleDeg < 0) {
            angleDeg += 360;
        }

        lastTouchpadVector.x = x;
        lastTouchpadVector.y = y;

        setOrigin(hero.getCenterVector().x, hero.getCenterVector().y);
        setRotation(angleDeg);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
    }

    @Override
    public void act(float delta) {
        super.act(delta);

        flyPoint.x = vector.x + hero.getWidth() / 2;
        flyPoint.y = (hero.getHeight() + hero.getWidth()) / 2 + hero.getVector().y;
    }

    @Override
    public void dispose() {
        super.dispose();
    }
}
