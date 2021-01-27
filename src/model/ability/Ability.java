package model.ability;

/**
 * This is the parent class of all usable abilities
 * @author Jason Owens
 */
public abstract class Ability {

    private String name;
    
    protected Ability(){
        this.name = "uninitialized ability";
    }
    protected Ability(String name){
        this.name = name;
    }

    public abstract void execute();
    
    public String getName(){
        return this.name;
    }
}
