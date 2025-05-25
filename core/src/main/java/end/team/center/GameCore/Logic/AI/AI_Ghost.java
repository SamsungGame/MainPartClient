package end.team.center.GameCore.Logic.AI;

import com.badlogic.gdx.math.Vector2;

import end.team.center.GameCore.Library.Mobs.Ghost;
import end.team.center.GameCore.Objects.OnMap.Hero;

public class AI_Ghost extends AI {
    protected boolean isShotLoad = false, isReloaded = true;
    protected Vector2 shotPos;
    public AI_Ghost(Hero hero) {
        super(hero);
    }

    @Override
    public Vector2 MoveToPlayer(Vector2 target, Vector2 position, float speed, float delta) {
        return super.MoveToPlayer(target, position, speed, delta);
    }

    public Vector2 RunOurPlayer(Vector2 target, Vector2 position, float speed, float delta) {
        Vector2 v = super.MoveToPlayer(target, position, speed, delta);

        v.x = -v.x;
        v.y = -v.y;

        return v;
    }

    public Vector2 move(Ghost ghost, float delta) {
        if        (hero.getBound().contains(ghost.getRunCircle()) && !isShotLoad) {
            return RunOurPlayer(hero.getVector(), ghost.getVector(), ghost.getSpeed(), delta);
        } else if (hero.getBound().contains(ghost.getStopCircle())) {
            return new Vector2(0, 0);
        } else if (!isShotLoad) {
            return MoveToPlayer(hero.getVector(), ghost.getVector(), ghost.getSpeed(), delta);
        } else {
            return new Vector2(0, 0);
        }
    }

    public void shot(Ghost ghost) {
        if (hero.getBound().contains(ghost.getStopCircle()) && !hero.getBound().contains(ghost.getRunCircle()) && !isShotLoad && isReloaded) {
            isShotLoad = true;
            isReloaded = false;

            shotPos = new Vector2(hero.getCenterVector());

            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException ignored) {}

                    shotStart(ghost);
                }
            }).start();
        }
    }

    public void shotStart(Ghost ghost) {

        // Атака

        isShotLoad = false;

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException ignore) {}

                isReloaded = true;
            }
        }).start();
    }
}
