//Hangman Game by River Dyke, 2021. For AP Computer Science.
import java.util.Scanner;
public class hangman {
    //main method, plays the game until the player doesn't want to anymore
    public static void main(String[] args){
        Scanner scan = new Scanner(System.in);
        final int a = argsHandle(args);
        if (a > 0) {
            while(true){
                playGame(scan, a);
                if (playAgain(scan)){
                    continue;
                }
                else{
                    break;
                }
            }
        }
        scan.close();
    }
    //run the game code
    public static void playGame(Scanner scan, int a){
        initialize();
        final String secret = pickWord(scan, a);
        String hidden = makeDisplay(secret);
        String guess = "";
        String alreadyGuessed = "";
        String misses = "";
        int missedNum = 0;
        makeMan(0);
        while (!checkWin(hidden, secret) && !checkLose(missedNum, secret)){
            System.out.println("Word: " + hidden);
            guess = getInput(scan);
            if (checkValid(guess)){
                if (checkNew(guess, alreadyGuessed)){
                    if (checkCorrect(secret, guess)){
                        hidden = unHide(secret, hidden, guess);
                    }
                    else{
                        misses += guess + " ";
                        missedNum = (misses.replaceAll("\\s","")).length();
                    }
                }
                alreadyGuessed += guess;
            }
            printInfo(missedNum, misses);
        }
    }
    //ask the player if they want to play again
    public static boolean playAgain(Scanner scan){
        System.out.println("Play Again? (y/n): ");
        String response = scan.nextLine();
        if ((response.toLowerCase().strip()).equals("y")){
            return true;
        }
        else {
            return false;
        }
        
    }
    public static void initialize(){
        clearScreen();
        System.out.println("Hangman by River Dyke");
        System.out.println("--------------------------");
    }
    //pick the word, either from the user or randomly from a list, depending on arguments
    public static String pickWord(Scanner scan, int args){
        String secretWord = "";
        String[] wordList = {"secret", "hypertext", "turtle", "river", "among", "keyboard", "linux", "string", "dragon", "computer"};
        if (args == 2){
        int randomWord = (int) (Math.random()* 10);
            secretWord = wordList[randomWord];
        }
        else{
            System.out.println("Input secret word to guess: ");
            //get secret word
            secretWord = scan.nextLine();
        }
        return secretWord;
    } 
    //create the hidden version of the word, made of * and spaces
    public static String makeDisplay(String secretWord) {
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
    //get the user's guess
    public static String getInput(Scanner scan){
        System.out.println("Guess: ");
        String guess = scan.nextLine();
        return guess;
    }
    //check if the guess was valid
    public static boolean checkValid(String guess){
        if (guess.indexOf("*") == -1 && guess.indexOf(" ") == -1 && guess.length() == 1 ){
            return true;
        }
        else {
            clearScreen();
            System.out.println("Please guess a valid character");
            return false;
        }
    }
    //check if the user has guessed the letter before
    public static boolean checkNew(String guess, String alreadyGuessed){
        if (alreadyGuessed.indexOf(guess) == -1){
            return true; 
        }
        else {
            clearScreen();
            System.out.println("Already Guessed " + guess + "!");
            return false;
        }


    }
    //check if the guess was correct
    public static boolean checkCorrect(String secret, String guess){
        if (secret.indexOf(guess) == -1){
            clearScreen();
            return false;
        }
        else {
            clearScreen();
            return true;
        }
    }
    //replace the *s in hidden with the guessed letter
    public static String unHide(String secret, String hidden, String guess){
        String secretIndex = "";
        for (int i = 0; i<secret.length(); i++){
            secretIndex = secret.substring(i,i+1);
            if (secretIndex.equals(guess)){
                hidden = hidden.substring(0,i) + guess + hidden.substring(i+1);
            }
        }
        return hidden;
    }

    public static void printInfo(int missedNum, String misses){
        makeMan(missedNum);
        System.out.println("Misses: " + misses);
    }
    //check if the player has won
    public static boolean checkWin(String hidden, String answer){
        if (hidden.indexOf("*") == -1){
            System.out.println("You Win!");
            System.out.println("The word was: " + answer);
            return true;
        }
        else{
            return false;
        }
    }
    //check if the player has lost
    public static boolean checkLose(int missedNum, String answer){
        if (missedNum >5){
            System.out.println("You Lose!");
            System.out.println("The word was: " + answer);
            return true;
        }
        else{
            return false;
        }
    }
    //make the hangman
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
    //clears the screen, from https://stackoverflow.com/questions/2979383/how-to-clear-the-console
    public static void clearScreen() {  
        System.out.print("\033[H\033[2J");  
        System.out.flush();  
    }  
    //displays the help screen and GNU GPL information
    public static void helpScreen(){
        System.out.println("Hangman Game by River Dyke, 2021. For AP Computer Science.");
        System.out.println("-----------------------------------------------------------------------");
        System.out.println("This software is licenced under The GNU General Public Licence v3, and comes with no warranty");
        System.out.println("-----------------------------------------------------------------------");
        System.out.println("Playing the game: ");
        System.out.println("The game starts by asking for a word. The first player should input a word or phrase for the second player to guess, and press enter.");
        System.out.println("The second player should now start playing by inputting a letter they think is in the word or phrase. If the player guesses incorrectly 6 times, the hangman has been hanged, and the game is over. If the player guesses every letter without hanging the hangman, they win.");
    }
    //handle command line arguments
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
        else {
            System.out.println("Hangman By River Dyke");
            System.out.println("Try \'java hangman --help\' for more information");
            return 0;
        }
    }

}