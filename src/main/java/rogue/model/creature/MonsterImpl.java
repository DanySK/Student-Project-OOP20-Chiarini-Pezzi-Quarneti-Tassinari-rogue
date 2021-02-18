package rogue.model.creature;

import javafx.util.Pair;
import rogue.model.items.potion.PotionImpl;
import rogue.model.world.Direction;

public class MonsterImpl implements Monster {

    private final MonsterType type;

    public MonsterImpl(final MonsterType type) {
        this.type = type;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean move() {
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public MonsterLife getLife() {
        return this.type.getLife();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public MonsterType getMonsterType() {
        return this.type;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getAC() {
        return this.type.getAC();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Pair<Integer, Integer> getDamage() {
        return this.type.getDamage();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Special getSpecial() {
        return this.type.getSpecial();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getMoney() {
        return this.type.getMoney();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PotionImpl getItem() {
        return this.type.getItem();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getItemChange() {
        return this.type.getItemChange();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int attackDamage() {
        return (int) (Math.random() * (this.getDamage().getValue() - this.getDamage().getKey()) + this.getDamage().getKey());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PotionImpl dropItem() {
        final int casuale = (int) (Math.random() * 100) + 1;
        if (casuale <= this.getItemChange()) {
            return this.getItem(); 
       } else {
           return null;
        }
    }

    private Direction randomMove() {
        final int direction = (int) (Math.random() * 4);
        switch (direction) {
        case 1:
            return Direction.NORTH;
        case 2:
            return Direction.EAST;
        case 3:
            return Direction.SOUTH;
        case 4:
            return Direction.WEST;
        default:
            return Direction.NONE;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Direction monsterMove(final Direction playerDirection, final Monster monster) {
        if (monster.getSpecial().isFlyingRandom()) {
            return this.randomMove();
        } else if (monster.getSpecial().isHostile()) {
            return playerDirection;
        } else {
            return Direction.NONE;
        }
    }

}
