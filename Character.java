import java.util.ArrayList;
import java.util.Random;

/**
 * Class to represent a character in the BattleGame (player and monster) and their attributes
 */
public class Character {
    private String name;
    private double attackValue;
    private double maxHealth;
    private double currentHealth;
    private int numWins;
    private static ArrayList<Spell> spells;

    /**
     * Constructor using fields
     *
     * @param name, name we want to give our character
     * @param attackValue, attack value of character
     * @param maxHealth, our character's health
     * @param numWins, number of times character has won a battle
     */
    public Character(String name, double attackValue, double maxHealth, int numWins){
        this.name = name;
        this.attackValue = attackValue;
        this.maxHealth = maxHealth;
        this.currentHealth = maxHealth;
        this.numWins = numWins;
    }
    public String getName() {
        return name;
    }
    public double getAttackValue() {
        return attackValue;
    }
    public double getMaxHealth() {
        return maxHealth;
    }
    public double getCurrentHealth() {
        return currentHealth;
    }
    public int getNumWins() {
        return numWins;
    }


    public String toString() {
        // format toString method to show the character's name and health when invoked
        return this.name + "'s current health is " + String.format("%.2f", this.currentHealth);
    }

    /**
     * Method to calculate total damaged cause by character
     *
     * @param seed, to control random input
     * @return
     */
    public double getAttackDamage(int seed){
        Random generator = new Random(seed);
        return this.attackValue * generator.nextDouble(0.7, 1);
    }

    /**
     * Method to update character's health when receiving damage
     *
     * @param damageReceived
     */
    public void takeDamage(double damageReceived){
        this.currentHealth -= damageReceived;
    }

    /**
     * Method to increase numWins variable when character has won a match
     */
    public void increaseWins(){
        this.numWins++;
    }

    /**
     * Method to assign list of spells available to character
     *
     * @param spells, list of spells we want to assign to character
     */
    public static void setSpells(ArrayList<Spell> spells){
        Character.spells = new ArrayList<>(spells);
    }

    /**
     * Method to display the spells available to character
     */
    public static void displaySpells(){
        System.out.println("Available Spells:");
        for (int i = 0; i < spells.size(); i++) {
            System.out.println(spells.get(i).toString());
        }
    }

    /**
     * Method to cast a spell
     *
     * @param name, name of spell we want to cast
     * @param seed, to control randomness of damage done by spell
     * @return damage done by spell or -1 if unsuccessful
     */
    public double castSpell(String name, int seed){
        // loop to search for the spell we want to cast and if found calculate damage done. If search is unsuccessful
        // return -1
        for (int i = 0; i < spells.size(); i++) {
            if (name.equalsIgnoreCase(spells.get(i).getName())){
                return spells.get(i).getMagicDamage(seed);
            }
        }
        return -1;
    }
}