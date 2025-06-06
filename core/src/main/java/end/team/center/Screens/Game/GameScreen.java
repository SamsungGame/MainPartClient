package end.team.center.Screens.Game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.ArrayList;
import java.util.Random;

import end.team.center.Center;
import end.team.center.GameCore.GameEvent.SpawnItem;
import end.team.center.GameCore.Library.CharacterAnimation;
import end.team.center.GameCore.GameEvent.Post;
import end.team.center.GameCore.GameEvent.SpawnMob;
import end.team.center.GameCore.Library.ItemType;
import end.team.center.GameCore.Library.Items.Experience;
import end.team.center.GameCore.Library.Other.Portal;
import end.team.center.GameCore.Objects.InInventary.Drops;
import end.team.center.GameCore.Objects.Map.BackgroundTiledRenderer;
import end.team.center.GameCore.Objects.Map.Tree;
import end.team.center.GameCore.Objects.Map.Zone;
import end.team.center.GameCore.Objects.OnMap.Enemy;
import end.team.center.GameCore.Objects.OnMap.Entity;
import end.team.center.GameCore.Objects.OnMap.Hero;
import end.team.center.GameCore.UIElements.Power;
import end.team.center.GameCore.UIElements.UIGameScreenElements.Heart;
import end.team.center.GameCore.UIElements.UIGameScreenElements.TouchpadClass;
import end.team.center.GameCore.Logic.GameCamera;
import end.team.center.GameCore.Logic.ShaderManager;
import end.team.center.ProgramSetting.Config;
import end.team.center.ProgramSetting.LocalDB.GameRepository;
import end.team.center.Redact.SystemOut.Console;

public class GameScreen implements Screen {
    public static GameRepository gameRepository;
    private TouchpadClass touchpadMove, touchpadAttack;
    public Hero hero;
    private Stage worldStage;
    private Stage uiStage;
    private Viewport worldViewport;
    private Viewport uiViewport;

    private GameCamera gameCamera;
    public static final float WORLD_WIDTH = 50000;
    public static final float WORLD_HEIGHT = 50000;
    public int maxMobSpawn = 60;
    public static float coinForEnemyValue = 0;
    public static float coinForTime = 0;
    public static float coinForGame = 0;

    public int maxDropSpawn = 1500;

    private static SpawnMob spawner;
    public static ArrayList<Enemy> enemies;
    private static SpawnItem spawnItem;
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
    protected ArrayList<Power> powers;
    public static boolean STOP = false;

    ProgressBar expBar;
    Label energyValue, radiationValue;
    public static ArrayList<Zone> zone = new ArrayList<>();

    protected PowerSelectScreen PSC;
    private boolean isShow = false;
    private BackgroundTiledRenderer backgroundTiledRenderer;
    public static Music backgroundMusic;

    private static boolean isTimeGo = true;
    private int countZone;

    //Деревья
    public static ArrayList<Tree> trees;
    private int countTree = 4000;

    public static Portal portal;

    public static boolean showAchivs = false;
    public static Image imageAchivs;
    public static int idAchivs;

    public static boolean isPickupItem = false;
    public static boolean isKill = false;
    boolean start = false;

    public int timeShowNewAch = 4; // sec




    @SuppressWarnings("NewApi")
    public GameScreen(GameRepository repo) {

        this.gameRepository = repo;

        System.out.println("Размеры экрана: " + Gdx.graphics.getWidth() + "x на " + Gdx.graphics.getHeight() + "y");

        countZone = (int) (100 + Math.random() * 100);

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

        backgroundTiledRenderer = new BackgroundTiledRenderer(tiles, tileWidth, tileHeight);

        wait = new ArrayList<>();

        gameCamera = new GameCamera(WORLD_WIDTH, WORLD_HEIGHT);

        worldViewport = new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), gameCamera.getCamera());
        worldStage = new Stage(worldViewport);

        uiViewport = new ScreenViewport();
        uiStage = new Stage(uiViewport);
        Gdx.input.setInputProcessor(uiStage);

        touchpadMove = new TouchpadClass(200, 200, false, "move");
        uiStage.addActor(touchpadMove);

        touchpadAttack = new TouchpadClass(Gdx.graphics.getWidth() - 500, 200, false, "attack");
        uiStage.addActor(touchpadAttack);

        if (!Config.skinIsKnight) {
            hero = new Hero(
                repo,
                new Texture(Gdx.files.internal("UI/GameUI/Hero/Right/heroRight.png")),
                CharacterAnimation.Hero,
                new Vector2(WORLD_WIDTH / 2f - 70, WORLD_HEIGHT / 2f - 80),
                140, 120, 3,
                1, 0, 300f,
                WORLD_WIDTH, WORLD_HEIGHT
            );
        } else {
            hero = new Hero(
                repo,
                new Texture(Gdx.files.internal("UI/GameUI/Hero/KnightRight/heroNighRight.png")),
                CharacterAnimation.Knight,
                new Vector2(WORLD_WIDTH / 2f - 70, WORLD_HEIGHT / 2f - 80),
                140, 120, 3,
                1, 0, 300f,
                WORLD_WIDTH, WORLD_HEIGHT
            );
        }

        worldStage.addActor(hero);

        Texture heartFull = new Texture("UI/GameUI/OtherGameItems/heart_full.png");
        Texture heartEmpty = new Texture("UI/GameUI/OtherGameItems/heart_empty.png");
        Texture heartFullBit = new Texture("UI/GameUI/OtherGameItems/heart_full_bit.png");
        hearts = new Heart(heartFull, heartEmpty,heartFullBit, hero.getHealth());
        uiStage.addActor(hearts);

        Texture EnergyValue = new Texture("UI/GameUI/OtherGameItems/energy.png");
        Image EnergyValueImg = new Image(EnergyValue);
        EnergyValueImg.setSize(70, 98);
        EnergyValueImg.setPosition((float) Gdx.graphics.getWidth() - 100, Gdx.graphics.getHeight() - 130);

        Texture radiationLevel = new Texture("UI/GameUI/OtherGameItems/warning.png");
        Image radiationLevelImg = new Image(radiationLevel);
        radiationLevelImg.setSize(90,78);
        radiationLevelImg.setPosition((float) Gdx.graphics.getWidth() - 120, Gdx.graphics.getHeight() - (130 + EnergyValueImg.getHeight()));

        Skin energySkin = new Skin(Gdx.files.internal("UI/GameUI/OtherGameItems/energyText.json"));
        energyValue = new Label(String.valueOf(hero.getAntiRadiationCostumePower()), energySkin);
        energyValue.setFontScale(5f);
        energyValue.setPosition(Gdx.graphics.getWidth() - (EnergyValueImg.getWidth() + energyValue.getWidth() + 150),
            Gdx.graphics.getHeight() - (energyValue.getHeight() + EnergyValueImg.getHeight() / 2 + 30));

        Skin radiationSkin = new Skin(Gdx.files.internal("UI/GameUI/OtherGameItems/energyText.json"));
        radiationValue = new Label(String.valueOf(hero.getAntiRadiationCostumePower()), radiationSkin);
        radiationValue.setFontScale(5f);
        radiationValue.setPosition(Gdx.graphics.getWidth() - radiationLevelImg.getWidth() - radiationValue.getWidth() - 30,
            Gdx.graphics.getHeight() - (energyValue.getHeight() + EnergyValueImg.getHeight() + radiationLevelImg.getHeight() / 2 + 50));

        uiStage.addActor(radiationValue);
        uiStage.addActor(EnergyValueImg);
        uiStage.addActor(radiationLevelImg);
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
        powers = new ArrayList<>();

        // Добавление существующих усилений
        Power p = new Power(new Texture("UI/GameUI/SelectPowerUI/Effect/expMore.png"),
            new Texture("UI/GameUI/SelectPowerUI/Effect/expMore_active.png"),
            "Увеличивает получаемый тобой опыт в 2 раза") {
            @Override
            public void effect() {
                hero.setExpBonus(hero.getExpBonus() * 2);

                hidePowerDialog();
                powers.remove(this);
            }
        };
        p.setSize(600, 600);
        powers.add(p);

        Power p1 = new Power(new Texture("UI/GameUI/SelectPowerUI/Effect/HPforAttack.png"),
            new Texture("UI/GameUI/SelectPowerUI/Effect/HPforAttack_active.png"),
            "Вы получаете вампиризм") {
            @Override
            public void effect() {
                hero.setVampirism(true);

                hidePowerDialog();
                powers.remove(this);
            }
        };
        p1.setSize(600, 600);
        powers.add(p1);

        Power p2 = new Power(new Texture("UI/GameUI/SelectPowerUI/Effect/speedHP.png"),
            new Texture("UI/GameUI/SelectPowerUI/Effect/speedHP_active.png"),
            "Ваша скорость увеличиваеться в 2 раза, но вы теряете 1 HP") {
            @Override
            public void effect() {
                hero.setHealth(hero.getHealth() - 1);
                hero.setSpeed(hero.getSpeed() * 2);

                hidePowerDialog();
                powers.remove(this);
            }
        };
        p2.setSize(600, 600);
        powers.add(p2);

        Power p3 = new Power(new Texture("UI/GameUI/SelectPowerUI/Effect/visible.png"),
            new Texture("UI/GameUI/SelectPowerUI/Effect/visible_active.png"),
            "Вы увеличиваете свой радиус обзора") {
            @Override
            public void effect() {
                ShaderManager.radiusView1 *= 1.2f;
                ShaderManager.radiusView2 *= 1.2f;
                ShaderManager.radiusView3 *= 1.2f;

                hidePowerDialog();
                powers.remove(this);
            }
        };
        p3.setSize(600, 600);
        powers.add(p3);

        Power p4 = new Power(new Texture("UI/GameUI/SelectPowerUI/Effect/saveHeart.png"),
            new Texture("UI/GameUI/SelectPowerUI/Effect/saveHeart_active.png"),
            "Вы получаете 1 перерождение в случае смерти") {
            @Override
            public void effect() {
                hero.setSafeInDeadDamage(true);

                hidePowerDialog();
                powers.remove(this);
            }
        };
        p4.setSize(600, 600);
        powers.add(p4);

        Power p5 = new Power(new Texture("UI/GameUI/SelectPowerUI/Effect/aura.png"),
            new Texture("UI/GameUI/SelectPowerUI/Effect/aura_active.png"),
            "Мобы умирают, если попадают по вам") {
            @Override
            public void effect() {
                hero.setReturnDamage(true);

                hidePowerDialog();
            }
        };
        p5.setSize(600, 600);
        powers.add(p5);

        Power p6 = new Power(new Texture("UI/GameUI/SelectPowerUI/Effect/timeShield.png"),
            new Texture("UI/GameUI/SelectPowerUI/Effect/timeShield_active.png"),
            "Вы получаете щит который улучшаетсья каждую минуту \n Улучшения сбрасываються при получении урона!") {
            @Override
            public void effect() {
                hero.activeBuffShield();

                hidePowerDialog();
                powers.remove(this);
            }
        };
        p6.setSize(600, 600);
        powers.add(p6);

        Power p7 = new Power(new Texture("UI/GameUI/SelectPowerUI/Effect/powerForAttack.png"),
            new Texture("UI/GameUI/SelectPowerUI/Effect/powerForAttack_active.png"),
            "Вы получаете немного энергии за каждый удар мобов") {
            @Override
            public void effect() {
                hero.activeCollectEnergy();

                hidePowerDialog();
                powers.remove(this);
            }
        };
        p7.setSize(600, 600);
        powers.add(p7);

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (isTimeGo) {
                    totalTime++;

                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException ignore) {}
                }
            }
        }).start();

        for (int i = 0; i < countZone; i++) {
            Zone z = new Zone((int) (1 + Math.random() * 5));
            zone.add(z);
        }

        boolean isGenerated = false;

        while (!isGenerated) {
            try {
                Random random = new Random();

                int x = (int) (Math.random() * WORLD_WIDTH / 5);
                int y = (int) (Math.random() * WORLD_HEIGHT / 5);

                float spawnX = Math.random() * 100 > 50 ? random.nextInt((int) (WORLD_WIDTH - x), (int) (WORLD_WIDTH - Entity.BOUNDARY_PADDING - 200))
                    : random.nextInt((int) (Entity.BOUNDARY_PADDING + 200), x);
                float spawnY = Math.random() * 100 > 50 ? random.nextInt((int) (WORLD_HEIGHT - y), (int) (WORLD_HEIGHT - Entity.BOUNDARY_PADDING - 200))
                    : random.nextInt((int) (Entity.BOUNDARY_PADDING + 200), y);
                portal = new Portal(
                    repo,
                    new Texture(Gdx.files.internal("UI/GameUI/Structure/portal1.png")),
                    new Texture(Gdx.files.internal("UI/GameUI/Structure/portal2.png")),
                    new Texture(Gdx.files.internal("UI/GameUI/Structure/portal3.png")),
                    new Vector2(spawnX, spawnY),
                    hero,
                    171, 189);

                isGenerated = true;
            } catch (Exception e) {
                System.out.println("Ожидаемая ошибка при генерации портала - пересоздание");
                System.out.println("Ошибка: ");
                e.printStackTrace();
                System.out.println("---------------------");
                isGenerated = false;
            }
        }

        backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("Sounds/nightMusic.mp3"));
        backgroundMusic.setLooping(true);
        backgroundMusic.setVolume(0.3f);
        backgroundMusic.play();

        wait.addAll(spawnItem.startDropSet());


        trees = new ArrayList<>();

        Texture tree1 = new Texture(Gdx.files.internal("UI/GameUI/Mobs/Tree/treeT1.png"));
        Texture tree2 = new Texture(Gdx.files.internal("UI/GameUI/Mobs/Tree/treeT2.png"));
        Texture tree3 = new Texture(Gdx.files.internal("UI/GameUI/Mobs/Tree/treeT3.png"));
        Texture tree4 = new Texture(Gdx.files.internal("UI/GameUI/Mobs/Tree/treeT4.png"));

        Texture[] treesTexture = new Texture[] {tree1, tree2, tree3, tree4};

        Vector2 center = new Vector2(WORLD_WIDTH / 2f, WORLD_HEIGHT / 2f);
        float minDistance = 400f;

        for (int i = 0; i < countTree; i++) {
            Texture treeTexture = treesTexture[new Random().nextInt(treesTexture.length)];

            Vector2 spawnPos;
            float distance;

            // Генерируем позицию, пока она не будет вне зоны 400px от центра
            do {
                spawnPos = new Vector2(
                    (float) (Math.random() * WORLD_WIDTH),
                    (float) (Math.random() * WORLD_HEIGHT)
                );
                distance = spawnPos.dst(center);
            } while (distance < minDistance);

            Tree tree = new Tree(
                treeTexture,
                spawnPos,
                treeTexture.getHeight() * 10,
                treeTexture.getWidth() * 10,
                false
            );

            trees.add(tree);
            worldStage.addActor(tree);
        }


        worldStage.addActor(portal);

        spawner.startWork();
        spawnItem.goWork();

        initiaizationConsole();
    }




    public void initiaizationConsole() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException ignore) {}

                Console.showPlayerAndPortalCords(hero, portal);
            }
        });
    }

    @SuppressWarnings("DefaultLocale")
    @Override
    public void render(float delta) {
        addToList();

        if (showAchivs) showNewAchivs();

        TIME += delta;

        coinForTime += delta/20;

        if (totalTime >= 600 && !gameRepository.getAchievements().get(3)) {
           showAchivs = true;
//            imageAchivs = new Image(new Texture("UI/GameUI/Achievements/open/time_open.png"));
            idAchivs = 3;
        }

        if (hero.newLevelFlag) {
            showPowerDialog(delta);
            return;
        }

        coinForGame = coinForEnemyValue + coinForTime;
        touchpadMove.TouchpadLogic(uiStage);
        touchpadAttack.TouchpadLogic(uiStage);
        // Получаем значения от джойстиков
        float moveX = touchpadMove.getKnobPercentX();
        float moveY = touchpadMove.getKnobPercentY();

        float normalizedX = (touchpadAttack.getKnobPercentX() + 1) / 2;
        float normalizedY = (touchpadAttack.getKnobPercentY() + 1) / 2;
        float dx = normalizedX * 2 - 1;
        float dy = normalizedY * 2 - 1;

        // Движение героя
        hero.move(moveX, moveY, delta);

        // Атака
        if (touchpadAttack.isTouchpadActive()) {
            hero.useWeapon(dx, dy);
        } else if (hero.getWep().getShow() && hero.getWep().isCanAttack()) {
            hero.startAttackAnim();
            for (Enemy e : enemies) {
                if (hero.getWep().checkTouchRectangle(e.getBound())) {
                    e.setHealth(e.getHealth() - hero.getWep().getDamage());
                    e.stan(1);

                    if (Math.random() * 100 > 80 && hero.getHealth() < 3 && hero.getVampirism()) {
                        hero.setHealth(hero.getHealth() + 1);
                    }

                    if (e.getHealth() <= 0) {
                        e.die();
                        Experience exp = new Experience(ItemType.exp, new Vector2(e.getCenterVector()), hero, e.getExp());
                        worldStage.addActor(exp);

                        isKill = true;
                    }

                    if (hero.getEnergyCollect()) {
                        hero.addAntiRadiationCostumePower(0.5f);
                    }
                }
            }
            hero.unUseWeapon();
        } else if (hero.getWep().getShow()) {
            hero.unUseWeapon();
        }

        // Урон от врагов
        for (Enemy e : enemies) {
            if (e.getBound().overlaps(hero.getBound())) {
                if (hero.getLevelSheild() == 0) {
                    e.attack(hero);
                } else if (!hero.getIsInvulnerability()) {
                    hero.setSheildLevel(hero.getLevelSheild() - 1);
                    hero.frameInvulnerability(2);
                }

                if (hero.returnDamage) {
                    e.die();

                    for (Enemy ee : enemies) {
                        if (ee.getBound().overlaps(hero.deathZone)) ee.die();
                    }
                }
                if (hero.getActiveSheild()) hero.offShield();
            }
        }

        // Удаление мертвых врагов
        enemies.removeIf(e -> {
            if (!e.isLive()) {
                coinForEnemyValue+=0.1f;
                e.remove();
                return true;
            }
            return false;
        });

        expBar.setValue(hero.getExp());
        hero.newLevel();
        energyValue.setText(String.format("%.1f", hero.getAntiRadiationCostumePower()));
        radiationValue.setText(hero.getRadiationLevel());
        hearts.updateAnimation(delta);
        hearts.updateHealth(hero.getHealth());

        // Обновление камеры
        gameCamera.updateCameraPosition(hero.getX(), hero.getY(), hero.getWidth(), hero.getHeight());

        // Рендер в буфер
        if (!STOP) {
            frameBuffer.begin();
            Gdx.gl.glClearColor(0, 0, 0, 1);
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

            batch.setProjectionMatrix(gameCamera.getCamera().combined);
            batch.begin();

            // Фон отрисовывается первым
            backgroundTiledRenderer.render(batch, gameCamera.getCamera());

            // Сцена с героями, врагами и т.п.
            worldStage.act(delta);
            worldStage.draw();

            batch.end();
            frameBuffer.end();
        }

        // Вычисление координат героя в экране
        Vector2 heroPosScreen = worldStage.stageToScreenCoordinates(
            new Vector2(hero.getX() + hero.getWidth() / 2f, hero.getY() + hero.getHeight() / 2f)
        );
        float heroXNorm = heroPosScreen.x / Gdx.graphics.getWidth();
        float heroYNorm = 1f - (heroPosScreen.y / Gdx.graphics.getHeight());

        Texture worldTexture = frameBuffer.getColorBufferTexture();
        worldTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        // Шейдер маски
        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);

        batch.setProjectionMatrix(uiStage.getCamera().combined);

        batch.setShader(ShaderManager.maskShader);
        ShaderManager.maskShader.bind();
        ShaderManager.maskShader.setUniformf("u_heroPos", heroXNorm, heroYNorm);
        ShaderManager.maskShader.setUniformf("u_time", TIME);
        ShaderManager.maskShader.setUniformf("u_baseRadius", ShaderManager.radiusView1);

        batch.begin();
        batch.draw(worldTexture, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight(),
            0, 0, worldTexture.getWidth(), worldTexture.getHeight(), false, true);
        batch.end();

        // Шейдер затемнения
        batch.setShader(ShaderManager.dimmingShader);
        ShaderManager.dimmingShader.bind();
        ShaderManager.dimmingShader.setUniformf("u_heroPos", heroXNorm, heroYNorm);
        ShaderManager.dimmingShader.setUniformf("u_innerRadius", ShaderManager.radiusView3);


        batch.begin();
        batch.draw(worldTexture, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight(),
            0, 0, worldTexture.getWidth(), worldTexture.getHeight(), false, true);
        batch.end();

        batch.setShader(null);
        Gdx.gl.glDisable(GL20.GL_BLEND);

        // Джойстик
        // UI поверх всего
        uiStage.act(delta);
        uiStage.draw();
    }

    @Override
    public void resize(int width, int height) {
        uiStage.getViewport().update(width, height, true);
    }

    @Override
    public void dispose() {
        isTimeGo = false;

        worldStage.dispose();
        uiStage.dispose();
        touchpadMove.dispose();
        touchpadAttack.dispose();
        hero.dispose();

        backgroundMusic.stop();
        backgroundMusic.dispose();

        spawnItem.dispose();
        spawner.dispose();
    }

    public static void endTask() {
        spawnItem.dispose();
        spawner.dispose();

        backgroundMusic.stop();
        backgroundMusic.dispose();

        isTimeGo = false;
    }

    @Override public void show() {

    }
    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}

    @SuppressWarnings("NewApi")
    public void showPowerDialog(float delta) {
        STOP = true;

        ArrayList<Power> powers = this.powers;
        ArrayList<Power> added = new ArrayList<>();

        int countPower = Math.min(powers.size(), 3);

        if (countPower == 0) hidePowerDialog();

        Power[] imgB = new Power[countPower];

        for (int i = 0; i < countPower; i++) {
            boolean ok = true;

            while (ok) {
                int index = (int) (Math.random() * powers.size());
                if (index == powers.size()) index--;
                if (index < 0) index = 0;

                boolean canAdd = true;
                for (Power p: added) {
                    if (p == powers.get(index)) {
                        canAdd = false;
                    }
                }

                if (canAdd) {
                    imgB[i] = powers.get(index);
                    added.add(powers.get(index));
                    ok = false;
                }
            }
        }

        added.clear();

        if (!isShow) {
            isShow = true;
            PSC = new PowerSelectScreen(imgB);
        }

        PSC.render(delta);
    }

    public void hidePowerDialog() {
        STOP = false;
        hero.newLevelFlag = false;
        isShow = false;

        PSC = null;

        Gdx.input.setInputProcessor(uiStage);
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
        for(int i = 0; i < wait.size(); i++) {
            if (wait.get(i) != null) {
                if (wait.get(i) instanceof Enemy && enemies.size() < maxMobSpawn) {
                    enemies.add((Enemy) wait.get(i));
                    worldStage.addActor((Actor) wait.get(i));
                } else if (wait.get(i) instanceof Drops && drop.size() < maxDropSpawn) {
                    drop.add((Drops) wait.get(i));
                    worldStage.addActor((Actor) wait.get(i));
                }
            }
        }
        wait.clear();
    }

    public void showNewAchivs() {
        if (!start) {
//            imageAchivs.setSize(imageAchivs.getWidth() * 5, imageAchivs.getHeight() * 5);
//            imageAchivs.setPosition(Gdx.graphics.getWidth() / 2 - imageAchivs.getWidth() / 2, imageAchivs.getHeight() + 20);
//            uiStage.addActor(imageAchivs);

            start = true;
        }

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(timeShowNewAch * 1000);
                } catch (InterruptedException ignored) {}

                showAchivs = false;
                start = false;

//                imageAchivs.remove();

                gameRepository.unlockAchievement(idAchivs);
                idAchivs = -1;
            }
        }).start();
    }
}
