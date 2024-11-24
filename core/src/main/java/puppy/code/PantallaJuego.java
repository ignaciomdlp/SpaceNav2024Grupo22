package puppy.code;

import java.util.ArrayList;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class PantallaJuego implements Screen {
    private SpaceNavigation game;
    private OrthographicCamera camera;
    private SpriteBatch batch;
    private Sound explosionSound;
    private Music gameMusic;
    private int score;
    private int round;
    private int asteroidXSpeed;
    private int asteroidYSpeed;
    private int asteroidCount;

    private Nave4 ship;
    private ArrayList<Ball2> balls1 = new ArrayList<>();
    private ArrayList<Ball2> balls2 = new ArrayList<>();
    private ArrayList<Bullet> bullets = new ArrayList<>();

    public PantallaJuego(SpaceNavigation game, int round, int lives, int score,
        int asteroidXSpeed, int asteroidYSpeed, int asteroidCount) {
        this.game = game;
        this.round = round;
        this.score = score;
        this.asteroidXSpeed = asteroidXSpeed;
        this.asteroidYSpeed = asteroidYSpeed;
        this.asteroidCount = asteroidCount;
        game.setPantallaJuego(this);  // Registrar esta instancia como la actual

        batch = game.getBatch();
        camera = new OrthographicCamera();
        camera.setToOrtho(false);

        explosionSound = Gdx.audio.newSound(Gdx.files.internal("explosion.ogg"));
        explosionSound.setVolume(1, 0.0001f);
        gameMusic = Gdx.audio.newMusic(Gdx.files.internal("musiquita.mp3"));
        gameMusic.setLooping(true);
        gameMusic.setVolume(0.9f);


        ship = new Nave4(Gdx.graphics.getWidth() / 2 - 50, 30,
                new Texture(Gdx.files.internal("MainShip3.png")),
                Gdx.audio.newSound(Gdx.files.internal("hurt.ogg")),
                new Texture(Gdx.files.internal("Rocket2.png")),
                Gdx.audio.newSound(Gdx.files.internal("pop-sound.mp3")));
        ship.setLives(lives);

        Random r = new Random();
        for (int i = 0; i < asteroidCount; i++) {
            Ball2 asteroid = new Ball2(r.nextInt((int) Gdx.graphics.getWidth()),
                    50 + r.nextInt((int) Gdx.graphics.getHeight() - 50),
                    20 + r.nextInt(10), asteroidXSpeed + r.nextInt(4), asteroidYSpeed + r.nextInt(4),
                    new Texture(Gdx.files.internal("aGreyMedium4.png")));
            balls1.add(asteroid);
            balls2.add(asteroid);
        }
    }

    private void drawHeader() {
        CharSequence str = "Lives: " + ship.getLives() + " Round: " + round;
        game.getFont().getData().setScale(2f);
        game.getFont().draw(batch, str, 10, 30);
        game.getFont().draw(batch, "Score: " + score, Gdx.graphics.getWidth() - 150, 30);
        game.getFont().draw(batch, "HighScore: " + game.getHighScore(), Gdx.graphics.getWidth() / 2 - 100, 30);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        drawHeader();

        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            game.setScreen(MenuPausa.getInstancia(game));  // Cambiar al menú de pausa (patron singleton)
        }

        if (!ship.isInjured()) {
            // Handle bullet collisions with asteroids
            for (int i = 0; i < bullets.size(); i++) {
                Bullet bullet = bullets.get(i);
                bullet.update();
                for (int j = 0; j < balls1.size(); j++) {
                    if (bullet.checkCollision(balls1.get(j))) {
                        explosionSound.play();
                        balls1.remove(j);
                        balls2.remove(j);
                        j--;
                        score += 10;
                    }
                }
                if (bullet.isDestroyed()) {
                    bullets.remove(bullet);
                    i--;
                }
            }

            // Update asteroid movement
            for (Ball2 asteroid : balls1) {
                asteroid.update();
            }

            // Handle asteroid collisions
            for (int i = 0; i < balls1.size(); i++) {
                Ball2 a1 = balls1.get(i);
                for (int j = i + 1; j < balls2.size(); j++) {
                    Ball2 a2 = balls2.get(j);
                    a1.checkCollision(a2);
                }
            }
        }

        // Draw bullets
        for (Bullet bullet : bullets) {
            bullet.draw(batch);
        }

        ship.draw(batch, this);

        // Draw and handle collisions with asteroids
        for (int i = 0; i < balls1.size(); i++) {
            Ball2 asteroid = balls1.get(i);
            asteroid.draw(batch);
            if (ship.checkCollision(asteroid)) {
                balls1.remove(i);
                balls2.remove(i);
                i--;
            }
        }

        if (ship.isDestroyed()) {
            if (score > game.getHighScore()) {
                game.setHighScore(score);
            }
            Screen gameOverScreen = new PantallaGameOver(game);
            //gameOverScreen.resize(1200, 800);
            game.setScreen(gameOverScreen);
            dispose();
        }

        batch.end();

        // Check for level completion
        if (balls1.isEmpty()) {
            Screen nextLevelScreen = new PantallaJuego(game, round + 1, ship.getLives(), score,
                    asteroidXSpeed + 3, asteroidYSpeed + 3, asteroidCount + 10);
            //nextLevelScreen.resize(1200, 800);
            game.setScreen(nextLevelScreen);
            dispose();
        }
    }

    public boolean addBullet(Bullet bullet) {
        return bullets.add(bullet);
    }
    public Music getGameMusic() {
        return gameMusic;
    }

    @Override
    public void show() {
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
        explosionSound.dispose();
        gameMusic.dispose();
    }
}