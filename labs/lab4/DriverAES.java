/*
 * @author Matthew Sokoloff
 * AES, Lab 4, MSCS630
 * DriverAES Class
 */


import java.util.Scanner;

/*
*Reads in 16 2-digit hex numbers from an external source
*Then prints out the resulting 11 keys composed of 16 2-digit hex numbers
*/

public class DriverAES {
    
  public static void main(String[] args) {
    Scanner input = new Scanner(System.in);
    String keyHex = input.nextLine().toUpperCase();
    long startTime = System.nanoTime();
    System.out.println("Hex Input: \n" + keyHex );
    System.out.println("\n11 Resulting Keys: \n" + AESCipher.aesRoundKeys(keyHex));
    long elapsedInMS = (System.nanoTime() - startTime)/ 1000000;
    System.out.println("\nElapsed Time : " + elapsedInMS +" ms");          
  } 
  
}

