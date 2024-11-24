package puppy.code;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;

public class TripleShootStrategy implements ShootingStrategy {
    @Override
    public void shoot(float x, float y, PantallaJuego juego, Texture txBala) {
        // Bala central
        juego.addBullet(new Bullet(x, y, 0, 3, txBala));
        
        // Balas laterales (20 grados)
        float angleLeft = -20f;
        float angleRight = 20f;
        
        int velocidad = 3;
        
        // Bala izquierda
        juego.addBullet(new Bullet(x, y, 
            (int)(velocidad * MathUtils.sin(angleLeft)), 
            (int)(velocidad * MathUtils.cos(angleLeft)), 
            txBala, angleLeft));
            
        // Bala derecha
        juego.addBullet(new Bullet(x, y, 
            (int)(velocidad * MathUtils.sin(angleRight)), 
            (int)(velocidad * MathUtils.cos(angleRight)), 
            txBala, angleRight));
    }
}