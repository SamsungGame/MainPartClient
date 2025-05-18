package end.team.center.Screens.Game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import end.team.center.GameCore.HelpsClass.PowerElement;

public class PowerSelectScreen implements Screen {
    /*
    Предпологается что png у PowerElements равен 180 на 180 пикселей
     */
    private Stage stage;
    private SpriteBatch batch;
    private PowerElement[] buffs = new PowerElement[3];
    private Texture selectBuff, unSelectBuff;
    private Sprite portal;
    private Table table;
    private boolean buffSelected = false;
    private int distance = 30;

    @Override
    public void show() {
        // Создаем сцену
        stage = new Stage(new ScreenViewport());

        selectBuff   = new Texture(Gdx.files.internal("UI/GameUI/SelectPowerUI/selectPower.png"));
        unSelectBuff = new Texture(Gdx.files.internal("UI/GameUI/SelectPowerUI/power.png"));

        portal = new Sprite(new Texture(Gdx.files.internal("UI/GameUI/SelectPowerUI/portal.png")));
        portal.setPosition((float) (Gdx.graphics.getWidth() / 2) - (portal.getWidth() / 2),
            (float) Gdx.graphics.getHeight() / 2.5f);

        batch = new SpriteBatch();
        buffs[0] = new PowerElement(unSelectBuff, portal);
        buffs[1] = new PowerElement(unSelectBuff, portal);
        buffs[2] = new PowerElement(unSelectBuff, portal);

        buffs[0].setTouchable(Touchable.enabled);
        buffs[1].setTouchable(Touchable.enabled);
        buffs[2].setTouchable(Touchable.enabled);

        buffs[0].addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                selectBuff(0);
            }
        });
        buffs[1].addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                selectBuff(1);
            }
        });
        buffs[2].addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                selectBuff(2);
            }
        });

        // Создаем таблицу
        table = new Table();
        table.setFillParent(true);
        table.center().bottom();

        // Добавляем изображения в таблицу с отступами
        table.add(buffs[0]).padRight(distance).height(180).width(180); // Отступ справа от первого спрайта
        table.add(buffs[1]).padRight(distance).height(180).width(180); // Отступ справа от второго спрайта
        table.add(buffs[2]).height(180).width(180); // Последний спрайт без отступа

        stage.addActor(table);
        Gdx.input.setInputProcessor(stage); // Устанавливаем обработчик ввода
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));

        batch.begin();
        for (PowerElement pe: buffs) {
            pe.draw(batch, 1);
        }
        portal.draw(batch);
        batch.end();
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
        unSelectBuff.dispose();
        batch.dispose();
    }

    private void selectBuff(int id) {
        if (!buffSelected) { // Если не был выбран ни один бафф - мы выделяем текущий
            buffSelected = true;
            buffs[id].isSelected = true;
            buffs[id].setTexture(selectBuff);
        }
        else if (buffs[id].isSelected) { // Если мы нажимаем на бафф второй раз - мы выбираем его
            buffs[id].startAnimation();
            for (int i = 0; i < buffs.length; i++) {
                if (i != id) buffs[i].startSmallAnimation();
            }
            System.out.println("Выбрано!");
        }
        else { // Если был выбран другой бафф, но мы нажали на новый, то мы выключаем прошлый и включаем новый
            for (int i = 0; i < buffs.length; i++) {
                if (buffs[i].isSelected) {
                    buffs[i].isSelected = false;
                    buffs[i].setTexture(unSelectBuff);
                }
            }
            buffSelected = true;
            buffs[id].isSelected = true;
            buffs[id].setTexture(selectBuff);
        }
        table.invalidate();
    }
}
