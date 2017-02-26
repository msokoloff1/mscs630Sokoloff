/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package faround;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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
    int[][] test = new int[][]{{-1,0, 9, 8}
                             , {6,7, 5, 6}
                             , {4,9, 8, 7}
                             , {8,9, 9, 9}};
    
    
    
    int mod = 25;
    ArrayList<int[][]> testing = new ArrayList<>();
    testing.add(test);
    
    int finalValue = cofRecursive(10000,testing);
    print("FINAL VALUE : " + finalValue);
    
    System.exit(0);
    /*while(input.hasNext()) {
      line = input.nextLine();
      numbers = line.split(" ");
      
    }
            */
  }

  public static int cofModDet(int m, int[][] A) {
    ArrayList<int[][]> cofs = new ArrayList<int[][]>();
    cofs.add(A);
    return Determinant.cofRecursive(m, cofs);
  }

  public static int n2Det(int[][] A, int scaler) {
      /*System.out.println("A[0][0] ---- " + A[0][0]);
      System.out.println("A[1][1] ---- " + A[1][1]);
      System.out.println("A[0][1] ---- " + A[0][1]);
      System.out.println("A[1][0] ---- " + A[1][0]);
*/
      int result = (A[0][0] * A[1][1]) - (A[1][0] * A[0][1]);
  //    print(scaler + "<= scaler");
  //    print("Result " + result*scaler);
    return result*scaler;
  }

  public static int cofRecursive(int m, ArrayList<int[][]> A){
    boolean reducing = true;
    int result = 0;
    int everyOther = 0;
    boolean once = true;
    
    ArrayList<Integer> scalers = new ArrayList<Integer>();
    ArrayList<Integer> resScalers = new ArrayList<Integer>();
    
    while(reducing){
      ArrayList<int[][]> expanded = new ArrayList<int[][]>();
      
      
      
    //For each matrix, expand and add to expanded. Then return the result
      int matrixIndex = 0;
      for(int[][] matrix : A) {
          
        if(matrix.length == 2){
            reducing = false;
         //   print("REDUCING:");
            print("====");
            print2(matrix);
            print("====");
            if(everyOther%2 == 0){
                result += Determinant.n2Det(matrix, resScalers.get(everyOther));
            }else{
                result += Determinant.n2Det(matrix, resScalers.get(everyOther));
            }
            everyOther++;
        }else{
           
        //Iterate over each column to get the scaler multiple and smaller matrix.
        //Get the remainder (mod n)
        for(int colIndex = 0; colIndex < matrix.length; colIndex++) {
          int scaler =matrix[0][colIndex]* (int)Math.pow(-1, colIndex);
          if(!once){
              print(resScalers.size() + " " + matrixIndex);
              scaler = resScalers.get(matrixIndex)*scaler;
          }
          
          
          
          int[][] newMatrix = new int[matrix.length-1][matrix.length-1];
            //Iterate over all columns again. Do not include the current column
            int count = 0;
            for(int colIndexNest = 0; colIndexNest < matrix.length; colIndexNest++) {
                if(colIndexNest != colIndex){        
                    int[] res = Determinant.slice(1, matrix.length-1, matrix, colIndexNest);
                    newMatrix[count] = res;
                    count++;
                }
            }
            expanded.add(Determinant.modScalerMatMul(newMatrix, m));
            scalers.add(scaler);
          }
        }
        matrixIndex++;
      }
      once = false;
      matrixIndex = 0;
      A = expanded;
      expanded = new ArrayList<>();
      print(" " + scalers.size());
      resScalers = copyList(scalers);
      scalers = new ArrayList<Integer>();
    }
    return result;
  }
  
  
  public static ArrayList<Integer> copyList(ArrayList<Integer> list){
      ArrayList<Integer> copy = new ArrayList<Integer>();
    for (Integer foo: list) {
    copy.add(foo);
    }
    return copy;
  }
  
  public static int[][] modScalerMatMul(int[][] matrix,int mod){
      for(int row=0; row<matrix.length; row++){
          for(int col = 0; col < matrix[0].length; col++){
              matrix[row][col] = ((matrix[row][col])%mod);
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

