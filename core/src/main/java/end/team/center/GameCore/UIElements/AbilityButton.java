package end.team.center.GameCore.UIElements;

import static end.team.center.Screens.Game.GameScreen.hero;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

public class AbilityButton extends ImageButton {
    public AbilityButton(Skin skin) {
        super(skin);

        setSize(200, 200);
        setPosition(Gdx.graphics.getWidth() - 400, 40);

        addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                hero.activateUniqueAbility();
            }
        });
    }
}
