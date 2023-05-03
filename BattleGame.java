import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

/**
 * Class with the game dynamics
 */
public class BattleGame {

    private static Random randomInt = new Random(789);

    /**
     * Method to play the game
     *
     * @param playerFile, name of file that contains player character
     * @param monsterFile, name of file that contains monster character
     * @param spellFile, name of file that contains spells
     */
    public static void playGame(String playerFile, String monsterFile, String spellFile) {

        ArrayList<Spell> availableSpells = FileIO.readSpells(spellFile);  // create list of spells from file
        addSpells(availableSpells);    // add previously created list of spells to character

        Character player = FileIO.readCharacter(playerFile);   // create player character from file
        Character monster = FileIO.readCharacter(monsterFile); // create monster character from file

        displayCharacters(player, monster);   // show characters info

        // Determine if we have spells available for battle. If we don't battle without spells, otherwise battle with the
        // available spells
        if (availableSpells == null){
            battle(player, monster);
        }
        else {
            battleWithSpells(player, monster);
        }
    }

    /**
     * Helper method to add list of spells to character
     *
     * @param availableSpells
     */
    private static void addSpells(ArrayList<Spell> availableSpells) {
        // Determine if we have spells to use. If we don't inform the user, otherwise add spells to character and
        // display them
        if (availableSpells == null){
            System.out.println("The game will be played without spells\n");
        }
        else {
            Character.setSpells(availableSpells);
            Character.displaySpells();
            System.out.println();
        }
    }

    /**
     * Helper method to display the characters to be used in game
     *
     * @param player, first character
     * @param monster, second character
     */
    private static void displayCharacters(Character player, Character monster){
        // If no player has been set, inform the user and exit game
        if (player == null || monster == null) {
            System.out.println("Game cannot be played");
            System.exit(1);
        }

        // Display characters' info
        System.out.println("Name: " + player.getName() + "\n" + "Health: " + player.getCurrentHealth() + "\n" +
                "Attack: " + player.getAttackValue() + "\n" + "Number of Wins: " + player.getNumWins() + "\n");

        System.out.println("Name: " + monster.getName() + "\n" + "Health: " + monster.getCurrentHealth() + "\n" +
                "Attack: " + monster.getAttackValue() + "\n" + "Number of Wins: " + monster.getNumWins() + "\n");
    }

    /**
     * Method to play the game without spells
     *
     * @param player, first character
     * @param monster, second character
     */
    private static void battle(Character player, Character monster) {

        Scanner input = new Scanner(System.in);
        // loop to keep playing until any character's health goes to zero or user decides to quit
        while (player.getCurrentHealth() > 0 || monster.getCurrentHealth() > 0) {

            System.out.print("Enter a command: " + "\n");
            String command = input.next();   // user input

            if (command.equals("attack")) {
                // if user chooses to attack, player will attack first. Calculate damage done and inform user
                int seed = randomInt.nextInt();
                double attack = player.getAttackDamage(seed);
                String attackStr = String.format("%.2f", attack);
                System.out.println(player.getName() + " attacks for " + attackStr + " damage!");

                // Check monster's health, if >0 show updated info and game continues, otherwise inform user of monster's
                // defeat and update and save info.
                if (monster.getCurrentHealth() > 0) {
                    System.out.println(monster.toString() + "\n");
                } else {
                    System.out.println(monster.getName() + " has been Knocked Out!!\n\nYou have defeated "+
                            monster.getName() + "!!!");
                    player.increaseWins();            // increase player's win count
                    System.out.println(player.getName() + " has won " + player.getNumWins() + " battles");
                    FileIO.writeCharacter(player, "player");           // save updated info to file
                    System.out.println("Successfully wrote to file player.txt");
                    break;
                }
                // if game continued it is monster's turn to attack, calculate damage and inform user
                seed = randomInt.nextInt();
                double attack2 = monster.getAttackDamage(seed);
                String attackStr2 = String.format("%.2f", attack2);
                System.out.println(monster.getName() + " attacks for " + attackStr2 + " damage!");
                player.takeDamage(attack2);

                // Check player's health, if >0 show updated info and game continues, otherwise inform user of player's
                // defeat and update and save info.
                if (player.getCurrentHealth() > 0) {
                    System.out.println(player.toString() + "\n");
                }
                else {
                    System.out.println(player.getName() + " has been Knocked Out!!\n\nYou have been defeated!!");
                    monster.increaseWins();     // increase monster's win count
                    System.out.println(monster.getName() + " has won " + monster.getNumWins() + " battles");
                    FileIO.writeCharacter(monster, "monster");    // save updated data to file
                    System.out.println("Successfully wrote to file monster.txt");
                    break;
                }
            }
            // if user decides to quit exit game
            if (command.equals("quit")) {
                System.out.println("Thank you for playing!");
                System.exit(2);
            }
            // if input not recognized inform user
            if (!command.equals("quit") && !command.equals("attack")) {
                System.out.println("Input not recognized. Please enter attack or quit");
            }
        }
    }
    /**
     * Method to play the game with spells
     *
     * @param player, first character
     * @param monster, second character
     */
    private static void battleWithSpells(Character player, Character monster) {

        Scanner input = new Scanner(System.in);
        // loop to keep playing until any character's health goes to zero or user decides to quit
        while (player.getCurrentHealth() > 0 || monster.getCurrentHealth() > 0) {

            System.out.print("Enter a command: " + "\n");
            String command = input.nextLine();

            if (command.equals("attack")) {
                // if user chooses to attack, player will attack first. Calculate damage done and inform user
                int seed = randomInt.nextInt();
                double attack = player.getAttackDamage(seed);
                String attackStr = String.format("%.2f", attack);
                System.out.println(player.getName() + " attacks for " + attackStr + " damage!");
                monster.takeDamage(attack);

                // Check monster's health, if >0 show updated info and game continues, otherwise inform user of monster's
                // defeat and update and save info.
                if (monster.getCurrentHealth() > 0) {
                    System.out.println(monster.toString() + "\n");
                } else {
                    System.out.println(monster.getName() + " has been Knocked Out!!\n\nYou have defeated "+
                            monster.getName() + "!!!");
                    player.increaseWins();     // update player's win count
                    System.out.println(player.getName() + " has won " + player.getNumWins() + " battles");
                    FileIO.writeCharacter(player, "player");          // save updated data to file
                    System.out.println("Successfully wrote to file player.txt");
                    break;
                }
                // if game continued it is monster's turn to attack, calculate damage and inform user
                seed = randomInt.nextInt();
                double attack2 = monster.getAttackDamage(seed);
                String attackStr2 = String.format("%.2f", attack2);
                System.out.println(monster.getName() + " attacks for " + attackStr2 + " damage!");
                player.takeDamage(attack2);
                // Check player's health, if >0 show updated info and game continues, otherwise inform user of player's
                // defeat and update and save info.
                if (player.getCurrentHealth() > 0) {
                    System.out.println(player.toString() + "\n");
                }
                else {
                    System.out.println(player.getName() + " has been Knocked Out!!\n\nYou have been defeated!!");
                    monster.increaseWins();       // increase monster's win count
                    System.out.println(monster.getName() + " has won " + monster.getNumWins() + " battles");
                    FileIO.writeCharacter(monster, "monster");     // save updated data to file
                    System.out.println("Successfully wrote to file monster.txt");
                    break;
                }
            }
            if (command.equals("quit")) {
                System.out.println("Thank you for playing!");
                System.exit(2);
            }

            // If player inputs something other than quit or attack we assume he is trying to input a spell. If the spell
            // is not found in the list of available spells it will fail and do 0 damage. As for the rest, it is the same as
            // a battle without spells
            if (!command.equals("quit") && !command.equals("attack")) {
                // player attacks first
                int seed = randomInt.nextInt();
                double damage = player.castSpell(command, seed);
                if (damage <= 0){
                    System.out.println(player.getName() + " tried to cast " + command + " but failed" + "\n");
                }
                else {
                    String damageStr = String.format("%.2f", damage);
                    System.out.println(player.getName() + " casts " + command + " for " + damageStr + " damage!");
                    monster.takeDamage(damage);

                    if (monster.getCurrentHealth() > 0) {
                        System.out.println(monster.toString() + "\n");
                    } else {
                        System.out.println(monster.getName() + " has been Knocked Out!!\n\nYou have defeated "+
                                monster.getName() + "!!!");
                        player.increaseWins();
                        System.out.println(player.getName() + " has won " + player.getNumWins() + " battles");
                        FileIO.writeCharacter(player, "player");
                        System.out.println("Successfully wrote to file player.txt");
                        break;
                    }
                }
                // if monster's health did not go to 0, it attacks (no spells, only player can use spells)
                seed = randomInt.nextInt();
                double attack3 = monster.getAttackDamage(seed);
                String attackStr3 = String.format("%.2f", attack3);
                System.out.println(monster.getName() + " attacks for " + attackStr3 + " damage!");
                player.takeDamage(attack3);

                if (player.getCurrentHealth() > 0) {
                    System.out.println(player.toString() + "\n");
                }
                else {
                    System.out.println(player.getName() + " has been Knocked Out!!\n\nYou have been defeated!!");
                    monster.increaseWins();
                    System.out.println(monster.getName() + " has won " + monster.getNumWins() + " battles");
                    FileIO.writeCharacter(monster, "monster");
                    System.out.println("Successfully wrote to file monster.txt");
                    break;
                }
            }
        }
    }
}
