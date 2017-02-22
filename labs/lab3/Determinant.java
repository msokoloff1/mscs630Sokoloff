import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author matthewsokoloff
 */
public class Determinant{
    
 
   
  public static void main(String[] args) {
    Scanner input = new Scanner(System.in);
    String line = "";
    String[] numbers;
    while(input.hasNext()) {
      line = input.nextLine();
      numbers = line.split(" ");
    }
  }

  public static int cofModDet(int m, int[][] A) {
    ArrayList<int[][]> cofs = new ArrayList<int[][]>();
    cofs.add(A);
    return Determinant.cofRecursive(m, cofs);
  }

  public static int n2Det(int[][] A) {
    return (A[0][0] * A[1][1]) - (A[1][0] * A[0][1]);
  }

  public static int cofRecursive(int m, ArrayList<int[][]> A) {
    
    boolean reducing = true;
    int result = 0;
    
    while(reducing){
      ArrayList<int[][]> expanded = new ArrayList<int[][]>();
      
    //For each matrix, expand and add to expanded. Then return the result
      for(int[][] matrix : A) {
        
        if(matrix.length == 2)
            result += Determinant.n2Det(matrix);
           
        //Iterate over each column to get the scaler multiple and smaller matrix.
        //Get the remainder (mod n)
        for(int colIndex = 0; colIndex < matrix.length; colIndex++) {
          int scaler = matrix[colIndex][0] * ( (-1) ^colIndex);
          int[][] newMatrix = new int[matrix.length-2][matrix.length-2];
            //Iterate over all columns again. Do not include the current column
            for(int colIndexNest = 0; colIndexNest < matrix.length; colIndexNest++) {
                if(colIndexNest != colIndex){
                    newMatrix[colIndexNest] = slice(1, matrix.length-1, matrix[colIndexNest]);
                }
            }
            expanded.add(Determinant.modScalerMatMul(newMatrix, m, scaler));

          }
        }
      
      if (result != 0){
          reducing = false;
      }
      
      A = expanded;
      expanded = new ArrayList<>();
    }
       
    return result;
  }
  
  public static int[][] modScalerMatMul(int[][] matrix,int mod, int scaler){
      for(int[] col: matrix) {
        for(int element: col) {
           element = ((element*scaler)%mod);
        }
      }
      return matrix;
    }
  
  public static int[] slice(int start, int end, int[] array) {
      int range = end - start + 1;
      int[] result = new int[range];
      for (int index = 0; index< range; index++){
          result[index] = array[start+index];
      }
      
      return result;
      
  }
  

}  

