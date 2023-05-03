import java.util.Random;

/**
 * Class to define magical spells to be used by character
 */
public class Spell {

    private String name;
    private double minimumDamage;
    private double maximumDamage;
    private double chanceOfSuccess;

    /**
     * Constructor for a magical spell
     *
     * @param name, name we want for the spell
     * @param minimumDamage, to determine the range of the damaged caused by spell (lower limit)
     * @param maximumDamage, to determine the range of the damaged caused by spell (higher limit)
     * @param chanceOfSuccess, to determine probability of spell causing damage
     */
    public Spell(String name, double minimumDamage, double maximumDamage, double chanceOfSuccess){
        // error handling for illegal parameters
        if (minimumDamage < 0 || minimumDamage > maximumDamage || chanceOfSuccess < 0 || chanceOfSuccess > 1){
            throw new IllegalArgumentException("Illegal attribute");
        }
        this.name = name;
        this.minimumDamage = minimumDamage;
        this.maximumDamage = maximumDamage;
        this.chanceOfSuccess = chanceOfSuccess;
    }
    public String getName(){
        return this.name;
    }

    /**
     * Method to calculate damage done by spell cast
     *
     * @param seed, to control randomness of damage
     * @return value of damage done
     */
    public double getMagicDamage(int seed){

        Random generator = new Random(seed);
        double chanceNum = generator.nextDouble();
        if (chanceNum > this.chanceOfSuccess){
            return 0;
        }
        else{
            return generator.nextDouble(this.minimumDamage, this.maximumDamage); // use limits to determine damage
        }
    }

    public String toString() {
        // format toString method to show spell's name, damage range and chance of success
        return String.format("%-20s",this.name) + ">>\tDamage: " + this.minimumDamage + " - " +
                this.maximumDamage + "\t Chance: " + this.chanceOfSuccess * 100 + "%";
    }
}
