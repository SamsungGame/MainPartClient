package end.team.center.Screens.Game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class GameScreen implements Screen {

    private Stage stage;
    private TouchpadClass touchpad;
    private Hero hero;  // Добавляем героя

    private float speed = 500;

    public GameScreen() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        touchpad = new TouchpadClass(200, 200, false);
        stage.addActor(touchpad);

        // Создаём героя с нужными параметрами и добавляем на сцену
        hero = new Hero(
            Gdx.graphics.getWidth() / 2f - 50,
            Gdx.graphics.getHeight() / 2f - 50,
            140, 160,
            100,
            speed,
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

        touchpad.TouchpadLogic();
        touchpad.touchpadSetBounds(touchpad);

        float moveX = touchpad.getKnobPercentX();
        float moveY = touchpad.getKnobPercentY();


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
        touchpad.dispose();
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
