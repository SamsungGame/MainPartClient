package end.team.center.GameCore.Library.Mobs;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;

import end.team.center.GameCore.Library.CharacterAnimation;
import end.team.center.GameCore.Library.EnemyType;
import end.team.center.GameCore.Logic.AI.AI;
import end.team.center.GameCore.Logic.AI.AI_Ghost;
import end.team.center.GameCore.Logic.AI.AI_Owl;
import end.team.center.GameCore.Objects.OnMap.Enemy;

public class Ghost extends Enemy {
    protected AI_Ghost ai;
    protected Circle stop, run;
    public Ghost(Texture texture, CharacterAnimation anim, Vector2 vector, float height, float width, int health, int damage, int defence, float speed, int level, int exp, float worldHeight, float worldWidth, AI ai) {
        super(texture, anim, vector, height, width, health, damage, defence, speed, level, exp, worldHeight, worldWidth, ai);
        this.ai = (AI_Ghost) ai;

        initialization();
    }

    public Ghost(EnemyType type, Vector2 vector, float worldHeight, float worldWidth, AI ai) {
        super(type.getTexture(), type.getAnim(), vector, type.getHeight(), type.getWidth(), type.getHealth(), type.getDamage(), type.getDefense(), type.getSpeed(), type.getLevel(), type.getExp(), worldHeight, worldWidth, ai);
        this.ai = (AI_Ghost) ai;

        initialization();
    }

    protected void initialization() {
        stop = new Circle(getCenterVector().x, getCenterVector().y, 2000);
        run  = new Circle(getCenterVector().x, getCenterVector().y, 350);
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
            super.draw(batch, parentAlpha);
        }
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        updateCircle();

        Vector2 v = ai.move(this, delta);
        move(v.x, v.y, delta);

        ai.shot(this);
    }

    @Override
    public void dispose() {
        super.dispose();
    }
}
