package end.team.center.Screens.Game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import end.team.center.GameCore.Objects.Hero;
import end.team.center.GameCore.UIElements.TouchpadClass;
import end.team.center.logic.GameCamera;

public class GameScreen implements Screen {

    private TouchpadClass touchpadMove, touchpadAttack;
    private Hero hero;
    private Stage worldStage;
    private Stage uiStage;
    private Viewport worldViewport;
    private Viewport uiViewport;

    private GameCamera gameCamera;
    private static final float WORLD_WIDTH = 5000;
    private static final float WORLD_HEIGHT = 5000;



    public GameScreen() {

        gameCamera = new GameCamera(WORLD_WIDTH, WORLD_HEIGHT);

        worldViewport = new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), gameCamera.getCamera());
        worldStage = new Stage(worldViewport);

        uiViewport = new ScreenViewport();
        uiStage = new Stage(uiViewport);
        Gdx.input.setInputProcessor(uiStage);


        touchpadMove = new TouchpadClass(200, 200, false, "move");
        uiStage.addActor(touchpadMove);

        touchpadAttack = new TouchpadClass(Gdx.graphics.getWidth()-500, 200, false, "attack");
        uiStage.addActor(touchpadAttack);

        hero = new Hero(
            new Vector2(WORLD_WIDTH / 2f - 70, WORLD_HEIGHT / 2f - 80),
            140, 160,
                3, 300,
                "UI/GameUI/Hero/heroRight.png",
                WORLD_WIDTH, WORLD_HEIGHT

        );
        worldStage.addActor(hero);

        // Устанавливаем изначальную позицию камеры
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
    }

    @Override
    public void dispose() {
        worldStage.dispose();
        uiStage.dispose();
        touchpadMove.dispose();
        touchpadAttack.dispose();
        hero.dispose();
    }

    @Override public void show() {}
    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}
}
