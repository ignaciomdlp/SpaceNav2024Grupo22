package puppy.code;

import com.badlogic.gdx.graphics.Texture;

public class DoubleShootStrategy implements ShootingStrategy {
    @Override
    public void shoot(float x, float y, PantallaJuego juego, Texture txBala) {
        Bullet bala1 = new Bullet(x - 10, y, 0, 3, txBala);
        Bullet bala2 = new Bullet(x + 10, y, 0, 3, txBala);
        juego.addBullet(bala1);
        juego.addBullet(bala2);
    }
}