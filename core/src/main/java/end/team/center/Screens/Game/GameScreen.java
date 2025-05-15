package end.team.center.Screens.Game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class GameScreen implements Screen {

    private Stage stage;
    private TouchpadClass touchpad;
    private ShapeRenderer shapeRenderer;

    private float rectX, rectY;
    private float rectWidth = 100;
    private float rectHeight = 100;
    private float speed = 1000;

    public GameScreen() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        touchpad = new TouchpadClass(500, 500, 300);
        stage.addActor(touchpad);

        shapeRenderer = new ShapeRenderer();

        rectX = Gdx.graphics.getWidth() / 2f - rectWidth / 2;
        rectY = Gdx.graphics.getHeight() / 2f - rectHeight / 2;
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act(delta);

        float moveX = touchpad.getKnobPercentX();
        float moveY = touchpad.getKnobPercentY();

        rectX += moveX * speed * delta;
        rectY += moveY * speed * delta;

        rectX = Math.max(0, Math.min(rectX, Gdx.graphics.getWidth() - rectWidth));
        rectY = Math.max(0, Math.min(rectY, Gdx.graphics.getHeight() - rectHeight));

        // Отрисовка прямоугольника
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.RED);
        shapeRenderer.rect(rectX, rectY, rectWidth, rectHeight);
        shapeRenderer.end();

        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void dispose() {
        stage.dispose();
        shapeRenderer.dispose();
        touchpad.dispose();
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
