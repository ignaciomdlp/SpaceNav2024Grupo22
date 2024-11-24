package puppy.code;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

public abstract class AbstractMenuScreen implements Screen {
    protected SpaceNavigation game;
    protected OrthographicCamera camera;
    protected SpriteBatch batch;
    
    public AbstractMenuScreen(SpaceNavigation game) {
        this.game = game;
        this.batch = game.getBatch();
        this.camera = new OrthographicCamera();
        camera.setToOrtho(false);
        initializeScreen();
    }
    
    // Template method que define la estructura base del renderizado
    @Override
    public void render(float delta) {
        // Limpia la pantalla
        clearScreen();
        
        // Actualiza la cámara
        camera.update();
        batch.setProjectionMatrix(camera.combined);
        
        // Comienza el batch
        batch.begin();
        
        // Dibuja el contenido específico del menú
        drawMenuContent();
        
        // Termina el batch
        batch.end();
        
        // Maneja los inputs
        handleInput();
    }
    
    // Métodos que deben ser implementados por las subclases
    protected abstract void drawMenuContent();
    protected abstract void handleInput();
    protected abstract void initializeScreen();
    
    // Método común para limpiar la pantalla
    protected void clearScreen() {
        ScreenUtils.clear(0, 0, 0.2f, 1);
    }
    
    // Implementaciones por defecto de los métodos de Screen
    @Override
    public void show() {
        // Implementación específica en las subclases si es necesario
    }
    
    @Override
    public void resize(int width, int height) {
        // Por defecto no hace nada, pero las subclases pueden sobrescribirlo
    }
    
    @Override
    public void pause() {
        // Por defecto no hace nada, pero las subclases pueden sobrescribirlo
    }
    
    @Override
    public void resume() {
        // Por defecto no hace nada, pero las subclases pueden sobrescribirlo
    }
    
    @Override
    public void hide() {
        // Por defecto no hace nada, pero las subclases pueden sobrescribirlo
    }
    
    @Override
    public void dispose() {
        // Por defecto no hace nada, pero las subclases pueden sobrescribirlo
    }
}