package end.team.center.GameCore.Objects.InInventary;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import end.team.center.GameCore.Objects.OnMap.Hero;

public abstract class Weapon extends Interactable {
    protected TextureRegion canTextureR, cantTextureR;
    protected Rectangle attackGhost;
    protected Vector2 vectorGhost;
    protected int damage;
    protected float reload; // время перезарядки в секундах
    protected boolean show = false;
    protected boolean canAttack = false;

    private float reloadTimer = 0f;

    public Weapon(Texture canTexture, Texture cantTexture, Hero hero, int widthAttackZone, int heightAttackZone, int damage, float reload) {
        super(hero, widthAttackZone, heightAttackZone);
        this.damage = damage;
        this.reload = reload;
        this.hero = hero;
        canTextureR  = new TextureRegion(canTexture);
        cantTextureR = new TextureRegion(cantTexture);

        vectorGhost = new Vector2(hero.getCenterVector());
        vectorGhost.x -= (float) width / 2;

        attackGhost = new Rectangle(vectorGhost.x, vectorGhost.y, width, height);

        setOrigin((float) width / 2, 0);
        setPosition(vectorGhost.x, vectorGhost.y);
        setBounds(vectorGhost.x, vectorGhost.y, width, height);
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public void setRotationAroundPlayer(float x, float y) {
        float angleRad = (float) Math.atan2(y, x);
        float angleDeg = (float) Math.toDegrees(angleRad);
        if (angleDeg < 0) {
            angleDeg += 360;
        }
        angleDeg -= 90;

        setRotation(angleDeg);
    }

    public boolean checkTouchRectangle(Rectangle simpleRect) {
        float centerX = attackGhost.x + attackGhost.width / 2;
        float centerY = attackGhost.y;

        float angleRad = MathUtils.degreesToRadians * getRotation();

        float[] vertices = {
            attackGhost.x,  attackGhost.y,
            attackGhost.x + attackGhost.width, attackGhost.y,
            attackGhost.x + attackGhost.width, attackGhost.y + attackGhost.height,
            attackGhost.x,  attackGhost.y + attackGhost.height
        };

        for (int i = 0; i < vertices.length; i += 2) {
            float vx = vertices[i];
            float vy = vertices[i + 1];

            float dx = vx - centerX;
            float dy = vy - centerY;

            float rotatedX = dx * MathUtils.cos(angleRad) - dy * MathUtils.sin(angleRad);
            float rotatedY = dx * MathUtils.sin(angleRad) + dy * MathUtils.cos(angleRad);

            vertices[i] = rotatedX + centerX;
            vertices[i + 1] = rotatedY + centerY;
        }

        float[] sVertices = {
            simpleRect.x,  simpleRect.y,
            simpleRect.x + simpleRect.width, simpleRect.y,
            simpleRect.x + simpleRect.width, simpleRect.y + simpleRect.height,
            simpleRect.x,  simpleRect.y + simpleRect.height
        };

        Polygon rPolygon = new Polygon(vertices);
        Polygon sPolygon = new Polygon(sVertices);

        return Intersector.overlapConvexPolygons(rPolygon, sPolygon);
    }

    public void updateAttackGhost() {
        attackGhost.x = vectorGhost.x;
        attackGhost.y = vectorGhost.y;
        attackGhost.width = width;
        attackGhost.height = height;
    }

    public void showGhost(float x, float y) {
        show = true;
        setRotationAroundPlayer(x, y);
    }

    public void hideGhost() {
        show = false;
        setRotation(0);
        canAttack = false;
        reloadTimer = 0f;
    }

    public Rectangle getAttackGhost() {
        return attackGhost;
    }

    public boolean isCanAttack() {
        return canAttack;
    }

    public boolean getShow() {
        return show;
    }

    public int getDamage() {
        return damage;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);

        if (show && canAttack)  {
            batch.draw(canTextureR, getX(), getY(), getOriginX(), getOriginY(), getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation());
        } else if (show && !canAttack) {
            batch.draw(cantTextureR, getX(), getY(), getOriginX(), getOriginY(), getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation());
        }
    }

    @Override
    public void act(float delta) {
        super.act(delta);

        vectorGhost = new Vector2(hero.getCenterVector());
        vectorGhost.x -= (float) width / 2;

        setPosition(vectorGhost.x, vectorGhost.y);
        updateAttackGhost();

        if (!canAttack) {
            reloadTimer += delta;
            if (reloadTimer >= reload) {
                canAttack = true;
                reloadTimer = 0f;
            }
        }
    }

    public void startReload() {
        canAttack = false;
        reloadTimer = 0f;
    }

    @Override
    public void dispose() {
        super.dispose();
    }
}
