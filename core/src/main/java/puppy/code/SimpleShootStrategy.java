package puppy.code;

import com.badlogic.gdx.graphics.Texture;

public class SimpleShootStrategy implements ShootingStrategy {
    @Override
    public void shoot(float x, float y, PantallaJuego juego, Texture txBala) {
        Bullet bala = new Bullet(x, y, 0, 3, txBala);
        juego.addBullet(bala);
    }
}