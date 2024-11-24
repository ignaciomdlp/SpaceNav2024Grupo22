package puppy.code;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

public class MenuPausa extends AbstractMenuScreen {
    private static MenuPausa instancia;
    
    private MenuPausa(SpaceNavigation game) {
        super(game);
    }
    
    public static MenuPausa getInstancia(SpaceNavigation game) {
        if (instancia == null) {
            instancia = new MenuPausa(game);
        }
        return instancia;
    }
    
    @Override
    protected void drawMenuContent() {
        game.getFont().draw(batch, "Menu de Pausa", 120, 400);
        game.getFont().draw(batch, "[R] Reanudar  |  [S] Salir", 100, 300);
    }
    
    @Override
    protected void handleInput() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.R)) {
            game.setScreen(game.getPantallaJuego());
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.S)) {
            Gdx.app.exit();
        }
    }
    
    @Override
    protected void initializeScreen() {
        camera.setToOrtho(false);
    }
}