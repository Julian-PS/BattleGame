import java.io.*;
import java.util.ArrayList;

/**
 * Class to read and write characters and spells from a file
 */
public class FileIO {
    /**
     * Method to create a character from the information provided on a file
     *
     * @param filename, file we want to get info from
     * @return new character
     */
    public static Character readCharacter(String filename){

        String name;
        double attackValue;
        double maxHealth;
        double currentHealth;
        int numWins;
        // Get information from file and assign it to variables
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(filename));
            name = bufferedReader.readLine();
            attackValue = Double.parseDouble(bufferedReader.readLine());
            maxHealth = Double.parseDouble(bufferedReader.readLine());
            numWins = Integer.parseInt(bufferedReader.readLine());

            bufferedReader.close();

            return new Character(name, attackValue, maxHealth, numWins);   // create new character using variables we got from file

            // Error handling
        } catch (FileNotFoundException e) {
            System.out.println("Character File has not been found");
            return null;
        } catch (IOException e) {
            System.out.println("Error produced while reading file");
            return null;
        }
    }

    /**
     * Method to create a list of spells from a given file
     *
     * @param file, file we want to get the info from
     * @return a list of spells
     */
    public static ArrayList<Spell> readSpells(String file){

        ArrayList<Spell> spellArray = new ArrayList<>();
        String[] temp = new String[4];    // we know each spell has 4 variables (name, minDamage, maxDamage, chanceOfSuccess)

        // create new spell list using information from the file
        try {
            BufferedReader usableSpells = new BufferedReader(new FileReader(file));
            String line = usableSpells.readLine();

            while (line != null) {
                temp = line.split("\t");

                spellArray.add(new Spell(temp[0], Double.parseDouble(temp[1]), Double.parseDouble(temp[2]),
                        Double.parseDouble(temp[3])));

                line = usableSpells.readLine();
            }
            usableSpells.close();
            return spellArray;      // return the newly created list of spells

            // Error handling
        } catch (FileNotFoundException e) {
            System.out.println("File for spells has not been found");
            return null;

        } catch (IOException e) {
            System.out.println("Error produced while reading file");
            return null;
        }
    }

    /**
     * Method to write our character's info into a file
     *
     * @param character, we want to write into a file
     * @param fileName, file where we want to write ouc character's info
     */
    public static void writeCharacter(Character character, String fileName){
        // get the character's info and write it into a file on separate lines
        try {
            BufferedWriter writeCharacter = new BufferedWriter(new FileWriter(fileName));
            writeCharacter.write(character.getName() + "\n" + character.getAttackValue() + "\n" +
                    character.getMaxHealth() + "\n" + character.getNumWins());

            writeCharacter.close();
            // Error handling
        } catch (IOException e) {
            throw new RuntimeException("Sorry, an error has occurred while writing the file");
        }
    }
}
