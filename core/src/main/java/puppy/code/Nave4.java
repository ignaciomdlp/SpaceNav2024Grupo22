package puppy.code;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;

public class Nave4 extends Entidad implements Mover{
    private boolean destruida = false;
    private int vidas = 3;
    private boolean herido = false;
    private int tiempoHeridoMax = 50;
    private int tiempoHerido;
    private Sound sonidoHerido;
    private Sound soundBala;
    private Texture txBala;
    private static final int constantVel = 3;

    public Nave4(int x, int y, Texture tx, Sound soundChoque, Texture txBala, Sound soundBala) {
        super(x, y, 45, 0, 0, tx);
        this.sonidoHerido = soundChoque;
        this.soundBala = soundBala;
        this.txBala = txBala;
    }

    @Override
    public void moverArriba() {
        setYSpeed(constantVel);
    }
    
    @Override
    public void moverAbajo() {
        setYSpeed(-constantVel);
    }
    
    @Override
    public void moverIzquierda() {
        setXSpeed(-constantVel);
    }
    
    @Override
    public void moverDerecha() {
        setXSpeed(constantVel);
    }
    
    @Override
    protected void handleBorders() {
    // VERIFICA QUE LA NAVE NO SE SALGA DE LOS BORDES DE LA PANTALLA
        if (x < 0) {
            x = 0; // AJUSTE A LÍMITE IZQUIERDO
        } else if (x + sprite.getWidth() > Gdx.graphics.getWidth()) {
            x = (int) (Gdx.graphics.getWidth() - sprite.getWidth()); // AJUSTE A LÍMITE DERECHO
        }

        if (y < 0) {
            y = 0; // AJUSTE A LÍMITE INFERIOR
        } else if (y + sprite.getHeight() > Gdx.graphics.getHeight()) {
            y = (int) (Gdx.graphics.getHeight() - sprite.getHeight()); // AJUSTE A LÍMITE SUPERIOR
        }
    }
    
    // CAMBIO: IMPLEMENTACIÓN DE REACCIÓN ANTE BORDES
    @Override
    protected void onHorizontalBorderHit() {
        setXSpeed(0); // DETIENE EL MOVIMIENTO HORIZONTAL AL TOCAR EL BORDE
    }

    @Override
    protected void onVerticalBorderHit() {
        setYSpeed(0); // DETIENE EL MOVIMIENTO VERTICAL AL TOCAR EL BORDE
    }

    public void draw(SpriteBatch batch, PantallaJuego juego) {
        if (!herido) {
            // Control de movimiento con WASD
            setXSpeed(0);
            setYSpeed(0);

            if (Gdx.input.isKeyPressed(Input.Keys.A)) moverIzquierda();
            if (Gdx.input.isKeyPressed(Input.Keys.D)) moverDerecha();
            if (Gdx.input.isKeyPressed(Input.Keys.S)) moverAbajo();
            if (Gdx.input.isKeyPressed(Input.Keys.W)) moverArriba();

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