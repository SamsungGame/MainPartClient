package end.team.center.Screens.Menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import end.team.center.ProgramSetting.Config;

public class SettingScreen implements Screen {
    private Stage stage;
    private Skin skin;

    // TODO: Изменить Skin у CheckBox'ов
    public SettingScreen() {
        // Создаем Stage и Skin
        stage = new Stage(new ScreenViewport());
        skin = new Skin(Gdx.files.internal("recourse/SettingUI/checkBox.json")); // Убедитесь, что у вас есть файл skin

        // Создаем CheckBox для музыки
        CheckBox musicCheckBox = new CheckBox("Музыка", skin);
        musicCheckBox.setChecked(true);

        // Создаем CheckBox для звуковых эффектов
        CheckBox soundCheckBox = new CheckBox("Звук", skin);
        soundCheckBox.setChecked(true);

        // Создаем таблицу для размещения CheckBox по центру экрана
        Table table = new Table();
        table.setFillParent(true);
        table.center();

        // Добавляем CheckBox в таблицу
        table.add(musicCheckBox).pad(10).width(300).height(130);
        table.row();
        table.add(soundCheckBox).pad(10).width(300).height(130);

        musicCheckBox.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Config.playMusic = !Config.playMusic;
            }
        });

        soundCheckBox.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Config.playSound = !Config.playSound;
            }
        });

        // Добавляем таблицу на сцену
        stage.addActor(table);

        Gdx.input.setInputProcessor(stage); // Устанавливаем обработчик ввода для сцены
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
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
    }
}
