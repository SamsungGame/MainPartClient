package end.team.center.Screens.Menu;

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
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import java.util.ArrayList;

import end.team.center.Center;
import end.team.center.ProgramSetting.Achievement;
import end.team.center.ProgramSetting.LocalDB.GameData;
import end.team.center.ProgramSetting.LocalDB.GameRepository;
import end.team.center.Screens.Game.GameScreen;

public class AchievementsScreen implements Screen {
    private final SpriteBatch batch = new SpriteBatch();
    private final BitmapFont fontAchievements = new BitmapFont(Gdx.files.internal("UI/AboutGame/pixel_font.fnt"));
    private final BitmapFont fontDescription = new BitmapFont(Gdx.files.internal("UI/AboutGame/pixel_font.fnt"));
    private final GlyphLayout layoutAchievements = new GlyphLayout();
    private final GlyphLayout layoutDescription = new GlyphLayout();
    private final float layoutAchievementsX;
    private final float layoutAchievementsY;
    private final Stage stage;
    private final Skin skin;
    private final Texture backgroundTexture = new Texture(Gdx.files.internal("UI/MainMenu/fon.jpg"));
    private final String[] achievementsIfObtainedTexturePaths = {
        "UI/GameUI/Achievements/mini/exit_mini.png",
        "UI/GameUI/Achievements/mini/knife_mini.png",
        "UI/GameUI/Achievements/mini/time_mini.png",
        "UI/GameUI/Achievements/mini/door_mini.png",
        "UI/GameUI/Achievements/mini/clear_mini.png",
    };
    private final String[] achievementsIfNotObtainedTexturePaths = {
        "UI/GameUI/Achievements/mini/notActive/notActive_exit.png",
        "UI/GameUI/Achievements/mini/notActive/notActive_knife.png",
        "UI/GameUI/Achievements/mini/notActive/notActive_time.png",
        "UI/GameUI/Achievements/mini/notActive/notActive_door.png",
        "UI/GameUI/Achievements/mini/notActive/notActive_clear.png",
    };

    ArrayList<Image> achievementsIfObtainedImages = new ArrayList<>();
    ArrayList<Image> achievementsIfNotObtainedImages = new ArrayList<>();
    ArrayList<Achievement> achievements = new ArrayList<>();
    private int selectedPosition = 0;
    private boolean isAchievementSelected = false;

    public AchievementsScreen(GameRepository repo) {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        skin = new Skin(Gdx.files.internal("UI/MainMenu/backButton.json"));

        fontAchievements.getData().setScale(4.0f);
        String achievementsText = "Достижения";
        layoutAchievements.setText(fontAchievements, achievementsText);
        layoutAchievementsX = (Gdx.graphics.getWidth() - layoutAchievements.width) / 2;
        layoutAchievementsY = Gdx.graphics.getHeight() - layoutAchievements.height;

        fontDescription.getData().setScale(2.0f);
        layoutDescription.setText(fontDescription, "");

        ImageButton backButton = new ImageButton(skin);
        backButton.setSize(200, 150);
        backButton.setPosition(50,  Gdx.graphics.getHeight() - backButton.getHeight());

        backButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                ((Center) Gdx.app.getApplicationListener()).setScreen(new MainMenuScreen(GameScreen.endCode, repo));
            }
        });

        for (String path : achievementsIfObtainedTexturePaths) {
            achievementsIfObtainedImages.add(new Image(new Texture(Gdx.files.internal(path))));
        }

        for (String path : achievementsIfNotObtainedTexturePaths) {
            achievementsIfNotObtainedImages.add(new Image(new Texture(Gdx.files.internal(path))));
        }

        // --- Изменения начинаются здесь ---
        float achievementWidth = 150; // Ширина каждой иконки достижения
        float spacing = achievementWidth / 2; // Промежуток между иконками (равный половине ширины иконки)
        int numberOfAchievements = achievementsIfObtainedImages.size();
        float totalWidth = (numberOfAchievements * achievementWidth) + ((numberOfAchievements - 1) * spacing);
        float startX = (Gdx.graphics.getWidth() - totalWidth) / 2; // Начальная X-координата для центрирования

        for (int i = 0; i < numberOfAchievements; i++) {
            float currentX = startX + i * (achievementWidth + spacing);

            achievementsIfObtainedImages.get(i).setSize(achievementWidth, achievementWidth);
            achievementsIfObtainedImages.get(i).setPosition(currentX,
                Gdx.graphics.getHeight() / 2 - achievementWidth / 2); // Центрирование по Y
            achievementsIfNotObtainedImages.get(i).setSize(achievementWidth, achievementWidth);
            achievementsIfNotObtainedImages.get(i).setPosition(currentX,
                Gdx.graphics.getHeight() / 2 - achievementWidth / 2); // Центрирование по Y
        }
        // --- Изменения заканчиваются здесь ---

        for (int i = 0; i < achievementsIfObtainedImages.size(); i++) {
            achievements.add(new Achievement(
                GameData.ACHIEVEMENTS.get(i).name,
                GameData.ACHIEVEMENTS.get(i).description,
                Boolean.TRUE.equals(repo.getAchievements().get(i + 1)), // ← safe!
                achievementsIfObtainedImages.get(i),
                achievementsIfNotObtainedImages.get(i)
            ));
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
                descriptionText += "\n" + achievement.description;
            }
            layoutDescription.setText(fontDescription, descriptionText);
            float layoutDescriptionX = Gdx.graphics.getWidth() / 2 - layoutDescription.width / 2;
            // Убедитесь, что эта Y-координата не перекрывает ачивки и находится ниже
            float layoutDescriptionY = Gdx.graphics.getHeight() / 2 - (achievements.get(0).achievementIfObtained.getHeight() / 2) - layoutDescription.height - 30; // 30 - это дополнительный отступ
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
        stage.getViewport().update(width, height, true); // Обновление вьюпорта при изменении размера экрана
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
            // Проверяем, что drawable является TextureRegionDrawable перед приведением
            if (image.getDrawable() instanceof TextureRegionDrawable) {
                new TextureRegionDrawable((TextureRegionDrawable) image.getDrawable()).getRegion().getTexture().dispose();
            }
        }
        for (Image image : achievementsIfNotObtainedImages) {
            // Проверяем, что drawable является TextureRegionDrawable перед приведением
            if (image.getDrawable() instanceof TextureRegionDrawable) {
                new TextureRegionDrawable((TextureRegionDrawable) image.getDrawable()).getRegion().getTexture().dispose();
            }
        }
    }
}
