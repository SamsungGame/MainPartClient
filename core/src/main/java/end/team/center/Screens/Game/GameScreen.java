package end.team.center.Screens.Game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import java.util.ArrayList;
import java.util.Arrays;

import end.team.center.GameCore.GameEvent.PostMob;
import end.team.center.GameCore.GameEvent.Spawner;
import end.team.center.GameCore.Objects.Enemy;
import end.team.center.GameCore.Objects.Hero;
import end.team.center.GameCore.UIElements.TouchpadClass;

public class GameScreen implements Screen {

    private Stage stage;
    private TouchpadClass touchpadMove, touchpadAttack;
    public static Hero hero;  // Добавляем героя
    private Spawner spawner;
    private int countLocation = 0;
    private ArrayList<Enemy> mobs;
    private SpriteBatch batch;


    public GameScreen() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        batch = new SpriteBatch();

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
            1,
            0,
            500
        );
        stage.addActor(hero);

        spawner = new Spawner(new PostMob() {
            @Override
            public void post(Enemy[] enemy) {
                mobs.addAll(Arrays.asList(enemy));
            }
        }, countLocation);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act(delta);
        for(Enemy m: mobs) {
            m.act(delta);
        }

        touchpadMove.TouchpadLogic();
        touchpadMove.touchpadSetBounds();

        touchpadAttack.TouchpadLogic();
        touchpadAttack.touchpadSetBounds();

        float moveX = touchpadMove.getKnobPercentX();
        float moveY = touchpadMove.getKnobPercentY();

        batch.begin();
        for(Enemy m: mobs) {
            m.draw(batch, 1);
        }
        batch.end();

        hero.move(moveX, moveY, delta);
        hero.setPosition(hero.getX(), hero.getY());

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
        batch.dispose();
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

    public void setNewMob(Enemy[] enemies) {

    }
}
