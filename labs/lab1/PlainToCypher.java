/*
*Matt Sokoloff
*MSCS630
*Lab 1
*
*This program converts a given string of plaintext into cypher text
*
*/

import java.util.Scanner;

public class PlainToCypher {

  public static void main(String[] args) {
    while(true) {
      Scanner input = new Scanner(System.in);
      System.out.println("Please Enter the string you want to encrypt or enter q to quit");
      String userInput = input.next().toLowerCase();
      if(userInput.charAt(0) == 'q')
        break;
      int[] result = PlainToCypher.str2int(userInput);
      for (int numberEquivalent : result) { 
        System.out.print(numberEquivalent + " ");
      }
      System.out.print("\n");
    }
  }

  public static int[] str2int(String inputStr) {
    int[] result = new int[inputStr.length()];
    char[] inputAsChars = inputStr.toCharArray();
    for (int index = 0; index < inputAsChars.length; index++) {
      result[index] = ((int) inputAsChars[index]) -97;
    }
    return result;
  }
}
