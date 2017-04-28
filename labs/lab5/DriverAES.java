/*
r Matthew Sokoloff
 * AES, Lab 4, MSCS630
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
    //Scanner input = new Scanner(System.in);
    //String keyHex = input.nextLine().toUpperCase();
    /*String keyHex = "";
    long startTime = System.nanoTime();
    System.out.println("Hex Input: \n" + keyHex );
    System.out.println("\n11 Resulting Keys: \n" + AESCipher.aesRoundKeys(keyHex));
    long elapsedInMS = (System.nanoTime() - startTime)/ 1000000;
    System.out.println("\nElapsed Time : " + elapsedInMS +" ms"); 
            */
    boolean realTest = true;
      if(realTest){
      String[][] testCase = {{"63","EB","9F","A0"},{"C0","2F","93","92"},{"AB","30","AF","C7"},{"20","CB","2B","A2"}};
    
    String[][] shifted = AESCipher.AESShiftRow(testCase);
    System.out.println(Arrays.deepToString(AESCipher.AESMixColumn(shifted)));
    String[][] answer = { {"ba", "84", "e8", "1b"}
                        , {"75", "a4", "8d", "40"}
                        , {"f4", "8d", "06", "7d"}
                        , {"7a", "32", "0e", "5d"}};
    
    System.out.println(Arrays.deepToString(answer));
            }else{
                           //0                   2
      String[] vector = {"11010100","10111111","01011101","00110000"};
           String[][] matBox = {{"00000010","00000011","00000001","00000001"}
                          ,{"00000001","00000010","00000011","00000001"}
                          ,{"00000001","00000001","00000010","00000011"}
                          ,{"00000011","00000001","00000001","00000010"}};
           
      String[] result = AESCipher.multiplyWithForLoops(matBox,vector);
      for(int i =0 ; i<4; i++){
          int decimal = Integer.parseInt(result[i],2);
          result[i] = Integer.toString(decimal,16);
            
      }
      System.out.println(Arrays.toString(result));
  } }
  
}

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

