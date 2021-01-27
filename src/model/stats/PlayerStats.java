package model.stats;

public class PlayerStats extends Stats {

    private int currentHealth;
    private int currentMana;
    private int level;
    private int livesLeft;
    private int experience;
    

    public PlayerStats(int level,
            int livesLeft,
            int strength,
            int agility,
            int intellect,
            int hardiness,
            int experience,
            int maxHealth,
            int maxMana,
            int defense,
            int offense, int speed, int healthRegenPerSec, int manaRegenPerSec) {

        super(strength, agility, intellect, hardiness, maxHealth, maxMana, defense, offense, speed, healthRegenPerSec, manaRegenPerSec);

        this.currentHealth = maxHealth;
        this.currentMana = maxMana;
        this.level = level;
        this.livesLeft = livesLeft;
        this.experience = experience;
    }

    public PlayerStats() {
        this(1, 1, 1, 1, 1, 1, 0, 100, 100, 1, 1, 1, 1, 10);
    }

    public int getCurrentHealth() {
        return currentHealth;
    }

    public int getCurrentMana() {
        return currentMana;
    }

    public int getLevel() {
        return level;
    }

    public int getLivesLeft() {
        return livesLeft;
    }

    public int getExperience() {
        return experience;
    }

    public void modCurrentHealth(int value) {
        currentHealth += value;
        if(currentHealth > maxHealth) currentHealth = maxHealth;
        if(currentHealth < 0) currentHealth = 0;
        this.setChanged();
        this.notifyObservers(this);
    }

    public void modCurrentMana(int value) {
        currentMana += value;
        if(currentMana > maxMana) currentMana = maxMana;
        if(currentMana < 0) currentMana = 0;
        this.setChanged();
        this.notifyObservers(this);
    }

    public void setCurrentHealth(int value) {
        currentHealth = value;
        this.setChanged();
        this.notifyObservers(this);
    }

    public void setCurrentMana(int value) {
        currentMana = value;
        this.setChanged();
        this.notifyObservers(this);
    }
    
    public void gainLevel(){
        level++;
    }
    
    public void gainLife(){
        livesLeft++;
    }
    
    public void lostLife(){
        livesLeft--;
    }

    public void gainExperience(int value) {
        experience += value;
    }

    public int getManaRegenPerSecond() {
        return manaRegenPerSecond;
    }

    public int getHealthRegenPerSecond() {
        return healthRegenPerSecond;
    }

    public void setManaRegenPerSecond(int manaRegenPerSecond) {
        this.manaRegenPerSecond = manaRegenPerSecond;
    }

    public void setHealthRegenPerSecond(int healthRegenPerSecond) {
        this.healthRegenPerSecond = healthRegenPerSecond;
    }
    
    

}