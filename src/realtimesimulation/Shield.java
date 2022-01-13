
package realtimesimulation;


public class Shield extends GameObject{
    
    static int hitCount = 0;
    
    public Shield(Vector2D position, Vector2D velocity, float radius)
    {
        super(position, velocity, new Vector2D(0,0), radius);
        circle.setFill(AssetManager.getShieldImage());
    }
    
    public int getHitCount() {
        return hitCount;
    }
    
    public void setHitCount(int i) {
        hitCount = i;
    }
    
}
