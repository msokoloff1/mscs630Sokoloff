/*
*Matt Sokoloff
*MSCS630
*Lab 2 (part2)
*
*This program applies the more efficient, extended euclidean algorithm to a given input.
*
*/

import java.util.Scanner;

public class EuclideanAlgorithmExt {

  public static void main(String[] args) {
    Scanner input = new Scanner(System.in);
    String line = "";
    String[] numbers;
    while(input.hasNext()) {
      line = input.nextLine();
      numbers = line.split(" ");
      if(numbers.length != 2)
        throw new IllegalStateException("Number of elements per line is not 2");
      long[] result = EuclideanAlgorithmExt.euclidAlgExt(Long.parseLong(numbers[0]),Long.parseLong(numbers[1]));
      System.out.println("GCD: " + result[0] + ", x: " + result[1] + ", y: " + result[2] );
    }
  }

  public static long[] euclidAlgExt(long a, long b) {
    long[] x = {0,1};
    long[] y = {1,0};
    long[] r = {b,a};
    while(r[0] != 0) {
      long q = (long) r[1]/r[0];
      x = EuclideanAlgorithmExt.updateValue(x[1], x[0], q);
      y = EuclideanAlgorithmExt.updateValue(y[1], y[0], q);
      r = EuclideanAlgorithmExt.updateValue(r[1], r[0], q);
    }
    long[] result = {r[1], x[1], y[1]};
    return result;
  }

  private static long[] updateValue(long prev,long current,long quotient) {
    long[] result = {0,0};
    result[1] = current;
    result[0] = prev - quotient * current;
    return result;
  }
}
