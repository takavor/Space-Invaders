
package realtimesimulation;

import java.net.URL;
import java.util.ArrayList;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;
import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.AudioClip;
import javafx.scene.shape.Circle;

public class FXMLDocumentController implements Initializable {

    @FXML
    AnchorPane pane;

    @FXML
    Label centerLabel;

    @FXML
    Label scoreLabel;

    @FXML
    Label livesLabel;

    @FXML
    Button startButton;

    private double lastFrameTime = 0.0;
    static int score = 0;
    static int lives = 3;
    static double lastAlienY = 0;
    static int amountKilled = 0;
    static int shield1Damage = 0;
    static int shield2Damage = 0;

    private ArrayList<GameObject> objectList = new ArrayList<>();
    private Spaceship spaceship = null;
    private Shield shield1 = null;
    private Shield shield2 = null;
    private static boolean isPlayerHit = false;

    private Alien firstAlien = null;
    private Alien lastAlien = null;
    public static ArrayList<Alien> alienList = new ArrayList<>();
    public static ArrayList<Alien> shootingAliensList = new ArrayList<>();

    public void addToPane(Node node) {
        pane.getChildren().add(node);
    }

    @FXML
    private void onKeyPressed(KeyEvent e) {
        System.out.println("Key pressed: " + e.getCode());
    }

    @FXML
    private void onKeyReleased(KeyEvent e) {
        System.out.println("Key released: " + e.getCode());
    }

    public void generateProjectile() {

        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> {

                    if (isPlayerHit == true && score == 24) {

                    } else if (isPlayerHit == false && score != 24) {

                        Random rng = new Random();
                        int i = rng.nextInt(shootingAliensList.size());

                        Alien randomAlien = shootingAliensList.get(i);

                        double x = randomAlien.getCircle().getCenterX();
                        double y = randomAlien.getCircle().getCenterY();

                        Projectile projectile = new Projectile(new Vector2D(x, y), new Vector2D(0, 60), 10);

                        addToPane(projectile.getCircle());
                        objectList.add(projectile);
                    }

                });
            }
        }, 3000, 3000);

    }

    @FXML
    private void onMouseMoved(MouseEvent e) {
        if (score != 24 && isPlayerHit == false) {
            spaceship.setPosition(new Vector2D(e.getX(), 500)); // Y IS ARBITRARY VALUE
        }
    }

    @FXML
    private void onMouseClicked(MouseEvent e) {
        if (score != 24 && isPlayerHit == false) {
            final float BULLET_SPEED = 300;

            Vector2D position = spaceship.getPosition();
            Vector2D mousePosition = new Vector2D(e.getX(), e.getY());
            Vector2D velocity = mousePosition.sub(position);
            velocity.normalize();
            velocity.setX(0); // no horizontal movement
            velocity.setY(-Math.abs(velocity.getY()));
            velocity = velocity.mult(BULLET_SPEED);

            Bullet bullet = new Bullet(position, velocity, 10);
            bullet.setPosition(new Vector2D(e.getX(), 472));

            addToPane(bullet.getCircle());
            objectList.add(bullet);

            AudioClip shootingSound = AssetManager.getShootingSound();
            shootingSound.play();

        }

    }

    private void updateAlienLevel() {
        for (Alien alien : alienList) {

            alien.setVelocity(alien.getVelocity().mult(-1));
            alien.setPosition(new Vector2D(alien.getPosition().getX(), alien.getPosition().getY() + 10));
        }
    }

    public void updateLives() {
        --lives;
        livesLabel.setText("LIVES: " + lives);
    }

    public void updateShieldImage() {

        if(shield1Damage < 2) {
            shield1.getCircle().setFill( AssetManager.shields.get(shield1Damage) );
        } else {
            pane.getChildren().remove(shield1.getCircle());
            objectList.remove(shield1);
        }
        
        if(shield2Damage < 2) {
            shield2.getCircle().setFill( AssetManager.shields.get(shield2Damage) );
        } else {
            pane.getChildren().remove(shield2.getCircle());
            objectList.remove(shield2);
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        lastFrameTime = 0.0f;
        long initialTime = System.nanoTime();

        AssetManager.preloadAllAssets();

        spaceship = new Spaceship(new Vector2D(350, 500));
        
        shield1 = new Shield(new Vector2D(150, 400), new Vector2D(0, 0), 30);
        shield2 = new Shield(new Vector2D(550, 400), new Vector2D(0, 0), 30);
        
        objectList.add(shield1);
        objectList.add(shield2);

        addToPane(shield1.getCircle());
        addToPane(shield2.getCircle());

        // cursor = new Crosshair();
        for (int i = 0; i < 8; i++) {

            Alien alien = new Alien(new Vector2D(100 + 30 * i, 100), new Vector2D(60, 0), 15);

            if (i == 0) {
                firstAlien = alien;
            }

            addToPane(alien.getCircle());
            objectList.add(alien);
            alienList.add(alien);
            shootingAliensList.add(alien);

        }

        for (int i = 0; i < 8; i++) {

            Alien alien = new Alien(new Vector2D(100 + 30 * i, 130), new Vector2D(60, 0), 15);
            addToPane(alien.getCircle());
            objectList.add(alien);
            alienList.add(alien);
            shootingAliensList.add(alien);
        }

        for (int i = 0; i < 8; i++) {

            Alien alien = new Alien(new Vector2D(100 + 30 * i, 160), new Vector2D(60, 0), 15);

            if (i == 12) {
                lastAlien = alien;
                lastAlienY = lastAlien.getCircle().getCenterY();
            }

            addToPane(alien.getCircle());
            objectList.add(alien);
            alienList.add(alien);
            shootingAliensList.add(alien);

        }

        generateProjectile();

        pane.setBackground(AssetManager.getBackgroundImage());

        addToPane(spaceship.getCircle());
        objectList.add(spaceship);

        new AnimationTimer() {
            @Override
            public void handle(long now) {
                // Time calculation                

                double currentTime = (now - initialTime) / 1000000000.0;
                double frameDeltaTime = currentTime - lastFrameTime;
                lastFrameTime = currentTime;

                for (GameObject obj : objectList) {
                    obj.update(frameDeltaTime);

                }

                for (Alien alien : alienList) {
                    if (alien.getPosition().getX() > 640 || alien.getPosition().getX() < 10) {
                        updateAlienLevel();
                        break;
                    }

                }

                // Collision detection and response
                for (int i = 0; i < objectList.size(); i++) {
                    for (int j = i + 1; j < objectList.size(); j++) {
                        if (objectList.get(i) instanceof GameObject
                                && objectList.get(i) instanceof GameObject) {
                            Circle circle1 = objectList.get(i).getCircle();
                            Circle circle2 = objectList.get(j).getCircle();

                            Vector2D c1 = new Vector2D(circle1.getCenterX(), circle1.getCenterY());
                            Vector2D c2 = new Vector2D(circle2.getCenterX(), circle2.getCenterY());

                            Vector2D n = c2.sub(c1);
                            double distance = n.magnitude();

                            if (distance < circle1.getRadius() + circle2.getRadius()) {

                                if (objectList.get(j) instanceof Alien && objectList.get(i) instanceof Projectile) {
                                    continue;
                                } else if (objectList.get(i) instanceof Alien && objectList.get(j) instanceof Projectile) {
                                    continue;
                                }

                                if (objectList.get(i) instanceof Alien && objectList.get(j) instanceof Alien) {
                                    continue;
                                } else if (objectList.get(j) instanceof Alien && objectList.get(i) instanceof Alien) {
                                    continue;
                                }

                                // Compute normal and tangent vectors
                                n.normalize();
                                Vector2D t = new Vector2D(-n.getY(), n.getX());

                                // Separate circles - Compute new positions and assign to circles
                                double overlap = distance - (circle1.getRadius() + circle2.getRadius());
                                c1 = c1.add(n.mult(overlap / 2));
                                c2 = c2.sub(n.mult(overlap / 2));
                                objectList.get(i).setPosition(c1);
                                objectList.get(j).setPosition(c2);

                                // Decompose velocities, project them on n and t
                                Vector2D v1 = objectList.get(i).getVelocity();
                                Vector2D v2 = objectList.get(j).getVelocity();

                                Vector2D v1N = n.mult(v1.dot(n));
                                Vector2D v2N = n.mult(v2.dot(n));

                                Vector2D v1T = t.mult(v1.dot(t));
                                Vector2D v2T = t.mult(v2.dot(t));

                                // Change velocities
                                if (objectList.get(i) instanceof Spaceship) {
                                    v1.setY(0);
                                } else if (objectList.get(i) instanceof Projectile) {
                                    v1.setX(0);
                                } else if (objectList.get(i) instanceof Shield) {
                                    v1.setX(0);
                                    v1.setY(0);
                                    double x = objectList.get(i).getCircle().getCenterX();
                                    double y = objectList.get(i).getCircle().getCenterY();
                                    objectList.get(i).setPosition(new Vector2D(x, y));
                                } else if (objectList.get(i) instanceof Bullet) {
                                    v1.setX(0);
                                } else if (objectList.get(i) instanceof Projectile) {
                                    v1.setX(0);
                                } else {
                                    v1.set(v1T.add(v2N));
                                }

                                if (objectList.get(j) instanceof Spaceship) {
                                    v2.setY(0);
                                } else if (objectList.get(j) instanceof Projectile) {
                                    v2.setX(0);
                                } else if (objectList.get(j) instanceof Shield) {
                                    v2.setX(0);
                                    v2.setY(0);
                                    double x = objectList.get(j).getCircle().getCenterX();
                                    double y = objectList.get(j).getCircle().getCenterY();
                                    objectList.get(j).setPosition(new Vector2D(x, y));
                                } else if (objectList.get(j) instanceof Bullet) {
                                    v2.setX(0);
                                } else if (objectList.get(j) instanceof Projectile) {
                                    v2.setX(0);
                                } else {
                                    v2.set(v2T.add(v1N));
                                }

                                if (objectList.get(i) instanceof Shield && objectList.get(j) instanceof Bullet) {
                                    pane.getChildren().remove(objectList.get(j).getCircle());
                                    objectList.remove(j);

                                    if (objectList.get(i).getPosition().getX() == shield1.getPosition().getX()) {
                                        ++shield1Damage;
                                    } else {
                                        ++shield2Damage;
                                    }
                                    
                                    updateShieldImage();

                                } else if (objectList.get(j) instanceof Shield && objectList.get(i) instanceof Bullet) {
                                    pane.getChildren().remove(objectList.get(i).getCircle());
                                    objectList.remove(i);

                                    if (objectList.get(j).getPosition().getX() == shield1.getPosition().getX()) {
                                        ++shield1Damage;
                                    } else {
                                        ++shield2Damage;
                                    }
                                    
                                    updateShieldImage();

                                }

                                if (objectList.get(i) instanceof Alien && objectList.get(j) instanceof Spaceship) {

                                    // pane.getChildren().remove(j);
                                    spaceship.setVelocity(new Vector2D(0, 0));

                                    pane.getChildren().remove(spaceship.getCircle());
                                    objectList.remove(j);

                                    for (Alien a : alienList) {

                                        a.setVelocity(new Vector2D(0, 0));

                                    }

                                    centerLabel.setText("GAME OVER");
                                    livesLabel.setText("LIVES: 0");

                                } else if (objectList.get(j) instanceof Alien && objectList.get(i) instanceof Spaceship) {

                                    // pane.getChildren().remove(i);
                                    spaceship.setVelocity(new Vector2D(0, 0));
                                    pane.getChildren().remove(spaceship.getCircle());

                                    objectList.remove(i);

                                    for (Alien a : alienList) {

                                        a.setVelocity(new Vector2D(0, 0));

                                    }

                                    centerLabel.setText("GAME OVER");
                                    livesLabel.setText("LIVES: 0");

                                }

                                if (objectList.get(i) instanceof Bullet && objectList.get(j) instanceof Alien) {

                                    ++score;

                                    if (score == 24) {
                                        centerLabel.setText("VICTORY");
                                    }

                                    circle1.setDisable(true);
                                    pane.getChildren().remove(circle1);
                                    objectList.remove(i);

                                    // circle2.setFill( AssetManager.getDeadAlienImage() );
                                    pane.getChildren().remove(circle2);
                                    objectList.remove(j);

                                    scoreLabel.setText("SCORE: " + score);

                                    shootingAliensList.remove(objectList.get(j));

                                    AudioClip damage = AssetManager.getDamageSound();
                                    damage.play();

                                } else if (objectList.get(j) instanceof Bullet && objectList.get(i) instanceof Alien) {
                                    ++score;

                                    if (score == 24) {
                                        centerLabel.setText("VICTORY");
                                    }

                                    circle2.setDisable(true);
                                    pane.getChildren().remove(circle2);

                                    objectList.remove(j);

                                    circle1.setDisable(true);
                                    pane.getChildren().remove(circle1);
                                    objectList.remove(i);

                                    scoreLabel.setText("SCORE: " + score);

                                    shootingAliensList.remove(objectList.get(i));

                                    AudioClip damage = AssetManager.getDamageSound();
                                    damage.play();

                                }

                                if (objectList.get(i) instanceof Bullet && objectList.get(j) instanceof Projectile) {

                                    circle1.setDisable(true);
                                    circle2.setDisable(true);

                                    pane.getChildren().remove(circle1);
                                    pane.getChildren().remove(circle2);

                                    objectList.remove(i);
                                    objectList.remove(j);

                                } else if (objectList.get(j) instanceof Bullet && objectList.get(i) instanceof Projectile) {

                                    circle1.setDisable(true);
                                    circle2.setDisable(true);

                                    pane.getChildren().remove(circle1);
                                    pane.getChildren().remove(circle2);

                                    objectList.remove(i);
                                    objectList.remove(j);

                                }

                                if (objectList.get(i) instanceof Projectile && objectList.get(j) instanceof Spaceship) {

                                    pane.getChildren().remove(objectList.get(i).getCircle());
                                    objectList.remove(j);

                                    updateLives();

                                    if (lives == 0) {

                                        // pane.getChildren().remove(j);
                                        spaceship.setVelocity(new Vector2D(0, 0));
                                        pane.getChildren().remove(spaceship.getCircle());
                                        pane.getChildren().remove(objectList.get(i).getCircle());

                                        isPlayerHit = true;

                                        objectList.remove(i);

                                        for (Alien a : alienList) {

                                            a.setVelocity(new Vector2D(0, 0));

                                        }

                                        centerLabel.setText("GAME OVER");
                                    }

                                } else if (objectList.get(j) instanceof Projectile && objectList.get(i) instanceof Spaceship) {

                                    pane.getChildren().remove(objectList.get(j).getCircle());
                                    objectList.remove(j);

                                    updateLives();

                                    if (lives == 0) {

                                        // pane.getChildren().remove(i);
                                        spaceship.setVelocity(new Vector2D(0, 0));
                                        pane.getChildren().remove(spaceship.getCircle());
                                        pane.getChildren().remove(objectList.get(j).getCircle());

                                        isPlayerHit = true;

                                        objectList.remove(i);

                                        for (Alien a : alienList) {

                                            a.setVelocity(new Vector2D(0, 0));

                                        }

                                        centerLabel.setText("GAME OVER");
                                    }

                                }

                                if (objectList.get(i) instanceof Bullet) {
                                    if (objectList.get(i).getVelocity().getY() == 0) {
                                        pane.getChildren().remove(objectList.get(i).getCircle());
                                        objectList.remove(i);
                                    }
                                } else if (objectList.get(j) instanceof Bullet) {
                                    if (objectList.get(j).getVelocity().getY() == 0) {
                                        pane.getChildren().remove(objectList.get(j).getCircle());
                                        objectList.remove(j);
                                    }
                                }

                                if (objectList.get(i) instanceof Projectile) {
                                    if (objectList.get(i).getVelocity().getY() == 0) {
                                        pane.getChildren().remove(objectList.get(i).getCircle());
                                        objectList.remove(i);
                                    }
                                } else if (objectList.get(j) instanceof Projectile) {
                                    if (objectList.get(j).getVelocity().getY() == 0) {
                                        pane.getChildren().remove(objectList.get(j).getCircle());
                                        objectList.remove(j);
                                    }
                                }

                                if (objectList.get(i) instanceof Shield && objectList.get(j) instanceof Projectile) {
                                    
                                    if(objectList.get(i).getPosition().getX() == shield1.getPosition().getX()) {
                                        ++shield1Damage;
                                    } else {
                                        ++shield2Damage;
                                    }
                                    
                                    updateShieldImage();
                                    
                                    pane.getChildren().remove(objectList.get(j).getCircle());
                                    objectList.remove(j);
                                    
                                } else if (objectList.get(j) instanceof Shield && objectList.get(i) instanceof Projectile) {
                                    
                                    if(objectList.get(j).getPosition().getX() == shield1.getPosition().getX()) {
                                        ++shield1Damage;
                                    } else {
                                        ++shield2Damage;
                                    }
                                    
                                    updateShieldImage();
                                    
                                    pane.getChildren().remove(objectList.get(i).getCircle());
                                    objectList.remove(i);
                                }

                            }

                        }

                    }
                }

            }

        }.start();
    }

}
