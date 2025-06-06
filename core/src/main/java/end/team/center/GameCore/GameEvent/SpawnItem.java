package end.team.center.GameCore.GameEvent;

import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;
import java.util.Random;

import end.team.center.GameCore.Library.ItemType;
import end.team.center.GameCore.Library.Items.Acumulattor;
import end.team.center.GameCore.Library.Items.Bandage;
import end.team.center.GameCore.Library.Items.SharpnessStone;
import end.team.center.GameCore.Library.Items.Lamp;
import end.team.center.GameCore.Objects.InInventary.Drops;
import end.team.center.GameCore.Objects.OnMap.Entity;
import end.team.center.GameCore.Objects.OnMap.Hero;
import end.team.center.Screens.Game.GameScreen;

public class SpawnItem {

    private float minRadiusSpawn = Entity.BOUNDARY_PADDING + 100;
    private final int maxRadiusSpawn = (int) Math.min(GameScreen.WORLD_HEIGHT, GameScreen.WORLD_WIDTH);
    private Post poster;
    private Hero hero;
    private ItemType[] canDrop;
    private float timeSpawn;
    private boolean isSpawn = true;

    public SpawnItem(Post poster, Hero hero) {
        this.poster = poster;
        this.hero = hero;

        canDrop = new ItemType[4];
        canDrop[0] = ItemType.accumulator;
        canDrop[1] = ItemType.BreakIron;
        canDrop[2] = ItemType.Bandage;
        canDrop[3] = ItemType.lamp;

        timeSpawn = 0.5f;
    }

    public void goWork() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (isSpawn) {
                    if (!GameScreen.STOP) {
                        poster.post(spawn());

                        try {
                            Thread.sleep((long) (timeSpawn * 1000L));
                        } catch (InterruptedException ignore) {
                        }
                    }
                }
            }
        }).start();
    }
    @SuppressWarnings("NewApi")
    public Drops spawn() {
        int r = (int) Math.floor(Math.random() * 4);

        if(r == 0) {
            return new Acumulattor(ItemType.accumulator, setPosition(), hero);
        } else if (r == 1) {
            return new SharpnessStone(ItemType.BreakIron, setPosition(), hero);
        } else if (r == 2) {
            return new Bandage(ItemType.Bandage, setPosition(), hero);
        } else {
            return new Lamp(ItemType.lamp, setPosition(), hero);
        }
    }

    @SuppressWarnings("NewApi")
    public Vector2 setPosition() {
        Random random = new Random();

        return new Vector2(random.nextInt((int) minRadiusSpawn, (int) (GameScreen.WORLD_WIDTH - minRadiusSpawn)), random.nextInt((int) minRadiusSpawn, (int) (GameScreen.WORLD_HEIGHT - minRadiusSpawn)));
    }

    public ArrayList<Drops> startDropSet() {
        ArrayList<Drops> dropsStart = new ArrayList<>();

        for(int i = 0; i < 900; i++) {
            dropsStart.add(spawn());
        }

        return dropsStart;
    }

    public void dispose() {
        isSpawn = false;
    }
}
