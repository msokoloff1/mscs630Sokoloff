/*
*Matt Sokoloff
*MSCS630
*Lab 2 (part 1)
*
*This program applies the euclidean algorithm to a given input.
*
*/

import java.util.Scanner;

public class EuclideanAlgorithm {
  
  public static void main(String[] args) {
    Scanner input = new Scanner(System.in);
    String line = ""; 
    String[] numbers;
    while(input.hasNext()) {
      line = input.nextLine();
      numbers = line.split(" ");
      if(numbers.length != 2)
        throw new IllegalStateException("Number of elements per line is not 2");
      System.out.println(EuclideanAlgorithm.euclidAlgExt(Long.parseLong(numbers[0]),Long.parseLong(numbers[1])));   
    }  
  }

  public static long euclidAlgExt(long a, long b) {
    if(b == 0)
      return a;

    return EuclideanAlgorithm.euclidAlgExt(b, a%b);
  }
}  
