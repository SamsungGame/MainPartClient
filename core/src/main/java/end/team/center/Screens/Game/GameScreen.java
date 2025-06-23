package end.team.center.Screens.Game;

import static end.team.center.GameCore.Objects.OnMap.Hero.HeroClassType.*;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.ArrayList;

import end.team.center.Center;
import end.team.center.GameCore.GameEvent.Post;
import end.team.center.GameCore.GameEvent.SpawnMob;
import end.team.center.GameCore.Library.ItemType;
import end.team.center.GameCore.Library.Items.Experience;
import end.team.center.GameCore.Library.Other.Portal;
import end.team.center.GameCore.Logic.GameLauncher;
import end.team.center.GameCore.Logic.MapLauncher;
import end.team.center.GameCore.Objects.InInventary.Drops;
import end.team.center.GameCore.Objects.Map.BackgroundTiledRenderer;
import end.team.center.GameCore.Objects.Map.NebulaCloud;
import end.team.center.GameCore.Objects.Map.Tree;
import end.team.center.GameCore.Objects.Map.Zone;
import end.team.center.GameCore.Objects.OnMap.Enemy;
import end.team.center.GameCore.Objects.OnMap.Hero;
import end.team.center.GameCore.UIElements.Power;
import end.team.center.GameCore.UIElements.UIGameScreenElements.Heart;
import end.team.center.GameCore.UIElements.UIGameScreenElements.TouchpadClass;
import end.team.center.GameCore.Logic.GameCamera;
import end.team.center.GameCore.Logic.ShaderManager;
import end.team.center.ProgramSetting.LocalDB.GameRepository;
import end.team.center.Screens.Menu.MainMenuScreen;

public class GameScreen implements Screen {
    // <><><><><><><><><> Высшие классы <><><><><><><><><>
    public static GameRepository gameRepository;
    public static Stage worldStage, noAct, uiStage, pauseStage, deathStage, infoStage;
    protected static PowerSelectScreen PSC;
    public static Viewport worldViewport, uiViewport;
    public static GameCamera gameCamera;

    // <><><><><><><><><> Классы обьектов <><><><><><><><><>
    public static Hero hero;
    private BackgroundTiledRenderer backgroundTiledRenderer;
    public static Portal portal;

    // <><><><><><><><><> Классы UI <><><><><><><><><>
    public static TouchpadClass touchpadMove, touchpadAttack;
    public static Heart hearts;
    public static ProgressBar expBar;
    public static Hero.HeroClassType heroClassType;

    // <><><><><><><><><> Низшие классы UI <><><><><><><><><>
    public static Image imageAchivs;
    public static Label energyValue, radiationValue;
    public static ImageButton pauseButton;
    public static ImageButton abilityButton;

    // <><><><><><><><><> Шейдер-классы <><><><><><><><><>
    private FrameBuffer frameBuffer;
    private FrameBuffer hardMaskBuffer;
    private SpriteBatch batch;
    private ShaderProgram maskShader;
    private ShaderProgram hardMaskShader;
    private ShaderProgram dimmingShader;
    private NebulaCloud cloud;

    //  <><><><><><><><><> Музыкальные интерфейсы  <><><><><><><><><>
    public static Music backgroundMusic, backgroundMusicInstrumental;

    // <><><><><><><><><> Низшие классы абстрактых обьектов <><><><><><><><><>
    private static SpawnMob spawner;

    // <><><><><><><><><> Низшие классы массивов <><><><><><><><><>
    private ArrayList<Object> wait;
    public static ArrayList<Power> powers;
    public static ArrayList<Zone> zone;
    public static ArrayList<Enemy> enemies;
    public static ArrayList<Drops> drop;
    public static ArrayList<Tree> trees;

    // <><><><><><><><><> Низшие типы данных <><><><><><><><><>
    public static final float WORLD_WIDTH = 30000, WORLD_HEIGHT = 30000;
    public static float coinForEnemyValue = 0, coinForTime = 0, coinForGame = 0, totalTime = 0f, TIME = 0f, elapsedTime;
    public float timeForAch = 0f;

    public static int idAchivs, endCode = 0, maxMobSpawn = 120, maxDropSpawn = 250;
    public int timeShowNewAch = 4;
    public static float infoTime = 2;

    public static boolean endForHero = false, isPause = false, isDeath = false, STOP = false, isShow = false, isTimeGo = true, showAchivs = false, isPickupItem = false, isKill = false, isFirstRender = true;

    // <><><><><><><><><> Фигня которую Сергей не может рассортировать <><><><><><><><><>
    public static Skin label = new Skin(Gdx.files.internal("UI/AboutGame/label.json"));
    public boolean start = false;
    static Label textItem;
    TextButton revivalButton;



    public GameScreen(GameRepository repo) {

        gameRepository = repo;

        // <><><><><><><><><><> Создание сцен, камер, экранов <><><><><><><><><><>
        gameCamera    = new GameCamera(WORLD_WIDTH, WORLD_HEIGHT);
        worldViewport = new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), gameCamera.getCamera());
        uiViewport    = new ScreenViewport();

        noAct      = new Stage(worldViewport);
        pauseStage = new Stage(uiViewport);
        deathStage = new Stage(uiViewport);
        worldStage = new Stage(worldViewport);
        uiStage    = new Stage(uiViewport);
        infoStage = new Stage(uiViewport);

        zone    = new ArrayList<>();
        wait    = new ArrayList<>();
        drop    = new ArrayList<>();
        enemies = new ArrayList<>();

        batch  = new SpriteBatch();
        powers = new ArrayList<>();

        Gdx.input.setInputProcessor(uiStage);

        // <><><><><><><><><><> Создание игрока <><><><><><><><><><>
        GameLauncher gLauncher = new GameLauncher();
        hero = gLauncher.loadHero(gameRepository);
        worldStage.addActor(hero);

        // <><><><><><><><><><> Создание интерфейса <><><><><><><><><><>
        ArrayList<Actor> ac = gLauncher.generationUI();

        textItem = new Label("", label);
        textItem.setPosition((float)  10,
            (float) Gdx.graphics.getHeight() - 400);

        for (Actor a: ac) {
            uiStage.addActor(a);
        }
        uiStage.addActor(touchpadMove);
        uiStage.addActor(radiationValue);
        uiStage.addActor(energyValue);
        uiStage.addActor(touchpadAttack);
        uiStage.addActor(abilityButton);
        uiStage.addActor(pauseButton);
        uiStage.addActor(hearts);
        uiStage.addActor(expBar);
        textItem.setFontScale(2.1f);
        infoStage.addActor(textItem);

        // <><><><><><><><><><> Настройки спавна мобов <><><><><><><><><><>
        spawner = new SpawnMob(new Post() {
            @Override
            public void post(Enemy[] enemy) {
                setSpawnMob(enemy);
            }
            @Override
            public void post(Drops drops) {

            }
        }, hero);

        // <><><><><><><><><><> Настройка шейдеров <><><><><><><><><><>
        frameBuffer = new FrameBuffer(Pixmap.Format.RGBA8888, Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), false);
        hardMaskBuffer = new FrameBuffer(Pixmap.Format.RGBA8888, Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), false);

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

        // <><><><><><><><><><> Добавление существующих усилений <><><><><><><><><><>
        powers.addAll(gLauncher.generatePowers());

        // <><><><><><><><><><> Запуск музыки <><><><><><><><><><>
        gLauncher.loadMusic();

        // <><><><><><><><><><> Генерация структур на карте и предметов <><><><><><><><><><>
        MapLauncher launcer = new MapLauncher();

        backgroundTiledRenderer = launcer.generationBrick();

        trees = launcer.generationTree();
        for (Tree t: trees) {
            noAct.addActor(t);
        }

        portal = launcer.generationPortal(gameRepository); // не трогай
        worldStage.addActor(portal);

        wait.addAll(launcer.generationItems());

        launcer.generateZone();

        // <><><><><><><><><><> Генерация тумана на карте <><><><><><><><><><>
        cloud = new NebulaCloud(450);
        cloud.addToStage(worldStage);

        // <><><><><><><><><><> Запуск спавнера мобов <><><><><><><><><><>
        spawner.startWork();

        // <><><><><><><><><><> Создание меню <><><><><><><><><><>
        Table pauseTable = new Table();
        pauseTable.setFillParent(true);  // Таблица занимает весь экран
        pauseTable.center();              // Выравнивание всей таблицы по центру

        Skin buttonSkin = new Skin(Gdx.files.internal("UI/AboutGame/pauseStyle.json"));

        TextButton backToMainMenuScreenButton = new TextButton("В главное меню", buttonSkin);
        TextButton continueButton = new TextButton("Продолжить игру", buttonSkin);

        backToMainMenuScreenButton.getLabel().setFontScale(3f);
        continueButton.getLabel().setFontScale(3f);

        backToMainMenuScreenButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                GameScreen.endCode = 0;
                GameScreen.endForHero = true;
            }
        });

        continueButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                togglePause(false);
            }
        });

        // Чтобы текст внутри кнопок был по центру:
        backToMainMenuScreenButton.getLabel().setAlignment(Align.center);
        continueButton.getLabel().setAlignment(Align.center);

        pauseTable.defaults().pad(150).expandX().fillX();
        pauseTable.padTop(0);

        pauseTable.add(backToMainMenuScreenButton).row();
        pauseTable.add(continueButton);

        pauseStage.addActor(pauseTable);

        Table DeathTable = new Table();
        DeathTable.setFillParent(true);
        DeathTable.center();

        TextButton backToMainMenuScreenButtonAfterDeath = new TextButton("В главное меню", buttonSkin);
        revivalButton = new TextButton("", buttonSkin);

        backToMainMenuScreenButtonAfterDeath.getLabel().setFontScale(3f);
        revivalButton.getLabel().setFontScale(3.3f);

        backToMainMenuScreenButtonAfterDeath.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                GameScreen.endForHero = true;
            }
        });

        revivalButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(gameRepository.getCoins()>=50)
                    toggleDeath(false);
            }
        });

        // Чтобы текст внутри кнопок был по центру:
        backToMainMenuScreenButtonAfterDeath.getLabel().setAlignment(Align.center);
        revivalButton.getLabel().setAlignment(Align.center);

        DeathTable.defaults().pad(150).expandX().fillX();
        DeathTable.padTop(0);

        DeathTable.add(backToMainMenuScreenButtonAfterDeath).row();
        DeathTable.add(revivalButton);

        deathStage.addActor(DeathTable);
    }

    // <><><><><><><><><><> <><><><><><><><><><> <><><><><><><><><><> <><><><><><><><><><> <><><><><><><><><><>
    @SuppressWarnings("DefaultLocale")
    @Override
    public void render(float delta) {

        // <><><><><><><><><><> ОБЯЗАТЕЛЬНО В САМОМ НАЧАЛЕ <><><><><><><><><><>
        if(endForHero) {
            endGame();
            return;
        }
        if(gameRepository.getCoins()>=50) revivalButton.setText("Возрождние: 50");
        else revivalButton.setText("");

        addToList();

        if (isFirstRender) {
            isFirstRender = false;

            hero.PLAZ.update();

            delta = 0;
        }

        // <><><><><><><><><><> Обновление глобального времени <><><><><><><><><><>
        elapsedTime += delta;
        if (elapsedTime >= 1) {
            totalTime++;
            elapsedTime = 0;
        }

        // <><><><><><><><><><> Показ экрана усилений <><><><><><><><><><>
        if (hero.newLevelFlag && !powers.isEmpty()) {
            showPowerDialog(delta);
            return;
        }

        // <><><><><><><><><><> Прочее <><><><><><><><><><>
        // FIX
        if (totalTime < 2) isPickupItem = false;

        if (showAchivs) showNewAchivs();

        TIME += delta;
        timeForAch += delta;

        coinForTime += delta / 20;

        // <><><><><><><><><><> Выдача достижений <><><><><><><><><><>
        if (timeForAch >= 600 && !gameRepository.getAchievements().get(3) && !start) {
            showAchivs = true;
            imageAchivs = new Image(new Texture("UI/GameUI/Achievements/open/time_open.png"));
            idAchivs = 3;
            gameRepository.unlockAchievement(3);
        }

        // <><><><><><><><><><> Зачисление монет и обновление <><><><><><><><><><>
        if (powers.isEmpty()) expBar.setRange(0, 0);

        coinForGame = coinForEnemyValue + coinForTime;

        // <><><><><><><><><><> Отрисовка UI <><><><><><><><><><>
        if (!isPause && !isDeath) {
            uiStage.act(delta);
            uiStage.draw();

            touchpadMove.TouchpadLogic(uiStage);
            touchpadAttack.TouchpadLogic(uiStage);
        }

        // <><><><><><><><><><> Управление игроком <><><><><><><><><><>
        // Получаем значения от джойстиков
        float deadZone = 0.1f; // минимальное отклонение от центра

        float moveX = touchpadMove.getKnobPercentX();
        float moveY = touchpadMove.getKnobPercentY();

        if (hero.disableMovement) {
            moveX = 0;
            moveY = 0;
        }

        if (!hero.disableMovement) {
            hero.move(moveX, moveY, delta);
        }
        if (hero.uniqueAbility!= null) {
            hero.uniqueAbility.update(delta); // Это обновит shieldPolygon до его актуальной позиции
        }

        // <><><><><><><><><><> Обработка точпадов <><><><><><><><><><>
        if (touchpadAttack.isTouchpadActive()) {
            float normalizedX = (touchpadAttack.getKnobPercentX() + 1) / 2;
            float normalizedY = (touchpadAttack.getKnobPercentY() + 1) / 2;
            float dx = normalizedX * 2 - 1;
            float dy = normalizedY * 2 - 1;

            if (Math.abs(dx) > deadZone || Math.abs(dy) > deadZone) {
                hero.useWeapon(dx, dy);
            }

        } else if (hero.getWep().getShow() && hero.getWep().isCanAttack()) {
            hero.startAttackAnim();
            for (Enemy e : enemies) {
                if (hero.getWep().checkTouchRectangle(e.getBound())) {
                    e.setHealth(e.getHealth() - hero.getWep().getDamage());
                    e.stan(1000);

                    if (Math.random() * 100 > 90 && hero.getHealth() < 3 && hero.getVampirism()) {
                        hero.setHealth(hero.getHealth() + 1);
                    }

                    if (e.getHealth() <= 0) {
                        e.die();
                        Experience exp = new Experience(ItemType.exp, new Vector2(e.getCenterVector()), hero, e.getExp());
                        worldStage.addActor(exp);

                        isKill = true;
                    }

                    if (hero.getEnergyCollect()) {
                        hero.addCostumePower(0.5f);
                    }
                }
            }
            hero.unUseWeapon();
        } else if (hero.getWep().getShow()) {
            hero.unUseWeapon();
        }

        infoTime -= delta;

        if (infoTime <= 0) {
            textItem.setText("");
        }


        // <><><><><><><><><><> Обработка логики <><><><><><><><><><>
        if (!STOP) {
            for (Enemy e : enemies) {
                if (e.getBound().overlaps(hero.getBound()) && !(heroClassType == GHOST_HERO && hero.isAbilityActive)) {

                    if (hero.getLevelSheild() == 0) {
                        e.attack(hero);
                    } else if (!hero.getIsInvulnerability()) {
                        hero.setSheildLevel(hero.getLevelSheild() - 1);
                        hero.frameInvulnerability(2);
                    }

                    if (hero.returnDamage) e.die();

                    if (hero.getActiveSheild()) hero.offShield();
                }
            }

            if (hero.getAntiRadiationCostumePower() < 10) {
                energyValue.setColor(1f, 0f, 0f, 1f);
            } else if (hero.getAntiRadiationCostumePower() < 60) {
                energyValue.setColor(1f, 1f, 0f, 1f);
            } else {
                energyValue.setColor(1f, 1f, 1f, 1f);
            }

            // <><><><><><><><><><> Удаление мертвых врагов <><><><><><><><><><>
            enemies.removeIf(e -> {
                if (!e.isLive()) {
                    coinForEnemyValue += 0.1f;
                    e.remove();
                    return true;
                }
                return false;
            });

            // <><><><><><><><><><> Обновление данных UI <><><><><><><><><><>
            expBar.setRange(0, hero.getMaxExp());
            expBar.setValue(hero.getExp());
            hero.newLevel();
            energyValue.setText(String.format("%.1f", hero.getAntiRadiationCostumePower()));

            int l = hero.getRadiationLevel();
            radiationValue.setText(hero.getRadiationLevel());
            if (l == 1)      radiationValue.setColor(1f, 1f,   1f,   1f);
            else if (l == 2) radiationValue.setColor(1f, 0.8f, 0.8f, 1f);
            else if (l == 3) radiationValue.setColor(1f, 0.5f, 0.5f, 1f);
            else if (l == 4) radiationValue.setColor(1f, 0.3f, 0.3f, 1f);
            else if (l == 5) radiationValue.setColor(1f, 0,    0,    1f);

            hearts.updateAnimation(delta);
            hearts.setCurrentHealth(hero.getHealth());

            // <><><><><><><><><><> Обновление камеры <><><><><><><><><><>
            gameCamera.updateCameraPosition(hero.getX(), hero.getY(), hero.getWidth(), hero.getHeight());

            frameBuffer.begin();
            Gdx.gl.glClearColor(0, 0, 0, 1);
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

            batch.setProjectionMatrix(gameCamera.getCamera().combined);
            batch.begin();

            // Фон отрисовывается первым
            backgroundTiledRenderer.render(batch, gameCamera.getCamera());

            // Сцена с героями, врагами и т.п.
            hero.PLAZ.act(delta);

            worldStage.act(delta);
            worldStage.draw();

            // <><><><><><><><><><> Отрисовка чанков <><><><><><><><><><>

            if (noAct != null) {
                noAct.draw();
            }

            batch.end();
            frameBuffer.end();
        }

        // <><><><><><><><><><> Вычисление экрана <><><><><><><><><><>
        Vector2 heroPosScreen = worldStage.stageToScreenCoordinates(
            new Vector2(hero.getX() + hero.getWidth() / 2f, hero.getY() + hero.getHeight() / 2f)
        );
        float heroXNorm = heroPosScreen.x / Gdx.graphics.getWidth();
        float heroYNorm = 1f - (heroPosScreen.y / Gdx.graphics.getHeight());

        Texture worldTexture = frameBuffer.getColorBufferTexture();
        worldTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        // <><><><><><><><><><> Шейдер маски <><><><><><><><><><>
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

        // <><><><><><><><><><> Шейдер затемнения <><><><><><><><><><>
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

        // <><><><><><><><><><> Проверка завершение экрана усиления <><><><><><><><><><>
        if (PSC != null && PSC.isFinished()) {
            hidePowerDialog();
        }

        // <><><><><><><><><><> Отрисовка экрана паузы <><><><><><><><><><>
        if (isPause) {
            pauseStage.act(delta);
            pauseStage.draw();
        }

        if (isDeath) {
            deathStage.act(delta);
            deathStage.draw();
        }

        // <><><><><><><><><><> Отрисовка UI <><><><><><><><><><>
        infoStage.act(delta);
        infoStage.draw();
        uiStage.act(delta);
        uiStage.draw();
    }

    // <><><><><><><><><><> <><><><><><><><><><> <><><><><><><><><><> <><><><><><><><><><> <><><><><><><><><><>
    @Override
    public void resize(int width, int height) {
        uiStage.getViewport().update(width, height, true);
    }

    // <><><><><><><><><><> <><><><><><><><><><> <><><><><><><><><><> <><><><><><><><><><> <><><><><><><><><><>
    @Override
    public void dispose() {}

    // <><><><><><><><><><> <><><><><><><><><><> <><><><><><><><><><> <><><><><><><><><><> <><><><><><><><><><>
    public static void endForStaticParams() {
        endForHero = false;
        isTimeGo = false;
        STOP = false;
        isPause = false;
        isDeath = false;
        ShaderManager.radiusView1 = 0.2f;
        ShaderManager.radiusView3 = 0.15f;
        coinForEnemyValue = 0;
        coinForTime = 0;
        coinForGame = 0;
        isKill = false;
        totalTime = 0f;
        TIME = 0f;
    }

    @Override
    public void show() {

    }
    @Override public void pause() {}
    @Override public void resume() {}

    @Override
    public void hide() {

        timeForAch = 0;

        if (worldStage != null) {
            worldStage.dispose();
            worldStage = null;
        }
        if (uiStage != null) {
            uiStage.dispose();
            uiStage = null;
        }
        if (pauseStage != null) {
            pauseStage.dispose();
            pauseStage = null;
        }
        if (deathStage != null) {
            deathStage.dispose();
            deathStage = null;
        }
        if (noAct != null) {
            noAct.dispose();
            noAct = null;
        }

        if (touchpadMove != null) {
            touchpadMove.dispose();
            touchpadMove = null;
        }
        if (touchpadAttack != null) {
            touchpadAttack.dispose();
            touchpadAttack = null;
        }


        if (backgroundMusic != null) {
            backgroundMusic.stop();
            backgroundMusic.dispose();
            backgroundMusic = null;
        }
        if (backgroundMusicInstrumental != null) {
            backgroundMusicInstrumental.stop();
            backgroundMusicInstrumental.dispose();
            backgroundMusicInstrumental = null;
        }

        if (PSC != null) {
            PSC.dispose();
            PSC = null;
        }

        if (cloud != null) {
            cloud.dispose();
            cloud = null;
        }

        if (frameBuffer != null) {
            frameBuffer.dispose();
            frameBuffer = null;
        }
        if (hardMaskBuffer != null) {
            hardMaskBuffer.dispose();
            hardMaskBuffer = null;
        }


        for (Texture treeTex : new Texture[]{new Texture(Gdx.files.internal("UI/GameUI/Mobs/Tree/treeT1.png")), new Texture(Gdx.files.internal("UI/GameUI/Mobs/Tree/treeT2.png")), new Texture(Gdx.files.internal("UI/GameUI/Mobs/Tree/treeT3.png")), new Texture(Gdx.files.internal("UI/GameUI/Mobs/Tree/treeT4.png"))}) {
            treeTex.dispose();
        }

        if (enemies != null) enemies.clear();
        if (drop != null) drop.clear();
        if (zone != null) zone.clear();
        if (trees != null) trees.clear();
        if (powers != null) powers.clear();
        if (wait != null) wait.clear();
    }

    public void showPowerDialog(float delta) {
        uiStage.getRoot().removeActor(touchpadMove);
        uiStage.getRoot().removeActor(touchpadAttack);
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
    public static void togglePause(boolean pause) {

        if (pause) {
            STOP = true;
            isPause = true;
            Gdx.input.setInputProcessor(pauseStage);
            uiStage.getRoot().removeActor(touchpadMove);
            uiStage.getRoot().removeActor(touchpadAttack);
            uiStage.getRoot().removeActor(abilityButton);
        }
        else {
            uiStage.addActor(touchpadMove);
            uiStage.addActor(touchpadAttack);
            uiStage.addActor(abilityButton);
            STOP = false;
            isPause = false;
            Gdx.input.setInputProcessor(uiStage);
        }
    }
    public static void toggleDeath(boolean pause) {

        if (pause) {
            STOP = true;
            isDeath = true;
            Gdx.input.setInputProcessor(deathStage);
            uiStage.getRoot().removeActor(touchpadMove);
            uiStage.getRoot().removeActor(touchpadAttack);
            uiStage.getRoot().removeActor(abilityButton);
        }
        else {
            gameRepository.spendCoins(50);
            uiStage.addActor(touchpadMove);
            uiStage.addActor(touchpadAttack);
            uiStage.addActor(abilityButton);
            hero.setHealth(hero.getMaxHealth());
            hero.addCostumePower(100f);
            hero.setSheildLevel(1);
            STOP = false;
            isDeath = false;
            Gdx.input.setInputProcessor(uiStage);
        }
    }

    public static void hidePowerDialog() {

        uiStage.addActor(touchpadMove);

        uiStage.addActor(touchpadAttack);

        STOP = false;
        hero.newLevelFlag = false;
        isShow = false;

        touchpadMove.TouchpadLogic(uiStage);
        touchpadAttack.TouchpadLogic(uiStage);

        if (PSC != null) {
            PSC.dispose();  // Очистка ресурсов PSC
        }
        PSC = null;

        hero.frameInvulnerability(3);

        Gdx.input.setInputProcessor(uiStage);
    }

    public void setSpawnMob(Enemy[] enemy) {
        for(Enemy e: enemy) {
            if (e != null) wait.add(e);
        }
    }

    public void addToList() {
        for(int i = 0; i < wait.size(); i++) {
            if (wait.get(i) != null) {
                if (wait.get(i) instanceof Enemy && enemies.size() < maxMobSpawn) {
                    enemies.add((Enemy) wait.get(i));
                    worldStage.addActor((Actor) wait.get(i));
                } else if (wait.get(i) instanceof Drops && drop.size() < maxDropSpawn) {
                    drop.add((Drops) wait.get(i));

                    if (noAct != null) noAct.addActor(((Drops) wait.get(i)));
                }
            }
        }
        wait.clear();
    }

    public void showNewAchivs() {
        if (!start) {
            imageAchivs.setSize(imageAchivs.getWidth() * 5, imageAchivs.getHeight() * 5);
            imageAchivs.setPosition(Gdx.graphics.getWidth() / 2 - imageAchivs.getWidth() / 2, imageAchivs.getHeight() + 20);
            uiStage.addActor(imageAchivs);

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

                imageAchivs.remove();

                idAchivs = -1;
            }
        }).start();
    }
    public static void textItemFun(String textItemParam) {
        infoTime = 2;
        textItem.setText(textItemParam);
    }

    public void endGame() {
        if(endForHero) {
            endForStaticParams();
            ((Center) Gdx.app.getApplicationListener()).setScreen(new MainMenuScreen(endCode, gameRepository));
            dispose();
        }
    }
}
