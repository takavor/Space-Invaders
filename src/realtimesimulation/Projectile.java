
package realtimesimulation;


public class Projectile extends GameObject{
    public Projectile(Vector2D position, Vector2D velocity, float radius)
    {
        super(position, velocity, new Vector2D(0,0), radius);
        circle.setFill(AssetManager.getProjectileImage());
    }
    
    public static void nullify() {
        
        
        
    }
    
    
}
