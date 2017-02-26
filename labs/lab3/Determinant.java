
/**
 * @author matthewsokoloff (mscs630, spring 2017)
 * This program can be used to calculate the determinant of 
 * any arbitrarily sized square matrix in any modular number system.
 * 
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Determinant{
    
  public static void main(String[] args) {      
    Scanner input = new Scanner(System.in);
    String line = "";
    String[] numbers;
    ArrayList<String[]> lineData = new ArrayList<>();
    
    while(input.hasNext()) {
      line = input.nextLine();
      numbers = line.split(" ");
      lineData.add(numbers);
    }
    
    String[] line1 = lineData.get(0);
    int mod = Integer.parseInt(line1[0]);
    int size = Integer.parseInt(line1[1]);
    int[][] matrix = new int[size][size];
    
    for (int lineIndex = 0; lineIndex < size; lineIndex++) {
      String[] currentLine =  lineData.get(lineIndex+1);
      for (int rowIndex = 0; rowIndex < size; rowIndex++) {
        matrix[lineIndex][rowIndex] = Integer.parseInt(currentLine[rowIndex]);
      }
    }
    
    int finalValue = cofModDet(mod,matrix);
    
    while(finalValue < 0) {
      finalValue += mod;
    }

    System.out.println("Original Matrix:");
    System.out.println(Arrays.deepToString(matrix));
    System.out.println("The determinant of your matrix in mod " + mod + " is : " + finalValue);
  }

  public static int cofModDet(int m, int[][] A) {
    ArrayList<int[][]> cofs = new ArrayList<>();
    cofs.add(A);
    return (getDeterminant(cofs) % m);
  }

  public static int n2Det(int[][] A, int scaler) {
    int result = (A[0][0] * A[1][1]) - (A[1][0] * A[0][1]);
    return result*scaler;
  }

  public static int getDeterminant(ArrayList<int[][]> A) {
    boolean reducing = true;
    int result = 0;
    int scalerMultipleIndex = 0;
    boolean once = true;
    ArrayList<Integer> scalers = new ArrayList<>();
    ArrayList<Integer> resScalers = new ArrayList<>();
    while(reducing){
      ArrayList<int[][]> expanded = new ArrayList<>();
      int matrixIndex = 0;
      for(int[][] matrix : A) {
        if(matrix.length == 2) {
          reducing = false;
          result += n2Det(matrix, resScalers.get(scalerMultipleIndex));
          scalerMultipleIndex++;
        }else {
          for(int colIndex = 0; colIndex < matrix.length; colIndex++) {
            int scaler =matrix[0][colIndex]* (int)Math.pow(-1, colIndex);
            if(!once) {
              scaler = resScalers.get(matrixIndex)*scaler;
            }
            int[][] newMatrix = new int[matrix.length-1][matrix.length-1];
            int count = 0;
            for(int colIndexNest = 0; colIndexNest < matrix.length; colIndexNest++) {
              if(colIndexNest != colIndex) {        
                int[] res = Determinant.slice(1, matrix.length-1, matrix, colIndexNest);
                newMatrix[count] = res;
                count++;
              }
            }
            expanded.add(newMatrix);
            scalers.add(scaler);
          }
        }
        matrixIndex++;
      }
      once = false;
      matrixIndex = 0;
      A = expanded;
      expanded = new ArrayList<>();
      resScalers = copyList(scalers);
      scalers = new ArrayList<>();
    }
    return result;
  }
  
  public static ArrayList<Integer> copyList(ArrayList<Integer> list) {
    ArrayList<Integer> copy = new ArrayList<>();
    list.stream().forEach((foo) -> {
      copy.add(foo);
    });
    return copy;
  }
 
  public static int[] slice(int start, int end, int[][] array, int column) {
    int range = (end) - start;
    int[] result = new int[range+1]; 
    
    for (int index = 0; index< range + 1; index++) {
      result[index] = array[start+index][column];
    }   
    return result;  
  }
}
