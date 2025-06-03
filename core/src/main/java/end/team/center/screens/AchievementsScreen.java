package end.team.center.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import java.util.ArrayList;

import end.team.center.Achievement;
import end.team.center.MyGame;

public class AchievementsScreen implements Screen {
    private final SpriteBatch batch = new SpriteBatch();
    private final BitmapFont fontAchievements = new BitmapFont(Gdx.files.internal("buttonStyle/pixel_font.fnt"));
    private final BitmapFont fontDescription = new BitmapFont(Gdx.files.internal("buttonStyle/pixel_font.fnt"));
    private final GlyphLayout layoutAchievements = new GlyphLayout();
    private final float layoutAchievementsX;
    private final float layoutAchievementsY;
//    private final float layoutDescriptionX;
//    private final float layoutDescriptionY;
    private final Stage stage;
    private final Skin skin;
    private final Texture backgroundTexture = new Texture(Gdx.files.internal("background.png"));
    private final Texture achievementDoorObtainedTexture = new Texture(Gdx.files.internal("achievements/obtained/door_mini.png"));
    private final Texture achievementExitObtainedTexture = new Texture(Gdx.files.internal("achievements/obtained/exit_mini.png"));
    ArrayList<Achievement> achievements = new ArrayList<>();

    public AchievementsScreen(MyGame game) {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        skin = new Skin(Gdx.files.internal("buttonStyle/buttonStyle.json"));

        fontAchievements.getData().setScale(4.0f);
        String achievementsText = "Достижения";
        layoutAchievements.setText(fontAchievements, achievementsText);
        layoutAchievementsX = (Gdx.graphics.getWidth() - layoutAchievements.width) / 2;
        layoutAchievementsY = Gdx.graphics.getHeight() - layoutAchievements.height;

        TextButton backButton = new TextButton("Назад", skin);
        backButton.setSize(200, 150);
        backButton.setPosition(50,  Gdx.graphics.getHeight() - backButton.getHeight());

        backButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(new MainMenuScreen(game));
            }
        });

        Image achievementDoorObtainedImage = new Image(achievementDoorObtainedTexture);
        achievementDoorObtainedImage.setSize(150, 150);
        achievementDoorObtainedImage.setPosition(100, Gdx.graphics.getHeight() / 2 - achievementDoorObtainedImage.getHeight() / 2);

        achievements.add(new Achievement("Прошёл сквозь завесу", "Заверши забег и выберись", false, achievementDoorObtainedImage, achievementDoorObtainedImage));

        stage.addActor(backButton);
        for (Achievement achievement : achievements) {
            if (achievement.isObtained) {
                stage.addActor(achievement.achievementIfObtained);
            }
            else {
                stage.addActor(achievement.achievementIfNotObtained);
            }
        }
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        batch.setColor(0.5f, 0.5f, 0.5f, 1f);
        batch.draw(backgroundTexture, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        fontAchievements.draw(batch, layoutAchievements, layoutAchievementsX, layoutAchievementsY);
        batch.end();

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
        batch.dispose();
        fontAchievements.dispose();
        fontDescription.dispose();
        stage.dispose();
        skin.dispose();
        backgroundTexture.dispose();
        achievementDoorObtainedTexture.dispose();
        achievementExitObtainedTexture.dispose();
    }
}
