package end.team.center.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;
import java.util.Random;

import end.team.center.MyGame;

public class FieldScreen implements Screen {
    private static final int fieldX = 2000; // Размер поля по X
    private static final int fieldY = 2000; // Размер поля по Y
    private static final int cellSize = 1; // Размер клетки для отображения
    private final SpriteBatch batch = new SpriteBatch();
    private final Texture grassTexture = new Texture("grass.png");
    private final Texture radiationTexture = new Texture("radiation.png");
    private final Texture gardenTexture = new Texture("garden.png");
    private final Texture bossFieldTexture = new Texture("bossField.png");
    private final Texture dungeonTexture = new Texture("dungeon.png");
    Random globalRandom = new Random();
    ArrayList<Long> seeds = new ArrayList<>();
    ArrayList<Integer> sizes = new ArrayList<>();
    ArrayList<Character> symbols = new ArrayList<>();

    public FieldScreen(MyGame game) {

    }

    @Override
    public void show() {
        for (int i = 0; i < 10; i++) {
            seeds.add(globalRandom.nextLong());
        }
        sizes.add(200);
        sizes.add(400);
        sizes.add(500);
        symbols.add('@');
        symbols.add('S');
        symbols.add('#');
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();

        for (int i = 0; i < 10; i++) {
            long seed = seeds.get(i);
            generateAndDrawField(batch, seed, i);
        }

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

    }

    private void generateAndDrawField(SpriteBatch batch, long seed, int index) {
        Random localRandom = new Random(seed);
        int offsetX = index * fieldX * cellSize;

        char[][] field = new char[fieldX][fieldY];
        for (int x = 0; x < field.length; x++) {
            for (int y = 0; y < field.length; y++) {
                field[x][y] = '.';
            }
        }

        for (int i = 0; i < 200; i++) {
            int radiationWidth = localRandom.nextInt(50);
            int radiationHeight = localRandom.nextInt(50);
            if (radiationWidth == 0) {
                radiationWidth = 50;
            }
            if (radiationHeight == 0) {
                radiationHeight = 50;
            }
            radiationWidth += 50;
            radiationHeight += 50;

            int radiationX = localRandom.nextInt(fieldX - radiationWidth);
            int radiationY = localRandom.nextInt(fieldY - radiationHeight);

            for (int x = radiationX; x < radiationX + radiationWidth; x++) {
                for (int y = radiationY; y < radiationY + radiationHeight; y++) {
                    field[x][y] = '?';
                }
            }
        }

        for (int i = 0; i < 3; i++) {
            int structureX;
            int structureY;
            boolean incorrect;
            do {
                structureX = localRandom.nextInt(fieldX - sizes.get(i));
                structureY = localRandom.nextInt(fieldY - sizes.get(i));
                incorrect = false;
                for (int x = structureX; x < structureX + sizes.get(i); x++) {
                    for (int y = structureY; y < structureY + sizes.get(i); y++) {
                        if (field[x][y] != '.' && field[x][y] != '?') {
                            incorrect = true;
                            break;
                        }
                    }
                    if (incorrect) {
                        break;
                    }
                }
            } while (incorrect);
            for (int x = structureX; x < structureX + sizes.get(i); x++) {
                for(int y = structureY; y < structureY + sizes.get(i); y++) {
                    field[x][y] = symbols.get(i);
                }
            }
        }

        for (int x = 0; x < fieldX; x++) {
            for (int y = 0; y < fieldY; y++) {
                int drawX = offsetX + x * cellSize;
                int drawY = y * cellSize;
                if (field[x][y] == '.') {
                    batch.draw(grassTexture, drawX, drawY, cellSize, cellSize);
                }
                else if (field[x][y] == '?') {
                    batch.draw(radiationTexture, drawX, drawY, cellSize, cellSize);
                }
                else if (field[x][y] == '@') {
                    batch.draw(gardenTexture, drawX, drawY, cellSize, cellSize);
                }
                else if (field[x][y] == '$') {
                    batch.draw(bossFieldTexture, drawX, drawY, cellSize, cellSize);
                }
                else {
                    batch.draw(dungeonTexture, drawX, drawY, cellSize, cellSize);
                }
            }
        }
    }
}
