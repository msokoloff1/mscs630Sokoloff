
import java.util.stream.IntStream;


/*
 * @author Matthew Sokoloff
 * AES, Lab 4, MSCS630
 * AESCipher Class
 */


public class AESCipher {
    
  private static final String[][] S_BOX = {
   { "63", "7C", "77", "7B", "F2", "6B", "6F", "C5", "30", "01", "67", "2B", "FE", "D7", "AB", "76" },
   { "CA", "82", "C9", "7D", "FA", "59", "47", "F0", "AD", "D4", "A2", "AF", "9C", "A4", "72", "C0" },
   { "B7", "FD", "93", "26", "36", "3F", "F7", "CC", "34", "A5", "E5", "F1", "71", "D8", "31", "15" },
   { "04", "C7", "23", "C3", "18", "96", "05", "9A", "07", "12", "80", "E2", "EB", "27", "B2", "75" },
   { "09", "83", "2C", "1A", "1B", "6E", "5A", "A0", "52", "3B", "D6", "B3", "29", "E3", "2F", "84" },
   { "53", "D1", "00", "ED", "20", "FC", "B1", "5B", "6A", "CB", "BE", "39", "4A", "4C", "58", "CF" },
   { "D0", "EF", "AA", "FB", "43", "4D", "33", "85", "45", "F9", "02", "7F", "50", "3C", "9F", "A8" },
   { "51", "A3", "40", "8F", "92", "9D", "38", "F5", "BC", "B6", "DA", "21", "10", "FF", "F3", "D2" },
   { "CD", "0C", "13", "EC", "5F", "97", "44", "17", "C4", "A7", "7E", "3D", "64", "5D", "19", "73" },
   { "60", "81", "4F", "DC", "22", "2A", "90", "88", "46", "EE", "B8", "14", "DE", "5E", "0B", "DB" },
   { "E0", "32", "3A", "0A", "49", "06", "24", "5C", "C2", "D3", "AC", "62", "91", "95", "E4", "79" },
   { "E7", "C8", "37", "6D", "8D", "D5", "4E", "A9", "6C", "56", "F4", "EA", "65", "7A", "AE", "08" },
   { "BA", "78", "25", "2E", "1C", "A6", "B4", "C6", "E8", "DD", "74", "1F", "4B", "BD", "8B", "8A" },
   { "70", "3E", "B5", "66", "48", "03", "F6", "0E", "61", "35", "57", "B9", "86", "C1", "1D", "9E" },
   { "E1", "F8", "98", "11", "69", "D9", "8E", "94", "9B", "1E", "87", "E9", "CE", "55", "28", "DF" },
   { "8C", "A1", "89", "0D", "BF", "E6", "42", "68", "41", "99", "2D", "0F", "B0", "54", "BB", "16" } 
  };

  private static final String[][] R_CON = {
   { "8D", "01", "02", "04", "08", "10", "20", "40", "80", "1B", "36", "6C", "D8", "AB", "4D", "9A" },
   { "2F", "5E", "BC", "63", "C6", "97", "35", "6A", "D4", "B3", "7D", "FA", "EF", "C5", "91", "39" },
   { "72", "E4", "D3", "BD", "61", "C2", "9F", "25", "4A", "94", "33", "66", "CC", "83", "1D", "3A" },
   { "74", "E8", "CB", "8D", "01", "02", "04", "08", "10", "20", "40", "80", "1B", "36", "6C", "D8" },
   { "AB", "4D", "9A", "2F", "5E", "BC", "63", "C6", "97", "35", "6A", "D4", "B3", "7D", "FA", "EF" },
   { "C5", "91", "39", "72", "E4", "D3", "BD", "61", "C2", "9F", "25", "4A", "94", "33", "66", "CC" },
   { "83", "1D", "3A", "74", "E8", "CB", "8D", "01", "02", "04", "08", "10", "20", "40", "80", "1B" },
   { "36", "6C", "D8", "AB", "4D", "9A", "2F", "5E", "BC", "63", "C6", "97", "35", "6A", "D4", "B3" },
   { "7D", "FA", "EF", "C5", "91", "39", "72", "E4", "D3", "BD", "61", "C2", "9F", "25", "4A", "94" },
   { "33", "66", "CC", "83", "1D", "3A", "74", "E8", "CB", "8D", "01", "02", "04", "08", "10", "20" },
   { "40", "80", "1B", "36", "6C", "D8", "AB", "4D", "9A", "2F", "5E", "BC", "63", "C6", "97", "35" },
   { "6A", "D4", "B3", "7D", "FA", "EF", "C5", "91", "39", "72", "E4", "D3", "BD", "61", "C2", "9F" },
   { "25", "4A", "94", "33", "66", "CC", "83", "1D", "3A", "74", "E8", "CB", "8D", "01", "02", "04" },
   { "08", "10", "20", "40", "80", "1B", "36", "6C", "D8", "AB", "4D", "9A", "2F", "5E", "BC", "63" },
   { "C6", "97", "35", "6A", "D4", "B3", "7D", "FA", "EF", "C5", "91", "39", "72", "E4", "D3", "BD" },
   { "61", "C2", "9F", "25", "4A", "94", "33", "66", "CC", "83", "1D", "3A", "74", "E8", "CB", "8D" } 
  };
    
  /*
  *@param keyHex : 16 2-digit hex numbers
  *Returns a string contianing 11 keys composed of 16 hex numbers
  */
  public static String aesRoundKeys(String keyHex) {
    int NUM_NIBBLES = 2;
    int squareMatrixDims = (int) Math.sqrt(keyHex.length()/NUM_NIBBLES);
    String[][] keyMatrix = make2D(keyHex, squareMatrixDims);
    String[][] wMatrix = makeWMatrix(keyMatrix);
    String result = "";
    int count = 0;
    for(int box = 0; box<11; box++) {
      for(int col =(box*4); col<(box*4)+4; col++) {
        for(int row = 0; row< 4; row++) {
          result += wMatrix[row][col].toUpperCase();
        }
      }
      result +="\n";
    }
    return result;
  }
    
  /*
  *@param keyHex : 16 2-digit hex numbers
  *@squareMatrixDims : x and y dimension for one round's key
  *Returns a two dimensional array of the input key
  */
  private static String[][] make2D(String keyHex, int squareMatrixDims) {
    String[][] keyMatrix = new String[squareMatrixDims][squareMatrixDims];
    int row = 0, col = 0;
    for (int index = 0; index < keyHex.length(); index += 2) {
      keyMatrix[row][col] = "" + keyHex.charAt(index) + keyHex.charAt(index+1);
      row++;
      if(row == 4) {
        row = 0;
        col++;
      }
    }
    return keyMatrix;
  }
    
  /*
  *@param keyMatrix : 16 2-digit hex numbers, in a 4x4 array
  *Returns a two dimensional array of containing all 11 keys
  */
  private static String[][] makeWMatrix(String[][] keyMatrix) {
    int NUM_ROUNDS = 11;
    String[][] wMatrix = new String[4][NUM_ROUNDS*4];
    //Set round zero
    for (int r = 0; r < 4; r++) {
      System.arraycopy(keyMatrix[r], 0, wMatrix[r], 0, 4);
    }
    //All subsequent rounds
    for(int j = 4; j <44; j++ ) {
      if(j%4 != 0) {
        for(int row = 0; row < 4; row ++) {
          wMatrix[row][j] = xOr(wMatrix[row][j-4], wMatrix[row][j-1]);
          }
      }else {
        String[] wNew = new String[4];
        for(int row = 0; row < 4; row++) {
          //Shift in place
          wNew[((row-1)+4)%4] = aesSBox(wMatrix[row][j-1]);                    
        }   
        String rconVal = aesRcon(j);
        wNew[0] = xOr(rconVal, wNew[0]);
     
        for(int row = 0; row< 4; row++) {
          wMatrix[row][j] = xOr(wMatrix[row][j-4], wNew[row]);
        }
      }
    }
    return wMatrix;
  }
    
    
  /*
  *@param inHex: 2-digit hex number
  *returns the s-box value whose row index corresponds to the first digit
  *  of the hex number and column whose index corresponds to 
  *   the second digit of the hex number.
  */
  private static String aesSBox(String inHex) {
    String formatted = String.format("%2s", inHex).replace(' ', '0');
    int rowIndex = Integer.parseInt(""+formatted.charAt(0), 16);
    int colIndex = Integer.parseInt(""+formatted.charAt(1), 16);
    return S_BOX[rowIndex][colIndex];
  }
    
  /*
  *@param round : The index number for the key currently being produced
  *Returns the correct value of the r-con array depending on the round.
  */
  private static String aesRcon(int round) {
    return R_CON[0][round / 4];
 }
  
  
  
  /*
  *@param value1 : 2-digit Hex number
  *@param value2 : 2-digit Hex number
  *Returns the result of applying the exlusive or to both inputs
  */  
  private static String xOr(String value1, String value2) {
    int value1Decimal = Integer.parseInt(value1, 16);
    int value2Decimal = Integer.parseInt(value2, 16);
    String bin1 = String.format("%8s", Integer.toBinaryString(value1Decimal)).replace(' ', '0');
    String bin2 = String.format("%8s", Integer.toBinaryString(value2Decimal)).replace(' ', '0');
    StringBuilder sb = new StringBuilder();
    for(int index = 0; index < bin1.length(); index ++ ) {
      sb.append((bin1.charAt(index) !=  bin2.charAt(index)) ? 1 : 0);
    }
    int decimal = Integer.parseInt(sb.toString(),2);
    String hexStr = Integer.toString(decimal,16);
    return hexStr + "";
  }
  
  
  
  /*
  
  NEW SHIT!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
  
  */
  
  public static String[][] AESStateXOR(String[][] sHex,String[][] keyHex){
      String[][] results = new String[4][4];
      for(int i = 0; i < 4; i++){
          for(int j = 0; j<4; j++){
              results[i][j] = xOr(sHex[i][j], keyHex[i][j]);
          }
      }
      return results;
  }
  
  public static String[][] AESNibbleSub(String[][] inStateHex){
      String[][] result = new String[4][4];

      for(int i=0; i<4; i++){
          for(int j = 0; j<4;j++){
              result[i][j] = aesSBox(inStateHex[i][j]);
          }
      }    
    return result;
  }
  
  
  public static String[][] AESShiftRow(String[][] inStateHex){
      String[][] result = new String[4][4];
      result[0] = inStateHex[0];
      for(int row = 1; row < 4; row++){
          for(int col = 0; col<4; col++){
              result[row][col] = inStateHex[row][(col+row)%4];
          }
      }
      
      return result;
  }
  
  public static String[][] AESMixColumn(String[][] inStateHex){
      String[][] matBox = {{"00000010","00000011","00000001","00000001"}
                          ,{"00000001","00000010","00000011","00000001"}
                          ,{"00000001","00000001","00000010","00000011"}
                          ,{"00000011","00000001","00000001","00000010"}};
      String[][] resultHex = new String[4][4];
      String[][] resultHexSub = new String[4][4];

      for(int i =0; i<4; i++){
          String[] col = new String[4];
          for(int j =0; j<4; j++){
            int valueDecimal = Integer.parseInt(inStateHex[j][i], 16);
          col[j] = String.format("%8s", Integer.toBinaryString(valueDecimal)).replace(' ', '0');
          }
          //Need to transpose after..
          resultHexSub[i] = multiplyWithForLoops(matBox, col);
          
      }
      
      for(int i =0; i<4;i++){
          for(int j =0; j<4; j++){
              int decimal = Integer.parseInt(resultHexSub[j][i],2);
               resultHex[i][j] = Integer.toString(decimal,16);
          }
      }
     
      return resultHex;
      
  }
  
  
  //Addition = xor
  //multiplication = complicated mult
      public static String[] multiplyWithForLoops(String[][] matrix, String[] vector) {
       String[] result = new String[4];
       for(int matRow = 0; matRow<4; matRow++){
           String[] colSub = new String[4];
           for(int col = 0; col<4; col++){
               if(matrix[matRow][col].matches("00000001")){
                  colSub[col] = vector[col]; 
               }else if(matrix[matRow][col].matches("00000010")){
                   colSub[col] = binXOr(vector[col].substring(1)+"0", "00011011");
               }else if(matrix[matRow][col].matches("00000011") && vector[col].charAt(0) == '1'){
                   //Its only correct sometimes...
                   colSub[col] = binXOr(vector[col].substring(1) + "0", vector[col]);
               }else{
                   colSub[col] = binXOr(vector[col].substring(1)+"0", vector[col]);
               }
           }
           //Now xor everything
           result[matRow] = binXOr(binXOr(binXOr(colSub[0],colSub[1]),colSub[2]),colSub[3]);
       }   
          
        
        return result;
    }
  
  
private static String binXOr(String value1, String value2) {
    System.out.println(value1);
    System.out.println(value2);
    StringBuilder sb = new StringBuilder();
    for(int index = 0; index < value1.length(); index ++ ) {
      sb.append((value1.charAt(index) !=  value2.charAt(index)) ? 1 : 0);
    }
    return sb.toString();
  }
    
}

