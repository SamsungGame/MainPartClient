package end.team.center.GameCore.Logic.AI;

import com.badlogic.gdx.math.Vector2;

import end.team.center.GameCore.Library.Mobs.Owl;
import end.team.center.GameCore.Logic.GMath;
import end.team.center.GameCore.Objects.Hero;

public class AI_Owl extends AI {

    public boolean isDiveAttacking = false, isAttaking = false, isTimeGo = false;
    public Vector2 lockAttack;
    public float timeToDive = 1f, timeToReloadDive = 0f;
    public AI_Owl(Hero hero) {
        super(hero);
    }

    @Override
    public Vector2 MoveToPlayer(Vector2 target, Vector2 position, float speed, float delta) {
        target = hero.getVector();

        if (!isDiveAttacking) return super.MoveToPlayer(target, position, speed, delta);
        else if (!isAttaking) return new Vector2(0, 0);
        else if (!GMath.checkVectorDistance(position, lockAttack, 50, 50))
            return super.MoveToPlayer(lockAttack, position, speed * 6, delta);
        else {
            lockAttack = null;
            isAttaking = false;
            isDiveAttacking = false;
            timeToReloadDive = 5;
            return super.MoveToPlayer(target, position, speed, delta);
        }
    }

    public void diveAttack(Owl owl) {
        if(GMath.circleRectangleOverlap(owl.getCircle(), hero.getBound()) && !isDiveAttacking && timeToReloadDive == 0) {

            isDiveAttacking = true;
            lockAttack = new Vector2(hero.getVector().x, hero.getVector().y);

            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep((long) (timeToDive * 1000L));
                    } catch (InterruptedException ignore) {}

                    isAttaking = true;
                }
            }).start();
        } else if (timeToReloadDive != 0 && !isTimeGo) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    isTimeGo = true;

                    try {
                        Thread.sleep((long) (timeToReloadDive * 1000L));
                    } catch (InterruptedException ignore) {}

                    timeToReloadDive = 0;
                    isTimeGo = false;
                }
            }).start();
        }
    }
}
