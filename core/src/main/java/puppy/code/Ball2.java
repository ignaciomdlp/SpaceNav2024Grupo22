package puppy.code;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class Ball2 extends Entidad {
    public Ball2(int x, int y, int size, int xSpeed, int ySpeed, Texture tx) {
        super(x, y, size, xSpeed, ySpeed, tx);
    }

    @Override
    protected void handleBorders() {
        if (x - sprite.getWidth() / 2 < 0 || x + sprite.getWidth() / 2 > Gdx.graphics.getWidth()) {
            xSpeed *= -1;
        }
        if (y - sprite.getHeight() / 2 < 0 || y + sprite.getHeight() / 2 > Gdx.graphics.getHeight()) {
            ySpeed *= -1;
        }
    }

    public boolean checkCollision(Ball2 other) {
    return this.getArea().overlaps(other.getArea());
    }
}
