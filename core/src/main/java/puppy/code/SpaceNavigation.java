package puppy.code;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;


public class SpaceNavigation extends Game {
    private String nombreJuego = "Space Navigation";
    private SpriteBatch batch;
    private BitmapFont font;
    private int highScore;
    private PantallaJuego pantallaJuego;

    @Override
    public void create() {
            highScore = 0;
            batch = new SpriteBatch();
            font = new BitmapFont(); // usa Arial font x defecto
            font.getData().setScale(2f);
            setPantallaJuego(new PantallaJuego(this, 1, 3, 0, 1, 1, 10)); // Crear instancia inicial
    setScreen(new PantallaMenu(this));  // Mostrar menú principal
    }

    @Override
    public void render() {
            super.render(); // important!
    }

    @Override
    public void dispose() {
        batch.dispose();
        font.dispose();
    }

    public SpriteBatch getBatch() {
        return batch;
    }

    public BitmapFont getFont() {
        return font;
    }

    public int getHighScore() {
        return highScore;
    }

    public void setHighScore(int highScore) {
        this.highScore = highScore;
    }

    public PantallaJuego getPantallaJuego() {
        return pantallaJuego;
    }	

    public void setPantallaJuego(PantallaJuego pantallaJuego) {
        this.pantallaJuego = pantallaJuego;
    }
}