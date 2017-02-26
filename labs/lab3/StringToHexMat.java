/**
 * @author matthewsokoloff (mscs630, spring 2017)
 * This program turns any arbitrarily sized string into a series of
 * padded four by four matrices containing the byte representations of 
 * the original string.
 */

import java.util.ArrayList;
import java.util.Scanner;


public class StringToHexMat {
    
  public static void main(String[] args) {
    Scanner input = new Scanner(System.in);
    char s = input.nextLine().charAt(0);
    String plainText = input.nextLine();
    input.close();
    ArrayList<int[][]> HexMatrices = new ArrayList<>();
    
    while(plainText.length()>0){
      int index = Math.min(16, plainText.length());
      String truncatedPlainText = plainText.substring(0, index);
      HexMatrices.add(getHexMatP(s,truncatedPlainText));
      plainText = plainText.substring(index, plainText.length());
    }
   
    HexMatrices.stream().forEach((matrix) -> {
      System.out.println(baseTenToHex(matrix));
    });
    
  }
  
  public static String baseTenToHex(int[][] baseTenArray){
    String result = "";
    for (int[] baseTenArray1 : baseTenArray) {
      result += "[";
      for (int colIndex = 0; colIndex < baseTenArray.length; colIndex ++) {
          result += Integer.toHexString(baseTenArray1[colIndex]) + " ";
      }
      result += "]\n";
    }
    return result;
  }
    
  public static int[][] getHexMatP(char s, String p) {
    int[][] result = new int[4][4];
    while(p.length()<16) {
      p+=s;
    }
    for(int colIndex = 0; colIndex < 4; colIndex ++) {
      for(int rowIndex = 0; rowIndex < 4; rowIndex ++) {
        result[rowIndex][colIndex] = (int) p.charAt(rowIndex + (4*colIndex));
      }
    }
    return result;
  }   
}

