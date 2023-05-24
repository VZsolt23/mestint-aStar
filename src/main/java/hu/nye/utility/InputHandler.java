package hu.nye.utility;

import java.util.Scanner;

public class InputHandler {

    public String inputReader() {
        Scanner sc = new Scanner(System.in);
        return sc.nextLine();
    }

    public char[] handleInput(String input) {
        String[] letters = input.split(" ");
        char[] lettersChar = new char[2];

        if (letters.length == 2) {
            lettersChar[0] = letters[0].toCharArray()[0];
            lettersChar[1] = letters[1].toCharArray()[0];
            return lettersChar;
        } else {
            System.out.println("Nem megfelelő az input!");
            return null;
        }
    }

}
