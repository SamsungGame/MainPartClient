package end.team.center.Screens.Game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import end.team.center.GameCore.Objects.Hero;

public class GameScreen implements Screen {

    private Stage stage;
    private TouchpadClass touchpadMove, touchpadAttack;
    private Hero hero;  // Добавляем героя


    public GameScreen() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        touchpadMove = new TouchpadClass(200, 200, false, "move");
        stage.addActor(touchpadMove);
        touchpadAttack = new TouchpadClass(Gdx.graphics.getWidth()-500, 200, false, "attack");
        stage.addActor(touchpadAttack);

        // Создаём героя с нужными параметрами и добавляем на сцену
        hero = new Hero(
            Gdx.graphics.getWidth() / 2f - 70,
            Gdx.graphics.getHeight() / 2f - 80,
            140, 160,
            100,
            0,
            5,
            500,
            "UI/GameUI/Hero/heroRight.png",
            "UI/GameUI/Hero/heroLeft.png"
        );
        stage.addActor(hero);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act(delta);

        touchpadMove.TouchpadLogic();
        touchpadMove.touchpadSetBounds();

        touchpadAttack.TouchpadLogic();
        touchpadAttack.touchpadSetBounds();

        float moveX = touchpadMove.getKnobPercentX();
        float moveY = touchpadMove.getKnobPercentY();


        hero.move(moveX, moveY, delta);
        hero.setPosition(hero.heroX, hero.heroY);

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
