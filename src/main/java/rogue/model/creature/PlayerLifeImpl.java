package rogue.model.creature;

import java.util.Set;

import javafx.util.Pair;
import rogue.model.events.LifeEvent;

/**
 * An implementation for a {@link PlayerLife}. 
 * Uses the pattern builder in order to create a new instance (even custom).
 */
public final class PlayerLifeImpl extends AbstractLife implements PlayerLife {

    private final LevelIncreaseStrategy levelStrategy;
    private final MaxHpIncreaseStrategy maxHpStrategy;

    private static final int MAX_FOOD = 100; // fixed
    private int maxHealthPoints; // changes dynamically during the game
    private int strength;
    private int leftFood;
    private int level;
    private int coins;

    private PlayerLifeImpl(final LevelIncreaseStrategy levelStrategy, final MaxHpIncreaseStrategy maxHpStrategy, final int healthPoints, 
            final int maxHealthPoints, final int experience, final int strength, final int food, final int level, final int coins) {
        super(healthPoints, experience);
        this.levelStrategy = levelStrategy;
        this.maxHpStrategy = maxHpStrategy;
        this.maxHealthPoints = maxHealthPoints;
        this.strength = strength;
        this.leftFood = food;
        this.level = level;
        this.coins = coins;
    }

    private int checkNotExceeding(final int val, final int max) {
        return val > max ? max : val;
    }

    private void postLifeChange(final LifeParameter parameter, final int value) {
        this.post(new LifeEvent<>(this, Set.of(new Pair<>(parameter, value))));
    }

    @Override
    public void postAllLife() {
        Set.of(new Pair<>(LifeParameter.HP, this.getHealthPoints()), 
               new Pair<>(LifeParameter.MAX_HP, this.maxHealthPoints), 
               new Pair<>(LifeParameter.COINS, this.coins), 
               new Pair<>(LifeParameter.LEVEL, this.level),
               new Pair<>(LifeParameter.STRENGTH, this.strength), 
               new Pair<>(LifeParameter.EXPERIENCE, this.getExperience()), 
               new Pair<>(LifeParameter.FOOD, this.leftFood))
            .forEach(p -> this.postLifeChange(p.getKey(), p.getValue()));
    }

    @Override
    public void hurt(final int damage) {
        super.hurt(damage);
        this.postLifeChange(LifeParameter.HP, this.getHealthPoints());
    }

    @Override
    public void powerUp(final int increment) {
        final var newHp = this.getHealthPoints() + increment;
        this.setHealthPoints(this.checkNotExceeding(newHp, this.maxHealthPoints));
        this.postLifeChange(LifeParameter.HP, this.getHealthPoints());
    }

    @Override
    public void increaseExperience(final int increment) {
        this.setExperience(this.getExperience() + increment);
        final var newLevel = this.levelStrategy.getLevel(this.getExperience());
        if (this.level != newLevel) {
            this.setLevel(newLevel);
            System.out.println(newLevel);
            this.setMaxHealthPoints(this.maxHpStrategy.getMaxHp(this.level));
        }
        this.postLifeChange(LifeParameter.EXPERIENCE, this.getExperience());
    }

    @Override
    public void addStrength(final int increment) {
        this.strength = this.strength + increment;
        this.postLifeChange(LifeParameter.STRENGTH, this.strength);
    }

    @Override
    public int getStrength() {
        return this.strength;
    }

    private void updateFood(final int amount) {
        final var newFood = this.leftFood + amount;
        this.leftFood = this.checkNotNegative(this.checkNotExceeding(newFood, MAX_FOOD));
        this.postLifeChange(LifeParameter.FOOD, this.leftFood);
    }

    @Override
    public void increaseFood(final int amount) {
        this.updateFood(amount);
    }

    @Override
    public void decreaseFood(final int amount) {
        this.updateFood(-amount);
    }

    @Override
    public int getFood() {
        return this.leftFood;
    }

    @Override
    public boolean isDead() {
        return super.isDead() || this.leftFood == 0;
    }

    private void updateCoins(final int amount) {
        this.coins = this.checkNotNegative(this.coins + amount);
        this.postLifeChange(LifeParameter.COINS, this.coins);
    }

    @Override
    public void addCoins(final int amount) {
        this.updateCoins(amount);
    }

    @Override
    public void subCoins(final int amount) {
        this.updateCoins(-amount);
    }

    @Override
    public int getCoins() {
        return this.coins;
    }

    private void setLevel(final int level) {
        this.level = level;
        this.postLifeChange(LifeParameter.LEVEL, this.level);
    }

    @Override
    public int getLevel() {
        return this.level;
    }

    private void setMaxHealthPoints(final int maxHealthPoints) {
        this.maxHealthPoints = maxHealthPoints;
        this.postLifeChange(LifeParameter.MAX_HP, this.maxHealthPoints);
    }

    @Override
    public int getMaxHealthPoints() {
        return this.maxHealthPoints;
    }

    @Override
    public int getMaxFood() {
        return MAX_FOOD;
    }

    @Override
    public String toString() {
        return "PlayerLifeImpl [maxHealthPoints=" + maxHealthPoints + ", strength="
        + strength + ", leftFood=" + leftFood + ", level=" + level + ", coins=" + coins + ", toString()="
        + super.toString() + "]";
    }

    public static class Builder {

        /**
         * Default values.
         */
        private static final int MAX_HEALTH_POINTS = 12;
        private static final int HEALTH_POINTS = 12;
        private static final int FOOD = 50; 
        private static final int EXPERIENCE = 0;
        private static final int STRENGTH = 16;
        private static final int COINS = 0;
        private static final int LEVEL = 1;

        private LevelIncreaseStrategy levelStrategy = new StandardLevelIncreaseStrategy();
        private MaxHpIncreaseStrategy maxHpStrategy = new StandardMaxHpIncreaseStrategy();

        private int maxHealthPoints = MAX_HEALTH_POINTS;
        private int healthPoints = HEALTH_POINTS;
        private int food = FOOD;
        private int experience = EXPERIENCE;
        private int strength = STRENGTH;
        private int level = LEVEL;
        private int coins = COINS;
        private boolean consumed;

        /**
         * Initialize the level increase strategy.
         * @param levelStrategy
         *      the {@link LevelIncreaseStrategy} to use
         * @return this Builder for chaining
         */
        public Builder initLevelStrategy(final LevelIncreaseStrategy levelStrategy) {
            this.levelStrategy = levelStrategy;
            return this;
        }

        /**
         * Initialize the maximum health points increase strategy.
         * @param maxHpStrategy
         *      the {@link MaxHpIncreaseStrategy} to use
         * @return this Builder for chaining
         */
        public Builder initMaxHpStrategy(final MaxHpIncreaseStrategy maxHpStrategy) {
            this.maxHpStrategy = maxHpStrategy;
            return this;
        }

        /**
         * Initialize the food.
         * @param food
         *      the initial food quantity
         * @return this Builder for chaining
         */
        public Builder initFood(final int food) {
            this.food = food;
            return this;
        }

        /**
         * Initialize the player experience.
         * @param experience
         *      the initial player experience
         * @return this Builder for chaining
         */
        public Builder initExperience(final int experience) {
            this.experience = experience;
            return this;
        }

        /**
         * Initialize the player health points.
         * @param healthPoints
         *      the initial player health points.
         * @return this Builder for chaining
         */
        public Builder initHealthPoints(final int healthPoints) {
            this.healthPoints = healthPoints;
            return this;
        }

        /**
         * Initialize the player maximum health points.
         * @param maxHealthPoints
         *      the max hp value
         * @return this Builder for chaining
         */
        public Builder initMaxHealthPoints(final int maxHealthPoints) {
            this.maxHealthPoints = maxHealthPoints;
            return this;
        }

        /**
         * Initialize the player experience.
         * @param strength
         *      the initial player strength
         * @return this Builder for chaining
         */
        public Builder initStrength(final int strength) {
            this.strength = strength;
            return this;
        }

        /**
         * Initialize the player level.
         * @param level
         *      the initial player level
         * @return this Builder for chaining
         */
        public Builder initLevel(final int level) {
            this.level = level;
            return this;
        }

        /**
         * Initialize the player level.
         * @param coins
         *      the initial amount of player coins
         * @return this Builder for chaining
         */
        public Builder initCoins(final int coins) {
            this.coins = coins;
            return this;
        }

        /**
         * @return a player life 
         */
        public final PlayerLifeImpl build() {
            if (consumed) {
                throw new IllegalStateException("The builder can only be used once");
            }
            consumed = true;
            return new PlayerLifeImpl(levelStrategy, maxHpStrategy, healthPoints, 
                    maxHealthPoints, experience, strength, food, level, coins);
        }
    }

}
