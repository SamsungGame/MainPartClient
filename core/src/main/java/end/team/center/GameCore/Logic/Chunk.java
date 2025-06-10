package end.team.center.GameCore.Logic;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.ArrayList;

import end.team.center.GameCore.Objects.InInventary.Drops;
import end.team.center.GameCore.Objects.Map.Tree;

public class Chunk extends Stage {
    private ArrayList<Tree> actorsTree;
    private ArrayList<Drops> actorsItem;
    private Rectangle bound;

    public Chunk(float x, float y, float width, float height, Viewport viewport) {
        super(viewport);
        bound = new Rectangle(x - width / 2, y - height / 2, width * 2, height * 2);
        actorsTree = new ArrayList<>();
        actorsItem = new ArrayList<>();
    }

    @Override
    public void addActor(Actor a) {
        if (a instanceof Drops) super.addActor((Drops) a);
        if (a instanceof Tree) super.addActor((Tree) a);

        if (a instanceof Drops) actorsItem.add((Drops) a);
        else actorsTree.add((Tree) a);
    }

    public ArrayList<Drops> getItems() {
        return actorsItem;
    }

    public ArrayList<Tree> getTrees() {
        return actorsTree;
    }

    @Override
    public void draw() {
        super.draw();
    }

    @Override
    public void act(float delta) {
        super.act(delta);
    }

    public Rectangle getBound() {
        return bound;
    }
}
