package end.team.center.GameCore.Library.Mobs;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;

import end.team.center.GameCore.Library.CharacterAnimation;
import end.team.center.GameCore.Library.EnemyType;
import end.team.center.GameCore.Logic.AI.AI;
import end.team.center.GameCore.Logic.AI.AI_Ghost;
import end.team.center.GameCore.Logic.AI.AI_Owl;
import end.team.center.GameCore.Objects.OnMap.Ammo;
import end.team.center.GameCore.Objects.OnMap.Enemy;

public class Ghost extends Enemy {
    public TextureRegion r, l, shotR, shotL;
    protected AI_Ghost ai;
    protected Circle stop, run;
    protected ArrayList<Ammo> ammos;

//    public Ghost(Texture texture, CharacterAnimation anim, Vector2 vector, float height, float width, int health, int damage, int defence, float speed, int level, int exp, float worldHeight, float worldWidth, AI ai) {
//        super(texture, anim, vector, height, width, health, damage, defence, speed, level, exp, worldHeight, worldWidth, ai);
//        this.ai = (AI_Ghost) ai;
//
//        initialization();
//    }

    public Ghost(EnemyType type, Vector2 vector, int level, float worldHeight, float worldWidth, AI ai, TextureRegion attack) {
        super(type.getTexture(), type.getAnim(), vector, type.getHeight(), type.getWidth(), type.getHealth(), type.getDamage(), type.getDefense(), type.getSpeed(), level, type.getExp(), worldHeight, worldWidth, ai);
        this.ai = (AI_Ghost) ai;
        this.shotR = attack;

        initialization();
    }

    public void addAmmo(Ammo a) {
        ammos.add(a);
    }

    protected void initialization() {
        stop = new Circle(getCenterVector().x, getCenterVector().y, 2000);
        run  = new Circle(getCenterVector().x, getCenterVector().y, 350);

        r = new TextureRegion(texture);
        l = new TextureRegion(texture);
        l.flip(true, false);

        shotL = new TextureRegion(shotR);
        shotL.flip(true, false);

        health = health + (health / 5 * level);

        if (level > 7 && level < 14) damage = 2;
        else if (level >= 14) damage = 3;

        speed = speed + 15 * level;
    }

    protected void updateCircle() {
        stop.x = getCenterVector().x;
        stop.y = getCenterVector().y;
        run.x  = getCenterVector().x;
        run.y  = getCenterVector().y;
    }

    public Circle getStopCircle() {
        return stop;
    }

    public Circle getRunCircle() {
        return run;
    }

    @Override
    public void move(float deltaX, float deltaY, float delta) {
        super.move(deltaX, deltaY, delta);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        if (isLive) {
            if (mRight && !ai.getIsShotLoad()) batch.draw(r, getX(), getY(), getWidth(), getHeight());
            else if (!ai.getIsShotLoad())      batch.draw(l, getX(), getY(), getWidth(), getHeight());
            else if (mRight)                   batch.draw(shotR, getX(), getY(), getWidth(), getHeight());
            else                               batch.draw(shotL, getX(), getY(), getWidth(), getHeight());
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
