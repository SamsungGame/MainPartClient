package end.team.center.GameCore.Objects.OnMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

import end.team.center.Center;
import end.team.center.GameCore.Library.CharacterAnimation;
import end.team.center.GameCore.Library.Items.Knife;
import end.team.center.GameCore.Library.WeaponType;
import end.team.center.GameCore.Logic.GMath;
import end.team.center.GameCore.Logic.ShaderManager;
import end.team.center.GameCore.Objects.InInventary.Weapon;
import end.team.center.GameCore.Objects.Map.Zone;
import end.team.center.GameCore.SuperAbilities.ChargeRecoveryAbility;
import end.team.center.GameCore.SuperAbilities.HeroAbility;
import end.team.center.GameCore.SuperAbilities.ProjectionAbility;
//import end.team.center.GameCore.SuperAbilities.ShieldAbility;
import end.team.center.GameCore.SuperAbilities.ShieldAbility;
import end.team.center.GameCore.SuperAbilities.TeleportAbility;
import end.team.center.ProgramSetting.LocalDB.GameRepository;
import end.team.center.Screens.Game.GameScreen;
import end.team.center.Screens.Menu.MainMenuScreen;

public class Hero extends Friendly {
    private GameRepository gameRepository;
    protected boolean isInvulnerability = false;
    protected int radiationProtect = 1;
    protected float antiRadiationCostumePower = 100.0f;
    protected int radiationLevel = 0;
    protected Weapon weapon;
    public Rectangle deathZone;
    protected int expWeapon = 0, levelWeapon = 0;
    protected int exp, level, maxHP;
    public HeroAbility uniqueAbility;
    public boolean isAbilityActive = false;
    public enum HeroClassType {
        STALKER_HERO,
        GHOST_HERO,
        KNIGHT_HERO,
        CYBER_HERO
    }
    private HeroClassType heroClassType;
    protected float expBonus = 1, damageBonus = 0;
    protected boolean vampirism = false, activeBaffShield = false, collectEnergy = false;
    protected int maxExp, sheildLevel;
    public boolean newLevelFlag = false, shyne = false, safeInDeadDamage = false, returnDamage = false;
    private int duration = 30;
    private float elapsedTime = 0;
    public boolean disableMovement = false;
    private float disableMovementTimer = 0;
    private final float DISABLE_MOVEMENT_DURATION = 0.1f;
    private Texture activeSheild, sheild1, sheild2, sheild3;
    Sound soundUronShield = Gdx.audio.newSound(Gdx.files.internal("Sounds/uron.mp3"));


    public Hero(GameRepository repo, Texture texture, CharacterAnimation anim, HeroClassType heroClassType, Vector2 vector, float height, float width, int health, int damage, int defence, float speed, float worldWidth, float worldHeight) {
        super(texture, anim, vector, height, width, health, damage, defence, speed, worldHeight, worldWidth);
        this.gameRepository = repo;
        weapon = new Knife(WeaponType.knife, this);
        exp = 0;
        level = 1;
        maxExp = 10;
        sheildLevel = 0;
        maxHP = 3;
        deathZone = new Rectangle(vector.x - 50, vector.y - 40, 220, 220);
        this.heroClassType = heroClassType;
        sheild1 = new Texture(Gdx.files.internal("UI/GameUI/SelectPowerUI/shield1.png"));
        sheild2 = new Texture(Gdx.files.internal("UI/GameUI/SelectPowerUI/shield2.png"));
        sheild3 = new Texture(Gdx.files.internal("UI/GameUI/SelectPowerUI/shield3.png"));

        switch (heroClassType) {
            case STALKER_HERO:
                this.uniqueAbility = new ChargeRecoveryAbility(this);
                break;
            case GHOST_HERO:
                this.uniqueAbility = new ProjectionAbility(this);
                break;
            case KNIGHT_HERO:
                this.uniqueAbility = new ShieldAbility(this);
                break;
            case CYBER_HERO:
                this.uniqueAbility = new TeleportAbility(this);
                break;
            default:
                this.uniqueAbility = new ChargeRecoveryAbility(this);;
                break;
        }
    }

    public void activateUniqueAbility() {
        if (uniqueAbility != null) {
            uniqueAbility.activate();
            isAbilityActive = uniqueAbility.isActive();
        }
    }
    public void setDisableMovement(boolean disable) {
        this.disableMovement = disable;
        if (disable) {
            this.disableMovementTimer = 0;
        }
    }

    public void activeBuffShield() {
        activeBaffShield = true;
        sheildLevel = 1;

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (activeBaffShield) {
                    try {
                        Thread.sleep(1000 * 60);
                    } catch (InterruptedException ignored) {}

                    if (sheildLevel < 3 && activeBaffShield) {
                        sheildLevel++;
                    }
                }
            }
        }).start();
    }

    public void activeCollectEnergy() {
        collectEnergy = true;
    }

    public boolean getEnergyCollect() {
        return collectEnergy;
    }

    public boolean getIsInvulnerability() {
        return isInvulnerability;
    }
    public int getLevelSheild() {
        return sheildLevel;
    }

    public void setSheildLevel(int sheildLevel) {
        this.sheildLevel = sheildLevel;
        long id = soundUronShield.play();
        soundUronShield.setVolume(id, 0.5f);
    }

    public void offShield() {
        activeBaffShield = false;
    }

    public boolean getActiveSheild() {
        return activeBaffShield;
    }

    public int getRadiationLevel() {
        return radiationLevel;
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
    public void addAntiRadiationCostumePower(float i) {
        antiRadiationCostumePower += i;
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
        return maxExp;
    }


    public void addExp(int add) {
        exp += (int) (add * expBonus);
    }

    public void newLevel() {
        if(exp >= maxExp) {
            level++;
            exp = 0;
            maxExp += 3;
            newLevelFlag = true;
        }
    }

    public void addExpWeapon(int add) {
        expWeapon += add;

        upgradeWeapon();
    }

    protected void upgradeWeapon() { // 140; +
        weapon.setDamage((int) (damageBonus + 3 + (float) (expWeapon / 3)));
        System.out.println("Урон оружия: " + weapon.getDamage());
    }

    public void setRadiationLevel() {
        boolean was = false;

        for(Zone z: GameScreen.zone) {
            if(z.bound.overlaps(bound)) {
                radiationLevel = z.level;
                was = true;
            }
        }

        if (!was) radiationLevel = 1;
    }

    public void addCostumePower(float add) {
        antiRadiationCostumePower += add;

        if (antiRadiationCostumePower > 100) antiRadiationCostumePower = 100;
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
    public void setReturnDamage(boolean returnDamage) {
        this.returnDamage = returnDamage;
    }
    public void setMaxHealth(int maxHP) {
        this.maxHP = maxHP;
    }

    public int getMaxHealth() {
        return maxHP;
    }

    @Override
    public void move(float deltaX, float deltaY, float delta) {
        super.move(deltaX, deltaY, delta);
    }

    @Override
    public void act(float delta) {
        super.act(delta);

        if (disableMovement) {
            disableMovementTimer += delta;
            if (disableMovementTimer >= DISABLE_MOVEMENT_DURATION) {
                disableMovement = false;
                disableMovementTimer = 0;
                Gdx.app.log("Hero", "Movement ENABLED again."); // Лог для отладки
            }
        }

        if (uniqueAbility != null) {
            uniqueAbility.update(delta);
            isAbilityActive = uniqueAbility.isActive(); // Обновляем состояние активации
        }

        setRadiationLevel();
        antiRadiationCostumePower -= (float) (((radiationLevel * 0.2) / radiationProtect) * delta);
        if (antiRadiationCostumePower < 0) {
            gameRepository.addCoins(((int) GameScreen.coinForGame));
            ((Center) Gdx.app.getApplicationListener()).setScreen(new MainMenuScreen(3, gameRepository));

            if (GMath.checkVectorDistance(getCenterVector(), GameScreen.portal.getCenterVector(), 800, 800) && !GameScreen.gameRepository.getAchievements().get(4)) {
                MainMenuScreen.showAchivs = true;
                MainMenuScreen.imageAchivs = new Image(new Texture("UI/GameUI/Achievements/open/door_open.png"));
                MainMenuScreen.idAchivs = 4;
            }

            GameScreen.endCode = 3;
            GameScreen.endForHero = true;

        }

        ((Knife) weapon).act(delta);

        if (shyne) {
            elapsedTime += delta;

            if (elapsedTime > duration) {
                shyne = false;
                duration = 30;
                ShaderManager.radiusView1 /= 1.1f;
                ShaderManager.radiusView2 /= 1.1f;
                ShaderManager.radiusView3 /= 1.1f;
                elapsedTime = 0;
            }
        }

        if (this.health <= 0) {
            if (!safeInDeadDamage) {
                gameRepository.addCoins(((int) GameScreen.coinForGame));



                if (GMath.checkVectorDistance(getCenterVector(), GameScreen.portal.getCenterVector(), 800, 800) && !GameScreen.gameRepository.getAchievements().get(4)) {
                    MainMenuScreen.showAchivs = true;
                    MainMenuScreen.imageAchivs = new Image(new Texture("UI/GameUI/Achievements/open/door_open.png"));
                    MainMenuScreen.idAchivs = 4;
                }
                GameScreen.endCode = 2;
                GameScreen.endForHero = true;
            } else {
                this.health++;
                safeInDeadDamage = false;
            }
        }

        if (sheildLevel == 0) {
            activeSheild = null;
        } else if (sheildLevel == 1) {
            activeSheild = new Texture(sheild1.getTextureData());
        } else if (sheildLevel == 2) {
            activeSheild = new Texture(sheild2.getTextureData());
        } else if (sheildLevel == 3) {
            activeSheild = new Texture(sheild3.getTextureData());
        }
    }

    public void setSafeInDeadDamage(boolean set) {
        this.safeInDeadDamage = set;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        TextureRegion currentFrame;

        if (isMoving) {
            currentFrame = mRight
                ? firstTypeAnimation.getKeyFrame(stateTime, true)
                : secondTypeAnimation.getKeyFrame(stateTime, true);
        } else {
            currentFrame = mRight
                ? therdTypeAnimation.getKeyFrame(stateTime, true)
                : fourthTypeAnimation.getKeyFrame(stateTime, true);
        }

        batch.draw(currentFrame, vector.x, vector.y, getWidth(), getHeight());

        if (weapon instanceof Knife) {
            ((Knife) weapon).draw(batch, parentAlpha);
        }

        if (activeSheild != null) {
            int size = (int) Math.max(getWidth(), getHeight()) + 100;
            batch.draw(activeSheild, vector.x - 50, vector.y - 50, size, size);
        }
        if (uniqueAbility != null) {
            uniqueAbility.draw(batch); // Вызываем метод draw у способности
        }
    }

    @Override
    public void dispose() {
        super.dispose();
    }

    @Override
    public void setHealth(int health) {
            super.setHealth(health);

            if (health > maxHP) {
                health = maxHP;
            }
            this.health = health;
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
