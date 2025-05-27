package end.team.center.Screens.Game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.VerticalGroup;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import end.team.center.GameCore.GameEvent.SpawnItem;
import end.team.center.GameCore.Library.CharacterAnimation;
import end.team.center.GameCore.GameEvent.Post;
import end.team.center.GameCore.GameEvent.SpawnMob;
import end.team.center.GameCore.Library.ItemType;
import end.team.center.GameCore.Library.Items.Experience;
import end.team.center.GameCore.Library.Other.Rock;
import end.team.center.GameCore.Objects.InInventary.Drops;
import end.team.center.GameCore.Objects.Map.BackgroundActor;
import end.team.center.GameCore.Objects.Map.Zone;
import end.team.center.GameCore.Objects.OnMap.Enemy;
import end.team.center.GameCore.Objects.OnMap.Entity;
import end.team.center.GameCore.Objects.OnMap.Hero;
import end.team.center.GameCore.UIElements.Power;
import end.team.center.GameCore.UIElements.UIGameScreenElements.Heart;
import end.team.center.GameCore.UIElements.UIGameScreenElements.TouchpadClass;
import end.team.center.GameCore.Logic.GameCamera;
import end.team.center.GameCore.Logic.ShaderManager;

public class GameScreen implements Screen {

    private TouchpadClass touchpadMove, touchpadAttack;
    private Hero hero;
    private Stage worldStage;
    private Stage uiStage;
    private Viewport worldViewport;
    private Viewport uiViewport;

    private GameCamera gameCamera;
    public static final float WORLD_WIDTH = 10000;
    public static final float WORLD_HEIGHT = 10000;

    private SpawnMob spawner;
    public static ArrayList<Enemy> enemies;
    private SpawnItem spawnItem;
    public static ArrayList<Drops> drop;
    private Heart hearts;
    private ArrayList<Object> wait;

    private FrameBuffer frameBuffer;
    private FrameBuffer hardMaskBuffer;
    private SpriteBatch batch;

    // Шейдеры теперь берутся из ShaderManager
    private ShaderProgram maskShader;
    private ShaderProgram hardMaskShader;
    private ShaderProgram dimmingShader;

    public static float totalTime = 0f;
    public static float TIME = 0f;

    // Dialog для усиления
    protected Dialog selectPower;
    protected VerticalGroup content1, content2, content3;
    protected ArrayList<Power> powers;
    public static boolean STOP = false;
    ProgressBar expBar;
    Label energyValue, radiationValue;

    public boolean isIteration = false;
    public static ArrayList<Zone> zone = new ArrayList<>();



    @SuppressWarnings("NewApi")
    public GameScreen() {
        System.out.println("Размеры экрана: " + Gdx.graphics.getWidth() + "x на " + Gdx.graphics.getHeight() + "y");

        Texture brick1 = new Texture("UI/GameUI/Grow/dirt1_big.png");
        Texture brick2 = new Texture("UI/GameUI/Grow/dirt2_big.png");
        Texture brick3 = new Texture("UI/GameUI/Grow/dirt3_big.png");
        Texture brick4 = new Texture("UI/GameUI/Grow/dirt4_big.png");
        Texture brick5 = new Texture("UI/GameUI/Grow/dirt5_big.png");

        TextureRegion[] tiles = new TextureRegion[] {
            new TextureRegion(brick1),
            new TextureRegion(brick2),
            new TextureRegion(brick3),
            new TextureRegion(brick4),
            new TextureRegion(brick5)
        };

        int tileWidth = 250;
        int tileHeight = 250;

        int cols = (int) (WORLD_WIDTH / tileWidth);
        int rows = (int) (WORLD_HEIGHT / tileHeight);


        BackgroundActor background = new BackgroundActor(tiles, cols, rows, tileWidth, tileHeight);


        wait = new ArrayList<>();

        gameCamera = new GameCamera(WORLD_WIDTH, WORLD_HEIGHT);

        worldViewport = new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), gameCamera.getCamera());
        worldStage = new Stage(worldViewport);

        uiViewport = new ScreenViewport();
        uiStage = new Stage(uiViewport);
        Gdx.input.setInputProcessor(uiStage);
        worldStage.addActor(background);

        touchpadMove = new TouchpadClass(200, 200, false, "move");
        uiStage.addActor(touchpadMove);

        touchpadAttack = new TouchpadClass(Gdx.graphics.getWidth() - 500, 200, false, "attack");
        uiStage.addActor(touchpadAttack);

        // Разбрасывание камней по карте
//        Texture textureT1 = new Texture(Gdx.files.internal("UI/GameUI/OtherGameItems/rock1.png"));
//        Texture textureT2 = new Texture(Gdx.files.internal("UI/GameUI/OtherGameItems/rock2.png"));
//
//        Random r = new Random();
//
//        for (int i = 0; i < 45; i++) { // 15 камней каждого вида
//            worldStage.addActor(new Rock(
//                textureT1,
//                new Vector2(r.nextInt((int) Entity.BOUNDARY_PADDING, (int) WORLD_WIDTH), r.nextInt((int) Entity.BOUNDARY_PADDING, (int) WORLD_HEIGHT)),
//                28, 20, true));
//            worldStage.addActor(new Rock(
//                textureT2,
//                new Vector2(r.nextInt((int) Entity.BOUNDARY_PADDING, (int) WORLD_WIDTH), r.nextInt((int) Entity.BOUNDARY_PADDING, (int) WORLD_HEIGHT)),
//                28, 20, true));
//        }

        hero = new Hero(
            new Texture(Gdx.files.internal("UI/GameUI/Hero/Right/heroRight.png")),
            CharacterAnimation.Hero,
            new Vector2(WORLD_WIDTH / 2f - 70, WORLD_HEIGHT / 2f - 80),
            140, 120 ,3,
            1, 0, 300f,
            WORLD_WIDTH, WORLD_HEIGHT
        );

        worldStage.addActor(hero);

        Texture heartFull = new Texture("UI/GameUI/OtherGameItems/heart_full.png");
        Texture heartEmpty = new Texture("UI/GameUI/OtherGameItems/heart_empty.png");
        Texture heartFullBit = new Texture("UI/GameUI/OtherGameItems/heart_full_bit.png");
        hearts = new Heart(heartFull, heartEmpty,heartFullBit, hero.getHealth());
        uiStage.addActor(hearts);

        Texture EnergyValue = new Texture("UI/GameUI/OtherGameItems/energy.png");
        Image EnergyValueImg = new Image(EnergyValue);
        EnergyValueImg.setSize(90,126);
        EnergyValueImg.setPosition((float) Gdx.graphics.getWidth() - 150, Gdx.graphics.getHeight() - 160);

        Skin energySkin = new Skin(Gdx.files.internal("UI/GameUI/OtherGameItems/energyText.json"));

        energyValue = new Label(String.valueOf(hero.getAntiRadiationCostumePower()), energySkin);
        energyValue.setFontScale(5f);

        energyValue.setPosition(Gdx.graphics.getWidth() - EnergyValue.getWidth() - energyValue.getWidth() - 280,
            Gdx.graphics.getHeight() - energyValue.getHeight() - 100);

        Skin radiationSkin = new Skin(Gdx.files.internal("UI/GameUI/OtherGameItems/energyText.json"));

        radiationValue = new Label(String.valueOf(hero.getAntiRadiationCostumePower()), radiationSkin);
        radiationValue.setFontScale(4f);

        radiationValue.setPosition(Gdx.graphics.getWidth() - EnergyValue.getWidth() - energyValue.getWidth() - 180,
            Gdx.graphics.getHeight() - energyValue.getHeight() - radiationValue.getHeight() - 200);

        uiStage.addActor(radiationValue);
        uiStage.addActor(EnergyValueImg);
        uiStage.addActor(energyValue);

        Texture ExpTexture1 = new Texture("UI/GameUI/OtherGameItems/expBorderLeft.png");
        Image image = new Image(ExpTexture1);
        image.setSize(20, 20);
        image.setPosition((float) Gdx.graphics.getWidth() /2 - 420, Gdx.graphics.getHeight() - 110);
        uiStage.addActor(image);

        Skin skin = new Skin(Gdx.files.internal("UI/GameUI/OtherGameItems/expProgress.json"));
        expBar = new ProgressBar(0, hero.getMaxExp(), 1, false, skin);
        expBar.setSize(800, 60);
        expBar.setPosition((float) Gdx.graphics.getWidth() /2 - 400, Gdx.graphics.getHeight() - 130);
        uiStage.addActor(expBar);

        Texture ExpTexture2 = new Texture("UI/GameUI/OtherGameItems/expBorderRight.png");
        Image image2 = new Image(ExpTexture2);
        image2.setSize(20,20);
        image2.setPosition((float) Gdx.graphics.getWidth() /2 + 400, Gdx.graphics.getHeight() - 110);
        uiStage.addActor(image2);

        drop = new ArrayList<>();

        // Настройки спавна мобов
        enemies = new ArrayList<>();

        spawner = new SpawnMob(new Post() {
            @Override
            public void post(Enemy[] enemy) {
                setSpawnMob(enemy);
            }
            @Override
            public void post(Drops drops) {

            }
        }, hero);

        spawnItem = new SpawnItem(new Post() {
            @Override
            public void post(Enemy[] enemy) {

            }

            @Override
            public void post(Drops drops) {
                setSpawnItem(drops);
            }
        }, hero);

        frameBuffer = new FrameBuffer(Pixmap.Format.RGBA8888, Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), false);
        hardMaskBuffer = new FrameBuffer(Pixmap.Format.RGBA8888, Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), false);

        batch = new SpriteBatch();

        // Шейдеры из ShaderManager
        maskShader = ShaderManager.maskShader;
        hardMaskShader = ShaderManager.hardMaskShader;
        dimmingShader = ShaderManager.dimmingShader;

        float aspectRatio = (float) Gdx.graphics.getHeight() / Gdx.graphics.getWidth();
        maskShader.bind();
        maskShader.setUniformf("u_aspectRatio", aspectRatio);
        hardMaskShader.bind();
        hardMaskShader.setUniformf("u_aspectRatio", aspectRatio);
        dimmingShader.bind();
        dimmingShader.setUniformf("u_aspectRatio", aspectRatio);

        // Создание окна
        Skin ws = new Skin(Gdx.files.internal("UI/GameUI/Dialog/dialog.json"));
        selectPower = new Dialog("Выберите усиление!", ws);

        powers = new ArrayList<>();

        // Добавление существующих усилений
        Power p = new Power(new TextureRegionDrawable(new Texture("UI/GameUI/SelectPowerUI/Effect/expMore.png"))) {
            @Override
            public void effect() {
                hero.setExpBonus(hero.getExpBonus() * 2);
            }
        };
        p.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                p.effect();
                hidePowerDialog();
            }
        });
        p.setSize(600, 600);
        powers.add(p);

        Power p1 = new Power(new TextureRegionDrawable(new Texture("UI/GameUI/SelectPowerUI/Effect/HPforAttack.png"))) {
            @Override
            public void effect() {
                hero.setVampirism(true);
            }
        };
        p.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                p.effect();
                hidePowerDialog();
            }
        });
        p1.setSize(600, 600);
        powers.add(p1);

        Power p2 = new Power(new TextureRegionDrawable(new Texture("UI/GameUI/SelectPowerUI/Effect/speedHP.png"))) {
            @Override
            public void effect() {
                hero.setHealth(hero.getHealth() - 1);
                hero.setSpeed(hero.getSpeed() * 2);
            }
        };
        p.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                p.effect();
                hidePowerDialog();
            }
        });
        p2.setSize(600, 600);
        powers.add(p2);

        Power p3 = new Power(new TextureRegionDrawable(new Texture("UI/GameUI/SelectPowerUI/Effect/visible.png"))) {
            @Override
            public void effect() {
                ShaderManager.radiusView1 += 0.1f;
                ShaderManager.radiusView2 += 0.1f;
                ShaderManager.radiusView3 += 0.1f;
            }
        };
        p.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                p.effect();
                hidePowerDialog();
            }
        });
        p3.setSize(600, 600);
        powers.add(p3);

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    totalTime++;

                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException ignore) {}
                }
            }
        }).start();

        for (int i = 0; i < 6; i++) {
            Zone z = new Zone((int) Math.floor(1 + Math.random() * 5));
            zone.add(z);
        }

        spawner.startWork();
        spawnItem.goWork();
    }

    @SuppressWarnings("DefaultLocale")
    @Override
    public void render(float delta) {
        addToList();

        TIME += delta;

        if (hero.newLevelFlag) {
            showPowerDialog();
            hero.newLevelFlag = false;
        }

        float moveX = touchpadMove.getKnobPercentX();
        float moveY = touchpadMove.getKnobPercentY();

        // Получаем нормализованные значения от Touchpad
        float normalizedX = (touchpadAttack.getKnobPercentX() + 1) / 2;
        float normalizedY = (touchpadAttack.getKnobPercentY() + 1) / 2;
        float dx = normalizedX * 2 - 1; // от -1 до 1
        float dy = normalizedY * 2 - 1;

        // Обновление значений классов типа "Object & Interacteble"
        hero.move(moveX, moveY, delta);

        if (touchpadAttack.isTouchpadActive()) {
            hero.useWeapon(dx, dy);

        } else if (hero.getWep().getShow() && hero.getWep().isCanAttack()) {
            hero.startAttackAnim();

            for(Enemy e: enemies) {
                if (hero.getWep().checkTouchRectangle(e.getBound())) {

                    System.out.println("Удар! Врагов: " + enemies.size());

                    e.setHealth(e.getHealth() - hero.getWep().getDamage());
                    e.stan(1);

                    if (Math.random() * 100 > 80 && hero.getHealth() < 3 && hero.getVampirism()) {
                        hero.setHealth(hero.getHealth() + 1);
                    }

                    if (e.getHealth() <= 0) {
                        e.die();

                        Experience experience = new Experience(ItemType.exp, new Vector2(e.getCenterVector()), hero, e.getExp());
                        worldStage.addActor(experience);
                    }
                }
            }

            hero.unUseWeapon();
        } else if (hero.getWep().getShow()) {
            hero.unUseWeapon();
        }

        for(Enemy e: enemies) {
            if (e.getBound().overlaps(hero.getBound())) e.attack(hero);
        }
        int max = enemies.size();

        // Удаление мобов
        for(int i = 0; i < max; i++) {
            if (!enemies.get(i).isLive()) {
                Enemy e = enemies.get(i);
                enemies.remove(e);
                e.remove();
                max--;
            }
        }

        expBar.setValue(hero.getExp());
        hero.newLevel();

        // Обновление UI игрока
        energyValue.setText(String.format("%.1f", hero.getAntiRadiationCostumePower()));
        radiationValue.setText(hero.getRadiationLevel());

        hearts.updateAnimation(delta);
        hearts.updateHealth(hero.getHealth());

        // Обновление камеры
        gameCamera.updateCameraPosition(hero.getVector().x, hero.getVector().y, hero.getWidth(), hero.getHeight());

        // Шейдер
        if (!STOP) {
            frameBuffer.begin();
            Gdx.gl.glClearColor(0, 0, 0, 1);
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
            batch.begin();
            worldStage.act(delta);
            worldStage.draw();
            batch.end();
            frameBuffer.end();
        }

        Vector2 heroPosScreen = worldStage.stageToScreenCoordinates(
            new Vector2(hero.getX() + hero.getWidth() / 2f, hero.getY() + hero.getHeight() / 2f)
        );
        float heroXNorm = heroPosScreen.x / Gdx.graphics.getWidth();
        float heroYNorm = 1f - (heroPosScreen.y / Gdx.graphics.getHeight());

        Texture worldTexture = frameBuffer.getColorBufferTexture();
        worldTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);

        // Мягкая маска
        batch.setShader(maskShader);
        maskShader.bind();
        maskShader.setUniformf("u_heroPos", heroXNorm, heroYNorm);
        maskShader.setUniformf("u_time", TIME);

        batch.begin();
        batch.draw(worldTexture, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight(),
            0, 0, worldTexture.getWidth(), worldTexture.getHeight(), false, true);
        batch.end();

        // Затемнение
        batch.setShader(dimmingShader);
        dimmingShader.bind();
        dimmingShader.setUniformf("u_heroPos", heroXNorm, heroYNorm);

        batch.begin();
        batch.draw(worldTexture, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight(),
            0, 0, worldTexture.getWidth(), worldTexture.getHeight(), false, true);
        batch.end();

        batch.setShader(null);
        Gdx.gl.glDisable(GL20.GL_BLEND);

        // Обновление джостиков
        touchpadMove.TouchpadLogic(uiStage);
        touchpadAttack.TouchpadLogic(uiStage);

        // Установка джостиков
        touchpadMove.touchpadSetBounds();
        touchpadAttack.touchpadSetBounds();


        // Отрисовка обьектов
        uiStage.act(delta);
        uiStage.draw();
    }

    @Override
    public void resize(int width, int height) {
        // Сообщаем об изменении размеров камере и вьюпортам
    }

    @Override
    public void dispose() {
        worldStage.dispose();
        uiStage.dispose();
        touchpadMove.dispose();
        touchpadAttack.dispose();
        hero.dispose();
    }

    @Override public void show() {

    }
    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}

    @SuppressWarnings("NewApi")
    public void showPowerDialog() {
        STOP = true;

        Power[] imgB = new Power[3];

        Random random = new Random();

        for (int i = 0; i < 3; i++) {
            imgB[i] = powers.get(random.nextInt(0, powers.size() - 1));
        }

        selectPower.getContentTable().center().setFillParent(true);

        content1 = new VerticalGroup();
        content2 = new VerticalGroup();
        content3 = new VerticalGroup();

        content1.addActor(imgB[0]);
        content1.setSize(600, 600);
        selectPower.getContentTable().add(content1).height(600).width(600).padRight(20);

        content2.addActor(imgB[1]);
        content2.setSize(600, 600);
        selectPower.getContentTable().add(content2).height(600).width(600).padRight(20);

        content3.addActor(imgB[2]);
        content3.setSize(600, 600);
        selectPower.getContentTable().add(content3).height(600).width(600);

        selectPower.show(uiStage);
    }

    public void hidePowerDialog() {
        STOP = false;
        selectPower.hide();
        selectPower.getContentTable().clear();
    }

    public void setSpawnMob(Enemy[] enemy) {
        for(Enemy e: enemy) {
            if (e != null) wait.add(e);
        }
    }

    public void setSpawnItem(Drops d) {
        wait.add(d);
    }

    public void addToList() {
        for(Object o: wait) {
            if (o != null) {
                if (o instanceof Enemy) {
                    enemies.add((Enemy) o);
                } else if (o instanceof Drops) {
                    drop.add((Drops) o);
                }
                worldStage.addActor((Actor) o);
            }
        }
        wait.clear();
    }
}
