package puppy.code;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.ScreenUtils;

public class MenuPausa implements Screen {
    private static MenuPausa instancia;
    private SpaceNavigation game;
    private OrthographicCamera camera;

    private MenuPausa(SpaceNavigation game) {
        this.game = game;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 1200, 800);
    }

    public static MenuPausa getInstancia(SpaceNavigation game) {
        if (instancia == null) {
            instancia = new MenuPausa(game);
        }
        return instancia;
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0.2f, 1);

        camera.update();
        game.getBatch().setProjectionMatrix(camera.combined);

        game.getBatch().begin();
        game.getFont().draw(game.getBatch(), "Menu de Pausa", 120, 400);
        game.getFont().draw(game.getBatch(), "[R] Reanudar  |  [S] Salir", 100, 300);
        game.getBatch().end();

        if (Gdx.input.isKeyJustPressed(Input.Keys.R)) {
            game.setScreen(game.getPantallaJuego());
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.S)) {
            Gdx.app.exit();  // Salir del juego
        }
    }

    @Override
    public void show() {}
    @Override
    public void resize(int width, int height) {}
    @Override
    public void pause() {}
    @Override
    public void resume() {}
    @Override
    public void hide() {}
    @Override
    public void dispose() {}
}