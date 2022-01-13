
package realtimesimulation;

public class Alien extends GameObject {
    
    static boolean canShoot = true;
    
    public Alien(Vector2D position, Vector2D velocity, float radius)
    {
        super(position, velocity, new Vector2D(0,0), radius);
        circle.setFill(AssetManager.getRandomAlienImage());
    }
    
    public static boolean getCanShoot() {
        return canShoot;
    }
    
    public static void setCanShoot(boolean b) {
        canShoot = b;
    }
    
}
