/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package faround;

import java.util.ArrayList;
import java.util.Arrays;
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
    //[row][column] //det = 1*(6-1) + all zeros = 5
    int[][] test = new int[][]{{1, 2, 3}
                             , {4, 5, 6}
                             , {7, 8, 9}};
    
    
    int mod = 25;
    ArrayList<int[][]> testing = new ArrayList<>();
    testing.add(test);
    cofRecursive(10000,testing);
    
    System.exit(0);
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
      /*System.out.println("A[0][0] ---- " + A[0][0]);
      System.out.println("A[1][1] ---- " + A[1][1]);
      System.out.println("A[0][1] ---- " + A[0][1]);
      System.out.println("A[1][0] ---- " + A[1][0]);*/
    return (A[0][0] * A[1][1]) - (A[1][0] * A[0][1]);
  }

  public static int cofRecursive(int m, ArrayList<int[][]> A) {
    boolean reducing = true;
    int result = 0;
    
    while(reducing){
      ArrayList<int[][]> expanded = new ArrayList<int[][]>();
      
      
    //For each matrix, expand and add to expanded. Then return the result
      for(int[][] matrix : A) {
          
        
        if(matrix.length == 2){
            result += Determinant.n2Det(matrix);
        }else{
           
        //Iterate over each column to get the scaler multiple and smaller matrix.
        //Get the remainder (mod n)
        for(int colIndex = 0; colIndex < matrix.length; colIndex++) {
          int scaler = matrix[0][colIndex];// * ( (-1) ^colIndex);
          int[][] newMatrix = new int[matrix.length-1][matrix.length-1];
            //Iterate over all columns again. Do not include the current column
            
            int count = 0;
            for(int colIndexNest = 0; colIndexNest < matrix.length; colIndexNest++) {
                print("Col Index Nest " + colIndexNest);
                if(colIndexNest != colIndex){        
                                                        //This is getting the row not the column!!!
                    int[] res = Determinant.slice(1, matrix.length-1, matrix, colIndexNest);
                    newMatrix[count] = res;
                    
                    print2(newMatrix);
                    print("++++++++"); 
                    count++;
                }
            }
            
            //The scalers are right. The sizes are right.... now the matrix elements need to be corrected
            
            expanded.add(Determinant.modScalerMatMul(newMatrix, m, scaler));

          }
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
  
  public static int[] slice(int start, int end, int[][] array, int column) {
      int range = (end) - start;
      int[] result = new int[range+1];
      
      for (int index = 0; index< range + 1; index++){
          result[index] = array[start+index][column];
      }
      return result;
      
  }
   
  
  public static void print2(int[][] array){
      System.out.println(Arrays.deepToString(array));
  }
  
  public static void print1(int[] array){
      System.out.println(Arrays.toString(array));
  }
  
  public static void print(String s){
      System.out.println(s);
  }
  

}  

