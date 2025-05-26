package end.team.center.GameCore.Objects.OnMap;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import end.team.center.GameCore.Library.CharacterAnimation;
import end.team.center.GameCore.Library.Items.Knife;
import end.team.center.GameCore.Library.WeaponType;
import end.team.center.GameCore.Logic.ShaderManager;
import end.team.center.GameCore.Objects.InInventary.Weapon;
import end.team.center.Screens.Game.GameScreen;

public class Hero extends Friendly {

    protected boolean isInvulnerability = false;
    protected int radiationProtect = 1;
    protected float antiRadiationCostumePower = 100.0f;
    protected int radiationLevel = 0;
    protected Weapon weapon;
    protected int expWeapon = 0, levelWeapon = 0;
    protected int exp, level ;
    protected float expBonus = 1, damageBonus = 0;
    protected boolean vampirism = false;
    protected int maxExp;
    public boolean newLevelFlag = false, shyne = false;
    private int duration = 30;
    private float elapsedTime = 0;



    public Hero(Texture texture, CharacterAnimation anim, Vector2 vector, float height, float width, int health, int damage, int defence, float speed, float worldWidth, float worldHeight) {
        super(texture, anim, vector, height, width, health, damage, defence, speed, worldHeight, worldWidth);

        weapon = new Knife(WeaponType.knife, this);
        exp = 0;
        level = 1;
        maxExp = 20;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public void setVampirism(boolean set) {
        vampirism = set;
    }

    public boolean getVampirism() {
        return vampirism;
    }

    public float getExpBonus() {
        return expBonus;
    }

    public void setExpBonus(float expBonus) {
        this.expBonus = expBonus;
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
    public int getMaxExp() {
        return level;
    }


    public void addExp(int add) {
        exp += (int) (add * expBonus);
    }

    public void newLevel() {
        if(exp >= maxExp) {
            level++;
            maxExp = (int) (maxExp * level * 0.3);
            exp = 0;
            newLevelFlag = true;
        }
    }

    public void addExpWeapon(int add) {
        expWeapon += add;

        upgradeWeapon();
    }

    protected void upgradeWeapon() { // 140; +
        if (expWeapon >= 10 && levelWeapon == 0) {
            System.out.println("Новый уроввень оружия! >1<");
            levelWeapon = 1;
            weapon.setSize(90, 150);
        } else if (expWeapon >= 30 && levelWeapon == 1) {
            System.out.println("Новый уроввень оружия! >2<");
            levelWeapon = 2;
            weapon.setSize(100, 170);
        } else if (expWeapon >= 60 && levelWeapon == 2) {
            System.out.println("Новый уроввень оружия! >3<");
            levelWeapon = 3;
            weapon.setSize(110, 190);
        } else if (expWeapon >= 90 && levelWeapon == 3) {
            System.out.println("Новый уроввень оружия! >4<");
            levelWeapon = 4;
            weapon.setSize(120, 210);
        } else if (expWeapon >= 130 && levelWeapon == 4) {
            System.out.println("Новый уроввень оружия! >5<");
            levelWeapon = 5;
            weapon.setSize(140, 230);
        } else if (expWeapon >= 170 && levelWeapon == 5) {
            System.out.println("Новый уроввень оружия! >6<");
            levelWeapon = 6;
            weapon.setSize(150, 260);
        } else if (expWeapon >= 200 && levelWeapon == 6) {
            System.out.println("Новый уроввень оружия! >7<");
            levelWeapon = 7;
            weapon.setSize(155, 300);
        }

        weapon.setDamage((int) (damageBonus + 3 + (float) (expWeapon / 5)));
        System.out.println("Урон оружия: " + weapon.getDamage());
    }

    public void setRadiationLevel() {
        if        (GameScreen.totalTime >= 10 && radiationLevel < 1) {
            System.out.println("Урвоень радиации: 1");
            radiationLevel = 1;
        } else if (GameScreen.totalTime >= 50 && radiationLevel < 2) {
            System.out.println("Урвоень радиации: 2");
            radiationLevel = 2;
        } else if (GameScreen.totalTime >= 90 && radiationLevel < 3) {
            System.out.println("Урвоень радиации: 3");
            radiationLevel = 3;
        } else if (GameScreen.totalTime >= 130 && radiationLevel < 4) {
            System.out.println("Урвоень радиации: 4");
            radiationLevel = 4;
        }
    }

    public void addCostumePower(int add) {
        antiRadiationCostumePower += add;
    }

    public void startShyne() {
        shyne = true;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    @Override
    public void move(float deltaX, float deltaY, float delta) {
        super.move(deltaX, deltaY, delta);
    }

    @Override
    public void act(float delta) {
        super.act(delta);

        setRadiationLevel();
        antiRadiationCostumePower -= (float) (((radiationLevel * 0.6) / radiationProtect) * delta);
        if (antiRadiationCostumePower < 0) antiRadiationCostumePower = 0;

        ((Knife) weapon).act(delta);

        if (shyne) {
            elapsedTime += delta;

            if (elapsedTime > duration) {
                shyne = false;
                duration = 30;
                ShaderManager.radiusView1 = -0.05f;
                ShaderManager.radiusView2 = -0.05f;
                ShaderManager.radiusView3 = -0.05f;
            }
        }
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        TextureRegion currentFrame;

        if (isMoving) {
            currentFrame = mRight
                ? walkRightAnimation.getKeyFrame(stateTime, true)
                : walkLeftAnimation .getKeyFrame(stateTime, true);
        } else {
            currentFrame = mRight
                ? stayRightAnimation.getKeyFrame(stateTime, true)
                : stayLeftAnimation .getKeyFrame(stateTime, true);
        }

        batch.draw(currentFrame, vector.x, vector.y, getWidth(), getHeight());

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
