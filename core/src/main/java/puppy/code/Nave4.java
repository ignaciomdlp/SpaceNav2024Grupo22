package puppy.code;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;

public class Nave4 extends Entidad {
    private boolean destruida = false;
    private int vidas = 3;
    private boolean herido = false;
    private int tiempoHeridoMax = 50;
    private int tiempoHerido;
    private Sound sonidoHerido;
    private Sound soundBala;
    private Texture txBala;

    public Nave4(int x, int y, Texture tx, Sound soundChoque, Texture txBala, Sound soundBala) {
        super(x, y, 45, 0, 0, tx);
        this.sonidoHerido = soundChoque;
        this.soundBala = soundBala;
        this.txBala = txBala;
    }

    @Override
    protected void handleBorders() {
        if (x + xSpeed < 0 || x + xSpeed + sprite.getWidth() > Gdx.graphics.getWidth()) {
            xSpeed *= -1;
        }
        if (y + ySpeed < 0 || y + ySpeed + sprite.getHeight() > Gdx.graphics.getHeight()) {
            ySpeed *= -1;
        }
    }

    public void draw(SpriteBatch batch, PantallaJuego juego) {
        if (!herido) {
            // Control de movimiento con teclado
            if (Gdx.input.isKeyJustPressed(Input.Keys.LEFT)) setXSpeed(getXSpeed() - 1);
            if (Gdx.input.isKeyJustPressed(Input.Keys.RIGHT)) setXSpeed(getXSpeed() + 1);
            if (Gdx.input.isKeyJustPressed(Input.Keys.DOWN)) setYSpeed(getYSpeed() - 1);
            if (Gdx.input.isKeyJustPressed(Input.Keys.UP)) setYSpeed(getYSpeed() + 1);

            super.update();
            draw(batch);
        } else {
            // Efecto de daño
            setX(getX() + MathUtils.random(-2, 2));
            sprite.draw(batch);
            tiempoHerido--;
            if (tiempoHerido <= 0) herido = false;
        }

        // Disparo
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            Bullet bala = new Bullet(getX() + sprite.getWidth() / 2 - 5, getY() + sprite.getHeight() - 5, 0, 3, txBala);
            juego.agregarBala(bala);
            sonidoHerido.play();  // Asumiendo que el sonido de disparo se almacena en sonidoHerido
        }
    }

    public boolean checkCollision(Ball2 b) {
        if (!herido && b.getArea().overlaps(getArea())) {
            // Rebote
            if (getXSpeed() == 0) setXSpeed(b.getXSpeed() / 2);
            if (b.getXSpeed() == 0) b.setXSpeed(getXSpeed() / 2);
            setXSpeed(-getXSpeed());
            b.setXSpeed(-b.getXSpeed());

            if (getYSpeed() == 0) setYSpeed(b.getYSpeed() / 2);
            if (b.getYSpeed() == 0) b.setYSpeed(getYSpeed() / 2);
            setYSpeed(-getYSpeed());
            b.setYSpeed(-b.getYSpeed());

            // Actualizar vidas y estado de daño
            if (vidas > 0) {
                vidas--;
            }
            herido = true;
            tiempoHerido = tiempoHeridoMax;
            sonidoHerido.play();
            if (vidas <= 0) {
                destruida = true;
            }
            return true;
        }
        return false;
    }
    public boolean estaDestruido() {
        return destruida;
    }

    public boolean estaHerido() {
        return herido;
    }

    public int getVidas() {
        return vidas;
    }

    public void setVidas(int vidas2) {
        vidas = vidas2;
    }
}