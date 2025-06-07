package end.team.center.GameCore.Library.Mobs;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;

import end.team.center.GameCore.Library.EnemyType;
import end.team.center.GameCore.Logic.AI.AI;
import end.team.center.GameCore.Logic.AI.AI_Ghost;
import end.team.center.GameCore.Objects.OnMap.Ammo;
import end.team.center.GameCore.Objects.OnMap.Enemy;

public class Ghost extends Enemy {
    public TextureRegion r, l, shotR, shotL;
    protected AI_Ghost ai;
    protected Rectangle stop;
    protected ArrayList<Ammo> ammos;

    public Ghost(EnemyType type, Texture texture, Vector2 vector, int level, float worldHeight, float worldWidth, AI ai, TextureRegion attack) {
        super(texture, type.getAnim(), vector, type.getHeight(), type.getWidth(), type.getHealth(), type.getDamage(), type.getDefense(), type.getSpeed(), level, type.getExp(), worldHeight, worldWidth, ai);
        this.ai = (AI_Ghost) ai;
        this.shotR = attack;

        initialization();
    }

    public void addAmmo(Ammo a) {
        ammos.add(a);
    }

    protected void initialization() {
        stop = new Rectangle(getCenterVector().x - 900, getCenterVector().y - 900, 1800, 1800);

        r = new TextureRegion(texture);
        l = new TextureRegion(texture);
        l.flip(true, false);

        shotL = new TextureRegion(shotR);
        shotL.flip(true, false);

        ammos = new ArrayList<>();

        health = health + (health / 5 * level);
    }

    public ArrayList<Ammo> getAmmos() {
        return ammos;
    }

    protected void updateCircle() {
        stop.x = getCenterVector().x;
        stop.y = getCenterVector().y;
    }

    public Rectangle getStopRectangle() {
        return stop;
    }

    @Override
    public void move(float deltaX, float deltaY, float delta) {
        super.move(deltaX, deltaY, delta);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        if (isLive) {

            for(Ammo a: ammos) {
                a.draw(batch, parentAlpha);
            }

            OrthographicCamera camera = (OrthographicCamera) getStage().getCamera();

            float camLeft = camera.position.x - camera.viewportWidth / 2 * camera.zoom;
            float camRight = camera.position.x + camera.viewportWidth / 2 * camera.zoom;
            float camBottom = camera.position.y - camera.viewportHeight / 2 * camera.zoom;
            float camTop = camera.position.y + camera.viewportHeight / 2 * camera.zoom;

            // Рисуем только если дерево в зоне видимости камеры
            if (getX() + getWidth() < camLeft || getX() > camRight ||
                getY() + getHeight() < camBottom || getY() > camTop) {
                return;
            }

            TextureRegion currentFrame;

            if (isMoving) {
                currentFrame = mRight
                    ? firstTypeAnimation.getKeyFrame(stateTime, true) // Хотьба направо
                    : secondTypeAnimation.getKeyFrame(stateTime, true); // Хотьба налево
            } else if (ai.getIsShotLoad()) {
                currentFrame = therdTypeAnimation.getKeyFrame(stateTime, true); // Заряд атаки
            } else currentFrame = firstTypeAnimation.getKeyFrame(stateTime, true);

            if (!mRight) currentFrame.flip(true, false);

            batch.draw(currentFrame, vector.x, vector.y, getWidth(), getHeight());
        }
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        updateCircle();

        Vector2 v = ai.move(this, delta);
        move(v.x, v.y, delta);

        ai.shot(this);

        int size = ammos.size();
        for(int i = 0; i < size; i++) {
            if (ammos.get(i) == null) {
                ammos.remove(i);
                size--;
            } else {
                ammos.get(i).act(delta);
            }
        }
    }

    @Override
    public void dispose() {
        super.dispose();
    }
}
