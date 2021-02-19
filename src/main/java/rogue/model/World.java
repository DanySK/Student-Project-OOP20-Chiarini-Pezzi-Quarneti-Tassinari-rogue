package rogue.model;

import java.util.Map;
import java.util.stream.Stream;

import rogue.model.world.Direction;
import rogue.model.world.Tile;

/**
 * the game world (level manager).
 */
public interface World {
    /**
     * @return the current level's width
     */
    int getWidth();

    /**
     * @return the current level's height
     */
    int getHeight();

    /**
     * @return all of the current level's tiles
     */
    Stream<Tile> getTiles();

    /**
     * @return all of the current level's entities with their corresponding tiles
     */
    Map<Entity, Tile> getEntityMap();

    /**
     * @param direction the player movement's direction
     * @return if the level is changed
     */
    boolean round(Direction direction);
}