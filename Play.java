import java.util.*;

class Play{
  FrontEnd visuals = new FrontEnd();

  Grid grid;
  int depth; // Node depth in search tree
  int currentPlayer;

  Play(Grid grid){
    this.grid = grid;
    this.depth = depth;
  }

  // Receive place to drop piece and make a play
  void makePlay(int col, int picker){
   if(col < 0 || col > 6){
     System.out.println("Input mismatch");
     System.out.println();
     return;
   }

   if(grid.colFull(col)){
     System.out.println(visuals.ANSI_RED + "This collumn is full, you can't place there, please choose another collumn." + visuals.ANSI_RESET);
     System.out.println();
   }

   if(!grid.colFull(col)){
     if(even(picker)) this.grid.config[grid.freeLine(col)][col] = 'X';
     else this.grid.config[grid.freeLine(col)][col] = 'O';
   }
   return;
  }

  // Picker system auxiliar
  boolean even(int a){
    if(a % 2 == 0) return true;
    return false;
  }

  // Utility calculator
  public int utility(int player){
    return (linesUtility(player) + colUtility(player) + principalDiagUtility(player) + secondaryDiagUtility(player));
  }

  private int linesUtility(int currentPlayer){
    int xCounter = 0;
    int oCounter = 0;
    int linesSum = 0;

    for(int i=0; i < 6; i++){
      for(int j=0; j < 4; j++){

        if(grid.config[i][j] == 'X' || grid.config[i][j+1] == 'X' || grid.config[i][j+2] == 'X' || grid.config[i][j+3] == 'X'){
          xCounter++;
        }

        if(grid.config[i][j] == 'O' || grid.config[i][j+1] == 'O' || grid.config[i][j+2] == 'O' || grid.config[i][j+3] == 'O'){
          oCounter++;
        }

        linesSum = linesSum + segmentEval(xCounter, oCounter);
        xCounter = 0;
        oCounter = 0;
      }
    }

    int finalSum;
    if(even(currentPlayer)) finalSum = linesSum + 16; // Rule for bonus
    else finalSum = linesSum - 16; // Rule for bonus

    return finalSum;
  }

  private int colUtility(int currentPlayer){

    int xCounter = 0;
    int oCounter = 0;
    int colsSum = 0;

    for(int i=0; i < 3; i++){
      for(int j=0; j < 7; j++){

        if(grid.config[i][j] == 'X' || grid.config[i+1][j] == 'X' || grid.config[i+2][j] == 'X' || grid.config[i+3][j] == 'X'){
          xCounter++;
        }

        if(grid.config[i][j] == 'O' || grid.config[i+1][j] == 'O' || grid.config[i+2][j] == 'O' || grid.config[i+3][j] == 'O'){
          oCounter++;
        }

        colsSum += segmentEval(xCounter, oCounter);
        xCounter = 0; oCounter = 0; // Resets each collumn
      }
    }

    int finalSum;
    if(even(currentPlayer)) finalSum = colsSum + 16;
    else finalSum = colsSum - 16;

    return finalSum;
  }

  private int principalDiagUtility(int currentPlayer){
    int xCounter = 0;
    int oCounter = 0;
    int diagonalSum = 0;

    for(int i=3; i < 6; i++){
      for(int j=0; j < 4; j++){

        if(grid.config[i][j] == 'X' || grid.config[i-1][j+1] == 'X' || grid.config[i-2][j+2] == 'X' || grid.config[i-3][j+3] == 'X'){
          xCounter++;
        }

        if(grid.config[i][j] == 'O' || grid.config[i-1][j+1] == 'O' || grid.config[i-2][j+2] == 'O' || grid.config[i-3][j+3] == 'O'){
          oCounter++;
        }

        diagonalSum += segmentEval(xCounter, oCounter);
        xCounter = 0; oCounter = 0;
      }
    }

    int finalSum;
    if(even(currentPlayer)) finalSum = diagonalSum + 16;
    else finalSum = diagonalSum - 16;

    return finalSum;
  }

  private int secondaryDiagUtility(int currentPlayer){
    int xCounter = 0;
    int oCounter = 0;
    int diagonalSum = 0;

    for(int i=3; i < 6; i++){
      for(int j=6; j > 2; j--){

        if(grid.config[i][j] == 'X' || grid.config[i-1][j-1] == 'X' || grid.config[i-2][j-2] == 'X' || grid.config[i-3][j-3] == 'X'){
          xCounter++;
        }

        if(grid.config[i][j] == 'O' || grid.config[i-1][j-1] == 'O' || grid.config[i-2][j-2] == 'O' || grid.config[i-3][j-3] == 'O'){
          oCounter++;
        }

        diagonalSum += segmentEval(xCounter, oCounter);
        xCounter = 0; oCounter = 0;
      }
    }

    int finalSum;
    if(even(currentPlayer)) finalSum = diagonalSum + 16;
    else finalSum = diagonalSum - 16;

    return finalSum;
  }

  private int segmentEval(int xCounter, int oCounter){

    // Segments with no O's
    if(xCounter == 1 && oCounter == 0) return 1;
    if(xCounter == 2 && oCounter == 0) return 10;
    if(xCounter == 3 && oCounter == 0) return 50;
    if(xCounter == 4 && oCounter == 0) return 512;

    // Segments with no X's
    if(xCounter == 0 && oCounter == 1) return -1;
    if(xCounter == 0 && oCounter == 2) return -10;
    if(xCounter == 0 && oCounter == 3) return -50;
    if(xCounter == 0 && oCounter == 4) return -512;

    // Empty or mixed segments
    else return 0;
  }
}
