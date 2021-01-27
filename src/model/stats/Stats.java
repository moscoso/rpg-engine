package model.stats;

import java.util.Observable;
import java.util.Observer;

public class Stats extends Observable {

    protected int strength;
    protected int agility;
    protected int intellect;
    protected int hardiness;
    protected int maxHealth;
    protected int maxMana;
    protected int speed;
    protected int defense;
    protected int offense;
    protected int manaRegenPerSecond;
    protected int healthRegenPerSecond;

    public Stats(int strength,
            int agility,
            int intellect,
            int hardiness,
            int maxHealth,
            int maxMana,
            int defense,
            int offense,
            int speed,
            int manaRegenPerSec,
            int healthRegenPerSec) {
        this.strength = strength;
        this.agility = agility;
        this.intellect = intellect;
        this.hardiness = hardiness;
        this.maxHealth = maxHealth;
        this.maxMana = maxMana;
        this.defense = defense;
        this.offense = offense;
        this.speed = speed;
        this.manaRegenPerSecond = manaRegenPerSec;
        this.healthRegenPerSecond = healthRegenPerSec;
    }

    public Stats() {
        this(1, 1, 1, 1, 100, 100, 1, 1, 1, 1, 1);

    }

    public int getStrength() {
        return strength;
    }

    public int getAgility() {
        return agility;
    }

    public int getIntellect() {
        return intellect;
    }

    public int getHardiness() {
        return hardiness;
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    public int getMaxMana() {
        return maxMana;
    }

    public int getOffense() {
        return offense;
    }

    public int getDefense() {
        return defense;
    }

    public int getHpMax() {
        return maxHealth;
    }

    public int getMpMax() {
        return maxMana;
    }

    public int getSpeed() {
        return speed;
    }

    /**
     * Speed should be a number from 0 to getFPS.
     *
     * @param speed is how many frames it takes before an entity move to the
     * next tile. Thus,ß ironcially a speed of 1 is faster than a speed of 30.
     *
     * PRECONDITIONS: speed > 0 < GameEngine.getFPS();
     */
    public void setSpeed(int speed) {
        this.speed = speed;
        this.setChanged();
        this.notifyObservers(this);

    }

    public void setStrength(int nextStr) {
        strength = verifyBounds(nextStr) ? nextStr : strength;
        this.setChanged();
        this.notifyObservers(this);
    }

    public void setAgility(int nextAgi) {
        agility = verifyBounds(nextAgi) ? nextAgi : agility;
        this.setChanged();
        this.notifyObservers(this);
    }

    public void setIntellect(int nextInt) {
        intellect = verifyBounds(nextInt) ? nextInt : intellect;
        this.setChanged();
        this.notifyObservers(this);
    }

    public void setHardiness(int nextHard) {
        hardiness = verifyBounds(nextHard) ? nextHard : hardiness;
        this.setChanged();
        this.notifyObservers(this);
    }

    public void setMaxHealth(int nextHP) {
        maxHealth = nextHP;
        this.setChanged();
        this.notifyObservers(this);
    }

    public void setMaxMana(int nextMP) {
        maxMana = verifyBounds(nextMP) ? nextMP : maxMana;
        this.setChanged();
        this.notifyObservers(this);
    }

    public void setDefense(int nextDef) {
        defense = verifyBounds(nextDef) ? nextDef : defense;
        this.setChanged();
        this.notifyObservers(this);
    }

    public void setOffense(int nextOff) {
        offense = verifyBounds(nextOff) ? nextOff : offense;
        this.setChanged();
        this.notifyObservers(this);
    }

    public void modStrength(int strAdded) {
        strength = verifyBounds(strength + strAdded) ? (strength + strAdded) : 0;
        this.setChanged();
        this.notifyObservers(this);
    }

    public void modAgility(int agiAdded) {
        agility = verifyBounds(agility + agiAdded) ? (agility + agiAdded) : 0;
        this.setChanged();
        this.notifyObservers(this);
    }

    public void modIntellect(int intAdded) {
        intellect = verifyBounds(intellect + intAdded) ? (intellect + intAdded) : 0;
        this.setChanged();
        this.notifyObservers(this);
    }

    public void modHardiness(int hardAdded) {
        hardiness = verifyBounds(hardiness + hardAdded) ? (hardiness + hardAdded) : 0;
        this.setChanged();
        this.notifyObservers(this);
    }

    public void modmaxHealth(int hpAdded) {
        maxHealth += hpAdded;
        this.setChanged();
        this.notifyObservers(this);
    }

    public void modMaxMana(int mpAdded) {
        maxMana = verifyBounds(maxMana + mpAdded) ? (maxMana + mpAdded) : 0;
        this.setChanged();
        this.notifyObservers(this);
    }

    public void modOffense(int offAdded) {
        offense = verifyBounds(offense + offAdded) ? (offense + offAdded) : 0;
        this.setChanged();
        this.notifyObservers(this);
    }

    public void modDefense(int defAdded) {
        defense = verifyBounds(defense + defAdded) ? (defense + defAdded) : 0;
        this.setChanged();
        this.notifyObservers(this);
    }

    public Stats inverted() {
        return new Stats(strength * -1, agility * -1, intellect * -1, hardiness * -1, maxHealth * -1, maxMana * -1, defense * -1, offense * -1, speed * -1, healthRegenPerSecond * -1, manaRegenPerSecond * -1);
    }

    protected boolean verifyBounds(int value) {
        return (value >= 0);
    }

    public void mergeStats(Stats modifier) {
        modStrength(modifier.getStrength());
        modAgility(modifier.getAgility());
        modIntellect(modifier.getIntellect());
        modHardiness(modifier.getHardiness());
        modmaxHealth(modifier.getMaxHealth());
        modMaxMana(modifier.getMaxMana());
        modOffense(modifier.getOffense());
        modDefense(modifier.getDefense());
        this.setChanged();
        this.notifyObservers(this);
    }

    @Override
    public void addObserver(Observer o) {
        super.addObserver(o);
        this.setChanged();
        this.notifyObservers(this);
    }
}
