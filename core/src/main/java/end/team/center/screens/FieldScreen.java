package end.team.center.screens;

import static end.team.center.MyGame.currentVolume;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;
import java.util.Random;

import end.team.center.Zone;
import end.team.center.MyGame;
import end.team.center.zoneTypes.BossField;
import end.team.center.zoneTypes.Dungeon;
import end.team.center.zoneTypes.Garden;
import end.team.center.zoneTypes.Player;
import end.team.center.zoneTypes.Portal;
import end.team.center.zoneTypes.Radiation;
import end.team.center.zoneTypes.Tree;

public class FieldScreen implements Screen {
    private static final int fieldX = 20000; // Размер поля по X
    private static final int fieldY = 20000; // Размер поля по Y
    private static final int cellSize = 1; // Размер клетки для отображения
    private final float centerX = fieldX / 2f;
    private final float centerY = fieldY / 2f;
    private float minX;
    private float minY;
    private float maxX;
    private float maxY;
    private final float viewportWidth = Gdx.graphics.getWidth();
    private final float viewportHeight = Gdx.graphics.getHeight();
    private final SpriteBatch batch = new SpriteBatch();
    private final Texture grassTexture = new Texture("field/grass.png");
    private final Texture treeTexture = new Texture("field/tree.png");
    private final Texture radiationTexture = new Texture("field/radiation.png");
    private final Texture gardenTexture = new Texture("field/garden.png");
    private final Texture bossFieldTexture = new Texture("field/bossField.png");
    private final Texture dungeonTexture = new Texture("field/dungeon.png");
    private final Music mainMenuMusic;
    private final Music gameMusic = Gdx.audio.newMusic(Gdx.files.internal("music/gameMusic.mp3"));
    ArrayList<Zone> zones = new ArrayList<>();
    ArrayList<Tree> treeZonesInsidePlayerZone = new ArrayList<>();

    public FieldScreen(MyGame game) {
        this.mainMenuMusic = MyGame.mainMenuMusic;

        generateZones();
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        minX = centerX - viewportWidth / 2;
        maxX = centerX + viewportWidth / 2;
        minY = centerY - viewportHeight / 2;
        maxY = centerY + viewportHeight / 2;

        if (minX < 0) {
            minX = 0;
            maxX = viewportWidth;
        }
        if (minY < 0) {
            minY = 0;
            maxY = viewportHeight;
        }
        if (maxX > fieldX) {
            maxX = fieldX;
            minX = fieldX - viewportWidth;
        }
        if (maxY > fieldY) {
            maxY = fieldY;
            minY = fieldY - viewportHeight;
        }

        ArrayList<Zone> visibleZones = getZonesInView();

        batch.begin();
        drawZones(batch, visibleZones);
        batch.end();

        mainMenuMusic.stop();
        gameMusic.setLooping(true);
        if (mainMenuMusic.getVolume() > 0) {
            gameMusic.setVolume(currentVolume);
        }
        else {
            gameMusic.setVolume(0.0f);
        }
        gameMusic.play();
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
        grassTexture.dispose();
        treeTexture.dispose();
        radiationTexture.dispose();
        gardenTexture.dispose();
        bossFieldTexture.dispose();
        dungeonTexture.dispose();
    }

    private void generateZones() {
        Random random = new Random();
        zones.add(new Player());

        int x;
        int y;
        int size = 50;
        while (true) {
            x = random.nextInt(fieldX - size);
            y = random.nextInt(fieldY - size);
            boolean correct = true;
            for (Zone zone : zones) {
                if (!(x + size < zone.x || zone.x + zone.size < x || y + size < zone.y || zone.y + zone.size < y)) {
                    correct = false;
                    break;
                }
            }
            if (correct) {
                break;
            }
        }

        zones.add(new Portal(x, y));

        size = 200;
        for (int j = 0; j < 100; j++) {
            while (true) {
                x = random.nextInt(fieldX - size);
                y = random.nextInt(fieldY - size);
                boolean correct = true;
                for (Zone zone : zones) {
                    if (!(x + size < zone.x || zone.x + zone.size < x || y + size < zone.y || zone.y + zone.size < y)) {
                        correct = false;
                        break;
                    }
                }
                if (correct) {
                    break;
                }
            }

            Garden garden = new Garden(x, y);
            garden.createRadiation();
            zones.add(garden);
        }

        size = 400;
        for (int j = 0; j < 100; j++) {
            while (true) {
                x = random.nextInt(fieldX - size);
                y = random.nextInt(fieldY - size);
                boolean correct = true;
                for (Zone zone : zones) {
                    if (!(x + size < zone.x || zone.x + zone.size < x || y + size < zone.y || zone.y + zone.size < y)) {
                        correct = false;
                        break;
                    }
                }
                if (correct) {
                    break;
                }
            }

            BossField bossField = new BossField(x, y);
            bossField.createRadiation();
            zones.add(bossField);
        }

        size = 500;
        for (int j = 0; j < 100; j++) {
            while (true) {
                x = random.nextInt(fieldX - size);
                y = random.nextInt(fieldY - size);
                boolean correct = true;
                for (Zone zone : zones) {
                    if (!(x + size < zone.x || zone.x + zone.size < x || y + size < zone.y || zone.y + zone.size < y)) {
                        correct = false;
                        break;
                    }
                }
                if (correct) {
                    break;
                }
            }

            Dungeon dungeon = new Dungeon(x, y);
            dungeon.createRadiation();
            zones.add(dungeon);
        }

        size = random.nextInt(100);
        if (size == 0) {
            size = 100;
        }
        size += 100;
        for (int i = 0; i < 2000; i++) {
            while (true) {
                x = random.nextInt(fieldX - size);
                y = random.nextInt(fieldY - size);
                boolean correct = true;
                for (Zone zone : zones) {
                    if (!(x + size < zone.x || zone.x + zone.size < x || y + size < zone.y || zone.y + zone.size < y)) {
                        correct = false;
                        break;
                    }
                }
                if (correct) {
                    break;
                }
            }

            zones.add(new Radiation(x, y, size));
        }

        size = 1;
        for (int i = 0; i < 9900; i++) {
            while (true) {
                x = random.nextInt(fieldX - size);
                y = random.nextInt(fieldY - size);
                boolean correct = true;
                for (Zone zone : zones) {
                    if (!(x + size < zone.x || zone.x + zone.size < x || y + size < zone.y || zone.y + zone.size < y)) {
                        correct = false;
                        break;
                    }
                }
                if (correct) {
                    break;
                }
            }

            Tree tree = new Tree(x, y);
            zones.add(tree);
        }

        for (int i = 0; i < 100; i++) {
            x = random.nextInt(zones.get(0).size - size);
            y = random.nextInt(zones.get(0).size - size);
            x += zones.get(0).x;
            y += zones.get(0).y;
            Tree tree = new Tree(x, y);
            treeZonesInsidePlayerZone.add(tree);
        }
    }

    private void drawZones(SpriteBatch batch, ArrayList<Zone> visibleZones) {
        int startX = Math.max(0, (int)(minX / cellSize));
        int endX = Math.min(fieldX / cellSize, (int)(maxX / cellSize) + 1);
        int startY = Math.max(0, (int)(minY / cellSize));
        int endY = Math.min(fieldY / cellSize, (int)(maxY / cellSize) + 1);

        for (int x = startX; x < endX; x++) {
            for (int y = startY; y < endY; y++) {
                float drawX = x * cellSize - minX;
                float drawY = y * cellSize - minY;
                boolean isNotDrawn = true;
                for (Zone zone : visibleZones) {
                    if (x < zone.x / cellSize + zone.size / cellSize &&
                        x + 1 > zone.x / cellSize &&
                        y < zone.y / cellSize + zone.size / cellSize &&
                        y + 1 > zone.y / cellSize) {

                        switch (zone.type) {
                            case ("Player"):
                                boolean treeIsNotDrawn = true;
                                for (Tree tree : treeZonesInsidePlayerZone) {
                                    if (x >= tree.x / cellSize &&
                                        x < tree.x / cellSize + tree.size / cellSize &&
                                        y >= tree.y / cellSize &&
                                        y < tree.y / cellSize + tree.size / cellSize) {

                                        batch.draw(treeTexture, drawX, drawY, cellSize, cellSize);
                                        treeIsNotDrawn = false;
                                        break;
                                    }
                                }
                                if (treeIsNotDrawn) {
                                    batch.draw(grassTexture, drawX, drawY, cellSize, cellSize);
                                }
                                break;
                            case ("Tree"):
                                batch.draw(treeTexture, drawX, drawY, cellSize, cellSize);
                                break;
                            case ("Radiation"):
                                batch.draw(radiationTexture, drawX, drawY, cellSize, cellSize);
                                break;
                            case ("Garden"):
                                batch.draw(gardenTexture, drawX, drawY, cellSize, cellSize);
                                break;
                            case ("BossField"):
                                batch.draw(bossFieldTexture, drawX, drawY, cellSize, cellSize);
                                break;
                            case ("Dungeon"):
                                batch.draw(dungeonTexture, drawX, drawY, cellSize, cellSize);
                                break;
                        }
                        isNotDrawn = false;
                        break;
                    }
                }
                if (isNotDrawn) {
                    batch.draw(grassTexture, drawX, drawY, cellSize, cellSize);
                }
            }
        }
    }

    private ArrayList<Zone> getZonesInView() {
        ArrayList<Zone> visibleZones = new ArrayList<>();
        for (Zone zone : zones) {
            if (zone.x + zone.size >= minX && zone.x <= maxX &&
                zone.y + zone.size >= minY && zone.y <= maxY) {
                visibleZones.add(zone);
            }
        }
        return visibleZones;
    }
}
