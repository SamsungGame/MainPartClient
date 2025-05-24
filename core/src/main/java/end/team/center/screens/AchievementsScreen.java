package end.team.center.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import end.team.center.MyGame;

public class AchievementsScreen implements Screen {
    private final Stage stage;
    private final Skin skin;

    private final Texture achievementsTexture = new Texture(Gdx.files.internal("achievements.png"));
    private final Texture achievementTexture1 = new Texture(Gdx.files.internal("achievement1.png"));
    private final Texture achievementTexture2 = new Texture(Gdx.files.internal("achievement2.png"));
    private final Texture achievementTexture3 = new Texture(Gdx.files.internal("achievement3.png"));
    public AchievementsScreen(MyGame game) {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        skin = new Skin(Gdx.files.internal("buttonStyle/buttonStyle.json"));

        TextButton backButton = new TextButton("Назад", skin);
        backButton.setSize(200, 150);
        backButton.setPosition(50,  Gdx.graphics.getHeight() - backButton.getHeight());
        backButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(new MainMenuScreen(game));
            }
        });

        Image achievementsImage = new Image(achievementsTexture);
        achievementsImage.setSize(500, 150);
        achievementsImage.setPosition(Gdx.graphics.getWidth() / 2 - achievementsImage.getWidth() / 2,
            Gdx.graphics.getHeight() / 2 - achievementsImage.getHeight() / 2 + 250);

        Image achievementImage1 = new Image(achievementTexture1);
        achievementImage1.setSize(400, 200);
        achievementImage1.setPosition(Gdx.graphics.getWidth() / 2 - achievementImage1.getWidth() / 2 - 500,
            Gdx.graphics.getHeight() / 2 - achievementImage1.getHeight() / 2);

        Image achievementImage2 = new Image(achievementTexture2);
        achievementImage2.setSize(400, 200);
        achievementImage2.setPosition(Gdx.graphics.getWidth() / 2 - achievementImage2.getWidth() / 2,
            Gdx.graphics.getHeight() / 2 - achievementImage2.getHeight() / 2);

        Image achievementImage3 = new Image(achievementTexture3);
        achievementImage3.setSize(400, 200);
        achievementImage3.setPosition(Gdx.graphics.getWidth() / 2 - achievementImage3.getWidth() / 2 + 500,
            Gdx.graphics.getHeight() / 2 - achievementImage3.getHeight() / 2);

        stage.addActor(backButton);
        stage.addActor(achievementsImage);
        stage.addActor(achievementImage1);
        stage.addActor(achievementImage2);
        stage.addActor(achievementImage3);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act();
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        stage.dispose();
        skin.dispose();
        achievementsTexture.dispose();
        achievementTexture1.dispose();
        achievementTexture2.dispose();
        achievementTexture3.dispose();
    }
}
