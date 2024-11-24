package puppy.code;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

public class PantallaGameOver extends AbstractMenuScreen {
    
    public PantallaGameOver(SpaceNavigation game) {
        super(game);
    }
    
    @Override
    protected void drawMenuContent() {
        game.getFont().draw(batch, "Game Over !!! ", 120, 400, 400, 1, true);
        game.getFont().draw(batch, "Pincha en cualquier lado para reiniciar ...", 100, 300);
    }
    
    @Override
    protected void handleInput() {
        if (Gdx.input.isTouched() || Gdx.input.isKeyJustPressed(Input.Keys.ANY_KEY)) {
            game.setScreen(new PantallaJuego(game, 1, 3, 0, 1, 1, 10));
            dispose();
        }
    }
    
    @Override
    protected void initializeScreen() {
        camera.setToOrtho(false);
    }
}
