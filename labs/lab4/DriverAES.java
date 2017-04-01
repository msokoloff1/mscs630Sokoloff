import java.util.Arrays;
import java.util.Scanner;

/**
 *
 * @author matthewsokoloff
 */
public class DriverAES {

    public static void main(String[] args) {
        /*Scanner input = new Scanner(System.in);
        String keyHex = input.nextLine().toUpperCase();
        arrayPrint(AESCipher.aesRoundKeys(keyHex));*/
        String keyHex = "5468617473206D79204B756E67204675";   
        String result = AESCipher.aesRoundKeys(keyHex);
        System.out.println(result);
                
    } 
}
