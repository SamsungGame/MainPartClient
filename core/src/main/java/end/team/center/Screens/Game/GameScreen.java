package end.team.center.Screens.Game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import end.team.center.GameCore.Objects.Hero;
import end.team.center.Screens.Game.TouchpadClass;

public class GameScreen implements Screen {

    private Stage stage;
    private OrthographicCamera camera;
    private Viewport viewport;
    private TouchpadClass touchpadMove, touchpadAttack;
    private Hero hero;


    public GameScreen() {

        camera = new OrthographicCamera();
        viewport = new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), camera);

        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);



        touchpadMove = new TouchpadClass(200, 200, false, "move");
        stage.addActor(touchpadMove);
        touchpadAttack = new TouchpadClass(Gdx.graphics.getWidth()-500, 200, false, "attack");
        stage.addActor(touchpadAttack);

        // Создаём героя с нужными параметрами и добавляем на сцену
        hero = new Hero(
            new Texture("UI/GameUI/Hero/heroRight.png"),
            new Texture("UI/GameUI/Hero/heroLeft.png"),
            new Vector2(Gdx.graphics.getWidth() / 2f - 70, Gdx.graphics.getHeight() / 2f - 80),
            140, 160,
            100,
            5,
            0,
            500
        );
        stage.addActor(hero);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(256, 256, 256, 3);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act(delta);

        touchpadMove.TouchpadLogic();
        touchpadMove.touchpadSetBounds();

        touchpadAttack.TouchpadLogic();
        touchpadAttack.touchpadSetBounds();

        float moveX = touchpadMove.getKnobPercentX();
        float moveY = touchpadMove.getKnobPercentY();


        hero.move(moveX, moveY, delta);

        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void dispose() {
        stage.dispose();
        touchpadMove.dispose();
        hero.dispose();
    }

    @Override
    public void show() {}
    @Override
    public void pause() {}
    @Override
    public void resume() {}
    @Override
    public void hide() {}
}
