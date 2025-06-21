package end.team.center.screens;

import static end.team.center.MyGame.currentWeapon;
import static end.team.center.MyGame.isBoughtWeapons;
import static end.team.center.MyGame.pricesOfWeapons;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import end.team.center.MyGame;

public class WeaponsScreen implements Screen {
    private final SpriteBatch batch = new SpriteBatch();
    private final BitmapFont fontWeapons = new BitmapFont(Gdx.files.internal("buttonStyle/pixel_font.fnt"));
    private final BitmapFont fontName = new BitmapFont(Gdx.files.internal("buttonStyle/pixel_font.fnt"));
    private final BitmapFont fontDescription = new BitmapFont(Gdx.files.internal("buttonStyle/pixel_font.fnt"));
    private final GlyphLayout layoutWeapons = new GlyphLayout();
    private final GlyphLayout layoutName = new GlyphLayout();
    private final GlyphLayout layoutDescription = new GlyphLayout();
    public final float layoutWeaponsX;
    public final float layoutWeaponsY;
    public float layoutNameX;
    public float layoutNameY;
    public float layoutDescriptionX;
    public float layoutDescriptionY;
    private final Stage stage;
    private final Skin skin;
    private final Skin leftButtonSkin;
    private final Skin rightButtonSkin;
    private final Image currentImage;
    private int currentIndex;
    private float touchStartX;
    private final Texture backgroundTexture = new Texture(Gdx.files.internal("background.png"));
    private final Texture[] images = new Texture[] {
        new Texture(Gdx.files.internal("weapons/knife.png")),
        new Texture(Gdx.files.internal("weapons/knife.png")),
        new Texture(Gdx.files.internal("weapons/knife.png"))
    };
    private final String[] names = new String[] {
        "Нож",
        "Лук",
        "Булава"
    };
    private final String[] descriptions = new String[] {
        "Начальный друг",
        "Зоркий помощник",
        "Тяжёлый устранитель"
    };
    private final TextButton buyButton;

    public WeaponsScreen(MyGame game) {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(new InputAdapter() {
            @Override
            public boolean touchDown(int screenX, int screenY, int pointer, int button) {
                if (screenX < Gdx.graphics.getWidth() / 2) {
                    touchStartX = screenX;
                }
                return stage.touchDown(screenX, screenY, pointer, button);
            }

            @Override
            public boolean touchUp(int screenX, int screenY, int pointer, int button) {
                if (screenX < Gdx.graphics.getWidth() / 2) {
                    float deltaX = screenX - touchStartX;
                    if (Math.abs(deltaX) > 50) {
                        if (deltaX > 0) {
                            showPreviousImage();
                        }
                        else {
                            showNextImage();
                        }
                        return true;
                    }
                }
                return stage.touchUp(screenX, screenY, pointer, button);
            }

            @Override
            public boolean touchDragged(int screenX, int screenY, int pointer) {
                if (screenX < Gdx.graphics.getWidth() / 2) {
                    return stage.touchDragged(screenX, screenY, pointer);
                }
                return false;
            }
        });

        skin = new Skin(Gdx.files.internal("buttonStyle/buttonStyle.json"));
        leftButtonSkin = new Skin();
        leftButtonSkin.add("button_up", new Texture(Gdx.files.internal("buttons/leftButton.png")));
        leftButtonSkin.add("button_down", new Texture(Gdx.files.internal("buttons/leftButton.png")));
        ImageButton.ImageButtonStyle leftButtonStyle = new ImageButton.ImageButtonStyle();
        leftButtonStyle.imageUp = leftButtonSkin.getDrawable("button_up");
        leftButtonStyle.imageDown = leftButtonSkin.getDrawable("button_down");
        rightButtonSkin = new Skin();
        rightButtonSkin.add("button_up", new Texture(Gdx.files.internal("buttons/rightButton.png")));
        rightButtonSkin.add("button_down", new Texture(Gdx.files.internal("buttons/rightButton.png")));
        ImageButton.ImageButtonStyle rightButtonStyle = new ImageButton.ImageButtonStyle();
        rightButtonStyle.imageUp = rightButtonSkin.getDrawable("button_up");
        rightButtonStyle.imageDown = rightButtonSkin.getDrawable("button_down");

        fontWeapons.getData().setScale(4.0f);
        String weaponsText = "Оружие";
        layoutWeapons.setText(fontWeapons, weaponsText);
        layoutWeaponsX = (Gdx.graphics.getWidth() - layoutWeapons.width) / 2;
        layoutWeaponsY = Gdx.graphics.getHeight() - layoutWeapons.height;

        fontName.getData().setScale(2.0f);

        fontDescription.getData().setScale(1.5f);

        TextButton backButton = new TextButton("Назад", skin);
        backButton.setSize(200, 150);
        backButton.setPosition(50,  Gdx.graphics.getHeight() - backButton.getHeight());

        backButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(new MainMenuScreen(game));
            }
        });

        currentIndex = currentWeapon - 1;

        currentImage = new Image(new TextureRegionDrawable(images[currentIndex]));
        currentImage.setSize(30, 130);
        currentImage.setPosition(Gdx.graphics.getWidth() / 4 - currentImage.getWidth() / 2, Gdx.graphics.getHeight() / 2 - currentImage.getHeight() / 2);

        ImageButton leftButton = new ImageButton(leftButtonStyle);
        leftButton.setSize(100, 100);
        leftButton.setPosition(currentImage.getX() - leftButton.getWidth() * 1.5f, Gdx.graphics.getHeight() / 2 - leftButton.getHeight() / 2);

        ImageButton rightButton = new ImageButton(rightButtonStyle);
        rightButton.setSize(100, 100);
        rightButton.setPosition(currentImage.getX() + currentImage.getWidth() + rightButton.getWidth() / 2, Gdx.graphics.getHeight() / 2 - rightButton.getHeight() / 2);

        leftButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                showPreviousImage();
            }
        });

        rightButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                showNextImage();
            }
        });

        buyButton = new TextButton("", skin);
        buyButton.setSize(currentImage.getWidth() + (leftButton.getWidth() + rightButton.getWidth()) / 2, 150);
        buyButton.setPosition(currentImage.getX() + currentImage.getWidth() / 2 - buyButton.getWidth() / 2, currentImage.getY() - buyButton.getHeight() / 2 - 100);

        buyButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                isBoughtWeapons.remove(currentIndex);
                isBoughtWeapons.add(currentIndex, true);
                currentWeapon = currentIndex + 1;
            }
        });

        stage.addActor(backButton);
        stage.addActor(leftButton);
        stage.addActor(rightButton);
        stage.addActor(currentImage);
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
        fontWeapons.draw(batch, layoutWeapons, layoutWeaponsX, layoutWeaponsY);
        String name = names[currentIndex];
        layoutName.setText(fontName, name);
        layoutNameX = Gdx.graphics.getWidth() * 3 / 4 - layoutName.width / 2;
        layoutNameY = layoutWeaponsY - layoutName.height - 150;
        fontName.draw(batch, layoutName, layoutNameX, layoutNameY);
        String description = descriptions[currentIndex];
        layoutDescription.setText(fontDescription, description);
        layoutDescriptionX = Gdx.graphics.getWidth() * 3 / 4 - layoutDescription.width / 2;
        layoutDescriptionY = layoutNameY - layoutDescription.height - 150;
        fontDescription.draw(batch, layoutDescription, layoutDescriptionX, layoutDescriptionY);
        batch.end();

        buyButton.setText(pricesOfWeapons.get(currentIndex));
        if (!isBoughtWeapons.get(currentIndex)) {
            stage.addActor(buyButton);
        }
        else {
            buyButton.remove();
        }

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
        fontWeapons.dispose();
        fontName.dispose();
        fontDescription.dispose();
        stage.dispose();
        skin.dispose();
        leftButtonSkin.dispose();
        rightButtonSkin.dispose();
        backgroundTexture.dispose();
    }

    private void showNextImage() {
        currentIndex = (currentIndex + 1) % images.length;
        updateImage();
    }

    private void showPreviousImage() {
        currentIndex = (currentIndex - 1 + images.length) % images.length;
        updateImage();
    }

    private void updateImage() {
        currentImage.setDrawable(new TextureRegionDrawable(images[currentIndex]));
        if (isBoughtWeapons.get(currentIndex)) {
            currentWeapon = currentIndex + 1;
        }
    }
}
