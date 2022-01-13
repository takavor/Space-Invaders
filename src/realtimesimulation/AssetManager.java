package realtimesimulation;

import java.io.File;
import java.util.ArrayList;
import java.util.Random;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.paint.ImagePattern;

public class AssetManager extends FXMLDocumentController {
    static private Background backgroundImage = null;
    static private ArrayList<ImagePattern> aliens = new ArrayList<>();
    static public ArrayList<ImagePattern> shields = new ArrayList<>();
    
    static private ImagePattern spaceshipImage = null;
    static private ImagePattern crossHairImage = null;
    static private ImagePattern bulletImage = null;
    static private ImagePattern deadAlienImage = null;
    static private ImagePattern projectileImage = null;
    static private ImagePattern shieldImage = null;
    static private ImagePattern shieldImage2 = null;
    
    static private Media backgroundMusic = null;
    static private AudioClip newPlanetSound = null;
    static private AudioClip shootingSound = null;
    static private AudioClip damageSound = null;

    public static AudioClip getDamageSound() {
        return damageSound;
    }

    public static void setDamageSound(AudioClip damageSound) {
        AssetManager.damageSound = damageSound;
    }
    
    
    static private String fileURL(String relativePath)
    {
        return new File(relativePath).toURI().toString();
    }
    
    static public void preloadAllAssets()
    {
        // Preload all images
        Image background = new Image(fileURL("./assets/images/bg.png"));
        backgroundImage = new Background(
                            new BackgroundImage(background, 
                                                BackgroundRepeat.NO_REPEAT, 
                                                BackgroundRepeat.NO_REPEAT, 
                                                BackgroundPosition.DEFAULT,
                                                BackgroundSize.DEFAULT));
        
        aliens.add(new ImagePattern(new Image(fileURL("./assets/images/orangealien.png"))));
        aliens.add(new ImagePattern(new Image(fileURL("./assets/images/redalien.png"))));
        aliens.add(new ImagePattern(new Image(fileURL("./assets/images/bluealien.png"))));
        
        
        spaceshipImage = new ImagePattern(new Image(fileURL("./assets/images/spaceship.png")));
        crossHairImage = new ImagePattern(new Image(fileURL("./assets/images/crosshair.png")));
        bulletImage = new ImagePattern(new Image(fileURL("./assets/images/bullet.png")));
        deadAlienImage = new ImagePattern(new Image(fileURL("./assets/images/deadalien.png")));
        projectileImage = new ImagePattern(new Image(fileURL("./assets/images/projectile.png")));
        shieldImage = new ImagePattern(new Image(fileURL("./assets/images/greenshield.png")));
        shieldImage2 = new ImagePattern(new Image(fileURL("./assets/images/greenshield2.png")));
        
        shields.add(shieldImage);
        shields.add(shieldImage2);
        
        // Preload all music tracks
        backgroundMusic = new Media(fileURL("./assets/music/backgroundMusic.mp3"));

        // Preload all sound effects
        newPlanetSound = new AudioClip(fileURL("./assets/soundfx/newPlanet.wav"));
        shootingSound =  new AudioClip(fileURL("./assets/soundfx/shooting.wav"));
        damageSound = new AudioClip(fileURL("./assets/soundfx/pop.wav"));
    }

    public static ArrayList<ImagePattern> getShields() {
        return shields;
    }

    public static ArrayList<ImagePattern> getAliens() {
        return aliens;
    }

    public static ImagePattern getShieldImage2() {
        return shieldImage2;
    }
    
    static public Background getBackgroundImage()
    {
        return backgroundImage;        
    }
    
    static public ImagePattern getBulletImage() {
        return bulletImage;
    }
    
    static public ImagePattern getShieldImage() {
        return shieldImage;
    }
    
    static public ImagePattern getRandomAlienImage()
    {
        Random rng = new Random();
        int i = rng.nextInt(aliens.size());
        return aliens.get(i);
    }
    
    static public Alien getRandomAlien() {
        Random rng = new Random();
        int i = rng.nextInt(alienList.size());
        return alienList.get(i);
    }
    
    static public ImagePattern getDeadAlien() {
        return deadAlienImage;
    }

    static public ImagePattern getSpaceshipImage()
    {
        return spaceshipImage;
    }
    
    static public AudioClip getShootingSound() {
        return shootingSound;
    }
    
    static public ImagePattern getCrossHairImage()
    {
        return crossHairImage;
    }
    
    static public ImagePattern getProjectileImage() {
        return projectileImage;
    }

    static public Media getBackgroundMusic()
    {
        return backgroundMusic;
    }
    
    static public AudioClip getNewPlanetSound()
    {
        return newPlanetSound;
    }
}
