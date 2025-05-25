package end.team.center.GameCore.Objects.OnMap;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;

import end.team.center.GameCore.Library.CharacterAnimation;
import end.team.center.GameCore.Library.Items.Knife;
import end.team.center.GameCore.Library.WeaponType;
import end.team.center.GameCore.Objects.InInventary.Weapon;

public class Hero extends Friendly {

    protected boolean isInvulnerability = false;
    protected int radiationProtect = 1;
    protected float antiRadiationCostumePower = 100.0f;
    protected int radiationLevel = 1;
    protected Weapon weapon;
    protected int exp, level;



    public Hero(Texture texture, CharacterAnimation anim, Vector2 vector, float height, float width, int health, int damage, int defence, float speed, float worldWidth, float worldHeight) {
        super(texture, anim, vector, height, width, health, damage, defence, speed, worldHeight, worldWidth);

        weapon = new Knife(WeaponType.knife, this);
    }

    public float getAntiRadiationCostumePower() {
        return antiRadiationCostumePower;
    }
    public Weapon getWep() {
        return weapon;
    }

    public void useWeapon(float x, float y) {
        if (weapon instanceof Knife) {
            ((Knife) weapon).showGhost(x, y);
        }
    }

    public void unUseWeapon() {
        if (weapon instanceof Knife) {
            ((Knife) weapon).hideGhost();
        }
    }

    public void startAttackAnim() {
        if (weapon instanceof Knife) {
            ((Knife) weapon).startAnimation();
        }
    }

    public int getExp() {
        return exp;
    }

    public int getLevel() {
        return level;
    }

    public void addExp(int add) {
        exp += add;
    }

    @Override
    public void move(float deltaX, float deltaY, float delta, boolean isMob) {
        super.move(deltaX, deltaY, delta, isMob);
    }

    @Override
    public void act(float delta) {
        super.act(delta);

        antiRadiationCostumePower -= (float) (((radiationLevel * 0.6) / radiationProtect) * delta);

        ((Knife) weapon).act(delta);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);

        if (weapon instanceof Knife) {
            ((Knife) weapon).draw(batch, parentAlpha);
        }
    }

    @Override
    public void dispose() {
        super.dispose();
    }

    public void frameInvulnerability(float sec) {
        isInvulnerability = true;

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep((long) (sec * 1000L));

                    isInvulnerability = false;
                } catch (InterruptedException ignore) {}
            }
        }).start();
    }


}
