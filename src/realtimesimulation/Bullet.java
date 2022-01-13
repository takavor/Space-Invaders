
package realtimesimulation;

public class Bullet extends GameObject{
    public Bullet(Vector2D position, Vector2D velocity, float radius)
    {
        super(position, velocity, new Vector2D(0,0), radius);
        circle.setFill(AssetManager.getBulletImage());
    }
    
    public static void nullify() {
        
        
        
    }
    
    
}
