package end.team.center.Screens.Game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.ArrayList;
import java.util.List;

import end.team.center.GameCore.Animations.CharacterAnimation;
import end.team.center.GameCore.GameEvent.PostMob;
import end.team.center.GameCore.GameEvent.Spawner;
import end.team.center.GameCore.Objects.Enemy;
import end.team.center.GameCore.Objects.Hero;
import end.team.center.GameCore.UIElements.TouchpadClass;
import end.team.center.GameCore.Logic.GameCamera;

public class GameScreen implements Screen {

    private TouchpadClass touchpadMove, touchpadAttack;
    private Hero hero;
    private Stage worldStage;
    private Stage uiStage;
    private Viewport worldViewport;
    private Viewport uiViewport;

    private GameCamera gameCamera;
    public static final float WORLD_WIDTH = 5000;
    public static final float WORLD_HEIGHT = 5000;

    private Spawner spawner;
    private int locationCount = 0;
    private ArrayList<Enemy> enemies;

    private Label costumePower;
    private Label playerHealth;



    public GameScreen() {
        System.out.println("Размеры экрана: " + Gdx.graphics.getWidth() + "x на " + Gdx.graphics.getHeight() + "y");

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
            new Texture(Gdx.files.internal("UI/GameUI/Hero/Right/heroRight.png")),
            CharacterAnimation.Hero,
            new Vector2(WORLD_WIDTH / 2f - 70, WORLD_HEIGHT / 2f - 80),
            140, 120 ,3,
            1, 0, 300f,
            WORLD_WIDTH, WORLD_HEIGHT
        );

        worldStage.addActor(hero);

        // Настройки UI на карте
        Image imageHealth = new Image(new TextureRegion(new Texture(Gdx.files.internal("UI/GameUI/Hero/HealthLabel/heart.png"))));
        imageHealth.setSize(180, 180);
        imageHealth.setPosition(20, Gdx.graphics.getHeight() - imageHealth.getHeight() - 20);

        Image imagePower = new Image(new TextureRegion(new Texture(Gdx.files.internal("UI/GameUI/Hero/PowerLabel/power.png"))));
        imagePower.setSize(360, 120);
        imagePower.setPosition(Gdx.graphics.getWidth() - imagePower.getWidth() - 20,
            Gdx.graphics.getHeight() - imageHealth.getHeight() + 30);

        Skin h = new Skin(Gdx.files.internal("UI/GameUI/Hero/HealthLabel/label.json"));
        Skin l = new Skin(Gdx.files.internal("UI/GameUI/Hero/PowerLabel/label.json"));

        playerHealth = new Label(String.valueOf(hero.getHealth()), h);
        costumePower = new Label(String.valueOf(hero.getAntiRadiationCostumePower()), l);

        playerHealth.setPosition(playerHealth.getWidth() + 20 + 105,
            Gdx.graphics.getHeight() - (imageHealth.getHeight() / 2) - 40);
        costumePower.setPosition(Gdx.graphics.getWidth() - costumePower.getWidth() - 20 - 155,
            Gdx.graphics.getHeight() - (imagePower.getHeight() / 2) - 40);

        costumePower.setFontScale(3.5f);
        playerHealth.setFontScale(2.5f);

        uiStage.addActor(imagePower);
        uiStage.addActor(costumePower);
        uiStage.addActor(imageHealth);
        uiStage.addActor(playerHealth);

        // Настройки спавна мобов
        enemies = new ArrayList<>();

        spawner = new Spawner(new PostMob() {
            @Override
            public void post(Enemy[] enemy) {
                setSpawnMob(enemy);
            }
        }, hero, locationCount);
    }

    @SuppressWarnings("DefaultLocale")
    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        playerHealth.setText(String.valueOf(hero.getHealth()));
        costumePower.setText(String.format("%.1f", hero.getAntiRadiationCostumePower()));

        float moveX = touchpadMove.getKnobPercentX();
        float moveY = touchpadMove.getKnobPercentY();

        hero.move(moveX, moveY, delta, false);

        gameCamera.updateCameraPosition(hero.getVector().x, hero.getVector().y, hero.getWidth(), hero.getHeight());

        worldStage.act(delta);
        worldStage.draw();

        for(Enemy e: enemies) {
            if (e.getBound().overlaps(hero.getBound())) e.attack(hero);
        }

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

    @Override public void show() {
        spawner.startWork();
    }
    @Override public void pause() {}
    @Override public void resume() {}
    @Override public void hide() {}

    public void setSpawnMob(Enemy[] enemy) {
        enemies.addAll(List.of(enemy));

        for(Enemy e: enemy) {
            worldStage.addActor(e);
        }
    }
}
