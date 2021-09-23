//Hangman Game by River Dyke, 2021. For AP Computer Science.
import java.util.Scanner;
public class hangman {
    public static void main(String[] args){
        int a = argsHandle(args);
        if (a > 0) {
            Scanner scan = new Scanner(System.in);
            String guess = "";
            String misses = "";
            int missedNum = 0;
            boolean win = false;
            boolean lose = false;
            boolean hit = false;
            //start
            clearScreen();
            System.out.println("Hangman by River Dyke");
            System.out.println("--------------------------");
            String secret = pickWord(a);
            final String answer = secret;
            String hidden = makeDisplay(secret);
            clearScreen();
            makeMan(0);
            while (win == false && lose == false){
                System.out.println("Word: " + hidden);
                System.out.println("--------------------------");
                //get the player's guess
                System.out.println("Guess: ");
                guess = scan.nextLine();
                //check if guess is valid
                if (guess.indexOf("*") == -1 && guess.indexOf(" ") == -1 && guess.length() == 1 ){
                    clearScreen();
                    if (secret.indexOf(guess) != -1) {
                        while(secret.indexOf(guess) != -1){
                            //check for any of the guessed letter in the secret word until there is no more, then acknowledge the correct guess
                            hidden = hidden.substring(0,secret.indexOf(guess)) + guess + hidden.substring(secret.indexOf(guess)+1);
                            secret = secret.substring(0,secret.indexOf(guess)) + "*" + secret.substring(secret.indexOf(guess)+1);
                        }
                        hit = true;
                    }
                    if (hit == false){
                        //check if letter was already guessed
                        if (misses.indexOf(guess) != -1 || hidden.indexOf(guess) != -1){
                            System.out.println("Already guessed " + guess + "!");
                        }
                        //add the miss if the player was incorrect
                        else{
                            misses += guess + " ";
                            missedNum = (misses.replaceAll("\\s","")).length();
                        }
                    }
                    //create the hangman, set hit to false, and print any misses
                    makeMan(missedNum);
                    hit = false;
                    System.out.println("Misses: " + misses);

                    //win condition
                    if (hidden.indexOf("*") == -1){
                        win=true;
                        scan.close();
                        System.out.println("You Win!");
                        System.out.println("The word was: " + answer);
                    }
                    //lose condition
                    if (missedNum >5){
                        lose = true;
                        scan.close();
                        System.out.println("You Lose!");
                        System.out.println("The word was: " + answer);
                    }
                }
                else{
                    clearScreen();
                    makeMan(missedNum);
                    System.out.println("Please guess a valid character");
                }
            }
        }
    }
    public static String pickWord(int args){
        Scanner word = new Scanner(System.in);
        String secretWord = "";
        String[] wordList = {"secret", "hypertext", "turtle", "river", "among", "keyboard", "linux", "string", "dragon", "computer"};
        if (args == 1){
        int randomWord = (int) (Math.random()* 10);
            secretWord = wordList[randomWord];
        }
        else{
            System.out.println("Input secret word to guess: ");
            //get secret word
            secretWord = word.nextLine();
        }
        return secretWord;
    } 
    public static String makeDisplay(String secretWord) {
        //create * version of secretWord (hidden)
        String hidden = "";
        for (int i = 0; i < secretWord.length(); i++){
            if (secretWord.substring(i, i+1).equals(" ")){
                hidden += " ";
            }
            else{
                hidden += "*";
            }
        }
        return hidden;
    }
    public static void makeMan(int parts){
        //prints a different hanged man depending on misses
        System.out.println("--------");
        switch (parts){
            case 0:
                System.out.println("        |");
                System.out.println("        |");
                System.out.println("        |");
                break;
            case 1:
                System.out.println(" O      |");
                System.out.println("        |");
                System.out.println("        |");
                break;
            case 2:
                System.out.println(" O      |");
                System.out.println(" |      |");
                System.out.println("        |");
                break; 
            case 3:
                System.out.println(" O      |");
                System.out.println("/|      |");
                System.out.println("        |");
                break; 
            case 4:
                System.out.println(" O      |");
                System.out.println("/|\\     |");
                System.out.println("        |");
                break; 
            case 5:
                System.out.println(" O      |");
                System.out.println("/|\\     |");
                System.out.println("/       |");
                break; 
            case 6:
                System.out.println(" O      |");
                System.out.println("/|\\     |");
                System.out.println("/ \\     |");
                break; 
        }
        System.out.println("        |");
        System.out.println("     ------");
    }
    public static void clearScreen() {  
        //clears the screen, from https://stackoverflow.com/questions/2979383/how-to-clear-the-console
        System.out.print("\033[H\033[2J");  
        System.out.flush();  
    }  
    public static void helpScreen(){
        //displays the help screen and GNU GPL licence information
        System.out.println("Hangman Game by River Dyke, 2021. For AP Computer Science.");
        System.out.println("-----------------------------------------------------------------------");
        System.out.println("This software is licenced under The GNU General Public Licence v3, and comes with no warranty");
        System.out.println("-----------------------------------------------------------------------");
        System.out.println("Playing the game: ");
        System.out.println("The game starts by asking for a word. The first player should input a word or phrase for the second player to guess, and press enter.");
        System.out.println("The second player should now start playing by inputting a letter they think is in the word or phrase. If the player guesses incorrectly 6 times, the hangman has been hanged, and the game is over. If the player guesses every letter without hanging the hangman, they win.");
    }
    public static int argsHandle(String[] args){
        if (args.length == 0) {
            return 1;
        }
        if (args.length == 1 && args[0].equals("--help")) {
            helpScreen();
            return 0;
        }
        else if (args.length == 1 && args[0].equals("--random")) {
            return 2;
        }
        else if (args.length > 1){
            System.out.println("Hangman By River Dyke");
            System.out.println("Try \'java hangman --help\' for more information");
            return 0;
        }
        else {
            return 0;
        }
    }

}