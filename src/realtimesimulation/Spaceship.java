package realtimesimulation;


public class Spaceship extends GameObject{
    
    private float rotationSpeed = 0.0f;
    
    
    public Spaceship(Vector2D position)
    {
        super(position, new Vector2D(0,0), new Vector2D(0,0), 20);
        this.rotationSpeed = rotationSpeed;
        circle.setFill(AssetManager.getSpaceshipImage());
    }
    
    @Override
    public void update(double dt)
    {
        super.update(dt);

        
    }
    
}
