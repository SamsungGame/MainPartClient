package end.team.center.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import java.util.ArrayList;

import end.team.center.Achievement;
import end.team.center.MyGame;

public class AchievementsScreen implements Screen {
    private final SpriteBatch batch = new SpriteBatch();
    private final BitmapFont fontAchievements = new BitmapFont(Gdx.files.internal("buttonStyle/pixel_font.fnt"));
    private final BitmapFont fontDescription = new BitmapFont(Gdx.files.internal("buttonStyle/pixel_font.fnt"));
    private final GlyphLayout layoutAchievements = new GlyphLayout();
    private final GlyphLayout layoutDescription = new GlyphLayout();
    private final float layoutAchievementsX;
    private final float layoutAchievementsY;
    private final Stage stage;
    private final Skin skin;
    private final Texture backgroundTexture = new Texture(Gdx.files.internal("background.png"));
    private final String[] achievementsNames = {
        "Прошёл сквозь завесу",
        "Нож - всё, что было нужно",
        "Тишина длиной в вечность",
        "Дверь была открыта...",
        "Руки остались чистыми"
    };
    private final String[] achievementsDescriptions = {
        "Заверши забег и выберись",
        "Заверши забег, не подняв ни одного предмета",
        "Выживи 10 минут",
        "Умри у самого выхода",
        "Выберись, никого не убив"
    };
    private final String[] achievementsIfObtainedTexturePaths = {
        "achievements/obtained/clear_mini.png",
        "achievements/obtained/door_mini.png",
        "achievements/obtained/exit_mini.png",
        "achievements/obtained/knife_mini.png",
        "achievements/obtained/time_mini.png"
    };
    private final String[] achievementsIfNotObtainedTexturePaths = {
        "achievements/notObtained/grayscale_clear.png",
        "achievements/notObtained/grayscale_door.png",
        "achievements/notObtained/grayscale_exit.png",
        "achievements/notObtained/grayscale_knife.png",
        "achievements/notObtained/grayscale_time.png"
    };

    ArrayList<Image> achievementsIfObtainedImages = new ArrayList<>();
    ArrayList<Image> achievementsIfNotObtainedImages = new ArrayList<>();
    ArrayList<Achievement> achievements = new ArrayList<>();
    private int selectedPosition = 0;
    private boolean isAchievementSelected = false;

    public AchievementsScreen(MyGame game) {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        skin = new Skin(Gdx.files.internal("buttonStyle/buttonStyle.json"));

        fontAchievements.getData().setScale(4.0f);
        String achievementsText = "Достижения";
        layoutAchievements.setText(fontAchievements, achievementsText);
        layoutAchievementsX = (Gdx.graphics.getWidth() - layoutAchievements.width) / 2;
        layoutAchievementsY = Gdx.graphics.getHeight() - layoutAchievements.height;

        fontDescription.getData().setScale(1.0f);
        layoutDescription.setText(fontDescription, "");

        TextButton backButton = new TextButton("Назад", skin);
        backButton.setSize(200, 150);
        backButton.setPosition(50,  Gdx.graphics.getHeight() - backButton.getHeight());

        backButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(new MainMenuScreen(game));
            }
        });

        for (String path : achievementsIfObtainedTexturePaths) {
            achievementsIfObtainedImages.add(new Image(new Texture(Gdx.files.internal(path))));
        }
        for (String path : achievementsIfNotObtainedTexturePaths) {
            achievementsIfNotObtainedImages.add(new Image(new Texture(Gdx.files.internal(path))));
        }
        for (int i = 0; i < achievementsIfObtainedImages.size(); i++) {
            achievementsIfObtainedImages.get(i).setSize(150, 150);
            achievementsIfObtainedImages.get(i).setPosition(225 + i * achievementsIfObtainedImages.get(i).getWidth() + i * achievementsIfObtainedImages.get(i).getWidth() / 2,
                Gdx.graphics.getHeight() / 2 - achievementsIfObtainedImages.get(i).getHeight() / 2);
            achievementsIfNotObtainedImages.get(i).setSize(150, 150);
            achievementsIfNotObtainedImages.get(i).setPosition(225 + i * achievementsIfNotObtainedImages.get(i).getWidth() + i * achievementsIfObtainedImages.get(i).getWidth() / 2,
                Gdx.graphics.getHeight() / 2 - achievementsIfNotObtainedImages.get(i).getHeight() / 2);
        }
        for (int i = 0; i < achievementsIfObtainedImages.size(); i++) {
            achievements.add(new Achievement(achievementsNames[i], achievementsDescriptions[i], false, achievementsIfObtainedImages.get(i), achievementsIfNotObtainedImages.get(i)));
        }

        stage.addActor(backButton);
        for (Achievement achievement : achievements) {
            achievement.achievementIfObtained.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    selectedPosition = achievements.indexOf(achievement);
                    isAchievementSelected = true;
                }
            });
            achievement.achievementIfNotObtained.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    selectedPosition = achievements.indexOf(achievement);
                    isAchievementSelected = true;
                }
            });
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
        batch.setColor(1f, 1f, 1f, 1f);
        if (isAchievementSelected) {
            Achievement achievement = achievements.get(selectedPosition);
            String descriptionText = achievement.name;
            if (achievement.isObtained) {
                descriptionText += "\n\n" + achievement.description;
            }
            layoutDescription.setText(fontDescription, descriptionText);
            float layoutDescriptionX = Gdx.graphics.getWidth() / 2 - layoutDescription.width / 2;
            float layoutDescriptionY = achievements.get(2).achievementIfObtained.getY() - layoutDescription.height - 10;
            fontDescription.draw(batch, layoutDescription, layoutDescriptionX, layoutDescriptionY);

            Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
            pixmap.setColor(Color.WHITE);
            pixmap.fill();
            Texture whitePixel = new Texture(pixmap);
            pixmap.dispose();
            batch.setColor(1, 1, 1f, 0.5f);
            float x = achievement.achievementIfObtained.getX() - 2.5f;
            float y = achievement.achievementIfObtained.getY() - 2.5f;
            batch.draw(whitePixel, x, y, achievement.achievementIfObtained.getWidth() + 5, achievement.achievementIfObtained.getHeight() + 5);
        }
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
        for (Image image : achievementsIfObtainedImages) {
            new TextureRegionDrawable((TextureRegionDrawable) image.getDrawable()).getRegion().getTexture().dispose();
        }
        for (Image image : achievementsIfNotObtainedImages) {
            new TextureRegionDrawable((TextureRegionDrawable) image.getDrawable()).getRegion().getTexture().dispose();
        }
    }
}
