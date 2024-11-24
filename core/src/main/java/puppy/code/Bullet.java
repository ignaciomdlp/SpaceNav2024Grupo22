package puppy.code;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Bullet {
    private int xSpeed, ySpeed;
    private boolean destroyed = false;
    private Sprite sprite;
    private float rotation;

    public Bullet(float x, float y, int xSpeed, int ySpeed, Texture tex) {
        sprite = new Sprite(tex);
        sprite.setPosition(x, y);
        this.xSpeed = xSpeed;
        this.ySpeed = ySpeed;
        this.rotation = 0;
    }
    
    public Bullet(float x, float y, int xSpeed, int ySpeed, Texture tex, float rotation) {
        sprite = new Sprite(tex);
        sprite.setPosition(x, y);
        this.xSpeed = xSpeed;
        this.ySpeed = ySpeed;
        this.rotation = -rotation;
        sprite.setRotation(rotation);
    }   
    
    public void update() {
        sprite.setPosition(sprite.getX() + xSpeed, sprite.getY() + ySpeed);
        if (sprite.getX() < 0 || sprite.getX() + sprite.getWidth() > Gdx.graphics.getWidth()
            || sprite.getY() < 0 || sprite.getY() + sprite.getHeight() > Gdx.graphics.getHeight()) {
            destroyed = true;
        }
    }
    
    public void draw(SpriteBatch batch) {
        sprite.setRotation(rotation); // Asegúrate de que la rotación esté aplicada
        sprite.draw(batch);
    }

    public boolean checkCollision(Ball2 b2) {
        if (sprite.getBoundingRectangle().overlaps(b2.getArea())) {
            destroyed = true;
            return true;
        }
        return false;
    }

    public boolean isDestroyed() { return destroyed; }
}