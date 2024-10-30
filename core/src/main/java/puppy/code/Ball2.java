package puppy.code;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class Ball2 extends Entidad implements Mover{
    public Ball2(int x, int y, int size, int xSpeed, int ySpeed, Texture tx) {
        super(x, y, size, xSpeed, ySpeed, tx);
    }

    @Override
    protected void onHorizontalBorderHit() {
        setXSpeed(xSpeed * (-1));
    }

    @Override
    protected void onVerticalBorderHit() {
        setYSpeed(ySpeed * (-1));
    }
 
    public boolean checkCollision(Ball2 other) {
        return this.getArea().overlaps(other.getArea());
    }

    @Override
    public void moverArriba() {
        setYSpeed(Math.abs(getYSpeed()));
    }

    @Override
    public void moverAbajo() {
        setYSpeed(-Math.abs(getYSpeed()));
    }

    @Override
    public void moverIzquierda() {
        setXSpeed(-Math.abs(getXSpeed()));
    }

    @Override
    public void moverDerecha() {
        setXSpeed(Math.abs(getXSpeed()));
    }
}
