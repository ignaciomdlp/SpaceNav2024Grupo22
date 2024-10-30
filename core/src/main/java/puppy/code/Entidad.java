package puppy.code;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public abstract class Entidad {
    protected int x;
    protected int y;
    protected int xSpeed;
    protected int ySpeed;
    protected Sprite sprite;

    public Entidad(int x, int y, int size, int xSpeed, int ySpeed, Texture texture) {
        this.x = x;
        this.y = y;
        this.xSpeed = xSpeed;
        this.ySpeed = ySpeed;
        this.sprite = new Sprite(texture);
        this.sprite.setPosition(x, y);
    }
    
    public void update() {
        x += xSpeed;
        y += ySpeed;
        handleBorders();
        sprite.setPosition(x, y);
    }
    
    protected void handleBorders() {
        checkBorders();
    }
    
        // CAMBIO: AGREGAMOS MÉTODO DE CHEQUEO DE BORDES
    protected void checkBorders() {
        if (x < 0 || x + sprite.getWidth() > com.badlogic.gdx.Gdx.graphics.getWidth()) {
            onHorizontalBorderHit();
        }
        if (y < 0 || y + sprite.getHeight() > com.badlogic.gdx.Gdx.graphics.getHeight()) {
            onVerticalBorderHit();
        }
    }

    // CAMBIO: MÉTODOS ABSTRACTOS PARA REACCIONAR ANTE BORDES
    protected abstract void onHorizontalBorderHit();
    protected abstract void onVerticalBorderHit();


    //protected abstract void handleBorders();

    public Rectangle getArea() {
        return sprite.getBoundingRectangle();
    }

    public void draw(SpriteBatch batch) {
        sprite.draw(batch);
    }

    public int getXSpeed() {
        return xSpeed;
    }

    public int getYSpeed() {
        return ySpeed;
    }

    public void setXSpeed(int xSpeed) {
        this.xSpeed = xSpeed;
    }

    public void setYSpeed(int ySpeed) {
        this.ySpeed = ySpeed;
    }
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
        sprite.setX(x); // Actualiza la posición del sprite
    }

    public void setY(int y) {
        this.y = y;
        sprite.setY(y); // Actualiza la posición del sprite
    }
}

