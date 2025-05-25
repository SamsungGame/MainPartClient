package end.team.center.Screens.Game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.ArrayList;
import java.util.List;

import end.team.center.GameCore.Animations.CharacterAnimation;
import end.team.center.GameCore.GameEvent.PostMob;
import end.team.center.GameCore.GameEvent.Spawner;
import end.team.center.GameCore.Objects.Enemy;
import end.team.center.GameCore.Objects.Hero;
import end.team.center.GameCore.UIGameScreenElements.Heart;
import end.team.center.GameCore.UIGameScreenElements.TouchpadClass;
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
    public static final float WORLD_WIDTH = 5000;
    public static final float WORLD_HEIGHT = 5000;

    private Spawner spawner;
    private int locationCount = 0;
    private ArrayList<Enemy> enemies;

    private Heart hearts;

    private FrameBuffer frameBuffer;
    private FrameBuffer hardMaskBuffer;
    private SpriteBatch batch;

    // Шейдеры теперь берутся из ShaderManager
    private ShaderProgram maskShader;
    private ShaderProgram hardMaskShader;
    private ShaderProgram dimmingShader;

    private float totalTime = 0f;

    public GameScreen() {
        System.out.println("Размеры экрана: " + Gdx.graphics.getWidth() + "x" + Gdx.graphics.getHeight());

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

        hero = new Hero(
            new Texture(Gdx.files.internal("UI/GameUI/Hero/Right/heroRight.png")),
            CharacterAnimation.Hero,
            new Vector2(WORLD_WIDTH / 2f - 70, WORLD_HEIGHT / 2f - 80),
            140, 120, 3,
            1, 0, 300f,
            WORLD_WIDTH, WORLD_HEIGHT
        );
        worldStage.addActor(hero);

        Texture heartFull = new Texture("UI/GameUI/OtherGameItems/heart_full.png");
        Texture heartEmpty = new Texture("UI/GameUI/OtherGameItems/heart_empty.png");
        Texture heartFullBit = new Texture("UI/GameUI/OtherGameItems/heart_full_bit.png");
        hearts = new Heart(heartFull, heartEmpty, heartFullBit, hero.getHealth());
        uiStage.addActor(hearts);

        enemies = new ArrayList<>();

        spawner = new Spawner(new PostMob() {
            @Override
            public void post(Enemy[] enemy) {
                setSpawnMob(enemy);
            }
        }, hero, locationCount);

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
    }

    @Override
    public void render(float delta) {
        totalTime += delta;

        float moveX = touchpadMove.getKnobPercentX();
        float moveY = touchpadMove.getKnobPercentY();
        hero.move(moveX, moveY, delta, false);

        gameCamera.updateCameraPosition(hero.getVector().x, hero.getVector().y, hero.getWidth(), hero.getHeight());

        frameBuffer.begin();
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        worldStage.act(delta);
        worldStage.draw();
        frameBuffer.end();

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
        maskShader.setUniformf("u_time", totalTime);

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

        uiStage.act(delta);
        uiStage.draw();

        for (Enemy e : enemies) {
            if (e.getBound().overlaps(hero.getBound())) {
                e.attack(hero, e.damage);
            }
        }

        hearts.updateAnimation(delta);
        hearts.updateHealth(hero.getHealth());

        touchpadMove.TouchpadLogic(uiStage);
        touchpadAttack.TouchpadLogic(uiStage);

        touchpadMove.touchpadSetBounds();
        touchpadAttack.touchpadSetBounds();
    }

    @Override
    public void resize(int width, int height) {
        worldViewport.update(width, height);
        uiViewport.update(width, height);

        frameBuffer.dispose();
        frameBuffer = new FrameBuffer(Pixmap.Format.RGBA8888, width, height, false);

        hardMaskBuffer.dispose();
        hardMaskBuffer = new FrameBuffer(Pixmap.Format.RGBA8888, width, height, false);
    }

    @Override
    public void dispose() {
        worldStage.dispose();
        uiStage.dispose();
        touchpadMove.dispose();
        touchpadAttack.dispose();
        hero.dispose();
        frameBuffer.dispose();
        hardMaskBuffer.dispose();
        batch.dispose();
        // Шейдеры не удаляются здесь — управляются ShaderManager
    }

    @Override
    public void show() {
        spawner.startWork();
    }

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {}

    public void setSpawnMob(Enemy[] enemy) {
        enemies.addAll(List.of(enemy));
        for (Enemy e : enemy) {
            worldStage.addActor(e);
        }
    }
}
