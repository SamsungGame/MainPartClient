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
    private float speed = 500;
    private float touchSize = 300;
    private float x = 150, y = 150;
    private boolean isTouchpadActive = false;

    public GameScreen() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        shapeRenderer = new ShapeRenderer();

        rectX = Gdx.graphics.getWidth() / 2f - rectWidth / 2;
        rectY = Gdx.graphics.getHeight() / 2f - rectHeight / 2;
        touchpad = new TouchpadClass(x, y, touchSize);
        stage.addActor(touchpad);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act(delta);

        if (Gdx.input.isTouched()) {
            float touchX = Gdx.input.getX();
            float touchY = Gdx.graphics.getHeight() - Gdx.input.getY();

            if (!isTouchpadActive) {
                if (touchX >= x - touchSize / 2 && touchX <= x + touchSize / 2 &&
                        touchY >= y - touchSize / 2 && touchY <= y + touchSize / 2) {
                    isTouchpadActive = true;
                } else {
                    x = touchX;
                    y = touchY;
                    touchpad.setBounds(x - touchSize / 2, y - touchSize / 2, touchSize, touchSize);
                }
            }
        } else {
            isTouchpadActive = false;
        }

        float moveX = touchpad.getKnobPercentX();
        float moveY = touchpad.getKnobPercentY();

        rectX += moveX * speed * delta;
        rectY += moveY * speed * delta;

        rectX = Math.max(0, Math.min(rectX, Gdx.graphics.getWidth() - rectWidth));
        rectY = Math.max(0, Math.min(rectY, Gdx.graphics.getHeight() - rectHeight));

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
