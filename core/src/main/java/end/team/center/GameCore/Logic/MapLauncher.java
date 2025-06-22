package end.team.center.GameCore.Logic;

import static end.team.center.Screens.Game.GameScreen.WORLD_HEIGHT;
import static end.team.center.Screens.Game.GameScreen.WORLD_WIDTH;
import static end.team.center.Screens.Game.GameScreen.chunks;
import static end.team.center.Screens.Game.GameScreen.hero;
import static end.team.center.Screens.Game.GameScreen.worldViewport;
import static end.team.center.Screens.Game.GameScreen.zone;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;

import end.team.center.GameCore.Library.Other.Portal;
import end.team.center.GameCore.Objects.Map.BackgroundTiledRenderer;
import end.team.center.GameCore.Objects.Map.Tree;
import end.team.center.GameCore.Objects.Map.Zone;
import end.team.center.GameCore.Objects.OnMap.GameObject;
import end.team.center.ProgramSetting.LocalDB.GameRepository;

public class MapLauncher {
    private static ArrayList<Tree> treesObj;
    private static ArrayList<Tree> obj = new ArrayList<>();
    private static final int COUNT_TREE = 3500;
    private static final int COUNT_ZONE = 200;
    private static final Texture[] trees = {
        new Texture(Gdx.files.internal("UI/GameUI/Mobs/Tree/treeT1.png")),
        new Texture(Gdx.files.internal("UI/GameUI/Mobs/Tree/treeT2.png")),
        new Texture(Gdx.files.internal("UI/GameUI/Mobs/Tree/treeT3.png")),
        new Texture(Gdx.files.internal("UI/GameUI/Mobs/Tree/treeT4.png"))
    };

    public MapLauncher() {

    }

    public BackgroundTiledRenderer generationBrick() {
        TextureRegion[] tiles = new TextureRegion[]{
            new TextureRegion(new Texture("UI/GameUI/Grow/dirt1_big.png")),
            new TextureRegion(new Texture("UI/GameUI/Grow/dirt2_big.png")),
            new TextureRegion(new Texture("UI/GameUI/Grow/dirt3_big.png")),
            new TextureRegion(new Texture("UI/GameUI/Grow/dirt4_big.png")),
        };

        int tileWidth = 250;
        int tileHeight = 250;

        return new BackgroundTiledRenderer(tiles, tileWidth, tileHeight);
    }

    public void generateChunk() {
        for (int i = 0; i < WORLD_WIDTH; i += (int) (WORLD_WIDTH / 20)) {
            for (int y = 0; y < WORLD_HEIGHT; y += (int) (WORLD_HEIGHT / 20)) {
                chunks.add(new Chunk(i, y, WORLD_WIDTH / 20, WORLD_HEIGHT / 20, worldViewport));
            }
        }
    }

    public void generateZone() {
        for (int i = 0; i < COUNT_ZONE; i++) {
            Zone z = new Zone((int) (1 + Math.random() * 5));
            zone.add(z);
        }
    }

    public Portal generationPortal(GameRepository repo) {
        return new Portal(repo,
            new Texture(Gdx.files.internal("UI/GameUI/Structure/portal1.png")),
            new Texture(Gdx.files.internal("UI/GameUI/Structure/portal2.png")),
            new Texture(Gdx.files.internal("UI/GameUI/Structure/portal3.png")),
            spawnPos(-1), hero, 171, 189);
    }

    public ArrayList<Tree> generationTree() {
        treesObj = new ArrayList<>();

        for (int i = 0; i < COUNT_TREE; i++) {
            int key = MathUtils.random(0, 3);

            Tree t = new Tree(trees[key], spawnPos(key), trees[key].getHeight() * 10, trees[key].getWidth() * 10, false);

            treesObj.add(t);
            obj     .add(t);
        }

        return treesObj;
    }

    public Vector2 spawnPos(int key) {
        Vector2 center = new Vector2(WORLD_WIDTH / 2f, WORLD_HEIGHT / 2f), spawnPos;
        float distance, minDistance = 400;

        Vector2 size;
        if (key != -1) size = new Vector2(trees[key].getWidth() * 10, trees[key].getHeight() * 10);
        else           size = new Vector2(189, 171);

        boolean correctPos;

        do {
            correctPos = true;

            spawnPos = new Vector2(
                MathUtils.random(0, WORLD_WIDTH),
                MathUtils.random(0, WORLD_HEIGHT)
            );
            distance = spawnPos.dst(center);

            for (GameObject o: obj) {
                if (o.getBound().overlaps(new Rectangle(spawnPos.x, spawnPos.y, size.x, size.y))) {
                    correctPos = false;
                }
            }

        } while (distance < minDistance && correctPos);

        return spawnPos;
    }
}
