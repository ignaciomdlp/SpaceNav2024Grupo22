package puppy.code;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;

public class Nave4 extends Entidad implements Mover {
    private boolean destroyed = false, injured = false;
    private int lives = 3, injuredTime, injuredTimeMax = 50;
    private Sound injuredSound, shootSound;
    private Texture bulletTex;
    private static final int SPEED = 3;
    private ShootingStrategy shootingStrategy;

    public Nave4(int x, int y, Texture tex, Sound injuredSound, Texture bulletTex, Sound shootSound) {
        super(x, y, 45, 0, 0, tex);
        this.injuredSound = injuredSound;
        this.shootSound = shootSound;
        this.bulletTex = bulletTex;
        //this.shootingStrategy = new SimpleShootStrategy();
        this.shootingStrategy = new TripleShootStrategy();
    }

    public void setShootingStrategy(ShootingStrategy strategy) {
        this.shootingStrategy = strategy;
    }

    @Override
    public void moverArriba() { setYSpeed(SPEED); }
    @Override
    public void moverAbajo() { setYSpeed(-SPEED); }
    @Override
    public void moverIzquierda() { setXSpeed(-SPEED); }
    @Override
    public void moverDerecha() { setXSpeed(SPEED); }

    @Override
    protected void handleBorders() {
        if (x < 0) x = 0; 
        else if (x + sprite.getWidth() > Gdx.graphics.getWidth()) x = Gdx.graphics.getWidth() - (int)sprite.getWidth();
        if (y < 0) y = 0;
        else if (y + sprite.getHeight() > Gdx.graphics.getHeight()) y = Gdx.graphics.getHeight() - (int)sprite.getHeight();
    }

    @Override
    protected void onHorizontalBorderHit() { setXSpeed(0); }
    @Override
    protected void onVerticalBorderHit() { setYSpeed(0); }

    public void draw(SpriteBatch batch, PantallaJuego game) {
        if (!injured) {
            setXSpeed(0); setYSpeed(0);
            handleMovement();
            super.update();
            draw(batch);
        } else {
            setX(getX() + MathUtils.random(-2, 2));
            sprite.draw(batch);
            injuredTime--;
            if (injuredTime <= 0) injured = false;
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            shootingStrategy.shoot(getX() + sprite.getWidth() / 2 - 5, getY() + sprite.getHeight() - 5, game, bulletTex);
            shootSound.play();
        }
    }

    private void handleMovement() {
        if (Gdx.input.isKeyPressed(Input.Keys.A)) moverIzquierda();
        if (Gdx.input.isKeyPressed(Input.Keys.D)) moverDerecha();
        if (Gdx.input.isKeyPressed(Input.Keys.S)) moverAbajo();
        if (Gdx.input.isKeyPressed(Input.Keys.W)) moverArriba();
    }

    public boolean checkCollision(Ball2 b) {
        if (!injured && b.getArea().overlaps(getArea())) {
            handleBounce(b);
            if (lives > 0) lives--;
            injured = true;
            injuredTime = injuredTimeMax;
            injuredSound.play();
            if (lives <= 0) destroyed = true;
            return true;
        }
        return false;
    }

    private void handleBounce(Ball2 b) {
        if (getXSpeed() == 0) setXSpeed(b.getXSpeed() / 2);
        else setXSpeed(-getXSpeed());
        if (getYSpeed() == 0) setYSpeed(b.getYSpeed() / 2);
        else setYSpeed(-getYSpeed());
        if (b.getXSpeed() == 0) b.setXSpeed(getXSpeed() / 2);
        else b.setXSpeed(-b.getXSpeed());
        if (b.getYSpeed() == 0) b.setYSpeed(getYSpeed() / 2);
        else b.setYSpeed(-b.getYSpeed());
    }

    public boolean isDestroyed() { return destroyed; }
    public boolean isInjured() { return injured; }
    public int getLives() { return lives; }
    public void setLives(int lives) { this.lives = lives; }
}