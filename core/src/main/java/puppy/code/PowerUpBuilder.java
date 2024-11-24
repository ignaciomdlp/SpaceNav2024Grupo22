package puppy.code;

public class PowerUpBuilder {
    private Nave4 nave;
    private int score;
    
    public PowerUpBuilder(Nave4 nave, int score) {
        this.nave = nave;
        this.score = score;
    }
    
    public void buildPowerUps() {
        // Power-ups basados en el puntaje
        if (score >= 450) {
            nave.setShootingStrategy(new TripleShootStrategy());
            nave.setSpeedMultiplier(2.0f);
        } else if (score >= 200) {
            nave.setShootingStrategy(new DoubleShootStrategy());
            nave.setSpeedMultiplier(1.5f);
        } else {
            nave.setShootingStrategy(new SimpleShootStrategy());
            nave.setSpeedMultiplier(1.0f);
        }
    }
}
