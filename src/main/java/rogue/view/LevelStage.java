package rogue.view;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import rogue.model.Entity;
import rogue.model.creature.Player;
import rogue.model.world.Level;
import rogue.model.world.Tile;

public class LevelStage extends Stage {
    private static final int SCALE = 32;

    public LevelStage(final Level level) {
        super();

        setTitle("Mondo di merda");

        setScene(new Scene(initSceneUI(level), level.getWidth() * SCALE, level.getHeight() * SCALE));
    }

    private Parent initSceneUI(final Level level) {
        final Pane root = new Pane();

        final Canvas c = new Canvas(level.getWidth() * SCALE, level.getHeight() * SCALE);
        final GraphicsContext gc = c.getGraphicsContext2D();

        // level
        level.getTileStream().forEach(tile -> {
            Image img = getImage(tile);

            //// 1024 fucking threads
            // new Thread(() -> {
            gc.drawImage(img, tile.getX() * SCALE, tile.getY() * SCALE, SCALE, SCALE);
            // }).start();
        });

        // entity
        level.getEntityMap().forEach((entity, tile) -> {
            Image img = getImage(entity);
            gc.drawImage(img, tile.getX() * SCALE, tile.getY() * SCALE, SCALE, SCALE);
        });

        root.getChildren().add(c);
        return root;
    }

    private Image getImage(final Tile tile) {
        final String variant = tile.isWall() ? "WALL" : "FLOOR";
        final String material = tile.getMaterial().toString();

        return new Image(ClassLoader.getSystemResource("images/" + variant + "-" + material + ".png").toExternalForm());
    }

    private Image getImage(final Entity entity) {
        String name = "";

        if (entity instanceof Player) {
            name = "Player";
        }

        return new Image(ClassLoader.getSystemResource("images/" + name + ".png").toExternalForm());
    }
}
