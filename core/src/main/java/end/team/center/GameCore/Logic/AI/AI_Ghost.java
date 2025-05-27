package end.team.center.GameCore.Logic.AI;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;

import end.team.center.GameCore.Library.Mobs.Ghost;
import end.team.center.GameCore.Objects.Effects.Death;
import end.team.center.GameCore.Objects.OnMap.Ammo;
import end.team.center.GameCore.Objects.OnMap.Hero;

public class AI_Ghost extends AI {
    protected boolean isShotLoad = false, isReloaded = true;
    protected Vector2 shotPos;
    public AI_Ghost(Hero hero) {
        super(hero);
    }

    public boolean getIsShotLoad() {
        return isShotLoad;
    }

    @Override
    public Vector2 MoveToPlayer(Vector2 target, Vector2 position, float speed, float delta) {
        return super.MoveToPlayer(target, position, speed, delta);
    }

    public Vector2 move(Ghost ghost, float delta) {
        if        (hero.getBound().overlaps(ghost.getStopRectangle()) && !isShotLoad) {
            return new Vector2(0, 0);
        } else if (!isShotLoad) {
            return new Vector2(0, 0);
        } else {
            return MoveToPlayer(hero.getVector(), ghost.getVector(), ghost.getSpeed(), delta);
        }
    }

    public void shot(Ghost ghost) {
        if (hero.getBound().overlaps(ghost.getStopRectangle()) && !isShotLoad && isReloaded) {
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

        Ammo ammo = new Ammo(new Texture(Gdx.files.internal("UI/GameUI/Mobs/Ghost/ammo.png")), ghost.therdTypeAnimation.getKeyFrame(ghost.stateTime, true), new Death() {
            @Override
            public void die() {

            }

            @Override
            public void whoDie(Object o) {
                whoDieAmmo((Ammo) o, ghost);
            }
        }, ghost, hero, 42, 42, 400);
        ghost.addAmmo(ammo);

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

    public void whoDieAmmo(Ammo a, Ghost g) {
        int size = g.getAmmos().size();
        for (int i = 0; i < size; i++) {
            if (g.getAmmos().get(i) == a) g.getAmmos().remove(a);
            size--;
        }
    }
}
