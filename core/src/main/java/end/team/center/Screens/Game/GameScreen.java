package end.team.center.Screens.Game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.badlogic.gdx.scenes.scene2d.Stage;

import end.team.center.Entity.Hero;
import end.team.center.logic.GameCamera;

public class GameScreen implements Screen {

    private static final float WORLD_WIDTH = 5000;
    private static final float WORLD_HEIGHT = 5000;

    private Stage worldStage;
    private Stage uiStage;
    private Viewport worldViewport;
    private Viewport uiViewport;

    private GameCamera gameCamera;

    private TouchpadClass touchpadMove, touchpadAttack;
    private Hero hero;
    private ShapeRenderer shapeRenderer;

    public GameScreen() {
        shapeRenderer = new ShapeRenderer();

        // Создаем наш GameCamera
        gameCamera = new GameCamera(WORLD_WIDTH, WORLD_HEIGHT);

        // Используем камеру из GameCamera при создании Viewport
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
                WORLD_WIDTH / 2f - 70,
                WORLD_HEIGHT / 2f - 80,
                140, 160,
                100,
                500,
                "UI/GameUI/Hero/heroRight.png",
                "UI/GameUI/Hero/heroLeft.png",
                WORLD_WIDTH, WORLD_HEIGHT
        );
        worldStage.addActor(hero);

        // Устанавливаем изначальную позицию камеры
        gameCamera.updateCameraPosition(hero.getX(), hero.getY(), hero.getWidth(), hero.getHeight());
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        float moveX = touchpadMove.getKnobPercentX();
        float moveY = touchpadMove.getKnobPercentY();

        hero.move(moveX, moveY, delta);

        gameCamera.updateCameraPosition(hero.getX(), hero.getY(), hero.getWidth(), hero.getHeight());

        worldStage.act(delta);
        worldStage.draw();

        touchpadMove.TouchpadLogic(uiStage);
        touchpadAttack.TouchpadLogic(uiStage);

        touchpadMove.touchpadSetBounds();
        touchpadAttack.touchpadSetBounds();

        uiStage.act(delta);
        uiStage.draw();
    }

    @Override
    public void resize(int width, int height) {
        // Сообщаем об изменении размеров камере и вьюпортам
        gameCamera.resize(width, height);
        worldViewport.update(width, height);
        uiViewport.update(width, height);
    }

    @Override
    public void dispose() {
        worldStage.dispose();
        uiStage.dispose();
        touchpadMove.dispose();
        touchpadAttack.dispose();
        hero.dispose();
        shapeRenderer.dispose();
    }

    @Override public void show() {}
    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}
}
