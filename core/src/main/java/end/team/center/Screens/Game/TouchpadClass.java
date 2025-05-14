
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
    private float speed = 1000;
    private float touchX = 50;
    private float touchY = 50;
    private boolean touch = false;

    public TouchpadClass() {
        // Создаем сцену с вьюпортом, который автоматически адаптируется к экрану
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        // Создаем ShapeRenderer для отрисовки прямоугольника
        shapeRenderer = new ShapeRenderer();

        // Создаем скин для джойстика
        skin = new Skin();
        Texture bgTexture = new Texture("UI/GameUI/Direction/backTouch.png");
        Texture knobTexture = new Texture("UI/GameUI/Direction/knobIMG.png");
        skin.add("touchBackground", bgTexture);
        skin.add("touchKnob", knobTexture);

        // Создаем стиль джойстика
        touchpadStyle = new Touchpad.TouchpadStyle();
        touchpadStyle.background = skin.getDrawable("touchBackground");
        touchpadStyle.knob = skin.getDrawable("touchKnob");

        touchpadStyle.knob.setMinHeight(100);
        touchpadStyle.knob.setMinWidth(100);


        // Создаем джойстик с мертвой зоной 10 пикселей
        touchpad = new Touchpad(20, touchpadStyle);
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

        if (Gdx.input.isTouched()) {
            touchX = Gdx.input.getX()-150;
            touchY = Gdx.graphics.getHeight()-Gdx.input.getY()-150;
            touch = false;
        }
        if(touch)
            touchpad.setBounds(touchX, touchY, 300, 300);


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
