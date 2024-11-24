package puppy.code;

import com.badlogic.gdx.graphics.Texture;

public interface ShootingStrategy {
    void shoot(float x, float y, PantallaJuego juego, Texture txBala);
}
