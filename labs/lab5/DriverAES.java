/*
 * * @author Matthew Sokoloff
 * AES, Lab 5, MSCS630
 * DriverAES Class
 */


import java.util.Arrays;
import java.util.Scanner;

/*
*Reads in 16 2-digit hex numbers from an external source
*Then prints out the resulting 11 keys composed of 16 2-digit hex numbers
*/

public class DriverAES {
    
  public static void main(String[] args) {
    Scanner input = new Scanner(System.in);
    String key = input.nextLine().toUpperCase();
    String pText = input.nextLine().toUpperCase();
    long startTime = System.nanoTime();
    System.out.println("Hex Input: \n" + key );
    System.out.println("\n11 Resulting Keys: \n" + AESCipher.aesRoundKeys(key));
    System.out.println("Result:");
    System.out.println(Arrays.deepToString(AESCipher.aes(pText, key)));
    long elapsedInMS = (System.nanoTime() - startTime)/ 1000000;
    System.out.println("\nElapsed Time : " + elapsedInMS +" ms"); 
  } 
}

