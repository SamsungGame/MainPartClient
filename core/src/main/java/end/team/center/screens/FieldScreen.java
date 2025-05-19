package end.team.center.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;
import java.util.Random;

import end.team.center.Zone;
import end.team.center.MyGame;

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
    private final Texture grassTexture = new Texture("grass.png");
    private final Texture radiationTexture = new Texture("radiation.png");
    private final Texture gardenTexture = new Texture("garden.png");
    private final Texture bossFieldTexture = new Texture("bossField.png");
    private final Texture dungeonTexture = new Texture("dungeon.png");
    ArrayList<Integer> sizes = new ArrayList<>();
    ArrayList<Character> symbols = new ArrayList<>();
    ArrayList<Zone> zones = new ArrayList<>();

    public FieldScreen(MyGame game) {

    }

    @Override
    public void show() {
        sizes.add(200);
        sizes.add(400);
        sizes.add(500);
        symbols.add('@');
        symbols.add('$');
        symbols.add('#');
        zones.add(new Zone(0, 0, 0, '0'));
        generateZones();
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

        batch.begin();

        drawZones(batch);

        batch.end();
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
        radiationTexture.dispose();
        gardenTexture.dispose();
        bossFieldTexture.dispose();
        dungeonTexture.dispose();
    }

    private void generateZones() {
        Random random = new Random();
        for (int i = 0; i < sizes.size(); i++) {
            for (int j = 0; j < 100; j++) {
                int size = sizes.get(i);
                char symbol = symbols.get(i);
                int x;
                int y;
                while (true) {
                    x = random.nextInt(fieldX - size);
                    y = random.nextInt(fieldY - size);
                    boolean correct = true;
                    for (Zone zone : zones) {
                        if (x < zone.x + zone.size && x + size > zone.x && y < zone.y + zone.size && y + size > zone.y) {
                            correct = false;
                            break;
                        }
                    }
                    if (correct) {
                        break;
                    }
                }
                zones.add(new Zone(x, y, size, symbol));
            }
        }

        for (int i = 0; i < 200; i++) {
            int size = random.nextInt(50);
            if (size == 0) {
                size = 50;
            }
            size += 50;
            char symbol = '?';
            int x;
            int y;
            while (true) {
                x = random.nextInt(fieldX - size);
                y = random.nextInt(fieldY - size);
                boolean correct = true;
                for (Zone zone : zones) {
                    if (x < zone.x + zone.size && x + size > zone.x && y < zone.y + zone.size && y + size > zone.y) {
                        correct = false;
                        break;
                    }
                }
                if (correct) {
                    break;
                }
            }
            zones.add(new Zone(x, y, size, symbol));
        }
    }

    private void drawZones(SpriteBatch batch) {
        int startX = Math.max(0, (int)(minX / cellSize));
        int endX = Math.min(fieldX / cellSize, (int)(maxX / cellSize) + 1);
        int startY = Math.max(0, (int)(minY / cellSize));
        int endY = Math.min(fieldY / cellSize, (int)(maxY / cellSize) + 1);

        for (int x = startX; x < endX; x++) {
            for (int y = startY; y < endY; y++) {
                float drawX = x * cellSize - minX;
                float drawY = y * cellSize - minY;
                boolean isNotDrawn = true;
                for (Zone zone : zones) {
                    if (x < zone.x / cellSize + zone.size / cellSize &&
                        x + 1 > zone.x / cellSize &&
                        y < zone.y / cellSize + zone.size / cellSize &&
                        y + 1 > zone.y / cellSize) {

                        switch (zone.symbol) {
                            case ('?'):
                                batch.draw(radiationTexture, drawX, drawY, cellSize, cellSize);
                                break;
                            case ('@'):
                                batch.draw(gardenTexture, drawX, drawY, cellSize, cellSize);
                                break;
                            case ('$'):
                                batch.draw(bossFieldTexture, drawX, drawY, cellSize, cellSize);
                                break;
                            case ('#'):
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
}
