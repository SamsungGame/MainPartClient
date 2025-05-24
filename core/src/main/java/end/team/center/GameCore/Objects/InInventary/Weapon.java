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
    protected boolean show = false, isActivate = false;
    protected Vector2 lastTouchpadVector;

    public Weapon(Texture texture, Hero hero, int width, int height, int damage, float reload) {
        super(texture, hero, width, height);
        this.damage = damage;
        this.reload = reload;
        this.hero = hero;

        lastTouchpadVector = new Vector2();
        textureR = new TextureRegion(texture);

        vector = new Vector2(hero.getCenterVector());
        vector.y = hero.getHeight() + hero.getVector().y;

        setPosition(vector.x, vector.y);
        setBounds(vector.x, vector.y, width, height);
    }

    public void showGhost() {
        System.out.println("Позиция игрока: " + hero.getCenterVector().x + "/" + hero.getCenterVector().y);
        System.out.println("Центр ножа: " + getOriginX() + "/" + getOriginY());
        System.out.println("Позиция ножа: " + getX() + "/" + getY());

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

        lastTouchpadVector.set(x, y);

        // Получаем локальные координаты для центра героя
        Vector2 localPos = super.stageToLocalCoordinates(hero.getCenterVector());

        // Устанавливаем точку вращения
        setOrigin(localPos.x, localPos.y);

        setRotation(angleDeg);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);

        if (show) batch.draw(textureR, getX(), getY(), getOriginX(), getOriginY(), getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation());
    }

    @Override
    public void act(float delta) {
        super.act(delta);

        setOrigin(hero.getCenterVector().x, hero.getCenterVector().y);

        vector = new Vector2(hero.getCenterVector());
        vector.y += hero.getHeight() + 20 + hero.getVector().y;
    }

    @Override
    public void dispose() {
        super.dispose();
    }
}
