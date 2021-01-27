package model.stats;

public class ItemStats extends Stats {

    private int durability;
    private int goldValue;

    public ItemStats(
            int strength,
            int agility,
            int intellect,
            int hardiness,
            int maxHealth,
            int maxMana,
            int durability,
            int value,
            int defense,
            int offense,
            int speed,
            int healthRegenPerSec,
            int manaRegenPerSec
    ) {

        super(strength, agility, intellect, hardiness, maxHealth, maxMana, defense, offense, speed, healthRegenPerSec, manaRegenPerSec);

        this.durability = durability;
        this.goldValue = value;
    }

    public ItemStats() {
        super(1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1);
        this.durability = 10;
        this.goldValue = 10;
    }

    public int getDurability() {
        return durability;
    }

    public int getValue() {
        return goldValue;
    }

    public void setDurability(int durabilityNew) {
        durability = durabilityNew;
    }

    public void setGoldValue(int value) {
        goldValue = value;
    }

    public void modDurability(int durabilityMod) {
        durability += durabilityMod;
    }

    public void modGoldValue(int value) {
        goldValue += value;
    }
}
