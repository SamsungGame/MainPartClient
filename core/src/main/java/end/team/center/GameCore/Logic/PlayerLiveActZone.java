package end.team.center.GameCore.Logic;

import static end.team.center.Screens.Game.GameScreen.hero;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;

import java.util.ArrayList;

import end.team.center.GameCore.Objects.InInventary.Drops;
import end.team.center.GameCore.Objects.Map.Tree;
import end.team.center.GameCore.Objects.OnMap.Hero;
import end.team.center.Screens.Game.GameScreen;

public class PlayerLiveActZone {
    public Rectangle bound;
    public int size = 5500;

    public ArrayList<Actor> actors;

    public PlayerLiveActZone(Hero h) {
        bound = new Rectangle((int) (h.getX() - size / 2), (int) (h.getY() - size / 2), size, size);
        actors = new ArrayList<>();

        update();
    }

    public void update() {
        actors.clear();

        bound.x = (int) (hero.getX() - size / 2);
        bound.y = (int) (hero.getY() - size / 2);

        for(Tree t: GameScreen.trees) {
            if (t.getBound().overlaps(bound)) {
                actors.add(t);
            }
        }

        for(Drops t: GameScreen.drop) {
            if (t.getBound().overlaps(bound)) {
                actors.add(t);
            }
        }
    }

    public void act(float delta) {
        for (Actor a: actors) {
            a.act(delta);
        }
    }
}
