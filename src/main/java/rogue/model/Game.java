package rogue.model;

import java.util.stream.Stream;

import rogue.model.world.Tile;

public interface Game {
    int getWidth();
    int getHeight();
    Stream<Tile> getTiles();
}