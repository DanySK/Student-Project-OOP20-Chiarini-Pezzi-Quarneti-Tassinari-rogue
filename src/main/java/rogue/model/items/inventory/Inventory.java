package rogue.model.items.inventory;

import java.util.Optional;

import rogue.model.items.Item;

public interface Inventory {

    boolean useItem(int index);

    Optional<Item> getItem(int index);

    boolean addItem(Item item);

}
