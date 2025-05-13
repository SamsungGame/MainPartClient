package end.team.center.Screens.Game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.Screen;

/**
 * Класс Touchpad реализует Screen и представляет собой экран игры
 * с красным прямоугольником, который управляется джойстиком.
 */
public class TouchpadClass implements Screen {

    private Stage stage;
    private Touchpad touchpad;
    private Touchpad.TouchpadStyle touchpadStyle;
    private Skin skin;
    private ShapeRenderer shapeRenderer;

    private float rectX, rectY;
    private float rectWidth = 100;
    private float rectHeight = 100;
    private float speed = 200;

    public TouchpadClass() {
        // Создаем сцену с вьюпортом, который автоматически адаптируется к экрану
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        // Создаем ShapeRenderer для отрисовки прямоугольника
        shapeRenderer = new ShapeRenderer();

        // Создаем скин для джойстика
        skin = new Skin();
        Texture bgTexture = new Texture("joystick_bg.png");
        Texture knobTexture = new Texture("joystick_knob.png");
        skin.add("touchBackground", bgTexture);
        skin.add("touchKnob", knobTexture);

        // Создаем стиль джойстика
        touchpadStyle = new Touchpad.TouchpadStyle();
        touchpadStyle.background = new TextureRegionDrawable(skin.getDrawable("touchBackground"));
        touchpadStyle.knob = new TextureRegionDrawable(skin.getDrawable("touchKnob"));

        // Создаем джойстик с мертвой зоной 10 пикселей
        touchpad = new Touchpad(10, touchpadStyle);
        touchpad.setBounds(50, 50, 150, 150);
        stage.addActor(touchpad);

        // Начальная позиция прямоугольника
        rectX = Gdx.graphics.getWidth() / 2f - rectWidth / 2;
        rectY = Gdx.graphics.getHeight() / 2f - rectHeight / 2;
    }

    @Override
    public void show() {}

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        float moveX = touchpad.getKnobPercentX();
        float moveY = touchpad.getKnobPercentY();

        rectX += moveX * speed * delta;
        rectY += moveY * speed * delta;

        rectX = Math.max(0, Math.min(rectX, Gdx.graphics.getWidth() - rectWidth));
        rectY = Math.max(0, Math.min(rectY, Gdx.graphics.getHeight() - rectHeight));

        stage.act(delta);
        stage.draw();

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.RED);
        shapeRenderer.rect(rectX, rectY, rectWidth, rectHeight);
        shapeRenderer.end();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {}

    @Override
    public void dispose() {
        stage.dispose();
        shapeRenderer.dispose();
        skin.dispose();
    }
}
